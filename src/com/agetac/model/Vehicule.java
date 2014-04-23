package com.agetac.model;

import java.sql.Timestamp;
import java.util.Date;

public class Vehicule {

	private int disponibilite;
	private int typeVehicule;
	private String nomVehicule;
	private String nameSecteur;
	private Coordonnees position;
	private int id;
	private int idCaserne;
	private int idInter;
	private int idSecteur;
	private int idGroupeInter;
	private Date demande;
	private Date arrivee;
	private Date departCaserne;
	private Date retour;
	private Date secteurFDate;
	private int secteurF;
	
	/**
	 * @param id_cas
	 * @param id_type
	 * @param id_ve
	 * @param id_inter
	 * @param id_sec
	 * @param id_groupe
	 * @param datearrivee
	 * @param datedemande
	 * @param datedepartcaserne
	 * @param dateretourcaserne
	 * @param datesecteurfinal
	 * @param secteurFinal
	 */
	public Vehicule(int id_cas, int id_type, int id_ve, int id_inter,
			int id_sec, int id_groupe, Timestamp datearrivee,
			Timestamp datedemande, Timestamp datedepartcaserne,
			Timestamp dateretourcaserne, Timestamp datesecteurfinal,
			int secteurFinal, String nomVehicule1, String nameSecteur1) {
		
		typeVehicule = id_type;
		id = id_ve;
		idCaserne = id_cas;
		idInter = id_inter;
		idSecteur = id_sec;
		idGroupeInter = id_groupe;
		demande = datedemande;
		arrivee = datearrivee;
		departCaserne = datedepartcaserne;
		retour = dateretourcaserne;
		secteurFDate = datesecteurfinal;
		secteurF = secteurFinal;
		nomVehicule = nomVehicule1;
		nameSecteur = nameSecteur1;
		
	}
	/**
	 * 
	 */
	public Vehicule() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param idCaserne2
	 * @param typeVehicule2
	 * @param id2
	 * @param idInter2
	 * @param idSecteur2
	 * @param idGroupeInter2
	 * @param arrivee2
	 * @param demande2
	 * @param departCaserne2
	 * @param retour2
	 * @param secteurFDate2
	 * @param secteurF2
	 */
	public Vehicule(int idCaserne2, int typeVehicule2, int id2, int idInter2,
			int idSecteur2, int idGroupeInter2, Date arrivee2, Date demande2,
			Date departCaserne2, Date retour2, Date secteurFDate2, int secteurF2, String nomVehicule2, String nameSecteur2) {
		typeVehicule = typeVehicule2;
		id = id2;
		idCaserne = idCaserne2;
		idInter = idInter2;
		idSecteur = idSecteur2;
		idGroupeInter = idGroupeInter2;
		demande = demande2;
		arrivee = arrivee2;
		departCaserne = departCaserne2;
		retour = retour2;
		secteurFDate = secteurFDate2;
		secteurF = secteurF2;
		nomVehicule = nomVehicule2;
		nameSecteur = nameSecteur2;
	}
	/************************************************
	* GETTERS & SETTERS
	************************************************/
	
	public int getDisponibilite() {
		return disponibilite;
	}
	public void setDisponibilite(int disponibilite) {
		this.disponibilite = disponibilite;
	}
	public int getTypeVehicule() {
		return typeVehicule;
	}
	public void setTypeVehicule(int typeVehicule) {
		this.typeVehicule = typeVehicule;
	}
	public Coordonnees getPosition() {
		return position;
	}
	public void setPosition(Coordonnees position) {
		this.position = position;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdCaserne() {
		return idCaserne;
	}
	public void setIdCaserne(int idCaserne) {
		this.idCaserne = idCaserne;
	}
	public int getIdInter() {
		return idInter;
	}
	public void setIdInter(int idInter) {
		this.idInter = idInter;
	}
	public int getIdSecteur() {
		return idSecteur;
	}
	public void setIdSecteur(int idSecteur) {
		this.idSecteur = idSecteur;
	}
	public int getIdGroupeInter() {
		return idGroupeInter;
	}
	public void setIdGroupeInter(int idGroupeInter) {
		this.idGroupeInter = idGroupeInter;
	}
	public Date getDemande() {
		return demande;
	}
	public void setDemande(Date demande) {
		this.demande = demande;
	}
	public Date getArrivee() {
		return arrivee;
	}
	public void setArrivee(Date arrivee) {
		this.arrivee = arrivee;
	}
	public Date getDepartCaserne() {
		return departCaserne;
	}
	public void setDepartCaserne(Date departCaserne) {
		this.departCaserne = departCaserne;
	}
	public Date getRetour() {
		return retour;
	}
	public void setRetour(Date retour) {
		this.retour = retour;
	}
	public Date getSecteurFDate() {
		return secteurFDate;
	}
	public void setSecteurFDate(Date secteurFDate) {
		this.secteurFDate = secteurFDate;
	}
	public int getSecteurF() {
		return secteurF;
	}
	public void setSecteurF(int secteurF) {
		this.secteurF = secteurF;
	}
	/**
	 * @param nomVehicule the nomVehicule to set
	 */
	public void setNomVehicule(String nomVehicule) {
		this.nomVehicule = nomVehicule;
	}
	/**
	 * @return the nomVehicule
	 */
	public String getNomVehicule() {
		return nomVehicule;
	}
	/**
	 * @param nameSecteur the nameSecteur to set
	 */
	public void setNameSecteur(String nameSecteur) {
		this.nameSecteur = nameSecteur;
	}
	/**
	 * @return the nameSecteur
	 */
	public String getNameSecteur() {
		return nameSecteur;
	}
}
