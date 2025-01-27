package ims.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Parts_View_Controller {

    @FXML
    public void initialize() {
        // Initialization code if needed
    }


    public void handleOnCancelBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("IMS_Main.fxml"));

        // Load the FXML file directly into a scene
        Scene newScene = new Scene(loader.load(), 1094, 481);

        // Get the current stage from the action source (button in this case)
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        primaryStage.setScene(newScene);
    }
}
