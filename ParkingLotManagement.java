import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class ParkingSpace {
    private int spaceNumber;
    private boolean occupied;
    private long startTime;

    public ParkingSpace(int spaceNumber) {
        this.spaceNumber = spaceNumber;
        this.occupied = false;
    }

    public int getSpaceNumber() {
        return spaceNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void parkVehicle() {
        this.occupied = true;
        this.startTime = System.currentTimeMillis();
    }

    public long calculateParkingDuration() {
        return System.currentTimeMillis() - startTime;
    }
}

class Vehicle {
    private String licensePlate;

    public Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}

class ParkingLot {
    private List<ParkingSpace> parkingSpaces;
    private static final double RATE_PER_SECOND = 5.0;

    public ParkingLot(int capacity) {
        parkingSpaces = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            parkingSpaces.add(new ParkingSpace(i));
        }
    }

    public ParkingSpace findFreeSpace() {
        for (ParkingSpace space : parkingSpaces) {
            if (!space.isOccupied()) {
                return space;
            }
        }
        return null;
    }

    public void parkVehicle(Vehicle vehicle) {
        ParkingSpace freeSpace = findFreeSpace();
        if (freeSpace != null) {
            freeSpace.parkVehicle();
            System.out
                    .println("Vehicle " + vehicle.getLicensePlate() + " parked in space " + freeSpace.getSpaceNumber());
        } else {
            System.out.println("No free parking spaces available.");
        }
    }

    public void freeUpSpace(int spaceNumber) {
        if (spaceNumber >= 0 && spaceNumber < parkingSpaces.size()) {
            ParkingSpace space = parkingSpaces.get(spaceNumber);
            if (space.isOccupied()) {
                long duration = space.calculateParkingDuration();
                double fee = (duration / 1000.0) * RATE_PER_SECOND;
                space.setOccupied(false);
                System.out.println("Space " + spaceNumber + " is now free. Parking fee: Rupeess " + fee);
            } else {
                System.out.println("Space " + spaceNumber + " is already free.");
            }
        } else {
            System.out.println("Invalid parking space number.");
        }
    }
}

public class ParkingLotManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingLot parkingLot = new ParkingLot(10); 

        while (true) {
            System.out.println("1. Park Vehicle");
            System.out.println("2. Free Up Space");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Vehicle License Plate: ");
                    String licensePlate = scanner.next();
                    Vehicle vehicle = new Vehicle(licensePlate);
                    parkingLot.parkVehicle(vehicle);
                    break;
                case 2:
                    System.out.print("Enter Parking Space Number to Free: ");
                    int spaceNumber = scanner.nextInt();
                    parkingLot.freeUpSpace(spaceNumber);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}