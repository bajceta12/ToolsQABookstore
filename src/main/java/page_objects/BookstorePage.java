package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class BookstorePage extends BasePage<BookstorePage> {
    private NavigationBar navigationBar;
    private static final String URL = "https://demoqa.com/books";

    public BookstorePage(WebDriver driver) {
        super(driver);
        navigationBar = new NavigationBar(driver);
        PageFactory.initElements(driver, this);
    }

    public NavigationBar navigateTo() {
        return navigationBar;
    }

    public static String getUrl() {
        return URL;
    }

    @Override
    protected void load() {
        driver.get(URL);
    }

    @Override
    protected void isLoaded() throws Error {
        String currUrl = driver.getCurrentUrl();
        if (!currUrl.equals(URL)) {
            throw new Error("Not on BookstorePage: " + currUrl);
        }
    }
}
