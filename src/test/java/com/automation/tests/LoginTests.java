package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.LoginPage;
import com.automation.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Login Tests - TC-006 to TC-008
 * Tests for user authentication including valid/invalid credentials
 */
public class LoginTests extends BaseTest {

    /**
     * TC-006: Login with Valid Credentials - Admin
     * Verify admin login with correct credentials
     */
    @Test(
        description = "TC-006: Login with Valid Credentials - Admin",
        groups = {"smoke", "functional"},
        priority = 1
    )
    public void testAdminLoginWithValidCredentials() {
        logger.info("Starting TC-006: Admin login test");

        // Navigate to home page
        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        // Click login link
        LoginPage loginPage = homePage.clickLoginLink();
        Assert.assertNotNull(loginPage, "Login page should load");

        // Enter admin credentials
        loginPage.enterEmail("admin@practicesoftwaretesting.com");
        loginPage.enterPassword("welcome01");
        loginPage.clickLoginButton();

        logger.info("Admin login completed, waiting for dashboard");

        // Verify successful login by checking for logout link
        boolean isLoggedIn = homePage.isLogoutLinkVisible();
        Assert.assertTrue(isLoggedIn, "Admin should be logged in successfully");

        logger.info("TC-006 PASSED: Admin login successful");
    }

    /**
     * TC-007: Login with Valid Credentials - Customer
     * Verify customer login with correct credentials
     */
    @Test(
        description = "TC-007: Login with Valid Credentials - Customer",
        groups = {"smoke", "functional"},
        priority = 2
    )
    public void testCustomerLoginWithValidCredentials() {
        logger.info("Starting TC-007: Customer login test");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        LoginPage loginPage = homePage.clickLoginLink();
        Assert.assertNotNull(loginPage, "Login page should load");

        loginPage.enterEmail("customer@practicesoftwaretesting.com");
        loginPage.enterPassword("welcome01");
        loginPage.clickLoginButton();

        logger.info("Customer login completed");

        boolean isLoggedIn = homePage.isLogoutLinkVisible();
        Assert.assertTrue(isLoggedIn, "Customer should be logged in successfully");

        logger.info("TC-007 PASSED: Customer login successful");
    }

    /**
     * TC-008: Logout Functionality
     * Verify that user can logout successfully
     */
    @Test(
        description = "TC-008: Logout Functionality",
        groups = {"smoke", "functional"},
        priority = 3
    )
    public void testLogoutFunctionality() {
        logger.info("Starting TC-008: Logout test");

        // First login
        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();
        LoginPage loginPage = homePage.clickLoginLink();

        loginPage.enterEmail("customer@practicesoftwaretesting.com");
        loginPage.enterPassword("welcome01");
        loginPage.clickLoginButton();

        // Verify logged in
        Assert.assertTrue(homePage.isLogoutLinkVisible(), "User should be logged in");

        // Logout
        loginPage = homePage.clickLogoutLink();
        logger.info("Logout clicked");

        // Verify returned to login page or home page
        Assert.assertTrue(loginPage.isLoginFormVisible(),
            "Should return to login page after logout");

        logger.info("TC-008 PASSED: Logout successful");
    }

    /**
     * TC-029: Login with Invalid Email
     * Verify error handling for invalid email
     */
    @Test(
        description = "TC-029: Login with Invalid Email",
        groups = {"negative"},
        priority = 10
    )
    public void testLoginWithInvalidEmail() {
        logger.info("Starting TC-029: Invalid email login test");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();
        LoginPage loginPage = homePage.clickLoginLink();

        loginPage.enterEmail("invalid@email.com");
        loginPage.enterPassword("welcome01");
        loginPage.clickLoginButton();

        logger.info("Checking for error message");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Error message should be displayed for invalid credentials");

        String errorText = loginPage.getErrorMessage();
        Assert.assertTrue(errorText.toLowerCase().contains("invalid") ||
                         errorText.toLowerCase().contains("credentials"),
            "Error message should indicate invalid credentials");

        logger.info("TC-029 PASSED: Invalid email error handling works");
    }

    /**
     * TC-030: Login with Invalid Password
     * Verify error handling for incorrect password
     */
    @Test(
        description = "TC-030: Login with Invalid Password",
        groups = {"negative"},
        priority = 11
    )
    public void testLoginWithInvalidPassword() {
        logger.info("Starting TC-030: Invalid password login test");

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();
        LoginPage loginPage = homePage.clickLoginLink();

        loginPage.enterEmail("admin@practicesoftwaretesting.com");
        loginPage.enterPassword("wrongpassword");
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Error message should be displayed for wrong password");

        logger.info("TC-030 PASSED: Invalid password error handling works");
    }
}

