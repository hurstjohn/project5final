import java.io.BufferedReader;
import java.io.*;
import java.util.ArrayList;

/**
 * <h1>Vehicle</h1> Represents a vehicle
 */

public class Vehicle implements Profitable {
    private String licensePlate;
    private double maxWeight;
    private double currentWeight;
    private int zipDest;
    private ArrayList<Package> packages;


    /**
     * Default Constructor
     */
    public Vehicle () {

        this.licensePlate = "";

        this.maxWeight = 0;

        this.currentWeight = 0;

        this.zipDest = 0;

        this.packages = new ArrayList<>();




    }

    /**
     * Constructor
     *
     * @param licensePlate license plate of vehicle
     * @param maxWeight    maximum weight of vehicle
     */

    public Vehicle(String licensePlate, double maxWeight) {

        this();

        this.licensePlate = licensePlate;

        this.maxWeight = maxWeight;



    }



    /**
     * Returns the license plate of this vehicle
     *
     * @return license plate of this vehicle
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Updates the license plate of vehicle
     *
     * @param licensePlate license plate to be updated to
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    /**
     * Returns the maximum weight this vehicle can carry
     *
     * @return the maximum weight that this vehicle can carry
     */
    public double getMaxWeight() {
        return maxWeight;
    }

    /**
     * Updates the maximum weight of this vehicle
     *
     * @param maxWeight max weight to be updated to
     */
    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    /**
     * Returns the current weight of all packages inside vehicle
     *
     * @return current weight of all packages inside vehicle
     */
    public double getCurrentWeight() {
        return currentWeight;
    }

    /**
     * Returns the current ZIP code desitnation of the vehicle
     *
     * @return current ZIP code destination of vehicle
     */
    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getZipDest() {
        return zipDest;
    }

    /***
     * Updates the ZIP code destination of vehicle
     *
     * @param //zipDest ZIP code destination to be updated to
     ***/

    public void setZipDest(int zipDest) {
        this.zipDest = zipDest;
    }

    /**
     * Returns ArrayList of packages currently in Vehicle
     *
     * @return ArrayList of packages in vehicle
     */
    public ArrayList<Package> getPackages() {
        return packages;
    }

    /**
     * Adds Package to the vehicle only if has room to carry it (adding it would not
     * cause it to go over its maximum carry weight).
     *
     * @param pkg Package to add
     * @return whether or not it was successful in adding the package
     */
    public boolean addPackage(Package pkg) {

            if (this.getCurrentWeight() + pkg.getWeight() <= this.getMaxWeight()) {

                packages.add(pkg);

                this.setCurrentWeight(this.getCurrentWeight() + pkg.getWeight());

                return true;


        } else  {

            return false;

        }

    }






    /**
     * Clears vehicle of packages and resets its weight to zero
     */
    public void empty() {
        //figure out where the packages are emptied
        packages.clear();


    }






    /**
     * Returns true if the Vehicle has reached its maximum weight load, false
     * otherwise.
     *
     * @return whether or not Vehicle is full
     */
    public boolean isFull() {

        if (this.getCurrentWeight() == this.getMaxWeight()) {

            return true;

        } else {

            return false;

        }
    }






    /**
     * Fills vehicle with packages with preference of date added and range of its
     * destination zip code. It will iterate over the packages intially at a range
     * of zero and fill it with as many as it can within its range without going
     * over its maximum weight. The amount the range increases is dependent on the
     * vehicle that is using this. This range it increases by after each iteration
     * is by default one.
     *
     * @param warehousePackages List of packages to add from
     */
    public void fill(ArrayList<Package> warehousePackages) {



        for (int i = 0; i < warehousePackages.size() - 1; i++) {


            int zipFirst = warehousePackages.get(i).getDestination().getZipCode();

            int zipSec = warehousePackages.get(i + 1).getDestination().getZipCode();

            Package first = warehousePackages.get(i);

            Package sec = warehousePackages.get(i + 1);


            if (Math.abs(this.getZipDest() - zipFirst) > Math.abs(this.getZipDest() - zipSec)) {

                warehousePackages.set(i + 1, first);
                warehousePackages.set(i, sec);


                i = -1;



            }

            //use arraylist.set method to change if second less than first



        }
       // for (Package p : warehousePackages) {

         //   System.out.println(p.getDestination().getZipCode());
        //    System.out.println(p.getDestination().getName());

     //   }
        try {
            BufferedReader r = new BufferedReader(new FileReader("/Users/Jack Hurst/project5Final/src/files/PrimeDay.txt"));

            int isPrimeDay = Integer.parseInt(r.readLine());

            for (int i = 0; i < warehousePackages.size(); i++) {

                if (isPrimeDay == 1) {
                    double priceDouble = warehousePackages.get(i).getPrice() * .85;
                    double pricePrime = Double.parseDouble(String.format("%.2f", priceDouble));
                    warehousePackages.get(i).setPrice(pricePrime);



                }



                if (warehousePackages.get(i).getWeight() != 0
                        && this.addPackage( warehousePackages.get(i))) {

                    warehousePackages.remove(i);
                    i--;


                }





            }


        } catch (IOException E) {

        }




    }

    public double getProfit() {


        int maxRange = Math.abs(this.getZipDest() -
                this.getPackages().get(this.getPackages().size() - 1).getDestination().getZipCode());


        double revenue = 0;

        for (int i = 0; i < this.getPackages().size(); i++) {

            revenue += this.getPackages().get(i).getPrice();

        }

        String profitString =  Double.toString(revenue - (double) maxRange * 1);
        return Double.parseDouble(String.format("%.2f",profitString));

    }

    public String report() {

        return null;

    }

}


