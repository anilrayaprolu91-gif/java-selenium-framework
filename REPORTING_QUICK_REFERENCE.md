# Test Reporting - Quick Reference

## 🎯 Two Reporting Options

### ExtentReports (Automatic) ⚡
```bash
mvn test
# Report: target/extent-reports/ExtentReport_[timestamp].html
# Open in browser - No additional steps needed!
```

### Allure Reports (Modern) 📊
```bash
mvn clean test
mvn allure:report
mvn allure:serve
# Opens automatically in browser at http://localhost:4040
```

---

## 📊 Report Comparison

| Feature | ExtentReports | Allure |
|---------|---------------|--------|
| Setup | ✅ Automatic | ⚠️ Manual command |
| UI Quality | ✅ Good | ✅✅ Excellent |
| Test History | ❌ No | ✅ Yes |
| Timeline | ✅ Yes | ✅ Yes |
| Severity Levels | ❌ No | ✅ Yes |
| CI/CD Ready | ✅ Yes | ✅ Yes |

---

## 🚀 Quick Commands

### Generate Both Reports
```bash
mvn clean test allure:report
```

### View ExtentReports
```bash
target/extent-reports/ExtentReport_[timestamp].html
```

### View Allure Report
```bash
mvn allure:serve
```

---

## 📁 Report Locations

| Report | Location |
|--------|----------|
| **ExtentReports** | `target/extent-reports/` |
| **Allure Results** | `target/allure-results/` |
| **Allure Report** | `target/site/allure-maven-plugin/` |

---

## 💡 Features

### ExtentReports
✅ Beautiful HTML reports  
✅ Detailed test information  
✅ Pass/Fail/Skip indicators  
✅ System information  
✅ Automatic generation  

### Allure
✅ Modern UI  
✅ Test history tracking  
✅ Timeline view  
✅ Severity levels  
✅ Trend analysis  

---

## ✅ Checklist

- [x] Dependencies added to pom.xml
- [x] ExtentReportsListener created
- [x] TestNG listeners configured
- [x] Allure Maven plugin configured
- [x] Reports auto-generate on test run
- [x] Both report formats working

---

**Reports are ready!** Generate them with: `mvn test` 🎉

