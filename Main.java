
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RailwayService service = new RailwayService();

        while (true) {

            System.out.println("\n==========================================");
            System.out.println("   RAILWAY TICKET RESERVATION SYSTEM");
            System.out.println("==========================================");
            System.out.println("1. View All Trains");
            System.out.println("2. Search Trains");
            System.out.println("3. Book Ticket");
            System.out.println("4. View Booking");
            System.out.println("5. Exit");
            System.out.println("6. Cancel Ticket");
            System.out.println("==========================================");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {

                    case 1:
                        service.viewTrains();
                        break;

                    case 2:
                        System.out.print("Enter source station: ");
                        String source = sc.nextLine().trim();

                        System.out.print("Enter destination station: ");
                        String destination = sc.nextLine().trim();

                        service.searchTrains(source, destination);
                        break;

                    case 3:
                        System.out.print("Enter passenger name: ");
                        String name = sc.nextLine().trim();

                        System.out.print("Enter passenger age: ");
                        int age = Integer.parseInt(sc.nextLine().trim());

                        System.out.print("Enter passenger gender: ");
                        String gender = sc.nextLine().trim();

                        System.out.print("Enter train number: ");
                        int trainNo = Integer.parseInt(sc.nextLine().trim());

                        System.out.print("Enter number of seats: ");
                        int seats = Integer.parseInt(sc.nextLine().trim());

                        // Correct argument order
                        service.bookTicket(
                            name, age, gender, trainNo, seats
                        );
                        break;

                    case 4:
                        System.out.print("Enter PNR number: ");
                        long pnr = Long.parseLong(sc.nextLine().trim());

                        service.viewBooking(pnr);
                        break;

                    case 5:
                        System.out.println(
                            "Thank you for using the Railway Ticket Reservation System!"
                        );
                        sc.close();
                        return;

                    case 6:
                        System.out.print("Enter PNR number to cancel: ");
                        long cancelPnr =
                            Long.parseLong(sc.nextLine().trim());

                        service.cancelTicket(cancelPnr);
                        break;

                    default:
                        System.out.println(
                            "Invalid choice! Please enter a number from 1 to 6."
                        );
                }

            } catch (NumberFormatException e) {
                System.out.println(
                    "Invalid input! Please enter a valid number."
                );

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
