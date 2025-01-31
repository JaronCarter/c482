package ims.c482.controllers;

import ims.c482.models.InHouse;
import ims.c482.models.Inventory;
import ims.c482.models.Outsourced;
import ims.c482.models.Part;
import ims.c482.utils.ConvertPart;
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
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outsourcedRadio;

    private int selectedIndex;
    private Part selectedPart;

    /**
     * Any time I attempted to update a part, I ran into this error due to trying to follow the video guidelines on having the part IDs start at 1:
     * java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1
     * This error is caused by the index of the Inventory list and the IDs being offset by that 1 step. To keep from modifying the Inventory class restraints given by the UML diagram, I
     * added a passing of the index by grabbing it from the table itself and handing it off to the modify controller allowing for updating parts by correct index.
     */

    public void initData(Part part, int index) {
        selectedIndex = index;
        selectedPart = part;

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
        Inventory invInstance = Inventory.getInstance();

        System.out.println(selectedPart.getId());

        if (inHouseRadio.isSelected()){
            if (selectedPart instanceof Outsourced){
                selectedPart = ConvertPart.update(selectedPart, dynamicField.getText());
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
                selectedPart = ConvertPart.update(selectedPart, dynamicField.getText());
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
}
