/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.batallanaval;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author ANTONY JOSUE
 */
public class Boat extends ImageView {
    public static final String BASEPATH = "others/img/";
    public static final String[] IMAGE_PATH = {
        BASEPATH+"submarines/submarine.jpg",
        BASEPATH+"destroyers/Destroyer.jpg",
        BASEPATH+"cruisers/Cruisers.jpg",
        BASEPATH+"battleship/battleship.jpg"};
    public static final String[] TYPES = {"submarines","destroyers","cruisers","battleship"};
    protected String type; //"submarines","Destroyers","cruisers","Battleship"
    protected Boolean state; // linving or dead
    protected Health[] healths;
    
    public Boat(String ptype){
        this.type = ptype;
        this.state = true;
        if(ptype.equals(TYPES[0])){
            this.healths = new Health[1];
            healths[0] = new Health(type,0);
            Image photo = new Image(Tools.getPath(IMAGE_PATH[0]).toString());
            setImage(photo);

        }
        else if(ptype.equals(TYPES[1])){
            this.healths = new Health[2];
            for (int i =0; i<2;i++){
                healths[i] = new Health(type,i);
            }
            Image photo = new Image(Tools.getPath(IMAGE_PATH[1]).toString());
            setImage(photo);


        }else if(ptype.equals(TYPES[2])){
            this.healths = new Health[3];
            for (int i =0; i<3;i++){
                healths[i] = new Health(type,i);
            }
            Image photo = new Image(Tools.getPath(IMAGE_PATH[2]).toString());
            setImage(photo);

        }
        else if (ptype.equals(TYPES[3])){
            this.healths = new Health[4];
            for (int i =0; i<4;i++){
                healths[i] = new Health(type,i);
            }
            Image photo = new Image(Tools.getPath(IMAGE_PATH[3]).toString());
            setImage(photo);
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

	

	public Health[] getHealths() {
		return healths;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public void setHealths(Health[] healths) {
		this.healths = healths;
	}

    public void healShip(){
        this.state = true;
        for(Health i: healths){
            i.setState(true);
        }
    }

    public void updateState(){
        int sizeBoat = healths.length;
        int counter = 0;
        for(Health i: healths){
            if(i.getState() == false){
                counter++;
            }
            
        }
        if(counter == sizeBoat){
            this.state = false;
        }
    }
}
