package busreservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Bus {
    private int busId;
    private String busName;
    private int totalSeats;
    private int availableSeats;

    public Bus(int busId, String busName, int totalSeats) {
        this.busId = busId;
        this.busName = busName;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public int getBusId() {
        return busId;
    }

    public String getBusName() {
        return busName;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void reserveSeat() {
        if (availableSeats > 0) {
            availableSeats--;
        } else {
            System.out.println("No available seats.");
        }
    }
}

class User {
    private int userId;
    private String userName;
    private String userPhone;
    private String userEmail;

    public User(int userId, String userName, String userPhone, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }
}

interface BusDAO {
    void addBus(Bus bus);
    List<Bus> getAllBuses();
    Bus getBusById(int busId);
    void reserveSeat(int busId, User user);
}

class BusDAOImpl implements BusDAO {
    private List<Bus> buses;

    public BusDAOImpl() {
        buses = new ArrayList<>();
    }

    @Override
    public void addBus(Bus bus) {
        buses.add(bus);
    }

    @Override
    public List<Bus> getAllBuses() {
        return buses;
    }

    @Override
    public Bus getBusById(int busId) {
        for (Bus bus : buses) {
            if (bus.getBusId() == busId) {
                return bus;
            }
        }
        return null;
    }

    @Override
    public void reserveSeat(int busId, User user) {
        Bus bus = getBusById(busId);
        if (bus != null) {
            bus.reserveSeat();
            System.out.println("Bus reservation successful for user: " + user.getUserName());
            System.out.println("Bus booked: " + bus.getBusName());
        } else {
            System.out.println("Bus not found.");
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BusDAO busDAO = new BusDAOImpl();

        Bus bus1 = new Bus(1, "Abishai", 50);
        Bus bus2 = new Bus(2, "Honeybee", 30);

        busDAO.addBus(bus1);
        busDAO.addBus(bus2);

        displayBuses(busDAO.getAllBuses());

        System.out.println("\nEnter your details to reserve a seat:");

        int userId = getIntInput(scanner, "Enter User ID: ");
        scanner.nextLine(); // consume newline

        System.out.print("Enter User Name: ");
        String userName = scanner.nextLine();

        System.out.print("Enter User Phone: ");
        String userPhone = scanner.nextLine();

        System.out.print("Enter User Email: ");
        String userEmail = scanner.nextLine();

        User user = new User(userId, userName, userPhone, userEmail);

        int busId = getIntInput(scanner, "Enter Bus ID to reserve a seat: ");

        busDAO.reserveSeat(busId, user);

        System.out.println("\nUpdated Bus List:");
        displayBuses(busDAO.getAllBuses());

        scanner.close();
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        int result = -1;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                result = Integer.parseInt(scanner.nextLine());
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return result;
    }

    private static void displayBuses(List<Bus> buses) {
        System.out.println("All Buses:");
        for (Bus bus : buses) {
            System.out.println("Bus ID: " + bus.getBusId() + ", Name: " + bus.getBusName() + ", Available Seats: " + bus.getAvailableSeats());
        }
    }
}
