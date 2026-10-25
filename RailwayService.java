
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RailwayService {

    // 1. Display all available trains
    public void viewTrains() {
        String sql = "SELECT * FROM trains ORDER BY trainno";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n================ ALL TRAINS ================");
            printTrainHeader();

            boolean found = false;

            while (rs.next()) {
                printTrain(rs);
                found = true;
            }

            if (!found) {
                System.out.println("No trains found.");
            }

        } catch (SQLException e) {
            System.out.println("Unable to display trains: " + e.getMessage());
        }
    }

    // 2. Search trains by source and destination
    public void searchTrains(String source, String destination) {
        String sql = "SELECT * FROM trains " +
                     "WHERE LOWER(source) = LOWER(?) " +
                     "AND LOWER(destination) = LOWER(?) " +
                     "ORDER BY trainno";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, source);
            ps.setString(2, destination);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n========== SEARCH RESULTS ==========");
                printTrainHeader();

                boolean found = false;

                while (rs.next()) {
                    printTrain(rs);
                    found = true;
                }

                if (!found) {
                    System.out.println(
                        "No trains found for " + source + " to " + destination + "."
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Unable to search trains: " + e.getMessage());
        }
    }

    // Print train table heading
    private void printTrainHeader() {
        System.out.printf(
            "%-10s %-25s %-18s %-18s %-12s %-12s %-10s%n",
            "Train No", "Train Name", "Source", "Destination",
            "Total Seats", "Available", "Fare"
        );

        System.out.println(
            "---------------------------------------------------------------------------------------------------------------"
        );
    }

    // Print one train record
    private void printTrain(ResultSet rs) throws SQLException {
        System.out.printf(
            "%-10d %-25s %-18s %-18s %-12d %-12d Rs.%-7.2f%n",
            rs.getInt("trainno"),
            rs.getString("trainname"),
            rs.getString("source"),
            rs.getString("destination"),
            rs.getInt("totalseats"),
            rs.getInt("availableseats"),
            rs.getDouble("fare")
        );
    }

    // 3. Book a ticket
    // Argument order matches Main.java:
    // name, age, gender, trainNo, seats
    public void bookTicket(
            String name,
            int age,
            String gender,
            int trainNo,
            int seats) {

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Passenger name cannot be empty.");
            return;
        }

        if (age <= 0 || age > 120) {
            System.out.println("Please enter a valid passenger age.");
            return;
        }

        if (gender == null || gender.trim().isEmpty()) {
            System.out.println("Gender cannot be empty.");
            return;
        }

        if (seats <= 0) {
            System.out.println("Number of seats must be greater than zero.");
            return;
        }

        String trainSql =
            "SELECT trainname, availableseats, fare " +
            "FROM trains WHERE trainno = ? FOR UPDATE";

        String passengerSql =
            "INSERT INTO passengers (name, age, gender) VALUES (?, ?, ?)";

        String bookingSql =
            "INSERT INTO bookings " +
            "(passengerid, trainno, seatsbooked, status) " +
            "VALUES (?, ?, ?, 'BOOKED')";

        String updateTrainSql =
            "UPDATE trains SET availableseats = availableseats - ? " +
            "WHERE trainno = ? AND availableseats >= ?";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                String trainName;
                int availableSeats;
                double fare;

                // Lock the selected train row during booking
                try (PreparedStatement ps = con.prepareStatement(trainSql)) {
                    ps.setInt(1, trainNo);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("Train number not found.");
                            con.rollback();
                            return;
                        }

                        trainName = rs.getString("trainname");
                        availableSeats = rs.getInt("availableseats");
                        fare = rs.getDouble("fare");
                    }
                }

                if (availableSeats < seats) {
                    System.out.println("Sorry, only " + availableSeats
                            + " seats are available.");
                    con.rollback();
                    return;
                }

                double totalFare = fare * seats;
                int passengerId;

                // Save passenger details
                try (PreparedStatement ps = con.prepareStatement(
                        passengerSql,
                        java.sql.Statement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, name.trim());
                    ps.setInt(2, age);
                    ps.setString(3, gender.trim());

                    ps.executeUpdate();

                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException(
                                "Could not generate passenger ID."
                            );
                        }

                        passengerId = keys.getInt(1);
                    }
                }

                // Update available seats
                try (PreparedStatement ps = con.prepareStatement(updateTrainSql)) {
                    ps.setInt(1, seats);
                    ps.setInt(2, trainNo);
                    ps.setInt(3, seats);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                            "Seat availability changed. Please try again."
                        );
                    }
                }

                // Create booking and obtain PNR
                long pnr;

                try (PreparedStatement ps = con.prepareStatement(
                        bookingSql,
                        java.sql.Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, passengerId);
                    ps.setInt(2, trainNo);
                    ps.setInt(3, seats);

                    ps.executeUpdate();

                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException(
                                "Could not generate booking PNR."
                            );
                        }

                        pnr = keys.getLong(1);
                    }
                }

                con.commit();

                System.out.println("\n========== BOOKING SUCCESSFUL ==========");
                System.out.println("PNR Number       : " + pnr);
                System.out.println("Passenger ID     : " + passengerId);
                System.out.println("Passenger Name   : " + name);
                System.out.println("Age               : " + age);
                System.out.println("Gender            : " + gender);
                System.out.println("Train Number      : " + trainNo);
                System.out.println("Train Name        : " + trainName);
                System.out.println("Seats Booked      : " + seats);
                System.out.printf("Fare per Seat     : Rs. %.2f%n", fare);
                System.out.printf("Total Fare        : Rs. %.2f%n", totalFare);
                System.out.println("Booking Status    : BOOKED");
                System.out.println("=========================================");

            } catch (Exception e) {
                con.rollback();
                System.out.println("Booking failed: " + e.getMessage());

            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Database error during booking: " + e.getMessage());
        }
    }

    // 4. View booking details using PNR
    public void viewBooking(long pnr) {
        String sql =
            "SELECT b.pnr, b.passengerid, b.trainno, b.seatsbooked, " +
            "b.bookingdate, b.status, p.name, p.age, p.gender, " +
            "t.trainname, t.source, t.destination, t.fare " +
            "FROM bookings b " +
            "JOIN passengers p ON b.passengerid = p.passengerid " +
            "JOIN trains t ON b.trainno = t.trainno " +
            "WHERE b.pnr = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, pnr);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("No booking found for PNR: " + pnr);
                    return;
                }

                double totalFare =
                    rs.getDouble("fare") * rs.getInt("seatsbooked");

                System.out.println("\n========== BOOKING DETAILS ==========");
                System.out.println("PNR Number      : " + rs.getLong("pnr"));
                System.out.println("Passenger ID    : " + rs.getInt("passengerid"));
                System.out.println("Passenger Name  : " + rs.getString("name"));
                System.out.println("Age             : " + rs.getInt("age"));
                System.out.println("Gender          : " + rs.getString("gender"));
                System.out.println("Train Number    : " + rs.getInt("trainno"));
                System.out.println("Train Name      : " + rs.getString("trainname"));
                System.out.println("Source          : " + rs.getString("source"));
                System.out.println("Destination     : " + rs.getString("destination"));
                System.out.println("Seats Booked    : " + rs.getInt("seatsbooked"));
                System.out.println("Booking Date    : " + rs.getTimestamp("bookingdate"));
                System.out.println("Status          : " + rs.getString("status"));
                System.out.printf("Total Fare      : Rs. %.2f%n", totalFare);
                System.out.println("=====================================");

            }

        } catch (SQLException e) {
            System.out.println("Unable to view booking: " + e.getMessage());
        }
    }

    // 5. Cancel a booking and restore available seats
    public void cancelTicket(long pnr) {
        String bookingSql =
            "SELECT trainno, seatsbooked, status " +
            "FROM bookings WHERE pnr = ? FOR UPDATE";

        String cancelSql =
            "UPDATE bookings SET status = 'CANCELLED' " +
            "WHERE pnr = ? AND status = 'BOOKED'";

        String restoreSeatsSql =
            "UPDATE trains SET availableseats = availableseats + ? " +
            "WHERE trainno = ?";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                int trainNo;
                int seatsBooked;
                String status;

                // Lock the booking row
                try (PreparedStatement ps = con.prepareStatement(bookingSql)) {
                    ps.setLong(1, pnr);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("PNR not found.");
                            con.rollback();
                            return;
                        }

                        trainNo = rs.getInt("trainno");
                        seatsBooked = rs.getInt("seatsbooked");
                        status = rs.getString("status");
                    }
                }

                if (!"BOOKED".equalsIgnoreCase(status)) {
                    System.out.println(
                        "This ticket cannot be cancelled. Current status: "
                        + status
                    );
                    con.rollback();
                    return;
                }

                // Mark booking as cancelled
                try (PreparedStatement ps = con.prepareStatement(cancelSql)) {
                    ps.setLong(1, pnr);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                            "Could not update booking status."
                        );
                    }
                }

                // Return the seats to the train
                try (PreparedStatement ps = con.prepareStatement(restoreSeatsSql)) {
                    ps.setInt(1, seatsBooked);
                    ps.setInt(2, trainNo);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                            "Could not restore available seats."
                        );
                    }
                }

                con.commit();

                System.out.println("\nTicket cancelled successfully.");
                System.out.println("PNR Number          : " + pnr);
                System.out.println("Seats Restored      : " + seatsBooked);
                System.out.println("Booking Status      : CANCELLED");

            } catch (Exception e) {
                con.rollback();
                System.out.println("Cancellation failed: " + e.getMessage());

            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Database error during cancellation: "
                    + e.getMessage());
        }
    }
}
