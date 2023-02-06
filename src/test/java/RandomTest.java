import bookstore_api.BookstoreAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page_objects.*;
import classes.User;
import tests.BaseTest;
import util.factories.DriverFactory;
import util.factories.UserFactory;


public class RandomTest extends BaseTest {
    @Test
    public void navTest() {
        LoginPage loginPageObject = new LoginPage(driver).get();
        BookstorePage bookstorePageObject = loginPageObject.navigateTo().bookstorePage();
        ProfilePage profilePageObject = bookstorePageObject.navigateTo().profilePage();
        loginPageObject = profilePageObject.navigateTo().loginPage()
                .loginAsExpectingError("dasasd", "dsaasd");
        Assert.assertEquals(1, 1);
    }

    @Test
    public void loginTest() {
        LoginPage lp = new LoginPage(driver).get();
        ProfilePage pp = lp.loginAs("user1", "Password!123");
    }

    @Test
    public void validUserLogin() throws InterruptedException {
        User validUser = UserFactory.getExistingUser();
        System.out.println(validUser);
        BookstoreAPI.authorize(validUser, driver);
        LoginPage lp = new LoginPage(driver).get();
        Thread.sleep(5000);
        UserFactory.deleteUser(validUser);
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        Thread.sleep(5000);
    }

    @AfterMethod
    public void afterMethod() {
        driver.manage().deleteAllCookies();
    }
}
