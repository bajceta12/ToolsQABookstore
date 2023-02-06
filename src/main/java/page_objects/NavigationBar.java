package page_objects;

import org.apache.commons.logging.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NavigationBar {
    private final WebDriver driver;
    private final WebDriverWait wdwait;
    private final JavascriptExecutor js;

    public NavigationBar(WebDriver driver) {
        this.driver = driver;
        wdwait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[@class='text' and text()='Login']")
    private WebElement loginTab;

    @FindBy(xpath = "//span[@class='text' and text()='Book Store']")
    private WebElement bookstoreTab;

    @FindBy(xpath = "//span[@class='text' and text()='Profile']")
    private WebElement profileTab;

    public LoginPage loginPage() {
        scrollWaitAndClick(loginTab);
        waitForUrl(LoginPage.getUrl());
        return new LoginPage(driver);
    }

    public BookstorePage bookstorePage() {
        scrollWaitAndClick(bookstoreTab);
        waitForUrl(BookstorePage.getUrl());
        return new BookstorePage(driver);
    }

    public ProfilePage profilePage() {
        scrollWaitAndClick(profileTab);
        waitForUrl(ProfilePage.getUrl());
        return new ProfilePage(driver);
    }

    private void scrollWaitAndClick(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView();", element);
        driver.findElement(By.cssSelector("*")).sendKeys(Keys.END);
        wdwait.until(ExpectedConditions.elementToBeClickable(element)).click();
        try {
            Thread.sleep(395);
        } catch (InterruptedException ignored) {

        }
    }

    protected void waitForUrl(String url) {
        wdwait.until(ExpectedConditions.urlToBe(url));
    }
}
