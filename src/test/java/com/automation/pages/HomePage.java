package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * HomePage Page Object Model class
 * Contains all locators and methods related to the Home page of Practice Software Testing
 */
public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;

    /**
     * Search Input Field - to enter search query
     */
    @FindBy(css = "[data-test='search-query']")
    private WebElement searchInput;

    /**
     * Search Submit Button - to submit the search
     */
    @FindBy(css = "[data-test='search-submit']")
    private WebElement searchButton;

    /**
     * Search Reset Button - to clear the search
     */
    @FindBy(css = "[data-test='search-reset']")
    private WebElement searchResetButton;

    /**
     * List of all products displayed on the page
     */
    @FindBy(css = "a[data-test^='product-']")
    private List<WebElement> productList;

    /**
     * Language selector dropdown
     */
    @FindBy(css = "[data-test='language-select']")
    private WebElement languageSelect;

    /**
     * Chat toggle button
     */
    @FindBy(css = "[data-test='chat-toggle']")
    private WebElement chatToggle;

    /**
     * Constructor to initialize PageFactory
     *
     * @param driver WebDriver instance
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    /**
     * Enter search query in the search input field
     *
     * @param searchQuery The search term to enter
     */
    public void enterSearchQuery(String searchQuery) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(searchQuery);
    }

    /**
     * Click the search button to perform search
     */
    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    /**
     * Search for a product - enters query and clicks search button
     *
     * @param searchQuery The search term to search for
     */
    public void searchProduct(String searchQuery) {
        enterSearchQuery(searchQuery);
        clickSearchButton();
    }

    /**
     * Click the reset button to clear search filters
     */
    public void clickResetButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchResetButton));
        searchResetButton.click();
    }

    /**
     * Get the number of products currently displayed
     *
     * @return Number of products
     */
    public int getProductCount() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                org.openqa.selenium.By.cssSelector("a[data-test^='product-']")));
        return productList.size();
    }

    /**
     * Get a product by index (0-based)
     *
     * @param index Index of the product
     * @return WebElement of the product
     */
    public WebElement getProductByIndex(int index) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                org.openqa.selenium.By.cssSelector("a[data-test^='product-']")));
        if (index >= 0 && index < productList.size()) {
            return productList.get(index);
        }
        throw new IndexOutOfBoundsException("Product index " + index + " is out of bounds. Total products: " + productList.size());
    }

    /**
     * Click on a product by index
     *
     * @param index Index of the product to click
     */
    public void clickProductByIndex(int index) {
        WebElement product = getProductByIndex(index);
        wait.until(ExpectedConditions.elementToBeClickable(product));
        product.click();
    }

    /**
     * Get the first product in the results
     *
     * @return WebElement of the first product
     */
    public WebElement getFirstProduct() {
        return getProductByIndex(0);
    }

    /**
     * Click on the first product
     */
    public void clickFirstProduct() {
        clickProductByIndex(0);
    }

    /**
     * Get product name by index
     *
     * @param index Index of the product
     * @return Product name as text
     */
    public String getProductNameByIndex(int index) {
        WebElement product = getProductByIndex(index);
        return product.getText();
    }

    /**
     * Get the first product name
     *
     * @return First product name
     */
    public String getFirstProductName() {
        return getProductNameByIndex(0);
    }

    /**
     * Check if search input is visible
     *
     * @return True if visible, false otherwise
     */
    public boolean isSearchInputVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchInput));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the current value of the search input
     *
     * @return Current search input value
     */
    public String getSearchInputValue() {
        return searchInput.getAttribute("value");
    }

    /**
     * Check if any products are displayed
     *
     * @return True if products are visible, false otherwise
     */
    public boolean areProductsDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    org.openqa.selenium.By.cssSelector("a[data-test^='product-']")));
            return productList.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Toggle the chat window
     */
    public void toggleChat() {
        wait.until(ExpectedConditions.elementToBeClickable(chatToggle));
        chatToggle.click();
    }

    /**
     * Select a language from the dropdown
     *
     * @param language Language to select (e.g., "EN", "ES", "FR")
     */
    public void selectLanguage(String language) {
        wait.until(ExpectedConditions.elementToBeClickable(languageSelect));
        languageSelect.click();
        // Note: Implementation depends on dropdown structure
    }
}

