module com.example.todolist {
    requires javafx.controls;
//    requires javafx.swing;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.todolist to javafx.fxml;
    exports com.example.todolist;
}