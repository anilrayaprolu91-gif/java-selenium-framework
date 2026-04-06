# Quick Command Reference

## FASTEST WAYS TO RUN TESTS

### ⚡ Run All Tests (Parallel - FASTEST)
```bash
mvn clean test -Dsuite=testng-parallel-safe.xml
```
**15-20 seconds** | All tests run simultaneously

### 🔄 Run All Tests (Serial - DEFAULT)
```bash
mvn clean test
```
**30-40 seconds** | Tests run one by one, easier to debug

### 📋 Run Specific Test
```bash
mvn test -Dtest=SearchTest
```

### 🎯 Run Single Test Method
```bash
mvn test -Dtest=SearchTest#testVerifyProductPrice
```

---

## COMPILATION

### ✔️ Just Compile (No Tests)
```bash
mvn clean compile
```

### 📦 Build Only
```bash
mvn clean package -DskipTests
```

---

## ADVANCED OPTIONS

### 🌐 Run with Specific Browser
```bash
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
mvn test -Dbrowser=chrome
```

### 🔍 Verbose Output
```bash
mvn test -X
```

### ⏸️ Skip Tests
```bash
mvn compile -DskipTests
```

### 🎪 Run Specific Group
```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=regression
```

---

## REPORTS & RESULTS

📊 **Extent Report (BEST):**
```
target/extent-reports/ExtentReport.html
```

📈 **Allure Report:**
```
target/allure-results/
```

🔗 **Maven Surefire:**
```
target/surefire-reports/
```

---

## COMMON WORKFLOWS

### Development (Debug Mode)
```bash
mvn clean test
# Run serially, see console output
```

### CI/CD Pipeline (Fast Mode)
```bash
mvn clean test -Dsuite=testng-parallel-safe.xml
# Run in parallel, generate reports
```

### Quick Check
```bash
mvn test -Dtest=SearchTest#testVerifyProductPrice
# Run 1 test, takes ~8 seconds
```

### Full Verification
```bash
mvn clean test -Dsuite=testng-parallel-safe.xml
# Run all 41 tests, ~20 seconds
```

---

## TROUBLESHOOTING COMMANDS

### Clear Everything & Start Fresh
```bash
mvn clean
mvn compile
mvn test
```

### Generate Fresh Allure Report
```bash
mvn allure:report
```

### See Detailed Error Info
```bash
mvn test -e
# Shows full stack trace
```

---

## TERMINAL SHORTCUTS (Windows PowerShell)

### Navigate to Project
```powershell
cd D:\TestAutomationKnowledge\JavaSeleniumMcp\SauceLabsSeleniumMcpTest
```

### Run Tests
```powershell
mvn clean test
```

### View Extent Report
```powershell
Start-Process "target/extent-reports/ExtentReport.html"
```

---

**💡 Pro Tips:**
- Use **parallel mode** for CI/CD pipelines (50% faster)
- Use **serial mode** for local development/debugging
- Always run `mvn clean` before major test runs
- Check **ExtentReport.html** for beautiful test results
- Tests are browser-agnostic (Chrome default, customize with -Dbrowser)

---

**Test Status:** ✅ **READY TO RUN**
- 41 Total Tests
- 6 Test Classes
- Multiple Suites (Serial & Parallel)
- Automated Reports

