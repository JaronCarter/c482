package ims.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class IMS_Main_Controller {

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

    public void handlePartAddBtnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Parts_View.fxml"));

        // Load the FXML file directly into a scene
        Scene newScene = new Scene(loader.load(), 599, 402);

        // Get the current stage from the action source (button in this case)
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        primaryStage.setScene(newScene);
    }
}
