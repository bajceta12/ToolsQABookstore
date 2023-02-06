package page_objects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProfilePage extends BasePage<ProfilePage> {
    private final NavigationBar navigationBar;
    private static final String noBooksAvailablePromptMsg = "No books available in your's collection!";
    private static final String allBookDeletedPromptMsg = "All Books deleted.";
    private static final String URL = "https://demoqa.com/profile";

    @FindBy (css = ".btn.btn-primary")
    private List<WebElement> buttons;

    @FindBy (className = "modal-body")
    private WebElement promptBody;

    @FindBy (id = "closeSmallModal-ok")
    private WebElement promptOkButton;

    @FindBy (id = "closeSmallModal-cancel")
    private WebElement promptCancelButton;

    @FindBy (className = "rt-noData")
    private WebElement noData;

    @FindBy (id = "userName-value")
    private WebElement loggedInUserName;

    @FindBy (linkText = "login")
    private WebElement loginButton;

    @FindBy (linkText = "register")
    private WebElement registerButton;

    @FindBy (id = "searchBox")
    private WebElement searchBox;

    @FindBy (className = "-btn")
    private List<WebElement> pagesButtons;
//    @FindBy (css = "#button:contains('Next')")
//    private WebElement nextButton;
//
//    @FindBy (css = "#button:contains('Previous')")
//    private WebElement prevButton;

    @FindBy (css = "[aria-label='rows per page'")
    private WebElement rowsPerPage;

    @FindBy (css = "[aria-label='jump to page'")
    private WebElement jumpToPage;

    public ProfilePage(WebDriver driver) {
        super(driver);
        navigationBar = new NavigationBar(driver);
        PageFactory.initElements(driver, this);
    }

    public String getLoggedInUserName() {
        return waitForClickable(loggedInUserName).getText();
    }

    public LoginPage goToLoginPage() {
        loginButton.click();
        waitForUrl(LoginPage.getUrl());
        return new LoginPage(driver);
    }

    public RegistrationPage goToRegistrationPage() {
        registerButton.click();
        waitForUrl(RegistrationPage.getUrl());
        return new RegistrationPage(driver);
    }

    public BookstorePage goToBookstore() {
        getGoToBookstoreButton().click();
        waitForUrl(BookstorePage.getUrl());
        return new BookstorePage(driver);
    }

    private WebElement getPrevPageButton() {
        return getButton("Previous", pagesButtons);
    }

    private WebElement getNextPageButton() {
        return getButton("Next", pagesButtons);
    }

    public ProfilePage searchFor(String book) {
        clearAndType(searchBox, book);
        searchBox.click();
        return this;
    }

    public ProfilePage setBooksPerPage(int rowsPerPage) {
        Select s = new Select(this.rowsPerPage);
        switch (rowsPerPage) {
            case 5 -> s.selectByVisibleText("5 rows");
            case 10 -> s.selectByVisibleText("10 rows");
            case 20 -> s.selectByVisibleText("20 rows");
            case 25 -> s.selectByVisibleText("25 rows");
            case 50 -> s.selectByVisibleText("50 rows");
            case 100 -> s.selectByVisibleText("100 rows");
            default -> throw new IllegalStateException("Options for rows per page doesn't exist: " + rowsPerPage);
        }
        return this;
    }

    public ProfilePage goToPreviousPage() {
        if (hasPreviousPage()) {
            getPrevPageButton().click();
            return this;
        }
        throw new IllegalStateException("Clicking on previous page button but no previous page is found.");
    }

    public ProfilePage goToNexPage() {
        if (hasNextPage()) {
            getPrevPageButton().click();
            return this;
        }
        throw new IllegalStateException("Clicking on next page button but no next page is found.");
    }

    public boolean hasPreviousPage() {
        return isEnabled(getPrevPageButton());
    }

    public boolean hasNextPage() {
        return isEnabled(getNextPageButton());
    }

    private boolean isEnabled(WebElement elem) {
        return elem.getAttribute("disabled") == null;
    }

    public LoginPage logout() {
        getLogouButton().click();
        waitForUrl(LoginPage.getUrl());
        return new LoginPage(driver);
    }

    private WebElement getLogouButton() {
        return getButton("Log out", buttons);
    }

    private WebElement getGoToBookstoreButton() {
        return getButton("Go To Book Store", buttons);
    }

    private WebElement getDeleteAccountButton() {
        return getButton("Delete Account", buttons);
    }
    private WebElement getDeleteAllBooksButton() {
        return getButton("Delete All Books", buttons);
    }

    public String deleteAllBooks(boolean cancel) {
        return doActionGetAlertText(cancel, getDeleteAllBooksButton());
    }

    public String deleteAccount(boolean cancel) {
        return doActionGetAlertText(cancel, getDeleteAccountButton());
    }

    private WebElement getButton(String visibleText, List<WebElement> buttons) {
        for (WebElement btn : buttons) {
            if (btn.getText().equals(visibleText)) return btn;
        }
        throw new NoSuchElementException("No button[" + visibleText + "] found.");
    }

    private String doActionGetAlertText(boolean cancel, WebElement elem) {
        elem.click();
        waitForClickable(promptBody);
        if (cancel) {
            waitForClickable(promptCancelButton).click();
            return "canceled";
        } else {
            waitForClickable(promptOkButton).click();
        }

        Alert alert = waitForAlert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }

    public boolean hasBooks() {
        try {
            waitForClickable(noData);
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public NavigationBar navigateTo() {
        return navigationBar;
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
            throw new Error("Not on ProfilePage: " + currUrl);
        }
    }
}
