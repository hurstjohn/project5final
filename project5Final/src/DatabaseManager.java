import java.io.*;
import java.util.ArrayList;

/**
 * <h1>Database Manager</h1>
 * <p>
 * Used to locally save and retrieve data.
 */
public class DatabaseManager {

    /**
     * Creates an ArrayList of Vehicles from the passed CSV file. The values are in
     * the CSV file as followed:
     * <ol>
     * <li>Vehicle Type (Truck/Drone/Cargo Plane)</li>
     * <li>Vehicle License Plate</li>
     * <li>Maximum Carry Weight</li>
     * </ol>
     * If filePath does not exist, a blank ArrayList will be returned.
     *
     * @param file CSV File
     * @return ArrayList of vehicles
     */
    public static ArrayList<Vehicle> loadVehicles(File file) {
        //TODO
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] vehicleParts = line.split(",");
                vehicles.add(new Vehicle(vehicleParts[1], Double.parseDouble(vehicleParts[2])));
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found in method loadVehicles");
        } catch (IOException e) {
            System.out.println("IOException in method loadVehicles");
        }
        return vehicles;
    }


    /**
     * Creates an ArrayList of Packages from the passed CSV file. The values are in
     * the CSV file as followed:
     * <ol>
     * <li>ID</li>
     * <li>Product Name</li>
     * <li>Weight</li>
     * <li>Price</li>
     * <li>Address Name</li>
     * <li>Address</li>
     * <li>City</li>
     * <li>State</li>
     * <li>ZIP Code</li>
     * </ol>
     * <p>
     * If filePath does not exist, a blank ArrayList will be returned.
     *
     * @param file CSV File
     * @return ArrayList of packages
     */
    public static ArrayList<Package> loadPackages(File file) {
        //TODO
        ArrayList<Package> packages = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] packageParts = line.split(",");
                packages.add(new Package(packageParts[0], packageParts[1], Double.parseDouble(packageParts[2]), Double.parseDouble(packageParts[3]), new ShippingAddress(packageParts[4], packageParts[5], packageParts[6], packageParts[7], Integer.parseInt(packageParts[8]))));
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found in method loadPackages");
            return packages;
        } catch (IOException e) {
            System.out.println("IOException in method loadpackages");
        }
        return packages;
    }


    /**
     * Returns the total Profits from passed text file. If the file does not exist 0
     * will be returned.
     *
     * @param file file where profits are stored
     * @return profits from file
     */
    public static double loadProfit(File file) {
        //TODO
        double profits = 0.0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                profits += Double.parseDouble(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found in method loadProfits");
            return 0;
        } catch (IOException e) {
            System.out.println("IOException in method loadProfits");
        }
        return profits;
    }


    /**
     * Returns the total number of packages shipped stored in the text file. If the
     * file does not exist 0 will be returned.
     *
     * @param file file where number of packages shipped are stored
     * @return number of packages shipped from file
     */
    public static int loadPackagesShipped(File file) {
        //TODO
        int packagesShipped = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                packagesShipped += Integer.parseInt(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found in method loadPackagesShipped");
            return 0;
        } catch (IOException e) {
            System.out.println("IOException in method loadPackagesShipped");
        }
        return packagesShipped;
    }


    /**
     * Returns whether or not it was Prime Day in the previous session. If file does
     * not exist, returns false.
     *
     * @param file file where prime day is stored
     * @return whether or not it is prime day
     */
    public static boolean loadPrimeDay(File file) {
        //TODO
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if (Integer.parseInt(line) == 1) {
                    return true;
                } else {
                    return false;
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found in method loadPackagesShipped");
            return false;
        } catch (IOException e) {
            System.out.println("IOException in method loadPackagesShipped");
        }
        return false; //this line should never be reached
    }


    /**
     * Saves (writes) vehicles from ArrayList of vehicles to file in CSV format one vehicle per line.
     * Each line (vehicle) has following fields separated by comma in the same order.
     * <ol>
     * <li>Vehicle Type (Truck/Drone/Cargo Plane)</li>
     * <li>Vehicle License Plate</li>
     * <li>Maximum Carry Weight</li>
     * </ol>
     *
     * @param file     File to write vehicles to
     * @param vehicles ArrayList of vehicles to save to file
     */
    public static void saveVehicles(File file, ArrayList<Vehicle> vehicles) {
        //TODO
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int a = 0; a < vehicles.size(); a++) {
                Vehicle currentVehicle = vehicles.get(a);
                if (currentVehicle instanceof Truck) {
                    bw.write("Truck," + currentVehicle.getLicensePlate() + "," + currentVehicle.getMaxWeight()+"\n");
                } else if (currentVehicle instanceof Drone) {
                    bw.write("Drone," + currentVehicle.getLicensePlate() + "," + currentVehicle.getMaxWeight()+"\n");
                } else if (currentVehicle instanceof CargoPlane) {
                    bw.write("Cargo Plane," + currentVehicle.getLicensePlate() + "," + currentVehicle.getMaxWeight()+"\n");
                }
            }
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found in method saveVehicles");
        } catch (IOException e) {
            System.out.println("OIException in method saveVehices");
        }
    }


    /**
     * Saves (writes) packages from ArrayList of package to file in CSV format one package per line.
     * Each line (package) has following fields separated by comma in the same order.
     * <ol>
     * <li>ID</li>
     * <li>Product Name</li>
     * <li>Weight</li>
     * <li>Price</li>
     * <li>Address Name</li>
     * <li>Address</li>
     * <li>City</li>
     * <li>State</li>
     * <li>ZIP Code</li>
     * </ol>
     *
     * @param file     File to write packages to
     * @param packages ArrayList of packages to save to file
     */
    public static void savePackages(File file, ArrayList<Package> packages) {
        //TODO
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int a = 0; a < packages.size(); a++) {
                Package currentPackage = packages.get(a);
                String id = currentPackage.getID();
                String name = currentPackage.getProduct();
                double weight = currentPackage.getWeight();
                double price = currentPackage.getPrice();
                ShippingAddress shippingAddress = currentPackage.getDestination();
                String buyerName = shippingAddress.getName();
                String address = shippingAddress.getAddress();
                String city = shippingAddress.getCity();
                String state = shippingAddress.getState();
                int zip = shippingAddress.getZipCode();
                bw.write(id + "," + name + "," + Double.toString(weight) + "," + Double.toString(price) + "," + buyerName + "," + address + "," + city + "," + state + "," + Integer.toString(zip)+"\n");
            }
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found in method savePackages");
        } catch (IOException e) {
            System.out.println("OIException in method savePackages");
        }
    }


    /**
     * Saves profit to text file.
     *
     * @param file   File to write profits to
     * @param profit Total profits
     */

    public static void saveProfit(File file, double profit) {
        //TODO
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(Double.toString(profit)+"\n");
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found in method saveProfit");
        } catch (IOException e) {
            System.out.println("OIException in method saveProfit");
        }
    }


    /**
     * Saves number of packages shipped to text file.
     *
     * @param file      File to write profits to
     * @param nPackages Number of packages shipped
     */

    public static void savePackagesShipped(File file, int nPackages) {
        //TODO
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(Integer.toString(nPackages)+"\n");
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found in method savePackagesShipped");
        } catch (IOException e) {
            System.out.println("OIException in method savePackagesShipped");
        }
    }


    /**
     * Saves status of prime day to text file. If it is primeDay "1" will be
     * writtern, otherwise "0" will be written.
     *
     * @param file     File to write profits to
     * @param primeDay Whether or not it is Prime Day
     */

    public static void savePrimeDay(File file, boolean primeDay) {
        //TODO
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            if (primeDay == true) {
                bw.write("1");
            } else {
                bw.write("0");
            }
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found in method savePrimeDay");
        } catch (IOException e) {
            System.out.println("OIException in method savePrimeDay");
        }
    }
}