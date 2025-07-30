<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Biometric Attendance System – Spring Boot + TCP Socket</title>
</head>
<body>

  <h1>Biometric Attendance System using Spring Boot & TCP Socket</h1>

  <h2>1. Project Structure</h2>
  <pre>
Attaindance_NetCat_rawTCP_Attaindance_NetCat/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.example/
│   │   │   │   └── AttaindanceNetCatRawTcpApplication.java
│   │   │   └── com.example.attendance/
│   │   │       ├── AttendanceApplication.java
│   │   │       ├── controller/
│   │   │       │   └── AttendanceController.java
│   │   │       ├── model/
│   │   │       │   ├── Attendance.java
│   │   │       │   └── Device.java
│   │   │       ├── repository/
│   │   │       │   ├── AttendanceRepository.java
│   │   │       │   └── DeviceRepository.java
│   │   │       └── service/
│   │   │           └── AttendanceService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── com.example/
│               └── AttaindanceNetCatRawTcpApplicationTests.java
├── .gitignore
├── pom.xml
├── HELP.md
├── mvnw / mvnw.cmd
├── target/   (ignored)
  </pre>

  <h2>2. Project Overview</h2>
  <p>
    This project is a <strong>real-time Biometric Attendance System</strong> built with Java Spring Boot, MySQL, and raw TCP socket communication.
    It simulates biometric scan data using <code>Netcat (ncat)</code> and automatically stores it in the database.
  </p>
  <p>
    Manual attendance entry is eliminated, reducing human error and improving real-time tracking of employee attendance logs.
  </p>

  <h2>3. Key Features</h2>
  <ul>
    <li>⚡ Real-time data reception over TCP socket</li>
    <li>✅ Automatic parsing and processing of attendance entries</li>
    <li>🔒 Device validation before saving to the database</li>
    <li>💾 Uses Spring Data JPA to persist data in MySQL</li>
    <li>🧪 Easily testable using <code>ncat</code> command-line utility</li>
  </ul>

  <h3>📡 Data Flow</h3>
  <pre>
  [Biometric Scanner (Simulated via Netcat)] 
           │
           ▼
     [TCP Socket Listener]
           │
           ▼
  [Spring Boot Backend]
      ├── Socket Listener Service
      ├── Attendance Service (Business Logic)
      └── Repositories (MySQL Persistence)
           │
           ▼
      [MySQL Database]
  </pre>

  <h2>4. How It Works (In Depth)</h2>
  <p>
    The system listens on a TCP port (e.g., 20245) for incoming text messages that simulate biometric scan data.
    Each message contains a formatted string like:<br>
    <code>"Employee: 13245, Timestamp: 2024-12-14T10:15:30, Action: check-in, Device: office_1_device_001"</code>
  </p>

  <p><strong>Step-by-Step:</strong></p>
  <ol>
    <li>Run Spring Boot application (automatically starts TCP socket listener)</li>
    <li>Send test data using <code>ncat</code>:
      <pre>echo "Employee: 13245, Timestamp: 2024-12-14T10:15:30, Action: check-in, Device: office_1_device_001" | ncat 127.0.0.1 20245</pre>
    </li>
    <li>Listener parses raw string into structured fields: Employee ID, Timestamp, Action, Device ID</li>
    <li>System verifies if Device is registered</li>
    <li>Attendance is saved using JPA to MySQL</li>
    <li>Data is available for reports, analysis, and real-time tracking</li>
  </ol>

  <h2>5. Technologies Used</h2>
  <ul>
    <li>Java 17</li>
    <li>Spring Boot</li>
    <li>Spring Data JPA</li>
    <li>MySQL</li>
    <li>Netcat (ncat) for socket simulation</li>
    <li>Maven</li>
  </ul>

  <h2>6. Sample Command for Testing</h2>
  <pre>
echo "Employee: 13245, Timestamp: 2024-12-14T10:15:30, Action: check-in, Device: office_1_device_001" | ncat 127.0.0.1 20245
  </pre>

  <h2>7. Author</h2>
  <p>Developed by <strong>Komal Shendage</strong></p>

</body>
</html>
