package ims.c482.controllers;

import ims.c482.models.InHouse;
import ims.c482.models.Inventory;
import ims.c482.models.Outsourced;
import ims.c482.models.Part;
import ims.c482.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartController {

    @FXML
    public Label dynamicLabel;
    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField dynamicField;
    public TextField minField;
    public Label errorLabel;
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outsourcedRadio;

    private int selectedIndex;
    private Part selectedPart;
    private Inventory invInstance;
    /**
     * Any time I attempted to update a part, I ran into this error due to trying to follow the video guidelines on having the part IDs start at 1:
     * java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1
     * This error is caused by the index of the Inventory list and the IDs being offset by that 1 step. To keep from modifying the Inventory class restraints given by the UML diagram, I
     * added a passing of the index by grabbing it from the table itself and handing it off to the modify controller allowing for updating parts by correct index.
     */

    public void initData(Part part) {
        selectedPart = part;
        selectedIndex = invInstance.getAllParts().indexOf(selectedPart);

        if (part instanceof InHouse inHousePart){
            inHouseRadio.setSelected(true);
            dynamicLabel.setText("Machine ID");
            dynamicField.setText((Integer.toString(inHousePart.getMachineId())));
        }
        else {
            Outsourced outsourcedPart = (Outsourced) part;
            outsourcedRadio.setSelected(true);
            dynamicLabel.setText("Company Name");
            dynamicField.setText(outsourcedPart.getCompanyName());
        }

        idField.setText(Integer.toString(part.getId()));
        nameField.setText(part.getName());
        invField.setText(Integer.toString(part.getStock()));
        priceField.setText(Double.toString(part.getPrice()));
        maxField.setText(Integer.toString(part.getMax()));
        minField.setText(Integer.toString(part.getMin()));
    }

    @FXML
    public void initialize() {
        invInstance = Inventory.getInstance();
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

    public void handleInHouseRadio(ActionEvent event) {
        if (inHouseRadio.isSelected()) {
            dynamicLabel.setText("Machine ID");
        }
    }

    public void handleOutsourcedRadio(ActionEvent event) {
        if (outsourcedRadio.isSelected()) {
            dynamicLabel.setText("Company Name");
        }
    }

    /** I had a problem with the below instantiation of the part object where my original lookup function was returning null even when parsed to an int. I realized after this that because I was counting by 1 rather than starting at 0
     * I could not use a standard get method to lookup from a list because it looks at index. I could change my approach and leave index at 0 then change the form by 1 for show, but I
     * decided to keep the logic as is and re-write the lookup method in Inventory to for loop through the list and return the first with a matching ID.
     **/
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
            if(max<min){
                errors.append("The maximum inventory limit must be greater than the minimum inventory limit\n");
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
        }
        else if (inHouseRadio.isSelected() && !Utils.isInteger(dynamicField.getText())) {
            errors.append("Machine ID must be an integer\n");
        }
        if (errors.isEmpty()) {
            if (inHouseRadio.isSelected()){
                if (selectedPart instanceof Outsourced){
                    selectedPart = Utils.convertPart(selectedPart, dynamicField.getText());
                }
                InHouse modifiedPart = (InHouse) selectedPart;
                modifiedPart.setName(nameField.getText());
                modifiedPart.setStock(Integer.parseInt(invField.getText()));
                modifiedPart.setPrice(Double.parseDouble(priceField.getText()));
                modifiedPart.setMax(Integer.parseInt(maxField.getText()));
                modifiedPart.setMin(Integer.parseInt(minField.getText()));
                modifiedPart.setMachineId(Integer.parseInt(dynamicField.getText()));
                invInstance.updatePart(selectedIndex, modifiedPart);
            }
            else {
                if (selectedPart instanceof InHouse){
                    selectedPart = Utils.convertPart(selectedPart, dynamicField.getText());
                }
                Outsourced outsourcedPart = (Outsourced) selectedPart;
                outsourcedPart.setName(nameField.getText());
                outsourcedPart.setStock(Integer.parseInt(invField.getText()));
                outsourcedPart.setPrice(Double.parseDouble(priceField.getText()));
                outsourcedPart.setMax(Integer.parseInt(maxField.getText()));
                outsourcedPart.setMin(Integer.parseInt(minField.getText()));
                outsourcedPart.setCompanyName(dynamicField.getText());
                invInstance.updatePart(selectedIndex, outsourcedPart);
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
}
