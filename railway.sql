
CREATE DATABASE IF NOT EXISTS railway_db;
USE railway_db;

CREATE TABLE IF NOT EXISTS trains (
    trainno INT PRIMARY KEY,
    trainname VARCHAR(100) NOT NULL,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    totalseats INT NOT NULL,
    availableseats INT NOT NULL,
    fare DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS passengers (
    passengerid INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS bookings (
    pnr BIGINT PRIMARY KEY AUTO_INCREMENT,
    passengerid INT NOT NULL,
    trainno INT NOT NULL,
    seatsbooked INT NOT NULL,
    bookingdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'BOOKED',
    FOREIGN KEY (passengerid)
        REFERENCES passengers(passengerid),
    FOREIGN KEY (trainno)
        REFERENCES trains(trainno)
);

INSERT IGNORE INTO trains
(trainno, trainname, source, destination,
 totalseats, availableseats, fare)
VALUES
(1001, 'Express One', 'Hyderabad', 'Chennai', 100, 100, 450.00),
(1002, 'Superfast Express', 'Hyderabad', 'Bangalore', 120, 120, 550.00),
(1003, 'City Express', 'Chennai', 'Bangalore', 80, 80, 350.00),
(1004, 'Deccan Express', 'Hyderabad', 'Mumbai', 150, 150, 700.00),
(1005, 'Southern Express', 'Bangalore', 'Chennai', 90, 90, 400.00);

SELECT * FROM trains;
