# CustomerManagementSystem

## Introduction
This Customer Management System allows user manage customers through various of operations that interact with the database

## Features
- Add a single customer or multiple customers using name, email and phone number with validation to make sure inputs are correct
- Retrieve customers:
    - Retrieve all customers
    - Retrieve customers by Name
    - Retrieve customers by Email
    - Retrieve customers by Phone Number
    - Retrieve customers by ID
    - Retrieve customers in sorted orders ( Name, Email, Phone Number )
- Update existing customer details with validation
- Delete customer from the database

## Setup Instruction
1. Clone the repository: `git clone https://github.com/hyah01/CustomerManagementSystem`
2. Navigate to the project directory `cd CustomerManagementSystem`
3. Create a MySQL database for the project `CREATE DATABASE your_database_name`
4. Open `application.properties` file located in `src/main/resources`
5. Modify the configuration based on your database
```
   spring.datasource.url=jdbc:mysql://localhost:3306/customermanagementapi
   spring.datasource.username=root
   spring.datasource.password=gensparksql
```
6. Build the project `mvn clean install`
7. Run the application `mvn spring-boot:run`
8. Access the application through  `http://localhost:8080`
9. Use Postman to utilize HTTP Requests

## Usage
### Add 1 Customer
Use `POST` method on `/customers`
On Body `[{"name": "Customer a","email": "customer@gmail.com","phoneNumber": "12312389712"}]`

### Add Multiple Customer
Use `POST` method on `/customers`
On Body `[{"name": "Customer1 a","email": "customer1@gmail.com","phoneNumber": "12312389712"},
{"name": "Customer2 a","email": "customer2@gmail.com","phoneNumber": "12312389712"}]`

### Update 1 Customer
Use `PUT` method on `/customers`
On Body `[{"id": 1, """name": "Customer a","email": "customer@gmail.com","phoneNumber": "12312389712"}]`

### Update  Multiple Customer
Use `PUT` method on `/customers`
On Body `[{"id": 1,"name": "Customer1 a","email": "customer1@gmail.com","phoneNumber": "12312389712"},
{"id": 2,"name": "Customer2 a","email": "customer2@gmail.com","phoneNumber": "12312389712"}]`

### Delete a Customer
Use `DELETE` method on `/customers/{id}` Where id is the ID of the customer

### Get All Customers
Use `GET` method on `/customers` 

### Get Customer By ID
Use `GET` method on `/customers/{id}` Where id is the ID of the customer

### Get Customer By Name
Use `GET` method on `/customers/name?name={CustomerName}` Where CustomerName is the name of the customer

### Get Customer By Email
Use `GET` method on `/customers/email?email={CustomerEmail}` Where CustomerEmail is the name of the customer

### Get Customer By Phone Number
Use `GET` method on `/customers/phone?phone={Customerphone}` Where Customerphone is the phone number of the customer

### Get Customer Sorted By Name
Use `GET` method on `/customers/name/sorted` 

### Get Customer Sorted By Email
Use `GET` method on `/customers/email/sorted` 

### Get Customer Sorted By Phone Number
Use `GET` method on `/customers/phone/sorted` 




