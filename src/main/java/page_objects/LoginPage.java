package page_objects;

import classes.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage<LoginPage> {
    private final NavigationBar navigationBar;

    private static final String URL = "https://demoqa.com/login";

    @FindBy (id = "userName-value")
    private WebElement loggedInUser;

    @FindBy (linkText = "profile")
    private WebElement profileButton;

    @FindBy (id = "userName")
    private WebElement userNameField;

    @FindBy (id = "password")
    private WebElement passwordField;

    @FindBy (id = "login")
    private WebElement loginButton;

    @FindBy (id = "newUser")
    private WebElement newUserButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        navigationBar = new NavigationBar(driver);
        PageFactory.initElements(driver, this);
    }
    public NavigationBar navigateTo() {
        return navigationBar;
    }

    public ProfilePage loginAs(User validUser) {
        return loginAs(validUser.getUsername(), validUser.getPassword());
    }

    public ProfilePage loginAs(String username, String password) {
        return  typeUsername(username)
                .typePassword(password)
                .login();
    }

    public LoginPage loginAsExpectingError(String username, String password) {
        return  typeUsername(username)
                .typePassword(password)
                .loginExpectingError();
    }

    public RegistrationPage goToRegistrationPage() {
        newUserButton.click();
        waitForUrl(RegistrationPage.getUrl());
        return new RegistrationPage(driver);
    }

    public LoginPage typeUsername(String username) {
        clearAndType(userNameField, username);
        return this;
    }

    public LoginPage typePassword(String password) {
        clearAndType(passwordField, password);
        return this;
    }

    public ProfilePage login() {
        loginButton.click();
        waitForUrl(ProfilePage.getUrl());
        return new ProfilePage(driver);
    }

    public LoginPage loginExpectingError() {
        loginButton.click();
        return this;
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
            throw new Error("Not on LoginPage: " + currUrl);
        }
    }
}
