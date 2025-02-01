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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ModifyProductController {

    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public TableView<Part> associatedPartsTable;
    public TableColumn<Part, Integer> columnAssociatedID;
    public TableColumn<Part, String> columnAssociatedName;
    public TableColumn<Part, Integer> columnAssociatedInv;
    public TableColumn<Part, Double> columnAssociatedPrice;
    public TableView<Part> allPartsTable;
    public TableColumn<Part, Integer> columnPartID;
    public TableColumn<Part, String> columnPartName;
    public TableColumn<Part, Integer> columnPartInv;
    public TableColumn<Part, Double> columnPartPrice;
    public Label errorLabel;
    public TextField searchField;
    private Product selectedProduct;
    private Integer selectedIndex;
    private Inventory inventory;
    private ObservableList<Part> associatedParts;

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

    public void handleCancelBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/MainForm.fxml"));

        // Load the FXML file directly into a scene
        Scene newScene = new Scene(loader.load(), 1094, 481);

        // Get the current stage from the action source (button in this case)
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        primaryStage.setScene(newScene);
    }

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

    public void handleAdd(ActionEvent event) {
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

    public void handleRemove(ActionEvent event) {
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
