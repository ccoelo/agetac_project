/*	
 * Projet AGETAC	
 * Anthony LE MEE	
 * Universite de Rennes 1	
 * ISTIC	
 */
package com.agetac.activity.items;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import com.agetac.R;

/**
 * class itemListDessins : Mod�le de repr�sentation des item de la listView de
 * la vue SITAC - s�rializable afin de permettre la sauvegarde
 * 
 * @version 2.0 26/03/2013
 * @author Anthony LE MEE - 10003134
 * @author Christophe Coelo - 29001702
 */
public class itemListDessins implements Serializable {

	/** Attributs */
	private static final long serialVersionUID = 1L;
	private String nom; // nom du dessin ajout�
	private Object instanceDessins; // instance du dessin
	private int type; // type du dessin ajout�
	/**
	 *  0 : PathOverlay
	 *  1 : DragItemOverlay - Vehicule
	 *  2 : DragItemOverlay - Icone (danger ou sensible)
	 */
	private int color = -16777216; // couleur du dessin
	private int idRessource;
	/**
	 * Constructeur itemListDessins
	 * 
	 * @param nom
	 *            String - nom du dessin ajout�
	 * @param i
	 *            Object - instance du dessin
	 * @param t
	 *            int - type du dessin
	 * @param c
	 *            String - couleur du dessin
	 */
	public itemListDessins(String nom, Object i, int t, int c, int idR) {
		
		this.setNom(nom);
		this.setInstanceDessins(i);
		this.setType(t);
		if (c != 0)
		{
			this.setColor(c);
		}
		this.idRessource=idR;

	}// m�thode

	/*****************************************************************
	 * GETTERS & SETTERS
	 ****************************************************************/

	public int getIcon(){
		return idRessource;
	}
	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param instanceDessins
	 *            the instanceDessins to set
	 */
	public void setInstanceDessins(Object instanceDessins) {
		this.instanceDessins = instanceDessins;
	}

	/**
	 * @return the instanceDessins
	 */
	public Object getInstanceDessins() {
		return instanceDessins;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

} // class
