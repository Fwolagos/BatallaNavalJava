/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.batallanaval;

import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;

/**
 *
 * @author ANTONY JOSUE
 */
public class Tools {
    public static final String BASE_PATH_IMAGE = "others/img/";
    public static final String[] DIFFICULTY = { "Easy", "Normal", "Hard" };
    public static final int[] NUMBER_OF_BULLETS = { 100, 20 };
    public static final String[] TYPE_OF_SHOTS = { "shottosea", "Shottoboat" };
    public static final String[] TYPE_PLAYERS = { "Normal", "Teacher" };

    public static URL getPath(String path) {
        return Tools.class.getClassLoader().getResource(path);
    }

    public static void markShot(ImageView img, String typeOfShot) {
        if (typeOfShot == TYPE_OF_SHOTS[1]) {
            Image photo = new Image(getPath(BASE_PATH_IMAGE + "headshot.jpg").toString());
            img.setImage(photo);
        } else {
            Image photo = new Image(getPath(BASE_PATH_IMAGE + "shot.jpg").toString());
            img.setImage(photo);
        }
    }

    public static void showBoat(GridPane gpEnemy, Boat ship) {
        for (Health i : ship.getHealths()) {
            gpEnemy.add(i, i.getCoordenates().getY(), i.getCoordenates().getX());
        }

    }

    public static Node getNodeFromGridPane(GridPane gpEnemy, Position coordenates) {
        for (Node node : gpEnemy.getChildren()) {
            Integer columnIndex = gpEnemy.getColumnIndex(node);
            Integer rowIndex = gpEnemy.getRowIndex(node);

            // JavaFX trata índices no definidos como 0
            if (columnIndex == null)
                columnIndex = 0;
            if (rowIndex == null)
                rowIndex = 0;

            if (columnIndex == coordenates.getY() && rowIndex == coordenates.getX()) {
                return node;
            }
        }
        return null; // No se encontró ningún nodo en esa posición
    }
}
