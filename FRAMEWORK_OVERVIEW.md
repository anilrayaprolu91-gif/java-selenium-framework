# Java Selenium MCP Framework - Complete Implementation Guide

## Project Overview

This is a comprehensive, modern Java Selenium 4 framework built following Angie Jones' best practices and MCP (Model Context Protocol) principles for clean, modular, AI-agent-friendly architecture.

### Technology Stack
- **Java**: 17+ (Records, Sealed Classes, Pattern Matching)
- **Build Tool**: Maven 3.8+ with dependency management
- **Selenium**: 4.25+
- **Test Framework**: TestNG 7.8+
- **Excel**: Apache POI 5.2+ for data-driven testing
- **Reporting**: Allure Reports + ExtentReports
- **Logging**: SLF4J + Logback
- **Database**: Optional H2 for test data
- **Configuration**: YAML (application.yml) + properties

### Key Features
✅ Page Object Model (POM) with fluent API  
✅ Thread-local WebDriver for parallel execution  
✅ Excel-based data-driven testing  
✅ Explicit waits everywhere (no Thread.sleep)  
✅ Screenshot on failure  
✅ Retry mechanism for flaky tests  
✅ Cross-browser support (Chrome, Firefox, Edge, Safari)  
✅ Headless mode support  
✅ Comprehensive logging  
✅ CI/CD ready  

---

## Project Structure

```
selenium-mcp-framework/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/automation/
│   │   │       ├── config/              # Configuration management
│   │   │       ├── driver/              # WebDriver factory & management
│   │   │       ├── pages/               # Page Object Model classes
│   │   │       ├── utils/               # Utility classes
│   │   │       ├── models/              # Data models
│   │   │       └── enums/               # Enumerations
│   │   └── resources/
│   │       ├── application.yml          # Main config
│   │       ├── application-local.yml    # Local environment
│   │       ├── application-staging.yml  # Staging environment
│   │       ├── logback.xml              # Logging configuration
│   │       └── test-data/
│   │           ├── login_data.xlsx      # Test data
│   │           ├── product_data.xlsx
│   │           └── checkout_data.xlsx
│   │
│   └── test/
│       ├── java/
│       │   └── com/automation/
│       │       ├── base/                # Base test classes
│       │       ├── tests/               # Test classes
│       │       ├── listeners/           # TestNG listeners
│       │       └── data/                # Data providers
│       │
│       └── resources/
│           ├── testng.xml               # TestNG configuration
│           ├── testng-parallel.xml      # Parallel execution
│           └── test-data/               # Excel files
│
├── docs/
│   ├── TEST_PLAN.md                     # Comprehensive test plan
│   ├── TEST_CASES.md                    # 30+ test cases
│   ├── ARCHITECTURE.md                  # Framework architecture
│   └── TROUBLESHOOTING.md               # Troubleshooting guide
│
├── pom.xml                              # Maven POM
├── README.md                            # Setup & usage guide
└── .gitignore
```

---

## Quick Start Commands

### Setup
```bash
# Clone and setup
git clone <repo>
cd selenium-mcp-framework
mvn clean install

# Run all tests (parallel)
mvn clean test

# Run specific test
mvn clean test -Dtest=LoginTests

# Run with specific environment
mvn clean test -Denv=staging

# Generate Allure report
mvn allure:report
```

### Browser Options
```bash
# Chrome (default)
mvn test -Dbrowser=chrome -Dheadless=true

# Firefox
mvn test -Dbrowser=firefox

# Cross-browser
mvn test -Dbrowser=chrome,firefox,edge
```

---

## Key Components

### 1. WebDriver Factory
Thread-local WebDriver management for parallel execution with automatic driver cleanup.

### 2. Page Object Model (POM)
Fluent API-based POM with BasePage for common operations.

### 3. Utility Classes
- WaitUtils: Explicit waits (no Thread.sleep)
- ExcelUtils: Apache POI-based Excel data reading
- ScreenshotUtils: Screenshot on failure
- JSExecutorUtils: JavaScript execution helpers
- ApiUtils: REST API testing support

### 4. Data-Driven Testing
TestNG DataProviders with Excel-backed test data.

### 5. Reporting & Logging
- Allure Reports with screenshots
- ExtentReports HTML dashboard
- SLF4J + Logback for structured logging

---

## Environment Configuration

### Local Development
```bash
mvn test -Denv=local
```

### Staging/QA
```bash
mvn test -Denv=staging
```

### Production (Read-Only)
```bash
mvn test -Denv=production
```

---

## Test Execution Modes

### Serial Execution (Debugging)
```bash
mvn test -Dparallel=false
```

### Parallel by Methods
```bash
mvn test -Dparallel=methods -Dthread-count=4
```

### Parallel by Classes
```bash
mvn test -Dparallel=classes -Dthread-count=2
```

---

## See Also
- **README.md**: Complete setup and usage guide
- **TEST_PLAN.md**: Comprehensive test strategy
- **TEST_CASES.md**: 30+ detailed test cases
- **ARCHITECTURE.md**: Framework design details

---

**Framework Version**: 1.0  
**Last Updated**: April 6, 2026  
**Status**: Production Ready ✅

