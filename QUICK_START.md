# Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Prerequisites
- ✅ Java 18+ installed
- ✅ Maven 3.6+ installed
- ✅ Chrome browser (or Firefox/Edge)

---

## ⚡ Quick Setup

### Step 1: Open Project
```bash
cd D:\TestAutomationKnowledge\JavaSeleniumMcp\SauceLabsSeleniumMcpTest
```

### Step 2: Install Dependencies
```bash
mvn clean install
```

### Step 3: Run Tests
```bash
mvn test
```

**That's it!** ✅ Tests will run automatically.

---

## 📊 What Just Happened?

The framework just:
1. ✅ Downloaded Selenium 4 and TestNG
2. ✅ Launched Chrome browser automatically
3. ✅ Navigated to practicesoftwaretesting.com
4. ✅ Searched for "Hammer"
5. ✅ Opened the first product
6. ✅ Verified the product title contains "Hammer"
7. ✅ Checked product price and description
8. ✅ Closed the browser
9. ✅ Generated test reports

---

## 🎯 Project Structure (What You Need to Know)

```
src/test/java/com/automation/
├── base/
│   └── BaseTest.java ..................... Base class (driver setup)
├── pages/
│   ├── HomePage.java ..................... Home page interactions
│   └── ProductPage.java .................. Product detail interactions
└── tests/
    └── SearchTest.java ................... Test cases (runs tests)
```

**That's all you need to understand!**

---

## 🧪 The 3 Test Cases

### Test 1: Search & Verify Title
```
What it does: Searches for "Hammer", opens first product, checks title
Command: mvn test -Dtest=SearchTest#testSearchHammerAndVerifyProductTitle
```

### Test 2: Check Cart Button
```
What it does: Verifies "Add to Cart" button exists on product page
Command: mvn test -Dtest=SearchTest#testAddToCartButtonIsVisible
```

### Test 3: Verify Price
```
What it does: Checks product price is valid
Command: mvn test -Dtest=SearchTest#testVerifyProductPrice
```

---

## 🔍 How It Works (Simple Explanation)

### Traditional Selenium Test (❌ Not Recommended)
```java
// Bad: Locators scattered in test
driver.findElement(By.css("[data-test='search-query']")).sendKeys("Hammer");
driver.findElement(By.css("[data-test='search-submit']")).click();
List<WebElement> products = driver.findElements(By.css("a[data-test^='product-']"));
products.get(0).click();
```

### This Framework (✅ Clean & Maintainable)
```java
// Good: Page objects handle all details
HomePage home = new HomePage(driver);
home.searchProduct("Hammer");
home.clickFirstProduct();

ProductPage product = new ProductPage(driver);
Assert.assertTrue(product.getProductTitle().contains("Hammer"));
```

**See the difference?** Page objects = Cleaner code!

---

## 🛠️ Common Commands

### Run ALL tests
```bash
mvn test
```

### Run ONE test class
```bash
mvn test -Dtest=SearchTest
```

### Run ONE test method
```bash
mvn test -Dtest=SearchTest#testSearchHammerAndVerifyProductTitle
```

### View test reports
```bash
# After running tests, open:
target/surefire-reports/index.html
```

### Compile only (no tests)
```bash
mvn clean compile
```

### Skip tests
```bash
mvn clean package -DskipTests
```

---

## 🌐 How to Use from Your IDE

### IntelliJ IDEA
1. Open the project
2. Right-click on `SearchTest.java`
3. Click "Run SearchTest"
4. View results in Run window

### Eclipse
1. Open the project
2. Right-click on `SearchTest.java`
3. Select "Run As" → "TestNG Test"
4. View results in JUnit window

### VS Code
1. Install "TestNG for Java" extension
2. Click "Run Test" above any test method
3. View results in terminal

---

## 📖 Want to Understand Better?

| File | Purpose | Read When |
|------|---------|-----------|
| `BaseTest.java` | Driver setup | Want to know how tests start |
| `HomePage.java` | Search page | Want to add search features |
| `ProductPage.java` | Product page | Want to add product features |
| `SearchTest.java` | Tests | Want to write new tests |
| `README.md` | Full docs | Need complete documentation |
| `IMPLEMENTATION_SUMMARY.md` | Details | Need technical details |

---

## 🎓 Learning Path

### Beginner
- [ ] Run the tests: `mvn test`
- [ ] Read `README.md`
- [ ] Look at `SearchTest.java` test methods

### Intermediate
- [ ] Understand `HomePage.java` methods
- [ ] Understand `ProductPage.java` methods
- [ ] Try modifying an assertion

### Advanced
- [ ] Write a new test method
- [ ] Add a new page object
- [ ] Modify locators for new features

---

## 🐛 Troubleshooting

### Issue: Tests don't run
```
Error: Maven not found
Solution: Install Maven from https://maven.apache.org
```

### Issue: Browser doesn't open
```
Error: WebDriver Manager can't download driver
Solution: Check internet connection and try again
```

### Issue: Elements not found
```
Error: "No such element" exception
Solution: Website layout may have changed - update locators in page objects
```

### Issue: Tests are slow
```
Reason: WebDriver Manager downloading driver for first time
Solution: Just wait, or increase timeout: DEFAULT_TIMEOUT in BaseTest.java
```

---

## 📈 Test Results

After running tests, you'll see:
```
======================================================================
Tests run: 3, Failures: 0, Skipped: 0, Time elapsed: 45 sec
======================================================================

✅ testSearchHammerAndVerifyProductTitle ................ PASSED
✅ testAddToCartButtonIsVisible ......................... PASSED
✅ testVerifyProductPrice ............................... PASSED
```

---

## 🎨 Customization Examples

### Change Search Product
In `SearchTest.java`, change:
```java
homePage.searchProduct("Hammer");  // Change to "Drill", "Saw", etc.
```

### Change Browser
In `BaseTest.java`, modify `setUp()`:
```java
initializeDriver("firefox");  // Use Firefox instead of Chrome
```

### Add Timeout
In `BaseTest.java`:
```java
private static final int DEFAULT_TIMEOUT = 20;  // Increase to 20 seconds
```

---

## 📚 Next Steps

### Want to Add a New Test?
1. Open `SearchTest.java`
2. Add a new `@Test` method
3. Use `HomePage` and `ProductPage` objects
4. Run with `mvn test`

### Want to Test a Different Page?
1. Create new file: `src/test/java/com/automation/pages/NewPage.java`
2. Add `@FindBy` locators
3. Create methods
4. Use in tests

### Want to Run on Different Browser?
1. Update `BaseTest.setUp()` parameter
2. Or modify `testng.xml`
3. Or pass parameter: `mvn test -Dbrowser=firefox`

---

## ✅ Checklist

- [ ] Java 18+ installed
- [ ] Maven installed
- [ ] Project downloaded
- [ ] First test run: `mvn test`
- [ ] Tests passed ✅
- [ ] Read README.md
- [ ] Understand test structure
- [ ] Ready to customize!

---

## 💬 Key Concepts

**Page Object Model (POM)**
- Each page = one class (HomePage, ProductPage)
- All locators in that class
- All actions/methods in that class
- Tests just call methods

**WebDriver Management**
- BaseTest starts/stops driver
- Your tests extend BaseTest
- Driver ready before each test
- Cleanup automatic after test

**Assertions**
- TestNG's Assert class
- Example: `Assert.assertTrue(condition)`
- Check README.md for all assertions

**Waits**
- Explicit: `wait.until(ExpectedConditions.visibilityOf(element))`
- Implicit: 10 seconds default in BaseTest
- Better than `Thread.sleep()`

---

## 🎯 You're Ready!

You now have a **professional, production-grade** automation framework!

- ✅ 3 working test cases
- ✅ Clean Page Object Model
- ✅ Proper driver management
- ✅ Best practices throughout
- ✅ Fully documented
- ✅ Easy to extend

**Start testing!** 🚀

```bash
mvn test
```

---

**Happy Testing! 🎉**

For more details, see:
- `README.md` - Full documentation
- `IMPLEMENTATION_SUMMARY.md` - Technical details
- Source code - Well commented!

