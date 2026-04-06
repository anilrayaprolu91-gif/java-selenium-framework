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
 * CheckoutPage - Page Object for Checkout process
 */
public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;

    @FindBy(css = "[data-test='first-name']")
    private WebElement firstNameInput;

    @FindBy(css = "[data-test='last-name']")
    private WebElement lastNameInput;

    @FindBy(css = "[data-test='address']")
    private WebElement addressInput;

    @FindBy(css = "[data-test='city']")
    private WebElement cityInput;

    @FindBy(css = "[data-test='state']")
    private WebElement stateInput;

    @FindBy(css = "[data-test='postal-code']")
    private WebElement postalCodeInput;

    @FindBy(css = "[data-test='country']")
    private WebElement countrySelect;

    @FindBy(css = "[data-test='same-as-shipping']")
    private WebElement sameAsShippingCheckbox;

    @FindBy(css = "[data-test='payment-method']")
    private WebElement paymentMethodSelect;

    @FindBy(css = "[data-test='card-number']")
    private WebElement cardNumberInput;

    @FindBy(css = "[data-test='card-expiry']")
    private WebElement cardExpiryInput;

    @FindBy(css = "[data-test='card-cvc']")
    private WebElement cardCvcInput;

    @FindBy(css = "[data-test='place-order']")
    private WebElement placeOrderButton;

    @FindBy(css = "[data-test='continue-button']")
    private WebElement continueButton;

    @FindBy(css = "[class*='validation-error'], [class*='error-message']")
    private WebElement validationError;

    @FindBy(css = "[data-test='checkout-form']")
    private WebElement checkoutForm;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if checkout page is loaded
     */
    public boolean isCheckoutPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(checkoutForm));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Fill first name
     */
    public void fillFirstName(String firstName) {
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    /**
     * Fill last name
     */
    public void fillLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    /**
     * Fill address
     */
    public void fillAddress(String address) {
        addressInput.clear();
        addressInput.sendKeys(address);
    }

    /**
     * Fill city
     */
    public void fillCity(String city) {
        cityInput.clear();
        cityInput.sendKeys(city);
    }

    /**
     * Fill state
     */
    public void fillState(String state) {
        stateInput.clear();
        stateInput.sendKeys(state);
    }

    /**
     * Fill postal code
     */
    public void fillPostalCode(String postalCode) {
        postalCodeInput.clear();
        postalCodeInput.sendKeys(postalCode);
    }

    /**
     * Select country
     */
    public void fillCountry(String country) {
        countrySelect.clear();
        countrySelect.sendKeys(country);
    }

    /**
     * Click same as shipping checkbox
     */
    public void clickSameAsShippingCheckbox() {
        if (!sameAsShippingCheckbox.isSelected()) {
            sameAsShippingCheckbox.click();
        }
    }

    /**
     * Select payment method
     */
    public void selectPaymentMethod(String method) {
        paymentMethodSelect.clear();
        paymentMethodSelect.sendKeys(method);
    }

    /**
     * Fill card number
     */
    public void fillCardNumber(String cardNumber) {
        cardNumberInput.clear();
        cardNumberInput.sendKeys(cardNumber);
    }

    /**
     * Fill card expiry
     */
    public void fillCardExpiry(String expiry) {
        cardExpiryInput.clear();
        cardExpiryInput.sendKeys(expiry);
    }

    /**
     * Fill card CVC
     */
    public void fillCardCVC(String cvc) {
        cardCvcInput.clear();
        cardCvcInput.sendKeys(cvc);
    }

    /**
     * Click place order button
     */
    public OrderConfirmationPage clickPlaceOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton));
        placeOrderButton.click();
        return new OrderConfirmationPage(driver);
    }

    /**
     * Click continue button
     */
    public void clickContinueButton() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
    }

    /**
     * Check if validation error is displayed
     */
    public boolean isValidationErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(validationError));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get validation error message
     */
    public String getValidationErrorMessage() {
        if (isValidationErrorDisplayed()) {
            return validationError.getText();
        }
        return "";
    }

    /**
     * Select country (alternative method)
     */
    public void selectCountry(String country) {
        fillCountry(country);
    }
}

