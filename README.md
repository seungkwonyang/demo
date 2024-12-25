# Product API Documentation

## Overview
The Product API allows clients to:
- Retrieve the brand and price of the product with the minimum and maximum prices within a specified category.

## Project 구성
- admin-api : 상품 등록을 위한 admin
- serving-api : 상품 조회를 위한 serving api
- batch-job : 빠른 조회를 위한 사전에 복잡한 비지니스 요건에 대응하기 위한 cache layer

## application 별 port
- admin-api : 기본 8080
- serving-api : 9091
- batch-job : 9090

## 사전 필요작업
- redis설치 : batch job과 api를 실행하기 위해 필요
1. redis 설치
    ```bash
    brew install redis
    ```
2. redis 접속
    ```bash
    redis-cli
    ```
## application 별 swagger 주소
- admin-api : http://localhost:8080/swagger-ui/index.html
- serving-api : http://localhost:9091/swagger-ui/index.html
- batch-job : http://localhost:9090/swagger-ui/index.html
---
## Setup Instructions

### Prerequisites
- Java 17+
- Gradle 7.x

### Steps
1. Clone the repository:
    ```bash
    git clone <repository_url>
    ```

2. Navigate to the project directory:
    ```bash
    cd demo
    ```

3. Run the application:
    ```bash
    ./gradlew bootRun
    ```

4. Access the Swagger UI:
    ```
    http://localhost:8080/swagger-ui.html
    ```

---
## Error Handling
### **Error Codes**

| Code                | Message                                  | Description                          |
|---------------------|------------------------------------------|--------------------------------------|
| `SUCCESS`           | Operation completed successfully.        | Used for successful operations.     |
| `INVALID_ARGUMENT`  | Invalid argument provided.               | Used when a request contains invalid data. |
| `BRAND_NOT_FOUND`   | Brand not found.                         | Used when a specified brand cannot be found. |
| `PRODUCT_NOT_FOUND` | Product not found.                       | Used when a specified product cannot be found. |
| `UNEXPECTED_ERROR`  | An unexpected error occurred.            | Used for general, unhandled exceptions. |
| `RUNTIME_EXCEPTION` | A runtime exception occurred.            | Used when an unexpected runtime exception occurs. |

### Error Response
In case of an error, the API returns the following structure:

#### Response Structure

| Field    | Type     | Description                           |
|----------|----------|---------------------------------------|
| `code`   | String   | The response code indicating status.  |
| `message`| String   | A message providing additional context. |
| `data`   | Object   | The response data. Can be `null`.     |

## Example Success Response

```json
{
  "code": "SUCCESS",
  "message": "Operation completed successfully.",
  "data": {
    "id": 1,
    "name": "Brand A"
  }
}
```
---

## Endpoints

### 1. **Get Price Range by Category**
Retrieve the minimum and maximum price products for a given category code.

#### **URL**
`GET /api/products/price-range`

#### **Request Parameters**
| Name           | Type   | Required | Description                     |
|-----------------|--------|----------|---------------------------------|
| `categoryCode`  | String | Yes      | The code of the category to query. |

#### **Response**
| Field             | Type                  | Description                                   |
|-------------------|-----------------------|-----------------------------------------------|
| `code`            | String                | Response code.                                |
| `message`         | String                | Response message.                             |
| `data`            | Object                | The price range data.                         |
| `minPriceProduct` | Object                | Details of the product with the minimum price.|
| `maxPriceProduct` | Object                | Details of the product with the maximum price.|

#### **`minPriceProduct` and `maxPriceProduct` Fields**
| Field    | Type   | Description                 |
|----------|--------|-----------------------------|
| `brand`  | String | Name of the brand.          |
| `price`  | Int    | Price of the product.       |

---

## Example

#### **Request Body**
```json
{
    "name": "New Brand"
}
```

### **Response**
```json
{
  "code": "SUCCESS",
  "message": "Brand created successfully.",
  "data": {
    "id": 1,
    "name": "New Brand"
  }
}
```
---

### 2. **Add a Brand**
Create a new brand.

#### **URL**
`POST /api/brands`

#### **Request Body**
```json
{
    "name": "New Brand"
}
```

#### **Response Body**
```json
{
  "code": "SUCCESS",
  "message": "Brand created successfully.",
  "data": {
    "id": 1,
    "name": "New Brand"
  }
}

```

### 3. **Update a Brand**
Update an existing brand

#### **URL**
`PUT /api/brands/{id}`

#### **Request Body**
```json
{
  "name": "Updated Brand"
}

```

#### **Response Body**
```json
{
  "code": "SUCCESS",
  "message": "Brand updated successfully.",
  "data": {
    "id": 1,
    "name": "Updated Brand"
  }
}

```


### 4. **Delete a Brand**
Delete an existing brand.

#### **URL**
`DELETE /api/brands/{id}`

#### **Response Body**
```json
{
  "code": "SUCCESS",
  "message": "Brand deleted successfully.",
  "data": null
}


```
