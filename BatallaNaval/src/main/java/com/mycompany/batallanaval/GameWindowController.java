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
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ANTONY JOSUE
 */
public class GameWindowController implements Initializable {
	protected Game gameOfBattleship;
	public static String kindOfPlayer;
	@FXML
	private Button bntShoot;
	@FXML
	private Button btnGiveUp;
	@FXML
	private GridPane gpPlayer;
	@FXML
	private GridPane gpEnemy;

	private ImageView selectedSquare;

	private DropShadow itemShadow;

	public void bringData(Player player, NonPlayerCharacter pc, String kindOfPlayer) {
		this.gameOfBattleship = new Game(player, pc);
		this.kindOfPlayer = kindOfPlayer;

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.itemShadow = new DropShadow();
		itemShadow.setColor(Color.BLUE);
		itemShadow.setRadius(10);

		for (int i = 0; i < Player.SIZE_MATRIX; i++) {
			for (int j = 0; j < Player.SIZE_MATRIX; j++) {
				ImageView square = new ImageView(Tools.getPath(Tools.BASE_PATH_IMAGE + "water.jpg").toString());
				square.setFitHeight(27);
				square.setFitWidth(27);
				square.setOpacity(0.3);
				handleSquareClick(square);
				gpEnemy.add(square, j, i);
				gpEnemy.setHalignment(square, HPos.CENTER);
				gpEnemy.setValignment(square, VPos.CENTER);
			}
		}

	}

	public void loadData() {
		Player player = gameOfBattleship.getPlayer();
		NonPlayerCharacter pc = gameOfBattleship.getPc();
		if (kindOfPlayer == Tools.TYPE_PLAYERS[1]) {
			for (Boat i : pc.getBoats()) {
				for (Health j : i.getHealths()) {
					gpEnemy.add(j, j.getCoordenates().getY(), j.getCoordenates().getX());
					gpEnemy.setHalignment(i, HPos.CENTER);
					gpEnemy.setValignment(i, VPos.CENTER);
					handleSquareClick(j);
				}
			}
		}
		for (Boat i : player.getBoats()) {
			for (Health j : i.getHealths()) {
				gpPlayer.add(j, j.getCoordenates().getY(), j.getCoordenates().getX());
			}
		}
	}

	private void handleSquareClick(ImageView square) {
		square.setOnMouseClicked(event -> {
			if (selectedSquare == square) {
				square.setEffect(null);
				selectedSquare = null;
			} else {
				if (selectedSquare != null) {
					selectedSquare.setEffect(null);
				}

				selectedSquare = square;
				square.setEffect(itemShadow);
			}
		});
	}

	@FXML
	private void play() throws IOException {
		selectedSquare.setEffect(null);
		Parent father = selectedSquare.getParent();
		// boolean consult = (
		// gameOfBattleship.getPlayer().canPlay()&&gameOfBattleship.getPc().canPlay());
		if (father == gpEnemy) {
			int x, y;
			x = gpEnemy.getRowIndex(selectedSquare);
			y = gpEnemy.getColumnIndex(selectedSquare);
			Position coordenates = new Position(x, y);
			if (gameOfBattleship.gameTurn(coordenates, selectedSquare, gpEnemy))
				return;
			else {
				goToWinnerWindow(bntShoot);
			}

		}

	}

	@FXML
	private void giveUp()throws IOException {
		goToWinnerWindow(bntShoot);
	}

	private void goToWinnerWindow(Node sourceNode) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("winerWindow.fxml"));
		Parent root = loader.load();
		WinerWindowController controller = loader.getController();
		ArrayList<String> info = gameOfBattleship.whoIsWinner();
		controller.loadInformation(info);

		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();

		if (sourceNode != null) {
			Stage currentStage = (Stage) sourceNode.getScene().getWindow();
			currentStage.close();
		}
	}

}
