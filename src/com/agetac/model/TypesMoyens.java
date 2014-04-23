/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.model;

/**
 *
 */
public class TypesMoyens {

	/** Champs */
	private int idTypeMoyen;
	private String nomTypeMoyen;
	private int idSecteur;
	
	/**
	 * Constructeur
	 * @param id
	 * @param nom
	 */
	public TypesMoyens (int id, String nom, int ids) {
		
		this.idTypeMoyen 	= id;
		this.nomTypeMoyen 	= nom;
		this.idSecteur 		= ids;
		
	}
	
	/************************************************
	* GETTERS & SETTERS
	************************************************/
	
	/**
	 * 
	 */
	public TypesMoyens() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param idTypeMoyen the idTypeMoyen to set
	 */
	public void setIdTypeMoyen(int idTypeMoyen) {
		this.idTypeMoyen = idTypeMoyen;
	}
	/**
	 * @return the idTypeMoyen
	 */
	public int getIdTypeMoyen() {
		return idTypeMoyen;
	}
	/**
	 * @param nomTypeMoyen the nomTypeMoyen to set
	 */
	public void setNomTypeMoyen(String nomTypeMoyen) {
		this.nomTypeMoyen = nomTypeMoyen;
	}
	/**
	 * @return the nomTypeMoyen
	 */
	public String getNomTypeMoyen() {
		return nomTypeMoyen;
	}

	public int getIdSecteur() {
		return idSecteur;
	}

	public void setIdSecteur(int idSecteur) {
		this.idSecteur = idSecteur;
	}
	
	
	
}
