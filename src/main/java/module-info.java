module com.dragonfly.dragonfly {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dragonfly.main to javafx.fxml;
    exports com.dragonfly.main;
}