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
import java.util.Optional;

/**
 * The modify product controller is used in tandem with the modify product view so any user may modify an existing product currently in the inventory product list.
 */
public class ModifyProductController {

    /** Product ID field **/
    @FXML
    private TextField idField;
    /** Product name field **/
    @FXML
    private TextField nameField;
    /** Product stock field **/
    @FXML
    private TextField invField;
    /** Product price field **/
    @FXML
    private TextField priceField;
    /** Product max inventory field **/
    @FXML
    private TextField maxField;
    /** Product min inventory field **/
    @FXML
    private TextField minField;
    /** The product's associated parts table **/
    @FXML
    private TableView<Part> associatedPartsTable;
    /** Column for associated part ids **/
    @FXML
    private TableColumn<Part, Integer> columnAssociatedID;
    /** Column for associated part names**/
    @FXML
    private TableColumn<Part, String> columnAssociatedName;
    /** Column for associated part stock **/
    @FXML
    private TableColumn<Part, Integer> columnAssociatedInv;
    /** Column for associated part price **/
    @FXML
    private TableColumn<Part, Double> columnAssociatedPrice;
    /** All parts table **/
    @FXML
    private TableView<Part> allPartsTable;
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
    /** Error label for updating if user validation fails **/
    @FXML
    private Label errorLabel;
    /** All parts search field **/
    @FXML
    private TextField searchField;
    /** Selected product holder variable for updating with the product passed from the main form **/
    private Product selectedProduct;
    /** Index holder for the selected product for modification **/
    private Integer selectedIndex;
    /** Inventory holder for the Inventory instance **/
    private Inventory inventory;
    /** Associated parts holder for filling with the selected product's associated parts list if any **/
    private ObservableList<Part> associatedParts;

    /**
     * Initializes data and fields for use in modification. This is publicly accessible and is meant for use from the main controller. Gives the ability for the main controller to pass selected products for modification.
     * @param product Passed through to give the modify product controller the exact product to modify.
     */
    public void initData(Product product) {
        selectedProduct = product;
        selectedIndex = inventory.getAllProducts().indexOf(product);
        associatedParts = selectedProduct.getAssociatedParts();
        associatedPartsTable.setItems(associatedParts);

        idField.setText(String.valueOf(product.getId()));
        nameField.setText(product.getName());
        invField.setText(String.valueOf(product.getStock()));
        priceField.setText(String.valueOf(product.getPrice()));
        maxField.setText(String.valueOf(product.getMax()));
        minField.setText(String.valueOf(product.getMin()));
    }

    /**
     * Modify Product Controller initialization of inventory instance and both part tables.
     */
    public void initialize() {
        inventory = Inventory.getInstance();

        allPartsTable.setItems(inventory.getAllParts());
        columnPartID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        columnPartName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        columnPartInv.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        columnPartPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        associatedPartsTable.setItems(associatedParts);
        columnAssociatedID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        columnAssociatedName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        columnAssociatedInv.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        columnAssociatedPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
    }

    /**
     * Handler for cancelling modification and returing to Main Form.
     * @param event Used for pulling the stage for setting the scene and view to show Main Form on window.
     * @throws IOException If Main Form has issues loading.
     */
    public void handleCancelBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/MainForm.fxml"));

        // Load the FXML file directly into a scene
        Scene newScene = new Scene(loader.load(), 1094, 481);

        // Get the current stage from the action source (button in this case)
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        primaryStage.setScene(newScene);
    }

    /**
     * Handler for saving the modified product. Validates all user input before saving.
     * @param event Passed for grabbing stage to change scene and view back to Main Form on proper save.
     * @throws IOException If Main Form has issues loading.
     */
    public void handleSave(ActionEvent event) throws IOException {
        StringBuilder errors = new StringBuilder();

        if (nameField.getText().isEmpty()) {
            errors.append("Please enter a valid name\n");
        }
        if (invField.getText().isEmpty() || !Utils.isInteger(invField.getText())) {
            errors.append("Please enter a valid inventory level\n");
        }
        if (priceField.getText().isEmpty() || !Utils.isDouble(priceField.getText())) {
            errors.append("Please enter a valid price\n");
        }
        try {
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());
            int inv = Integer.parseInt(invField.getText());
            if(max<min){
                errors.append("The maximum inventory limit must be greater than the minimum inventory limit\n");
            }
            else if(max<inv || inv<min){
                errors.append("The inventory limit cannot exceed nor be less than the maximum and minimum limits respectively\n");
            }
        } catch (NumberFormatException e) {
            if (!Utils.isInteger(maxField.getText())) {
                errors.append("Please enter a valid maximum inventory limit\n");
            }
            if (!Utils.isInteger(minField.getText())) {
                errors.append("Please enter a valid minimum inventory limit\n");
            }
        }

        if (errors.isEmpty()){
            selectedProduct.setName(nameField.getText());
            selectedProduct.setStock(Integer.parseInt(invField.getText()));
            selectedProduct.setPrice(Double.parseDouble(priceField.getText()));
            selectedProduct.setMax(Integer.parseInt(maxField.getText()));
            selectedProduct.setMin(Integer.parseInt(minField.getText()));

            inventory.updateProduct(selectedIndex, selectedProduct);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/MainForm.fxml"));

            // Load the FXML file directly into a scene
            Scene newScene = new Scene(loader.load(), 1094, 481);

            // Get the current stage from the action source (button in this case)
            Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            primaryStage.setScene(newScene);
        }
        else {
            errorLabel.setText(errors.toString());
        }
    }

    /**
     * Handler for adding parts from the parts table to the product's associated parts table.
     */
    public void handleAdd() {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            associatedParts.add(selectedPart);
            associatedPartsTable.setItems(associatedParts);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a part to add");
            alert.showAndWait();
        }
    }

    /**
     * Handler for removing parts from the product's association. Verifies if user does want to remove first.
     */
    public void handleRemove() {
        if (associatedParts != null && !associatedParts.isEmpty()) {
            Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();

            if (selectedPart != null) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirmation");
                confirmation.setHeaderText(null);
                confirmation.setContentText("Are you sure you want to delete this part?");
                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.get() == ButtonType.OK) {
                    associatedParts.remove(selectedPart);
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a part to remove");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("There are no parts available to remove.");
            alert.showAndWait();
        }
    }

    /**
     * Handler for searching all parts. First checks any characters against any products containing said characters in their name, then checks by ID before setting the table to what it finds (if any).
     * @param event For checking if the enter key is what caused the event.
     */
    public void handleSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (!searchField.getText().isEmpty()){
                ObservableList<Part> parts = inventory.lookupPart(searchField.getText());

                if (!parts.isEmpty()) {
                    allPartsTable.setItems(parts);
                }
                else {
                    if (Utils.isInteger(searchField.getText())) {
                        Part idPart = inventory.lookupPart(Integer.parseInt(searchField.getText()));
                        ObservableList<Part> idParts = FXCollections.observableArrayList();
                        idParts.add(idPart);
                        if (idPart != null) {
                            allPartsTable.setItems(idParts);
                        }
                        else {
                            allPartsTable.setItems(null);
                        }
                    }
                    else {
                        allPartsTable.setItems(null);
                    }
                }
            }
            else {
                allPartsTable.setItems(inventory.getAllParts());
            }
        }
    }
}
