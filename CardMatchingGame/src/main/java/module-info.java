module com.example.cardmatchinggame2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.cardmatchinggame2 to javafx.fxml;
    exports com.example.cardmatchinggame2;
}