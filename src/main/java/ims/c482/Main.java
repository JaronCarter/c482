package ims.c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
// javadocs folder is located at root of zip and root of the github repo.
/**
 * @author Jaron Carter
 *
 * <p>The Main class is the starting point for the application. Initialization of the JavaFX IMS Main application begins here with the loading of the main form.</p>
 *
 * <p><b>FUTURE ENHANCEMENT:</b> Having the products and parts pages either more tightly coupled or at least allowing a removal of a part to trigger a warning label on the products that have those parts associated would be very helpful in making sure a product can continue being built in the future. No manufacturer that I know of likes running out of parts without a backup.</p>
 *
 * <p><b>FUTURE ENHANCEMENT:</b> I followed the video and recommendations for this project's handling of IDs for both products and parts, but a better method for quick lookup in larger projects would be to try and switch to something like hash tables, where an operation's time complexity could be closer to O(1) rather than O(n) or O(n^2).</p>
 *
 * <p><b>FUTURE ENHANCEMENT:</b> Adding auth and security measures, as well as encrypted databases and proper security for data packet transfer, would be a logical next step for anyone with intellectual property concerns.</p>
 *
 * <p><b>FUTURE ENHANCEMENT:</b> Add further utilities (Utils) for making the code less redundant and even cleaner. Perhaps a new class or two with extra overloaded methods would help.</p>
 */
public class Main extends Application {
    /**
     * Creates the main stage and loads the MainForm view into the scene giving the user the main window of the application.
     *
     * @param stage Provided by the JavaFX runtime, this is stage primary for the application.
     * @throws IOException If the MainForm fxml file cannot load for any reason i.e. missing or corrupted files etc.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/ims/c482/views/MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1094, 481);
        stage.setTitle("IMS");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method used for launching the application.
     *
     * @param args Takes any arguments for handling from command line etc.
     */
    public static void main(String[] args) {
        launch();
    }
}