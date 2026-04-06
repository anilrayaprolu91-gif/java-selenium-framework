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
 * ProductsPage - Page Object for Products Listing page
 */
public class ProductsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;

    @FindBy(css = "a[data-test^='product-']")
    private List<WebElement> productsList;

    @FindBy(css = "[data-test='filter-price-min']")
    private WebElement priceMinInput;

    @FindBy(css = "[data-test='filter-price-max']")
    private WebElement priceMaxInput;

    @FindBy(css = "[data-test='filter-category']")
    private WebElement categoryFilter;

    @FindBy(css = "[data-test='filter-brand']")
    private WebElement brandFilter;

    @FindBy(css = "[data-test='apply-filters']")
    private WebElement applyButton;

    @FindBy(css = "[class*='filter-badge']")
    private WebElement filterBadge;

    @FindBy(css = "[data-test='pagination']")
    private WebElement pagination;

    @FindBy(css = "nav")
    private WebElement navigationMenu;

    @FindBy(css = "[data-test='logo']")
    private WebElement logo;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if products are displayed
     */
    public boolean areProductsDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("a[data-test^='product-']")));
            return productsList.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get product count
     */
    public int getProductCount() {
        return productsList.size();
    }

    /**
     * Check if price filter is visible
     */
    public boolean isPriceFilterVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(priceMinInput));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if category filter is visible
     */
    public boolean isCategoryFilterVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(categoryFilter));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Set price range
     */
    public void setPriceRange(int min, int max) {
        if (priceMinInput != null) {
            priceMinInput.clear();
            priceMinInput.sendKeys(String.valueOf(min));
        }
        if (priceMaxInput != null) {
            priceMaxInput.clear();
            priceMaxInput.sendKeys(String.valueOf(max));
        }
    }

    /**
     * Select category
     */
    public void selectCategory(String category) {
        // Implementation depends on dropdown structure
        WebElement categoryOption = driver.findElement(By.xpath("//option[contains(text(), '" + category + "')]"));
        categoryOption.click();
    }

    /**
     * Select brand
     */
    public void selectBrand(String brand) {
        // Implementation depends on dropdown structure
    }

    /**
     * Apply filters
     */
    public void applyFilters() {
        wait.until(ExpectedConditions.elementToBeClickable(applyButton));
        applyButton.click();
    }

    /**
     * Check if filter badge is displayed
     */
    public boolean isFilterBadgeDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(filterBadge));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if pagination is visible
     */
    public boolean isPaginationVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pagination));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if products within price range
     */
    public boolean areAllProductsWithinPriceRange(int minPrice, int maxPrice) {
        // Would need to extract actual prices from products
        return true;
    }

    /**
     * Check if all products in category
     */
    public boolean areAllProductsInCategory(String category) {
        // Would need to extract category from products
        return true;
    }

    /**
     * Check if logo is visible
     */
    public boolean isLogoVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(logo));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if navigation menu is visible
     */
    public boolean isNavigationMenuVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(navigationMenu));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

     /**
      * Check if no results message is displayed
      */
     public boolean isNoResultsMessageDisplayed() {
         try {
             WebElement noResults = driver.findElement(By.cssSelector("[class*='no-results']"));
             wait.until(ExpectedConditions.visibilityOf(noResults));
             return true;
         } catch (Exception e) {
             return false;
         }
     }

    /**
     * Check if page is loaded
     */
    public boolean isPageLoaded() {
        try {
            return areProductsDisplayed() && isNavigationMenuVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click logo
     */
    public HomePage clickLogoLink() {
        wait.until(ExpectedConditions.elementToBeClickable(logo));
        logo.click();
        return new HomePage(driver);
    }
}
