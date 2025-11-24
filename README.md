# Banking System Simulator – Spring Boot + MongoDB

A monolithic backend application that provides core banking features such as account creation, deposit, withdrawal, transfer, account modification, and transaction history using Spring Boot and MongoDB.

---

## 📌 Architecture Overview

This project is built using a layered monolithic architecture with clear separation of responsibilities:

### **1. Controller Layer**
- Defines REST API endpoints.
- Handles incoming HTTP requests.
- Returns JSON responses using DTOs.

### **2. Service Layer**
Implements business logic for:
- Creating accounts  
- Depositing & withdrawing money  
- Transferring funds  
- Updating or deleting accounts  
- Recording transactions  

### **3. Repository Layer**
- Uses Spring Data MongoDB
- Communicates with MongoDB collections:
  - `accounts`
  - `transactions`

### **4. Model Layer**
Contains MongoDB document classes:
- `Account`
- `Transaction`

### **5. DTO Layer**
Used for clean request & response handling:
- `CreateAccount`
- `DepositWithdraw`
- `Transfer`
- `UpdateDelete`
- `AccountResponse`

### **6. Exception Layer**
Custom exceptions + global handler:
- `AccountNotFoundException`
- `InvalidAmountException`
- `InsufficientBalanceException`

### **7. Logging**
- SLF4J logs in service classes for debugging:
  - Deposits  
  - Withdrawals  
  - Transfers  
  - Updates / Deletes  

---

## ⚙️ Setup Instructions

### **1. Requirements**
- Java 17+
- Maven
- MongoDB running on port **27017**
- IDE (IntelliJ / Eclipse)
- Postman for API testing

---

### **2. MongoDB Configuration**

Inside `src/main/resources/application.properties`, add:

```
spring.mongodb.uri=mongodb://localhost:27017/bank_db
```

MongoDB will automatically create:

- **bank_db** database  
- **accounts** collection  
- **transactions** collection  

---

### **3. Run the Application**

Using Maven:

```
mvn spring-boot:run
```

Or run from IDE:

```
BankSystem_Monolithic.java
```

---

## 📌 API Documentation

### **1. Create Account**
**POST** `/createAccount`

**Request Body**
```json
{
  "holderName": "Madiha"
}
```

---

### **2. Get Account**
**GET** `/getAccount/{accountNumber}`

---

### **3. Deposit Money**
**PUT** `/deposit/{accountNumber}`

```json
{
  "amount": 2000
}
```

---

### **4. Withdraw Money**
**PUT** `/withdraw/{accountNumber}`

```json
{
  "amount": 500
}
```

---

### **5. Transfer Money**
**POST** `/transfer`

```json
{
  "sourceAccount": "MAD1234",
  "destinationAccount": "ANN5678",
  "amount": 300
}
```

---

### **6. Modify Account (Update or Delete – Single API)**  
**PUT** `/modifyAccount/{accountNumber}`

#### Update Example:
```json
{
  "action": "UPDATE",
  "holderName": "Nusrat",
  "status": "ACTIVE"
}
```

#### Delete Example:
```json
{
  "action": "DELETE"
}
```

---

### **7. Get Account Transactions**
**GET** `/getTransactions/{accountNumber}`

---

### **8. Get All Transactions (Optional)**
**GET** `/getAllTransactions`

---

