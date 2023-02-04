package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage extends BasePage<RegistrationPage> {
    private final NavigationBar navigationBar;
    private static final String URL = "https://demoqa.com/register";

    @FindBy (id = "firstname")
    private WebElement firstNameField;

    @FindBy (id = "")
    private WebElement lastNameField;

    @FindBy (id = "")
    private WebElement userNameField;

    @FindBy (id = "")
    private WebElement passwordField;

    @FindBy (id = "recaptcha-anchor")
    private WebElement reCAPTCHA;

    @FindBy (id = "register")
    private WebElement registerButton;

    @FindBy (id = "gotologin")
    private WebElement backToLoginButton;

    public RegistrationPage(WebDriver driver) {
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
        if (!currUrl.equals(URL)) throw new Error("Not on RegistrationPage: " + currUrl);
    }
}
