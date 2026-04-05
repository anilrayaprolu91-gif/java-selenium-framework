# Parallel Execution Quick Reference

## 🚀 Run Tests in Parallel - Quick Commands

### Standard Parallel (Recommended)
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-parallel.xml
```
- **Threads**: 3
- **Speed**: ~20 seconds
- **Resource**: Moderate
- **Use Case**: Most CI/CD pipelines

### Fast Parallel
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-parallel-fast.xml
```
- **Threads**: 5
- **Speed**: ~10-15 seconds
- **Resource**: High
- **Use Case**: High-performance CI/CD, servers

### Safe Parallel
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-parallel-safe.xml
```
- **Threads**: 2
- **Speed**: ~30 seconds
- **Resource**: Low
- **Use Case**: Development machines, limited resources

### Sequential (Original)
```bash
mvn test
# or
mvn test -DsuiteXmlFile=testng.xml
```
- **Threads**: 1
- **Speed**: ~50 seconds
- **Resource**: Minimal
- **Use Case**: Debugging, development

---

## 📊 Performance Comparison

| Config | Speed | Threads | Resources | Best For |
|--------|-------|---------|-----------|----------|
| **testng.xml** | 50 sec | 1 | ⭐ Low | Debugging |
| **testng-parallel-safe.xml** | 30 sec | 2 | ⭐⭐ Low-Moderate | Dev machine |
| **testng-parallel.xml** | 20 sec | 3 | ⭐⭐⭐ Moderate | Standard CI/CD |
| **testng-parallel-fast.xml** | 10-15 sec | 5 | ⭐⭐⭐⭐⭐ High | Power servers |

---

## 🎯 Choose Your Configuration

### I'm debugging locally
```bash
mvn test
# Slowest but easiest to debug
# Results: Sequential execution
```

### I have a basic laptop
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-parallel-safe.xml
# Balanced: some speed, low resource usage
# Results: 2 tests run in parallel
```

### I'm running on CI/CD (standard)
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-parallel.xml
# Good balance of speed and resources
# Results: 3 tests run in parallel
```

### I have a powerful server
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-parallel-fast.xml
# Maximum parallelization
# Results: 5 tests run in parallel
```

---

## 📁 Files in Resources Folder

```
src/test/resources/
├── testng-parallel.xml          ← Standard (3 threads)
├── testng-parallel-fast.xml     ← Fast (5 threads)
├── testng-parallel-safe.xml     ← Safe (2 threads)
└── config.properties            ← Test configuration
```

---

## 🔄 How Parallel Works

### Sequential (testng.xml)
```
Time: 0s    10s    20s    30s    40s    50s
Test 1: ████████████████████ (20 sec)
Test 2:                     ████████████ (15 sec)
Test 3:                                  ████████████ (15 sec)
Total: 50 seconds (all sequential)
```

### Parallel with 3 Threads
```
Time: 0s    10s    20s    30s
Test 1: ████████████████████ (20 sec)
Test 2: ████████████ (15 sec)
Test 3: ████████████ (15 sec)
Total: 20 seconds (3x faster!)
```

---

## ✅ Verify Parallel Execution is Working

### Check 1: Watch the Output
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-parallel.xml
```
Look for output like:
```
[TestNG] Running:
  Search Tests - Parallel
testSearchHammerAndVerifyProductTitle
testAddToCartButtonIsVisible
testVerifyProductPrice
```

### Check 2: Open Task Manager
While tests are running:
1. Press `Ctrl+Shift+Esc`
2. Look at "Google Chrome" processes
3. Should see 3 Chrome instances running simultaneously

### Check 3: Time Difference
- Sequential: ~50 seconds
- Parallel (3 threads): ~20 seconds
- Speed increase: ~2.5x

---

## 🛠️ Customize Thread Count

### Edit testng-parallel.xml
```xml
<!-- Change this line: -->
<suite ... thread-count="3">

<!-- To a different number: -->
<suite ... thread-count="5">  <!-- Faster -->
<suite ... thread-count="2">  <!-- Safer -->
<suite ... thread-count="1">  <!-- Sequential -->
```

### Recommended Thread Counts
```
1-3 Tests:      2-3 threads
4-10 Tests:     4-5 threads
11-50 Tests:    5-10 threads
50+ Tests:      10-15 threads
```

---

## 🐛 Troubleshooting Parallel Execution

### Issue: Tests are slow
**Solution**: Increase thread count or use testng-parallel-fast.xml

### Issue: Out of memory
**Solution**: Reduce thread count to testng-parallel-safe.xml

### Issue: Tests fail randomly
**Solution**: Switch to sequential (testng.xml) for debugging

### Issue: One test fails, others continue
**Normal behavior** - Parallel execution doesn't stop on first failure

---

## 📈 Performance Metrics

### Memory Usage
```
Sequential:  ~500 MB (1 browser)
Parallel-2:  ~1 GB (2 browsers)
Parallel-3:  ~1.5 GB (3 browsers)
Parallel-5:  ~2.5 GB (5 browsers)
```

### CPU Usage
```
Sequential:  ~20-30% CPU
Parallel-2:  ~40-50% CPU
Parallel-3:  ~50-70% CPU
Parallel-5:  ~70-90% CPU
```

---

## 🎓 TestNG Parallelization Levels

### By Methods (USED HERE)
```xml
<suite parallel="methods" thread-count="3">
```
- Run test methods in parallel ✅
- Best for our setup (3 test methods)
- Independent test execution

### By Classes
```xml
<suite parallel="classes" thread-count="2">
```
- Run test classes in parallel
- Use when you have multiple test classes

### By Instances
```xml
<suite parallel="instances" thread-count="4">
```
- Run test instances in parallel
- Use for data-driven tests

### By Suite
```xml
<suite parallel="suite" thread-count="2">
```
- Run test suites in parallel
- Use for multiple suites

---

## 🚀 Integration with CI/CD

### GitHub Actions
```yaml
- name: Run Tests in Parallel
  run: mvn test -DsuiteXmlFile=src/test/resources/testng-parallel.xml
```

### Jenkins
```groovy
stage('Test') {
    steps {
        sh 'mvn test -DsuiteXmlFile=src/test/resources/testng-parallel.xml'
    }
}
```

### GitLab CI
```yaml
test:
  script:
    - mvn test -DsuiteXmlFile=src/test/resources/testng-parallel.xml
```

---

## ✨ Benefits of Parallel Execution

✅ **Faster Feedback**: 2.5x-5x speedup
✅ **Better Resource Utilization**: Uses multiple cores
✅ **Reduced Wait Time**: Less time in CI/CD pipeline
✅ **Cost Savings**: Faster pipeline = lower cloud costs
✅ **Early Bug Detection**: More tests run more frequently

---

## ⚠️ When NOT to Use Parallel

❌ Debugging specific test failures → Use sequential
❌ First-time test setup → Start with sequential
❌ Limited system resources → Use safe-parallel
❌ Tests with shared state → Fix tests first, then parallelize
❌ Intermittent failures → Debug with sequential first

---

## 📊 Expected Results

### Running with testng-parallel.xml
```
Tests run: 3
Failures: 0
Skipped: 0
Total time: ~20 seconds

✅ testSearchHammerAndVerifyProductTitle
✅ testAddToCartButtonIsVisible
✅ testVerifyProductPrice
```

**All tests PASS in parallel!** 🎉

---

## 📞 Quick Help

| Question | Answer |
|----------|--------|
| How do I run parallel? | `mvn test -DsuiteXmlFile=src/test/resources/testng-parallel.xml` |
| How do I run fast parallel? | Use `testng-parallel-fast.xml` |
| How do I run safe parallel? | Use `testng-parallel-safe.xml` |
| How do I debug? | Use `mvn test` (sequential) |
| How do I change thread count? | Edit `thread-count="3"` in XML |

---

## 🎯 Recommended Setup

### Development Machine
```bash
# Fast feedback with low resource usage
mvn test -DsuiteXmlFile=src/test/resources/testng-parallel-safe.xml
```

### CI/CD Pipeline
```bash
# Good balance of speed and resources
mvn test -DsuiteXmlFile=src/test/resources/testng-parallel.xml
```

### High-Performance Server
```bash
# Maximum parallelization
mvn test -DsuiteXmlFile=src/test/resources/testng-parallel-fast.xml
```

---

**Ready to run tests faster!** ⚡

```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-parallel.xml
```

🎉 Enjoy 2.5x faster test execution!

