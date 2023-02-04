import bookstore_api.BookstoreAPI;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page_objects.*;
import classes.User;
import util.factories.DriverFactory;
import util.factories.UserFactory;


public class RandomTest {
    WebDriver driver;

    @BeforeMethod
    public void beforeMethod() {
        driver = DriverFactory.getDriver("chrome", null);
    }

    @Test
    public void navTest() throws InterruptedException {
        LoginPage loginPageObject = new LoginPage(driver).get();
        BookstorePage bookstorePageObject = loginPageObject.navigateTo().bookstorePage();
        ProfilePage profilePageObject = bookstorePageObject.navigateTo().profilePage();
        loginPageObject = profilePageObject.navigateTo().loginPage()
                .loginAsExpectingError("dasasd", "dsaasd");
        Assert.assertEquals(1, 1);
    }

    @Test
    public void loginTest() throws InterruptedException {
        driver.get("https://demoqa.com/login");
        LoginPage lp = new LoginPage(driver);
        ProfilePage pp = lp.loginAs("user1", "Password!123");
    }

    @Test
    public void validUserLogin() throws InterruptedException {
        User validUser = UserFactory.createNewUser();
        System.out.println(validUser);
        BookstoreAPI.authorize(validUser, driver);
        LoginPage lp = new LoginPage(driver).get();
        driver.navigate().refresh();
        if (BookstoreAPI.deleteUser(validUser, driver)) {
            System.out.println("User successfully deleted.");
        } else {
            System.out.println("Something went wrong.");
        }
    }

    @Test
    public void testtest() throws InterruptedException {
        driver.get("https://demoqa.com/automation-practice-form");
        driver.findElement(By.id("react-select-3-input")).sendKeys("Hello there");
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
        DriverFactory.remove();
    }
}
