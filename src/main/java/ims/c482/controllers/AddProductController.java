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
 * The add product controller is used in tandem to the add product form. Allows for adding of products to the inventory products list.
 */
public class AddProductController {
    /** The table to show all parts available for adding to a product. **/
    @FXML
    private TableView<Part> allPartsTable;
    /** The column for part ids **/
    @FXML
    private TableColumn<Part, Integer> columnPartID;
    /** Column for Part Names **/
    @FXML
    private TableColumn<Part, String> columnPartName;
    /** Column for Part Inventory Stock **/
    @FXML
    private TableColumn<Part, Integer> columnPartInv;
    /** Column for Part Price **/
    @FXML
    private TableColumn<Part, Double> columnPartPrice;
    /** Table for the parts associated with the product being created. **/
    @FXML
    private TableView<Part> associatedPartsTable;
    /** Column for associated part ids **/
    @FXML
    private TableColumn<Part, Integer> columnAssociatedID;
    /** Column for associated part names **/
    @FXML
    private TableColumn<Part, String> columnAssociatedName;
    /** Column for associated part stock **/
    @FXML
    private TableColumn<Part, Integer> columnAssociatedInv;
    /** Column for associated part price **/
    @FXML
    private TableColumn<Part, Double> columnAssociatedPrice;
    /** Product ID field **/
    @FXML
    private TextField idField;
    /** Product name field **/
    @FXML
    private TextField nameField;
    /** Product Inventory Stock Field **/
    @FXML
    private TextField invField;
    /** Product Price Field **/
    @FXML
    private TextField priceField;
    /** Product Max Inventory Field **/
    @FXML
    private TextField maxField;
    /** Product Min Inventory Field **/
    @FXML
    private TextField minField;
    /** Error Label for adding error information to if any **/
    @FXML
    private Label errorLabel;
    /** Search Field for filtering all available parts **/
    @FXML
    private TextField searchField;
    /** The Inventory Instance for managing all parts and product stock. **/
    private Inventory inventory = Inventory.getInstance();
    /** A temporary associated parts list holder for the product to be. **/
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Initializes the controller for adding products. Sets the all parts table with available parts and adds the future ID to the ID field.
     */
    public void initialize() {
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

        idField.setText(Integer.toString(inventory.getProductsCount()));
    }


    /**
     * On cancel button press, takes user back to Main Form.
     * @param event Is passed in order to pull the stage for setting the scene and view for Main Form.
     * @throws IOException If the Main Form view fails to load for any reason.
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
     * Handler for adding parts from the parts table to the associated parts table for the product. Verifies a part is selected before doing so.
     */
    public void handleAdd() {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            associatedParts.add(selectedPart);
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
     * Handler for removing a part from the associated parts list. Verifies a part is selected first and also confirms whether the user means to remove the part selected.
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
     * Handler for saving the new product to the Inventory products list. Validation is done on all fields proceeding the save, otherwise the error label is updated with what the user needs to fix to proceed. Since the class method details specified by the UML diagram insist upon a single part insertion at a time, instead of appending a list of parts I have a loop that adds the parts one at a time to the product before saving.
     * @param event Is passed for grabbing the stage to set a scene and Main Form view for return on proper save.
     * @throws IOException If Main Form view fails to load for any reason.
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
            Product product = new Product(Integer.parseInt(idField.getText()),nameField.getText(),Double.parseDouble(priceField.getText()),Integer.parseInt(invField.getText()),Integer.parseInt(maxField.getText()),Integer.parseInt(minField.getText()));
            for(Part part : associatedParts) {
                product.addAssociatedPart(part);
            }
            inventory.addProduct(product);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/MainForm.fxml"));

            // Load the FXML file directly into a scene
            Scene newScene = new Scene(loader.load(), 1094, 481);

            // Get the current stage from the action source (button in this case)
            Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            primaryStage.setScene(newScene);
        }
        else{
            errorLabel.setText(errors.toString());
        }


    }

    /**
     * Handler for searching for parts containing any particular set of characters OR by ID.
     * @param event Passed through so we can do a conditional check on the event to see if the Enter key was pressed rather than by any keystroke.
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
