package tests.profile_page;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page_objects.LoginPage;
import page_objects.ProfilePage;
import page_objects.RegistrationPage;
import tests.BaseTest;

public class ProfilePageWhenLoggedOutTest extends BaseTest {
    private ProfilePage pp;

    @BeforeClass
    public void setupMe() {
        pp = new ProfilePage(driver).get();
    }

    @BeforeMethod
    public void beforeMethod() {
        pp.load();
    }

    @Test
    public void goToLoginPageTest() {
        LoginPage lp = pp.goToLoginPage();
        Assert.assertEquals(driver.getCurrentUrl(), LoginPage.getUrl());
    }

    @Test
    public void goToRegistrationPage() {
        RegistrationPage rp = pp.goToRegistrationPage();
        String currUrl = driver.getCurrentUrl();
        Assert.assertEquals(currUrl, RegistrationPage.getUrl(), "Not on registration page: " + currUrl);
    }

    // shouldn't be on the page

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void logoutButtonNotDisplayed() {
        pp.logout();
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void goToBookstoreButtonNotDisplayed() {
        pp.goToBookstore();
        Assert.fail();
    }

    @Test (expectedExceptions = {TimeoutException.class})
    public void loggedInUserNameNotDisplayed() {
        pp.getLoggedInUserName();
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void deleteAllBooksButtonNotDisplayed() {
        pp.deleteAllBooks(true);
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void deleteAccountButtonNotDisplayed() {
        pp.deleteAccount(true);
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void previousButtonNotDisplayed() {
        pp.goToPreviousPage();
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void nextButtonNotDisplayed() {
        pp.goToNexPage();
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void settingBooksPerPageNotDisplayed() {
        pp.setBooksPerPage(5);
        Assert.fail();
    }

    @Test (expectedExceptions = {NoSuchElementException.class})
    public void searchBoxNotDisplayed() {
        pp.searchFor("");
        Assert.fail();
    }
}
