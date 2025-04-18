module com.dragonfly.dragonfly {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;


    opens com.dragonfly.main to javafx.fxml;
    exports com.dragonfly.main;
}