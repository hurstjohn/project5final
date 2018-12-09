/**
 * <h1>Package</h1> Represents a package
 */

import java.text.NumberFormat;

public class Package {
    private String id;
    private String product;
    private double weight;
    private double price;
    private ShippingAddress destination;

    /**
     * Default Constructor
     */
    //============================================================================
    //TODO
    public Package() {
        id = "";
        product = "";
        weight = 0.0;
        price = 0.0;
        destination = new ShippingAddress();
    }
    //============================================================================

    /**
     * Constructor
     *
     * @param id          id number of product
     * @param product     name of product in package
     * @param weight      weight of package
     * @param price       price of product
     * @param destination the destination of the package
     */
    //============================================================================
    //TODO
    public Package(String id, String product, double weight, double price, ShippingAddress destination) {
        this.id = id;
        this.product = product;
        this.weight = weight;
        this.price = price;
        this.destination = destination;
    }
    //============================================================================

    /**
     * @return id of package
     */
    public String getID() {
        //TODO
        return id;
    }


    /**
     * @return Name of product in package
     */
    public String getProduct() {
        //TODO
        return product;
    }


    /**
     * @param product the product name to set
     */
    public void setProduct(String product) {
        //TODO
        this.product = product;
    }


    /**
     * @return price of product in package
     */
    public double getPrice() {
        //TODO
        return price;
    }


    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        //TODO
        this.price = price;
    }


    /**
     * @return Package weight
     */
    public double getWeight() {
        //TODO
        return weight;
    }


    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        //TODO
        this.weight = weight;
    }


    /**
     * @return The shipping address of package
     */
    public ShippingAddress getDestination() {
        //TODO
        return destination;
    }


    /**
     * @param destination the shipping address to set
     */
    public void setDestination(ShippingAddress destination) {
        //TODO
        this.destination = destination;
    }


    /**
     * @return The package's shipping label.
     */
    public String shippingLabel() {
        //TODO
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        String stringLabel = "";
        stringLabel += "====================\n";
        stringLabel += "TO: " + destination.getName() + "\n";
        stringLabel += destination.getAddress() + "\n";
        stringLabel += destination.getCity() + ", " + destination.getState() +", "+destination.getZipCode()+"\n" ;
        stringLabel += "Weight:         ";
        stringLabel += String.format("%.2f", weight) + "\n";
        stringLabel += "Price:        ";
        stringLabel += nf.format(price) + "\n";
        stringLabel += "Product:"+product+"\n";
        stringLabel += "====================";
        return stringLabel;
    }

}