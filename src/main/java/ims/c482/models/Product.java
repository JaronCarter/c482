package ims.c482.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class for instantiating product objects.
 */
public class Product {
    /** List of parts associated with the product. **/
    private ObservableList<Part> associatedParts;
    /** Product ID **/
    private int id;
    /** Product name **/
    private String name;
    /** Product price **/
    private double price;
    /** Product stock **/
    private int stock;
    /** Product min stock **/
    private int min;
    /** Product max stock **/
    private int max;

    /**
     * Product constructor
     * @param id Product ID
     * @param name Product name
     * @param price Product price
     * @param stock Product stock
     * @param min Product min stock
     * @param max Product max stock
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();
    }

    /**
     * Setter method for setting product ids.
     * @param id Takes new id for setting to current object.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter method for setting product name.
     * @param name Takes new name for setting to current object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter method for setting product price.
     * @param price Takes new price for setting to current object.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Setter method for setting product stock.
     * @param stock Takes new stock for setting to current object.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Setter method for setting product minimum stock.
     * @param min Takes new min for setting to current object.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Setter method for setting product maximum stock.
     * @param max Takes new max for setting to current object.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Getter method for retrieving product ID.
     * @return Returns the product ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method for retrieving product name.
     * @return Returns the product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for retrieving product price.
     * @return Returns the product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter method for retrieving product stock.
     * @return Returns the product stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Getter method for retrieving product minimum stock.
     * @return Returns the product minimum stock.
     */
    public int getMin() {
        return min;
    }

    /**
     * Getter method for retrieving product maximum stock.
     * @return Returns the product maximum stock.
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds a part to the list of associated parts for the product.
     * @param part The part to be added to the associated parts list.
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * Deletes a part from the list of associated parts for the product.
     * @param part The part to be removed from the associated parts list.
     */
    public void deleteAssociatedPart(Part part) {
        this.associatedParts.remove(part);
    }

    /**
     * Getter method for retrieving the list of associated parts.
     * @return Returns the list of parts associated with the product.
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
}