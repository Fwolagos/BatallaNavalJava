/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.batallanaval;

import java.util.ArrayList;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;

/**
 *
 * @author ANTONY JOSUE
 */
public class Game {
    public static final double HEALTH_POINT_PERCENTAGE = 0.5;
    protected Player player;
    protected NonPlayerCharacter pc;
    protected ArrayList<Boat> deadBoatsPlayer;
    protected ArrayList<Boat> deadBoatsPc;
    protected ArrayList<Position> hits;
    protected int counterSuccessesPlayer;
    protected int counterSuccessesPc;

    public Game() {
        this.player = new Player();
        this.pc = new NonPlayerCharacter();
        this.deadBoatsPc = new ArrayList<>();
        this.deadBoatsPlayer = new ArrayList<>();
        this.hits = new ArrayList<>();
    }

    public Game(Player player, NonPlayerCharacter pc) {
        this.player = player;
        this.pc = pc;
        this.deadBoatsPc = new ArrayList<>();
        this.deadBoatsPlayer = new ArrayList<>();
        this.hits = new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    public NonPlayerCharacter getPc() {
        return pc;
    }

    public ArrayList<Boat> getDeadBoatsPlayer() {
        return deadBoatsPlayer;
    }

    public ArrayList<Boat> getDeadBoatsPc() {
        return deadBoatsPc;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPc(NonPlayerCharacter pc) {
        this.pc = pc;
    }

    public void setDeadBoatsPlayer(ArrayList<Boat> deadBoatsPlayer) {
        this.deadBoatsPlayer = deadBoatsPlayer;
    }

    public void setDeadBoatsPc(ArrayList<Boat> deadBoatsPc) {
        this.deadBoatsPc = deadBoatsPc;
    }

    public void turn(Position coordenates, ImageView square, GridPane gpEnemy) {
        if (player.locationShoots[coordenates.getX()][coordenates.getY()]) {
            return;
        }
        if (!(square instanceof Health)) {
            Tools.markShot(square, Tools.TYPE_OF_SHOTS[0]);
        }
        ShootingInformation info;
        player.makeShot(coordenates);
        info = pc.handleShootEnemy(coordenates);
        if (info.isDestroyed()) {
            deadBoatsPc.add(info.getShip());
            if (!(square instanceof Health)) {

                Tools.showBoat(gpEnemy, info.getShip());
            }
        }
        if (info.isDamaged()) {
            if (!(square instanceof Health)) {
                Tools.markShot(square, Tools.TYPE_OF_SHOTS[1]);
            }
            hits.add(coordenates);
            counterSuccessesPlayer++;
            if (counterSuccessesPlayer == 2) {
                counterSuccessesPlayer = 0;
                if (pc.getDifficulty() == pc.DIFFICULTY[2] && !deadBoatsPlayer.isEmpty()) {
                    Boat ship = deadBoatsPlayer.removeLast();
                    ship.healShip();
                    pc.resetShotsOnBoat(ship);
                    return;
                }
            }
            return;
        } else {
            counterSuccessesPlayer = 0;
        }

        do {
            coordenates = pc.makeShot();
            info = player.handleShootEnemy(coordenates);
            if (info.isDamaged()) {
                if (pc.getDifficulty() == pc.DIFFICULTY[1]) {
                    pc.notifyHit(coordenates, true);
                }
                if (pc.getDifficulty() == pc.DIFFICULTY[2]) {
                    pc.notifyHit(coordenates);
                }
                counterSuccessesPc++;
                if (counterSuccessesPc == 2 && pc.getDifficulty() == pc.DIFFICULTY[2] && !deadBoatsPc.isEmpty()) {
                    Boat ship = deadBoatsPc.removeLast();
                    ship.healShip();
                    player.resetShotsOnBoat(ship);

                    counterSuccessesPc = 0;
                }

            } else {
                counterSuccessesPc = 0;
            }
            if (info.isDestroyed()) {
                pc.notifySink();
            }
        } while (info.isDamaged());

    }

    public boolean gameTurn(Position coordenates, ImageView square, GridPane gpEnemy) {
        if (!(player.canPlay() && pc.canPlay())) {
            return false;
            // se llama funcion para la otra pagina co ganador

        } else {
            turn(coordenates, square, gpEnemy);
            if (!(player.canPlay() && pc.canPlay())) {
                return false;

            }
            return true;
        }

    }

    public ArrayList whoIsWinner() {
        ArrayList<String> info = new ArrayList<>();
        double pointsOfPc, pointsOfPlayer;
        pointsOfPc = deadBoatsPlayer.size();
        pointsOfPlayer = deadBoatsPc.size();
        for (Boat i : player.getBoats()) {
            for (Health j : i.getHealths()) {
                if (j.getState() == false) {
                    pointsOfPc += HEALTH_POINT_PERCENTAGE;
                }
            }
        }
        for (Boat i : pc.getBoats()) {
            for (Health j : i.getHealths()) {
                if (j.getState() == false) {
                    pointsOfPlayer += HEALTH_POINT_PERCENTAGE;
                }
            }
        }
        if (pointsOfPc > pointsOfPlayer) {
            String name = pc.getName(), points;
            info.add(name);
            points = String.valueOf(pointsOfPc);
            info.add(points);
            return info;
        }
        if (pointsOfPc == pointsOfPlayer) {
            String name = "Empate", points;
            info.add(name);
            points = String.valueOf(pointsOfPc);
            info.add(points);
            return info;
        } else {
            String name = player.getName(), points;
            info.add(name);
            points = String.valueOf(pointsOfPlayer);
            info.add(points);
            return info;

        }

    }

}
