module com.ca2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.ca2 to javafx.fxml;
    exports com.ca2;
    exports com.ca2.ADT;
    opens com.ca2.ADT to javafx.fxml;
    exports com.ca2.Controllers;
    opens com.ca2.Controllers to javafx.fxml;
}