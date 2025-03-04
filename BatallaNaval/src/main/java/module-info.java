module com.mycompany.batallanaval {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.batallanaval to javafx.fxml;
    exports com.mycompany.batallanaval;
}
