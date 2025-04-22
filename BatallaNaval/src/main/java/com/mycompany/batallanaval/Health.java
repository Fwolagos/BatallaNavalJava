/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.batallanaval;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 *
 * @author ANTONY JOSUE
 */
public class Health extends ImageView  {
    public static final String[] TYPES = {"submarines","destroyers","cruisers","battleship"};
    public static final String BASEPATH = "others/img/";


// Submarinos
public static final String[] SUBMARINES_OK = {
    BASEPATH+"submarines/submarine.jpg"
};
public static final String[] SUBMARINES_DAMAGED = {
    BASEPATH + "submarines/submarineDamaged.jpg"
};

// Destroyers (2 partes)
public static final String[] DESTROYERS_OK = {
    BASEPATH + "destroyers/DestroyerLeft.jpg",
    BASEPATH + "destroyers/DestroyerRight.jpg"
};
public static final String[] DESTROYERS_DAMAGED = {
    BASEPATH + "destroyers/DestroyerLeftDamaged.jpg",
    BASEPATH + "destroyers/DestroyerRightDamaged.jpg"
};

// Cruisers (3 partes)
public static final String[] CRUISERS_OK = {
    BASEPATH + "cruisers/cruisers1.jpg",
    BASEPATH + "cruisers/cruisers2.jpg",
    BASEPATH + "cruisers/cruisers3.jpg"
};
public static final String[] CRUISERS_DAMAGED = {
    BASEPATH + "cruisers/cruisers1Damaged.jpg",
    BASEPATH + "cruisers/cruisers2Damaged.jpg",
    BASEPATH + "cruisers/cruisers3Damaged.jpg"
};

// Battleship (4 partes)
public static final String[] BATTLESHIP_OK = {
    BASEPATH + "battleship/battleship1.jpg",
    BASEPATH + "battleship/battleship2.jpg",
    BASEPATH + "battleship/battleship3.jpg",
    BASEPATH + "battleship/battleship4.jpg"
};
public static final String[] BATTLESHIP_DAMAGED = {
    BASEPATH + "battleship/battleship1Damaged.jpg",
    BASEPATH + "battleship/battleship2Damaged.jpg",
    BASEPATH + "battleship/battleship3Damaged.jpg",
    BASEPATH + "battleship/battleship4Damaged.jpg"
};
    public String living;
    public String damaged;
    protected boolean state;
    private Position coordenates;

    public Health(String type, int position){
        //super();
        this.state = true;
        if(type.equals(TYPES[0]) ){
            living = SUBMARINES_OK[position];
            damaged = SUBMARINES_DAMAGED[position];
        }
        else if(type.equals(TYPES[1])){
            living = DESTROYERS_OK[position];
            damaged = DESTROYERS_DAMAGED[position];

        }
        else if(type.equals(TYPES[2])){
            living = CRUISERS_OK[position];
            damaged = CRUISERS_DAMAGED[position];
        }
        else{
            living = BATTLESHIP_OK[position];
            damaged = BATTLESHIP_DAMAGED[position];
        }
        URL path = Tools.getPath(living);
        //URL path = getPath(living); //Tools.getPath(living);
        //Image photo = new Image(getPath1(living).toString());
        Image photo = new Image(path.toString());
        setImage(photo);
        coordenates = new Position();
        // setFitWidth(28);
        // setFitHeight(28);

    }

	

	public boolean getState() {
		return state;
	}


	public void setState(boolean state) {
		this.state = state;
        updateState();
	}


    public void updateState(){
	    Image photo;
	    if(state == true){
            photo = new Image(Tools.getPath(living).toString());
	    }else{photo = new Image(Tools.getPath(damaged).toString());}
        setImage(photo);
    
    }
    

	public Position getCoordenates() {
		return coordenates;
	}

	public void setCoordenates(Position pCoordenates) {
		this.coordenates = pCoordenates;
	}

    public void restartOrientation(){
        setRotate(0);      // Reinicia la rotación
        setScaleX(1);      // Reinicia volteo horizontal
        setScaleY(1);      // Reinicia volteo vertical

    }

}
