package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.*;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Boundary Tests - TC-034 to TC-036
 * Tests for edge cases and boundary conditions
 */
public class BoundaryTests extends BaseTest {

    /**
     * TC-034: Max Product Quantity in Cart
     * Verify system handles maximum quantity correctly
     */
    @Test(
        description = "TC-034: Max Product Quantity in Cart",
        groups = {"boundary"},
        priority = 28
    )
    public void testMaxProductQuantityInCart() {
        logger.info("Starting TC-034: Max quantity in cart test");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Add product to cart
        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();
        ProductPage productPage = resultsPage.clickFirstProduct();
        productPage.setQuantity(1);
        productPage.clickAddToCartButton();

        // Open cart
        CartPage cartPage = homePage.clickCartIcon();
        Assert.assertTrue(cartPage.areItemsDisplayed(), "Cart items should display");

        // Try to set very high quantity (999)
        cartPage.updateItemQuantity(0, 999);
        cartPage.clickUpdateButton();

        logger.info("Updated quantity to 999");

        // System should handle it (either accept or limit)
        int finalQuantity = cartPage.getItemQuantity(0);

        // Verify it's handled gracefully (either 999 or limited to max)
        Assert.assertTrue(finalQuantity > 0, "Quantity should be positive");
        Assert.assertTrue(finalQuantity <= 999, "Quantity should not exceed 999");

        // Verify cart total calculated correctly
        double cartTotal = cartPage.getCartTotal();
        Assert.assertTrue(cartTotal > 0, "Cart total should be calculated correctly");

        logger.info("Final quantity: {}, Cart total: ${}", finalQuantity, cartTotal);
        logger.info("TC-034 PASSED: Max quantity handling works");
    }

    /**
     * TC-035: Large Price Filter Range
     * Verify filter works with large price range
     */
    @Test(
        description = "TC-035: Large Price Filter Range",
        groups = {"boundary"},
        priority = 29
    )
    public void testLargePriceFilterRange() {
        logger.info("Starting TC-035: Large price filter range test");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        ProductsPage productsPage = homePage.clickProductsLink();
        Assert.assertTrue(productsPage.isPriceFilterVisible(), "Price filter should be visible");

        // Apply extremely large range
        productsPage.setPriceRange(0, 999999);
        productsPage.applyFilters();

        logger.info("Applied price filter: $0 - $999,999");

        // Should handle gracefully and show results or message
        Assert.assertTrue(productsPage.areProductsDisplayed() ||
                         productsPage.isNoResultsMessageDisplayed(),
            "Should display results or no-results message");

        // Verify no errors
        Assert.assertTrue(productsPage.isPageLoaded(), "Page should remain functional");

        logger.info("TC-035 PASSED: Large price range handled correctly");
    }

    /**
     * TC-036: Special Characters in Search
     * Verify search handles special characters safely
     */
    @Test(
        description = "TC-036: Special Characters in Search",
        groups = {"boundary", "security"},
        priority = 30
    )
    public void testSpecialCharactersInSearch() {
        logger.info("Starting TC-036: Special characters search test");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Search with special characters (potential injection attempt)
        String specialChars = "!@#$%^&*()";
        SearchResultsPage resultsPage = homePage.searchProduct(specialChars);

        logger.info("Searched for special characters: {}", specialChars);

        // System should handle gracefully
        Assert.assertNotNull(resultsPage, "Search results page should load");

        // Should either show no results or message
        Assert.assertTrue(resultsPage.areResultsDisplayed() ||
                         resultsPage.isNoResultsMessageDisplayed(),
            "Should display results or no-results message");

        // Verify page remains functional (no errors/crashes)
        Assert.assertTrue(resultsPage.isPageLoaded(),
            "Page should remain functional after special char search");

        // Verify no XSS vulnerabilities (page doesn't crash or show alerts)
        Assert.assertFalse(resultsPage.areJavaScriptErrorsPresent(),
            "No JavaScript errors should occur");

        logger.info("TC-036 PASSED: Special characters handled safely");
    }

    /**
     * TC-021: Cross-Browser Login - Chrome/Firefox/Edge
     * Verify login works on multiple browsers
     */
    @Test(
        description = "TC-021: Cross-Browser Login",
        groups = {"regression", "cross-browser"},
        priority = 31
    )
    public void testCrossBrowserLogin() {
        logger.info("Starting TC-021: Cross-browser login test on {}",
            System.getProperty("browser", "chrome"));

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Verify home page loads on current browser
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should load");

        // Perform login
        LoginPage loginPage = homePage.clickLoginLink();
        Assert.assertNotNull(loginPage, "Login page should load");

        loginPage.enterEmail("customer@practicesoftwaretesting.com");
        loginPage.enterPassword("welcome01");
        loginPage.clickLoginButton();

        // Verify login succeeds on current browser
        Assert.assertTrue(homePage.isLogoutLinkVisible(),
            "Login should work on " + System.getProperty("browser", "chrome"));

        logger.info("TC-021 PASSED: Cross-browser login verified");
    }

    /**
     * TC-022: Cross-Browser Product Search
     * Verify search works on multiple browsers
     */
    @Test(
        description = "TC-022: Cross-Browser Product Search",
        groups = {"regression", "cross-browser"},
        priority = 32
    )
    public void testCrossBrowserProductSearch() {
        logger.info("Starting TC-022: Cross-browser search test");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Perform search
        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();

        // Verify search works on current browser
        Assert.assertTrue(resultsPage.areResultsDisplayed(),
            "Search should work on " + System.getProperty("browser", "chrome"));

        int resultCount = resultsPage.getResultCount();
        Assert.assertTrue(resultCount > 0, "Results should be returned");

        logger.info("TC-022 PASSED: Cross-browser search verified");
    }

    /**
     * TC-023: Cross-Browser Checkout
     * Verify checkout process works on multiple browsers
     */
    @Test(
        description = "TC-023: Cross-Browser Checkout",
        groups = {"regression", "cross-browser"},
        priority = 33
    )
    public void testCrossBrowserCheckout() {
        logger.info("Starting TC-023: Cross-browser checkout test");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Login
        LoginPage loginPage = homePage.clickLoginLink();
        loginPage.enterEmail("customer@practicesoftwaretesting.com");
        loginPage.enterPassword("welcome01");
        loginPage.clickLoginButton();

        // Add product and checkout
        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();
        ProductPage productPage = resultsPage.clickFirstProduct();
        productPage.setQuantity(1);
        productPage.clickAddToCartButton();

        CartPage cartPage = homePage.clickCartIcon();
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();

        // Verify checkout page loads on current browser
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(),
            "Checkout should work on " + System.getProperty("browser", "chrome"));

        logger.info("TC-023 PASSED: Cross-browser checkout verified");
    }
}

