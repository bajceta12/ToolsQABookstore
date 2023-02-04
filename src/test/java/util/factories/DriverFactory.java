package util.factories;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static void remove() {
        driver.remove();
    }

    public static WebDriver getDriver(String browser, String options) {
        if (driver.get() == null) {
            driver.set(getNewDriverInstance(browser, options));
        }
        return driver.get();
    }

    private static WebDriver getNewDriverInstance(String browser, String options) {
        switch (browser) {
            case "chrome" -> {
                ChromeOptions opt = new ChromeOptions();
                if (options != null) {
                    for (String option : options.split(" ")) {
                        opt.addArguments(option);
                    }
                }
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(opt);
            }
            case "firefox" -> {
                FirefoxOptions opt = new FirefoxOptions();
                if (options != null) {
                    for (String option : options.split(" ")) {
                        opt.addArguments(option);
                    }
                }
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(opt);
            }
            case "edge" -> {
                EdgeOptions opt = new EdgeOptions();
                if (options != null) {
                    for (String option : options.split(" ")) {
                        opt.addArguments(option);
                    }
                }
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver(opt);
            }
            case "htmlunit" -> {
                return new HtmlUnitDriver();
            }
            default -> throw new IllegalArgumentException("Driver not supported for browser: " + browser);
        }
    }
}
