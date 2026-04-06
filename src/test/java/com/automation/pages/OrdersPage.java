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
 * OrdersPage - Page Object for Orders List
 */
public class OrdersPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;

    @FindBy(css = "[data-test='order-item']")
    private List<WebElement> ordersList;

    @FindBy(css = "[data-test='orders-container']")
    private WebElement ordersContainer;

    public OrdersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if orders page is loaded
     */
    public boolean isOrdersPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(ordersContainer));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if orders are displayed
     */
    public boolean areOrdersDisplayed() {
        return ordersList.size() > 0;
    }

    /**
     * Click on most recent order
     */
    public OrderDetailsPage clickMostRecentOrder() {
        if (ordersList.size() > 0) {
            wait.until(ExpectedConditions.elementToBeClickable(ordersList.get(0)));
            ordersList.get(0).click();
        }
        return new OrderDetailsPage(driver);
    }
}

