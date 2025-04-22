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
	public static final String[] DIFFICULTY = { "easy", "normal", "hard" };

	protected String difficulty;
	private Position lastHit; // Último golpe
	private List<Position> pendingTargets = new ArrayList<>();
	private List<Position> hitsCluster = new ArrayList<>();
	// private boolean huntingMode = false;
	Random rnd = new Random();

	public NonPlayerCharacter(String difficulty) {
		this.difficulty = difficulty;
	}

	public NonPlayerCharacter() {
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

	

	public void notifyHit(Position pos, boolean hit) {// Tiro dificil
		if (hit) {
			hitsCluster.add(pos);
			for (Position i : getAdjacentPositions(pos)) {
				if (!locationShoots[i.getX()][i.getY()]) {
					locationShoots[i.getX()][i.getY()] = true;
				}
			}
		}
	}

	private boolean isValid(Position p) {
		return p.getX() >= 0 && p.getX() < SIZE_MATRIX
				&& p.getY() >= 0 && p.getY() < SIZE_MATRIX;
	}

	public Position shootNormal() {
		// Paso 1: disparar siguiendo orientación si ya hay dos hits
		Position oriented = getNextTargetFromCluster();
		if (oriented != null) {
			locationShoots[oriented.getX()][oriented.getY()] = true;
			return oriented;
		}

		// Paso 2: disparar a los adyacentes de últimos hits
		while (!pendingTargets.isEmpty()) {
			Position target = pendingTargets.remove(0);
			if (!locationShoots[target.getX()][target.getY()]) {
				locationShoots[target.getX()][target.getY()] = true;
				return target;
			}
		}

		// Paso 3: disparar en patrón ajedrezado
		for (int x = 0; x < SIZE_MATRIX; x++) {
			for (int y = 0; y < SIZE_MATRIX; y++) {
				if ((x + y) % 2 == 0 && !locationShoots[x][y]) {
					locationShoots[x][y] = true;
					return new Position(x, y);
				}
			}
		}

		// Paso 4: disparo aleatorio si no hay otra opción
		return shotEasy();

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


	public void notifySink() {
		lastHit = null;
		hitsCluster.clear();
		pendingTargets.clear();
	}

	public Position makeShot() {
		if (thereAreBullets()) {
			switch (difficulty) {
				case "easy":
					return shotEasy();
				case "normal":
					return shootNormal();
				default:
					return shotEasy();

			}
		}
		return null;
	}

	private Position getNextTargetFromCluster() {
		if (hitsCluster.size() < 2)
			return null;

		Position p1 = hitsCluster.get(0);
		Position p2 = hitsCluster.get(1);
		int dx = p2.getX() - p1.getX();
		int dy = p2.getY() - p1.getY();

		for (Position p : hitsCluster) {
			Position forward = new Position(p.getX() + dx, p.getY() + dy);
			if (isValid(forward) && !locationShoots[forward.getX()][forward.getY()]) {
				return forward;
			}

			Position backward = new Position(p.getX() - dx, p.getY() - dy);
			if (isValid(backward) && !locationShoots[backward.getX()][backward.getY()]) {
				return backward;
			}
		}

		return null;
	}

	public Position shootHard(ArrayList<Boat> enemyBoats) {
		int[][] probabilityMap = new int[SIZE_MATRIX][SIZE_MATRIX];

		// Paso 1: Generar el mapa de probabilidad
		for (Boat boat : enemyBoats) {
			if (!boat.getState())
				continue; // Saltar barcos hundidos

			for (Health part : boat.getHealths()) {
				if (part.getState()) {
					Position pos = part.getCoordenates();
					int x = pos.getX();
					int y = pos.getY();

					// Aumentar probabilidad en la casilla si no se ha disparado ahí
					if (!locationShoots[x][y]) {
						probabilityMap[x][y]++;
					}

					// Aumentar probabilidad en vecinos si están dentro del rango y no se ha
					// disparado
					int[] dx = { -1, 1, 0, 0 };
					int[] dy = { 0, 0, -1, 1 };

					for (int d = 0; d < 4; d++) {
						int nx = x + dx[d];
						int ny = y + dy[d];

						if (nx >= 0 && ny >= 0 && nx < SIZE_MATRIX && ny < SIZE_MATRIX) {
							if (!locationShoots[nx][ny]) {
								probabilityMap[nx][ny]++;
							}
						}
					}
				}
			}
		}

		// Paso 2: Buscar la casilla con mayor probabilidad
		int maxProbability = -1;
		ArrayList<Position> bestOptions = new ArrayList<>();

		for (int i = 0; i < SIZE_MATRIX; i++) {
			for (int j = 0; j < SIZE_MATRIX; j++) {
				if (!locationShoots[i][j]) {
					if (probabilityMap[i][j] > maxProbability) {
						maxProbability = probabilityMap[i][j];
						bestOptions.clear();
						bestOptions.add(new Position(i, j));
					} else if (probabilityMap[i][j] == maxProbability) {
						bestOptions.add(new Position(i, j));
					}
				}
			}
		}

		// Si no hay opciones con probabilidad (por si acaso), hacer un disparo
		// aleatorio
		if (bestOptions.isEmpty()) {
			Random rand = new Random();
			int x, y;
			int tries = 0;
			do {
				x = rand.nextInt(SIZE_MATRIX);
				y = rand.nextInt(SIZE_MATRIX);
				tries++;
				if (tries > 500)
					break; // rompe el ciclo si no encuentra nada
			} while (locationShoots[x][y]);
			locationShoots[x][y] = true;
			return new Position(x, y);

		}

		// Elegir una posición aleatoria entre las mejores opciones
		Random rand = new Random();
		Position pos = bestOptions.get(rand.nextInt(bestOptions.size())); 	
			locationShoots[pos.getX()][pos.getY()] = true;
		return pos; 
	}
}
