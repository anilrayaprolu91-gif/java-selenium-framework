# Java Selenium MCP Framework - Complete Setup Guide

## 📋 Table of Contents
1. [Quick Start](#quick-start)
2. [Prerequisites](#prerequisites)
3. [Installation](#installation)
4. [Configuration](#configuration)
5. [Running Tests](#running-tests)
6. [Project Structure](#project-structure)
7. [Adding Test Data](#adding-test-data)
8. [Extending Framework](#extending-framework)
9. [Troubleshooting](#troubleshooting)
10. [Best Practices](#best-practices)

---

## Quick Start

### For Impatient (30 seconds)
```bash
# Clone, setup, and run
git clone <repo>
cd selenium-mcp-framework
mvn clean test

# View report
mvn allure:report
open target/site/allure-report/index.html
```

---

## Prerequisites

### System Requirements
- **OS**: Windows 10+, macOS 10.15+, Linux (Ubuntu 20.04+)
- **RAM**: 8GB minimum (for parallel execution)
- **Disk Space**: 2GB for dependencies

### Required Software
```
1. Java Development Kit (JDK) 17 or higher
   - Download: https://adoptium.net/
   - Verify: java -version
   
2. Maven 3.8 or higher
   - Download: https://maven.apache.org/download.cgi
   - Verify: mvn -version
   
3. Git (for version control)
   - Download: https://git-scm.com/
   - Verify: git --version
   
4. Browsers (for testing)
   - Chrome: https://www.google.com/chrome/
   - Firefox: https://www.mozilla.org/firefox/
   - Edge: https://www.microsoft.com/edge/ (optional)
```

### IDE Setup (Optional but Recommended)
```
IntelliJ IDEA Community: https://www.jetbrains.com/idea/
or
Visual Studio Code: https://code.visualstudio.com/
  - Extensions: Test Runner for Java, Maven for Java
```

---

## Installation

### Step 1: Clone Repository
```bash
git clone https://github.com/yourusername/selenium-mcp-framework.git
cd selenium-mcp-framework
```

### Step 2: Verify Java & Maven
```bash
java -version      # Should show Java 17+
mvn -version       # Should show Maven 3.8+
```

### Step 3: Download Dependencies
```bash
# Download all dependencies (first time only)
mvn clean install

# Or just compile
mvn clean compile
```

### Step 4: Verify Installation
```bash
# Run a single test
mvn test -Dtest=LoginTests#validLoginTest

# If test passes, you're ready!
```

---

## Configuration

### Step 1: Environment Setup

Create `src/test/resources/application-local.yml`:
```yaml
app:
  baseUrl: https://practicesoftwaretesting.com/
  timeout: 10
  implicit_wait: 10
  page_load_timeout: 15

browser:
  type: CHROME              # CHROME, FIREFOX, EDGE, SAFARI
  headless: false           # true for headless mode
  window_size: 1920x1080
  download_dir: ./downloads

logging:
  level: INFO               # DEBUG, INFO, WARN, ERROR
  pattern: "%d{HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

test:
  retry_count: 2            # Retry flaky tests
  parallel: true            # Enable parallel execution
  thread_count: 4           # Number of parallel threads

report:
  type: ALLURE              # ALLURE or EXTENT
  screenshot_on_failure: true
  attach_logs: true
```

### Step 2: Test Accounts

Create `src/test/resources/test-accounts.properties`:
```properties
# Admin Account
admin.email=admin@practicesoftwaretesting.com
admin.password=welcome01

# Customer Account
customer.email=customer@practicesoftwaretesting.com
customer.password=welcome01

# Test Data
test.product.id=1
test.product.name=Hammer
test.user.firstname=John
test.user.lastname=Doe
test.user.address=123 Main St
test.user.city=Springfield
test.user.state=IL
test.user.zip=62701
test.user.country=USA
```

### Step 3: System Properties (Optional)

Create `pom.xml` properties section:
```xml
<properties>
    <!-- Environment: local, staging, production -->
    <env>local</env>
    
    <!-- Browser: chrome, firefox, edge, safari -->
    <browser>chrome</browser>
    
    <!-- Headless mode: true, false -->
    <headless>false</headless>
    
    <!-- Parallel: true, false -->
    <parallel>true</parallel>
    
    <!-- Thread count for parallel execution -->
    <thread-count>4</thread-count>
</properties>
```

---

## Running Tests

### Basic Test Execution

#### 1. Run All Tests
```bash
mvn clean test

# Parallel execution (faster)
mvn clean test -Dparallel=true -Dthread-count=4

# Serial execution (safer)
mvn clean test -Dparallel=false
```

#### 2. Run Specific Test Class
```bash
mvn test -Dtest=LoginTests

# Multiple classes
mvn test -Dtest=LoginTests,ProductTests,CheckoutTests
```

#### 3. Run Specific Test Method
```bash
mvn test -Dtest=LoginTests#validLoginTest

# Multiple methods
mvn test -Dtest=LoginTests#validLoginTest+invalidPasswordTest
```

### Advanced Test Execution

#### By Test Type (Tags)
```bash
# Smoke tests only
mvn test -Dgroups="smoke"

# Functional tests
mvn test -Dgroups="functional"

# Exclude negative tests
mvn test -DexcludedGroups="negative"
```

#### By Environment
```bash
# Local environment
mvn test -Denv=local

# Staging environment
mvn test -Denv=staging

# Production (read-only)
mvn test -Denv=production
```

#### By Browser
```bash
# Chrome (default)
mvn test -Dbrowser=chrome

# Firefox
mvn test -Dbrowser=firefox

# Edge
mvn test -Dbrowser=edge

# Headless mode
mvn test -Dheadless=true
```

### Test Suites

#### Predefined Test Suites
```bash
# All smoke tests
mvn test -Dsuite=smoke

# Quick regression
mvn test -Dsuite=quick-regression

# Full regression
mvn test -Dsuite=full-regression

# Critical path
mvn test -Dsuite=critical-path
```

#### Using TestNG XML
```bash
# Custom TestNG configuration
mvn test -DsuiteXmlFile=src/test/resources/testng-custom.xml

# Parallel suite
mvn test -DsuiteXmlFile=src/test/resources/testng-parallel.xml
```

---

## Reporting

### Allure Reports
```bash
# Generate Allure report
mvn allure:report

# View report (opens in browser)
mvn allure:serve

# Generate and open in one command
mvn clean test allure:report && mvn allure:serve
```

### ExtentReports
```bash
# Reports auto-generated in:
target/extent-reports/ExtentReport_*.html

# Open latest report
open target/extent-reports/$(ls -t target/extent-reports | head -1)
```

### Log Files
```bash
# Test execution logs
tail -f target/surefire-reports/
cat target/logs/automation.log
```

---

## Adding Test Data

### Using Excel Files

#### Step 1: Create Excel File
Create `src/test/resources/test-data/login_data.xlsx`:

| Email | Password | ExpectedResult | UserType |
|-------|----------|---|---|
| admin@practicesoftwaretesting.com | welcome01 | Success | Admin |
| customer@practicesoftwaretesting.com | welcome01 | Success | Customer |
| invalid@test.com | wrongpass | Fail | Invalid |

#### Step 2: Create Data Provider
```java
public class LoginDataProvider {
    @DataProvider(name = "validUsers")
    public static Object[][] getValidUsers() {
        ExcelUtils excel = new ExcelUtils(
            "src/test/resources/test-data/login_data.xlsx",
            "ValidUsers"
        );
        List<Map<String, String>> data = excel.readAllData();
        return data.stream()
            .map(row -> new Object[]{row})
            .toArray(Object[][]::new);
    }
}
```

#### Step 3: Use in Test
```java
public class LoginTests extends BaseTest {
    @Test(dataProvider = "validUsers", dataProviderClass = LoginDataProvider.class)
    public void loginWithMultipleUsers(Map<String, String> testData) {
        String email = testData.get("Email");
        String password = testData.get("Password");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
        
        // Assert results
    }
}
```

### Adding Test Accounts

Edit `src/test/resources/test-accounts.properties`:
```properties
# Add new account
newuser.email=newuser@practicesoftwaretesting.com
newuser.password=SecurePassword123
```

### Using Environment Variables
```bash
# Run tests with environment variable
mvn test -Dtest.email="myemail@test.com" -Dtest.password="mypass"
```

---

## Extending Framework

### Adding a New Page Object

#### Step 1: Create Page Class
```java
package com.automation.pages;

import com.automation.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class MyNewPage extends BasePage {
    
    // Page locators
    private static final By ELEMENT_1 = By.id("element-id");
    private static final By ELEMENT_2 = By.xpath("//button[text()='Click']");
    
    // Constructor
    public MyNewPage(WebDriver driver) {
        super(driver);
    }
    
    // Page methods
    public void performAction() {
        click(ELEMENT_1);
        wait.waitForElementVisibility(ELEMENT_2);
    }
    
    public String getResultText() {
        return getText(ELEMENT_2);
    }
}
```

#### Step 2: Use in Tests
```java
public class MyNewTests extends BaseTest {
    @Test
    public void myTest() {
        MyNewPage page = new MyNewPage(driver);
        page.performAction();
        
        String result = page.getResultText();
        Assert.assertTrue(result.contains("Expected"));
    }
}
```

### Adding a New Utility Class

```java
package com.automation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCustomUtils {
    private static final Logger logger = 
        LoggerFactory.getLogger(MyCustomUtils.class);
    
    public static void myUtilityMethod(String param) {
        logger.info("Executing utility method with param: {}", param);
        // Implementation
    }
}
```

### Adding a New Test Class

```java
package com.automation.tests;

import org.testng.annotations.*;
import com.automation.base.BaseTest;

public class MyNewTests extends BaseTest {
    
    @BeforeClass
    public void beforeClass() {
        // Setup specific to this test class
    }
    
    @Test(priority = 1, groups = "smoke")
    public void test1() {
        // Test implementation
    }
    
    @Test(priority = 2, groups = "functional")
    public void test2() {
        // Test implementation
    }
    
    @AfterClass
    public void afterClass() {
        // Cleanup
    }
}
```

### Adding Configuration Options

1. Add to `application.yml`:
```yaml
custom:
  option1: value1
  option2: value2
```

2. Add to ConfigurationManager:
```java
public static String getCustomOption1() {
    return properties.getProperty("custom.option1");
}
```

---

## Troubleshooting

### Issue 1: Maven Build Failures

#### Error: "Could not find java compiler"
```
Solution:
1. Install JDK (not JRE)
2. Set JAVA_HOME environment variable
3. Restart IDE/Terminal
```

#### Error: "Failed to find chromedriver"
```
Solution:
1. WebDriverManager handles this automatically
2. If fails, ensure internet connection
3. Clear Maven cache: mvn clean -DskipTests
```

### Issue 2: Test Execution Failures

#### Flaky Tests
```
Solution:
1. Increase timeout in application.yml
2. Use wait utilities (not Thread.sleep)
3. Enable retry in application.yml: retry_count: 2
```

#### Parallel Execution Issues
```
Solution:
1. Use ThreadLocal for driver: DriverManager.setDriver()
2. Ensure no shared state between tests
3. Run serially to debug: mvn test -Dparallel=false
```

#### Element Not Found
```
Solution:
1. Add explicit wait: wait.waitForElementVisibility(locator)
2. Check element exists in DOM
3. Take screenshot: ScreenshotUtils.takeScreenshot()
4. Check locator with browser DevTools
```

### Issue 3: Report Generation

#### Allure Report Not Generated
```bash
# Install Allure CLI
# macOS: brew install allure
# Linux: sudo apt-get install allure
# Windows: choco install allure

# Or use Maven
mvn allure:report
```

#### ExtentReports Not Found
```
Solution:
1. Check dependency in pom.xml
2. Verify application.yml: report.type: EXTENT
3. Check target/extent-reports/ directory
```

### Issue 4: Environment & Configuration

#### "Connection Refused" Error
```
Solution:
1. Verify baseUrl is correct: application.yml
2. Check internet connection
3. Verify site is accessible: curl https://practicesoftwaretesting.com/
4. Check firewall/proxy settings
```

#### Wrong Environment Running
```
Solution:
mvn test -Denv=staging   # Explicitly set environment
```

---

## Best Practices

### 1. Test Organization
✅ Organize tests into logical classes  
✅ Use meaningful test names  
✅ Keep tests independent  
✅ Use data-driven approach  

### 2. Page Objects
✅ One class per page  
✅ Keep locators private  
✅ Extend BasePage  
✅ Use action-focused methods  

### 3. Waits & Timeouts
✅ **ALWAYS use explicit waits**  
❌ **NEVER use Thread.sleep()**  
✅ Use WaitUtils class  
✅ Set reasonable timeouts  

### 4. Logging & Reporting
✅ Log important steps  
✅ Take screenshots on failure  
✅ Attach logs to reports  
✅ Use meaningful assertions  

### 5. Code Quality
✅ Follow naming conventions  
✅ Keep methods small & focused  
✅ Comment complex logic  
✅ Use meaningful variable names  

### 6. Parallel Execution
✅ Use ThreadLocal for drivers  
✅ Ensure test isolation  
✅ No shared resources  
✅ Use unique test data  

### 7. Maintenance
✅ Regular dependency updates  
✅ Monitor test failures  
✅ Update locators as UI changes  
✅ Keep documentation current  

---

## Useful Commands

```bash
# Clean build
mvn clean

# Build without tests
mvn clean install -DskipTests

# Force update dependencies
mvn clean install -U

# Run tests with debug output
mvn test -X

# Run tests in offline mode
mvn test -o

# Generate project reports
mvn site

# Check for dependency updates
mvn versions:display-dependency-updates

# Update all dependencies
mvn versions:use-latest-versions
```

---

## Continuous Integration Setup

### GitHub Actions
```yaml
name: Test Suite
on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '17'
      - run: mvn clean test
```

### Jenkins
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Report') {
            steps {
                allure includeProperties: false
            }
        }
    }
}
```

---

## Support & Resources

### Documentation
- [TEST_PLAN.md](TEST_PLAN.md) - Test strategy
- [TEST_CASES.md](TEST_CASES.md) - 30+ test cases
- [ARCHITECTURE.md](ARCHITECTURE.md) - Framework design
- [FRAMEWORK_OVERVIEW.md](FRAMEWORK_OVERVIEW.md) - Project overview

### External Resources
- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Documentation](https://testng.org/)
- [Apache POI](https://poi.apache.org/)
- [Allure Reports](https://docs.qameta.io/allure/)

---

## Contributing

To contribute improvements:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

---

**Framework Version**: 1.0  
**Last Updated**: April 6, 2026  
**Status**: ✅ Production Ready

**Ready to start automating tests?** Run: `mvn clean test` 🚀

