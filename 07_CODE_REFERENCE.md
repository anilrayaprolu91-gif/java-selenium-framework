# Code Reference & Snippets Guide

## 🎯 Quick Reference for All Classes

---

## 1. BaseTest.java - Driver Management

### Key Methods

#### Initialize Driver Before Test
```java
@BeforeMethod
@Parameters("browser")
public void setUp(String browser) {
    initializeDriver(browser != null ? browser : "chrome");
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().window().maximize();
    driver.navigate().to("https://practicesoftwaretesting.com");
}
```

#### Cleanup After Test
```java
@AfterMethod
public void tearDown() {
    if (driver != null) {
        driver.quit();
    }
}
```

#### Initialize Different Browsers
```java
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
```

---

## 2. HomePage.java - Home Page POM

### Locators Definition
```java
@FindBy(css = "[data-test='search-query']")
private WebElement searchInput;

@FindBy(css = "[data-test='search-submit']")
private WebElement searchButton;

@FindBy(css = "a[data-test^='product-']")
private List<WebElement> productList;
```

### Initialize Page Object
```java
public HomePage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    PageFactory.initElements(driver, this);
}
```

### Search Methods
```java
public void enterSearchQuery(String searchQuery) {
    wait.until(ExpectedConditions.visibilityOf(searchInput));
    searchInput.clear();
    searchInput.sendKeys(searchQuery);
}

public void clickSearchButton() {
    wait.until(ExpectedConditions.elementToBeClickable(searchButton));
    searchButton.click();
}

public void searchProduct(String searchQuery) {
    enterSearchQuery(searchQuery);
    clickSearchButton();
}
```

### Product Selection Methods
```java
public WebElement getProductByIndex(int index) {
    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
        org.openqa.selenium.By.cssSelector("a[data-test^='product-']")));
    if (index >= 0 && index < productList.size()) {
        return productList.get(index);
    }
    throw new IndexOutOfBoundsException("Product index out of bounds");
}

public void clickProductByIndex(int index) {
    WebElement product = getProductByIndex(index);
    wait.until(ExpectedConditions.elementToBeClickable(product));
    product.click();
}

public void clickFirstProduct() {
    clickProductByIndex(0);
}
```

### Product Information Methods
```java
public int getProductCount() {
    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
        org.openqa.selenium.By.cssSelector("a[data-test^='product-']")));
    return productList.size();
}

public String getFirstProductName() {
    return getProductNameByIndex(0);
}

public boolean areProductsDisplayed() {
    try {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
            org.openqa.selenium.By.cssSelector("a[data-test^='product-']")));
        return productList.size() > 0;
    } catch (Exception e) {
        return false;
    }
}
```

---

## 3. ProductPage.java - Product Detail POM

### Locators Definition
```java
@FindBy(css = "[data-test='product-name']")
private WebElement productName;

@FindBy(css = "[data-test='product-description']")
private WebElement productDescription;

@FindBy(css = "[data-test='unit-price']")
private WebElement productPrice;

@FindBy(css = "[data-test='add-to-cart']")
private WebElement addToCartButton;

@FindBy(css = "[data-test='add-to-favorites']")
private WebElement addToFavoritesButton;
```

### Initialize Page Object
```java
public ProductPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    PageFactory.initElements(driver, this);
}
```

### Getter Methods
```java
public String getProductTitle() {
    wait.until(ExpectedConditions.visibilityOf(productName));
    return productName.getText().trim();
}

public String getProductDescription() {
    wait.until(ExpectedConditions.visibilityOf(productDescription));
    return productDescription.getText().trim();
}

public String getProductPrice() {
    wait.until(ExpectedConditions.visibilityOf(productPrice));
    return productPrice.getText().trim();
}

public double getProductPriceAsDouble() {
    String priceText = getProductPrice();
    try {
        return Double.parseDouble(priceText);
    } catch (NumberFormatException e) {
        throw new RuntimeException("Cannot convert price to double", e);
    }
}
```

### Action Methods
```java
public void clickAddToCartButton() {
    wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
    addToCartButton.click();
}

public void clickAddToFavoritesButton() {
    wait.until(ExpectedConditions.elementToBeClickable(addToFavoritesButton));
    addToFavoritesButton.click();
}
```

### Visibility Verification Methods
```java
public boolean isProductPageLoaded() {
    return isProductTitleDisplayed() && isProductPriceDisplayed();
}

public boolean isProductTitleDisplayed() {
    try {
        wait.until(ExpectedConditions.visibilityOf(productName));
        return productName.isDisplayed();
    } catch (Exception e) {
        return false;
    }
}

public boolean isAddToCartButtonVisible() {
    try {
        wait.until(ExpectedConditions.visibilityOf(addToCartButton));
        return true;
    } catch (Exception e) {
        return false;
    }
}
```

### Assertion Friendly Methods
```java
public boolean verifyProductTitleContains(String expectedText) {
    String actualTitle = getProductTitle();
    return actualTitle.contains(expectedText);
}

public boolean verifyProductTitleEquals(String expectedTitle) {
    String actualTitle = getProductTitle();
    return actualTitle.equals(expectedTitle);
}

public String getProductDetails() {
    return String.format(
        "Product Title: %s\nPrice: $%s\nDescription: %s",
        getProductTitle(),
        getProductPrice(),
        getProductDescription()
    );
}
```

---

## 4. SearchTest.java - Test Cases

### Test Case Structure Template
```java
@Test(description = "Test description")
public void testMethodName() {
    // Arrange - Setup
    HomePage homePage = new HomePage(driver);
    
    // Act - Execute
    homePage.searchProduct("Hammer");
    homePage.clickFirstProduct();
    
    // Assert - Verify
    ProductPage productPage = new ProductPage(driver);
    Assert.assertTrue(productPage.getProductTitle().contains("Hammer"));
}
```

### Complete Test Case 1
```java
@Test(description = "Search for Hammer product and verify product title")
public void testSearchHammerAndVerifyProductTitle() {
    // Verify home page is ready
    HomePage homePage = new HomePage(driver);
    Assert.assertTrue(homePage.isSearchInputVisible());
    
    // Search for product
    homePage.searchProduct("Hammer");
    
    // Verify results
    Assert.assertTrue(homePage.areProductsDisplayed());
    Assert.assertTrue(homePage.getProductCount() > 0);
    
    // Get first product info
    String firstProductName = homePage.getFirstProductName();
    System.out.println("First product: " + firstProductName);
    
    // Click and verify product page
    homePage.clickFirstProduct();
    ProductPage productPage = new ProductPage(driver);
    
    Assert.assertTrue(productPage.isProductPageLoaded());
    Assert.assertTrue(productPage.verifyProductTitleContains("Hammer"));
    Assert.assertTrue(productPage.isProductPriceDisplayed());
    Assert.assertTrue(productPage.isProductDescriptionDisplayed());
    
    // Verify price
    double price = productPage.getProductPriceAsDouble();
    Assert.assertTrue(price > 0);
    
    System.out.println(productPage.getProductDetails());
}
```

### Common Assertions
```java
// Simple assertions
Assert.assertTrue(condition);          // Assert condition is true
Assert.assertFalse(condition);         // Assert condition is false
Assert.assertNull(object);             // Assert object is null
Assert.assertNotNull(object);          // Assert object is not null

// String assertions
Assert.assertEquals(actual, expected); // Assert strings equal
Assert.assertTrue(str.contains(text)); // Assert string contains text

// Numeric assertions
Assert.assertTrue(price > 0);          // Assert price positive
Assert.assertTrue(count >= 3);         // Assert count at least 3

// With custom messages
Assert.assertTrue(condition, "Custom message if assertion fails");
```

---

## 5. Common Patterns

### Pattern 1: Page Navigation & Verification
```java
HomePage home = new HomePage(driver);
home.searchProduct("Hammer");
homePage.clickFirstProduct();

ProductPage product = new ProductPage(driver);
Assert.assertTrue(product.isProductPageLoaded());
```

### Pattern 2: Element Interaction
```java
// Find element
WebElement element = wait.until(
    ExpectedConditions.visibilityOf(searchInput)
);

// Interact with element
element.clear();
element.sendKeys("text");
```

### Pattern 3: Wait for Condition
```java
// Wait for element to be clickable
wait.until(ExpectedConditions.elementToBeClickable(button));
button.click();

// Wait for visibility
wait.until(ExpectedConditions.visibilityOf(element));
String text = element.getText();

// Wait for presence of elements
wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
```

### Pattern 4: List Operations
```java
// Get all products
List<WebElement> products = driver.findElements(By.css("a[data-test^='product-']"));

// Click first product
products.get(0).click();

// Get product count
int count = products.size();

// Iterate through products
for (WebElement product : products) {
    String name = product.getText();
    System.out.println(name);
}
```

### Pattern 5: Try-Catch for Robustness
```java
public boolean isElementVisible(WebElement element) {
    try {
        wait.until(ExpectedConditions.visibilityOf(element));
        return true;
    } catch (Exception e) {
        return false;
    }
}
```

---

## 6. Maven Commands Reference

```bash
# Install and compile
mvn clean install
mvn clean compile

# Run tests
mvn test
mvn test -Dtest=SearchTest
mvn test -Dtest=SearchTest#testMethod
mvn test -DsuiteXmlFile=testng.xml

# Skip tests during build
mvn clean package -DskipTests

# Build without running
mvn clean compile

# View help
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-surefire-plugin
```

---

## 7. TestNG Annotations Reference

```java
@BeforeClass        // Before all tests in class
@BeforeMethod       // Before each test method
@Test               // Mark as test method
@Test(enabled=false) // Skip this test
@Test(groups="fast") // Group tests
@AfterMethod        // After each test method
@AfterClass         // After all tests in class

@Parameters({"param1"}) // Use parameters from testng.xml
@DataProvider(name="data") // Provide test data
```

---

## 8. Expected Conditions Reference

```java
// Element visibility
ExpectedConditions.visibilityOf(element)
ExpectedConditions.visibilityOfAllElements(elements)
ExpectedConditions.presenceOfElementLocated(locator)
ExpectedConditions.presenceOfAllElementsLocatedBy(locator)

// Element interaction
ExpectedConditions.elementToBeClickable(element)
ExpectedConditions.elementToBeSelected(element)

// Text conditions
ExpectedConditions.textToBePresentInElement(element, text)
ExpectedConditions.textToBePresentInElementValue(element, text)

// Alert handling
ExpectedConditions.alertIsPresent()
```

---

## 9. Locator Examples

```java
// CSS Selectors (Preferred for data-test)
By.css("[data-test='search-query']")
By.css("[data-test^='product-']")
By.css("[data-test*='cart']")

// ID
By.id("elementId")

// Name
By.name("elementName")

// Tag
By.tagName("button")

// Class
By.className("className")

// XPath (Last resort)
By.xpath("//button[@type='submit']")
By.xpath("//*[text()='Search']")
```

---

## 10. Project Setup Reference

### POM.xml Key Sections
```xml
<dependencies>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.15.0</version>
    </dependency>
    
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.8.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.0.0</version>
        <configuration>
            <suiteXmlFiles>
                <suiteXmlFile>testng.xml</suiteXmlFile>
            </suiteXmlFiles>
        </configuration>
    </plugin>
</plugins>
```

### TestNG.xml Key Sections
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Suite" parallel="false" verbose="2">
    <test name="Tests">
        <classes>
            <class name="com.automation.tests.SearchTest">
                <methods>
                    <include name="testSearchHammerAndVerifyProductTitle"/>
                </methods>
            </class>
        </classes>
    </test>
</suite>
```

---

## 11. Useful Snippets

### Import Statements
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
```

### Print Statements for Debugging
```java
System.out.println("Test Step: Searching for Hammer");
System.out.println("Product found: " + productName);
System.out.println("Price: $" + price);
```

### Wait Patterns
```java
// Implicit wait - set once
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

// Explicit wait - use when needed
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.visibilityOf(element));
```

---

## Quick Copy-Paste Templates

### New Test Method Template
```java
@Test(description = "What this test does")
public void testFeatureName() {
    // Arrange
    HomePage homePage = new HomePage(driver);
    
    // Act
    homePage.someAction();
    
    // Assert
    Assert.assertTrue(someCondition);
}
```

### New Page Object Method Template
```java
public void methodName(String parameter) {
    wait.until(ExpectedConditions.elementToBeClickable(element));
    element.sendKeys(parameter);
}
```

### New Locator Template
```java
@FindBy(css = "[data-test='element-id']")
private WebElement elementName;
```

---

**Happy Coding!** 🎉

For complete code, refer to the actual source files in the project.

