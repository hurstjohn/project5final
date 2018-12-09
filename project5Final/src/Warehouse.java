import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Warehouse</h1>
 */

public class Warehouse {
    final static String folderPath = "/Users/Jack Hurst/project5Final/src/files/";
    final static File VEHICLE_FILE = new File(folderPath + "VehicleList.csv");
    final static File PACKAGE_FILE = new File(folderPath + "PackageList.csv");
    final static File PROFIT_FILE = new File(folderPath + "Profit.txt");
    final static File N_PACKAGES_FILE = new File(folderPath + "NumberOfPackages.txt");
    final static File PRIME_DAY_FILE = new File(folderPath + "PrimeDay.txt");
    final static double PRIME_DAY_DISCOUNT = .15;

    /**
     * Main Method
     *
     * @param args list of command line arguements
     */
    public static void main(String[] args) {
        //TODO
        Scanner reader = new Scanner(System.in);
        //1) load data (vehicle, packages, profits, packages shipped and primeday) from files using DatabaseManager
        //make package arraylist and load
        ArrayList<Package> packages = new ArrayList<>();
        packages = DatabaseManager.loadPackages(PACKAGE_FILE);
        //make vehicle arraylist and load
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles = DatabaseManager.loadVehicles(VEHICLE_FILE);
        //make profits double and load
        double profits = DatabaseManager.loadProfit(PROFIT_FILE);
        //make packagesShipped and load
        int packagesShipped = DatabaseManager.loadPackagesShipped(N_PACKAGES_FILE);
        //make primeDay and load
        boolean primeDay = DatabaseManager.loadPrimeDay(PRIME_DAY_FILE);

        //2) Show menu and handle user inputs
        boolean userExited = false;
        while (userExited == false) {
            System.out.println("==========Options==========");
            System.out.println("1) Add Package");
            System.out.println("2) Add Vehicle");
            if (primeDay == false) {
                System.out.println("3) Activate Prime Day");
            } else {
                System.out.println("3) Deactivate Prime Day");
            }
            System.out.println("4) Send Vehicle");
            System.out.println("5) Print Statistics");
            System.out.println("6) Exit");
            System.out.println("===========================");
            int choice = reader.nextInt();
            while (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6) {
                System.out.println("Error: Option not available.");
                choice = reader.nextInt();
            }
            switch (choice) {
                case 1:
                    //collect info about the package
                    String id;
                    System.out.println("Enter Package ID:");
                    id = reader.next();
                    String product;
                    System.out.println("Enter Product Name:");
                    product = reader.nextLine();
                    product = reader.nextLine(); //need to do this twice bc scanner is buggy as fuck
                    double weight;
                    System.out.println("Enter Weight:");
                    weight = reader.nextDouble();
                    double price;
                    System.out.println("Enter Price:");
                    price = reader.nextDouble();
                    String buyerName;
                    System.out.println("Enter Buyer Name:");
                    buyerName = reader.nextLine();
                    buyerName = reader.nextLine(); //need to do this twice bc scanner is buggy as fuck
                    String address;
                    System.out.println("Enter Address:");
                    address = reader.nextLine();
                    String city;
                    System.out.println("Enter City");
                    city = reader.nextLine();
                    String state;
                    System.out.println("Enter State:");
                    state = reader.next();
                    int zip;
                    System.out.println("Enter ZIP Code:");
                    zip = reader.nextInt();

                    //make the shipping address for the package
                    ShippingAddress shippingAddress = new ShippingAddress(buyerName, address, city, state, zip);

                    //actually make package
                    Package currentPackage = new Package(id, product, weight, price, shippingAddress);

                    //add the package to the arraylist
                    packages.add(currentPackage);

                    //print out shipping label
                    System.out.println(currentPackage.shippingLabel());
                    break;
                case 2:
                    //choose vehicle type
                    int vehicleType;
                    System.out.println("Vehicle Options:");
                    System.out.println("1) Truck");
                    System.out.println("2) Drone");
                    System.out.println("3) Cargo Plane");
                    vehicleType = reader.nextInt();

                    //get vehicle info
                    String licensePlate;
                    System.out.println("Enter License Plate No.:");
                    licensePlate = reader.next();
                    int maxCarry;
                    System.out.println("Enter Maximum Carry Weight:");
                    maxCarry = reader.nextInt();

                    //make a new vehicle and add it to the arraylist
                    Vehicle vehicle = null;
                    switch (vehicleType) {
                        case 1:
                            vehicle = new Truck(licensePlate, maxCarry);
                            break;
                        case 2:
                            vehicle = new Drone(licensePlate, maxCarry);
                            break;
                        case 3:
                            vehicle = new CargoPlane(licensePlate, maxCarry);
                            break;
                    }
                    vehicles.add(vehicle);
                    break;
                case 3:
                    if (primeDay == false) {
                        primeDay = true;
//                    //loop through all packages and take 15% off
//                    for (int a = 0; a < packages.size(); a++) {
//                        Package currentPackage = packages.get(a);
//                        currentPackage.setPrice(currentPackage.getPrice() * .85);
//                    }
                    } else { //you may or may not need to increase the price back to original
                        primeDay = false;
                    }
                    DatabaseManager.savePrimeDay(PRIME_DAY_FILE, primeDay);
                    break;
                case 4:
                    if (vehicles.size() == 0) {
                        System.out.println("Error: No vehicles available.");
                        continue;
                    } else if (packages.size() == 0) {
                        System.out.println("Error: No packages available.");
                        continue;
                    } else {
                        int vehicleChoice = 0;
                        System.out.println("Options:");
                        System.out.println("1) Send Truck");
                        System.out.println("2) Send Drone");
                        System.out.println("3) Send Cargo Plane");
                        System.out.println("4) Send First Available");
                        vehicleChoice = reader.nextInt();
                        //this boolean is for chekcing if the warehouse has a vehicle of the specified type to send out
                        boolean hasVehicleType = false;
                        Vehicle toSend = null;
                        int vehicleIndex = 0;
                        //this switch will determine if there is a vehicle of the specified type and if there is it will load the vehicle with packages and send it out
                        switch (vehicleChoice) {
                            case 1: //attempt to send a truck
                                for (int a = 0; a < vehicles.size(); a++) {
                                    if (vehicles.get(a) instanceof Truck) {
                                        hasVehicleType = true;
                                        vehicleIndex = a;
                                    }
                                }
                                if (hasVehicleType == false) {
                                    System.out.println("Error: No vehicles of selected type are available.");
                                    continue;
                                } else { //get the vehicle to be sent
                                    toSend = vehicles.get(vehicleIndex);
                                }
                                break;
                            case 2: //attempt to send a drone
                                for (int a = 0; a < vehicles.size(); a++) {
                                    if (vehicles.get(a) instanceof Drone) {
                                        hasVehicleType = true;
                                        vehicleIndex = a;
                                    }
                                }
                                if (hasVehicleType == false) {
                                    System.out.println("Error: No vehicles of selected type are available.");
                                    continue;
                                } else { //get the vehicle to be sent
                                    toSend = vehicles.get(vehicleIndex);
                                }
                                break;
                            case 3: //attempt to send a cargo plane
                                for (int a = 0; a < vehicles.size(); a++) {
                                    if (vehicles.get(a) instanceof CargoPlane) {
                                        hasVehicleType = true;
                                        vehicleIndex = a;
                                    }
                                }
                                if (hasVehicleType == false) {
                                    System.out.println("Error: No vehicles of selected type are available.");
                                    continue;
                                } else { //get the vehicle to be sent
                                    toSend = vehicles.get(vehicleIndex);
                                }
                                break;
                            case 4: //send first available vehicle
                                toSend = vehicles.get(0);
                                break;
                        }
                        //prompt user for sending to first zip code or zip code mode
                        int whichZip = 0;
                        System.out.println("ZIP Code Options:");
                        System.out.println("1) Send to first ZIP Code");
                        System.out.println("2) Send to mode of ZIP Codes");
                        whichZip = reader.nextInt();
                        int zipToSend = 0;
                        if (whichZip == 1) { //send to first ZIP Code
                            zipToSend = packages.get(0).getDestination().zipCode;
                        } else { //find the mode of the zips
                            ArrayList<Package> packagesCopy = packages;

                            int greatestMode = 0;
                            int modeZip = 0;

                            int thisZip = 0;
                            for (int a = 0; a < packagesCopy.size(); a++) {
                                //sets the current zip on the iteration as the reference zip
                                int ref = packagesCopy.get(a).getDestination().getZipCode();

                                int modeCount = 0;

                                for (int b = a + 1; b < packagesCopy.size(); b++) {
                                    //compares reference zip to all other zips and adds to count if zip ref equals current zip

                                    if (packagesCopy.get(b).getDestination().getZipCode() == ref) {

                                        modeCount++;

                                        thisZip = packagesCopy.get(b).getDestination().getZipCode();


                                    }


                                }
                                //compares this number with the greatest mode
                                if (modeCount > greatestMode) {


                                    greatestMode = modeCount;

                                    modeZip = thisZip;

                                }
                            }

                            zipToSend = modeZip;
                        }
                        //set destination of vehicle

                        System.out.println("zip:" + zipToSend);
                        toSend.setZipDest(zipToSend);
                        //load the selected vehicle


                        for (Package p : packages) {

                            System.out.println(p.getDestination().getZipCode());
                            System.out.println(p.getDestination().getName());


                        }

                        toSend.fill(packages);


                        System.out.println("\n\n");

                        for (Package p : toSend.getPackages()) {

                           System.out.println(p.getDestination().getZipCode());
                           System.out.println(p.getDestination().getName());

                       }

                        //print vehicle report
                        System.out.println(toSend.report());
                        //add profits
                        profits += toSend.getProfit();

                        //add to the total number of packages sent
                        packagesShipped = toSend.getPackages().size();
                        //empty the vehicle to represent delivering packages
                        toSend.empty();
                        //remove the vehicle from warehouse
                        vehicles.remove(toSend);
                    }
                    break;
                case 5:
                    printStatisticsReport(profits, packagesShipped, packages.size());
                    break;
                case 6:
                    userExited = true;
                    break;
            }
        }
        //3) save data (vehicle, packages, profits, packages shipped and primeday) to files (overwriting them) using DatabaseManager
        DatabaseManager.savePackages(PACKAGE_FILE, packages);
        DatabaseManager.saveVehicles(VEHICLE_FILE, vehicles);
        DatabaseManager.saveProfit(PROFIT_FILE, profits);
        DatabaseManager.savePackagesShipped(N_PACKAGES_FILE, packagesShipped);
        DatabaseManager.savePrimeDay(PRIME_DAY_FILE, primeDay);

    }

    public static void printStatisticsReport(double profits, int packagesShipped, int numberOfPackages) {
        System.out.println("==========Statistics==========");
        System.out.printf("%-30s", "Profits:");
        System.out.println("$" + profits);
        System.out.printf("%-30s", "Packages Shipped:");
        System.out.println(packagesShipped);
        System.out.printf("%-30s", "Packages in Warehouse:");
        System.out.println(numberOfPackages);
        System.out.println("==============================");
    }

}