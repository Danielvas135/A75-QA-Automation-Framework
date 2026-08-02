package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    protected WebDriver driver;

    @Parameters("Url")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("https://qa.koel.app/") String baseUrl) throws MalformedURLException {

        // Browser Factory - runs Chrome through local Selenium Grid
        driver = createChromeOnGrid();

        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    /**
     * Browser Factory method
     * Creates Chrome browser that runs on the local Selenium Grid (Standalone)
     */
    private WebDriver createChromeOnGrid() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Connects to the Grid running on localhost:4444
        return new RemoteWebDriver(new URL("http://localhost:4444"), options);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}