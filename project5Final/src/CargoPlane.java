import java.io.*;
import java.util.ArrayList;


/**
 * <h1>CargoPlane</h1> Represents a Cargo Plane
 */
public class CargoPlane extends Vehicle {
    final double GAS_RATE = 2.33;

    String licensePlate;
    double maxWeight;

    /**
     * Default Constructor
     */



    public CargoPlane() {


        super();


    }

    /**
     * Constructor
     *
     * @param licensePlate license plate of vehicle
     * @param maxWeight    maximum weight that the vehicle can hold
     */

    public CargoPlane(String licensePlate, double maxWeight) {


        super(licensePlate,maxWeight);

        this.maxWeight = maxWeight;
        this.licensePlate = licensePlate;
    }



    /**
     * Overides its superclass method. Instead, after each iteration, the range will
     * increase by 10.
     *
     * @param warehousePackages List of packages to add from
     */


    @Override
    public void fill(ArrayList<Package> warehousePackages) {

        for (int i = 0; i <warehousePackages.size() - 1; i++) {

            int zipFirst = warehousePackages.get(i).getDestination().getZipCode();


            if (Math.abs(zipFirst - this.getZipDest()) % 10 != 0) {

                warehousePackages.remove(i);

                i = -1;

            }


        }

        //somewhere in this loop lies the infinite loop
        for (int i = 0; i < warehousePackages.size() - 1; i++) {


            int zipFirst = warehousePackages.get(i).getDestination().getZipCode();

            int zipSec = warehousePackages.get(i + 1).getDestination().getZipCode();

            Package first = warehousePackages.get(i);

            Package sec = warehousePackages.get(i + 1);


            // i believe it is here
            if (Math.abs(this.getZipDest() - zipFirst) > Math.abs(this.getZipDest() - zipSec)) {

                warehousePackages.set(i + 1, first);
                warehousePackages.set(i, sec);


                i = 0;



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

                //warehousePackages.get(i).getWeight() != 0
                      //  && Math.abs(warehousePackages.get(i).getDestination().zipCode -
                     //   this.getZipDest()) % 10 == 0

                     //   &&

                if ( this.addPackage( warehousePackages.get(i))) {

                    warehousePackages.remove(i);
                    i--;


                }




                //this.addPackage( warehousePackages.get(i));

            }


        } catch (IOException E) { }




    }
    /*
     * =============================================================================
     * | Methods from Profitable Interface
     * =============================================================================
     */

    /**
     * Returns the profits generated by the packages currently in the Cargo Plane.
     * <p>
     * &sum;p<sub>price</sub> - (range<sub>max</sub> &times; 2.33)
     * </p>
     */
    @Override
    public double getProfit() {

        int maxRange = 0;
        try {
            maxRange = Math.abs(this.getZipDest() -
                    this.getPackages().get(this.getPackages().size() - 1).getDestination().getZipCode());

        } catch (IndexOutOfBoundsException e) {}
        double revenue = 0;

        for (int i = 0; i < this.getPackages().size(); i++) {

            revenue += this.getPackages().get(i).getPrice();

        }

        return revenue - (double) maxRange * GAS_RATE;
    }

    /**
     * Generates a String of the Cargo Plane report. Cargo plane report includes:
     * <ul>
     * <li>License Plate No.</li>
     * <li>Destination</li>
     * <li>Current Weight/Maximum Weight</li>
     * <li>Net Profit</li>
     * <li>Shipping labels of all packages in cargo plane</li>
     * </ul>
     *
     * @return Cargo Plane Report
     */
    @Override
    public String report() {


        String report = "";


        report += "==========Cargo Plane Report==========\n";
        report += "License Plate No.: " + this.getLicensePlate() + "\n";
        report += "Weight Load: " + this.getCurrentWeight() + "/" + this.getMaxWeight() + "\n";
        report += "Net Profit: $" + String.format("%.2f", this.getProfit()) + "\n";
        //report += "==============================\n";
        report += "=====Shipping Labels=====\n";


        for (int i = 0; i < this.getPackages().size(); i++) {

            report += "====================\n";
            report += "TO: " + this.getPackages().get(i).getDestination().getName() + "\n";
            report += this.getPackages().get(i).getDestination().getAddress() + "\n";
            report += this.getPackages().get(i).getDestination().getCity() + ", "
                    + this.getPackages().get(i).getDestination().getState() + ", "
                    + this.getPackages().get(i).getDestination().getZipCode() + "\n";
            report += "Weight:         " + this.getPackages().get(i).getWeight() + "\n";
            report += "Price:        $" + this.getPackages().get(i).getPrice() + "\n";
            report += "Product:" + this.getPackages().get(i).getProduct() + "\n";
            report += "====================\n";

        }


        return report;
    }


    public static void main (String [] args) {

        Package a = new Package();

        ShippingAddress w = new ShippingAddress();

        w.setName("A");
        w.setZipCode(47906);
        a.setDestination(w);
        a.setWeight(10);


        Package b = new Package();

        ShippingAddress x = new ShippingAddress();

        x.setName("B");
        x.setZipCode(47916);
        b.setDestination(x);
        b.setWeight(10);


        Package c = new Package();

        ShippingAddress y = new ShippingAddress();

        y.setName("C");
        y.setZipCode(47904);
        c.setDestination(y);
        c.setWeight(10);

        Package d = new Package();

        ShippingAddress z = new ShippingAddress();

        z.setName("D");
        z.setZipCode(47907);
        d.setDestination(z);
        d.setWeight(10);

        ArrayList <Package> pkgs = new ArrayList<Package>();

        pkgs.add(a);
        pkgs.add(b);
        pkgs.add(c);
        pkgs.add(d);


        CargoPlane v = new CargoPlane("asdjioasdj", 0);

        v.setZipDest(47906);

        System.out.println(v.getMaxWeight());
        System.out.println(v.getCurrentWeight());


        for (Package p : pkgs) {

            System.out.println(p.getDestination().getZipCode());
            System.out.println(p.getDestination().getName());


        }

        v.fill(pkgs);


        System.out.println("\n\n");


        ArrayList<Package> finalPackages = v.getPackages();


        for (Package p : finalPackages) {

            System.out.println(p.getDestination().getZipCode());
            System.out.println(p.getDestination().getName());

        }


        v.getProfit();


    }

}