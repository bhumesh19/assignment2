Bidirectional ClickHouse & Flat File Data Ingestion Tool
This is a web-based application for bidirectional data ingestion between ClickHouse and flat files (CSV). It supports JWT authentication, column selection, and record count reporting.
Prerequisites

Java 17
Maven
Node.js and npm
ClickHouse server (Docker recommended)
Docker Desktop (for Windows/Mac)

Setup Instructions
1. Clone the Repository
git clone <your-repo-url>
cd intern

2. Set Up ClickHouse
Run a ClickHouse container with a custom user:
docker run -d -p 8443:8443 -p 9000:9000 --name clickhouse-server clickhouse/clickhouse-server
docker exec clickhouse-server clickhouse-client -q "CREATE USER user1 IDENTIFIED WITH password BY 'password1'"
docker exec clickhouse-server clickhouse-client -q "GRANT ALL ON *.* TO user1"

3. Load Example Datasets
Download and import datasets (e.g., uk_price_paid):
curl -O https://clickhouse.com/docs/en/getting-started/example-datasets/uk_price_paid.csv
docker cp uk_price_paid.csv clickhouse-server:/uk_price_paid.csv
docker exec clickhouse-server clickhouse-client --query "CREATE TABLE uk_price_paid (price UInt32, date Date, postcode1 String, postcode2 String, type String, is_new UInt8, tenure String, paon String, saon String, street String, locality String, city String, district String, county String, ppd_category String, record_status String) ENGINE = MergeTree ORDER BY date"
docker exec clickhouse-server clickhouse-client --query "INSERT INTO uk_price_paid FORMAT CSVWithNames" < /uk_price_paid.csv

4. Backend Setup
Navigate to the backend directory and build:
cd backend
mvn clean install

Run the Spring Boot application:
mvn spring-boot:run

5. Frontend Setup
Navigate to the frontend directory and install dependencies:
cd frontend
npm install

Run the React application:
npm start

6. Access the Application
Open your browser and navigate to http://localhost:3000.
Usage

Select the source type (ClickHouse or Flat File).
For ClickHouse:
Enter host (localhost), port (8443), database (default), user (user1), JWT (password1).


For Flat File:
Upload a CSV file.


Click "Connect" to list tables.
Select a table and click "Load Columns".
Choose columns to ingest.
Click "Start Ingestion" to transfer data.
View the status and record count upon completion.

Testing
Run backend tests:
cd backend
mvn test

Test cases:

Connect to ClickHouse and list tables.
Fetch columns from uk_price_paid.
Ingest selected columns from ClickHouse to CSV.
Upload a CSV and ingest to a new ClickHouse table.
Verify record counts.

AI Tool Usage
Prompts used with AI tools are recorded in prompts.txt.
Directory Structure
intern/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/intern/
│   │   │   └── resources/application.properties
│   │   └── test/
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── App.jsx
│   │   ├── index.jsx
│   │   └── index.html
│   ├── package.json
│   └── vite.config.js
├── data/
│   └── sample_data.csv
├── prompts.txt
└── README.md

