# Practice Software Testing - Selenium Page Object Model Framework

## Project Overview

This is a comprehensive Java-based test automation framework built with **Selenium 4** and **TestNG**, implementing the **Page Object Model (POM)** design pattern for testing the **Practice Software Testing** website (https://practicesoftwaretesting.com).

The framework demonstrates professional automation engineering practices including:
- Page Object Model design pattern with PageFactory
- Stable locators using `data-test` attributes
- BaseTest class for driver initialization
- Comprehensive test cases with TestNG
- WebDriver Manager for automatic driver management
- Proper wait strategies and error handling

## Project Structure

```
SauceLabsSeleniumMcpTest/
├── pom.xml                                    # Maven configuration with dependencies
├── testng.xml                                 # TestNG test suite configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/
│   │   │       └── Main.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/automation/
│               ├── base/
│               │   └── BaseTest.java           # Base test class with driver initialization
│               ├── pages/
│               │   ├── HomePage.java           # Home page POM
│               │   └── ProductPage.java        # Product detail page POM
│               └── tests/
│                   └── SearchTest.java         # Test cases
└── README.md                                  # This file
```

## Dependencies

### Core Dependencies
- **Selenium Java 4.15.0** - Web browser automation
- **TestNG 7.8.1** - Test framework
- **WebDriver Manager 5.6.3** - Automatic driver management
- **SLF4J 2.0.9** - Logging framework

### Build Tools
- **Java 18** - Compilation target
- **Maven 3.6+** - Build automation

## Class Descriptions

### 1. BaseTest.java (`com.automation.base.BaseTest`)

**Purpose**: Base class that handles WebDriver initialization and teardown for all test classes.

**Key Features**:
- Initializes WebDriver before each test (`@BeforeMethod`)
- Supports multiple browsers (Chrome, Firefox, Edge)
- Sets implicit waits and page load timeouts
- Maximizes browser window
- Navigates to the base URL
- Cleans up resources after each test (`@AfterMethod`)

**Methods**:
- `setUp(String browser)` - Initialize driver
- `initializeDriver(String browser)` - Setup specific browser driver
- `tearDown()` - Close driver and cleanup
- `getDriver()` - Get WebDriver instance
- `getBaseURL()` - Get the base URL

**Usage**:
```java
public class MyTest extends BaseTest {
    @Test
    public void myTestCase() {
        // driver is automatically initialized
        driver.findElement(By.id("myElement")).click();
        // driver is automatically closed after test
    }
}
```

### 2. HomePage.java (`com.automation.pages.HomePage`)

**Purpose**: Page Object Model for the Home page with search functionality.

**Locators Used**:
- `[data-test='search-query']` - Search input field
- `[data-test='search-submit']` - Search button
- `[data-test='search-reset']` - Clear search button
- `a[data-test^='product-']` - Product list items
- `[data-test='language-select']` - Language selector
- `[data-test='chat-toggle']` - Chat toggle button

**Key Methods**:
- `enterSearchQuery(String searchQuery)` - Type search term
- `clickSearchButton()` - Submit search
- `searchProduct(String searchQuery)` - Combined search method
- `getProductCount()` - Get number of displayed products
- `getProductByIndex(int index)` - Get product at specific index
- `clickProductByIndex(int index)` - Click product by index
- `getFirstProduct()` / `clickFirstProduct()` - Get/click first product
- `getFirstProductName()` - Get first product name
- `areProductsDisplayed()` - Check if products are shown
- `getSearchInputValue()` - Get current search text

**Usage**:
```java
HomePage homePage = new HomePage(driver);
homePage.searchProduct("Hammer");
homePage.clickFirstProduct();
```

### 3. ProductPage.java (`com.automation.pages.ProductPage`)

**Purpose**: Page Object Model for the Product Detail page.

**Locators Used**:
- `[data-test='product-name']` - Product title/name
- `[data-test='product-description']` - Product description
- `[data-test='unit-price']` - Product price
- `[data-test='add-to-cart']` - Add to cart button
- `[data-test='add-to-favorites']` - Add to favorites button

**Key Methods**:
- `getProductTitle()` - Get product name
- `getProductDescription()` - Get product description
- `getProductPrice()` / `getProductPriceAsDouble()` - Get price
- `clickAddToCartButton()` - Add to cart
- `clickAddToFavoritesButton()` - Add to favorites
- `isProductPageLoaded()` - Verify page is loaded
- `verifyProductTitleContains(String text)` - Partial title match
- `verifyProductTitleEquals(String text)` - Exact title match
- `getProductDetails()` - Get formatted product info

**Usage**:
```java
ProductPage productPage = new ProductPage(driver);
String title = productPage.getProductTitle();
Assert.assertTrue(title.contains("Hammer"));
double price = productPage.getProductPriceAsDouble();
```

### 4. SearchTest.java (`com.automation.tests.SearchTest`)

**Purpose**: Test cases that validate search and product detail functionality.

**Test Cases**:

#### Test 1: `testSearchHammerAndVerifyProductTitle()`
- **Description**: Search for "Hammer" and verify the first product title
- **Steps**:
  1. Verify search input is visible
  2. Search for "Hammer"
  3. Verify search results are displayed
  4. Click the first product
  5. Assert that product title contains "Hammer"
  6. Verify price and description are displayed
  7. Validate price is a positive number

#### Test 2: `testAddToCartButtonIsVisible()`
- **Description**: Verify Add to Cart button exists on product page
- **Steps**:
  1. Search for "Hammer"
  2. Click first product
  3. Verify Add to Cart button is visible
  4. Verify Add to Favorites button is visible

#### Test 3: `testVerifyProductPrice()`
- **Description**: Verify product price is displayed and valid
- **Steps**:
  1. Search for "Hammer"
  2. Click first product
  3. Verify price text is not empty
  4. Verify price is a valid positive number

## Installation & Setup

### Prerequisites
- Java 18 or higher installed
- Maven 3.6 or higher installed
- ChromeDriver (automatic via WebDriver Manager)

### Steps

1. **Clone or Extract the Project**
```bash
cd SauceLabsSeleniumMcpTest
```

2. **Install Dependencies**
```bash
mvn clean install
```

3. **Run All Tests**
```bash
mvn test
```

4. **Run Specific Test Class**
```bash
mvn test -Dtest=SearchTest
```

5. **Run Specific Test Method**
```bash
mvn test -Dtest=SearchTest#testSearchHammerAndVerifyProductTitle
```

6. **Run with TestNG XML**
```bash
mvn test -DsuiteXmlFile=testng.xml
```

## Locator Strategy

The framework uses **stable locators** with preference for `data-test` attributes as they are:
- Less likely to change with UI redesigns
- Explicitly added for testing purposes
- Resistant to styling and layout changes
- More reliable than CSS/XPath selectors

### Example Locators from the Website

**Home Page**:
```
Search Input: [data-test='search-query']
Search Button: [data-test='search-submit']
Products: a[data-test^='product-']
```

**Product Page**:
```
Title: [data-test='product-name']
Price: [data-test='unit-price']
Add to Cart: [data-test='add-to-cart']
```

## Wait Strategies

The framework implements **WebDriverWait** with explicit waits:
- **Implicit Wait**: 10 seconds (global default timeout)
- **Page Load Timeout**: 10 seconds
- **Explicit Waits**: Used in page objects for element visibility and clickability

### Example:
```java
wait.until(ExpectedConditions.visibilityOf(element));
wait.until(ExpectedConditions.elementToBeClickable(button));
```

## Best Practices Implemented

1. **Page Object Model**: Each page is represented as a separate class
2. **PageFactory**: Using `@FindBy` annotations for element location
3. **Stable Locators**: Prioritizing `data-test` attributes
4. **DRY Principle**: Reusable methods in page objects
5. **Single Responsibility**: Each class has one clear purpose
6. **Proper Waits**: Explicit waits instead of Thread.sleep()
7. **Error Handling**: Try-catch blocks for robust error handling
8. **Documentation**: Comprehensive JavaDoc comments
9. **Test Independence**: Each test can run independently
10. **Clear Assertions**: Descriptive assertion messages

## TestNG Features Used

- **@BeforeMethod**: Initialize driver before each test
- **@AfterMethod**: Cleanup after each test
- **@Test**: Define test methods
- **@Parameters**: Support for parameterized tests
- **Assertions**: TestNG Assert for validation
- **Test Reporting**: Built-in test reports

## Running Tests from IDE

### In IntelliJ IDEA
1. Right-click on `SearchTest.java` → **Run 'SearchTest'**
2. Or right-click on specific test method → **Run** or **Debug**
3. View results in the Run window

### In Eclipse
1. Right-click on `SearchTest.java` → **Run As** → **TestNG Test**
2. Or right-click on specific test method → **Run As** → **TestNG Test**

### In VS Code
1. Install TestNG for Java extension
2. Click on **Run Test** above the test method

## Test Reports

After running tests, TestNG generates reports:
- **HTML Report**: `target/surefire-reports/index.html`
- **XML Report**: `target/surefire-reports/testng-results.xml`

## Configuration

### Browser Configuration
To run tests on different browsers, modify `BaseTest.setUp()` or use TestNG parameters:

```xml
<!-- In testng.xml -->
<parameter name="browser" value="firefox"/>
```

### Timeout Configuration
Modify `DEFAULT_TIMEOUT` in `BaseTest.java`:
```java
private static final int DEFAULT_TIMEOUT = 10; // in seconds
```

### Base URL Configuration
Modify `BASE_URL` in `BaseTest.java`:
```java
private static final String BASE_URL = "https://practicesoftwaretesting.com";
```

## Troubleshooting

### Issue: Driver executable does not exist
**Solution**: WebDriver Manager will automatically download the correct driver. Ensure internet connectivity.

### Issue: Elements not found
**Solution**: 
- Verify the website structure hasn't changed
- Update locators if needed
- Check implicit/explicit waits
- Use browser developer tools to inspect elements

### Issue: Tests timeout
**Solution**: 
- Increase `DEFAULT_TIMEOUT` in `BaseTest.java`
- Check internet connectivity
- Verify website is accessible

## Future Enhancements

1. **Parallel Execution**: Configure TestNG for parallel test execution
2. **Screenshot on Failure**: Add automatic screenshots on test failure
3. **Logging**: Integrate log4j for detailed logging
4. **API Testing**: Add REST API test cases
5. **Data-Driven Testing**: Implement data providers for parameterized tests
6. **CI/CD Integration**: Setup GitHub Actions or Jenkins pipeline
7. **Custom Reports**: Implement Allure or ExtentReports for better reporting
8. **Performance Testing**: Add Lighthouse performance checks

## Contact & Support

For questions or improvements to this framework, please reach out to the automation team.

---

**Framework Version**: 1.0  
**Last Updated**: April 2026  
**Java Version**: 18+  
**Selenium Version**: 4.15.0  
**TestNG Version**: 7.8.1

