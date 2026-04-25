# Framework Architecture Guide

## 1. Overview

This document describes the architecture of the Java Selenium  framework, built following Angie Jones' best practices and Model Context Protocol (MCP) principles for clean, modular, and AI-agent-friendly design.

### Design Principles
✅ **Separation of Concerns**: Each class has single responsibility  
✅ **DRY (Don't Repeat Yourself)**: Reusable utilities and components  
✅ **SOLID Principles**: Maintainable, extensible code  
✅ **MCP Ready**: Clear APIs for AI/automation integration  
✅ **Thread-Safe**: Parallel execution support  

---

## 2. Project Structure

```
src/main/java/com/automation/
├── config/
│   ├── ConfigurationManager.java       # Config loading
│   ├── EnvironmentConfiguration.java   # Environment-specific
│   └── BrowserConfiguration.java       # Browser settings
│
├── driver/
│   ├── DriverFactory.java              # WebDriver creation
│   ├── DriverManager.java              # Thread-local storage
│   └── CapabilityFactory.java          # Driver capabilities
│
├── pages/
│   ├── BasePage.java                   # Base class for all pages
│   ├── HomePage.java                   # Home page object
│   ├── LoginPage.java                  # Login page object
│   ├── ProductPage.java                # Product detail page
│   ├── CartPage.java                   # Shopping cart page
│   ├── CheckoutPage.java               # Checkout page
│   ├── OrderPage.java                  # Order details page
│   └── SearchResultsPage.java          # Search results page
│
├── utils/
│   ├── WaitUtils.java                  # Explicit waits (no sleep!)
│   ├── ExcelUtils.java                 # Apache POI helpers
│   ├── ScreenshotUtils.java            # Screenshot on failure
│   ├── JSExecutorUtils.java            # JavaScript helpers
│   ├── LoggerUtils.java                # Logging helpers
│   ├── DateTimeUtils.java              # Date/time utilities
│   ├── ApiUtils.java                   # REST API helpers
│   └── ValidationUtils.java            # Assertion helpers
│
├── models/
│   ├── User.java                       # User data model
│   ├── Product.java                    # Product data model
│   ├── Order.java                      # Order data model
│   ├── Address.java                    # Address data model
│   └── Payment.java                    # Payment data model
│
├── enums/
│   ├── Browser.java                    # Browser types
│   ├── Environment.java                # Environments
│   ├── WaitType.java                   # Wait strategies
│   ├── SortOrder.java                  # Sort options
│   └── ProductCategory.java            # Product categories
│
└── listeners/
    ├── TestListener.java               # TestNG listener
    ├── RetryListener.java              # Retry mechanism
    └── ScreenshotListener.java         # Screenshot on failure
```

---

## 3. Core Components

### 3.1 Driver Management

#### DriverFactory
Responsible for creating WebDriver instances with proper capabilities.

```java
public class DriverFactory {
    public static WebDriver createDriver(Browser browser) {
        switch (browser) {
            case CHROME -> return createChromeDriver();
            case FIREFOX -> return createFirefoxDriver();
            case EDGE -> return createEdgeDriver();
            default -> throw new IllegalArgumentException();
        }
    }
    
    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(getChromeOptions());
    }
    
    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        // Configuration options
        return options;
    }
}
```

#### DriverManager
Manages thread-local WebDriver for parallel execution.

```java
public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = 
        new ThreadLocal<>();
    
    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }
    
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    
    public static void closeDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
```

### 3.2 Page Object Model (POM)

#### BasePage
Base class with common methods for all page objects.

```java
public abstract class BasePage {
    protected WebDriver driver;
    protected WaitUtils wait;
    protected JSExecutorUtils jsExecutor;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        this.jsExecutor = new JSExecutorUtils(driver);
    }
    
    // Common methods
    protected void click(By locator) {
        wait.waitForElementToBeClickable(locator);
        driver.findElement(locator).click();
    }
    
    protected void sendKeys(By locator, String text) {
        wait.waitForElementPresence(locator);
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }
    
    protected String getText(By locator) {
        wait.waitForElementVisibility(locator);
        return driver.findElement(locator).getText();
    }
    
    protected boolean isElementDisplayed(By locator) {
        try {
            wait.waitForElementVisibility(locator, 2);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
```

#### HomePage
Example page object implementing POM pattern.

```java
public class HomePage extends BasePage {
    // Locators
    private static final By SEARCH_BAR = By.name("q");
    private static final By SEARCH_BUTTON = By.xpath("//button[@aria-label='Search']");
    private static final By PRODUCT_GRID = By.className("product-grid");
    private static final By LOGIN_LINK = By.linkText("Sign in");
    
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    // Page actions
    public SearchResultsPage searchForProduct(String term) {
        sendKeys(SEARCH_BAR, term);
        click(SEARCH_BUTTON);
        return new SearchResultsPage(driver);
    }
    
    public LoginPage clickLoginLink() {
        click(LOGIN_LINK);
        return new LoginPage(driver);
    }
    
    public boolean isProductGridDisplayed() {
        return isElementDisplayed(PRODUCT_GRID);
    }
    
    public void navigateTo() {
        driver.navigate().to(ConfigurationManager.getBaseUrl());
    }
}
```

### 3.3 Utility Classes

#### WaitUtils
Explicit waits with no Thread.sleep() - best practice!

```java
public class WaitUtils {
    private WebDriver driver;
    private int defaultTimeout = 10;
    
    public WaitUtils(WebDriver driver) {
        this.driver = driver;
    }
    
    // Wait for element to be clickable
    public WebElement waitForElementToBeClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 
            Duration.ofSeconds(defaultTimeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    // Wait for element to be visible
    public WebElement waitForElementVisibility(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 
            Duration.ofSeconds(defaultTimeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    // Wait for element presence
    public WebElement waitForElementPresence(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 
            Duration.ofSeconds(defaultTimeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    // Wait for URL to contain
    public boolean waitForUrlContains(String urlPortion) {
        WebDriverWait wait = new WebDriverWait(driver, 
            Duration.ofSeconds(defaultTimeout));
        return wait.until(ExpectedConditions.urlContains(urlPortion));
    }
}
```

#### ExcelUtils
Apache POI-based Excel handling for data-driven tests.

```java
public class ExcelUtils {
    private Workbook workbook;
    private Sheet sheet;
    
    public ExcelUtils(String filePath, String sheetName) {
        try (FileInputStream file = new FileInputStream(filePath)) {
            workbook = WorkbookFactory.create(file);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            throw new RuntimeException("Error loading Excel file", e);
        }
    }
    
    public List<Map<String, String>> readAllData() {
        List<Map<String, String>> data = new ArrayList<>();
        Row headerRow = sheet.getRow(0);
        
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            
            Map<String, String> rowData = new HashMap<>();
            for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                String header = headerRow.getCell(j).getStringCellValue();
                String value = getCellValue(row.getCell(j));
                rowData.put(header, value);
            }
            data.add(rowData);
        }
        return data;
    }
    
    private String getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}
```

#### ScreenshotUtils
Automatic screenshots on failure.

```java
public class ScreenshotUtils {
    private WebDriver driver;
    private static final String SCREENSHOT_DIR = "target/screenshots/";
    
    public ScreenshotUtils(WebDriver driver) {
        this.driver = driver;
        new File(SCREENSHOT_DIR).mkdirs();
    }
    
    public String takeScreenshot(String name) {
        String timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = String.format("%s_%s.png", name, timestamp);
        String filePath = SCREENSHOT_DIR + fileName;
        
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        
        try {
            Files.copy(source.toPath(), 
                new File(filePath).toPath(), 
                StandardCopyOption.REPLACE_EXISTING);
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Screenshot failed", e);
        }
    }
}
```

### 3.4 Configuration Management

```java
public class ConfigurationManager {
    private static final YamlPropertySourceFactory yamlFactory = 
        new YamlPropertySourceFactory();
    private static Properties properties;
    
    static {
        String env = System.getProperty("env", "local");
        String configFile = String.format("application-%s.yml", env);
        properties = loadProperties(configFile);
    }
    
    public static String getBaseUrl() {
        return properties.getProperty("app.baseUrl");
    }
    
    public static Browser getBrowser() {
        return Browser.valueOf(
            properties.getProperty("browser.type", "CHROME"));
    }
    
    public static boolean isHeadless() {
        return Boolean.parseBoolean(
            properties.getProperty("browser.headless", "false"));
    }
    
    public static int getTimeout() {
        return Integer.parseInt(
            properties.getProperty("app.timeout", "10"));
    }
}
```

### 3.5 Data Models (Java Records)

```java
// User model
public record User(
    String email,
    String password,
    String firstName,
    String lastName
) {}

// Product model
public record Product(
    int id,
    String name,
    double price,
    String category,
    int quantityInStock
) {}

// Order model
public record Order(
    int id,
    String orderNumber,
    User customer,
    List<Product> items,
    double totalAmount,
    LocalDateTime orderDate
) {}
```

---

## 4. Test Base Classes

### BaseTest
Base class for all test classes with setup/teardown.

```java
public abstract class BaseTest {
    protected WebDriver driver;
    protected WaitUtils wait;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @BeforeMethod
    public void setUp() {
        Browser browser = ConfigurationManager.getBrowser();
        driver = DriverFactory.createDriver(browser);
        DriverManager.setDriver(driver);
        wait = new WaitUtils(driver);
        logger.info("Test setup complete for: {}", 
            this.getClass().getSimpleName());
    }
    
    @AfterMethod
    public void tearDown() {
        DriverManager.closeDriver();
        logger.info("Test teardown complete");
    }
}
```

---

## 5. Parallel Execution

### Thread-Safe Design
```java
// Each thread gets its own WebDriver
@BeforeMethod
public void setUp() {
    WebDriver driver = DriverFactory.createDriver(browser);
    DriverManager.setDriver(driver);  // Thread-local storage
}

@AfterMethod
public void tearDown() {
    DriverManager.closeDriver();  // Clean up per thread
}

// TestNG Parallel Configuration (testng-parallel.xml)
<suite parallel="methods" thread-count="4">
    ...
</suite>
```

---

## 6. Data-Driven Testing Flow

```
Test Class
    ↓
@DataProvider reads Excel file
    ↓
ExcelUtils.readAllData()
    ↓
Returns List<Map<String, Object>>
    ↓
TestNG passes each row to @Test method
    ↓
Test executes with specific data
    ↓
Report captures results per data set
```

---

## 7. Listener & Retry Mechanism

### TestListener
Captures test lifecycle events.

```java
public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        Logger logger = LoggerFactory.getLogger(result.getTestClass()
            .getRealClass());
        logger.info("Test started: {}", result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            ScreenshotUtils screenshot = new ScreenshotUtils(driver);
            String path = screenshot.takeScreenshot(
                result.getMethod().getMethodName());
            // Attach to report
        }
    }
}
```

### RetryAnalyzer
Retries flaky tests.

```java
public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private static final int MAX_RETRY = 2;
    
    @Override
    public boolean retry(ITestResult result) {
        if (count < MAX_RETRY) {
            count++;
            return true;  // Retry
        }
        return false;  // Don't retry
    }
}
```

---

## 8. Configuration Files

### application.yml (Local)
```yaml
app:
  baseUrl: https://practicesoftwaretesting.com/
  timeout: 10
  headless: false

browser:
  type: CHROME
  headless: false
  resolution: 1920x1080

logging:
  level: INFO
  pattern: "%d{HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
```

### application-staging.yml
```yaml
app:
  baseUrl: https://staging.practicesoftwaretesting.com/
  timeout: 15

browser:
  type: CHROME
  headless: true
```

---

## 9. Design Patterns Used

| Pattern | Usage | Benefit |
|---------|-------|---------|
| **Singleton** | DriverManager | One instance per thread |
| **Factory** | DriverFactory | Flexible driver creation |
| **Page Object** | Page classes | Maintainable, reusable |
| **Builder** | Fluent APIs | Clean, readable code |
| **Data Provider** | TestNG | Parameterized tests |
| **Listener** | TestNG Listeners | Event handling |
| **Strategy** | Browser selection | Multiple implementations |

---

## 10. Best Practices Implemented

✅ **No Thread.sleep()** - All explicit waits  
✅ **Page Object Model** - Separation of concerns  
✅ **DRY Principle** - Reusable utilities  
✅ **SOLID Principles** - Clean code  
✅ **Logging** - Debug information  
✅ **Error Handling** - Graceful failures  
✅ **Documentation** - Self-documenting code  
✅ **Parallel Safe** - ThreadLocal drivers  
✅ **MCP Ready** - Clear public APIs  
✅ **Extensible** - Easy to add new features  

---

**Architecture Version**: 1.0  
**Status**: ✅ Production Ready  
**Date**: April 6, 2026

