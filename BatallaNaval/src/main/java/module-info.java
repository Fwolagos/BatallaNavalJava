module com.mycompany.batallanaval {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.base;
	requires javafx.graphics;

    opens com.mycompany.batallanaval to javafx.fxml;
    exports com.mycompany.batallanaval;
}
