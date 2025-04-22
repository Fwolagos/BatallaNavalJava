/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.batallanaval;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Estudiante
 */
public class WinerWindowController implements Initializable {

	@FXML
	private Button btnBack;
	@FXML
	private Button btnRematch;
	@FXML
	private Label lbName;
	@FXML
	private Label lbPoints;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	// @FXML
	// private void backToMainWindow() throws IOException {
	// 	// App.setRoot("mainWindow");
	// 	FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
	// 	Parent root = loader.load();

	// 	Stage stage = new Stage();
	// 	stage.setScene(new Scene(root));
	// 	stage.show();

	// 	((Stage) someNode.getScene().getWindow()).close();
	// }

	// @FXML
	// private void rematch() throws IOException {
	// 	// App.setRoot("loaderWindow");
	// 	FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
	// 	Parent root = loader.load();

	// 	Stage stage = new Stage();
	// 	stage.setScene(new Scene(root));
	// 	stage.show();

	// 	((Stage) someNode.getScene().getWindow()).close();
	// }
	
	@FXML
	private void backToMainWindow(ActionEvent event) throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
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
	private void rematch(ActionEvent event) throws IOException {
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

	public void loadInformation(ArrayList<String> info) {
		lbName.setText(info.removeFirst());
		lbPoints.setText(info.removeFirst());
	}
}
