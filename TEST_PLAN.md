# Test Plan: Practice Software Testing - Toolshop

## 1. Introduction

### 1.1 Purpose
This test plan defines the testing strategy and approach for the Practice Software Testing (Toolshop) web application. It covers functional, regression, negative, and boundary testing scenarios for the e-commerce platform.

### 1.2 Scope
- **Application**: https://practicesoftwaretesting.com/ (Toolshop - Sprint 5 Angular)
- **Modules**: User authentication, product catalog, search/filtering, cart, checkout, order management
- **Browser Support**: Chrome, Firefox, Edge, Safari
- **Test Execution**: Parallel execution (4 threads)
- **Environments**: Local, Staging, Production (read-only)

### 1.3 Not in Scope
- Performance testing under load
- Security penetration testing
- Load/stress testing
- Mobile app testing (web-only)

---

## 2. Test Objectives

### Primary Objectives
✅ Verify all core functionalities work correctly  
✅ Validate user workflows (login → browse → purchase)  
✅ Ensure data integrity across operations  
✅ Identify and document defects  
✅ Ensure regression-free releases  

### Secondary Objectives
✅ Maintain >85% test pass rate  
✅ Execute in <10 minutes (parallel)  
✅ Generate actionable test reports  
✅ Support CI/CD automation  

---

## 3. Test Strategy

### 3.1 Testing Levels
1. **Unit Tests**: Java utility classes (WaitUtils, ExcelUtils, etc.)
2. **Integration Tests**: Page objects + driver interactions
3. **End-to-End Tests**: Complete user workflows
4. **Regression Tests**: All previous passing tests
5. **Negative Tests**: Invalid inputs, error scenarios
6. **Data-Driven Tests**: Multiple data sets via Excel

### 3.2 Testing Types

#### Smoke Tests (5 tests)
- Application loads successfully
- Login/logout functionality
- Basic page navigation
- Search works

#### Functional Tests (15 tests)
- Complete user authentication
- Product catalog browsing
- Product filtering (price, category, brand)
- Search functionality
- Product details verification
- Cart operations
- Checkout process
- PDF invoice download

#### Regression Tests (10+ tests)
- All previously passing tests
- Cross-browser compatibility

#### Negative Tests (5+ tests)
- Invalid login credentials
- Out-of-stock handling
- Incomplete checkout
- Invalid data entry

#### Boundary Tests (3+ tests)
- Max product filters
- Min/max price range
- Large quantity orders
- Long product names

#### Data-Driven Tests (5+ tests)
- Multiple user accounts
- Various product combinations
- Different payment methods

---

## 4. Test Environment

### 4.1 Test Environment Details
```
Application URL: https://practicesoftwaretesting.com/
Test Data Server: Local H2 database (optional)
Browser Versions:
  - Chrome 120+
  - Firefox 121+
  - Edge 120+
  - Safari 17+

OS Support:
  - Windows 10/11
  - macOS 12+
  - Linux (Ubuntu 20.04+)

Java: 17 or higher
Maven: 3.8+
```

### 4.2 Test Accounts
```
Admin Account:
  Email: admin@practicesoftwaretesting.com
  Password: welcome01
  Role: Administrator

Customer Account:
  Email: customer@practicesoftwaretesting.com
  Password: welcome01
  Role: Customer

Guest User: N/A (anonymous)
```

### 4.3 Test Data
```
- Login credentials (3 variations)
- Product data (20+ products)
- Price ranges ($5 - $500+)
- Categories (Tools, Hardware, etc.)
- Brands (5+ brands)
```

---

## 5. Entry Criteria

Before testing begins:
- ✅ Test environment is stable
- ✅ Build passes compilation
- ✅ Test data is available
- ✅ Required dependencies installed
- ✅ Framework initialization successful

---

## 6. Exit Criteria

Testing can be considered complete when:
- ✅ All planned tests executed
- ✅ >85% of tests pass
- ✅ Critical/High priority bugs resolved
- ✅ Regression tests pass
- ✅ Performance baseline met (<10 min for full suite)

---

## 7. Deliverables

### Test Artifacts
1. ✅ Test code (Java)
2. ✅ Test data (Excel files)
3. ✅ Test reports (Allure + ExtentReports)
4. ✅ Configuration files (YAML)
5. ✅ Documentation (Markdown)
6. ✅ CI/CD pipeline configuration

### Documentation
1. ✅ TEST_PLAN.md (this document)
2. ✅ TEST_CASES.md (30+ detailed test cases)
3. ✅ README.md (setup guide)
4. ✅ ARCHITECTURE.md (framework design)
5. ✅ Code comments (inline documentation)

---

## 8. Testing Schedule

### Phase 1: Smoke Tests (1-2 days)
- Basic functionality verification
- Application readiness assessment

### Phase 2: Functional Tests (3-5 days)
- Core functionality testing
- Workflow validation

### Phase 3: Regression Tests (2-3 days)
- Previous tests validation
- Cross-browser testing

### Phase 4: Negative & Boundary Tests (2-3 days)
- Edge case handling
- Error scenario validation

---

## 9. Resource Requirements

### Team
- 1 Test Automation Engineer
- 1 QA Lead (review)
- Optional: 1 Developer (support)

### Tools & Infrastructure
- Local development machine (8GB RAM minimum)
- CI/CD server (Jenkins/GitHub Actions)
- Test reporting server (Allure server)

### Framework Components
- Java 17+ JDK
- Maven 3.8+
- Selenium WebDriver 4.25+
- TestNG 7.8+
- Apache POI 5.2+
- SLF4J + Logback
- Allure Reports

---

## 10. Risk Assessment

### Identified Risks

| Risk | Probability | Impact | Mitigation |
|------|-------------|--------|-----------|
| Flaky tests | Medium | Medium | Retry mechanism, proper waits |
| Parallel execution issues | Low | High | Thread-local drivers, careful synchronization |
| Environment instability | Low | High | Docker containers, environment validation |
| Test data corruption | Low | Medium | Backup & restore scripts |
| Browser compatibility | Medium | Low | Cross-browser matrix |

---

## 11. Assumptions & Dependencies

### Assumptions
- Application is stable and accessible
- Test accounts remain active
- No significant UI changes during testing
- Internet connectivity available
- Required browsers installed

### Dependencies
- Selenium Manager available
- Maven repositories accessible
- GitHub Actions/Jenkins available (for CI/CD)
- Allure server available (for reporting)

---

## 12. Quality Metrics

### Success Criteria
```
Test Pass Rate:           > 85%
Test Execution Time:      < 10 minutes (parallel)
Code Coverage:            > 80% (utility classes)
Defect Escape Rate:       < 5%
Documentation Completeness: 100%
```

### Key Performance Indicators (KPIs)
```
- Tests executed per day
- Defects found per test execution
- Average test execution time
- Parallel efficiency ratio
- Test maintenance cost
```

---

## 13. Approval & Sign-Off

| Role | Name | Signature | Date |
|------|------|-----------|------|
| Test Lead | [Name] | ________ | ________ |
| QA Manager | [Name] | ________ | ________ |
| Project Manager | [Name] | ________ | ________ |

---

## 14. References

### Documentation
- Selenium Documentation: https://www.selenium.dev/documentation/
- TestNG Documentation: https://testng.org/
- Apache POI: https://poi.apache.org/
- Allure Reports: https://docs.qameta.io/allure/

### Related Documents
- TEST_CASES.md - Detailed test case specifications
- ARCHITECTURE.md - Framework architecture details
- README.md - Setup and usage guide

---

## 15. Revision History

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | 04-06-2026 | QA Team | Initial test plan |

---

**Test Plan Status**: ✅ APPROVED  
**Effective Date**: April 6, 2026  
**Next Review**: May 6, 2026

