This project is a backend system that processes grocery product and discount data from multiple stores and exposes various price analysis features through REST API's.

## 1. Project Structure Overview:

### controller/   
API endpoints (e.g., discounts, basket, alerts)

### service/   
Business logic, interfaces + implementations

### model/   
Product and Discount data models

### dto/   
Request and response objects for API

## config/   
Virtual date provider (AppDateProvider)

## util/   
Helpers for unit conversion and filename parsing

## 2. How to Build and Run

### Prerequisites:
- Java 17+
- Maven

### Run Locally:
```bash
mvn clean install
mvn spring-boot:run
```
## 3. Assumptions and Simplifications

CSVs use ; as a delimiter and are located in resources/data

A virtual "today" date is used, defaulting to 2025-05-01, and can be updated via API

No database is used — data is loaded into memory from CSV at app startup

Only the latest offer per store (on or before today) is considered in relevant features

Units like g/ml are normalized to kg/l for price per unit comparison

## 4. Features & Example Endpoints
### Manage Virtual Date

GET /api/date → Current virtual "today"

POST /api/date?date=2025-05-08 → Set new virtual date

### Discounts

GET /api/discounts/best?top=5 → Top 5 current discounts

GET /api/discounts/new → Discounts starting today

### Price History

GET /api/products/P001/price-history

Returns historical prices (optionally filter by store, brand, category)

### Best Value per Unit

GET /api/products/best-value?productName=lapte zuzu

Shows latest offers sorted by price per kg/l/unit(best buys)


### Basket Optimization

POST /api/basket/optimize

Request:
{ "productNames": ["lapte zuzu", "spaghetti nr.5"] }

Response:
{
  "items": [{ "productName": "lapte zuzu", "store": "kaufland", "price": 9.90 }], 
  "totalPrice": 15.60
}

### Price Alert

POST /api/alerts/check

Request: { "productName": "lapte zuzu", "targetPrice": 10.00 }



