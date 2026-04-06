package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * ProductPage Page Object Model class
 * Contains all locators and methods related to the Product Detail page
 */
public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;

    /**
     * Product Name/Title - Main heading of the product
     */
    @FindBy(css = "[data-test='product-name']")
    private WebElement productName;

    /**
     * Product Description - Detailed description of the product
     */
    @FindBy(css = "[data-test='product-description']")
    private WebElement productDescription;

    /**
     * Product Price - The unit price of the product
     */
    @FindBy(css = "[data-test='unit-price']")
    private WebElement productPrice;

    /**
     * Add to Cart Button - Button to add product to cart
     */
    @FindBy(css = "[data-test='add-to-cart']")
    private WebElement addToCartButton;

    /**
     * Add to Favorites Button - Button to add product to favorites/wishlist
     */
    @FindBy(css = "[data-test='add-to-favorites']")
    private WebElement addToFavoritesButton;

    /**
     * Quantity input field - for setting product quantity before adding to cart
     */
    @FindBy(css = "[data-test='quantity-input']")
    private WebElement quantityInput;

    /**
     * Constructor to initialize PageFactory
     *
     * @param driver WebDriver instance
     */
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    /**
     * Get the product name/title
     *
     * @return Product title as string
     */
    public String getProductTitle() {
        wait.until(ExpectedConditions.visibilityOf(productName));
        return productName.getText().trim();
    }

    /**
     * Get the product description
     *
     * @return Product description as string
     */
    public String getProductDescription() {
        wait.until(ExpectedConditions.visibilityOf(productDescription));
        return productDescription.getText().trim();
    }

    /**
     * Get the product price
     *
     * @return Product price as string (e.g., "13.41")
     */
    public String getProductPrice() {
        wait.until(ExpectedConditions.visibilityOf(productPrice));
        return productPrice.getText().trim();
    }

    /**
     * Get the product price as a double value
     *
     * @return Product price as double
     */
    public double getProductPriceAsDouble() {
        String priceText = getProductPrice();
        try {
            return Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Cannot convert price '" + priceText + "' to double", e);
        }
    }

    /**
     * Check if the add to cart button is visible
     *
     * @return True if button is visible, false otherwise
     */
    public boolean isAddToCartButtonVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(addToCartButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click the Add to Cart button
     */
    public void clickAddToCartButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    /**
     * Check if the add to favorites button is visible
     *
     * @return True if button is visible, false otherwise
     */
    public boolean isAddToFavoritesButtonVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(addToFavoritesButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click the Add to Favorites button
     */
    public void clickAddToFavoritesButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addToFavoritesButton));
        addToFavoritesButton.click();
    }

    /**
     * Set the product quantity
     *
     * @param quantity The quantity to set
     */
    public void setQuantity(int quantity) {
        try {
            wait.until(ExpectedConditions.visibilityOf(quantityInput));
            quantityInput.clear();
            quantityInput.sendKeys(String.valueOf(quantity));
        } catch (Exception e) {
            // Quantity field might not exist for some products
        }
    }

    /**
     * Check if the product title is displayed
     *
     * @return True if title is displayed, false otherwise
     */
    public boolean isProductTitleDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productName));
            return productName.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the product price is displayed
     *
     * @return True if price is displayed, false otherwise
     */
    public boolean isProductPriceDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productPrice));
            return productPrice.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the product description is displayed
     *
     * @return True if description is displayed, false otherwise
     */
    public boolean isProductDescriptionDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productDescription));
            return productDescription.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify that the product page is fully loaded with all essential elements
     *
     * @return True if all essential elements are visible
     */
    public boolean isProductPageLoaded() {
        return isProductTitleDisplayed() && isProductPriceDisplayed();
    }

    /**
     * Get all product details as a string
     *
     * @return Product details in a formatted string
     */
    public String getProductDetails() {
        return String.format(
                "Product Title: %s\nPrice: $%s\nDescription: %s",
                getProductTitle(),
                getProductPrice(),
                getProductDescription()
        );
    }

    /**
     * Verify if the product title contains a specific text
     *
     * @param expectedText The text to verify
     * @return True if the product title contains the text, false otherwise
     */
    public boolean verifyProductTitleContains(String expectedText) {
        String actualTitle = getProductTitle();
        return actualTitle.contains(expectedText);
    }

    /**
     * Verify if the product title equals the expected text (case-sensitive)
     *
     * @param expectedTitle The expected product title
     * @return True if titles match, false otherwise
     */
    public boolean verifyProductTitleEquals(String expectedTitle) {
        String actualTitle = getProductTitle();
        return actualTitle.equals(expectedTitle);
    }

    /**
     * Check if out-of-stock indicator is visible
     */
    public boolean isOutOfStockIndicatorVisible() {
        try {
            WebElement outOfStockIndicator = driver.findElement(org.openqa.selenium.By.cssSelector("[class*='out-of-stock']"));
            wait.until(ExpectedConditions.visibilityOf(outOfStockIndicator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if add to cart button is clickable
     */
    public boolean isAddToCartButtonClickable() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
