# Test Cases: Practice Software Testing - Toolshop

## Test Case Format
```
TC-ID: [Test Case ID]
Title: [Clear, descriptive title]
Type: [Smoke/Functional/Regression/Negative/Boundary/Data-Driven]
Priority: [Critical/High/Medium/Low]
Precondition: [Setup required]
Steps: [Numbered steps]
Expected Result: [What should happen]
Data: [Test data]
Tags: [searchable tags]
```

---

## SMOKE TESTS (5 Tests)

### TC-001: Application Load
**Type**: Smoke | **Priority**: Critical  
**Precondition**: Browser open, network available  
**Steps**:
1. Navigate to https://practicesoftwaretesting.com/
2. Wait for page to load completely
3. Verify page title contains "Practice"

**Expected Result**: Page loads successfully with visible header, navigation, and content  
**Tags**: smoke, application, load

---

### TC-002: Homepage Elements Visible
**Type**: Smoke | **Priority**: Critical  
**Precondition**: Application loaded  
**Steps**:
1. Verify logo is visible
2. Verify navigation menu is present
3. Verify search bar is visible
4. Verify product grid is displayed

**Expected Result**: All key elements visible and functional  
**Tags**: smoke, homepage, ui

---

### TC-003: Login Page Accessible
**Type**: Smoke | **Priority**: Critical  
**Precondition**: Homepage loaded  
**Steps**:
1. Click "Sign in" link
2. Wait for login page to load
3. Verify email field is visible
4. Verify password field is visible

**Expected Result**: Login page loads successfully with all form fields  
**Tags**: smoke, login, page

---

### TC-004: Product Search Accessible
**Type**: Smoke | **Priority**: High  
**Precondition**: Homepage loaded  
**Steps**:
1. Verify search bar is visible
2. Click search bar
3. Enter "hammer"
4. Click search button

**Expected Result**: Search executes without errors  
**Tags**: smoke, search, product

---

### TC-005: Category Filter Visible
**Type**: Smoke | **Priority**: High  
**Precondition**: Product page loaded  
**Steps**:
1. Navigate to Products page
2. Verify category filter sidebar appears
3. Verify price filter appears
4. Verify brand filter appears

**Expected Result**: All filter options visible and clickable  
**Tags**: smoke, filter, product

---

## FUNCTIONAL TESTS (15 Tests)

### TC-006: Login with Valid Credentials - Admin
**Type**: Functional | **Priority**: Critical  
**Data**: admin@practicesoftwaretesting.com / welcome01  
**Precondition**: Login page loaded  
**Steps**:
1. Enter email: admin@practicesoftwaretesting.com
2. Enter password: welcome01
3. Click Login button
4. Wait for dashboard to load

**Expected Result**: 
- Login successful
- Redirected to admin dashboard
- User name displayed in header

**Tags**: functional, login, authentication, admin

---

### TC-007: Login with Valid Credentials - Customer
**Type**: Functional | **Priority**: Critical  
**Data**: customer@practicesoftwaretesting.com / welcome01  
**Precondition**: Login page loaded  
**Steps**:
1. Enter email: customer@practicesoftwaretesting.com
2. Enter password: welcome01
3. Click Login button
4. Wait for homepage to load

**Expected Result**: 
- Login successful
- Redirected to homepage
- "Sign out" link visible in header

**Tags**: functional, login, authentication, customer

---

### TC-008: Logout Functionality
**Type**: Functional | **Priority**: High  
**Precondition**: User logged in  
**Steps**:
1. Click "Sign out" link in header
2. Wait for redirect
3. Verify on login page

**Expected Result**: 
- Session terminated
- Redirected to login page
- No authentication cookies

**Tags**: functional, logout, authentication

---

### TC-009: Browse All Products
**Type**: Functional | **Priority**: High  
**Precondition**: Homepage loaded  
**Steps**:
1. Click "Products" in navigation
2. Wait for product list to load
3. Verify product count > 0
4. Scroll down and verify pagination

**Expected Result**: 
- All products displayed in grid
- Product details visible (name, price, rating)
- Pagination works correctly

**Tags**: functional, products, browsing

---

### TC-010: Search for Product by Name
**Type**: Functional | **Priority**: High  
**Data**: Search term = "hammer"  
**Precondition**: Homepage loaded  
**Steps**:
1. Click search bar
2. Enter "hammer"
3. Press Enter or click search button
4. Wait for results

**Expected Result**: 
- Search results displayed
- All results contain "hammer" (case-insensitive)
- Result count > 0

**Tags**: functional, search, product

---

### TC-011: Filter Products by Price Range
**Type**: Functional | **Priority**: High  
**Data**: Price range: $50-$100  
**Precondition**: Products page loaded  
**Steps**:
1. Click price filter
2. Set minimum price to $50
3. Set maximum price to $100
4. Click Apply
5. Wait for filtered results

**Expected Result**: 
- Only products in range displayed
- Each product price is $50-$100
- Filter badge shown

**Tags**: functional, filter, price, product

---

### TC-012: Filter Products by Category
**Type**: Functional | **Priority**: High  
**Data**: Category = "Hand Tools"  
**Precondition**: Products page loaded  
**Steps**:
1. Click "Hand Tools" category
2. Wait for filtered results
3. Verify all products are Hand Tools

**Expected Result**: 
- Only Hand Tools displayed
- Category filter badge shown
- Product count updated

**Tags**: functional, filter, category, product

---

### TC-013: View Product Details
**Type**: Functional | **Priority**: High  
**Precondition**: Products page loaded  
**Steps**:
1. Click on any product
2. Wait for product detail page
3. Verify product name displayed
4. Verify price displayed
5. Verify description displayed
6. Verify "Add to Cart" button visible

**Expected Result**: 
- Product detail page loads
- All information correct
- Related products suggested
- Add to Cart button clickable

**Tags**: functional, product, details

---

### TC-014: Add Product to Cart
**Type**: Functional | **Priority**: Critical  
**Precondition**: Product detail page loaded  
**Steps**:
1. Enter quantity: 2
2. Click "Add to Cart" button
3. Wait for confirmation
4. Verify cart count updated in header

**Expected Result**: 
- Product added successfully
- Cart count incremented
- Toast notification shown
- Can continue shopping or go to cart

**Tags**: functional, cart, add

---

### TC-015: Update Cart Quantity
**Type**: Functional | **Priority**: High  
**Precondition**: Product in cart  
**Steps**:
1. Click cart icon
2. Wait for cart page
3. Change quantity to 3
4. Click Update

**Expected Result**: 
- Quantity updated
- Total price recalculated
- Cart total updated

**Tags**: functional, cart, update

---

### TC-016: Remove Product from Cart
**Type**: Functional | **Priority**: High  
**Precondition**: Product in cart  
**Steps**:
1. Click cart icon
2. Click Remove button on product
3. Confirm removal
4. Verify product removed from cart

**Expected Result**: 
- Product removed from cart
- Cart total updated
- Empty cart if last item removed

**Tags**: functional, cart, remove

---

### TC-017: Checkout Process - Complete
**Type**: Functional | **Priority**: Critical  
**Data**: 
  - First Name: John
  - Last Name: Doe
  - Address: 123 Main St
  - City: Anytown
  - State: CA
  - Postal Code: 12345
  - Country: USA

**Precondition**: Products in cart, customer logged in  
**Steps**:
1. Click "Checkout" button
2. Fill billing address form
3. Fill shipping address (same as billing)
4. Select payment method: Credit Card
5. Fill payment details
6. Click "Place Order"
7. Wait for confirmation

**Expected Result**: 
- Order placed successfully
- Order confirmation displayed
- Order ID generated
- Email confirmation sent
- Redirected to order details page

**Tags**: functional, checkout, order

---

### TC-018: Download Invoice as PDF
**Type**: Functional | **Priority**: High  
**Precondition**: Order completed  
**Steps**:
1. Navigate to Orders page
2. Click on recent order
3. Click "Download Invoice"
4. Wait for PDF download
5. Verify PDF is valid

**Expected Result**: 
- PDF downloaded successfully
- File name: order_[ID].pdf
- PDF contains order details
- All order lines shown
- Total amount shown

**Tags**: functional, pdf, invoice, download

---

## REGRESSION TESTS (10 Tests)

### TC-019: All Smoke Tests Pass
**Type**: Regression | **Priority**: Critical  
**Precondition**: Full test suite execution  
**Steps**: Execute TC-001 through TC-005  
**Expected Result**: All smoke tests pass  
**Tags**: regression, smoke

---

### TC-020: All Functional Tests Pass
**Type**: Regression | **Priority**: Critical  
**Precondition**: Full test suite execution  
**Steps**: Execute TC-006 through TC-018  
**Expected Result**: All functional tests pass  
**Tags**: regression, functional

---

### TC-021: Cross-Browser Login
**Type**: Regression | **Priority**: High  
**Browsers**: Chrome, Firefox, Edge, Safari  
**Precondition**: Each browser available  
**Steps**:
1. Run TC-006 on each browser
2. Verify login successful on each

**Expected Result**: Login works on all browsers  
**Tags**: regression, cross-browser, login

---

### TC-022: Cross-Browser Product Search
**Type**: Regression | **Priority**: High  
**Browsers**: Chrome, Firefox, Edge  
**Precondition**: Each browser available  
**Steps**:
1. Run TC-010 on each browser
2. Verify search works identically

**Expected Result**: Search works consistently across browsers  
**Tags**: regression, cross-browser, search

---

### TC-023: Cross-Browser Checkout
**Type**: Regression | **Priority**: High  
**Browsers**: Chrome, Firefox  
**Precondition**: Each browser available  
**Steps**:
1. Run TC-017 on each browser
2. Verify checkout identical

**Expected Result**: Checkout process works on all browsers  
**Tags**: regression, cross-browser, checkout

---

### TC-024: UI Elements Consistency
**Type**: Regression | **Priority**: Medium  
**Precondition**: Application loaded  
**Steps**:
1. Verify header layout consistent
2. Verify footer present
3. Verify navigation menu same
4. Verify button styles consistent

**Expected Result**: UI consistent across all pages  
**Tags**: regression, ui, consistency

---

### TC-025: Performance Baseline
**Type**: Regression | **Priority**: Medium  
**Precondition**: Homepage  
**Steps**:
1. Measure page load time
2. Verify < 5 seconds
3. Check Core Web Vitals

**Expected Result**: 
- Page load < 5 seconds
- LCP < 2.5 seconds
- FID < 100ms

**Tags**: regression, performance

---

### TC-026: Data Integrity - Product Info
**Type**: Regression | **Priority**: Medium  
**Data**: Product ID = 1  
**Precondition**: Product page loaded  
**Steps**:
1. Load product detail
2. Reload page
3. Verify all data identical
4. Check database directly if possible

**Expected Result**: Product information consistent across page refreshes  
**Tags**: regression, data, integrity

---

### TC-027: Session Persistence
**Type**: Regression | **Priority**: High  
**Precondition**: User logged in  
**Steps**:
1. Load page
2. Open new tab with same site
3. Verify user still logged in
4. Refresh page
5. Verify session maintained

**Expected Result**: Session persists across tabs and page refreshes  
**Tags**: regression, session, authentication

---

### TC-028: Cart Persistence
**Type**: Regression | **Priority**: High  
**Precondition**: Products in cart  
**Steps**:
1. Refresh page
2. Navigate away and back
3. Close browser and reopen
4. Verify cart contents

**Expected Result**: Cart contents preserved (if logged in or cookie-based)  
**Tags**: regression, cart, persistence

---

## NEGATIVE TESTS (5 Tests)

### TC-029: Login with Invalid Email
**Type**: Negative | **Priority**: High  
**Data**: Email = "invalid@email.com" | Password = "welcome01"  
**Precondition**: Login page loaded  
**Steps**:
1. Enter invalid email
2. Enter valid password
3. Click Login
4. Wait for error message

**Expected Result**: 
- Login fails
- Error message displayed: "Invalid credentials"
- No session created
- Remain on login page

**Tags**: negative, login, validation

---

### TC-030: Login with Invalid Password
**Type**: Negative | **Priority**: High  
**Data**: Email = "admin@practicesoftwaretesting.com" | Password = "wrongpassword"  
**Precondition**: Login page loaded  
**Steps**:
1. Enter valid email
2. Enter invalid password
3. Click Login
4. Wait for error message

**Expected Result**: 
- Login fails
- Error message displayed
- Account not locked (multiple attempts allowed)
- Can retry

**Tags**: negative, login, password

---

### TC-031: Add Out-of-Stock Product to Cart
**Type**: Negative | **Priority**: High  
**Precondition**: Product detail page, product out-of-stock  
**Steps**:
1. Navigate to out-of-stock product
2. Verify "Out of Stock" badge shown
3. Verify "Add to Cart" button disabled
4. Click button (should not respond)

**Expected Result**: 
- Out of stock clearly indicated
- Add to Cart button disabled
- Cannot proceed to cart
- Suggestion to notify when available

**Tags**: negative, inventory, stock

---

### TC-032: Checkout without Shipping Address
**Type**: Negative | **Priority**: High  
**Precondition**: Checkout page, cart filled  
**Steps**:
1. Start checkout
2. Skip shipping address
3. Click "Continue to Payment"
4. Wait for validation

**Expected Result**: 
- Validation error shown
- Required fields highlighted
- Cannot proceed
- Error message clear

**Tags**: negative, validation, checkout

---

### TC-033: Submit Empty Search
**Type**: Negative | **Priority**: Medium  
**Precondition**: Search bar visible  
**Steps**:
1. Click search bar
2. Leave empty
3. Press Enter or click search
4. Wait for result

**Expected Result**: 
- Either: shows all products
- Or: shows "no search term" message
- Doesn't error
- Graceful handling

**Tags**: negative, search, validation

---

## BOUNDARY TESTS (3 Tests)

### TC-034: Max Product Quantity in Cart
**Type**: Boundary | **Priority**: Medium  
**Data**: Quantity = 999  
**Precondition**: Product in cart  
**Steps**:
1. Go to cart
2. Set quantity to 999
3. Click Update
4. Verify system handles

**Expected Result**: 
- Either: allows order (if warehouse supports)
- Or: limits to maximum (e.g., 100)
- Shows message if limited
- Total recalculated correctly

**Tags**: boundary, cart, quantity

---

### TC-035: Large Price Filter Range
**Type**: Boundary | **Priority**: Low  
**Data**: Price range: $0 - $999,999  
**Precondition**: Products page  
**Steps**:
1. Enter max price: 999999
2. Click Apply
3. Wait for results
4. Verify system handles

**Expected Result**: 
- Query executes without error
- All products within range displayed
- Performance acceptable
- No system crash

**Tags**: boundary, filter, price

---

### TC-036: Special Characters in Search
**Type**: Boundary | **Priority**: Low  
**Data**: Search = "!@#$%^&*()"  
**Precondition**: Search bar visible  
**Steps**:
1. Enter special characters
2. Click search
3. Wait for results

**Expected Result**: 
- No SQL injection or XSS
- Graceful error or "no results"
- No system error
- Application remains stable

**Tags**: boundary, search, security

---

## DATA-DRIVEN TESTS (5 Tests)

### TC-037: Login with Multiple Credentials (Data-Driven)
**Type**: Data-Driven | **Priority**: High  
**Data Source**: Excel file: login_data.xlsx  
**Test Data**:
```
| Email | Password | Expected Result |
|-------|----------|-----------------|
| admin@... | welcome01 | Success |
| customer@... | welcome01 | Success |
| invalid@... | wrong | Fail |
| user@... | blank | Fail |
```

**Precondition**: Excel file with credentials  
**Steps**:
1. Read credentials from Excel
2. For each row:
   - Enter email
   - Enter password
   - Click Login
   - Verify result

**Expected Result**: Each row produces expected result  
**Tags**: data-driven, login, parametrized

---

### TC-038: Product Search with Multiple Terms (Data-Driven)
**Type**: Data-Driven | **Priority**: High  
**Data Source**: Excel file: search_data.xlsx  
**Test Data**:
```
| Search Term | Expected Min Results | Expected Max Results |
|-------------|---------------------|---------------------|
| hammer | 1 | 50 |
| pliers | 1 | 50 |
| drill | 1 | 50 |
| tool | 10 | 100 |
| xyz | 0 | 0 |
```

**Precondition**: Product database loaded  
**Steps**:
1. Read search terms from Excel
2. For each term:
   - Search
   - Count results
   - Verify within range

**Expected Result**: Each search produces expected result count  
**Tags**: data-driven, search, parametrized

---

### TC-039: Add to Cart with Quantity Variations (Data-Driven)
**Type**: Data-Driven | **Priority**: Medium  
**Data Source**: Excel file: cart_data.xlsx  
**Test Data**:
```
| Product | Quantity | Expected Total |
|---------|----------|-----------------|
| Hammer | 1 | $19.99 |
| Hammer | 2 | $39.98 |
| Drill | 1 | $99.99 |
| Drill | 3 | $299.97 |
```

**Precondition**: Logged in, products available  
**Steps**:
1. Read product and quantity from Excel
2. For each row:
   - Add product with quantity
   - Verify total

**Expected Result**: Cart total matches expected value  
**Tags**: data-driven, cart, pricing

---

### TC-040: Checkout with Different Addresses (Data-Driven)
**Type**: Data-Driven | **Priority**: Medium  
**Data Source**: Excel file: address_data.xlsx  
**Test Data**:
```
| First Name | Last Name | Address | City | State | ZIP |
|------------|-----------|---------|------|-------|-----|
| John | Doe | 123 Main | City1 | CA | 12345 |
| Jane | Smith | 456 Oak | City2 | NY | 54321 |
```

**Precondition**: Checkout page, cart filled  
**Steps**:
1. Read address from Excel
2. For each row:
   - Fill address fields
   - Complete checkout
   - Verify order created

**Expected Result**: Order created with correct address  
**Tags**: data-driven, checkout, address

---

### TC-041: Filter Products with Multiple Criteria (Data-Driven)
**Type**: Data-Driven | **Priority**: Medium  
**Data Source**: Excel file: filter_data.xlsx  
**Test Data**:
```
| Category | Min Price | Max Price | Brand | Expected Results |
|----------|-----------|-----------|-------|-----------------|
| Tools | 20 | 50 | Any | 5+ |
| Hand Tools | 10 | 100 | Tool Co | 2+ |
| Power Tools | 100 | 500 | Any | 3+ |
```

**Precondition**: Product page loaded  
**Steps**:
1. Read filter criteria from Excel
2. For each row:
   - Apply all filters
   - Count results
   - Verify > expected

**Expected Result**: Filter combinations work and return expected results  
**Tags**: data-driven, filter, parametrized

---

## SUMMARY

**Total Test Cases**: 41  
**Smoke**: 5  
**Functional**: 13  
**Regression**: 10  
**Negative**: 5  
**Boundary**: 3  
**Data-Driven**: 5  

**Estimated Execution Time**: 5-10 minutes (parallel)  
**Automation Status**: Ready for implementation  

---

**Document Version**: 1.0  
**Date**: April 6, 2026  
**Status**: ✅ APPROVED FOR IMPLEMENTATION

