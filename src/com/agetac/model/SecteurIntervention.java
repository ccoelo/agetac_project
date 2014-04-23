package com.agetac.model;

public class SecteurIntervention {

	/** Champs */
	private int idSecteur;
	private String nomSecteur;
	private String couleurSecteur;
	private int id;
	
	/**
	 * Contructeur d'un secteur d'intervention
	 * @param id - int 
	 * @param nom - String
	 * @param couleur - String
	 */
	public SecteurIntervention(int id, String nom, String couleur) {
		
		this.idSecteur  = id;
		this.nomSecteur = nom;
		this.couleurSecteur = couleur;
		
	}
	
	/************************************************
	* GETTERS & SETTERS
	************************************************/

	/**
	 * @param string 
	 * @param id2 
	 * 
	 */
	public SecteurIntervention(int id2, String string) {
		idSecteur = id2;
		nomSecteur = string;
	}

	/**
	 * @param idSecteur the idSecteur to set
	 */
	public void setSecteur(int idSecteur) {
		this.idSecteur = idSecteur;
	}
	
	/**
	 * @return the idSecteur
	 */
	public int getIdSecteur() {
		return this.idSecteur;
	}

	/**
	 * @param nomSecteur the nomSecteur to set
	 */
	public void setNomSecteur(String nomSecteur) {
		this.nomSecteur = nomSecteur;
	}

	/**
	 * @return the nomSecteur
	 */
	public String getNomSecteur() {
		return nomSecteur;
	}

	/**
	 * @param couleurSecteur the couleurSecteur to set
	 */
	public void setCouleurSecteur(String couleurSecteur) {
		this.couleurSecteur = couleurSecteur;
	}

	/**
	 * @return the couleurSecteur
	 */
	public String getCouleurSecteur() {
		return couleurSecteur;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}// class
