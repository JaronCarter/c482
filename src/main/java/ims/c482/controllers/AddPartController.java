package ims.c482.controllers;

import ims.c482.models.InHouse;
import ims.c482.models.Inventory;
import ims.c482.models.Outsourced;
import ims.c482.models.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPartController {
    @FXML
    public Label dynamicLabel;
    @FXML
    public RadioButton inHouseRadio;
    @FXML
    public RadioButton outsourcedRadio;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private TextField dynamicField;

    private final Inventory invInstance = Inventory.getInstance();

    @FXML
    public void initialize() {
        // Initialization code if needed
    }

    public void handleOnCancelBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/MainForm.fxml"));

        // Load the FXML file directly into a scene
        Scene newScene = new Scene(loader.load(), 1094, 481);

        // Get the current stage from the action source (button in this case)
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        primaryStage.setScene(newScene);
    }

    public void handleSave(ActionEvent event) throws IOException {

        if (inHouseRadio.isSelected()) {
            Part newPart = new InHouse(invInstance.getCounter(), nameField.getText(), Double.parseDouble(priceField.getText()), Integer.parseInt(invField.getText()), Integer.parseInt(maxField.getText()), Integer.parseInt(minField.getText()), Integer.parseInt(dynamicField.getText()));
            invInstance.addPart(newPart);
        } else {
            Part newPart = new Outsourced(invInstance.getCounter(), nameField.getText(), Double.parseDouble(priceField.getText()), Integer.parseInt(invField.getText()), Integer.parseInt(maxField.getText()), Integer.parseInt(minField.getText()), (dynamicField.getText()));
            invInstance.addPart(newPart);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/MainForm.fxml"));

        // Load the FXML file directly into a scene
        Scene newScene = new Scene(loader.load(), 1094, 481);

        // Get the current stage from the action source (button in this case)
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        primaryStage.setScene(newScene);

    }

    public void handleOutsourcedRadio() {
        if (inHouseRadio.isSelected()) {
            dynamicLabel.setText("Machine ID");
        } else {
            dynamicLabel.setText("Company Name");
        }
    }

    public void handleInHouseRadio() {
        if (inHouseRadio.isSelected()) {
            dynamicLabel.setText("Machine ID");
        } else {
            dynamicLabel.setText("Company Name");
        }
    }
}
