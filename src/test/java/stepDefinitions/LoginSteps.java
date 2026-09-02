package stepDefinitions;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginSteps {

    private WebDriver driver;

    @Given("I open browser")
    public void iOpenBrowser() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @And("I open Login page")
    public void iOpenLoginPage() {
        driver.get("https://qa.koel.app/");
    }

    @When("I enter email {string}")
    public void iEnterEmail(String email) {
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(email);
    }

    @And("I enter password {string}")
    public void iEnterPassword(String password) {
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(password);
    }

    @And("I submit")
    public void iSubmit() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Then("I am logged in")
    public void iAmLoggedIn() {
        Assert.assertTrue(driver.getCurrentUrl().contains("qa.koel.app"));
    }
    @When("I open All Songs")
    public void iOpenAllSongs() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.songs")))
                .click();
    }

    @And("I play the first song")
    public void iPlayTheFirstSong() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement firstSong = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tr.song-item"))
        );

        new Actions(driver).doubleClick(firstSong).perform();
    }
    @io.cucumber.java.After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}