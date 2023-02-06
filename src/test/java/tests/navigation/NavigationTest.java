package tests.navigation;

;
import org.testng.Assert;
import org.testng.annotations.Test;
import page_objects.BookstorePage;
import page_objects.LoginPage;
import page_objects.ProfilePage;

import tests.BaseTest;

public class NavigationTest extends BaseTest {
    @Test
    public void navigation() {
        LoginPage lp = new LoginPage(driver).get();
        Assert.assertEquals(driver.getCurrentUrl(), LoginPage.getUrl(), "Not on login page.");

        ProfilePage pp = lp.navigateTo().profilePage();
        Assert.assertEquals(driver.getCurrentUrl(), ProfilePage.getUrl(), "Not on profile page.");

        BookstorePage bsp = pp.navigateTo().bookstorePage();
        Assert.assertEquals(driver.getCurrentUrl(), BookstorePage.getUrl(), "Not on bookstore page.");

        lp = bsp.navigateTo().loginPage();
        lp.navigateTo().bookstorePage()
                .navigateTo().profilePage()
                .navigateTo().bookstorePage()
                .navigateTo().profilePage();
    }
}
