package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import util.factories.DriverFactory;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wdwait;
    protected JavascriptExecutor js;

    @BeforeClass
    @Parameters ({"browser", "options"})
    public void setup(@Optional String browser, @Optional String options) {
        if (browser == null) {
            browser = "chrome";
        }
        if (options == null) {
            options = "--start-maximized";
        }
        driver = DriverFactory.getDriver(browser, options);
    }

    @AfterClass
    public void teardown() {
        DriverFactory.quitDriver();
    }

    protected void waitForUrl(String url) {
        wdwait.until(ExpectedConditions.urlToBe(url));
    }
}
