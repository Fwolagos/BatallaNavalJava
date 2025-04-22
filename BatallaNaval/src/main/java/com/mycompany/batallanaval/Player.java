/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.batallanaval;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ANTONY JOSUE
 */
public class Player {
    protected static final int SIZE_MATRIX = 10;
    protected static final int NUMBER_SUBMARINES = 4;
    protected static final int NUMBER_DESTROYERS = 3;
    protected static final int NUMBER_CRUISERS = 2;
    protected static final int NUMBER_BATTLESHIP = 1;
    protected static final String[] ORIENTATION = { "right", "left", "up", "down" };
    protected ArrayList<Boat> boats;
    protected boolean locationBoats[][];
    protected boolean locationShoots[][];
    protected String name;
    protected int bullets;

    public Player(String pName, int pBullets) {
        this.locationBoats = new boolean[SIZE_MATRIX][SIZE_MATRIX];
        this.locationShoots = new boolean[SIZE_MATRIX][SIZE_MATRIX];
        this.name = pName;
        this.bullets = pBullets;
        this.boats = new ArrayList<>();
        for (int i = 0; i < NUMBER_SUBMARINES; i++) {
            boats.add(new Boat(Boat.getTYPES()[0]));
        }
        for (int i = 0; i < NUMBER_DESTROYERS; i++) {
            boats.add(new Boat(Boat.getTYPES()[1]));
        }
        for (int i = 0; i < NUMBER_CRUISERS; i++) {
            boats.add(new Boat(Boat.getTYPES()[2]));
        }
        for (int i = 0; i < NUMBER_BATTLESHIP; i++) {
            boats.add(new Boat(Boat.getTYPES()[3]));
        }
    }

    public Player() {
        this.locationBoats = new boolean[SIZE_MATRIX][SIZE_MATRIX];
        this.locationShoots = new boolean[SIZE_MATRIX][SIZE_MATRIX];
        this.name = null;
        this.bullets = 0;
        this.boats = new ArrayList<>();
        for (int i = 0; i < NUMBER_SUBMARINES; i++) {
            boats.add(new Boat(Boat.getTYPES()[0]));
        }
        for (int i = 0; i < NUMBER_DESTROYERS; i++) {
            boats.add(new Boat(Boat.getTYPES()[1]));
        }
        for (int i = 0; i < NUMBER_CRUISERS; i++) {
            boats.add(new Boat(Boat.getTYPES()[2]));
        }
        for (int i = 0; i < NUMBER_BATTLESHIP; i++) {
            boats.add(new Boat(Boat.getTYPES()[3]));
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }

    public String getName() {
        return name;
    }

    public int getBullets() {
        return bullets;
    }

    public static int getSIZE_MATRIX() {
        return SIZE_MATRIX;
    }

    public ArrayList<Boat> getBoats() {
        return boats;
    }

    public boolean[][] getLocationBoats() {
        return locationBoats;
    }

    public boolean[][] getLocationShoots() {
        return locationShoots;
    }

    public void setBoats(ArrayList<Boat> boats) {
        this.boats = boats;
    }

    public void setLocationBoats(boolean[][] locationBoats) {
        this.locationBoats = locationBoats;
    }

    public void setLocationShoots(boolean[][] locationShoots) {
        this.locationShoots = locationShoots;
    }

    public boolean thereAreBullets() {
        if (bullets > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean canPlay() {
        int counter = 0;
        for (Boat i : boats) {
            if (i.getState() == true) {
                counter++;
            }
        }
        if (counter > 0 && thereAreBullets()) {
            return true;
        } else {
            return false;
        }
    }

    public ShootingInformation handleShootEnemy(Position coordenates) {
        Boat ship = searchBoat(coordenates);
        ShootingInformation information = new ShootingInformation();
        if (ship != null) {
            receilvedAttack(ship, coordenates);
            information.setDamaged(true);
            if (ship.getState() == false) {
                information.setDestroyed(true);
            }

        }

        information.setShip(ship);
        return information;
    }

    public void setRandomBoats() {
        boolean flag = true;
        int sizeBoat = 0;
        for (Boat i : boats) {
            flag = true;
            sizeBoat = i.getHealths().length;
            Position coordenates;
            String orientation;
            do {
                coordenates = positionRandom();
                orientation = orientationRandom();
                // orientation = ORIENTATION[3];
                flag = !canBePlaced(coordenates, sizeBoat, orientation);

            } while (flag);
            putBoat(i, coordenates, orientation);

        }

    }

    public void putBoat(Boat boat, Position coordenates, String orientation) {
        int sizeBoat = boat.getHealths().length;
        if (orientation == ORIENTATION[0]) {// derecha
            for (int i = 0; i < sizeBoat; i++) {
                locationBoats[coordenates.getX()][coordenates.getY() + i] = true;
                boat.getHealths()[i].getCoordenates().setX(coordenates.getX());
                boat.getHealths()[i].getCoordenates().setY(coordenates.getY() + i);
                boat.getHealths()[i].restartOrientation();
            }

        } else if (orientation == ORIENTATION[1]) {// izquierda
            for (int i = 0; i < sizeBoat; i++) {
                locationBoats[coordenates.getX()][coordenates.getY() - i] = true;
                boat.getHealths()[i].getCoordenates().setX(coordenates.getX());
                boat.getHealths()[i].getCoordenates().setY(coordenates.getY() - i);
                boat.getHealths()[i].restartOrientation();
                boat.getHealths()[i].setScaleX(-1);
            }

        } else if (orientation == ORIENTATION[2]) {// arriba
            for (int i = 0; i < sizeBoat; i++) {
                locationBoats[coordenates.getX() - i][coordenates.getY()] = true;
                boat.getHealths()[i].getCoordenates().setX(coordenates.getX() - i);
                boat.getHealths()[i].getCoordenates().setY(coordenates.getY());
                boat.getHealths()[i].restartOrientation();
                boat.getHealths()[i].setRotate(-90);
            }

        } else if (orientation == ORIENTATION[3]) {// abajo
            for (int i = 0; i < sizeBoat; i++) {
                locationBoats[coordenates.getX() + i][coordenates.getY()] = true;
                boat.getHealths()[i].getCoordenates().setX(coordenates.getX() + i);
                boat.getHealths()[i].getCoordenates().setY(coordenates.getY());
                boat.getHealths()[i].restartOrientation();
                boat.getHealths()[i].setRotate(90);
            }
        }
    }

    protected Position positionRandom() {
        Random rnd = new Random();
        int x = rnd.nextInt(Player.SIZE_MATRIX);
        int y = rnd.nextInt(Player.SIZE_MATRIX);
        Position coordenates = new Position(x, y);
        return coordenates;
    }

    protected String orientationRandom() {
        Random rnd = new Random();

        return ORIENTATION[rnd.nextInt(ORIENTATION.length)];
    }

    public boolean canBePlaced(Position coordenates, int sizeBoat, String orientation) {
        // sizeBoat -=1;
        if (orientation == ORIENTATION[0]) {// derecha
            if (!(coordenates.getY() + sizeBoat < SIZE_MATRIX)) {
                return false;
            }
            for (int i = coordenates.getY(); i < sizeBoat + coordenates.getY(); i++) {
                if (locationBoats[coordenates.getX()][i] == true) {
                    return false;
                }

            }
            return true;

        } else if (orientation == ORIENTATION[1]) {// izquierda
            if (!(coordenates.getY() - sizeBoat > -1)) {
                return false;
            }
            for (int i = coordenates.getY(); i > coordenates.getY() - sizeBoat; i--) {
                if (locationBoats[coordenates.getX()][i] == true) {
                    return false;
                }

            }
            return true;

        } else if (orientation == ORIENTATION[2]) {// arriba
            if (!(coordenates.getX() - sizeBoat > -1)) {
                return false;
            }
            for (int i = coordenates.getX(); i > coordenates.getX() - sizeBoat; i--) {
                if (locationBoats[i][coordenates.getY()] == true) {
                    return false;
                }
            }
            return true;

        } else if (orientation == ORIENTATION[3]) {// abajo
            if (!(coordenates.getX() + sizeBoat < SIZE_MATRIX)) {
                return false;
            }
            for (int i = coordenates.getX(); i < coordenates.getX() + sizeBoat; i++) {
                if (locationBoats[i][coordenates.getY()] == true) {
                    return false;
                }
            }
            return true;

        } else {
            return false;
        }

    }

    public void clearBoatMatrix() {
        locationBoats = new boolean[SIZE_MATRIX][SIZE_MATRIX];
    }

    public Boat searchBoat(Position coordenates) {
        for (Boat i : boats) {
            for (Health j : i.getHealths()) {
                if (j.getCoordenates().equals(coordenates)) {
                    return i;
                }
            }
        }
        return null;

    }

    public void receilvedAttack(Boat boat, Position coordenates) {
        for (Health i : boat.getHealths()) {
            if (i.getCoordenates().equals(coordenates)) {
                i.setState(false);
                boat.updateState();
            }
        }
    }

    public void makeShot(Position coordenates) {
        locationShoots[coordenates.getX()][coordenates.getY()] = true;
        bullets -= 1;
    }

    public void resetShotsOnBoat(Boat ship) {
        for (Health i : ship.getHealths()) {
            locationShoots[i.getCoordenates().getX()][i.getCoordenates().getY()] = false;

        }
    }

}
