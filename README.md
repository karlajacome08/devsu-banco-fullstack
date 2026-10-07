# Banking Full-Stack App — Spring Boot + Angular

Full-stack banking management system built as a technical assessment for **Devsu**. It covers customers, accounts, transactions with business-rule validation, and account-statement reports (JSON or PDF). The backend and database run with a single Docker Compose command.

![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-6DB33F) ![Angular](https://img.shields.io/badge/Angular-20-DD0031) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791) ![Docker](https://img.shields.io/badge/Docker-Compose-2496ED)

---

## Features

- **Customers (`/clientes`)**: full CRUD. `Cliente` extends `Persona` through JPA `JOINED` inheritance.
- **Accounts (`/cuentas`)**: full CRUD for accounts (number, type, opening balance, status) linked to a customer.
- **Transactions (`/movimientos`)**: deposits and withdrawals that update the balance automatically. Two business rules are enforced:
  - Withdrawing from a zero balance is rejected with **"Saldo no disponible"**.
  - Withdrawals are capped at **USD 1,000 per day per account** (`CupoDiarioExcedidoException`).
- **Reports (`/reportes`)**: account statement for a customer over a date range, returned as **JSON or PDF** (generated with iText).
- **Global error handling** through `@ControllerAdvice`, which returns consistent error responses.
- **Angular 20 SPA** with list and form views for customers, accounts, transactions and reports, plus a sidebar layout.

## Tech stack

| Layer | Technologies |
|---|---|
| Backend | Java 17, Spring Boot 4 (Web MVC, Data JPA, Validation), Lombok, iText PDF |
| Database | PostgreSQL 16 (schema script in `api-banco/BaseDatos.sql`) |
| Frontend | Angular 20, TypeScript, RxJS |
| Testing | JUnit 5 + Mockito (service layer), Jasmine + Karma (frontend) |
| DevOps | Multi-stage Dockerfile, Docker Compose, Postman collection |

## Architecture

```
devsu-banco-fullstack/
├── api-banco/                  # Spring Boot REST API
│   ├── controller/             # REST endpoints + DTOs
│   ├── service/                # Interfaces + implementations (business rules)
│   ├── repository/             # Spring Data JPA repositories
│   ├── model/                  # JPA entities (Persona → Cliente, Cuenta, Movimiento)
│   ├── exception/              # Custom exceptions + global handler
│   ├── config/                 # CORS configuration
│   ├── Dockerfile              # Multi-stage build (Maven → JRE 17)
│   ├── docker-compose.yml      # PostgreSQL 16 + backend
│   └── BaseDatos.sql           # Database schema
├── banco-frontend/             # Angular 20 SPA
│   └── src/app/
│       ├── clientes/  cuentas/  movimientos/  reportes/
│       ├── services/  models/
│       └── layout/sidebar/
└── Devsu-Fullstack.postman_collection.json
```

The backend uses a layered architecture (**Controller → Service → Repository**). Services are coded against interfaces so the business logic is decoupled and easy to unit-test with mocks.

## Getting started

### Prerequisites
- Docker and Docker Compose
- Node.js 20+ and Angular CLI (for the frontend)

### 1. Backend + database

```bash
cd api-banco
docker compose up --build
```

- API: `http://localhost:8080`
- PostgreSQL: `localhost:5432`

### 2. Frontend

```bash
cd banco-frontend
npm install
npm start
```

Open `http://localhost:4200`.

### 3. Try the API
Import `Devsu-Fullstack.postman_collection.json` into Postman to get ready-made requests for every endpoint.

## API reference

| Method | Endpoint | Description |
|---|---|---|
| GET / POST | `/clientes` | List / create customers |
| GET / PUT / DELETE | `/clientes/{id}` | Get / update / delete a customer |
| GET / POST | `/cuentas` | List / create accounts |
| GET / PUT / DELETE | `/cuentas/{id}` | Get / update / delete an account |
| GET / POST | `/movimientos` | List / register a transaction |
| GET / DELETE | `/movimientos/{id}` | Get / delete a transaction |
| GET | `/reportes?clientId={id}&fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD&formato=json\|pdf` | Account statement |

## Tests

```bash
# Backend unit tests (service layer)
cd api-banco
./mvnw test

# Frontend unit tests
cd banco-frontend
npm test
```

The backend tests cover `ClienteServiceImpl`, `CuentaServiceImpl` and `MovimientoServiceImpl`: successful deposits and withdrawals, insufficient balance, and non-existent accounts.

---

**Author:** Karla Jácome · Full-Stack Developer (Angular · .NET · Java · Python)
[LinkedIn](https://www.linkedin.com/in/karlajacome) · [GitHub](https://github.com/karlajacome08)
