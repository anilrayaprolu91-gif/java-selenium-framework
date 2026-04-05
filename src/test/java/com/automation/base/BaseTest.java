package com.automation.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.time.Duration;

/**
 * Base Test class that handles WebDriver initialization and teardown
 * Provides a common setup and teardown mechanism for all test classes
 */
public class BaseTest {

    protected WebDriver driver;
    private static final int DEFAULT_TIMEOUT = 10;
    private static final String BASE_URL = "https://practicesoftwaretesting.com";

    /**
     * Setup method to initialize the WebDriver before each test
     * Uses ChromeDriver by default
     */
    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        initializeDriver(browser != null ? browser : "chrome");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIMEOUT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT));
        driver.manage().window().maximize();
        driver.navigate().to(BASE_URL);
    }

    /**
     * Initialize WebDriver based on browser type
     *
     * @param browser Browser type (chrome, firefox, edge)
     */
    private void initializeDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
    }

    /**
     * Teardown method to close the WebDriver after each test
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Get the WebDriver instance
     *
     * @return WebDriver instance
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Get the base URL
     *
     * @return Base URL
     */
    public static String getBaseURL() {
        return BASE_URL;
    }
}

