package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * OrderConfirmationPage - Page Object for Order Confirmation
 */
public class OrderConfirmationPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;

    @FindBy(css = "[data-test='order-confirmation']")
    private WebElement confirmationMessage;

    @FindBy(css = "[data-test='order-number']")
    private WebElement orderNumber;

    @FindBy(css = "[data-test='order-total']")
    private WebElement orderTotal;

    public OrderConfirmationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if order confirmation is displayed
     */
    public boolean isOrderConfirmationDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get order number
     */
    public String getOrderNumber() {
        try {
            return orderNumber.getText();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get order total
     */
    public double getOrderTotal() {
        try {
            String totalText = orderTotal.getText().replaceAll("[^0-9.]", "");
            return Double.parseDouble(totalText);
        } catch (Exception e) {
            return 0.0;
        }
    }
}

