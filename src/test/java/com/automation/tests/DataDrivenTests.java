package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.*;
import com.automation.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Data-Driven Tests - TC-037 to TC-041
 * Tests using Excel-based data providers for parameterized testing
 */
public class DataDrivenTests extends BaseTest {

    /**
     * TC-037: Login with Multiple Credentials (Data-Driven)
     * Verify login with multiple user credentials from Excel
     */
    @Test(
        description = "TC-037: Login with Multiple Credentials (Data-Driven)",
        groups = {"data-driven", "functional"},
        priority = 16,
        dataProvider = "loginCredentials"
    )
    public void testLoginWithMultipleCredentials(Map<String, String> testData) {
        logger.info("Starting TC-037: Login with credentials - {}", testData.get("Email"));

        String email = testData.get("Email");
        String password = testData.get("Password");
        String expectedResult = testData.get("ExpectedResult");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        LoginPage loginPage = homePage.clickLoginLink();
        Assert.assertNotNull(loginPage, "Login page should load");

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();

        if ("Success".equalsIgnoreCase(expectedResult)) {
            // Verify successful login
            Assert.assertTrue(homePage.isLogoutLinkVisible(),
                "User should be logged in for: " + email);
            logger.info("Login successful for: {}", email);
        } else {
            // Verify login failure
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for: " + email);
            logger.info("Login failed as expected for: {}", email);
        }

        logger.info("TC-037 PASSED: Data-driven login test completed");
    }

    @DataProvider(name = "loginCredentials")
    public Object[][] getLoginCredentials() {
        String filePath = "src/test/resources/test-data/login_data.xlsx";
        ExcelUtils excelUtils = new ExcelUtils(filePath, "ValidUsers");
        List<Map<String, String>> data = excelUtils.readAllData();

        return data.stream()
            .map(row -> new Object[]{row})
            .toArray(Object[][]::new);
    }

    /**
     * TC-038: Product Search with Multiple Terms (Data-Driven)
     * Verify search functionality with multiple search terms
     */
    @Test(
        description = "TC-038: Product Search with Multiple Terms (Data-Driven)",
        groups = {"data-driven", "functional"},
        priority = 17,
        dataProvider = "searchData"
    )
    public void testProductSearchWithMultipleTerms(Map<String, String> testData) {
        logger.info("Starting TC-038: Search for - {}", testData.get("SearchTerm"));

        String searchTerm = testData.get("SearchTerm");
        int minResults = Integer.parseInt(testData.getOrDefault("MinResults", "0"));
        int maxResults = Integer.parseInt(testData.getOrDefault("MaxResults", "1000"));

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        SearchResultsPage resultsPage = homePage.searchProduct(searchTerm);
        Assert.assertNotNull(resultsPage, "Search results page should load");

        resultsPage.waitForResultsToLoad();

        int resultCount = resultsPage.getResultCount();
        logger.info("Search for '{}' returned {} results", searchTerm, resultCount);

        // Verify result count within range
        Assert.assertTrue(resultCount >= minResults && resultCount <= maxResults,
            String.format("Result count %d should be between %d and %d",
                resultCount, minResults, maxResults));

        logger.info("TC-038 PASSED: Search data-driven test completed");
    }

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        String filePath = "src/test/resources/test-data/search_data.xlsx";
        ExcelUtils excelUtils = new ExcelUtils(filePath, "SearchTerms");
        List<Map<String, String>> data = excelUtils.readAllData();

        return data.stream()
            .map(row -> new Object[]{row})
            .toArray(Object[][]::new);
    }

    /**
     * TC-039: Add to Cart with Quantity Variations (Data-Driven)
     * Verify cart operations with different quantities
     */
    @Test(
        description = "TC-039: Add to Cart with Quantity Variations (Data-Driven)",
        groups = {"data-driven", "functional"},
        priority = 18,
        dataProvider = "cartData"
    )
    public void testAddToCartWithQuantityVariations(Map<String, String> testData) {
        logger.info("Starting TC-039: Add to cart - Product: {}, Qty: {}",
            testData.get("Product"), testData.get("Quantity"));

        String product = testData.get("Product");
        int quantity = Integer.parseInt(testData.get("Quantity"));
        double expectedTotal = Double.parseDouble(testData.get("ExpectedTotal"));

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Search and add to cart
        SearchResultsPage resultsPage = homePage.searchProduct(product);
        resultsPage.waitForResultsToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
            "Search results should be displayed");

        ProductPage productPage = resultsPage.clickFirstProduct();
        productPage .setQuantity(quantity);
        productPage.clickAddToCartButton();

        // Verify cart
        CartPage cartPage = homePage.clickCartIcon();
        double cartTotal = cartPage.getCartTotal();

        // Verify total (with some tolerance for rounding)
        Assert.assertTrue(Math.abs(cartTotal - expectedTotal) < 0.01,
            String.format("Cart total %.2f should match expected %.2f",
                cartTotal, expectedTotal));

        logger.info("TC-039 PASSED: Cart quantity variation test completed");
    }

    @DataProvider(name = "cartData")
    public Object[][] getCartData() {
        String filePath = "src/test/resources/test-data/cart_data.xlsx";
        ExcelUtils excelUtils = new ExcelUtils(filePath, "CartItems");
        List<Map<String, String>> data = excelUtils.readAllData();

        return data.stream()
            .map(row -> new Object[]{row})
            .toArray(Object[][]::new);
    }

    /**
     * TC-040: Checkout with Different Addresses (Data-Driven)
     * Verify checkout with various address combinations
     */
    @Test(
        description = "TC-040: Checkout with Different Addresses (Data-Driven)",
        groups = {"data-driven", "functional"},
        priority = 19,
        dataProvider = "addressData"
    )
    public void testCheckoutWithDifferentAddresses(Map<String, String> testData) {
        logger.info("Starting TC-040: Checkout with address - {}", testData.get("FirstName"));

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Login
        LoginPage loginPage = homePage.clickLoginLink();
        loginPage.enterEmail("customer@practicesoftwaretesting.com");
        loginPage.enterPassword("welcome01");
        loginPage.clickLoginButton();

        // Add product to cart
        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();
        ProductPage productPage = resultsPage.clickFirstProduct();
        productPage.setQuantity(1);
        productPage.clickAddToCartButton();

        // Start checkout
        CartPage cartPage = homePage.clickCartIcon();
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();

        // Fill address from test data
        checkoutPage.fillFirstName(testData.get("FirstName"));
        checkoutPage.fillLastName(testData.get("LastName"));
        checkoutPage.fillAddress(testData.get("Address"));
        checkoutPage.fillCity(testData.get("City"));
        checkoutPage.fillState(testData.get("State"));
        checkoutPage.fillPostalCode(testData.get("ZIP"));
        checkoutPage.fillCountry(testData.getOrDefault("Country", "United States"));

        // Complete checkout
        checkoutPage.clickSameAsShippingCheckbox();
        checkoutPage.selectPaymentMethod("Credit Card");
        checkoutPage.fillCardNumber("4111111111111111");
        checkoutPage.fillCardExpiry("12/25");
        checkoutPage.fillCardCVC("123");

        OrderConfirmationPage confirmationPage = checkoutPage.clickPlaceOrderButton();

        // Verify order created with correct address
        Assert.assertTrue(confirmationPage.isOrderConfirmationDisplayed(),
            "Order should be created with address from: " + testData.get("FirstName"));

        String orderNumber = confirmationPage.getOrderNumber();
        Assert.assertNotNull(orderNumber, "Order number should be generated");

        logger.info("TC-040 PASSED: Checkout address variation test completed");
    }

    @DataProvider(name = "addressData")
    public Object[][] getAddressData() {
        String filePath = "src/test/resources/test-data/address_data.xlsx";
        ExcelUtils excelUtils = new ExcelUtils(filePath, "Addresses");
        List<Map<String, String>> data = excelUtils.readAllData();

        return data.stream()
            .map(row -> new Object[]{row})
            .toArray(Object[][]::new);
    }

    /**
     * TC-041: Filter Products with Multiple Criteria (Data-Driven)
     * Verify filtering with multiple criteria combinations
     */
    @Test(
        description = "TC-041: Filter Products with Multiple Criteria (Data-Driven)",
        groups = {"data-driven", "functional"},
        priority = 20,
        dataProvider = "filterData"
    )
    public void testFilterProductsWithMultipleCriteria(Map<String, String> testData) {
        logger.info("Starting TC-041: Filter with - Category: {}, Price: {} - {}",
            testData.get("Category"), testData.get("MinPrice"), testData.get("MaxPrice"));

        String category = testData.get("Category");
        int minPrice = Integer.parseInt(testData.get("MinPrice"));
        int maxPrice = Integer.parseInt(testData.get("MaxPrice"));
        String brand = testData.getOrDefault("Brand", "Any");
        int expectedMinResults = Integer.parseInt(testData.getOrDefault("ExpectedResults", "1"));

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        ProductsPage productsPage = homePage.clickProductsLink();

        // Apply filters
        if (!category.equals("Any")) {
            productsPage.selectCategory(category);
        }
        productsPage.setPriceRange(minPrice, maxPrice);
        if (!brand.equals("Any")) {
            productsPage.selectBrand(brand);
        }

        productsPage.applyFilters();
        logger.info("Filters applied: Category={}, Price=${}-${}", category, minPrice, maxPrice);

        // Verify results
        Assert.assertTrue(productsPage.areProductsDisplayed() ||
                         productsPage.isNoResultsMessageDisplayed(),
            "Should display results or no-results message");

        int resultCount = productsPage.getProductCount();
        Assert.assertTrue(resultCount >= expectedMinResults,
            String.format("Should have at least %d results, got %d",
                expectedMinResults, resultCount));

        // Verify all products match filter criteria
        Assert.assertTrue(productsPage.areAllProductsWithinPriceRange(minPrice, maxPrice),
            "All products should be within price range");

        if (!category.equals("Any")) {
            Assert.assertTrue(productsPage.areAllProductsInCategory(category),
                "All products should be in selected category");
        }

        logger.info("TC-041 PASSED: Filter variation test completed");
    }

    @DataProvider(name = "filterData")
    public Object[][] getFilterData() {
        String filePath = "src/test/resources/test-data/filter_data.xlsx";
        ExcelUtils excelUtils = new ExcelUtils(filePath, "Filters");
        List<Map<String, String>> data = excelUtils.readAllData();

        return data.stream()
            .map(row -> new Object[]{row})
            .toArray(Object[][]::new);
    }
}

