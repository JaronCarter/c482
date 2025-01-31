package ims.c482.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static final Inventory INSTANCE = new Inventory();

    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;
    private Integer partsCounter;
    private Integer productsCounter;

    private Inventory() {
        this.allParts = FXCollections.observableArrayList();
        this.allProducts = FXCollections.observableArrayList();
        this.partsCounter = 1;
        this.productsCounter = 1;
    }

    public static Inventory getInstance() {
        return INSTANCE;
    }

    public int getPartsCount() {
        return partsCounter;
    }

    public int getProductsCount() {
        return productsCounter;
    }

    public void addPart(Part newPart) {
        allParts.add(newPart);
        partsCounter++;
    }

    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
        productsCounter++;
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
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public Part lookupPart(String partName) {
        return allParts.stream().filter(p -> p.getName().startsWith(partName)).findFirst().orElse(null);
    }

    public Product lookupProduct(String productName) {
        return allProducts.stream().filter(p -> p.getName().startsWith(productName)).findFirst().orElse(null);
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
}
