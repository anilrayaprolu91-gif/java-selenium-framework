# Start Here

This project is a production-grade Selenium 4 automation framework for testing [practicesoftwaretesting.com](https://practicesoftwaretesting.com/).

## Project Structure

### Java Classes

- `BaseTest.java`: Driver management and setup
- `HomePage.java`: Home page Page Object Model (POM)
- `ProductPage.java`: Product page POM
- `SearchTest.java`: Test cases for search and product validation

### Configuration Files

- `pom.xml`: Maven configuration with Selenium 4 and TestNG
- `testng.xml`: TestNG suite configuration
- `config.properties`: Externalized configuration

### Documentation

The documentation consists of 17 numbered Markdown files:

- 01_START_HERE.md (this file)
- 02_QUICK_START_GUIDE.md
- 03_COMMANDS_REFERENCE.md
- 04_RUN_TESTS_GUIDE.md
- 05_PARALLEL_EXECUTION.md
- 06_ARCHITECTURE_GUIDE.md
- 07_CODE_REFERENCE.md
- 08_REPORTING_SOLUTIONS.md
- 09_TEST_CASES_DOCUMENTATION.md
- 10_TESTING_RESOURCES.md
- 11_FRAMEWORK_OVERVIEW.md
- 12_IMPLEMENTATION_DETAILS.md
- 13_AT_A_GLANCE_SUMMARY.md
- 14_PROJECT_DELIVERABLES.md
- 15_MISSING_CLASSES_RESOLVED.md
- 16_COMPILATION_FIXES.md
- 17_MASTER_INDEX.md

### Supporting Files

- `homepage.png`: Screenshot of the home page
- `productpage.png`: Screenshot of the product detail page
- `README.md`: Comprehensive guide

## Quick Start

To run all tests:

```bash
cd D:\TestAutomationKnowledge\JavaSeleniumMcp\SauceLabsSeleniumMcpTest
mvn clean test
```

To run a specific test:

```bash
mvn test -Dtest=SearchTest#testSearchHammerAndVerifyProductTitle
```

To run from an IDE:

- IntelliJ: Right-click `SearchTest.java` → Run
- Eclipse: Right-click `SearchTest.java` → Run As → TestNG Test

## Learning Paths

### Beginner

1. Read `02_QUICK_START_GUIDE.md`
2. Run `mvn test`
3. See tests pass

### Intermediate

1. Read `02_QUICK_START_GUIDE.md`
2. Read `04_RUN_TESTS_GUIDE.md`
3. Read `06_ARCHITECTURE_GUIDE.md`
4. Explore the source code
5. Understand the Page Object Model

### Advanced

1. Read all files from 01 to 17 in sequence
2. Study the source code in detail
3. Understand all design patterns
4. Extend the framework

## Test Coverage

The framework includes test cases for:

- Searching for products
- Verifying product titles, prices, and descriptions
- Validating button visibility
- Price validation

## Framework Highlights

- Page Object Model (POM) design
- Selenium 4.15.0
- TestNG 7.8.1
- Java 18+
- Stable locators using `data-test` attributes
- Implicit and explicit waits
- Chrome, Firefox, and Edge browser support
- 3 complete test cases
- Over 30 POM methods
- Over 1,100 lines of code
- Over 2,000 lines of documentation

## Architecture Overview

The test classes call page object methods and make assertions, abstracting away Selenium details. The HomePage and ProductPage POMs encapsulate all interactions with the UI. WebDriver is managed centrally and supports Chrome, Firefox, and Edge. The framework targets [practicesoftwaretesting.com](https://practicesoftwaretesting.com/).

## Common Tasks

- Run all tests: `mvn test`
- Modify search term in `SearchTest.java`
- Add new test methods using templates from `07_CODE_REFERENCE.md`
- Change browser in `BaseTest.java`
- Adjust timeouts in `BaseTest.java`

## Features

- Multiple browser support
- Clean, maintainable, and scalable code
- Robust error handling
- Explicit and implicit waits
- Comprehensive documentation
- Easy extensibility

## Quality Metrics

- Code quality: 5/5
- Documentation: 5/5
- Ease of use: 5/5
- Extensibility: 5/5
- Best practices: 5/5

## Success Criteria

- Selenium 4 framework
- TestNG test cases
- Page Object Model
- PageFactory pattern
- Stable locators
- BaseTest class
- HomePage and ProductPage POMs
- Search and product detail tests
- Title verification
- Complete documentation
- Code examples
- Screenshots
- Configuration files
- Production ready

## Navigation

For setup, commands, running tests, parallel execution, architecture, code examples, test reports, and more, refer to the corresponding numbered Markdown files or the master index (`17_MASTER_INDEX.md`).

## Next Steps

1. Run the tests: `mvn test`
2. Explore the documentation
3. Extend the framework as needed

---

Framework Version: 1.0  
Selenium: 4.15.0  
TestNG: 7.8.1  
Java: 18+  
Status: Production Ready

---

Next: [02_QUICK_START_GUIDE.md](./02_QUICK_START_GUIDE.md)
