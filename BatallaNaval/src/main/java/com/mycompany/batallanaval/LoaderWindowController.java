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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ANTONY JOSUE
 */
public class LoaderWindowController implements Initializable {

	public static final String[] TYPES = { "submarines", "destroyers", "cruisers", "battleship" };

	@FXML
	private Spinner<Integer> spnRow;
	@FXML
	private Spinner<Integer> spnColumn;
	@FXML
	private Button btnBack;

	@FXML
	private Button btnRandom;
	@FXML
	private VBox vbBoats;
	@FXML
	private Button btnSet;
	ArrayList<Boat> boats = new ArrayList<>();

	public static Player player;
	public Boat selectedBoat;
	private Map<Health, Boat> shipDictionary = new HashMap<>();
	private DropShadow itemShadow;
	private static final Image IMAGE_WATER = new Image(Tools.getPath(Boat.BASEPATH + "water.jpg").toString());
	@FXML
	private GridPane gpOcean;
	@FXML
	private Button btnRotate;
	@FXML
	private Button btnDelete;
	@FXML
	private TextField tbName;
	@FXML
	private Spinner<String> spnDifficulty;
	@FXML
	private Spinner<String> spnKindOfPlayer;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		loadSettings();
		registerShips();
		loadBoats();

	}

	@FXML
	private void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
		Parent root = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		currentStage.close();
	}

	private void registerShips() {
		for (Boat i : player.getBoats()) {
			handleBoatClick(i);
			for (Health j : i.getHealths()) {
				shipDictionary.put(j, i);
				j.setOnMouseClicked(event -> {
					handleHealthClick(j);
				});
			}
		}

	}

	private void handleHealthClick(Health h) {
		Boat boat = shipDictionary.get(h);

		if (boat == null)
			return;

		if (boat == selectedBoat) {
			// Deseleccionar el barco
			for (Health i : boat.getHealths()) {
				i.setEffect(null);
			}
			selectedBoat = null;
		} else {
			// Quitar la selección anterior
			if (selectedBoat != null) {
				for (Health i : selectedBoat.getHealths()) {
					i.setEffect(null);
				}
			}

			// Seleccionar el nuevo barco
			selectedBoat = boat;
			for (Health i : boat.getHealths()) {
				i.setEffect(itemShadow); // Resaltamos el barco seleccionado
			}

		}
	}

	private void handleBoatClick(Boat pBoat) {
		pBoat.setOnMouseClicked(event -> {
			if (selectedBoat == pBoat) {
				// Si el mismo bote ya estaba seleccionado, lo deseleccionamos
				pBoat.setEffect(null);
				selectedBoat = null;
			} else {
				// Si hay otro seleccionado, quitarle el efecto
				if (selectedBoat != null) {
					selectedBoat.setEffect(null);
				}

				// Seleccionar el nuevo
				selectedBoat = pBoat;
				pBoat.setEffect(itemShadow); // resaltado visual
			}
		});
	}

	private void loadSettings() {
		// este bloque de codigo lo que hace era llenar el grid panel con agua pero no
		// luce bien entonces de momento no la usare!
		// for(int i = 0; i < Player.SIZE_MATRIX;i++){
		// for(int j = 0; j < Player.SIZE_MATRIX;j++){
		// ImageView img = new ImageView(IMAGE_WATER);
		// gpOcean.add(img,i,j);
		// }
		// }
		spnRow.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9, 0));
		spnColumn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9, 0));
		// setting of listViews
		ObservableList<String> kindOfPlayer = FXCollections.observableArrayList(Tools.TYPE_PLAYERS);
		ObservableList<String> difficult = FXCollections.observableArrayList(Tools.DIFFICULTY);
		spnKindOfPlayer.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(kindOfPlayer));
		spnDifficulty.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(difficult));
		this.itemShadow = new DropShadow();
		itemShadow.setColor(Color.BLUE);
		itemShadow.setRadius(10);
		this.player = new Player();

	}

	private void loadBoats() {
		// el codigo siguiente sera puesto en otra funci'on para que cargue los barcos
		for (Boat i : player.getBoats()) {
			vbBoats.getChildren().add(i);
		}

	}

	@FXML
	private void putRandomShips() {
		// gpOcean.getChildren().clear();
		player.clearBoatMatrix();
		vbBoats.getChildren().clear();
		gpOcean.getChildren().removeIf(node -> node instanceof ImageView || node instanceof Health);

		player.setRandomBoats();
		for (Boat i : player.getBoats()) {
			for (Health j : i.getHealths()) {
				gpOcean.add(j, j.getCoordenates().getY(), j.getCoordenates().getX());
			}

		}
	}

	@FXML
	private void deleteBoatOfGridPane() {
		Parent father = selectedBoat.getHealths()[0].getParent();
		if (father instanceof GridPane) {
			for (Health i : selectedBoat.getHealths()) {
				player.getLocationBoats()[i.getCoordenates().getX()][i.getCoordenates().getY()] = false;
				gpOcean.getChildren().remove(i);
			}
			vbBoats.getChildren().add(selectedBoat);
		} else {
			return;
		}
	}

	@FXML
	private void setBoatOfGridPane() {
		Parent father = selectedBoat.getParent();
		if (father instanceof VBox) {
			Position coordenates = new Position(spnRow.getValue(), spnColumn.getValue());
			int size = selectedBoat.getHealths().length;
			for (String i : player.ORIENTATION) {
				if (player.canBePlaced(coordenates, size, i)) {
					player.putBoat(selectedBoat, coordenates, i);
					break;
				}
			}
			for (Health i : selectedBoat.getHealths()) {
				gpOcean.add(i, i.getCoordenates().getY(), i.getCoordenates().getX());
			}
			vbBoats.getChildren().remove(selectedBoat);
			spnRow.getValueFactory().setValue(0);
			spnColumn.getValueFactory().setValue(0);

		}

	}

	@FXML
	private void rotateBoatOfGridPane() {
		Parent father = selectedBoat.getHealths()[0].getParent();
		if (father instanceof GridPane) {
			boolean flag = true;
			String orientation;
			Position coordenates = selectedBoat.getHealths()[0].getCoordenates();
			int size = selectedBoat.getHealths().length;
			Boat copyBoat = selectedBoat;
			// int counter = 0;
			deleteBoatOfGridPane();
			do {
				orientation = player.orientationRandom();
				if (player.canBePlaced(coordenates, size, orientation)) {
					flag = false;
					player.putBoat(copyBoat, coordenates, orientation);
				}
				// if(selectedBoat.getHealths()[1].getCoordenates() ==
				// copyBoat.getHealths()[1].getCoordenates()){
				// flag = true;
				// }
				// if(counter > 100){
				// return;
				// }
				// counter++;
			} while (flag);
			player.putBoat(selectedBoat, coordenates, orientation);
			vbBoats.getChildren().remove(selectedBoat);
			for (Health i : selectedBoat.getHealths()) {
				gpOcean.add(i, i.getCoordenates().getY(), i.getCoordenates().getX());
			}

		}
	}

	@FXML
	private void goToGameWindow(ActionEvent event) throws IOException {
		if (vbBoats.getChildren().isEmpty()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
			Parent root = loader.load(); // Carga el FXML y crea los nodos
			GameWindowController controller = loader.getController(); // Obtiene la instancia del controlador

			// Ahora podés pasarle datos al controlador
			// Por ejemplo: controller.setPlayer(player);
			NonPlayerCharacter pc = new NonPlayerCharacter();
			player.setName(tbName.getText());
			pc.setName("AI");
			pc.setDifficulty(spnDifficulty.getValue());
			pc.setRandomBoats();
			if (pc.getDifficulty() == Tools.DIFFICULTY[2]) {
				pc.setBullets(Tools.NUMBER_OF_BULLETS[1]);
				player.setBullets(Tools.NUMBER_OF_BULLETS[1]);

			} else {
				pc.setBullets(Tools.NUMBER_OF_BULLETS[0]);
				player.setBullets(Tools.NUMBER_OF_BULLETS[0]);

			}
			controller.bringData(player, pc, spnKindOfPlayer.getValue());
			controller.loadData();

			// Y luego mostrás la escena
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();

			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.close();
		}
	}

}
