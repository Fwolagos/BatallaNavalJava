/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.batallanaval;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * FXML Controller class
 *
 * @author ANTONY JOSUE
 */
public class LoaderWindowController implements Initializable {

	@FXML
	private Spinner<Integer> spnRow;
	@FXML
	private Spinner<Integer> spnColumn;
	@FXML
	private Button btnBack;
	@FXML
	private ListView<String> lvPlayer;
	@FXML
	private ListView<String> lvDifficult;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//setting of spiners 
		spnRow.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,9,0));
		spnColumn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,9,0));
		//setting of listViews
		ObservableList<String> kindOfPlayer = FXCollections.observableArrayList("Jugador Normal","Jugador Profe");
		ObservableList<String> difficult = FXCollections.observableArrayList("Easy","Nomal","Hard");
		lvPlayer.setItems(kindOfPlayer);		
		lvDifficult.setItems(difficult);
	}	
	
	@FXML
	private void back() throws IOException {
	     App.setRoot("mainWindow");
    }
	
}
