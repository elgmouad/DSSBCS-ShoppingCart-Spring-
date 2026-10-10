# Shopping Cart

A server-rendered shopping cart application built with Spring Boot. Visitors can browse products and manage a session-based cart. Administrators can manage the product catalogue and download a SQL export.

## Features

- Browse products and filter by name or price range.
- Add products to a session-based shopping cart, remove them, and view the total.
- Create, edit, and delete products as an administrator.
- Sign in and sign out using form-based authentication.
- Download the product catalogue as an SQL file.
- View OpenAPI documentation through Swagger UI.

## Technology stack

| Area | Technology |
| --- | --- |
| Language | Java 25 |
| Framework | Spring Boot 4.1.1, Spring MVC |
| Views | Thymeleaf, Bootstrap |
| Security | Spring Security |
| Persistence | Spring Data JPA, Hibernate |
| Database | H2 in-memory database |
| Build | Maven, with the Maven Wrapper included |
| Tests | JUnit 5, Mockito, Spring Boot Test |

## Requirements

- Java 25
- IntelliJ IDEA or another Java IDE (optional)

The included Maven Wrapper downloads and runs the required Maven version, so a separate Maven installation is not necessary.

## Run the application

Run these commands from the project root.

**Windows PowerShell**

```powershell
.\mvnw.cmd spring-boot:run
```

**macOS or Linux**

```bash
./mvnw spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080) in a browser.

## Users and permissions

The sample accounts are stored in the configured database and loaded by `DatabaseUserDetailsService`. On startup, the application creates the default accounts if they do not already exist. Passwords are BCrypt-encoded before they are stored, and roles are persisted alongside each account.

| Account | Roles | Capabilities |
| --- | --- | --- |
| Anonymous visitor | None | View the home page, product catalogue, login page, cart, static resources, and API documentation. |
| `user` / `user` | `USER` | All anonymous capabilities; can also access routes requiring authentication. Cannot manage products or access administration pages. |
| `admin` / `admin` | `ADMIN`, `USER` | All `USER` capabilities; can view the admin dashboard, add/edit/delete products, and download the SQL export. |

These credentials are for local development and must not be used in a production deployment. Authenticated users without the required role are redirected to `/products?accessDenied=true`.

## Routes

### Home and authentication

| Method | Path | Access | Description |
| --- | --- | --- | --- |
| `GET` | `/` | Public | Display the home page. |
| `GET` | `/index` | Public | Home page alias. |
| `GET` | `/login` | Public | Display the login form. |
| `POST` | `/login` | Public | Process login credentials. Successful login redirects to `/`. |
| `POST` | `/logout` | Public | End the current session and redirect to `/login?logout`. |

### Product catalogue and management

| Method | Path | Access | Description |
| --- | --- | --- | --- |
| `GET` | `/products` | Public | Display products. Optional filters: `name`, `minPrice`, and `maxPrice`. |
| `GET` | `/products/add` | `ADMIN` | Display the new-product form. |
| `GET` | `/products/edit/{id}` | `ADMIN` | Display the edit form for a product. |
| `POST` | `/products/save` | `ADMIN` | Validate and save a new or edited product. |
| `POST` | `/products/delete/{id}` | `ADMIN` | Delete a product. |

The `name` filter matches product names. `minPrice` and `maxPrice` are inclusive price limits. Product forms validate that a name and a non-negative price are provided.

### Shopping cart

Cart contents are scoped to the visitor's HTTP session. The cart routes are publicly accessible.

| Method | Path | Access | Description |
| --- | --- | --- | --- |
| `GET` | `/cart` | Public | Display cart items and the total. |
| `POST` | `/cart/add/{id}` | Public | Add a product to the cart. |
| `POST` | `/cart/remove/{id}` | Public | Remove all instances of a product from the cart. |
| `POST` | `/cart/clear` | Public | Empty the cart. |

### Administration and developer tools

| Method | Path | Access | Description |
| --- | --- | --- | --- |
| `GET` | `/admin` | `ADMIN` | Display the administration dashboard and product list. |
| `GET` | `/admin/export` | `ADMIN` | Download the product SQL export as `products.sql`. |
| `GET` | `/admin/download-db-sql` | `ADMIN` | Alias for the SQL export. |
| `GET` | `/swagger-ui/index.html` | Public | Open Swagger UI. |
| `GET` | `/v3/api-docs` | Public | Retrieve the OpenAPI specification. |
| `GET` | `/h2-console` | Public when enabled | Open the H2 database console. |

Static resources under `/css/**`, `/js/**`, `/images/**`, and `/webjars/**` are public. CSRF protection applies to state-changing requests; only the H2 console is excluded.

## Database

The default database is H2 in-memory, configured in `src/main/resources/application.properties` as `jdbc:h2:mem:testdb`. On startup, the application inserts 12 sample products only if the product table is empty. Because the database is in memory, its data is cleared when the application stops.

## Project structure

| Path | Responsibility |
| --- | --- |
| `src/main/java/org/example/shoppingcart/controller` | MVC controllers and web routes. |
| `src/main/java/org/example/shoppingcart/config` | Spring Security and application configuration. |
| `src/main/java/org/example/shoppingcart/service` | Product, cart, and SQL export services. |
| `src/main/java/org/example/shoppingcart/repository` | Spring Data JPA repositories. |
| `src/main/java/org/example/shoppingcart/model` | JPA entities. |
| `src/main/java/org/example/shoppingcart/dto` | Data-transfer objects. |
| `src/main/resources/templates` | Thymeleaf page templates. |
| `src/main/resources/static` | CSS and other static resources. |
