# Reward Points API

A Spring Boot application that calculates customer reward points based on purchase transactions.  
This project was built as part of a developer homework assignment.

## 📌 Features

- RESTful API endpoints for managing transactions
- Reward points calculation:
    - 2 points for every dollar spent over $100
    - 1 point for every dollar spent between $50 and $100
- Monthly and total reward summary per customer
- In-memory H2 database for demo purposes
- JPA/Hibernate integration
- Unit tests with JUnit 5 + Mockito

## 🛠 Tech Stack

- Java 8
- Spring Boot 2.7.18
- Spring Data JPA
- H2 Database
- JUnit 5 + Mockito
- Jackson JSR310 for Java Time serialization

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/reward-points-api.git
   cd reward-points-api
