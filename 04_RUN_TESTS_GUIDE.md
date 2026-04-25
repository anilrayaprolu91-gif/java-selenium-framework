# How to Run Tests

This guide explains how to run all tests in the Selenium 4 automation framework and view results.

## Running All Tests

- Run all tests sequentially (default):
  ```bash
  mvn clean test
  ```
  - Uses `testng.xml` for configuration
  - Reports generated in `target/extent-reports/`

- Run all tests in parallel (faster):
  ```bash
  mvn clean test -Dsuite=testng-parallel-safe.xml
  ```
  - Multiple tests run simultaneously
  - Reports generated in `target/extent-reports/`

- Run without cleaning (faster for repeated runs):
  ```bash
  mvn test
  ```

- Run a specific test class:
  ```bash
  mvn test -Dtest=SearchTest
  ```

- Run a specific test method:
  ```bash
  mvn test -Dtest=SearchTest#testSearchHammerAndVerifyProductTitle
  ```

## Test Suites

| Suite File                  | Execution | Use Case   |
|----------------------------|-----------|------------|
| `testng.xml`               | Serial    | Debugging  |
| `testng-parallel-safe.xml` | Parallel  | CI/CD      |

## Viewing Results

- **Extent Reports:** `target/extent-reports/ExtentReport.html`
- **Surefire Reports:** `target/surefire-reports/`
- **Console Output:** Shows summary of tests run, failures, and errors

## Common Commands

- Compile only:
  ```bash
  mvn clean compile
  ```
- Skip tests:
  ```bash
  mvn clean compile -DskipTests
  ```
- Run with verbose output:
  ```bash
  mvn clean test -X
  ```
- Run with specific browser:
  ```bash
  mvn test -Dbrowser=firefox
  ```

## Troubleshooting

- Clean cache if tests fail to start:
  ```bash
  mvn clean
  mvn compile
  mvn test
  ```
- Ensure internet connection for Selenium Manager to download drivers
- Update locators if elements are not found
- Use parallel execution for faster tests

## IDE Integration

- IntelliJ: Right-click `testng.xml` or test class → Run
- Eclipse: Right-click `testng.xml` → Run As → TestNG Suite

## CI/CD Integration

- Example (GitHub Actions):
  ```yaml
  - name: Run Tests
    run: mvn clean test -Dsuite=testng-parallel-safe.xml
  - name: Publish Report
    uses: actions/upload-artifact@v2
    with:
      name: test-report
      path: target/extent-reports/
  ```

## Next Steps

- All tests are ready to run
- Reports are automatically generated
- Framework supports parallel execution
- Easy CI/CD integration

---

Next: [05_PARALLEL_EXECUTION.md](./05_PARALLEL_EXECUTION.md)
