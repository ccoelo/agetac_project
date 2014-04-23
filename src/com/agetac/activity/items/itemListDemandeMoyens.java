/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.items;

import java.io.Serializable;

/**
 * class itemListDemandeMoyens : Modèle de représentation des item de la listView de la vue DemandeDeMoyens - sérializable afin de permettre la sauvegarde
 * 
 * @version 2.0 08/02/2013
 * @author Anthony LE MEE - 10003134
*/
public class itemListDemandeMoyens implements Serializable{

	/** Attributs */
	private static final long serialVersionUID = 1L;
	private String nom;			// type du moyen ajouté
	private int nombre;			// nombre de moyen de ce type voulu
	private String secteur;		// couleur du secteur sur lequel on veux ce type de moyen
	
	/**
	 * Constructeur itemListDemandeMoyens
	 * @param nom String - type du moyen ajouté
	 * @param nombre int - nombre de moyen de ce type voulu
	 * @param secteur String - couleur du secteur sur lequel on veux ce type de moyen
	 */
	public itemListDemandeMoyens (String nom, int nombre, String secteur) {
	
		this.nom = nom;
		this.nombre = nombre;
		this.secteur = secteur;
		
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
	 * @param nombre the nombre to set
	 */
	public void setNombre(int nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the nombre
	 */
	public int getNombre() {
		return nombre;
	}

	/**
	 * @param secteur the secteur to set
	 */
	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}

	/**
	 * @return the secteur
	 */
	public String getSpinnerChoixSecteur() {
		return secteur;
	}
	
} // class
