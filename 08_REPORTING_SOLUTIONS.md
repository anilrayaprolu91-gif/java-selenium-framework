# Test Reporting Guide - Allure & ExtentReports

## Overview

This framework now includes **two professional reporting solutions**:

1. **ExtentReports** - Beautiful HTML reports with detailed test information
2. **Allure Reports** - Modern reporting with timeline and history tracking

Both reports are automatically generated when you run tests.

---

## 🎯 ExtentReports

### What is ExtentReports?

ExtentReports is a popular open-source reporting library that generates beautiful, interactive HTML test reports. It provides:

- ✅ Professional looking HTML reports
- ✅ Detailed test execution details
- ✅ Pass/Fail/Skip status indicators
- ✅ Exception stack traces
- ✅ System information
- ✅ Timeline view
- ✅ Author and category tracking

### ExtentReports Setup

The framework includes an `ExtentReportsListener` that automatically generates reports:

```
Location: src/test/java/com/automation/listeners/ExtentReportsListener.java
```

### Accessing ExtentReports

#### Generate Report
```bash
mvn clean test
```

#### View Report
```
Location: target/extent-reports/ExtentReport_[timestamp].html
```

**Simply open the HTML file in your browser to view the report!**

#### Report Features
- Test execution timeline
- Detailed test information
- Exception stack traces
- System information (OS, Java version, etc.)
- Categories and authors
- Color-coded pass/fail/skip indicators

### Example Report Output

```
Test Suite: Practice Software Testing Suite
├── Test 1: testSearchHammerAndVerifyProductTitle ✅ PASSED
├── Test 2: testAddToCartButtonIsVisible ✅ PASSED
└── Test 3: testVerifyProductPrice ✅ PASSED

System Information:
  OS: Windows 10
  Java Version: 18.0.1
  Browser: Chrome
  Framework: Selenium 4 + TestNG
```

### Customizing ExtentReports

Edit `ExtentReportsListener.java` to customize:

```java
// Change report name
sparkReporter.config().setReportName("Your Custom Report Name");

// Change theme (DARK or LIGHT)
sparkReporter.config().setTheme(Theme.DARK);

// Add custom system information
extentReports.setSystemInfo("Custom Key", "Custom Value");

// Change timestamp format
sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
```

---

## 🎯 Allure Reports

### What is Allure?

Allure is a modern reporting framework that creates beautiful, interactive reports with:

- ✅ Modern UI with timeline view
- ✅ Test history and trend analysis
- ✅ Detailed test information and attachments
- ✅ Severity levels and categories
- ✅ Integration with CI/CD pipelines
- ✅ Multi-language support

### Allure Setup

Allure is configured in `pom.xml` with:

```xml
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <version>2.24.0</version>
</dependency>

<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>2.13.0</version>
</plugin>
```

### Generating Allure Reports

#### Step 1: Run Tests
```bash
mvn clean test
```

This creates raw test results in `target/allure-results/`

#### Step 2: Generate Report
```bash
mvn allure:report
```

#### Step 3: View Report
```bash
mvn allure:serve
```

This opens the Allure report in your default browser at: `http://localhost:4040`

### Report Features

#### Overview Dashboard
- Test execution statistics
- Pass/Fail/Skip percentages
- Test duration
- Success rate trend

#### Test Cases
- Detailed test information
- Steps taken during test
- Attachments (screenshots, logs)
- Severity levels

#### Suites
- Organized test suites
- Test results by suite
- Execution timeline

#### Timeline
- Chronological test execution
- Parallel test visualization
- Duration per test

#### History
- Test execution trends
- Pass/Fail history
- Regression detection

### Example Allure Commands

```bash
# Clean, compile, and run tests
mvn clean test

# Generate Allure report from existing results
mvn allure:report

# Serve Allure report in browser
mvn allure:serve

# Generate and serve in one command
mvn clean test allure:report allure:serve

# View report history
mvn allure:history
```

### Adding Allure Annotations to Tests

To enhance Allure reports, add annotations to test methods:

```java
import io.qameta.allure.*;

@Feature("Search Functionality")
@Story("Search for products")
@Severity(SeverityLevel.CRITICAL)
@Test(description = "Search for Hammer and verify product title")
@Description("This test searches for a product and verifies the details")
public void testSearchHammerAndVerifyProductTitle() {
    // Test implementation
}
```

### Allure Configuration

Edit `allure.properties` to customize Allure behavior:

```properties
# Report directory
allure.results.directory=target/allure-results

# Report name
allure.results.title=Practice Software Testing - Allure Report

# Issue and TMS links
allure.results.links.issue.pattern=https://github.com/issues/%s
allure.results.links.tms.pattern=https://github.com/test-cases/%s
```

---

## 🔄 Complete Reporting Workflow

### Step 1: Install Dependencies
```bash
mvn clean install
```

### Step 2: Run Tests
```bash
mvn test
```

**Results:**
- ExtentReports HTML file generated in `target/extent-reports/`
- Allure test results saved in `target/allure-results/`

### Step 3: View Reports

#### ExtentReports
```bash
# Open the HTML file directly
target/extent-reports/ExtentReport_[timestamp].html
```

#### Allure
```bash
# Generate and serve report
mvn allure:serve
```

---

## 📊 Report Comparison

| Feature | ExtentReports | Allure |
|---------|---------------|--------|
| **HTML Report** | ✅ Built-in | ✅ Generated |
| **Modern UI** | ✅ Yes | ✅ Yes (better) |
| **Test History** | ❌ No | ✅ Yes |
| **Timeline View** | ✅ Yes | ✅ Yes (better) |
| **Severity Levels** | ❌ No | ✅ Yes |
| **Attachments** | ✅ Yes | ✅ Yes |
| **Easy Setup** | ✅ Auto | ⚠️ Needs command |
| **CI/CD Ready** | ✅ Yes | ✅ Yes (better) |
| **Parallel Support** | ✅ Yes | ✅ Yes |

---

## 🚀 Quick Start Commands

### ExtentReports (Automatic)
```bash
# Run tests - ExtentReports generated automatically
mvn test

# View report
target/extent-reports/ExtentReport_[timestamp].html
```

### Allure (Manual Generation)
```bash
# Run tests
mvn clean test

# Generate report
mvn allure:report

# Serve report
mvn allure:serve
```

### Both Reports
```bash
# Run tests and generate both reports
mvn clean test allure:report

# View ExtentReports
target/extent-reports/ExtentReport_[timestamp].html

# Serve Allure report
mvn allure:serve
```

---

## 📁 Report Locations

### ExtentReports
```
target/extent-reports/
└── ExtentReport_2026-04-06 10-30-45.html
```

Open directly in browser.

### Allure Results
```
target/allure-results/
├── 1_TestNG-1234567890.json
├── 2_TestNG-1234567891.json
├── 3_TestNG-1234567892.json
└── executor.json
```

### Allure Report
```
target/site/allure-maven-plugin/
└── index.html
```

Generated after running `mvn allure:report`

---

## 💡 Best Practices

### 1. Name Tests Descriptively
Good test names are reflected in both reports:
```java
@Test(description = "Search for Hammer and verify product title")
public void testSearchHammerAndVerifyProductTitle() { }
```

### 2. Add Allure Annotations
Enhance Allure reports with metadata:
```java
@Feature("Search")
@Story("Product search")
@Severity(SeverityLevel.CRITICAL)
@Test
public void testSearch() { }
```

### 3. Generate Reports Regularly
Include report generation in your CI/CD pipeline:
```bash
# CI/CD command
mvn clean test allure:report
```

### 4. Archive Reports
Keep historical reports for trend analysis:
```bash
# Archive reports
cp -r target/extent-reports/* reports/extent/$(date +%Y-%m-%d)
cp -r target/allure-results/* reports/allure/$(date +%Y-%m-%d)
```

---

## 🔧 Configuration Files

### pom.xml
Contains dependencies and Maven plugins:
```xml
<!-- Dependencies -->
<dependency>
    <groupId>com.aventstack</groupId>
    <artifactId>extentreports</artifactId>
    <version>5.1.1</version>
</dependency>

<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <version>2.24.0</version>
</dependency>

<!-- Plugins -->
<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>2.13.0</version>
</plugin>
```

### testng.xml
Includes listener for ExtentReports:
```xml
<listeners>
    <listener class-name="com.automation.listeners.ExtentReportsListener"/>
</listeners>
```

### allure.properties
Allure configuration:
```properties
allure.results.directory=target/allure-results
allure.results.title=Practice Software Testing - Allure Report
```

---

## 🎯 Implementation Details

### ExtentReportsListener Class
```
Location: src/test/java/com/automation/listeners/ExtentReportsListener.java

Implements: ITestListener
Features:
  - Auto-initializes on test suite start
  - Tracks test execution status
  - Captures exceptions and failures
  - Generates timestamped reports
  - System information tracking
  - Color-coded status indicators
```

### TestNG Integration
```
- testng.xml includes listener configuration
- Automatic initialization before tests run
- Report generation after test completion
- No additional code required in test classes
```

---

## 🌐 CI/CD Integration

### GitHub Actions
```yaml
name: Run Tests and Generate Reports
on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '18'
      - name: Run tests and generate reports
        run: mvn clean test allure:report
      - name: Archive reports
        if: always()
        uses: actions/upload-artifact@v2
        with:
          name: test-reports
          path: |
            target/extent-reports/
            target/site/allure-maven-plugin/
```

### Jenkins
```groovy
stage('Test and Report') {
    steps {
        sh 'mvn clean test allure:report'
    }
    post {
        always {
            archiveArtifacts artifacts: 'target/extent-reports/**,target/site/allure-maven-plugin/**'
        }
    }
}
```

---

## 📚 Resources

### Allure Documentation
- https://docs.qameta.io/allure/
- Allure TestNG Integration: https://docs.qameta.io/allure/#_testng

### ExtentReports Documentation
- https://extentreports.com/
- ExtentReports 5.x: https://extentreports.com/docs/v5/java/

### TestNG Listeners
- https://testng.org/doc/documentation-main.html#listeners

---

## ✅ Verification Checklist

- [x] ExtentReports dependency added
- [x] Allure dependencies added
- [x] ExtentReportsListener created
- [x] TestNG files updated with listener
- [x] allure.properties configured
- [x] pom.xml includes Allure Maven plugin
- [x] Reports generated on test run
- [x] Both report formats working

---

## 🎉 Ready to Generate Beautiful Reports!

Both reporting frameworks are now fully integrated and ready to use:

```bash
# Generate ExtentReports (automatic)
mvn test

# Generate Allure reports (manual)
mvn allure:serve
```

Enjoy professional test reporting! 📊

