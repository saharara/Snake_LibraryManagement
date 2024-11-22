module com.example.librarymanagementsystem2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.google.gson;
    requires okhttp3;
    requires org.apache.httpcomponents.httpcore;
    requires java.sql;
    requires java.net.http;
    requires org.json;

    opens com.example.librarymanagementsystem2 to javafx.fxml;
    exports com.example.librarymanagementsystem2;
}