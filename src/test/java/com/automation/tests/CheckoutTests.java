package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.*;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Checkout Tests - TC-017 and Order Tests - TC-018
 * Tests for checkout process and order management
 */
public class CheckoutTests extends BaseTest {

    /**
     * TC-017: Checkout Process - Complete
     * Verify complete checkout flow from cart to order confirmation
     */
    @Test(
        description = "TC-017: Checkout Process - Complete",
        groups = {"functional", "critical"},
        priority = 12
    )
    public void testCompleteCheckoutProcess() {
        logger.info("Starting TC-017: Complete checkout process");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Step 1: Login
        logger.info("Step 1: Login");
        LoginPage loginPage = homePage.clickLoginLink();
        loginPage.enterEmail("customer@practicesoftwaretesting.com");
        loginPage.enterPassword("welcome01");
        loginPage.clickLoginButton();

        Assert.assertTrue(homePage.isLogoutLinkVisible(),
            "User should be logged in");

        // Step 2: Add product to cart
        logger.info("Step 2: Add product to cart");
        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();
        ProductPage productPage = resultsPage.clickFirstProduct();
        productPage.setQuantity(2);
        productPage.clickAddToCartButton();

        // Step 3: Go to cart
        logger.info("Step 3: Navigate to cart");
        CartPage cartPage = homePage.clickCartIcon();
        Assert.assertTrue(cartPage.areItemsDisplayed(),
            "Cart items should be displayed");

        // Step 4: Checkout
        logger.info("Step 4: Start checkout");
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
        Assert.assertTrue(checkoutPage.isCheckoutPageLoaded(),
            "Checkout page should load");

        // Step 5: Fill billing address
        logger.info("Step 5: Fill billing address");
        checkoutPage.fillFirstName("John");
        checkoutPage.fillLastName("Doe");
        checkoutPage.fillAddress("123 Main Street");
        checkoutPage.fillCity("Springfield");
        checkoutPage.fillState("IL");
        checkoutPage.fillPostalCode("62701");
        checkoutPage.selectCountry("United States");

        // Step 6: Fill shipping address (same as billing)
        logger.info("Step 6: Fill shipping address");
        checkoutPage.clickSameAsShippingCheckbox();

        // Step 7: Select payment method
        logger.info("Step 7: Select payment method");
        checkoutPage.selectPaymentMethod("Credit Card");

        // Step 8: Fill payment details
        logger.info("Step 8: Fill payment details");
        checkoutPage.fillCardNumber("4111111111111111");
        checkoutPage.fillCardExpiry("12/25");
        checkoutPage.fillCardCVC("123");

        // Step 9: Place order
        logger.info("Step 9: Place order");
        OrderConfirmationPage confirmationPage = checkoutPage.clickPlaceOrderButton();
        Assert.assertNotNull(confirmationPage, "Order confirmation page should load");

        // Step 10: Verify order confirmation
        logger.info("Step 10: Verify order confirmation");
        Assert.assertTrue(confirmationPage.isOrderConfirmationDisplayed(),
            "Order confirmation should be displayed");

        String orderNumber = confirmationPage.getOrderNumber();
        Assert.assertNotNull(orderNumber, "Order number should be generated");
        logger.info("Order created successfully: {}", orderNumber);

        double orderTotal = confirmationPage.getOrderTotal();
        Assert.assertTrue(orderTotal > 0, "Order total should be greater than 0");

        logger.info("TC-017 PASSED: Checkout process completed successfully");
    }

    /**
     * TC-018: Download Invoice as PDF
     * Verify PDF invoice download functionality
     */
    @Test(
        description = "TC-018: Download Invoice as PDF",
        groups = {"functional"},
        priority = 13,
        dependsOnMethods = "testCompleteCheckoutProcess"
    )
    public void testDownloadInvoiceAsPDF() {
        logger.info("Starting TC-018: Download invoice as PDF");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Login
        LoginPage loginPage = homePage.clickLoginLink();
        loginPage.enterEmail("customer@practicesoftwaretesting.com");
        loginPage.enterPassword("welcome01");
        loginPage.clickLoginButton();

        // Navigate to Orders page
        OrdersPage ordersPage = homePage.clickOrdersLink();
        Assert.assertTrue(ordersPage.isOrdersPageLoaded(),
            "Orders page should load");

        // Get most recent order
        Assert.assertTrue(ordersPage.areOrdersDisplayed(),
            "Orders should be displayed");

        // Click on recent order
        OrderDetailsPage orderDetailsPage = ordersPage.clickMostRecentOrder();
        Assert.assertTrue(orderDetailsPage.isOrderDetailsLoaded(),
            "Order details should load");

        // Get order information
        String orderNumber = orderDetailsPage.getOrderNumber();
        logger.info("Opening order: {}", orderNumber);

        // Download invoice
        String downloadPath = orderDetailsPage.downloadInvoice();
        Assert.assertNotNull(downloadPath, "Invoice should be downloaded");

        logger.info("Invoice downloaded to: {}", downloadPath);

        // Verify PDF contains order details
        boolean pdfValid = orderDetailsPage.verifyPDFContent(downloadPath);
        Assert.assertTrue(pdfValid, "PDF should contain valid order details");

        logger.info("TC-018 PASSED: Invoice downloaded successfully");
    }

    /**
     * TC-032: Checkout without Shipping Address
     * Verify validation for required fields
     */
    @Test(
        description = "TC-032: Checkout without Shipping Address",
        groups = {"negative"},
        priority = 14
    )
    public void testCheckoutWithoutShippingAddress() {
        logger.info("Starting TC-032: Checkout validation");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Login and add product
        LoginPage loginPage = homePage.clickLoginLink();
        loginPage.enterEmail("customer@practicesoftwaretesting.com");
        loginPage.enterPassword("welcome01");
        loginPage.clickLoginButton();

        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();
        ProductPage productPage = resultsPage.clickFirstProduct();
        productPage.setQuantity(1);
        productPage.clickAddToCartButton();

        // Go to checkout
        CartPage cartPage = homePage.clickCartIcon();
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();

        // Fill only billing address, skip shipping
        checkoutPage.fillFirstName("John");
        checkoutPage.fillLastName("Doe");
        checkoutPage.fillAddress("123 Main Street");
        checkoutPage.fillCity("Springfield");
        checkoutPage.fillState("IL");
        checkoutPage.fillPostalCode("62701");

        // Try to continue without shipping address
        checkoutPage.clickContinueButton();

        // Verify validation error
        Assert.assertTrue(checkoutPage.isValidationErrorDisplayed(),
            "Validation error should be displayed for missing shipping address");

        String errorMessage = checkoutPage.getValidationErrorMessage();
        Assert.assertNotNull(errorMessage, "Error message should be provided");
        logger.info("Validation error: {}", errorMessage);

        logger.info("TC-032 PASSED: Validation works correctly");
    }

    /**
     * TC-033: Submit Empty Search
     * Verify graceful handling of empty search
     */
    @Test(
        description = "TC-033: Submit Empty Search",
        groups = {"negative"},
        priority = 15
    )
    public void testSubmitEmptySearch() {
        logger.info("Starting TC-033: Empty search handling");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Verify search bar visible
        Assert.assertTrue(homePage.isSearchInputVisible(),
            "Search bar should be visible");

        // Submit empty search
        SearchResultsPage resultsPage = homePage.searchProduct("");

        // Verify graceful handling
        Assert.assertNotNull(resultsPage, "Should handle empty search gracefully");

        // Should either show all products or message
        boolean showsAllOrMessage = resultsPage.areResultsDisplayed() ||
                                   resultsPage.isNoSearchTermMessageDisplayed();
        Assert.assertTrue(showsAllOrMessage,
            "Should show all products or 'no search term' message");

        logger.info("TC-033 PASSED: Empty search handled gracefully");
    }
}

