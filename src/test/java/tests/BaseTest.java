package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class BaseTest {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private final String LT_USERNAME = System.getenv("LT_USERNAME");
    private final String LT_ACCESS_KEY = System.getenv("LT_ACCESS_KEY");

    @Parameters("Url")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("https://qa.koel.app/") String baseUrl) throws MalformedURLException {
        // Default is local Chrome. Override with -Dbrowser=firefox|grid-chrome|cloud
        String browser = System.getProperty("browser", "chrome");

        driver.set(pickBrowser(browser));
        getDriver().manage().window().maximize();
        getDriver().get(baseUrl);
    }

    protected WebDriver getDriver() {
        return driver.get();
    }

    public WebDriver pickBrowser(String browser) throws MalformedURLException {
        String gridUrl = "http://localhost:4444";

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
                return new RemoteWebDriver(new URL(gridUrl), new FirefoxOptions());

            case "grid-chrome":
                return new RemoteWebDriver(new URL(gridUrl), new ChromeOptions());

            case "grid-edge":
                return new RemoteWebDriver(new URL(gridUrl), new EdgeOptions());

            case "cloud":
                return cloudBrowserSetup();

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-notifications");
                return new ChromeDriver(chromeOptions);
        }
    }

    public WebDriver cloudBrowserSetup() throws MalformedURLException {
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("latest");

        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", LT_USERNAME);
        ltOptions.put("accessKey", LT_ACCESS_KEY);
        ltOptions.put("project", "Koel Automation");
        ltOptions.put("build", "Regression");
        ltOptions.put("name", this.getClass().getSimpleName());
        ltOptions.put("w3c", true);
        browserOptions.setCapability("LT:Options", ltOptions);

        String hubUrl = "https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + "@hub.lambdatest.com/wd/hub";
        return new RemoteWebDriver(new URL(hubUrl), browserOptions);
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