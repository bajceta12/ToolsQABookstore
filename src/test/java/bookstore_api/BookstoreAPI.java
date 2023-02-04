package bookstore_api;

import bookstore_api.pojo.UsernameAndPassword;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import classes.User;
import org.openqa.selenium.chrome.ChromeDriver;
import page_objects.ProfilePage;
import util.factories.UserFactory;

import static io.restassured.RestAssured.*;

public class BookstoreAPI {
    private static final String baseURI = "https://demoqa.com";
    private static final String accountSrc = "/Account/v1";
    private static final String bookstoreSrc = "/BookStore/v1";
    private static final String bookSrc = bookstoreSrc + "/Book";
    private static final String booksSrc = bookstoreSrc +  "/Books";
    private static final String userSrc = accountSrc + "/User";
    private static final String authorizedSrc = accountSrc + "/Authorized";
    private static final String generateToken = accountSrc + "/GenerateToken";
    static {
        RestAssured.baseURI = baseURI;
    }

    public static String createUserAndGetID(String username, String password) {
        UsernameAndPassword unpw = new UsernameAndPassword(username, password);
        return given().
                    contentType(ContentType.JSON).body(unpw).
                when().
                    post(userSrc).
                    jsonPath().getString("userID");
    }

    public static boolean authorize(User user, WebDriver driver) {
        String originalUrl = driver.getCurrentUrl();
        driver.get("https://demoqa.com/");
        UsernameAndPassword unpw = new UsernameAndPassword(user.getUsername(), user.getPassword());
        JsonPath jsp = given().
                            contentType(ContentType.JSON).
                            body(unpw).
                        when().
                            post(generateToken).jsonPath();

        try {
            driver.manage().addCookie(new Cookie("token", jsp.getString("token")));
            driver.manage().addCookie(new Cookie("expires", jsp.getString("expires")));
            driver.manage().addCookie(new Cookie("userID", user.getUserID()));
            driver.manage().addCookie(new Cookie("userName", user.getUsername()));
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            driver.get(originalUrl);
            return false;
        }
        driver.get(originalUrl);
        return true;
    }

    public static boolean deleteUser(User user, WebDriver authorizedDriver) {
        try {
            String token = authorizedDriver.manage().getCookieNamed("token").getValue();
            return deleteUser(user, token);
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean deleteUser(User user, String token) {
        try {
            given().
                    contentType(ContentType.ANY).
                    header("Authorization", "Bearer " + token).
                    when().
                    delete(userSrc + "/" + user.getUserID()).
                    then().
                    assertThat().statusCode(204);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        try {
            User user = UserFactory.createNewUser();
            System.out.println(user);

            if (BookstoreAPI.authorize(user, driver)) {
                System.out.println("Authorized, token generated and added");
            } else {
                System.out.println("Authorization failed");
            }
            Thread.sleep(2000);
            ProfilePage pp = new ProfilePage(driver).get();
            Thread.sleep(2000);
            driver.navigate().refresh();
            Thread.sleep(2000);
            if (BookstoreAPI.deleteUser(user, driver)) {
                System.out.println("User deleted");
            } else {
                System.out.println("User not deleted");
            }
            driver.quit();
        } finally {
            driver.quit();
        }

    }
}
