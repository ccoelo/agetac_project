/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.items;

import java.io.Serializable;

/**
 * class itemListVisuSecteur : Modèle de représentation des item de la listView de la vue visualisationMoyen - 
 * sérializable afin de permettre la sauvegarde
 * 
 * @version 2.0 04/04/2013
 * @author Anthony LE MEE & Jimmy DANO - 10003134
*/
public class itemListVisuSecteur implements Serializable{

	/** Attributs */
	private static final long serialVersionUID = 1L;
	private int 	id;						// identifiant du secteur
	private String 	nom;					// nom du secteur
	private int 	nombreSurLieux 	= -1;	// nombre de moyen sur les lieux
	private int 	nombrePartis 	= -1;	// nombre de moyen sur les partis
	private String 	color;					// couleur du secteur
	
	/**
	 * Constructeur itemListDemandeMoyens
	 */
	public itemListVisuSecteur (int id, String nom, int nombreSurLieux, int nombrePartis, String Color) {
	
		this.id 				= id;
		this.nom 				= nom;
		this.nombreSurLieux 	= nombreSurLieux;
		this.nombrePartis	 	= nombrePartis;
		this.color 				= Color;
		
	}// méthode
	
	/*****************************************************************
	 * GETTERS & SETTERS
	 ****************************************************************/

	/**
	 * @param nom the nom to set
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
	 * @param nombreSurLieux the nombreSurLieux to set
	 */
	public void setNombreSurLieux(int nombreSurLieux) {
		this.nombreSurLieux = nombreSurLieux;
	}

	/**
	 * @return the nombreSurLieux
	 */
	public int getNombreSurLieux() {
		return nombreSurLieux;
	}

	/**
	 * @param nombrePartis the nombrePartis to set
	 */
	public void setNombrePartis(int nombrePartis) {
		this.nombrePartis = nombrePartis;
	}

	/**
	 * @return the nombrePartis
	 */
	public int getNombrePartis() {
		return nombrePartis;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	
} // class
