package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * CartPage - Page Object for Shopping Cart
 */
public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;

    @FindBy(css = "[data-test='cart-item']")
    private List<WebElement> cartItems;

    @FindBy(css = "[data-test='cart-total']")
    private WebElement cartTotal;

    @FindBy(css = "[data-test='remove-product']")
    private List<WebElement> removeButtons;

    @FindBy(css = "[data-test='update-cart']")
    private WebElement updateButton;

    @FindBy(css = "[data-test='checkout-button']")
    private WebElement checkoutButton;

    @FindBy(css = "[class*='empty-cart'], [class*='empty-message']")
    private WebElement emptyCartMessage;

    @FindBy(css = "[data-test='quantity-input']")
    private List<WebElement> quantityInputs;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

     /**
      * Check if cart page is loaded
      */
     public boolean isCartPageLoaded() {
         try {
             wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                 By.cssSelector("[data-test='cart-item']")));
             return true;
         } catch (Exception e) {
             return false;
         }
     }

    /**
     * Check if cart items are displayed
     */
    public boolean areItemsDisplayed() {
        return cartItems.size() > 0;
    }

    /**
     * Get cart total
     */
    public double getCartTotal() {
        String totalText = cartTotal.getText().replaceAll("[^0-9.]", "");
        try {
            return Double.parseDouble(totalText);
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Get item count
     */
    public int getItemCount() {
        return cartItems.size();
    }

    /**
     * Update item quantity
     */
    public void updateItemQuantity(int itemIndex, int quantity) {
        if (itemIndex < quantityInputs.size()) {
            WebElement qtyInput = quantityInputs.get(itemIndex);
            qtyInput.clear();
            qtyInput.sendKeys(String.valueOf(quantity));
        }
    }

     /**
      * Click on cart icon and return CartPage
      */
     public CartPage goToCart() {
         return this;
     }

     /**
      * Click update cart button
      */
     public void clickUpdateButton() {
         wait.until(ExpectedConditions.elementToBeClickable(updateButton));
         updateButton.click();
     }

     /**
      * Remove item
      */
     public void removeItem(int itemIndex) {
         if (itemIndex < removeButtons.size()) {
             wait.until(ExpectedConditions.elementToBeClickable(removeButtons.get(itemIndex)));
             removeButtons.get(itemIndex).click();
         }
     }

     /**
      * Confirm removal
      */
     public void confirmRemoval() {
         try {
             WebElement confirmBtn = driver.findElement(By.cssSelector("[class*='btn-confirm'], [class*='confirm']"));
             wait.until(ExpectedConditions.elementToBeClickable(confirmBtn));
             confirmBtn.click();
         } catch (Exception e) {
             // Might auto-confirm
         }
     }

    /**
     * Check if empty cart message is displayed
     */
    public boolean isEmptyCartMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emptyCartMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click checkout button
     */
    public CheckoutPage clickCheckoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        checkoutButton.click();
        return new CheckoutPage(driver);
    }

    /**
     * Get item quantity
     */
    public int getItemQuantity(int itemIndex) {
        if (itemIndex < quantityInputs.size()) {
            String qtyText = quantityInputs.get(itemIndex).getAttribute("value");
            try {
                return Integer.parseInt(qtyText);
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }
}
