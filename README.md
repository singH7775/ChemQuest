# 📘 ChemQuest

## 🌟 Overview

ChemQuest is an innovative, full-stack web application designed to provide an engaging and challenging quiz experience for chemistry students. Developed with Spring Boot and leveraging OpenAI's API, this project showcases a strong integration of modern web technologies, database management, and artificial intelligence.

## 🔑 Key Features

- **Dynamic Quiz Generation**: Utilizes OpenAI's API to create unique and challenging questions across various subjects of Chemistry.
- **Personalized Learning**: Students are required to answer a series of questions correctly before moving to harder subjects.
- **Scoring System**: +10 points for every right answer, only unlocking other subjects with a certain amount of points.
- **Secure Authentication**: Implements JWT auth for user security and personalized experiences.
- **Persistent Data Storage**: Utilizes PostgreSQL for efficient data management and user progress tracking.

## 🛠 Technology Stack

- **Backend**: Spring Boot
- **Frontend**: Thymeleaf, HTML, CSS
- **Database**: PostgreSQL
- **Security**: JWT
- **AI Integration**: OpenAI API
- **Containerization**: Docker

## 🏗 Architecture

- **Presentation Layer**: Thymeleaf templates render dynamic HTML views.
- **Application Layer**: Spring Boot handles business logic and API integration.
- **Data Layer**: PostgreSQL database for persistent storage.
- **Security Layer**: JWT auth ensures secure access to user data.
- **AI Layer**: OpenAI API integration for intelligent question generation.

## 🚀 Getting Started

### Prerequisites

- Docker and Docker Compose
- Your own OpenAI API key

### Setup and Running

1. Clone the repository:
   ```
   git clone https://github.com/singH7775/ChemQuest.git
   ```
2. Navigate to the project directory:
   ```
   cd ChemQuest
   ```
3. Set up your environment variables:
   - Create a `.env` file in the root directory
   - Add your OpenAI API key:
     ```
     OPENAI_API_KEY=your_api_key_here
     ```
4. Build and run with Docker Compose:
   ```
   docker compose up --build
   ```
5. Access the application at `http://localhost:8080`

## 📋 Testing

- Comprehensive unit tests for backend logic
- JWT tests
- ChatModel tests

## 📞 Contact

harsingh10000@gmail.com

## ♾️ Stack

![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=flat-square&logo=spring&logoColor=white)
![OpenAI](https://img.shields.io/badge/-OpenAI-412991?style=flat-square&logo=openai&logoColor=white)
![Docker](https://img.shields.io/badge/-Docker-2496ED?style=flat-square&logo=docker&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-336791?style=flat-square&logo=postgresql&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/-Thymeleaf-005F0F?style=flat-square&logo=thymeleaf&logoColor=white)
