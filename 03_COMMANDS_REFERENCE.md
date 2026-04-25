# Commands Reference

This document provides all Maven and test execution commands for the Selenium 4 automation framework.

## Basic Commands

- Install dependencies:
  ```bash
  mvn clean install
  ```
- Run all tests:
  ```bash
  mvn test
  ```
- Compile only (no tests):
  ```bash
  mvn clean compile
  ```
- Clean build artifacts:
  ```bash
  mvn clean
  ```
- Skip tests during build:
  ```bash
  mvn clean package -DskipTests
  ```

## Running Specific Tests

- Run a specific test class:
  ```bash
  mvn test -Dtest=SearchTest
  ```
- Run a specific test method:
  ```bash
  mvn test -Dtest=SearchTest#testSearchHammerAndVerifyProductTitle
  ```
- Run multiple specific test methods:
  ```bash
  mvn test -Dtest=SearchTest#testSearchHammerAndVerifyProductTitle,SearchTest#testAddToCartButtonIsVisible
  ```

## Parallel Execution

- Standard parallel (3 threads):
  ```bash
  mvn test -DsuiteXmlFile=testng-parallel.xml
  ```
- Safe parallel (2 threads):
  ```bash
  mvn test -DsuiteXmlFile=testng-parallel-safe.xml
  ```
- Fast parallel (5 threads):
  ```bash
  mvn test -DsuiteXmlFile=testng-parallel-fast.xml
  ```
- Sequential (1 thread):
  ```bash
  mvn test
  # or
  mvn test -DsuiteXmlFile=testng.xml
  ```

## Viewing Test Reports

- Open HTML report:
  - `target/surefire-reports/index.html`
  - `target/site/surefire-report.html`

## Advanced Commands

- Show Maven help:
  ```bash
  mvn help
  ```
- Verbose output:
  ```bash
  mvn test -X
  ```
- Custom JVM memory:
  ```bash
  MAVEN_OPTS="-Xmx2048m" mvn test
  ```
- Run offline:
  ```bash
  mvn test -o
  ```

## IDE Usage

- IntelliJ: Right-click `SearchTest.java` → Run
- Eclipse: Right-click `SearchTest.java` → Run As → TestNG Test
- VS Code: Use TestNG extension or run `mvn test` in terminal

## Troubleshooting

- Check Maven installation:
  ```bash
  mvn --version
  ```
- Check Java installation:
  ```bash
  java --version
  ```
- Force Maven to update dependencies:
  ```bash
  mvn clean install -U
  ```
- Check POM configuration:
  ```bash
  mvn help:describe
  ```

## Success Indicators

- Successful test run output:
  ```
  Tests run: 3, Failures: 0, Skipped: 0
  BUILD SUCCESS
  ```
- Failed test: Check internet, website, locators, or timeouts.

## Related Documentation

- `02_QUICK_START_GUIDE.md` — Basic setup
- `04_RUN_TESTS_GUIDE.md` — Running tests
- `05_PARALLEL_EXECUTION.md` — Parallel execution
- `README.md` — Complete guide

---

Next: [04_RUN_TESTS_GUIDE.md](./04_RUN_TESTS_GUIDE.md)
