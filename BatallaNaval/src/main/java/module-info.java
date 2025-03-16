module com.mycompany.batallanaval {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.base;

    opens com.mycompany.batallanaval to javafx.fxml;
    exports com.mycompany.batallanaval;
}
