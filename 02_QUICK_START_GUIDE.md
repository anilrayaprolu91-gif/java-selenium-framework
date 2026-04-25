# Quick Start Guide

This guide explains how to set up and run the Selenium 4 automation framework for practicesoftwaretesting.com in minutes.

## Prerequisites

- Java 18+ installed
- Maven 3.6+ installed
- Chrome, Firefox, or Edge browser

## Setup Steps

1. Open a terminal and navigate to the project directory:
   ```bash
   cd D:\TestAutomationKnowledge\JavaSeleniumMcp\SauceLabsSeleniumMcpTest
   ```
2. Install dependencies:
   ```bash
   mvn clean install
   ```
3. Run all tests:
   ```bash
   mvn test
   ```

Tests will run automatically and generate reports.

## Running Tests

- To run a specific test method:
  ```bash
  mvn test -Dtest=SearchTest#testSearchHammerAndVerifyProductTitle
  ```
- To run from an IDE:
  - IntelliJ: Right-click `SearchTest.java` → Run
  - Eclipse: Right-click `SearchTest.java` → Run As → TestNG Test

## Project Structure

- `src/test/java/com/automation/base/BaseTest.java`: Driver setup
- `src/test/java/com/automation/pages/HomePage.java`: Home page actions
- `src/test/java/com/automation/pages/ProductPage.java`: Product page actions
- `src/test/java/com/automation/tests/SearchTest.java`: Test cases

## Test Cases

- Search for "Hammer" and verify product title
- Verify "Add to Cart" button is visible
- Check product price is valid

## Customization

- Change search term in `SearchTest.java`:
  ```java
  homePage.searchProduct("Drill");
  ```
- Change browser in `BaseTest.java`:
  ```java
  initializeDriver("firefox");
  ```
- Adjust timeout in `BaseTest.java`:
  ```java
  private static final int DEFAULT_TIMEOUT = 20;
  ```

## Troubleshooting

- Maven not found: Install Maven from https://maven.apache.org
- WebDriver Manager can't download driver: Check internet connection
- "No such element" exception: Update locators in page objects
- Tests are slow: Wait for driver download or increase timeout

## Next Steps

- Add new test methods in `SearchTest.java`
- Create new page objects for additional pages
- Refer to `03_COMMANDS_REFERENCE.md` for all commands
- See `04_RUN_TESTS_GUIDE.md` for more details on running tests

---

Next: [03_COMMANDS_REFERENCE.md](./03_COMMANDS_REFERENCE.md)
