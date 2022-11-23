module com.ca2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ca2 to javafx.fxml;
    exports com.ca2;
    exports DataStructures;
    opens DataStructures to javafx.fxml;
}