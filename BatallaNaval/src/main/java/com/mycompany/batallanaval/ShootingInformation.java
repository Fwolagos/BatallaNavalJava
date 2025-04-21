/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.batallanaval;

/**
 *
 * @author ANTONY JOSUE
 */
public class ShootingInformation {
    protected boolean damaged;
    protected boolean destroyed;
    protected Boat ship;

	public ShootingInformation(boolean damaged, boolean destroyed, Boat ship) {
		this.damaged = damaged;
		this.destroyed = destroyed;
		this.ship = ship;
	}

	public ShootingInformation() {
        this.damaged = false;
        this.damaged = false;
        this.ship = null;
	}

	public boolean isDamaged() {
		return damaged;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public Boat getShip() {
		return ship;
	}

	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public void setShip(Boat ship) {
		this.ship = ship;
	}
	
}
