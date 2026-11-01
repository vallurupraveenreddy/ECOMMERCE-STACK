# E-commerce Store — React + Spring Boot + MySQL

This project began as a frontend-only Vite/React storefront. This release adds a separate Spring Boot REST backend under `backend/` and repairs several frontend issues.

## What is included

**React app:** storefront and product assets; collection filters/sorting; cart, wishlist and orders saved in browser localStorage; real API-based login/signup; click-to-open account menu; responsive UI.

**Spring Boot REST API:** BCrypt signup/login, signed JWT authentication, account profile/password change, product catalog with search/filter/sort/pagination, cart, wishlist, addresses, coupons, COD checkout with database inventory adjustment, order history/cancellation, verified-customer reviews, contact messages and admin endpoints.

**Integration status:** Login/signup talks to Spring Boot. The rest of the original storefront still uses its local product catalog and localStorage cart/orders/wishlist. The new Java REST endpoints are available through `src/api/client.js`, but **not all pages are wired to them yet**. The storefront's checkout is a **local demo** and does **not** create a database order. Use the documented APIs for real persisted orders. Neither Stripe nor Razorpay is integrated; COD is the only supported backend method.

## Prerequisites

- Java 17 or newer (Java 17 recommended)
- Maven 3.9+
- MySQL server running on port 3306
- Node.js 20+ and npm

## 1. Create the MySQL database

```bash
/usr/local/mysql/bin/mysql -u root -p
```

```sql
CREATE DATABASE IF NOT EXISTS ecommerce_db;
EXIT;
```

## 2. Set environment variables on macOS

```bash
export DB_USERNAME=root
export DB_PASSWORD='YOUR_MYSQL_PASSWORD'
export JWT_SECRET="$(openssl rand -hex 32)"
export CORS_ORIGIN=http://localhost:5173
```

`DB_PASSWORD` and `JWT_SECRET` must be configured for each new terminal session or loaded from a private local environment file. **Do not commit passwords or JWT secrets.** The JDBC URL currently defaults to MySQL without TLS and `allowPublicKeyRetrieval=true`, suitable only for trusted local development; use TLS for deployment.

## 3. Start the backend

```bash
cd backend
mvn spring-boot:run
```

The backend should listen on `http://localhost:8080` and health is available at `http://localhost:8080/actuator/health`. Initial startup requires correct MySQL credentials. Hibernate is configured to update the development schema. For production use migrations and controlled permissions.

## 4. Start the frontend in another terminal

```bash
npm install
cp .env.example .env
npm run dev
```

Open `http://localhost:5173` (or the URL printed by Vite). Register a new account from the Login page.

## Main backend endpoints

| Method | URL | Purpose |
|---|---|---|
| POST | `/api/auth/register` | Create user |
| POST | `/api/auth/login` | Log in and return JWT |
| GET/PUT | `/api/auth/me` | View/update account |
| PUT | `/api/auth/password` | Change password |
| GET | `/api/products?q=&category=&sort=price-asc&page=0&size=12` | Product catalog |
| GET | `/api/products/{id}` | Product detail |
| GET/PUT/DELETE | `/api/cart` | Read/update/clear cart |
| GET/POST/DELETE | `/api/wishlist` and `/api/wishlist/{id}` | Wishlists |
| GET/POST/PUT/DELETE | `/api/addresses` and `/api/addresses/{id}` | Addresses |
| GET | `/api/coupons/check?code=SAVE10&subtotal=1000` | Validate coupon |
| GET/POST | `/api/orders` | History/checkout |
| GET | `/api/orders/{id}` | Order detail |
| POST | `/api/orders/{id}/cancel` | Cancel unshipped order |
| GET/POST | `/api/reviews/product/{id}` | Reviews |
| POST | `/api/contact` | Contact |
| GET | `/api/admin/dashboard` | Admin totals |
| POST/PUT/DELETE | `/api/admin/products` and `/api/admin/products/{id}` | Manage catalog |
| GET/PUT | `/api/admin/orders` and `/api/admin/orders/{id}/status` | Orders |
| GET/POST | `/api/admin/coupons` | Coupons |
| GET | `/api/admin/users` | Users |
| PUT | `/api/admin/users/{id}/enabled` | Block/unblock |
| GET | `/api/admin/messages` | Contact submissions |

For protected endpoints, pass `Authorization: Bearer <token>`. Most POST/PUT routes accept JSON request bodies.

### Examples

Register:

```bash
curl -X POST http://localhost:8080/api/auth/register -H 'Content-Type: application/json' -d '{"name":"Demo User","email":"demo@example.com","password":"strongpassword123"}'
```

Create a product with an administrator token:

```bash
curl -X POST http://localhost:8080/api/admin/products -H 'Content-Type: application/json' -H 'Authorization: Bearer ADMIN_TOKEN' -d '{"name":"Cotton Shirt","description":"Comfort cotton shirt","category":"Men","subCategory":"Topwear","price":899,"originalPrice":1199,"imageUrl":"/images/shirt.png","sizes":"S,M,L,XL","stock":20,"active":true}'
```

Set an item in the persisted cart:

```bash
curl -X PUT http://localhost:8080/api/cart -H 'Content-Type: application/json' -H 'Authorization: Bearer USER_TOKEN' -d '{"productId":1,"size":"M","quantity":2}'
```

Place an order after creating an address:

```bash
curl -X POST http://localhost:8080/api/orders -H 'Content-Type: application/json' -H 'Authorization: Bearer USER_TOKEN' -d '{"addressId":1,"paymentMethod":"COD","couponCode":""}'
```

**Admin setup:** There is deliberately no default admin password. Register a personal account and manually assign `ADMIN` to that one trusted account in your local database if needed:

```sql
UPDATE app_users SET role='ADMIN' WHERE email='your-email@example.com';
```

Sign in again to use the admin endpoints.

## Known limits and next steps

- Existing frontend product `_id` strings are not yet mapped to MySQL numeric product IDs; populate the database and wire collection/product/cart/order React pages to the backend before calling the application fully integrated.
- Online payment gateway, webhook-based confirmation/refunds, email delivery and password-reset-by-email are **not implemented**.
- Admin APIs exist but a complete React admin dashboard does not yet exist.
- This is a development starter. Add database migrations, concurrency tests, rate limiting, token revocation, security hardening, email verification and automated integration tests before deploying.
- No compilation, build or integration test was executed in this environment because Maven dependencies and the frontend node_modules were not available.
