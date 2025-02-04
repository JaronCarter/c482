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
import ims.c482.utils.Utils;
import java.io.IOException;

/**
 * The add part controller is used in tandem to the add part form. Allows for adding of parts to the inventory parts list.
 */
public class AddPartController {
    /** The label that will change between Machine ID and Company Name when the InHouseRadio or OutsourcedRadio buttons are selected. **/
    @FXML
    private Label dynamicLabel;
    /** In House Radio Button **/
    @FXML
    private RadioButton inHouseRadio;
    /** Error Label that gets populated with each potential error before saving is allowed. **/
    @FXML
    private Label errorLabel;
    /** Part ID Field **/
    @FXML
    private TextField idField;
    /** Part Name Field **/
    @FXML
    private TextField nameField;
    /** Part Inventory Level Field **/
    @FXML
    private TextField invField;
    /** Part Price Field **/
    @FXML
    private TextField priceField;
    /** Maximum Inventory Level Allowed Field **/
    @FXML
    private TextField maxField;
    /** Minimum Inventory Level Allowed Field **/
    @FXML
    private TextField minField;
    /** Field that changes between Machine ID and Company Name text for adding to its respective part. **/
    @FXML
    private TextField dynamicField;
    /** Gets the Inventory Singleton instance and assigns it to a variable. **/
    private final Inventory invInstance = Inventory.getInstance();

    /** When the controller initializes set the ID Field's text to the current next ID allowed from the counter in Inventory. **/
    public void initialize() {
        idField.setText(Integer.toString(invInstance.getPartsCount()));
    }

    /**
     * The handler for the cancel button on the Add Part Form. Takes you back to Main Form.
     * @param event Grabs the event being passed through the handler so the primary stage can be used to reset the scene back to MainForm.
     * @throws IOException If there is an issue loading MainForm.
     */
    public void handleOnCancelBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/MainForm.fxml"));

        // Load the FXML file directly into a scene
        Scene newScene = new Scene(loader.load(), 1094, 481);

        // Get the current stage from the action source (button in this case)
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        primaryStage.setScene(newScene);
    }

    /**
     * Handler used for saving the new parts to be added to the Inventory's parts list. There is a StringBuilder that each conditional check loads to and then the StringBuilder is checked if empty. If not empty, changes the error label text to all appended errors, otherwise it saves.
     * @param event Passed through so on successful save the controller can reset the stage, pulled from this even, to the MainForm view and scene.
     * @throws IOException If main form cannot load.
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
            if (max < min) {
                errors.append("The maximum inventory limit must be greater than the minimum inventory limit\n");
            } else if (max < inv || inv < min) {
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
        if (dynamicField.getText().isEmpty()) {
            errors.append(String.format("Please enter a valid %s\n", dynamicLabel.getText()));
        } else if (inHouseRadio.isSelected() && !Utils.isInteger(dynamicField.getText())) {
            errors.append("Machine ID must be an integer\n");
        }
        if (errors.isEmpty()) {
            if (inHouseRadio.isSelected()) {
                Part newPart = new InHouse(invInstance.getPartsCount(), nameField.getText(), Double.parseDouble(priceField.getText()), Integer.parseInt(invField.getText()), Integer.parseInt(minField.getText()), Integer.parseInt(maxField.getText()), Integer.parseInt(dynamicField.getText()));
                invInstance.addPart(newPart);
            } else {
                Part newPart = new Outsourced(invInstance.getPartsCount(), nameField.getText(), Double.parseDouble(priceField.getText()), Integer.parseInt(invField.getText()), Integer.parseInt(minField.getText()), Integer.parseInt(maxField.getText()), (dynamicField.getText()));
                invInstance.addPart(newPart);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ims/c482/views/MainForm.fxml"));

            // Load the FXML file directly into a scene
            Scene newScene = new Scene(loader.load(), 1094, 481);

            // Get the current stage from the action source (button in this case)
            Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            primaryStage.setScene(newScene);
        } else {
            errorLabel.setText(errors.toString());
        }

    }

    /**
     * If the outsourced radio button is selected, changes the text of the dynamic label on the form to match.
     */
    public void handleOutsourcedRadio() {
        if (inHouseRadio.isSelected()) {
            dynamicLabel.setText("Machine ID");
        } else {
            dynamicLabel.setText("Company Name");
        }
    }

    /**
     * If the In House radio button is selected, changes the text of the dynamic label on the form to match.
     */
    public void handleInHouseRadio() {
        if (inHouseRadio.isSelected()) {
            dynamicLabel.setText("Machine ID");
        } else {
            dynamicLabel.setText("Company Name");
        }
    }
}
