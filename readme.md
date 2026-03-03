# Market Viewer API

Backend REST API for the **Market Viewer** — a device that displays real-time market data (stocks, crypto, clock, AI text) on configurable screens.

> ⚠️ Project is still work in progress

---

## Tech Stack

- **Java 25** · **Spring Boot 4**
- **PostgreSQL 17**
- **JWT** authentication
- **Docker** / **Docker Compose**

---

## Prerequisites

- [Docker](https://docs.docker.com/get-docker/) & Docker Compose
- A `.env` file in the project root (see template below)

---

## Environment Variables

Create a `.env` file in the project root:

```dotenv
# PostgreSQL
POSTGRES_DB=market_viewer
POSTGRES_USER=your_db_user
POSTGRES_PASSWORD=your_db_password

# JWT
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION_MS=86400000
```

---

## Running with Docker

**Production (build + run everything):**

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080`.

**Development (local database only, run the app from your IDE):**

```bash
docker compose -f docker-compose-dev.yml up
```

The dev database listens on port `5433`.

---

## API Overview

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `POST` | `/auth/register` | — | Register a new user |
| `POST` | `/auth/login` | — | Login and receive a JWT |
| `POST` | `/user/apiKey` | JWT | Add / update an API key |
| `GET` | `/user/apiKey` | JWT | List user API keys |
| `DELETE` | `/user/apiKey` | JWT | Remove an API key |
| `GET` | `/user/device` | JWT | List user devices |
| `POST` | `/device` | JWT | Create a device |
| `DELETE` | `/device/{deviceId}` | JWT | Delete a device |
| `GET` | `/device/{deviceId}/screen` | JWT | List screens on a device |
| `POST` | `/device/{deviceId}/screen` | JWT | Add a screen |
| `PUT` | `/device/{deviceId}/screen/{screenId}` | JWT | Update a screen |
| `DELETE` | `/device/{deviceId}/screen/{screenId}` | JWT | Remove a screen |
| `PATCH` | `/device/{deviceId}/screen/order` | JWT | Reorder screens |
| `GET` | `/hardware/{deviceHash}/screen` | Hash | Fetch all screens (device) |
| `GET` | `/hardware/{deviceHash}/screen/{position}` | Hash | Fetch screen at position |
| `GET` | `/hardware/{deviceHash}/screen/{position}/data` | Hash | Fetch live screen data |

Full interactive docs available at `http://localhost:8080/swagger-ui.html` when the app is running.

---

## Screen Types

Screens on a device can display one of the following content types:

- **Stock** — real-time stock ticker data
- **Crypto** — cryptocurrency prices
- **Clock** — current time / timezone
- **AI Text** — AI-generated text content
