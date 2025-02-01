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

/**
 * The modify part controller is used in tandem with the modify part view so any user may modify an existing part currently in the inventory part list.
 */
public class ModifyPartController {

    /** The label that will change between Machine ID and Company Name when the InHouseRadio or OutsourcedRadio buttons are selected. **/
    @FXML
    private Label dynamicLabel;
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
    /** Field that changes between Machine ID and Company Name text for adding to its respective part. **/
    @FXML
    private TextField dynamicField;
    /** Minimum Inventory Level Allowed Field **/
    @FXML
    private TextField minField;
    /** Error Label that gets populated with each potential error before saving is allowed. **/
    @FXML
    private Label errorLabel;
    /** In House Radio Button **/
    @FXML
    private RadioButton inHouseRadio;
    /** Outsourced Radio Button **/
    @FXML
    private RadioButton outsourcedRadio;
    /** The holder variable for the index of the part that is selected on main form and passed to this one for modification. **/
    private int selectedIndex;
    /** The holder variable for the part passed through to this form for modification. **/
    private Part selectedPart;
    /** The holder variable for later getting of the Inventory instance for storage of parts and information. **/
    private Inventory invInstance;

    /**
     * The initData method is set to allow passing of Part data from whatever outside controller may call it. Also sets the text fields correctly based on the passed part information.
     * @param part Passed through so it can be modified and potentially saved later.
     * <p><b>RUNTIME ERROR:</b> Any time I attempted to update a part, I ran into this error due to trying to follow the video guidelines on having the part IDs start at 1: "java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1". his error is caused by the index of the Inventory list and the IDs being offset by that 1 step. To keep from modifying the Inventory class restraints given by the UML diagram, I added a IndexOf style method for getting the index of a part from the list currently residing in the Inventory instance.</p>
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

    /**
     * At initialization of the controller, get and set the Inventory instance to a variable for using throughout the controller.
     */
    public void initialize() {
        invInstance = Inventory.getInstance();
    }


    /**
     * Handler for cancel button to return user back to the Main Form.
     * @param event Passed through for getting the stage for setting the Main Form view and scene on.
     * @throws IOException In the case that Main Form's fxml has issues loading etc.
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

    /** When the in house radio button is selected, changed the dynamic label text to Machine ID. **/
    public void handleInHouseRadio() {
        if (inHouseRadio.isSelected()) {
            dynamicLabel.setText("Machine ID");
        }
    }

    /** When the outsourced radio button is selected, changed the dynamic label text to Company Name. **/
    public void handleOutsourcedRadio() {
        if (outsourcedRadio.isSelected()) {
            dynamicLabel.setText("Company Name");
        }
    }

    /**
     * Handler for saving the modified part. If all fields pass conditional checks, the part will be modified taking even into account which radio button is selected.
     * @param event Passed through to aquire the stage from for setting the Main Form view on the scene and stage if the part modification passes all checks and does save.
     * @throws IOException If the Main Form view cannot be loaded for any reason.
     * <p><b>RUNTIME ERROR:</b> I had a problem with the below instantiation of the part object where my original lookup function was returning null even when parsed to an int. I realized after this that because I was counting by 1 rather than starting at 0 I could not use a standard get method to lookup from a list because it looks at index. I could change my approach and leave index at 0 then change the form by 1 for show, but I decided to keep the logic as is and re-write the lookup method in Inventory to for loop through the list and return the first with a matching ID.</p>
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
