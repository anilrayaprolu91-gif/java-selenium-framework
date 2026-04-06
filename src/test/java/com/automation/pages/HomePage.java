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
     * Sign in/Login link
     */
    @FindBy(linkText = "Sign in")
    private WebElement signInLink;

    /**
     * Sign out/Logout link
     */
    @FindBy(linkText = "Sign out")
    private WebElement signOutLink;

    /**
     * Cart icon
     */
    @FindBy(css = "[data-test='nav-cart']")
    private WebElement cartIcon;

    /**
     * Products link in navigation
     */
    @FindBy(linkText = "Products")
    private WebElement productsLink;

    /**
     * Orders link in navigation
     */
    @FindBy(linkText = "Orders")
    private WebElement ordersLink;

    /**
     * Logo
     */
    @FindBy(css = "[data-test='logo']")
    private WebElement logo;

    /**
     * Navigation menu
     */
    @FindBy(css = "nav")
    private WebElement navigationMenu;

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

    /**
     * Navigate to home page
     */
    public void navigateTo() {
        driver.get("https://practicesoftwaretesting.com/");
    }

    /**
     * Click on sign in link
     */
    public LoginPage clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(signInLink));
        signInLink.click();
        return new LoginPage(driver);
    }

    /**
     * Click on sign out link
     */
    public LoginPage clickLogoutLink() {
        wait.until(ExpectedConditions.elementToBeClickable(signOutLink));
        signOutLink.click();
        return new LoginPage(driver);
    }

    /**
     * Check if logout link is visible (user is logged in)
     */
    public boolean isLogoutLinkVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(signOutLink));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click on cart icon and return CartPage
     */
    public CartPage clickCartIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        cartIcon.click();
        return new CartPage(driver);
    }

    /**
     * Get cart item count from header badge
     */
     public int getCartItemCount() {
         try {
             WebElement cartBadge = driver.findElement(By.cssSelector("[data-test='cart-count']"));
             String countText = cartBadge.getText();
             return Integer.parseInt(countText);
         } catch (Exception e) {
             return 0;
         }
     }

     /**
      * Check if success notification is displayed
      */
     public boolean isSuccessNotificationDisplayed() {
         try {
             WebElement notification = driver.findElement(By.cssSelector("[class*='success'], [class*='alert-success']"));
             wait.until(ExpectedConditions.visibilityOf(notification));
             return true;
         } catch (Exception e) {
             return false;
         }
     }

    /**
     * Click on Products link
     */
    public ProductsPage clickProductsLink() {
        wait.until(ExpectedConditions.elementToBeClickable(productsLink));
        productsLink.click();
        return new ProductsPage(driver);
    }

    /**
     * Click on Orders link
     */
    public OrdersPage clickOrdersLink() {
        wait.until(ExpectedConditions.elementToBeClickable(ordersLink));
        ordersLink.click();
        return new OrdersPage(driver);
    }

    /**
     * Click on logo
     */
    public HomePage clickLogoLink() {
        wait.until(ExpectedConditions.elementToBeClickable(logo));
        logo.click();
        return this;
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
     * Check if page is loaded
     */
    public boolean isPageLoaded() {
        try {
            return isSearchInputVisible() && isNavigationMenuVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Search for product and return SearchResultsPage
     */
    public SearchResultsPage searchProduct(String searchTerm) {
        enterSearchQuery(searchTerm);
        clickSearchButton();
        return new SearchResultsPage(driver);
    }
}
