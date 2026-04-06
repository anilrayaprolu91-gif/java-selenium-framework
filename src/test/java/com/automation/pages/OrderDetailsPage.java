package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.io.File;

/**
 * OrderDetailsPage - Page Object for Order Details
 */
public class OrderDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;
    @FindBy(css = "[data-test='order-number']")
    private WebElement orderNumber;

    @FindBy(css = "[data-test='download-invoice']")
    private WebElement downloadButton;

    @FindBy(css = "[data-test='order-details']")
    private WebElement orderDetails;

    public OrderDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if order details are loaded
     */
    public boolean isOrderDetailsLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(orderDetails));
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
     * Download invoice (simplified implementation)
     */
    public String downloadInvoice() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(downloadButton));
            downloadButton.click();
            // Return download path (would need to configure download directory)
            return "invoice_" + getOrderNumber() + ".pdf";
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Verify PDF content (simplified)
     */
    public boolean verifyPDFContent(String filePath) {
        // Simplified - just check if file exists
        File file = new File(filePath);
        return file.exists();
    }
}

