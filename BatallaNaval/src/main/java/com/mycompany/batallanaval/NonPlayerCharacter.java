/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.batallanaval;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ANTONY JOSUE
 */
public class NonPlayerCharacter extends Player {
	public static final String[] DIFFICULTY = {"easy","normal","hard"};

	protected String difficulty;
	private Position lastHit; // Último golpe
	private List<Position> pendingTargets = new ArrayList<>(); // Próximos objetivos	
	Random rnd = new Random();

	public NonPlayerCharacter(String difficulty) {
		this.difficulty = difficulty;
	}

	public NonPlayerCharacter(){
		this.difficulty = DIFFICULTY[1];
	}

	public String getDifficulty() {
		return difficulty;
	}

	public Position getLastHit() {
		return lastHit;
	}

	public List<Position> getPendingTargets() {
		return pendingTargets;
	}

	public Random getRnd() {
		return rnd;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public void setLastHit(Position lastHit) {
		this.lastHit = lastHit;
	}

	public void setPendingTargets(List<Position> pendingTargets) {
		this.pendingTargets = pendingTargets;
	}

	public void setRnd(Random rnd) {
		this.rnd = rnd;
	}

	public Position shotEasy() {
		int x, y;
		do {
			x = rnd.nextInt(SIZE_MATRIX);
			y = rnd.nextInt(SIZE_MATRIX);
		} while (locationShoots[x][y]); // Ya fue disparado

		locationShoots[x][y] = true;
		return new Position(x, y);
	}

	public Position shotNormal() {
		if (lastHit != null) {
			// intenta disparar a una de las 4 direcciones
			List<Position> options = List.of(
				new Position(lastHit.getX() + 1, lastHit.getY()),
				new Position(lastHit.getX() - 1, lastHit.getY()),
				new Position(lastHit.getX(), lastHit.getY() + 1),
				new Position(lastHit.getX(), lastHit.getY() - 1)
			);
			for (Position pos : options) {
				if (isValid(pos) && !locationShoots[pos.getX()][pos.getY()]) {
					locationShoots[pos.getX()][pos.getY()] = true;
					return pos;
				}
			}
		}

		// Si no hay último golpe o ya intentó todos los alrededores
		Position shot = shotEasy();
		return shot;
	}

	public void notifyHit(Position pos, boolean hit) {
		if (hit) {
			lastHit = pos;
		} else {
			lastHit = null;
		}
	}

	private boolean isValid(Position p) {
		return p.getX() >= 0 && p.getX() < SIZE_MATRIX
			&& p.getY() >= 0 && p.getY() < SIZE_MATRIX;
	}

	public Position shootHard() {
		Position target;

		// Prioridad 1: seguir con posibles objetivos
		while (!pendingTargets.isEmpty()) {
			target = pendingTargets.remove(0);
			if (!locationShoots[target.getX()][target.getY()]) {
				locationShoots[target.getX()][target.getY()] = true;
				return target;
			}
		}

		// Prioridad 2: buscar aleatoriamente
		do {
			int x = rnd.nextInt(SIZE_MATRIX);
			int y = rnd.nextInt(SIZE_MATRIX);
			target = new Position(x, y);
		} while (locationShoots[target.getX()][target.getY()]);

		locationShoots[target.getX()][target.getY()] = true;
		return target;
	}

	private List<Position> getAdjacentPositions(Position pos) {
		List<Position> positions = new ArrayList<>();
		int x = pos.getX();
		int y = pos.getY();

		if (x > 0) {
			positions.add(new Position(x - 1, y));
		}
		if (x < SIZE_MATRIX - 1) {
			positions.add(new Position(x + 1, y));
		}
		if (y > 0) {
			positions.add(new Position(x, y - 1));
		}
		if (y < SIZE_MATRIX - 1) {
			positions.add(new Position(x, y + 1));
		}

		return positions;
	}

	public void notifyHit(Position hit) {
		this.lastHit = hit;
		for (Position p : getAdjacentPositions(hit)) {
			if (!locationShoots[p.getX()][p.getY()]) {
				locationShoots[p.getX()][p.getY()] = true;
			}
		}
	}

	public void notifySink() {
		lastHit = null;
		pendingTargets.clear();
	}

	public Position makeShot() {
		if (thereAreBullets()) {
			switch (difficulty) {
				case "easy":
					return shotEasy();
				case "normal":
					return shotNormal();
				case "hard":
					return shootHard();
				default:
					return shotEasy();

			}
		}
		return null;
	}
}
