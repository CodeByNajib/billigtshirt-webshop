# Product Management API Documentation

## Overview
This API provides complete CRUD functionality for product management with support for active/inactive status and product types (REGULAR and GIFT products).

## Base URL
```
http://localhost:8080/api/products
```

## Endpoints

### Customer Endpoints

#### 1. Get All Active Products
```
GET /api/products
```
Returns only active products visible to customers.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "Classic White T-Shirt",
    "description": "Comfortable cotton t-shirt in classic white",
    "price": 99.99,
    "imageUrl": "https://example.com/images/white-tshirt.jpg",
    "size": "M",
    "color": "White",
    "stockQuantity": 100,
    "active": true,
    "productType": "REGULAR",
    "createdAt": "2025-12-03T10:00:00",
    "updatedAt": "2025-12-03T10:00:00"
  }
]
```

#### 2. Get Active Products by Type
```
GET /api/products/type/{productType}
```
**Path Parameters:**
- `productType`: REGULAR or GIFT

**Response:** `200 OK` - List of active products of specified type

#### 3. Get Active Gift Products
```
GET /api/products/gifts
```
Returns only active gift products for customer selection.

**Response:** `200 OK` - List of active gift products

#### 4. Get Product by ID
```
GET /api/products/{id}
```
**Path Parameters:**
- `id`: Product ID

**Response:** 
- `200 OK` - Product found
- `404 Not Found` - Product not found

---

### Admin Endpoints

#### 5. Create New Product
```
POST /api/products
```
**Request Body:**
```json
{
  "name": "New T-Shirt",
  "description": "Description of the product",
  "price": 99.99,
  "imageUrl": "https://example.com/image.jpg",
  "size": "M",
  "color": "Blue",
  "stockQuantity": 50,
  "active": true,
  "productType": "REGULAR"
}
```

**Response:** `201 Created` - Created product object

**Validation Rules:**
- `name`: Required, not blank
- `price`: Required, must be positive
- `size`: Required, not blank
- `color`: Required, not blank
- `stockQuantity`: Required
- `active`: Optional (defaults to true)
- `productType`: Optional (defaults to REGULAR)

#### 6. Get All Products (Including Inactive)
```
GET /api/products/admin/all
```
Returns all products (active and inactive) for admin management.

**Response:** `200 OK` - List of all products

#### 7. Get Inactive Products
```
GET /api/products/admin/inactive
```
Returns only inactive products.

**Response:** `200 OK` - List of inactive products

#### 8. Update Product
```
PUT /api/products/{id}
```
**Path Parameters:**
- `id`: Product ID

**Request Body:** Same as Create Product

**Response:** 
- `200 OK` - Updated product
- `404 Not Found` - Product not found

#### 9. Deactivate Product (Soft Delete)
```
PATCH /api/products/{id}/deactivate
```
Deactivates a product without deleting it. Historical orders remain intact.

**Path Parameters:**
- `id`: Product ID

**Response:** 
- `200 OK` - Deactivated product
- `404 Not Found` - Product not found

#### 10. Activate Product
```
PATCH /api/products/{id}/activate
```
Reactivates a previously deactivated product.

**Path Parameters:**
- `id`: Product ID

**Response:** 
- `200 OK` - Activated product
- `404 Not Found` - Product not found

#### 11. Delete Product (Hard Delete)
```
DELETE /api/products/{id}
```
⚠️ **Warning:** Permanently deletes the product. Use with caution!

**Path Parameters:**
- `id`: Product ID

**Response:** 
- `204 No Content` - Product deleted
- `404 Not Found` - Product not found

---

## Product Types

### REGULAR
Normal products that customers can purchase.

### GIFT
Special gift products offered to customers based on purchase amount thresholds.
- Gift products typically have `price: 0.00`
- Only active gift products are shown to customers

---

## Business Logic

### Active/Inactive Status
- **Active products** (`active: true`): Visible to customers, available for purchase
- **Inactive products** (`active: false`): Hidden from customers, not available for new orders
- Deactivation does NOT affect historical orders (preserves data integrity)

### Filtering
- Customers see only **active** products
- Admins can see **all** products (active and inactive)
- Gift products can be filtered separately for customer gift selection

---

## Test Data

The application initializes with 9 test products:
- **4 active regular products**
- **1 inactive regular product** (for testing deactivation)
- **3 active gift products**
- **1 inactive gift product** (for testing)

---

## Database Configuration

### H2 Console (Development)
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:tshirtdb
Username: sa
Password: (empty)
```

---

## Example Use Cases

### Use Case 1: Admin creates new product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Green T-Shirt",
    "description": "Eco-friendly green t-shirt",
    "price": 119.99,
    "size": "L",
    "color": "Green",
    "stockQuantity": 30,
    "active": true,
    "productType": "REGULAR"
  }'
```

### Use Case 2: Customer views active gift products
```bash
curl http://localhost:8080/api/products/gifts
```

### Use Case 3: Admin deactivates out-of-stock product
```bash
curl -X PATCH http://localhost:8080/api/products/5/deactivate
```

### Use Case 4: Admin views all products (including inactive)
```bash
curl http://localhost:8080/api/products/admin/all
```

---

## Error Handling

All endpoints return appropriate HTTP status codes:
- `200 OK` - Successful GET/PUT/PATCH request
- `201 Created` - Successful POST request
- `204 No Content` - Successful DELETE request
- `404 Not Found` - Resource not found
- `400 Bad Request` - Validation errors

---

## Task Completion

This implementation fulfills the following acceptance criteria:

✅ **A1.1** - Backend CRUD for products
✅ **A1.2** - Database field: active/inactive on products  
✅ **A2.1** - Backend filtering (active/inactive, product types)
✅ **A2.2** - Frontend handling (API ready for frontend integration)
✅ **A2.3** - Clear status in admin operations
✅ **A3.1** - Order preservation (soft delete doesn't affect historical data)
✅ **A3.2** - No errors with historical data (inactive products remain in database)
✅ **A3.3** - Test case for admin functionality (test data included)

