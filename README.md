# 🛒 E-Commerce Full Stack Application

A modern, responsive, full-stack E-Commerce web application developed using **React.js, Spring Boot, Spring Security, REST APIs, and MySQL**.

The application provides a complete online shopping experience, including user authentication, product management, product searching, shopping cart, wishlist, order management, and user profile management.

## 🚀 Features

### 👤 User Authentication
- User registration and login
- Secure password encryption
- JWT-based authentication
- Spring Security integration
- Protected API endpoints
- Logout functionality

### 🛍️ Product Management
- View available products
- Product details and descriptions
- Search products by name
- Filter products by category
- Sort products by price
- Responsive product cards

### 🛒 Shopping Cart
- Add products to cart
- Remove products from cart
- Update product quantities
- Calculate total cart price
- View cart items

### ❤️ Wishlist
- Add products to wishlist
- Remove products from wishlist
- View saved products

### 📦 Order Management
- Place orders
- View order history
- Check order details
- Manage user-specific orders

### 👤 User Profile
- View profile information
- Edit profile details
- Manage account information

### 🔐 Security
- Spring Security
- JWT authentication
- Password hashing
- Protected REST APIs
- User-specific data access

## 🛠️ Technology Stack

| Layer | Technologies |
|---|---|
| Frontend | React.js, JavaScript, HTML5, CSS3 |
| Styling | Tailwind CSS |
| Backend | Java, Spring Boot |
| Security | Spring Security, JWT |
| Database | MySQL |
| ORM | Spring Data JPA, Hibernate |
| API | REST APIs |
| Build Tools | Maven, npm |
| Development Tools | VS Code, IntelliJ IDEA / STS, Postman |
| Version Control | Git, GitHub |

## 🏗️ Application Architecture

```text
                  USER
                    |
                    v
             React Frontend
                    |
                    v
              REST API Calls
                    |
                    v
           Spring Boot Backend
                    |
          Spring Security + JWT
                    |
                    v
             Service Layer
                    |
                    v
            Repository Layer
                    |
                    v
               MySQL DB
```

## 📁 Project Structure

Example organization:

```text
ECOMMERCE-STACK/
|
|-- frontend/
|   |-- public/
|   |-- src/
|   |   |-- components/
|   |   |-- pages/
|   |   |-- services/
|   |   |-- App.jsx
|   |   |-- main.jsx
|   |
|   |-- package.json
|   |-- vite.config.js
|
|-- backend/
|   |-- src/
|   |   |-- main/
|   |   |   |-- java/
|   |   |   |   |-- controller/
|   |   |   |   |-- service/
|   |   |   |   |-- repository/
|   |   |   |   |-- entity/
|   |   |   |   |-- security/
|   |   |   |
|   |   |   |-- resources/
|   |   |       |-- application.properties
|   |
|   |-- pom.xml
|
|-- README.md
|-- .gitignore
```

*Note: This is an illustrative structure. Actual folder names may differ.*

## ⚙️ Installation and Setup

### Prerequisites

- Java 17 or higher, compatible with the project
- Node.js and npm
- MySQL Server
- Maven
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/ECOMMERCE-STACK.git
cd ECOMMERCE-STACK
```

### 2. Configure the Database

Create a MySQL database:

```sql
CREATE DATABASE ecommerce_db;
```

Configure the backend's `application.properties` or environment variables:

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/ecommerce_db}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
```

Never commit real database passwords, JWT signing secrets, or API keys to GitHub.

### 3. Run the Spring Boot Backend

Navigate to the folder containing `pom.xml`:

```bash
mvn spring-boot:run
```

Default backend URL (if configured):

```text
http://localhost:8080
```

### 4. Run the React Frontend

Navigate to the folder containing `package.json`:

```bash
npm install
npm run dev
```

Default Vite frontend URL:

```text
http://localhost:5173
```

## 🔄 Application Workflow

1. Users register or log in.
2. The frontend communicates with the backend through REST APIs.
3. Spring Security validates authentication.
4. Users browse, search, and filter products.
5. Products can be added to the shopping cart or wishlist.
6. Users place orders.
7. Order and account information is managed through the backend.
8. MySQL stores application data.

## 🎯 Project Objectives

- Develop a complete full-stack application.
- Build reusable React components.
- Implement RESTful backend services.
- Integrate MySQL with Spring Data JPA.
- Implement secure authentication and authorization.
- Create a responsive and user-friendly shopping experience.
- Apply industry-standard development practices.

## 🔮 Future Enhancements

- Online payment gateway integration
- Product reviews and ratings
- Email notifications
- Advanced product recommendations
- Coupon and discount management
- Shipment tracking
- Sales analytics dashboard
- Cloud deployment

## 👨‍💻 Developer

**Valluru Praveen Kumar Reddy**

B.Tech – Computer Science and Engineering  
Mohan Babu University | Class of 2027

**Skills:** Java, Spring Boot, Spring Security, Hibernate, React.js, JavaScript, HTML, CSS, Tailwind CSS, MySQL, REST APIs, Git and GitHub.

## 📄 License

This project is developed for educational, learning, and portfolio purposes. No open-source license is granted unless a license file is added.

---

⭐ If you find this project useful, consider starring the repository!
