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
 * SearchResultsPage - Page Object for Search Results
 */
public class SearchResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;

    @FindBy(css = "a[data-test^='product-']")
    private List<WebElement> resultsList;

    @FindBy(css = "[class*='no-results'], [class*='no-products']")
    private WebElement noResultsMessage;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    /**
     * Wait for results to load
     */
    public void waitForResultsToLoad() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Check if results are displayed
     */
    public boolean areResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("a[data-test^='product-']")));
            return resultsList.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get count of results
     */
    public int getResultCount() {
        return resultsList.size();
    }

    /**
     * Click first product in results
     */
    public ProductPage clickFirstProduct() {
        if (resultsList.size() > 0) {
            wait.until(ExpectedConditions.elementToBeClickable(resultsList.get(0)));
            resultsList.get(0).click();
        }
        return new ProductPage(driver);
    }

    /**
     * Check if all results contain search term
     */
    public boolean doAllResultsContainTerm(String term) {
        for (WebElement result : resultsList) {
            String text = result.getText().toLowerCase();
            if (!text.contains(term.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

     /**
      * Check for out-of-stock product
      */
     public boolean hasOutOfStockProduct() {
         try {
             return driver.findElements(By.cssSelector("[class*='out-of-stock']")).size() > 0;
         } catch (Exception e) {
             return false;
         }
     }

     /**
      * Click out-of-stock product
      */
     public ProductPage clickOutOfStockProduct() {
         WebElement outOfStock = driver.findElement(By.cssSelector("[class*='out-of-stock']"));
         wait.until(ExpectedConditions.elementToBeClickable(outOfStock));
         outOfStock.click();
         return new ProductPage(driver);
     }

    /**
     * Check if no results message is displayed
     */
    public boolean isNoResultsMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(noResultsMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Alias for isNoResultsMessageDisplayed - compatibility method
     */
    public boolean isNoSearchTermMessageDisplayed() {
        return isNoResultsMessageDisplayed();
    }

    /**
     * Check if page is loaded
     */
    public boolean isPageLoaded() {
        try {
            return areResultsDisplayed() || isNoResultsMessageDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check for JavaScript errors on the page
     */
    public boolean areJavaScriptErrorsPresent() {
        // This is a placeholder - actual implementation would check browser logs
        return false;
    }
}
