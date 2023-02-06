package tests.login_page;

import bookstore_api.BookstoreAPI;
import classes.User;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.*;
import page_objects.LoginPage;
import page_objects.ProfilePage;
import tests.BaseTest;
import util.factories.UserFactory;

public class LoginPageWhenLoggedInTest extends BaseTest {
    private User u;
    private LoginPage lp;

    @BeforeClass
    public void setupMe() {
        u = UserFactory.getExistingUser();
        lp = new LoginPage(driver).get();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.manage().deleteAllCookies();
        BookstoreAPI.authorize(u, driver);
        lp.load();
    }


    @Test
    public void goToProfilePage() {
        ProfilePage pp = lp.goToProfilePage();
        Assert.assertEquals(pp.getLoggedInUserName(), u.getUsername(), "Username not displayed correctly.");
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void logout() {
        try {
            lp.logout();
        } catch (NoSuchElementException e) {
            Assert.fail("Couldn't find logout button.");
        }
        lp.logout();
    }

    // shouldn't be on the page

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void usernameFieldNotDisplayed() {
        lp.typeUsername("");
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void passwordFieldNotDisplayed() {
        lp.typePassword("");
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void logInButtonNotDisplayed() {
        lp.clickLoginButton();
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void newUserButtonNotDisplayed() {
        lp.goToRegistrationPage();
        Assert.fail();
    }

    @AfterClass
    public void teardown() {
        super.teardown();
        UserFactory.deleteUser(u);
    }
}
