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

public class MainController {
    public TextField partsSearch;
    public TableView<Product> productsTable;
    public TableColumn<Product, Integer> columnProductID;
    public TableColumn<Product, String> columnProductName;
    public TableColumn<Product, Integer> columnProductInv;
    public TableColumn<Product, Double> columnProductPrice;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> columnPartID;
    @FXML
    private TableColumn<Part, String> columnPartName;
    @FXML
    private TableColumn<Part, Integer> columnPartInv;
    @FXML
    private TableColumn<Part, Double> columnPartPrice;

    private Inventory inventory;

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

    public void handleExitButtonClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void handlePartAdd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/AddPartForm.fxml"));

        // Load the FXML file directly into a scene
        Scene newScene = new Scene(loader.load(), 599, 402);

        // Get the current stage from the action source (button in this case)
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        primaryStage.setScene(newScene);
    }

    public void handleProductAdd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/AddProductForm.fxml"));

        // Load the FXML file directly into a scene
        Scene newScene = new Scene(loader.load(), 1032, 711);

        // Get the current stage from the action source (button in this case)
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        primaryStage.setScene(newScene);
    }

    public void handlePartModify(ActionEvent event) throws IOException {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        int selectedIndex = partsTable.getSelectionModel().getSelectedIndex();

        if (selectedPart != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/ModifyPartForm.fxml"));

            // Load the FXML file directly into a scene
            Scene newScene = new Scene(loader.load(), 599, 402);
            ModifyPartController controller = loader.getController();
            controller.initData(selectedPart, selectedIndex);

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
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        }
    }

    public void handleProductModify(ActionEvent event) throws IOException {

            // Open modify form and pass the selected part

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/ModifyProductForm.fxml"));
            // Load the FXML file directly into a scene
            Scene newScene = new Scene(loader.load(), 1032, 711);


            // Get the current stage from the action source (button in this case)
            Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            primaryStage.setScene(newScene);
    }

    public void handlePartDelete(ActionEvent event) {
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

    public void handlePartsSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (!partsSearch.getText().isEmpty()){
                Part part = inventory.lookupPart(partsSearch.getText());
                ObservableList<Part> parts = FXCollections.observableArrayList();
                parts.add(part);

                if (part != null) {
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

    public void handleProductDelete(ActionEvent event) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this product?");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                inventory.deleteProduct(selectedProduct);
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
}
