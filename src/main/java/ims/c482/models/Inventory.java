package ims.c482.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class for managing the part and product lists as well as housing a singleton inventory instantiation for clean and efficient access to all inventory data.
 */
public class Inventory {
    /** Instantiates a single and final instance of inventory to keep from accidental duplication or loss of data due to any other instance. **/
    private static final Inventory INSTANCE = new Inventory();
    /** List for tracking all parts in inventory. **/
    private ObservableList<Part> allParts;
    /** List for tracking all products in inventory **/
    private ObservableList<Product> allProducts;
    /** Parts counter used for ID tracking. **/
    private Integer partsCounter;
    /** Products counter for ID tracking. **/
    private Integer productsCounter;

    /**
     * Inventory constructor that assigns the variables and sets counters to begin at 1.
     */
    private Inventory() {
        this.allParts = FXCollections.observableArrayList();
        this.allProducts = FXCollections.observableArrayList();
        this.partsCounter = 1;
        this.productsCounter = 1;
    }

    /**
     * Getter for inventory instance.
     * @return Returns the singleton instance of inventory.
     */
    public static Inventory getInstance() {
        return INSTANCE;
    }

    /**
     * Getter for parts counter. Used for part ID creation.
     * @return Returns current integer from parts counter.
     */
    public int getPartsCount() {
        return partsCounter;
    }
    /**
     * Getter for products counter. Used for product ID creation.
     * @return Returns current integer from products counter.
     */
    public int getProductsCount() {
        return productsCounter;
    }

    /**
     * Adds part to inventory. Ticks part counter up by 1 for ID tracking.
     * @param newPart Takes in the new part for adding to the list.
     */
    public void addPart(Part newPart) {
        allParts.add(newPart);
        partsCounter++;
    }

    /**
     * Adds product to inventory. Ticks counter up by 1 for product ID tracking.
     * @param newProduct Takes in the new product for adding to the list.
     */
    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
        productsCounter++;
    }

    /**
     * Method for looking up parts in inventory.
     * @param partId Part ID is passed for lookup by ID.
     * @return Returns part found if any.
     */
    public Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Lookup method for products by ID.
     * @param productId Takes a product ID and loops through all products in inventory to see if an ID matches.
     * @return Returns a product if one is found.
     */
    public Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Lookup part method by name. Creates a temporary parts list for adding all parts matching the string's characters passed through.
     * @param partName Passed through for checking against any part that may contain that string of characters.
     * @return Returns a part, or parts, if any.
     */
    public ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                parts.add(part);
            }
        }
        return parts;
    }

    /**
     * Lookup product method by name. Creates a temporary product list for adding all products matching the string's characters passed through.
     * @param productName Passed through for checking against any product that may contain that string of characters.
     * @return Returns a product, or products, if any.
     */
    public ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                products.add(product);
            }
        }
        return products;
    }

    /**
     * Updates a part in the list by index.
     * @param index Index passed for reference to update.
     * @param selectedPart Part passed for updating at specified index.
     */
    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates a product in the list by index.
     * @param index Index passed for reference to update.
     * @param selectedProduct Product passed for updating at specified index.
     */
    public void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Delete part method.
     * @param selectedPart Takes a part to be removed from the parts list.
     * @return Returns a boolean if a part was removed.
     */
    public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Delete product method.
     * @param selectedProduct Takes a product to be removed from the parts list.
     * @return Returns a boolean if a product was removed.
     */
    public boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Getter method to return all parts in inventory.
     * @return Returns a list of parts.
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Getter method to return all products in inventory.
     * @return Returns a list of products.
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
