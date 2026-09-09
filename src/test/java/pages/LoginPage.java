package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(css = "input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input[type='password']")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitBtn;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void provideEmail(String email) {
        type(emailInput, email);
    }

    public void providePassword(String password) {
        type(passwordInput, password);
    }

    public void clickSubmit() {
        safeClick(submitBtn);
    }

    public HomePage loginAs(String email, String password) {
        provideEmail(email);
        providePassword(password);
        clickSubmit();
        HomePage home = new HomePage(driver);
        home.isLoaded();
        return home;
    }
}