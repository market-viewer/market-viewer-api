# Market Viewer API

Backend REST API for the **Market Viewer** — a device that displays real-time market data (stocks, crypto, clock, AI text) on small microcontroller with display.

> ⚠️ Project is still work in progress

---

## Prerequisites

- Docker & Docker Compose
- A `.env` file in the project root (see template below)

---

## Environment Variables

Create a `.env` file in the project root:

```dotenv
#db connection
POSTGRES_DB=
POSTGRES_USER=
POSTGRES_PASSWORD=

#encryption
ENCRYPT_PASSWORD=
ENCRYPT_SALT=

GEMINI_MODEL=

#configuation
DEPLOY_ENVIROMENT=(local / prod)
FRONTEND_URL=
SHOW_ERROR_TRACE=

#JWT
JWT_SECRET=
#24h
JWT_EXPIRATION=

#github sso
GITHUB_CLIENT_ID=
GITHUB_CLIENT_SECRET=
```

---

## Running with Docker

**Production (build + run everything):**

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080`.

---
### API docs

Full interactive docs available at `https://market-viewer.jotalac.dev/api/swagger-ui/index.html`.
- or `http://localhost:8080/api/swagger-ui/index.html` - when you run the app locally

---

## Supported data providers

- **Crypto**:
  - coingecko
- **Stock**
  - twelvedata
