/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.batallanaval;


/**
 *
 * @author ANTONY JOSUE
 */
public class Boat{
    public static final String[] TYPES = {"submarines","Destroyers","cruisers","Battleship"};
    protected String type; //"submarines","Destroyers","cruisers","Battleship"
    protected Boolean state; // linving or dead
    protected int[] location;
    protected Health[] healths;
    public Boat(String ptype){
        this.type = ptype;
        this.state = true;
        if(ptype == TYPES[0]){
            this.location = new int[1];
            this.healths = new Health[1];

        }
        else if(ptype==TYPES[1]){
            this.location = new int[2];
            this.healths = new Health[2];


        }else if(ptype==TYPES[2]){
            this.location = new int[3];
            this.healths = new Health[3];
        }
        else if (ptype == TYPES[3]){
            this.location = new int[4];
            this.healths = new Health[4];
        }
    }

	public static String[] getTYPES() {
		return TYPES;
	}

	public String getType() {
		return type;
	}

	public Boolean getState() {
		return state;
	}

	public int[] getLocation() {
		return location;
	}

	public Health[] getHealths() {
		return healths;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public void setLocation(int[] location) {
		this.location = location;
	}

	public void setHealths(Health[] healths) {
		this.healths = healths;
	}

}
