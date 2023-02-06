package page_objects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage<T extends BasePage<T>> extends LoadableComponent<T> {
    protected WebDriver driver;
    protected WebDriverWait wdwait;

    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wdwait = new WebDriverWait(driver, Duration.ofSeconds(2));
        js = (JavascriptExecutor) driver;
    }

    protected void clearAndType(WebElement field, String text) {
        field.clear();
        field.sendKeys(text);
    }

    protected WebElement scrollTo(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return null;
    }

    protected WebElement waitForClickable(WebElement element) {
        return wdwait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForUrl(String url) {
        wdwait.until(ExpectedConditions.urlToBe(url));
    }

    protected void waitForAttributeToBe(WebElement element, String attribute, String value) {
        wdwait.until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    protected boolean waitForAttributeToContain(WebElement elem, String attr, String val) {
        try {
            return wdwait.until(ExpectedConditions.attributeContains(elem, attr, val));
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected Alert waitForAlert() {
        return wdwait.until(ExpectedConditions.alertIsPresent());
    }

    protected WebElement waitForVisiblity(WebElement elem) {
        return wdwait.until(ExpectedConditions.visibilityOf(elem));
    }

    protected void waitForInvisibility(WebElement element) {
        wdwait.until(ExpectedConditions.not(ExpectedConditions.invisibilityOf(element)));
    }
}