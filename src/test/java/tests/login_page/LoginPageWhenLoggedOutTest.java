package tests.login_page;

import classes.User;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.*;
import page_objects.LoginPage;
import page_objects.ProfilePage;
import tests.BaseTest;
import util.factories.UserFactory;

public class LoginPageWhenLoggedOutTest extends BaseTest {
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
        lp.load();
    }

    @Test
    public void noUsernameNoPassword() {
        lp.loginAsExpectingError("", "");
        Assert.assertTrue(lp.usernameIsInvalid(), "Username should be invalid.");
        Assert.assertTrue(lp.passwordIsInvalid(), "Password should be invalid.");
    }

    @Test
    public void noUsername() {
        lp.loginAsExpectingError("", u.getPassword());
        Assert.assertTrue(lp.usernameIsInvalid(), "Username should be invalid.");
        Assert.assertFalse(lp.passwordIsInvalid(), "Password should be valid.");
    }

    @Test
    public void noPassword() {
        lp.loginAsExpectingError(u.getUsername(), "");
        Assert.assertFalse(lp.usernameIsInvalid(), "Username should be valid.");
        Assert.assertTrue(lp.passwordIsInvalid(), "Password should be invalid.");
    }



    @Test (dataProvider = "invalid usernames and valid passwords", dataProviderClass = LoginDataProvider.class)
    public void invalidUsernameValidPassword(String un, String pw) {
        lp.loginAsExpectingError(un, pw);
        Assert.assertEquals(lp.getErrorMessage(), lp.getExpectedErrorMessage(), "Error message not as expected");
    }

    @Test (dataProvider = "valid usernames and invalid passwords", dataProviderClass = LoginDataProvider.class)
    public void validUsernameInvalidPassword(String un, String pw) {
        lp.loginAsExpectingError(un, pw);
        Assert.assertEquals(lp.getErrorMessage(), lp.getExpectedErrorMessage(), "Error message not as expected");
    }

    @Test
    public void existingUserCanLogIn() {
        LoginPage lp = new LoginPage(driver).get();
        ProfilePage pp = lp.loginAs(u);
        Assert.assertEquals(pp.getLoggedInUserName(), u.getUsername(), "Logged in username not correct");
    }

    // shouldn't be on the page

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void loggedInUserNameNotShown() {
        lp.getLoggedInUserName();
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void profileButtonNotDisplayed() {
        lp.goToProfilePage();
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void logoutButtonNotDisplayed() {
        lp.logout();
        Assert.fail();
    }

    @AfterClass
    public void teardown() {
        super.teardown();
        UserFactory.deleteUser(u);
    }
}
