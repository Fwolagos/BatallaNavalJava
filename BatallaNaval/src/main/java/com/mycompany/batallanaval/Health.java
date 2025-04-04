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
public class Health extends ImageView {
    public static final String LIVING = "img/ruta";
    public static final String DAMAGED = "img/ruta";
    public static final String DEAD = "img/ruta";
    protected int state; // 2 vivo, 1 d;ado, 0 muerto
    public Health(){
        this.state = 2;
        Image photo = new Image(LIVING);
        setImage(photo);

    }

	public static String getLIVING() {
		return LIVING;
	}

	public static String getDAMAGED() {
		return DAMAGED;
	}

	public static String getDEAD() {
		return DEAD;
	}

	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
        updateState();
	}


    public void updateState(){
	    Image photo;
        switch (state) {
            case 2:
                photo = new Image(LIVING);
                break;
            case 1:
                photo = new Image(DAMAGED);
                break;
            case 0:
                photo = new Image(DEAD);
                break;
            default:
                photo = new Image(LIVING);
                break;
        }
        setImage(photo);
    
    }
}
