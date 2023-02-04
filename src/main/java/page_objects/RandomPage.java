package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class RandomPage extends LoadableComponent<RandomPage> {
    private WebDriver driver;
    private static final String url = "https://demoqa.com/swagger/#/Account/AccountV1UserPost";

    public RandomPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected void isLoaded() throws Error {
        String currUrl = driver.getCurrentUrl();
        if (!currUrl.equals(url)) throw new Error("Not on random page :D " + currUrl);
    }

    @Override
    protected void load() {
        driver.get(url);
    }
}
