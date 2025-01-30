module ims.c482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ims.c482 to javafx.fxml;
    exports ims.c482;
    exports ims.c482.controllers;
    opens ims.c482.controllers to javafx.fxml;
}