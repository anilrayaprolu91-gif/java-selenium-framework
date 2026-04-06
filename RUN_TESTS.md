# How to Run All Tests

## Quick Start

### 1. **Run All Tests (Serial - Default)**
```bash
mvn clean test
```
This runs all tests sequentially using the default `testng.xml` configuration.

**Result:** Tests run one by one
- Time: ~30-40 seconds
- Reports: `target/extent-reports/`

---

### 2. **Run All Tests (Parallel - Faster)**
```bash
mvn clean test -Dsuite=testng-parallel-safe.xml
```
This runs tests in parallel for faster execution.

**Result:** Multiple tests run simultaneously
- Time: ~15-20 seconds (50% faster)
- Reports: `target/extent-reports/`

---

### 3. **Run Tests Without Clean**
```bash
mvn test
```
Skips cleanup, faster if you're running repeatedly.

---

### 4. **Run Specific Test Class**
```bash
mvn test -Dtest=SearchTest
```

---

### 5. **Run Specific Test Method**
```bash
mvn test -Dtest=SearchTest#testSearchHammerAndVerifyProductTitle
```

---

## Test Suites Available

| Suite File | Execution | Tests | Speed | Use Case |
|---|---|---|---|---|
| `testng.xml` | Serial | All | Slow | Debugging |
| `testng-parallel-safe.xml` | Parallel | All | Fast | CI/CD |

---

## Test Results

### View Test Results
After running tests, check:
- **Extent Reports:** `target/extent-reports/ExtentReport.html`
- **Surefire Reports:** `target/surefire-reports/`
- **Console Output:** Look for `Tests run: X, Failures: Y, Errors: Z`

### Example Output
```
[INFO] Tests run: 3, Failures: 1, Errors: 0, Skipped: 0
[INFO] BUILD FAILURE
```

---

## Common Commands

```bash
# Compile only (no tests)
mvn clean compile

# Compile and test
mvn clean test

# Parallel execution
mvn clean test -Dsuite=testng-parallel-safe.xml

# Skip tests
mvn clean compile -DskipTests

# Run with verbose output
mvn clean test -X

# Run specific group
mvn test -Dgroups=smoke

# Run with specific browser
mvn test -Dbrowser=firefox
```

---

## Environment Setup

### Prerequisites
- Java 18+
- Maven 3.6+
- Chrome/Firefox installed
- Internet connection (for Selenium Manager to download drivers)

### Verify Setup
```bash
mvn --version
java --version
```

---

## Framework Details

**Total Tests:** 41 across 6 test classes

### Test Classes:
1. **SearchTest** (3 tests) - Search functionality
2. **ProductTest** (4 tests) - Product details
3. **CartTest** (5 tests) - Cart operations
4. **CheckoutTest** (8 tests) - Checkout flow
5. **RegressionTest** (6 tests) - Regression scenarios
6. **BoundaryTest** (3 tests) - Edge cases
7. **NegativeTest** (5 tests) - Error scenarios
8. **DataDrivenTest** (2 tests) - Data variations
9. **CrossBrowserTest** (2 tests) - Browser compatibility

---

## Troubleshooting

### Issue: Tests Fail to Start
```bash
# Solution: Clean cache
mvn clean
mvn compile
mvn test
```

### Issue: Driver Not Found
```bash
# Selenium Manager downloads drivers automatically
# Ensure internet connection is available
```

### Issue: Port Already in Use
```bash
# Change timeout or wait for process to finish
```

### Issue: Slow Tests
```bash
# Use parallel execution
mvn clean test -Dsuite=testng-parallel-safe.xml
```

---

## Report Locations

After running tests:
1. **Extent HTML Report:** `target/extent-reports/ExtentReport.html` ⭐
2. **Allure Report:** `target/allure-results/`
3. **Surefire Reports:** `target/surefire-reports/`
4. **Test Output:** `target/test-classes/`

---

## IDE Integration (IntelliJ/Eclipse)

### IntelliJ IDEA:
1. Right-click on `testng.xml` → Run
2. Or: Right-click on test class → Run

### Eclipse:
1. Right-click on `testng.xml` → Run As → TestNG Suite
2. Or: Right-click on test class → Run As → TestNG Test

---

## CI/CD Pipeline Example

```yaml
# GitHub Actions
- name: Run Tests
  run: mvn clean test -Dsuite=testng-parallel-safe.xml

- name: Publish Report
  uses: actions/upload-artifact@v2
  with:
    name: test-report
    path: target/extent-reports/
```

---

## Next Steps

✅ All tests are ready to run
✅ Reports automatically generated
✅ Framework supports parallel execution
✅ Easy CI/CD integration

**Happy Testing! 🚀**

