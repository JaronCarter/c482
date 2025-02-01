package ims.c482.controllers;

import ims.c482.models.Inventory;
import ims.c482.models.Part;
import ims.c482.models.Product;
import ims.c482.utils.Utils;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The MainController is used for supplying logic to the MainForm view.
 */
public class MainController {
    /** Part search text field **/
    @FXML
    private TextField partsSearch;
    /** Products table **/
    @FXML
    private TableView<Product> productsTable;
    /** Column for product ids **/
    @FXML
    private TableColumn<Product, Integer> columnProductID;
    /** Column for product names **/
    @FXML
    private TableColumn<Product, String> columnProductName;
    /** Column for product stock **/
    @FXML
    private TableColumn<Product, Integer> columnProductInv;
    /** Column for product price **/
    @FXML
    private TableColumn<Product, Double> columnProductPrice;
    /** Product search text field **/
    @FXML
    private TextField productSearch;
    /** Parts table **/
    @FXML
    private TableView<Part> partsTable;
    /** Column for part ids **/
    @FXML
    private TableColumn<Part, Integer> columnPartID;
    /** Column for part names **/
    @FXML
    private TableColumn<Part, String> columnPartName;
    /** Column for part stock **/
    @FXML
    private TableColumn<Part, Integer> columnPartInv;
    /** Column for part price **/
    @FXML
    private TableColumn<Part, Double> columnPartPrice;
    /** Inventory variable holder for the Inventory instance **/
    private Inventory inventory;

    /**
     * Main controller initialization that sets the product or part tables up with proper data for viewing. Gets inventory instance and sets to the inventory variable.
     */
    public void initialize() {
        inventory = Inventory.getInstance();

        partsTable.setItems(inventory.getAllParts());

        columnPartID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        columnPartName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        columnPartInv.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        columnPartPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        productsTable.setItems(inventory.getAllProducts());

        columnProductID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        columnProductName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        columnProductInv.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        columnProductPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

    }

    /**
     * Handler for exiting the application cleanly.
     */
    public void handleExitButtonClick() {
        System.exit(0);
    }

    /**
     * Handler for adding a part.
     * @param event Passed for grabbing the stage in order to set the scene and view for the Add Part Form to show on window.
     * @throws IOException If the Add Part Form has any issue loading.
     */
    public void handlePartAdd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/AddPartForm.fxml"));

        // Load the FXML file directly into a scene
        Scene newScene = new Scene(loader.load(), 599, 402);

        // Get the current stage from the action source (button in this case)
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        primaryStage.setScene(newScene);
    }

    /**
     * Handler for adding product.
     * @param event Passed for grabbing the stage from in order to set the view and scene for Add Product Form to show on window.
     * @throws IOException If Add Product Form view has an issue loading.
     */
    public void handleProductAdd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/AddProductForm.fxml"));

        // Load the FXML file directly into a scene
        Scene newScene = new Scene(loader.load(), 1032, 711);

        // Get the current stage from the action source (button in this case)
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        primaryStage.setScene(newScene);
    }

    /**
     * Handler for opening a part for modification. Passes the selected and highlighted part from the parts table to the Modify Part controller for modification.
     * @param event Passed so the stage can be acquired for setting the view and scene for Modify Part Form.
     * @throws IOException If Modify Part Form has issue loading.
     */
    public void handlePartModify(ActionEvent event) throws IOException {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/ModifyPartForm.fxml"));

            // Load the FXML file directly into a scene
            Scene newScene = new Scene(loader.load(), 599, 402);
            ModifyPartController controller = loader.getController();
            controller.initData(selectedPart);

            // Get the current stage from the action source (button in this case)
            Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            primaryStage.setScene(newScene);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("A part must be selected to modify.");
            alert.showAndWait();
        }
    }

    /**
     * Handler for the Product Modification window. Passes the highlighted and selected product from the product table to the Modify Product controller.
     * @param event For use in grabbing the stage so a new view and scene can be set.
     * @throws IOException If there is issue loading the Modify Product Form.
     */
    public void handleProductModify(ActionEvent event) throws IOException {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            // Open modify form and pass the selected part
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/ModifyProductForm.fxml"));
            // Load the FXML file directly into a scene
            Scene newScene = new Scene(loader.load(), 1032, 711);
            ModifyProductController controller = loader.getController();
            controller.initData(selectedProduct);
            // Get the current stage from the action source (button in this case)
            Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            // Set the new scene on the current stage
            primaryStage.setScene(newScene);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("A product must be selected to modify.");
            alert.showAndWait();
        }


    }

    /**
     * Handler for deleting a part from the parts table. Makes sure a part is selected and that the user confirms they want to delete.
     */
    public void handlePartDelete() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this part?");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                inventory.deletePart(selectedPart);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("A part must be selected to delete.");
            alert.showAndWait();
        }
    }

    /**
     * Handler for parts search. Checks for any part names in the inventory parts list first. If none are found, and the entry is an integer, checks by ID. Sets the table to whatever, if any, it finds.
     * @param event Passed to check if the enter key is pressed during the key event.
     */
    public void handlePartsSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (!partsSearch.getText().isEmpty()){
                ObservableList<Part> parts = inventory.lookupPart(partsSearch.getText());

                if (!parts.isEmpty()) {
                    partsTable.setItems(parts);
                }
                else {
                    if (Utils.isInteger(partsSearch.getText())) {
                        Part idPart = inventory.lookupPart(Integer.parseInt(partsSearch.getText()));
                        ObservableList<Part> idParts = FXCollections.observableArrayList();
                        idParts.add(idPart);
                        if (idPart != null) {
                            partsTable.setItems(idParts);
                        }
                        else {
                            partsTable.setItems(null);
                        }
                    }
                    else {
                        partsTable.setItems(null);
                    }
                }
            }
            else {
                partsTable.setItems(inventory.getAllParts());
            }
        }
    }

    /**
     * Handler for Product Deletion. Checks if a product is selected and confirms if the user wants to delete the product. Also checks to see if any parts are currently associated with the product before allowing for deletion.
     */
    public void handleProductDelete() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            if (!selectedProduct.getAssociatedParts().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The chosen product for deletion has parts associated and cannot be deleted until they are removed.");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this product?");
                ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
                if (result == ButtonType.OK) {
                    inventory.deleteProduct(selectedProduct);
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("A product must be selected to delete.");
            alert.showAndWait();
        }
    }

    /**
     * Handler for Product Search. Checks for any characters in all products list in Inventory before checking by ID next. Updates the table if any are found.
     * @param event Passed to check if the enter key was pressed during the key event that triggers this handler.
     */
    public void handleProductSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (!productSearch.getText().isEmpty()){
                ObservableList<Product> products = inventory.lookupProduct(productSearch.getText());

                if (!products.isEmpty()) {
                    productsTable.setItems(products);
                }
                else {
                    if (Utils.isInteger(productSearch.getText())) {
                        Product idProduct = inventory.lookupProduct(Integer.parseInt(productSearch.getText()));
                        ObservableList<Product> idProducts = FXCollections.observableArrayList();
                        idProducts.add(idProduct);
                        if (idProduct != null) {
                            productsTable.setItems(idProducts);
                        }
                        else {
                            productsTable.setItems(null);
                        }
                    }
                    else {
                        productsTable.setItems(null);
                    }
                }
            }
            else {
                productsTable.setItems(inventory.getAllProducts());
            }
        }
    }
}
