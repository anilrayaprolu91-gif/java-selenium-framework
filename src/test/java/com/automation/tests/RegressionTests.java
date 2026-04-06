package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.*;
import com.automation.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Regression Tests - TC-019 to TC-028
 * Tests to ensure previously passing functionality continues to work
 */
public class RegressionTests extends BaseTest {

    protected WaitUtils wait;

    @org.testng.annotations.BeforeMethod
    public void setUpRegressionTests() {
        wait = new WaitUtils(driver);
    }

    /**
     * TC-019: All Smoke Tests Pass
     * Meta-test to verify smoke test suite passes
     */
    @Test(
        description = "TC-019: All Smoke Tests Pass",
        groups = {"regression", "smoke"},
        priority = 21
    )
    public void testAllSmokeTestsPass() {
        logger.info("Starting TC-019: Smoke tests validation");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Application loads
        Assert.assertTrue(homePage.isPageLoaded(),
            "Home page should load");

        // Login accessible
        LoginPage loginPage = homePage.clickLoginLink();
        Assert.assertNotNull(loginPage, "Login page should be accessible");

        // Search accessible
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isSearchInputVisible(),
            "Search should be accessible");

        // Products browsable
        ProductsPage productsPage = homePage.clickProductsLink();
        Assert.assertTrue(productsPage.areProductsDisplayed(),
            "Products should be browsable");

        logger.info("TC-019 PASSED: All smoke tests validated");
    }

    /**
     * TC-020: All Functional Tests Pass
     * Meta-test to verify functional test suite passes
     */
    @Test(
        description = "TC-020: All Functional Tests Pass",
        groups = {"regression", "functional"},
        priority = 22
    )
    public void testAllFunctionalTestsPass() {
        logger.info("Starting TC-020: Functional tests validation");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Search functionality
        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();
        Assert.assertTrue(resultsPage.areResultsDisplayed(),
            "Search should work");

        // Product details
        ProductPage productPage = resultsPage.clickFirstProduct();
        Assert.assertTrue(productPage.isProductPageLoaded(),
            "Product details should load");

        // Login/Logout
        LoginPage loginPage = homePage.clickLoginLink();
        loginPage.enterEmail("customer@practicesoftwaretesting.com");
        loginPage.enterPassword("welcome01");
        loginPage.clickLoginButton();
        Assert.assertTrue(homePage.isLogoutLinkVisible(),
            "Login should work");

        logger.info("TC-020 PASSED: Functional tests validated");
    }

    /**
     * TC-025: Performance Baseline
     * Verify page load performance meets baseline
     */
    @Test(
        description = "TC-025: Performance Baseline",
        groups = {"regression", "performance"},
        priority = 23
    )
    public void testPerformanceBaseline() {
        logger.info("Starting TC-025: Performance baseline check");

        HomePage homePage = new HomePage(driver);

        long startTime = System.currentTimeMillis();
        homePage.navigateTo();
        long loadTime = System.currentTimeMillis() - startTime;

        logger.info("Page load time: {} ms", loadTime);

        // Should load in less than 5 seconds
        Assert.assertTrue(loadTime < 5000,
            "Page should load in less than 5 seconds");

        logger.info("TC-025 PASSED: Performance baseline met");
    }

    /**
     * TC-026: Data Integrity - Product Info
     * Verify product data remains consistent
     */
    @Test(
        description = "TC-026: Data Integrity - Product Info",
        groups = {"regression", "data-integrity"},
        priority = 24
    )
    public void testDataIntegrityProductInfo() {
        logger.info("Starting TC-026: Product data integrity check");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();
        ProductPage productPage1 = resultsPage.clickFirstProduct();

        String title1 = productPage1.getProductTitle();
        String price1 = productPage1.getProductPrice();

        logger.info("First load - Title: {}, Price: {}", title1, price1);

        // Reload page
        driver.navigate().refresh();
        wait.waitForPageLoad();

        ProductPage productPage2 = new ProductPage(driver);
        String title2 = productPage2.getProductTitle();
        String price2 = productPage2.getProductPrice();

        logger.info("After refresh - Title: {}, Price: {}", title2, price2);

        // Verify data unchanged
        Assert.assertEquals(title2, title1, "Product title should remain same");
        Assert.assertEquals(price2, price1, "Product price should remain same");

        logger.info("TC-026 PASSED: Product data integrity verified");
    }

    /**
     * TC-027: Session Persistence
     * Verify user session persists across navigation
     */
    @Test(
        description = "TC-027: Session Persistence",
        groups = {"regression", "session"},
        priority = 25
    )
    public void testSessionPersistence() {
        logger.info("Starting TC-027: Session persistence check");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Login
        LoginPage loginPage = homePage.clickLoginLink();
        loginPage.enterEmail("customer@practicesoftwaretesting.com");
        loginPage.enterPassword("welcome01");
        loginPage.clickLoginButton();

        Assert.assertTrue(homePage.isLogoutLinkVisible(), "User should be logged in");

        // Navigate around
        ProductsPage productsPage = homePage.clickProductsLink();

        // Return to home
        homePage = (HomePage) productsPage.clickLogoLink();

        // Verify still logged in
        Assert.assertTrue(homePage.isLogoutLinkVisible(),
            "Session should persist after navigation");

        // Refresh page
        driver.navigate().refresh();
        wait.waitForPageLoad();

        // Verify still logged in
        Assert.assertTrue(homePage.isLogoutLinkVisible(),
            "Session should persist after page refresh");

        logger.info("TC-027 PASSED: Session persistence verified");
    }

    /**
     * TC-028: Cart Persistence
     * Verify cart contents persist across pages
     */
    @Test(
        description = "TC-028: Cart Persistence",
        groups = {"regression", "cart"},
        priority = 26
    )
    public void testCartPersistence() {
        logger.info("Starting TC-028: Cart persistence check");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Add product to cart
        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();
        ProductPage productPage = resultsPage.clickFirstProduct();
        productPage.setQuantity(2);
        productPage.clickAddToCartButton();

        int initialCartCount = homePage.getCartItemCount();
        logger.info("Initial cart count: {}", initialCartCount);

        // Navigate away
        ProductsPage productsPage = homePage.clickProductsLink();

        // Return and check cart
        homePage = (HomePage) productsPage.clickLogoLink();
        int cartCountAfterNav = homePage.getCartItemCount();

        Assert.assertEquals(cartCountAfterNav, initialCartCount,
            "Cart count should persist after navigation");

        // Refresh page
        driver.navigate().refresh();
        wait.waitForPageLoad();

        int cartCountAfterRefresh = homePage.getCartItemCount();
        Assert.assertEquals(cartCountAfterRefresh, initialCartCount,
            "Cart count should persist after page refresh");

        logger.info("TC-028 PASSED: Cart persistence verified");
    }

    /**
     * TC-024: UI Elements Consistency
     * Verify UI elements are consistent across pages
     */
    @Test(
        description = "TC-024: UI Elements Consistency",
        groups = {"regression", "ui"},
        priority = 27
    )
    public void testUIElementsConsistency() {
        logger.info("Starting TC-024: UI consistency check");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Check home page header
        Assert.assertTrue(homePage.isLogoVisible(), "Logo should be visible on home");
        Assert.assertTrue(homePage.isNavigationMenuVisible(), "Navigation should be visible");
        Assert.assertTrue(homePage.isSearchInputVisible(), "Search should be visible");

        // Check products page
        ProductsPage productsPage = homePage.clickProductsLink();
        Assert.assertTrue(productsPage.isLogoVisible(), "Logo should be visible on products page");
        Assert.assertTrue(productsPage.isNavigationMenuVisible(), "Navigation should be visible on products");

        logger.info("TC-024 PASSED: UI consistency verified");
    }
}

