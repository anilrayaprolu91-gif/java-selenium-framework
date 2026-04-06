package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.*;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Cart Tests - TC-014 to TC-016
 * Tests for shopping cart operations
 */
public class CartTests extends BaseTest {

    /**
     * TC-014: Add Product to Cart
     * Verify that products can be added to cart
     */
    @Test(
        description = "TC-014: Add Product to Cart",
        groups = {"functional"},
        priority = 9
    )
    public void testAddProductToCart() {
        logger.info("Starting TC-014: Add product to cart");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Search for product
        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
            "Search results should be displayed");

        // Click first product
        ProductPage productPage = resultsPage.clickFirstProduct();
        Assert.assertTrue(productPage.isProductPageLoaded(),
            "Product page should load");

        // Get initial cart count
        int initialCartCount = homePage.getCartItemCount();
        logger.info("Initial cart count: {}", initialCartCount);

        // Set quantity and add to cart
        productPage.setQuantity(2);
        productPage.clickAddToCartButton();

        logger.info("Product added to cart with quantity 2");

        // Verify cart count increased
        int updatedCartCount = homePage.getCartItemCount();
        Assert.assertTrue(updatedCartCount > initialCartCount,
            "Cart count should increase after adding product");

        // Verify toast notification
        Assert.assertTrue(homePage.isSuccessNotificationDisplayed(),
            "Success notification should be displayed");

        logger.info("TC-014 PASSED: Product added to cart successfully");
    }

    /**
     * TC-015: Update Cart Quantity
     * Verify that cart item quantities can be updated
     */
    @Test(
        description = "TC-015: Update Cart Quantity",
        groups = {"functional"},
        priority = 10,
        dependsOnMethods = "testAddProductToCart"
    )
    public void testUpdateCartQuantity() {
        logger.info("Starting TC-015: Update cart quantity");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Add product to cart first
        SearchResultsPage resultsPage = homePage.searchProduct("hammer");
        resultsPage.waitForResultsToLoad();
        ProductPage productPage = resultsPage.clickFirstProduct();
        productPage.setQuantity(1);
        productPage.clickAddToCartButton();

        // Open cart
        CartPage cartPage = homePage.clickCartIcon();
        Assert.assertTrue(cartPage.isCartPageLoaded(),
            "Cart page should load");

        Assert.assertTrue(cartPage.areItemsDisplayed(),
            "Cart items should be displayed");

        // Get initial total
        double initialTotal = cartPage.getCartTotal();
        logger.info("Initial cart total: ${}", initialTotal);

        // Update quantity
        cartPage.updateItemQuantity(0, 3);
        cartPage.clickUpdateButton();

        logger.info("Quantity updated to 3");

        // Verify total recalculated
        double updatedTotal = cartPage.getCartTotal();
        Assert.assertTrue(updatedTotal > initialTotal,
            "Cart total should increase with quantity");

        logger.info("TC-015 PASSED: Cart quantity updated successfully");
    }

    /**
     * TC-016: Remove Product from Cart
     * Verify that products can be removed from cart
     */
    @Test(
        description = "TC-016: Remove Product from Cart",
        groups = {"functional"},
        priority = 11,
        dependsOnMethods = "testAddProductToCart"
    )
    public void testRemoveProductFromCart() {
        logger.info("Starting TC-016: Remove product from cart");

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
        Assert.assertTrue(cartPage.areItemsDisplayed(),
            "Cart items should be displayed");

        int itemCountBefore = cartPage.getItemCount();
        logger.info("Items in cart before removal: {}", itemCountBefore);

        // Remove first item
        cartPage.removeItem(0);
        cartPage.confirmRemoval();

        logger.info("Item removed from cart");

        // Verify item removed
        if (itemCountBefore > 1) {
            int itemCountAfter = cartPage.getItemCount();
            Assert.assertEquals(itemCountAfter, itemCountBefore - 1,
                "Item count should decrease by 1");
        } else {
            Assert.assertTrue(cartPage.isEmptyCartMessageDisplayed(),
                "Empty cart message should be displayed");
        }

        logger.info("TC-016 PASSED: Product removed from cart successfully");
    }
}

