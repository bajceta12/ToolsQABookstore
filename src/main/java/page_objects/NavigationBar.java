package page_objects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        return new LoginPage(driver);
    }

    public BookstorePage bookstorePage() {
        scrollWaitAndClick(bookstoreTab);
        return new BookstorePage(driver);
    }

    public ProfilePage profilePage() {
        scrollWaitAndClick(profileTab);
        return new ProfilePage(driver);
    }

    private void scrollWaitAndClick(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView();", element);
        wdwait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
