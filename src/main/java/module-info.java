module com.project11.dragonfly_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;


    opens com.project11.dragonfly_1 to javafx.fxml;
    exports com.project11.dragonfly_1;
}