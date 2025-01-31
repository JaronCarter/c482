package ims.c482.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static final Inventory INSTANCE = new Inventory();

    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;
    private Integer counter;

    private Inventory() {
        this.allParts = FXCollections.observableArrayList();
        this.allProducts = FXCollections.observableArrayList();
        this.counter = 1;
    }

    public static Inventory getInstance() {
        return INSTANCE;
    }

    public void addPart(Part newPart) {
        allParts.add(newPart);
        counter++;
    }

    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * If I had to make this code better, the following lookupPart method might be where to start. Right now it loops through all parts, but what if the parts grew in size substantially?
     * It would be worth changing this approach and having the IDs changed from Integers and use hashed ids. This would allow for usage of a hash table, and if names would be considered
     * unique it would allow for quick and efficient lookups of parts regardless the size of the dataset.
     */
    public Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public Product lookupProduct(int productId) {
        return allProducts.get(productId);
    }

    public Part lookupPart(String partName) {
        return allParts.stream().filter(p -> p.getName().equals(partName)).findFirst().orElse(null);
    }

    public Product lookupProduct(String productName) {
        return allProducts.stream().filter(p -> p.getName().equals(productName)).findFirst().orElse(null);
    }

    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public Integer getCounter() {
        return counter;
    }
}
