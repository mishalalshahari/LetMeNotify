# LetMeNotify
**AI-Enhanced, Event-Driven Notification Service (Spring Boot)**

---

## 📌 Overview

**LetMeNotify** is a **backend-first, production-oriented notification service** built using **Spring Boot** and **Java 21**.  
It provides **template-based notifications**, **optional AI-powered content enhancement**, and **async event-driven processing** with strong reliability guarantees.

The service is designed to be **plug-and-play** for large-scale systems such as:

- E-commerce platforms
- Fintech applications
- SaaS products
- Enterprise internal tools

> ⚠️ **AI is treated as an enhancement — never a dependency.**  
If AI fails or is disabled, the system continues to work reliably.

---

## ✨ Key Features

- 🔔 Template-based notifications (Email / SMS / Push ready)
- 🤖 Optional AI content enhancement (OpenAI integration)
- ⚡ Async, event-driven processing (non-blocking APIs)
- 🛡 Resilience4j integration (rate limiting, retries, fallbacks)
- 🚀 Caching of AI responses (Redis-ready)
- 🧱 Clean architecture with strict separation of concerns
- 🐳 Docker-ready (optional)
- 📈 Scalable & extensible design

---

## 🧠 High-Level Architecture

```
Client
  |
  | POST /api/notifications
  |
Controller (HTTP 202 Accepted)
  |
ApplicationEventPublisher
  |
@Async Event Listener
  |
NotificationService
  ├─ Template Resolution
  ├─ AI Enhancer (Cache + Rate Limit)
  └─ MySQL Persistence
```

✔️ Request thread returns immediately  
✔️ Processing happens asynchronously  
✔️ Failures are isolated and handled gracefully

---

## 🛠 Tech Stack

| Category | Technology                           |
|--------|--------------------------------------|
| Language | Java 21                              |
| Framework | Spring Boot                          |
| Async | Spring Events + @Async (Kafka-ready) |
| AI Integration | OpenAI API                           |
| Resilience | Resilience4j                         |
| Caching | Spring Cache (Redis-ready)           |
| Database | MySQL                                |
| Build Tool | Maven                                |
| Containerization | Docker / Docker Compose              |

---

## 📦 Project Structure

```
com.project.letmenotify
 ├─ controller
 ├─ service
 ├─ event
 ├─ ai
 ├─ rules
 ├─ repository
 ├─ model
 ├─ config
```

---

## 🚀 Getting Started

### Prerequisites
- Java 21
- Maven 3.9+
- MySQL 8.x
- (Optional) Docker

### Clone Repository
```
git clone https://github.com/mishalalshahari/LetMeNotify.git
cd LetMeNotify
```

### Database Setup
```
CREATE DATABASE <db_name>;
```

Update `application.properties`:
```
spring.datasource.url=jdbc:<db_host>
spring.datasource.username=<username>
spring.datasource.password=<password>
```

---

## 📩 API Usage

### POST /api/notifications

Sample request:
```
{
  "eventType": "PAYMENT_FAILED",
  "userId": 101,
  "context": {
    "name": "User1 K",
    "amount": "6554",
    "dueDate": "2025-01-20"
  }
}
```

Response:
```
202 Accepted
Notification accepted for async processing
```

---

## 📝 Notification Templates

Templates are stored in `notification_template` table.

```
INSERT INTO notification_template (
  event_type,
  channel,
  template_text,
  tone
)
VALUES (
  'PAYMENT_FAILED',
  'EMAIL',
  'Hi {{name}}, your payment of ₹{{amount}} failed. Please retry before {{dueDate}}.',
  'FRIENDLY'
);
```

---

## 🔮 Future Enhancements

- Redis distributed cache
- Kafka / RabbitMQ
- Delivery adapters (Email / SMS / Push)
- Admin UI
- Observability (Prometheus + Grafana)
- DLQ & PII masking

---

## 👨‍💻 Author

**Mishal Al Shahari**
