package tests.profile_page;

import bookstore_api.BookstoreAPI;
import classes.User;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page_objects.BookstorePage;
import page_objects.LoginPage;
import page_objects.ProfilePage;
import tests.BaseTest;
import util.factories.UserFactory;

public class ProfilePageWhenLoggedInTest extends BaseTest {
    private User u;
    private ProfilePage pp;

    @BeforeClass
    public void setupMe() {
        u = UserFactory.getExistingUser();
        pp = new ProfilePage(driver).get();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.manage().deleteAllCookies();
        BookstoreAPI.authorize(u, driver);
        pp.load();
    }

    @Test
    public void newUserShouldHaveZeroBooks() {
        Assert.assertFalse(pp.hasBooks());
    }

    @Test
    public void goToBookstorePage() {
        BookstorePage bp = pp.goToBookstore();
        String currUrl = driver.getCurrentUrl();
        Assert.assertEquals(currUrl, BookstorePage.getUrl(), "Not on bookstore page: " + currUrl);
    }

    @Test
    public void logout() {
        LoginPage lp = pp.logout();
        String currUrl = driver.getCurrentUrl();
        Assert.assertEquals(currUrl, LoginPage.getUrl(), "Not on profile page: " + currUrl);
        lp.typeUsername("");
    }

    // shouldn't be on the page

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void loginButtonNotDisplayed() {
        pp.goToLoginPage();
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void registerButtonNotDisplayed() {
        pp.goToRegistrationPage();
        Assert.fail();
    }

    @AfterClass
    public void teardown() {
        super.teardown();
        UserFactory.deleteUser(u);
    }
}
