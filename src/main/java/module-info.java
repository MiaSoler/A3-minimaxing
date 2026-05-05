module com.minimaxing {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.minimaxing to javafx.fxml;
    exports com.minimaxing;
}
