package ims.c482.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static final Inventory INSTANCE = new Inventory();

    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    private Inventory() {
        this.allParts = FXCollections.observableArrayList();
        this.allProducts = FXCollections.observableArrayList();
    }

    public static Inventory getInstance() {
        return INSTANCE;
    }

    public void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    public Part lookupPart(int partId) {
        return allParts.get(partId);
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
    public void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    public void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }
    public boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }
    public boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }
    public ObservableList<Part> getAllParts() {
        return allParts;
    }
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
