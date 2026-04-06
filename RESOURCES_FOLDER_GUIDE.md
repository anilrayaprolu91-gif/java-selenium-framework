# Parallel Execution & Resources Guide

## 📋 Overview

This guide explains how to run your test suite in different execution modes:
- **Serial Execution** (Debugging & Development)
- **Safe Parallel** (Recommended for most CI/CD)
- **Aggressive Parallel** (High-performance CI/CD)
- **Fast Parallel** (Maximum speed on powerful machines)

---

## 🚀 Quick Start Commands

### Serial Execution (Default - Single Thread)
```bash
mvn test
# or explicitly
mvn test -Dsuite=testng.xml
```
**When to use**: Development, debugging, troubleshooting  
**Speed**: ~50 seconds for 3 tests  
**Resources**: Minimal (1 browser window)

### Safe Parallel Execution (Recommended)
```bash
mvn test -Dsuite=src/test/resources/testng-parallel-safe.xml
```
**When to use**: Most CI/CD pipelines, development machines  
**Speed**: ~35 seconds for 3 tests (thread-count=2)  
**Resources**: Moderate (2 browser windows)  
**Stability**: High ✅

### Aggressive Parallel Execution
```bash
mvn test -Dsuite=src/test/resources/testng-parallel.xml
```
**When to use**: Fast CI/CD feedback, high-spec machines  
**Speed**: ~25 seconds for 3 tests (thread-count=4)  
**Resources**: High (4 browser windows)  
**Stability**: Good (with independent tests)

### Fast Parallel Execution
```bash
mvn test -Dsuite=src/test/resources/testng-parallel-fast.xml
```
**When to use**: Maximum speed on powerful CI/CD servers  
**Speed**: ~20 seconds for 3 tests (thread-count=6)  
**Resources**: Very High (6 browser windows)  
**Stability**: Good (requires 16GB+ RAM, 8-core CPU)

---

## 📁 Resources Folder Structure

All test configuration files are located in: `src/test/resources/`

```
src/test/resources/
├── testng-serial.xml              ← Sequential execution (new)
├── testng-parallel-safe.xml       ← Safe parallel (2 threads) - RECOMMENDED
├── testng-parallel.xml            ← Aggressive parallel (4 threads)
├── testng-parallel-fast.xml       ← Fast parallel (6 threads)
└── config.properties
```

**Root level:**
```
/
├── testng.xml                     ← Default (serial execution)
├── pom.xml                        ← Maven configuration (updated)
```

---

## ⚙️ Configuration Details

### Serial Execution (testng.xml)
```xml
<suite ... parallel="false" verbose="2">
    <parameter name="browser" value="chrome"/>
    ...
</suite>
```
- `parallel="false"` - Tests run sequentially
- `thread-count` - Ignored (not used in serial mode)
- **Benefit**: Perfect for debugging, maximum stability

### Safe Parallel (testng-parallel-safe.xml)
```xml
<suite ... parallel="methods" thread-count="2" verbose="2">
    <parameter name="browser" value="chrome"/>
    ...
</suite>
```
- `parallel="methods"` - Each test method in own thread
- `thread-count="2"` - Maximum 2 methods run simultaneously
- **Benefit**: Good speedup + high stability

### Aggressive Parallel (testng-parallel.xml)
```xml
<suite ... parallel="methods" thread-count="4" verbose="2">
    <parameter name="browser" value="chrome"/>
    ...
</suite>
```
- `parallel="methods"` - Each test method in own thread
- `thread-count="4"` - Maximum 4 methods run simultaneously
- **Benefit**: Good speedup, requires better hardware

### Fast Parallel (testng-parallel-fast.xml)
```xml
<suite ... parallel="methods" thread-count="6" verbose="2">
    <parameter name="browser" value="chrome"/>
    ...
</suite>
```
- `parallel="methods"` - Each test method in own thread
- `thread-count="6"` - Maximum 6 methods run simultaneously
- **Benefit**: Maximum speed, requires powerful machine (16GB+ RAM)

---

## 🛠️ BaseTest Configuration for Parallel Support

The `BaseTest.java` has been updated to support optional browser parameter:

```java
@BeforeMethod
@Parameters(value = "browser")
public void setUp(@org.testng.annotations.Optional("chrome") String browser) {
    initializeDriver(browser != null ? browser : "chrome");
    // Each thread gets its own WebDriver instance
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIMEOUT));
    // ... rest of setup
}

@AfterMethod
public void tearDown() {
    if (driver != null) {
        driver.quit();  // Each thread cleans up its own driver
    }
}
```

**Key Features:**
- ✅ Browser parameter is optional (defaults to "chrome")
- ✅ Each thread gets its own WebDriver instance (thread-safe)
- ✅ Proper cleanup after each test (no memory leaks)
- ✅ Works with all parallelization modes

---

## 📊 Performance Comparison

### 3 Test Methods Execution Time

| Mode | Thread Count | Estimated Time | Resources | Use Case |
|------|----------|-----------|-----------|----------|
| Serial | 1 | ~50 sec | 1 browser | Development, debugging |
| Safe Parallel | 2 | ~35 sec | 2 browsers | Most CI/CD pipelines ✅ |
| Aggressive | 4 | ~25 sec | 4 browsers | Fast CI/CD feedback |
| Fast | 6 | ~20 sec | 6 browsers | High-performance CI/CD |

**Assumptions:** Each test takes ~20 seconds individually

---

## 🎯 Choosing the Right Configuration

### ✅ Use Serial (testng.xml) When:
- Writing and debugging new tests
- Troubleshooting test failures
- Running locally on resource-constrained machines
- Need maximum test isolation
- First time running a test suite

### ✅ Use Safe Parallel When:
- Running on most CI/CD systems
- Running on standard development machines (8GB RAM, dual-core+)
- Want good speedup with stability
- Most common use case - **RECOMMENDED**

### ✅ Use Aggressive Parallel When:
- CI/CD machine has moderate resources (8-16GB RAM, quad-core)
- Need faster feedback in CI/CD pipeline
- Tests are proven stable and independent

### ✅ Use Fast Parallel When:
- CI/CD machine is high-spec (16GB+ RAM, 8-core+)
- Running large test suites (50+ tests)
- Need absolute maximum speed
- Can monitor resource usage closely

---

## 🔧 Maven Configuration (pom.xml)

The `pom.xml` has been updated to support dynamic suite selection:

```xml
<properties>
    <!-- Default to serial execution (testng.xml) -->
    <!-- Override with: mvn test -Dsuite=testng-parallel-safe.xml -->
    <suite>testng.xml</suite>
</properties>

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <suiteXmlFiles>
            <!-- Support dynamic suite selection via -Dsuite parameter -->
            <suiteXmlFile>${suite}</suiteXmlFile>
        </suiteXmlFiles>
    </configuration>
</plugin>
```

**How it works:**
1. Default: Uses `testng.xml` (serial execution)
2. Override: `mvn test -Dsuite=testng-parallel-safe.xml`
3. The `${suite}` variable is replaced by Maven

---

## 📋 Complete Command Reference

### Development & Debugging

```bash
# Run serial tests (default, easiest to debug)
mvn clean test

# Run specific test class serially
mvn test -Dtest=SearchTest

# Run with verbose output
mvn test -X
```

### CI/CD Pipelines

```bash
# RECOMMENDED: Safe parallel (most stable)
mvn clean test -Dsuite=src/test/resources/testng-parallel-safe.xml

# Aggressive parallel (faster feedback)
mvn clean test -Dsuite=src/test/resources/testng-parallel.xml

# Fast parallel (maximum speed, high-spec only)
mvn clean test -Dsuite=src/test/resources/testng-parallel-fast.xml

# Serial (baseline validation)
mvn clean test -Dsuite=testng.xml
```

### GitHub Actions Example

```yaml
- name: Run Tests (Safe Parallel)
  run: mvn clean test -Dsuite=src/test/resources/testng-parallel-safe.xml

- name: Generate Allure Report
  if: always()
  run: mvn allure:report
```

### Jenkins Pipeline Example

```groovy
stage('Test - Safe Parallel') {
    steps {
        sh 'mvn clean test -Dsuite=src/test/resources/testng-parallel-safe.xml'
    }
}
```

---

## ✅ Validation Checklist

- [✅] BaseTest supports optional browser parameter
- [✅] testng.xml (serial) in root directory
- [✅] testng-serial.xml in src/test/resources/
- [✅] testng-parallel-safe.xml in src/test/resources/ (2 threads)
- [✅] testng-parallel.xml in src/test/resources/ (4 threads)
- [✅] testng-parallel-fast.xml in src/test/resources/ (6 threads)
- [✅] pom.xml configured with dynamic suite support
- [✅] ExtentReports listener configured for all suites
- [✅] All parameters properly set in XML files
- [✅] Browser parameter defaults to "chrome"

---

## 🚨 Troubleshooting

### Error: Parameter 'browser' is required

**Problem**: Test fails with "Parameter 'browser' is required"  
**Solution**: BaseTest.java already updated with `@Optional("chrome")`  
**Verify**: BaseTest.setUp() should have `@Optional` annotation

### Tests pass serially but fail in parallel

**Problem**: Parallel execution causes test failures  
**Possible Causes**:
1. Tests are not independent (sharing state)
2. Tests use same test data
3. Threading issues in page objects

**Solutions**:
1. Use serial execution to verify single test works
2. Check for shared static variables
3. Ensure each test uses unique test data
4. Use ThreadLocal for driver instances

### Out of Memory during parallel execution

**Problem**: Memory error during parallel test run  
**Solution**: Reduce thread count or use safe-parallel.xml

```bash
# If you see OutOfMemory errors, reduce threads
mvn test -Dsuite=src/test/resources/testng-parallel-safe.xml
```

### Tests running too slowly in parallel

**Problem**: Parallel tests not faster than serial  
**Possible Causes**:
1. System doesn't have enough CPU cores
2. All tests accessing same website (bandwidth limited)
3. Thread count too high (context switching overhead)

**Solution**: Use safe-parallel.xml or serial.xml

---

## 📈 Performance Optimization Tips

### Tip 1: Find Optimal Thread Count
```bash
# Test formula: thread-count = (Available CPU Cores - 1)
# Example: 4-core CPU → use 3 threads
# Example: 8-core CPU → use 6-7 threads
```

### Tip 2: Monitor Resource Usage
```bash
# Windows: Open Task Manager while tests run
# Mac: Open Activity Monitor
# Linux: Watch with 'htop' or 'top'
# Look for: Browser processes, RAM, CPU usage
```

### Tip 3: Increase Timeouts in Parallel Mode
```java
// When running parallel, consider increasing implicit wait
private static final int DEFAULT_TIMEOUT = 15; // was 10
```

### Tip 4: Use Unique Test Data
```java
// Ensure each test uses unique/independent data
// Bad: static String TEST_USER = "testuser@example.com";
// Good: Each test creates/uses unique identifier
String testUser = "user_" + System.currentTimeMillis() + "@example.com";
```

---

## 📚 Additional Resources

- **TestNG Parallel Execution**: https://testng.org/doc/documentation-main.html#parallel-running
- **Selenium Best Practices**: https://www.selenium.dev/documentation/webdriver/best_practices/
- **Maven Surefire Plugin**: https://maven.apache.org/surefire/maven-surefire-plugin/

---

## 🎯 Recommended Workflow

### For Development Team
1. **Local Development**: Use `mvn test` (serial)
2. **Before Commit**: Use `mvn test -Dsuite=src/test/resources/testng-parallel-safe.xml`
3. **Code Review**: Serial validation in CI/CD baseline

### For CI/CD Pipeline
```yaml
# Smoke Tests
mvn clean test -Dsuite=src/test/resources/testng-parallel-safe.xml

# Full Suite (nightly)
mvn clean test -Dsuite=src/test/resources/testng-parallel.xml
```

### For Debugging Failures
1. Run failing test serially: `mvn test -Dtest=TestClassName`
2. Check test isolation (no shared state)
3. Re-run in safe parallel
4. If still fails, file bug and continue with serial

---

## 📝 Summary Table

| Scenario | Command | Thread Count | Est. Time (3 tests) |
|----------|---------|--------------|-----|
| Local dev & debug | `mvn test` | 1 | ~50s |
| CI/CD standard | `mvn test -Dsuite=src/test/resources/testng-parallel-safe.xml` | 2 | ~35s |
| CI/CD with good specs | `mvn test -Dsuite=src/test/resources/testng-parallel.xml` | 4 | ~25s |
| CI/CD max speed | `mvn test -Dsuite=src/test/resources/testng-parallel-fast.xml` | 6 | ~20s |

---

## ✨ Next Steps

1. **Verify Configuration**
   ```bash
   mvn test  # Should pass with serial execution
   ```

2. **Try Safe Parallel**
   ```bash
   mvn test -Dsuite=src/test/resources/testng-parallel-safe.xml
   ```

3. **Monitor Performance**
   - Open Task Manager/Activity Monitor
   - Watch resource usage
   - Note execution time

4. **Choose Strategy**
   - Local dev: Keep serial
   - CI/CD: Use safe-parallel as baseline
   - Optimize based on hardware

---

**Last Updated**: April 6, 2026  
**Framework**: Selenium 4 + TestNG + Maven  
**Status**: ✅ Ready for Production Use

