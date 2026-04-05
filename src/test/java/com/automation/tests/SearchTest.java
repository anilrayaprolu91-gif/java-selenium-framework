package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * SearchTest class - Contains test cases for product search functionality
 * Tests the search and product detail verification workflow
 */
public class SearchTest extends BaseTest {

    /**
     * Test Case: Search for a product and verify product details
     *
     * Test Steps:
     * 1. Navigate to the home page (done in BaseTest.setUp())
     * 2. Search for "Hammer"
     * 3. Select the first product from search results
     * 4. Verify the product title contains "Hammer"
     * 5. Verify product page is loaded with price and description
     */
    @Test(description = "Search for Hammer product and verify product title on detail page")
    public void testSearchHammerAndVerifyProductTitle() {
        // Step 1: Initialize HomePage object
        HomePage homePage = new HomePage(driver);

        // Step 2: Verify that we are on the home page and search functionality is available
        Assert.assertTrue(homePage.isSearchInputVisible(), "Search input should be visible on home page");

        // Step 3: Search for "Hammer"
        homePage.searchProduct("Hammer");

        // Step 4: Wait for search results to be displayed and verify products are found
        Assert.assertTrue(homePage.areProductsDisplayed(), "Products should be displayed after search");
        int productCount = homePage.getProductCount();
        Assert.assertTrue(productCount > 0, "At least one product should be found when searching for 'Hammer'");

        // Step 5: Get the first product name to display in report
        String firstProductName = homePage.getFirstProductName();
        System.out.println("First product found: " + firstProductName);

        // Step 6: Click on the first product in the search results
        homePage.clickFirstProduct();

        // Step 7: Wait for the product detail page to load
        ProductPage productPage = new ProductPage(driver);
        Assert.assertTrue(productPage.isProductPageLoaded(), "Product page should be fully loaded with title and price");

        // Step 8: Get the product title from the detail page
        String productTitle = productPage.getProductTitle();
        System.out.println("Product Title: " + productTitle);

        // Step 9: Assert that the product title contains "Hammer"
        Assert.assertTrue(
                productPage.verifyProductTitleContains("Hammer"),
                "Product title should contain 'Hammer'. Actual title: " + productTitle
        );

        // Step 10: Additional assertions to verify complete product page
        Assert.assertTrue(productPage.isProductPriceDisplayed(), "Product price should be displayed");
        Assert.assertTrue(productPage.isProductDescriptionDisplayed(), "Product description should be displayed");

        // Step 11: Verify that price is a valid number
        double price = productPage.getProductPriceAsDouble();
        Assert.assertTrue(price > 0, "Product price should be greater than 0. Actual price: $" + price);

        // Step 12: Print full product details for verification
        System.out.println(productPage.getProductDetails());
    }

    /**
     * Test Case: Verify that Add to Cart button is available on product page
     *
     * Test Steps:
     * 1. Search for "Hammer"
     * 2. Click on the first product
     * 3. Verify the Add to Cart button is visible and clickable
     */
    @Test(description = "Verify Add to Cart button is visible on product detail page")
    public void testAddToCartButtonIsVisible() {
        // Step 1: Initialize HomePage object
        HomePage homePage = new HomePage(driver);

        // Step 2: Search for a product
        homePage.searchProduct("Hammer");

        // Step 3: Verify search results are displayed
        Assert.assertTrue(homePage.areProductsDisplayed(), "Search results should be displayed");

        // Step 4: Click on the first product
        homePage.clickFirstProduct();

        // Step 5: Initialize ProductPage object
        ProductPage productPage = new ProductPage(driver);

        // Step 6: Verify product page is loaded
        Assert.assertTrue(productPage.isProductPageLoaded(), "Product page should be loaded");

        // Step 7: Verify Add to Cart button is visible
        Assert.assertTrue(
                productPage.isAddToCartButtonVisible(),
                "Add to Cart button should be visible on product detail page"
        );

        // Step 8: Verify Add to Favorites button is visible
        Assert.assertTrue(
                productPage.isAddToFavoritesButtonVisible(),
                "Add to Favorites button should be visible on product detail page"
        );
    }

    /**
     * Test Case: Verify product price is displayed correctly
     *
     * Test Steps:
     * 1. Search for "Hammer"
     * 2. Click on the first product
     * 3. Verify that product price is displayed and is a valid number
     */
    @Test(description = "Verify product price is displayed and is valid")
    public void testVerifyProductPrice() {
        // Step 1: Initialize HomePage object
        HomePage homePage = new HomePage(driver);

        // Step 2: Search for "Hammer"
        homePage.searchProduct("Hammer");

        // Step 3: Verify search results
        Assert.assertTrue(homePage.areProductsDisplayed(), "Search results should be displayed");

        // Step 4: Click on the first product
        homePage.clickFirstProduct();

        // Step 5: Initialize ProductPage object
        ProductPage productPage = new ProductPage(driver);

        // Step 6: Verify product page is loaded
        Assert.assertTrue(productPage.isProductPageLoaded(), "Product page should be loaded");

        // Step 7: Get and verify product price
        String priceText = productPage.getProductPrice();
        Assert.assertNotNull(priceText, "Product price should not be null");
        Assert.assertFalse(priceText.isEmpty(), "Product price should not be empty");

        // Step 8: Verify price is a valid number
        double price = productPage.getProductPriceAsDouble();
        Assert.assertTrue(price > 0, "Product price should be a positive number");

        System.out.println("Product Price: $" + price);
    }
}

