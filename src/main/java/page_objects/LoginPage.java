package page_objects;

import classes.User;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage<LoginPage> {
    private final NavigationBar navigationBar;

    private static final String URL = "https://demoqa.com/login";

    private static final String invalidClass = "is-invalid";

    private static final String expectedErrorMessage = "Invalid username or password!";

    @FindBy (id = "userName-value")
    private WebElement loggedInUserName;

    @FindBy (linkText = "profile")
    private WebElement profileButton;

    @FindBy (id = "userName")
    private WebElement userNameField;

    @FindBy (id = "password")
    private WebElement passwordField;

    @FindBy (id = "login")
    private WebElement loginButton;

    @FindBy (id = "submit")
    private WebElement logoutButton;

    @FindBy (id = "newUser")
    private WebElement newUserButton;

    @FindBy (id = "name")
    private WebElement errorMessage;

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
                .clickLoginButton();
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

    public ProfilePage clickLoginButton() {
        loginButton.click();
        waitForUrl(ProfilePage.getUrl());
        return new ProfilePage(driver);
    }

    public LoginPage loginExpectingError() {
        loginButton.click();
        return this;
    }

    public String getLoggedInUserName() {
        return loggedInUserName.getText();
    }

    public ProfilePage goToProfilePage() {
        profileButton.click();
        waitForUrl(ProfilePage.getUrl());
        return new ProfilePage(driver);
    }

    public String getErrorMessage() {
        try {
            return waitForClickable(errorMessage).getText();
        } catch (TimeoutException e) {
            return "Failed waiting for error msg.";
        }

    }

    public String getExpectedErrorMessage() {
        return expectedErrorMessage;
    }

    public boolean passwordIsInvalid() {
        return waitForAttributeToContain(passwordField, "class", invalidClass);
    }

    public boolean usernameIsInvalid() {
        return waitForAttributeToContain(userNameField, "class", invalidClass);
    }

    public LoginPage logout() {
        logoutButton.click();
        waitForInvisibility(loginButton);
        return this;
    }

    public static String getUrl() {
        return URL;
    }

    @Override
    public void load() {
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
