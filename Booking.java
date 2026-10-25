
public class Booking {
    private long bookingId;
    private int trainNo;
    private String passengerName;
    private int age;
    private int seatsBooked;
    private double totalFare;

    public Booking(long bookingId, int trainNo, String passengerName,
                   int age, int seatsBooked, double totalFare) {
        this.bookingId = bookingId;
        this.trainNo = trainNo;
        this.passengerName = passengerName;
        this.age = age;
        this.seatsBooked = seatsBooked;
        this.totalFare = totalFare;
    }

    public long getBookingId() {
        return bookingId;
    }

    public int getTrainNo() {
        return trainNo;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public int getAge() {
        return age;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public double getTotalFare() {
        return totalFare;
    }
}
