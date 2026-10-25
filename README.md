# 🚆 Railway Ticket Reservation System

A menu-driven **Railway Ticket Reservation System developed using Java** that allows users to view trains, search for trains, book tickets, view booking details using a PNR number, and cancel reservations.

This project demonstrates the practical implementation of Object-Oriented Programming (OOP) concepts, exception handling, user input validation, and database connectivity using JDBC and MySQL.

## 📌 Project Overview

The Railway Ticket Reservation System is designed to simplify basic railway reservation operations through a simple, interactive, console-based interface.

Users can select different options from the main menu to perform reservation-related activities. The application processes user requests and displays the corresponding results.

The project is developed as part of the **Object-Oriented Programming through Java Laboratory** course.

## ✨ Features

- 🚆 **View All Trains** — Display available train information.
- 🔍 **Search Trains** — Search for trains using source and destination stations.
- 🎟️ **Book Tickets** — Enter passenger details and book seats on a selected train.
- 📋 **View Booking Details** — Retrieve booking information using a PNR number.
- ❌ **Cancel Tickets** — Cancel an existing reservation using its PNR number.
- 🔢 **PNR-Based Booking Retrieval** — Identify reservations using their PNR numbers.
- ⚠️ **Exception Handling** — Handle invalid numerical input and runtime errors.
- 🗃️ **Database Integration** — Support for storing and retrieving reservation records using JDBC and MySQL when configured.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core programming language |
| Object-Oriented Programming | Organizing the application into classes and methods |
| JDBC | Connecting Java to a relational database |
| MySQL | Storing train, passenger, and booking information |
| Java Scanner | Reading user input |
| SQL | Retrieving and modifying database records |
| VS Code | Development environment |

## 🏗️ Project Structure

The application can be organized into the following Java classes:

```text
Railway-Ticket-Reservation-System/
│
├── Main.java
├── RailwayService.java
├── Passenger.java
├── Booking.java
├── DBConnection.java
└── README.md
```

**Class descriptions:**

- **Main.java:** Displays the menu, accepts user input, and calls the appropriate service methods.
- **RailwayService.java:** Handles train viewing, train searching, ticket booking, booking retrieval, and cancellation.
- **Passenger.java:** Represents passenger information, such as name, age, and gender.
- **Booking.java:** Represents reservation details, including train information, passenger details, seats, and fare.
- **DBConnection.java:** Establishes a connection between the Java application and the MySQL database using JDBC.

*Note: The exact files and class names should match the source code in your repository.*

## ⚙️ Prerequisites

Before running the project, install the following software:

- Java Development Kit (JDK)
- Visual Studio Code or another Java-compatible IDE
- MySQL Server, if using database integration
- MySQL Connector/J, if using JDBC with MySQL

## 🚀 How to Run the Project

### Step 1: Clone the Repository

Open your terminal or PowerShell and execute:

```bash
git clone https://github.com/YOUR-USERNAME/Railway-Ticket-Reservation-System.git
```

Replace `YOUR-USERNAME` with your GitHub username.

### Step 2: Open the Project Folder

```bash
cd Railway-Ticket-Reservation-System
```

### Step 3: Compile the Java Files

If all Java files are in the same directory and package, compile them using:

```bash
javac *.java
```

### Step 4: Run the Application

```bash
java Main
```

**Note:** If your project uses JDBC and MySQL, configure the database, JDBC driver, and connection credentials before running the application. The exact setup depends on your source code.

## 🖥️ Application Menu

When the program starts, it displays the following menu:

```text
==========================================
   RAILWAY TICKET RESERVATION SYSTEM
==========================================
1. View All Trains
2. Search Trains
3. Book Ticket
4. View Booking
5. Exit
6. Cancel Ticket
==========================================
Enter your choice:
```

### Example Operations

**1. View All Trains**

Displays train information available through the application.

**2. Search Trains**

Enter the source and destination stations to search for matching trains.

**3. Book Ticket**

Enter the following details:

- Passenger name
- Passenger age
- Passenger gender
- Train number
- Number of seats

**4. View Booking**

Enter the PNR number to retrieve the corresponding booking details.

**5. Exit**

Terminates the application.

**6. Cancel Ticket**

Enter the PNR number of an existing booking to request cancellation.

## 🎯 Learning Objectives

This project helps demonstrate the following concepts:

- Classes and objects in Java
- Constructors and methods
- Encapsulation and object-oriented design
- Conditional statements and switch-case
- Loops and menu-driven programming
- Exception handling
- User input processing
- JDBC and SQL database operations
- Basic railway reservation management

## 🔮 Future Enhancements

The project can be extended with the following features:

- User registration and login
- Graphical User Interface (GUI)
- Improved train and seat selection
- Online payment integration
- Booking history
- Automatic fare calculation
- Enhanced input validation
- Better database security
- Booking confirmation and printable tickets

## 👨‍💻 Project Information

**Project Title:** Railway Ticket Reservation System

**Programming Language:** Java

**Course:** Object-Oriented Programming through Java Laboratory

**Department:** Computer Science and Engineering

**Institution:** Vardhaman College of Engineering, Hyderabad

**Project Type:** Academic Course-End Project

## 📚 References

- [Java Documentation](https://docs.oracle.com/en/java/)
- [JDBC Basics](https://docs.oracle.com/javase/tutorial/jdbc/)
- [MySQL Reference Manual](https://dev.mysql.com/doc/)
- [MySQL Connector/J Documentation](https://dev.mysql.com/doc/connector-j/en/)
