import java.util.*;
import java.io.*;

class Room {
    int roomNumber;
    String category;
    double price;
    boolean available;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class Reservation {
    String customerName;
    Room room;

    Reservation(String customerName, Room room) {
        this.customerName = customerName;
        this.room = room;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        rooms.add(new Room(101, "Standard", 1000));
        rooms.add(new Room(102, "Standard", 1000));
        rooms.add(new Room(201, "Deluxe", 2000));
        rooms.add(new Room(202, "Deluxe", 2000));
        rooms.add(new Room(301, "Suite", 3500));

        loadReservations();

        while (true) {

            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");

            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    searchRooms();
                    break;

                case 2:
                    System.out.print("Enter Customer Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Number: ");
                    int roomNo = sc.nextInt();

                    bookRoom(name, roomNo);
                    break;

                case 3:
                    System.out.print("Enter Customer Name: ");
                    String cancelName = sc.nextLine();

                    cancelReservation(cancelName);
                    break;

                case 4:
                    viewReservations();
                    break;

                case 5:
                    System.out.println("Thank You!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    static void searchRooms() {

        System.out.println("\nAvailable Rooms:");

        boolean found = false;

        for (Room room : rooms) {

            if (room.available) {

                System.out.println(
                        "Room: " + room.roomNumber +
                        " | Category: " + room.category +
                        " | Price: Rs. " + room.price);

                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available.");
        }
    }

    static void bookRoom(String name, int roomNo) {

        for (Room room : rooms) {

            if (room.roomNumber == roomNo && room.available) {

                System.out.println(
                        "Payment Successful: Rs. " + room.price);

                room.available = false;

                reservations.add(
                        new Reservation(name, room));

                saveReservations();

                System.out.println(
                        "Room Booked Successfully!");

                return;
            }
        }

        System.out.println("Room Not Available!");
    }

    static void cancelReservation(String name) {

        Iterator<Reservation> iterator =
                reservations.iterator();

        while (iterator.hasNext()) {

            Reservation r = iterator.next();

            if (r.customerName.equalsIgnoreCase(name)) {

                r.room.available = true;

                iterator.remove();

                saveReservations();

                System.out.println(
                        "Reservation Cancelled Successfully!");

                return;
            }
        }

        System.out.println("Reservation Not Found!");
    }

    static void viewReservations() {

        System.out.println("\nBooking Details:");

        if (reservations.isEmpty()) {

            System.out.println("No Reservations Found.");
            return;
        }

        for (Reservation r : reservations) {

            System.out.println(
                    "Customer: " + r.customerName +
                    " | Room: " + r.room.roomNumber +
                    " | Category: " + r.room.category +
                    " | Price: Rs. " + r.room.price);
        }
    }

    static void saveReservations() {

        try {

            PrintWriter writer =
                    new PrintWriter("bookings.txt");

            for (Reservation r : reservations) {

                writer.println(
                        r.customerName + "," +
                        r.room.roomNumber + "," +
                        r.room.category + "," +
                        r.room.price);
            }

            writer.close();

        } catch (Exception e) {

            System.out.println(
                    "Error saving reservations.");
        }
    }

    static void loadReservations() {

        try {

            File file = new File("bookings.txt");

            if (!file.exists())
                return;

            Scanner fileScanner =
                    new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String[] data =
                        fileScanner.nextLine().split(",");

                String customerName = data[0];
                int roomNo =
                        Integer.parseInt(data[1]);

                for (Room room : rooms) {

                    if (room.roomNumber == roomNo) {

                        room.available = false;

                        reservations.add(
                                new Reservation(
                                        customerName,
                                        room));

                        break;
                    }
                }
            }

            fileScanner.close();

        } catch (Exception e) {

            System.out.println(
                    "Error loading reservations.");
        }
    }
}