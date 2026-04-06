package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.*;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Product Tests - TC-009 to TC-013
 * Tests for product browsing, searching, filtering, and details
 */
public class ProductTests extends BaseTest {

    /**
     * TC-009: Browse All Products
     * Verify that user can view all products
     */
    @Test(
        description = "TC-009: Browse All Products",
        groups = {"smoke", "functional"},
        priority = 4
    )
    public void testBrowseAllProducts() {
        logger.info("Starting TC-009: Browse all products");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Navigate to Products
        ProductsPage productsPage = homePage.clickProductsLink();
        Assert.assertNotNull(productsPage, "Products page should load");

        // Verify products displayed
        Assert.assertTrue(productsPage.areProductsDisplayed(),
            "Products should be displayed");

        int productCount = productsPage.getProductCount();
        Assert.assertTrue(productCount > 0,
            "At least one product should be visible");

        logger.info("Found {} products", productCount);

        // Verify pagination
        Assert.assertTrue(productsPage.isPaginationVisible() || productCount < 20,
            "Pagination should be visible if many products");

        logger.info("TC-009 PASSED: All products browsing successful");
    }

    /**
     * TC-010: Search for Product by Name
     * Verify search functionality for products
     */
    @Test(
        description = "TC-010: Search for Product by Name",
        groups = {"functional"},
        priority = 5,
        dataProvider = "searchTerms"
    )
    public void testSearchForProductByName(String searchTerm) {
        logger.info("Starting TC-010: Search for product - {}", searchTerm);

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Verify search bar visible
        Assert.assertTrue(homePage.isSearchInputVisible(),
            "Search input should be visible");

        // Search for product
        SearchResultsPage resultsPage = homePage.searchProduct(searchTerm);
        Assert.assertNotNull(resultsPage, "Search results page should load");

        // Wait for results
        resultsPage.waitForResultsToLoad();

        // Verify results displayed
        Assert.assertTrue(resultsPage.areResultsDisplayed(),
            "Search results should be displayed");

        int resultCount = resultsPage.getResultCount();
        logger.info("Search returned {} results", resultCount);

        if (resultCount > 0) {
            // Verify all results contain search term
            Assert.assertTrue(resultsPage.doAllResultsContainTerm(searchTerm),
                "All results should contain search term");
        }

        logger.info("TC-010 PASSED: Search functionality works");
    }

    @DataProvider(name = "searchTerms")
    public Object[][] getSearchTerms() {
        return new Object[][] {
            {"hammer"},
            {"pliers"},
            {"drill"}
        };
    }

    /**
     * TC-011: Filter Products by Price Range
     * Verify price filtering functionality
     */
    @Test(
        description = "TC-011: Filter Products by Price Range",
        groups = {"functional"},
        priority = 6
    )
    public void testFilterProductsByPriceRange() {
        logger.info("Starting TC-011: Filter by price range");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        ProductsPage productsPage = homePage.clickProductsLink();
        Assert.assertNotNull(productsPage, "Products page should load");

        // Verify price filter visible
        Assert.assertTrue(productsPage.isPriceFilterVisible(),
            "Price filter should be visible");

        // Apply price filter
        productsPage.setPriceRange(50, 100);
        productsPage.applyFilters();

        logger.info("Price filter applied: $50 - $100");

        // Verify filtered results
        Assert.assertTrue(productsPage.areProductsDisplayed(),
            "Filtered products should be displayed");

        // Verify all products within price range
        Assert.assertTrue(productsPage.areAllProductsWithinPriceRange(50, 100),
            "All products should be within price range");

        // Verify filter badge
        Assert.assertTrue(productsPage.isFilterBadgeDisplayed(),
            "Filter badge should be displayed");

        logger.info("TC-011 PASSED: Price filter works correctly");
    }

    /**
     * TC-012: Filter Products by Category
     * Verify category filtering functionality
     */
    @Test(
        description = "TC-012: Filter Products by Category",
        groups = {"functional"},
        priority = 7
    )
    public void testFilterProductsByCategory() {
        logger.info("Starting TC-012: Filter by category");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        ProductsPage productsPage = homePage.clickProductsLink();

        // Verify category filter visible
        Assert.assertTrue(productsPage.isCategoryFilterVisible(),
            "Category filter should be visible");

        // Apply category filter
        productsPage.selectCategory("Hand Tools");
        productsPage.applyFilters();

        logger.info("Category filter applied: Hand Tools");

        // Verify filtered results
        Assert.assertTrue(productsPage.areProductsDisplayed(),
            "Filtered products should be displayed");

        // Verify category badge
        Assert.assertTrue(productsPage.isFilterBadgeDisplayed(),
            "Category filter badge should be displayed");

        logger.info("TC-012 PASSED: Category filter works correctly");
    }

    /**
     * TC-013: View Product Details
     * Verify product detail page loads with all information
     */
    @Test(
        description = "TC-013: View Product Details",
        groups = {"functional"},
        priority = 8
    )
    public void testViewProductDetails() {
        logger.info("Starting TC-013: View product details");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Search for a product
        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
            "Search results should be displayed");

        // Click on first product
        ProductPage productPage = resultsPage.clickFirstProduct();
        Assert.assertNotNull(productPage, "Product detail page should load");

        // Verify all elements visible
        Assert.assertTrue(productPage.isProductPageLoaded(),
            "Product page should be fully loaded");

        Assert.assertTrue(productPage.isProductTitleDisplayed(),
            "Product title should be displayed");
        Assert.assertTrue(productPage.isProductPriceDisplayed(),
            "Product price should be displayed");
        Assert.assertTrue(productPage.isProductDescriptionDisplayed(),
            "Product description should be displayed");

        // Verify action buttons
        Assert.assertTrue(productPage.isAddToCartButtonVisible(),
            "Add to Cart button should be visible");
        Assert.assertTrue(productPage.isAddToFavoritesButtonVisible(),
            "Add to Favorites button should be visible");

        // Get and log product details
        String productDetails = productPage.getProductDetails();
        logger.info("Product details:\n{}", productDetails);

        logger.info("TC-013 PASSED: Product details page works correctly");
    }

    /**
     * TC-031: Add Out-of-Stock Product to Cart
     * Verify proper handling of out-of-stock products
     */
    @Test(
        description = "TC-031: Add Out-of-Stock Product to Cart",
        groups = {"negative"},
        priority = 12
    )
    public void testAddOutOfStockProductToCart() {
        logger.info("Starting TC-031: Out-of-stock product handling");

        // This test would require finding an out-of-stock product
        // For now, we verify the button behavior

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();

        // Find an out-of-stock product (if available)
        if (resultsPage.hasOutOfStockProduct()) {
            ProductPage productPage = resultsPage.clickOutOfStockProduct();

            // Verify out-of-stock indicator
            Assert.assertTrue(productPage.isOutOfStockIndicatorVisible(),
                "Out-of-stock indicator should be visible");

            // Verify Add to Cart button is disabled
            Assert.assertFalse(productPage.isAddToCartButtonClickable(),
                "Add to Cart button should be disabled for out-of-stock");

            logger.info("TC-031 PASSED: Out-of-stock handling works");
        } else {
            logger.warn("No out-of-stock products found in results");
        }
    }
}

