package bookstore_api;

import bookstore_api.pojo.Book;
import bookstore_api.pojo.UsernameAndPassword;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import classes.User;
import org.openqa.selenium.chrome.ChromeDriver;
import page_objects.LoginPage;
import page_objects.ProfilePage;
import util.factories.UserFactory;

import java.util.*;

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
        if (!originalUrl.startsWith(baseURI)) {
            driver.get(baseURI);
        }
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
            e.printStackTrace();
            driver.get(originalUrl);
            return false;
        }
        driver.get(originalUrl);
        return true;
    }

    public static String getToken(User user) {
        UsernameAndPassword unpw = new UsernameAndPassword(user.getUsername(), user.getPassword());
        return  given().
                    contentType(ContentType.JSON).
                    body(unpw).
                when().
                    post(generateToken).jsonPath().getString("token");
    }

    public static void deleteUser(User u) {
        deleteUser(u, getToken(u));
    }

    public static void deleteUser(User user, String token) {
        given().
                contentType(ContentType.ANY).
                header("Authorization", "Bearer " + token).
        when().
                delete(userSrc + "/" + user.getUserID());
    }

    private static void deleteAllBooks(User u, String token) {
        given().
                contentType(ContentType.JSON).
                header("Authorization", "Bearer " + token).
                param("UserId", u.getUserID()).
        when().
                delete(bookSrc);
    }

    public static void deleteAllBooks(User u) {
        deleteAllBooks(u, getToken(u));
    }

    public static List<Book> getAllBooks() {
        List<LinkedHashMap<Object, Object>> lhm =
                given().
                        contentType(ContentType.JSON).
                when().
                        get(booksSrc).
                then().
                        extract().body().jsonPath().getList("books");
        ArrayList<Book> books = new ArrayList<>();
        for (HashMap<Object, Object> hm : lhm) {
            books.add(hashMapToBook(hm));
        }
        return books;
    }

    private static Book hashMapToBook(HashMap<Object, Object> hm) {
        return new Book(
                (String) hm.get("isbn"),
                (String) hm.get("title"),
                (String) hm.get("subTitle"),
                (String) hm.get("author"),
                (String) hm.get("publish_date"),
                (String) hm.get("publisher"),
                (Integer) hm.get("pages"),
                (String) hm.get("description"),
                (String) hm.get("website")
        );
    }

    public static Book getSingleBook(String isbn) {
        return  given().
                        contentType(ContentType.JSON).
                        param("ISBN", isbn).
                when().
                        get(bookSrc).
                then().
                        extract().body().jsonPath().getObject("", Book.class);

    }

    public static Book getSingleBook(Book b) {
        return getSingleBook(b.getIsbn());
    }

    public static void main(String[] args) {
        List<Book> books = getAllBooks();
        System.out.println(books);
        Book singleBook = books.get(0);
        System.out.println(getSingleBook(singleBook));
    }
}
