# UI + Privacy Update

- Collection: improved visible controls in light/dark modes; mobile filter drawer, categories, type, price range, search, sorting, URL-shareable filters, empty results.
- Homepage: refreshed hero, CTAs, category navigation, editorial promo, improved product cards.
- Authentication and browser storage: auth token/user and local cart/orders/wishlist use sessionStorage instead of localStorage. Logout clears these keys and resets React context immediately, avoiding another account seeing browser-local private data. Login also clears prior browser-local data. Legacy localStorage private keys are cleaned on app startup. Theme choice remains saved as a non-private preference.
- **Important:** Logging out does **not** delete an account, server-side MySQL records, previously placed server-side orders, or backend-side carts. Users normally expect order history and their accounts to persist for future logins. Browser tab sessionStorage may survive a tab refresh but not a closed tab. Current cart/checkout/order frontend remains browser-only, not connected to backend database.
- Start backend: export DB_PASSWORD and a stable JWT_SECRET then `mvn spring-boot:run` in backend. Start frontend: `npm install && npm run dev` in root.
