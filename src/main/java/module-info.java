module com.example.librarymanagementsystem2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.librarymanagementsystem2 to javafx.fxml;
    exports com.example.librarymanagementsystem2;
}