# E-commerce upgrade notes

## New features in this build
- Reactive login/logout state via `AuthContext`; navigation updates immediately after login.
- Account dropdown for **My Orders**, **My Profile**, **Wishlist**, appearance and logout.
- Theme picker: **Light**, **Dark**, **Auto (system)** with saved choice.
- Auth-protected profile and orders pages; login redirects back to the requested page.
- Editable profile persisted using Spring Boot `PUT /api/auth/me`; username, phone, shipping address, city, state, postal code, country, date of birth, gender. Email intentionally read-only.
- Password change form using `PUT /api/auth/password`.
- Login/signup form has confirm password, show/hide password and proper error handling.
- Responsive mobile menu, account greeting, click-outside/escape-to-close dropdown.
- Contact page submits to Spring Boot and FAQ page includes accessible expandable answers.
- Fixed duplicated ShopContext provider at application root.

## Important current limitations
- Frontend cart/checkout/orders still use original browser-state implementation; they are not yet synchronized with Spring Boot order APIs. Do not regard browser order history as real fulfillment.
- Online payment gateway, email reset links, shipment tracking and upload storage are **not implemented**.
- `spring.jpa.hibernate.ddl-auto=update` creates new profile columns on development MySQL; back up data before schema changes.
- The Java build and full end-to-end integration have not been verified in this environment.

## Run
In `backend/`: `export DB_PASSWORD='your_mysql_password'` and `export JWT_SECRET="$(openssl rand -hex 32)"`, then `mvn spring-boot:run`. Keep the same JWT secret across restarts.
In project root: `npm install && npm run dev`. Verify MySQL has `ecommerce_db`.
