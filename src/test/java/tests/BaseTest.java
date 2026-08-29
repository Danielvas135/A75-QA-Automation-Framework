package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class BaseTest {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private final String LT_USERNAME = "danielvas135";
    private final String LT_ACCESS_KEY = "LT_wrMi8ffiNwlALbgmbqE7uTsfYyOvTwkD5UMvV6PBQAT3ifF";

    @Parameters("Url")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("https://qa.koel.app/") String baseUrl) throws MalformedURLException {

        // Read browser from command line: -Dbrowser=cloud
        String browser = System.getProperty("browser", "cloud");

        driver.set(pickBrowser(browser));
        getDriver().manage().window().maximize();
        getDriver().get(baseUrl);
    }

    protected WebDriver getDriver() {
        return driver.get();
    }

    public WebDriver pickBrowser(String browser) throws MalformedURLException {
        String gridURL = "http://localhost:4444"; // change if your Grid is on another machine

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--remote-allow-origins=*");
                return new EdgeDriver(edgeOptions);

            case "grid-firefox":
                return new RemoteWebDriver(new URL(gridURL), new FirefoxOptions());

            case "grid-chrome":
                return new RemoteWebDriver(new URL(gridURL), new ChromeOptions());

            case "grid-edge":
                return new RemoteWebDriver(new URL(gridURL), new EdgeOptions());

            case "cloud":
                return cloudBrowserSetup();

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                return new ChromeDriver(chromeOptions);
        }
    }

    public WebDriver cloudBrowserSetup() throws MalformedURLException {
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("dev");

        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", LT_USERNAME);
        ltOptions.put("accessKey", LT_ACCESS_KEY);
        ltOptions.put("project", "A75 Homework 25");
        ltOptions.put("build", "Parallel Testing");
        ltOptions.put("name", this.getClass().getSimpleName());
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("w3c", true);

        browserOptions.setCapability("LT:Options", ltOptions);

        String hubURL = "https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + "@hub.lambdatest.com/wd/hub";
        return new RemoteWebDriver(new URL(hubURL), browserOptions);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            if (getDriver() != null) {
                getDriver().quit();
            }
        } catch (Exception e) {
            System.out.println("Error quitting driver: " + e.getMessage());
        } finally {
            driver.remove();
        }
    }
}