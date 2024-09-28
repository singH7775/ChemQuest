📘 ChemQuest

🌟 Overview
ChemQuest is an innovative, full-stack web application that offers an cutting-edge, full-stack web application designed to provide an engaging and challenging quiz experience. Developed with Spring Boot and leveraging OpenAI's API, this project showcases a strong integration of modern web technologies, database management, and artificial intelligence.

🔑 Key Features
Dynamic Quiz Generation: Utilizes OpenAI's API to create unique and challenging questions across various subjects of Chemistry.
Personalized Learning: Students are required to answer a serioes of question correct before moving to harder subjects.
Scoring System: +10 points for every right answer, only unlocking other subjects with a certain amount of points
Secure Authentication: Implements JWT auth for user security and personalized experiences.
Persistent Data Storage: Utilizes PostgreSql for efficient data management and user progress tracking.

🛠 Technology Stack
Backend: Spring Boot
Frontend: Thymeleaf, HTML, CSS
Database: PostgreSql
Security: JWT
AI Integration: OpenAI API
Containerization: Docker

🏗 Architecture
Presentation Layer: Thymeleaf templates render dynamic HTML views.
Application Layer: Spring Boot handles business logic and API integration.
Data Layer: PostgreSql database for persistent storage.
Security Layer: Jwt auth ensures secure access to user data.
AI Layer: OpenAI API integration for intelligent question generation.

🚀 Getting Started
Prerequisites
Docker and Docker Compose
Your own OpenAI API key

Setup and Running
Clone the repository:
git clone https://github.com/singH7775/ChemQuest.git
Navigate to the project directory:
cd ChemQuest
Set up your environment variables:
Create a .env file in the root directory
Add your OpenAI API key: OPENAI_API_KEY=your_api_key_here
Build and run with Docker Compose:
docker compose up --build
Access the application at http://localhost:8080

📋 Testing
Comprehensive unit tests for backend logic
Jwt tests
ChatModel tests

📞 Contact
harsingh10000@gmail.com

♾️ Stack
Spring Boot
OpenAI
Docker
PostgreSql
Thymeleaf
