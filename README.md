# Reward Points API

A Spring Boot application that calculates customer reward points based on purchase transactions.  
This project was built as part of a developer homework assignment.

## 📌 Features
- RESTful API endpoints for managing transactions
- Reward points calculation
  - 2 points for every dollar spent over $100
  - 1 point for every dollar spent between $50 and $100
- Monthly and total reward summary per customer
- In-memory H2 database for demo purposes
- JPAHibernate integration

## 🛠 Tech Stack
- Java 8
- Spring Boot 2.7.18
- Spring Data JPA
- H2 Database
- JUnit 5 + Mockito for testing

## 🚀 Getting Started
1. Clone the repository
   ```bash
   git clone httpsgithub.comyour-usernamereward-points-api.git
