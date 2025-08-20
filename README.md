# Expense Manager Riverty

The backend for the expense manager utilizes Spring Boot with a layered architecture with the controller delegating to service classes. It uses an in-memory H2 database and has a layered architecture (controller → service → repository) with validation and error handling. It implements a Command/Query pattern and maps the entities to DTOs.

## Features

- **CRUD operations** for expenses with amount, description, date, category and optional tags.
- **Filtering and pagination** of expenses by category and/or date range via `/expenses`.
- **Search by description** using `/expense/search`.
- **Monthly summary** of current month's expenses compared to a budget including category breakdown via `/expense/summary`.
- **Validation and global exception handling** for clean error responses.
- **H2 database** available at `/h2-console` and CORS configured for an Angular client on `http://localhost:4200`.

## Project Structure

```
src/main/java/com/example/expense_manager_riverty
├── config        # CORS and H2 console configuration
├── controller    # REST endpoints
├── entity        # JPA entities and DTOs
├── exception     # Custom exceptions and handlers
├── repository    # Spring Data JPA repositories
└── service       # Business logic
```

### API Endpoints

```
GET /expenses?category=&startDate=&endDate=            -> List expenses (with category and date filter)
GET /expense/{id}                                      -> Retrieve single expense
GET /expense/search?description=keyword                -> Search expenses by description
GET /expense/summary?budget=1000                       -> Current month summary
POST /expense                                          -> Create new expense
PUT /expense/{id}                                      -> Update expense
DELETE /expense/{id}                                   -> Remove expense
```

## Getting Started

1. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
2. **Run tests**
   ```bash
   ./mvnw test
   ```
3. Access H2 console: `http://localhost:8080/h2-console`

## Reflections on the development process

### What went well and was interesting

- Initial structure yielded for effective abstraction and implementation of the core features for the application, keeping a clean and manageable infrastructure
- Test development process in Spring Boot as well the usage of H2 was a new, but interesting experience. Learning how to implement using the documentation as well as resources on the Internet and using my knowledge from PHP-based back-end development was useful for me.

### What was more difficult

- Error handling approach could benefit from a clearer structure and abstraction, that helps with passing on descriptive errors onto the front-end, helping the customer, developer and the tester.
- Initially, coming up with tests was a bit more difficult, but eventually, once the first few tests were developed, it was an easier process.
- For the scale of this app, some further abstraction could be beneficial for the future application, whilst less abstraction could have been employed elsewhere.

## Future Improvements

- Authentication & authorization for implementing users.
- Adapting a more persistent database as well as error handling approach.
- Recurring expenses, implementation of incomes (as opposed to expenses), CSV/Excel exports, further analytics.
- More comprehensive unit/integration tests.
- REST documentation (OpenAPI/Swagger).
