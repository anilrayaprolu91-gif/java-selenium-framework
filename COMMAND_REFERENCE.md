# 📋 COMMAND REFERENCE CARD

## Quick Commands (Copy & Paste Ready)

### Development (Local)
```bash
# Serial execution (safest, good for debugging)
mvn clean test

# Run specific test
mvn test -Dtest=SearchTest

# Verbose output
mvn test -X
```

---

### CI/CD - RECOMMENDED ✅
```bash
# Safe parallel - 2 threads (best for most)
mvn clean test -Dsuite=src/test/resources/testng-parallel-safe.xml
```

---

### CI/CD - Alternative Options
```bash
# Aggressive parallel - 4 threads
mvn clean test -Dsuite=src/test/resources/testng-parallel.xml

# Fast parallel - 6 threads (high-spec only)
mvn clean test -Dsuite=src/test/resources/testng-parallel-fast.xml

# Serial baseline
mvn clean test -Dsuite=testng.xml
```

---

### Advanced
```bash
# More memory
mvn -Xmx1024m test

# Skip tests
mvn clean package -DskipTests

# Generate Allure report
mvn allure:report

# Run with detailed logging
mvn -X test
```

---

## Performance Chart

| Command | Time | Threads | Best For |
|---------|------|---------|----------|
| `mvn test` | ~50s | 1 | Development |
| Safe parallel | ~35s | 2 | CI/CD ✅ |
| Aggressive | ~25s | 4 | Fast CI/CD |
| Fast | ~20s | 6 | High-spec |

---

## Configuration at a Glance

| File | Threads | RAM | CPU | Use |
|------|---------|-----|-----|-----|
| testng.xml | 1 | 2GB | Any | Default |
| testng-parallel-safe.xml | 2 | 4GB | 2+ | CI/CD ✅ |
| testng-parallel.xml | 4 | 8GB | 4+ | Fast |
| testng-parallel-fast.xml | 6 | 16GB | 8+ | Max |

---

## CI/CD Integration

### GitHub Actions
```yaml
- run: mvn clean test -Dsuite=src/test/resources/testng-parallel-safe.xml
```

### Jenkins
```groovy
sh 'mvn clean test -Dsuite=src/test/resources/testng-parallel-safe.xml'
```

### GitLab
```yaml
script:
  - mvn clean test -Dsuite=src/test/resources/testng-parallel-safe.xml
```

---

## Troubleshooting

| Problem | Solution |
|---------|----------|
| Tests won't run | `mvn clean compile` then `mvn test` |
| Out of memory | Use safe-parallel or increase RAM |
| Tests fail parallel but pass serial | Check test isolation (no shared data) |
| Parameter errors | Confirmed fixed in BaseTest.java |

---

## Reports

**ExtentReports** (Automatic)
```
target/extent-reports/ExtentReport_[timestamp].html
```

**Allure** (Optional)
```bash
mvn allure:report
# Then open: target/site/allure-report/index.html
```

---

## Key Files

```
testng.xml                          ← Root (default serial)
src/test/resources/
  ├─ testng-serial.xml              ← Sequential
  ├─ testng-parallel-safe.xml       ← 2 threads ✅
  ├─ testng-parallel.xml            ← 4 threads
  └─ testng-parallel-fast.xml       ← 6 threads

src/test/java/com/automation/
  ├─ base/BaseTest.java             ← FIXED (optional browser)
  ├─ tests/SearchTest.java
  ├─ pages/
  └─ listeners/ExtentReportsListener.java
```

---

## Status: ✅ COMPLETE

- ✅ Parameter error fixed
- ✅ Parallel support added
- ✅ Reporting configured
- ✅ Documentation complete
- ✅ Ready for production

---

## One-Minute Start

```bash
# Try it now:
mvn clean test -Dsuite=src/test/resources/testng-parallel-safe.xml

# Check report:
target/extent-reports/ExtentReport_[timestamp].html
```

🚀 **That's it!**

---

**For more details, see:**
- QUICK_START_PARALLEL.md
- RESOURCES_FOLDER_GUIDE.md  
- ARCHITECTURE_GUIDE.md

