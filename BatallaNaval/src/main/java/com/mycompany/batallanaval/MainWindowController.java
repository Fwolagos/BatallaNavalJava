/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.batallanaval;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Estudiante
 */
public class MainWindowController implements Initializable {

	@FXML
	private Button btnGo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
	@FXML
    private void go(ActionEvent event) throws IOException {
	     FXMLLoader loader = new FXMLLoader(getClass().getResource("loaderWindow.fxml"));
			Parent root = loader.load(); // Carga el FXML y crea los nodos
			// GameWindowController controller = loader.getController(); // Obtiene la instancia del controlador

			// Ahora podés pasarle datos al controlador
					Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();

			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.close();
    }
	@FXML
    private void goAboutIt(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("creditsWindow.fxml"));
			Parent root = loader.load(); // Carga el FXML y crea los nodos
			// GameWindowController controller = loader.getController(); // Obtiene la instancia del controlador

			// Ahora podés pasarle datos al controlador
					Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();

			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.close(); 
    }
	@FXML
    private void endGame(){
        Platform.exit();
    }
    
}
