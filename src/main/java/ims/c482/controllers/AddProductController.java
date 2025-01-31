package ims.c482.controllers;

import ims.c482.models.Inventory;
import ims.c482.models.Part;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductController {
    public TableView<Part> allPartsTable;
    public TableColumn<Part, Integer> columnPartID;
    public TableColumn<Part, String> columnPartName;
    public TableColumn<Part, Integer> columnPartInv;
    public TableColumn<Part, Double> columnPartPrice;
    public TableView<Part> associatedPartsTable;
    public TableColumn<Part, Integer> columnAssociatedID;
    public TableColumn<Part, String> columnAssociatedName;
    public TableColumn<Part, Integer> columnAssociatedInv;
    public TableColumn<Part, Double> columnAssociatedPrice;
    private Inventory inventory = Inventory.getInstance();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

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

    public void handleAdd(ActionEvent event) {
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

    public void handleRemove(ActionEvent event) {
        if (associatedParts != null && !associatedParts.isEmpty()) {
            Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();

            if (selectedPart != null) {
                associatedParts.remove(selectedPart);
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
}
