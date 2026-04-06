# TEST EXECUTION CHEAT SHEET

## 🚀 ONE-LINERS

```bash
# FASTEST - Parallel execution (20 sec)
mvn clean test -Dsuite=testng-parallel-safe.xml

# DEFAULT - Serial execution (40 sec)
mvn clean test

# QUICK - Just compile
mvn clean compile

# SPECIFIC - One test class
mvn test -Dtest=SearchTest

# ONE TEST - Single method
mvn test -Dtest=SearchTest#testVerifyProductPrice
```

## 📊 FULL COMMANDS WITH OPTIONS

```bash
# Clean everything and run tests
mvn clean test

# Run with verbose output
mvn clean test -X

# Run in parallel for speed
mvn clean test -Dsuite=testng-parallel-safe.xml

# Run specific browser
mvn test -Dbrowser=firefox

# Skip tests but compile
mvn clean compile -DskipTests

# Generate report
mvn allure:report

# Run specific test group
mvn test -Dgroups=smoke
```

## 📂 PROJECT STRUCTURE

```
SauceLabsSeleniumMcpTest/
├── src/
│   ├── main/java/
│   └── test/
│       ├── java/com/automation/
│       │   ├── pages/        (Page Objects)
│       │   ├── tests/        (Test Classes)
│       │   ├── base/         (BaseTest)
│       │   └── utils/        (Utilities)
│       └── resources/
│           ├── testng.xml    (Serial Suite)
│           └── testng-parallel-safe.xml (Parallel Suite)
├── target/
│   ├── extent-reports/       (HTML Reports)
│   ├── allure-results/       (Allure Data)
│   └── surefire-reports/     (Maven Reports)
├── pom.xml                   (Dependencies)
└── README.md                 (Documentation)
```

## 📝 IMPORTANT FILES

| File | Purpose | Location |
|------|---------|----------|
| `testng.xml` | Serial test suite | `src/test/resources/` |
| `testng-parallel-safe.xml` | Parallel test suite | `src/test/resources/` |
| `pom.xml` | Maven dependencies | Root directory |
| `ExtentReport.html` | Test results | `target/extent-reports/` |

## ⏱️ EXPECTED EXECUTION TIMES

```
Serial (testng.xml):        30-40 seconds
Parallel (safe):            15-20 seconds
Compile only:               5 seconds
Single test:                8 seconds
Report generation:          3 seconds
```

## 🎯 COMMON SCENARIOS

### Scenario 1: "I want to run all tests ASAP"
```bash
mvn clean test -Dsuite=testng-parallel-safe.xml
```

### Scenario 2: "I want to debug a specific test"
```bash
mvn test -Dtest=SearchTest
```

### Scenario 3: "I just want to compile"
```bash
mvn clean compile
```

### Scenario 4: "I want to run one test method"
```bash
mvn test -Dtest=SearchTest#testVerifyProductPrice
```

### Scenario 5: "I want detailed logs"
```bash
mvn clean test -X
```

## 🔍 CHECK TEST RESULTS

1. **Terminal Output:**
   ```
   Tests run: 41, Failures: X, Errors: 0
   BUILD SUCCESS or BUILD FAILURE
   ```

2. **HTML Report:**
   ```
   target/extent-reports/ExtentReport.html
   ```

3. **Surefire Reports:**
   ```
   target/surefire-reports/
   ```

## ✅ VERIFICATION

- ✓ Java 18+ installed: `java --version`
- ✓ Maven installed: `mvn --version`
- ✓ Internet connection (for WebDriver download)
- ✓ Chrome browser installed
- ✓ No ports blocked

## 🐛 TROUBLESHOOTING

| Issue | Solution |
|-------|----------|
| Tests won't compile | `mvn clean compile` then `mvn test` |
| "Driver not found" | Ensure internet for auto-download |
| Tests too slow | Use parallel: `mvn test -Dsuite=testng-parallel-safe.xml` |
| Report not found | Check: `target/extent-reports/ExtentReport.html` |
| Specific test fails | Run only that test: `mvn test -Dtest=TestClassName` |

## 🎓 COMMAND BREAKDOWN

```bash
mvn clean test -Dsuite=testng-parallel-safe.xml
│   │     │    │      │                        │
│   │     │    │      └─ Run specific suite
│   │     │    └─ Run tests
│   │     └─ Clean previous builds
│   └─ Maven command
└─ Maven executable
```

## 📚 DEPENDENCIES

- Selenium 4.15.0
- TestNG 7.8.0
- WebDriverManager 5.6.3
- Extent Reports 5.1.1
- Allure TestNG 2.24.0

## 🌐 SUPPORTED BROWSERS

- Chrome (default)
- Firefox: `mvn test -Dbrowser=firefox`
- Edge: `mvn test -Dbrowser=edge`

## 📞 QUICK REFERENCE

**Print this page and keep it handy!**

```bash
# Most used command
mvn clean test -Dsuite=testng-parallel-safe.xml

# View results
Open: target/extent-reports/ExtentReport.html
```

## 🎯 REMEMBER

- `clean` = Remove old files
- `test` = Run tests
- `-Dsuite=` = Choose test configuration
- `-Dtest=` = Run specific test
- `-Dbrowser=` = Choose browser

---

**Last Updated:** 2026-04-06
**Status:** ✅ All 41 tests ready to run

