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
    public Label errorLabel;
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
        idField.setText(Integer.toString(invInstance.getCount()));
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
        StringBuilder errors = new StringBuilder();

        if (nameField.getText().isEmpty()) {
            errors.append("Please enter a valid name\n");
        }
        if (invField.getText().isEmpty() || !isInteger(invField.getText())) {
            errors.append("Please enter a valid inventory level\n");
        }
        if (priceField.getText().isEmpty() || !isDouble(priceField.getText())) {
            errors.append("Please enter a valid price\n");
        }
        try {
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());
            if(max<min){
                errors.append("The maximum inventory limit must be greater than the minimum inventory limit\n");
            }
        } catch (NumberFormatException e) {
            if (!isInteger(maxField.getText())) {
                errors.append("Please enter a valid maximum inventory limit\n");
            }
            if (!isInteger(minField.getText())) {
                errors.append("Please enter a valid minimum inventory limit\n");
            }
        }
        if (dynamicField.getText().isEmpty()) {
            errors.append(String.format("Please enter a valid %s\n", dynamicLabel.getText()));
        }
        else if (inHouseRadio.isSelected() && !isInteger(dynamicField.getText())) {
            errors.append("Machine ID must be an integer\n");
        }
        if (errors.isEmpty()) {
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
        else {
            errorLabel.setText(errors.toString());
        }

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

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;  // Valid integer
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
