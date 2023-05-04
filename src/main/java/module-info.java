module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;

    opens org.example to javafx.fxml;
    opens org.example.Connections to java.xml.bind;
    exports org.example;
}
