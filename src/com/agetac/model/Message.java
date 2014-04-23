package com.agetac.model;

import java.sql.Timestamp;

public class Message {

	
	private int idInter;
	private int idMessage;
	private int idEmmeteur;
	private int idRecepteur;
	private Timestamp envoi;
	private Timestamp lecture;
	private int idtype;
	private String contenu;
	private String libelleType;
	private String PrenomNomEmetteur;
	private Boolean lu;
	
	/**
	   Constructeur du message lu
	*/
	public Message(
			int idinter, int idMessage, int idEmmeteur,
			int idRecepteur, Timestamp envoi, Timestamp lecture,
			String contenu, int idtype, String libelletype,
			String nomprenomemetteur, boolean islu){
		
		this.idInter 			= idinter;
		this.idMessage 			= idMessage;
		this.idEmmeteur 		= idEmmeteur;
		this.idRecepteur 		= idRecepteur;
		this.envoi				= envoi;
		this.lecture			= lecture;
		this.contenu			= contenu;
		this.idtype				= idtype;
		this.libelleType		= libelletype;
		this.PrenomNomEmetteur 	= nomprenomemetteur;
		this.lu 				= islu;
		
	} 
	
	/**
	   Constructeur du message lu
	*/
	public Message(
			int idinter, int idMessage, int idEmmeteur,
			int idRecepteur, Timestamp envoi,
			String contenu, int idtype, String libelletype,
			String nomprenomemetteur, boolean islu){
		
		this.idInter 			= idinter;
		this.idMessage 			= idMessage;
		this.idEmmeteur 		= idEmmeteur;
		this.idRecepteur 		= idRecepteur;
		this.envoi				= envoi;
		this.contenu			= contenu;
		this.idtype				= idtype;
		this.libelleType		= libelletype;
		this.PrenomNomEmetteur 	= nomprenomemetteur;
		this.lu 				= islu;
		
	} 
	
	/**
	 * 
	 */
	public Message() {
		// TODO Auto-generated constructor stub
	}



	/**********************************************/
	/**********   GETTERS AND SETTERS     *********/
	/**********************************************/
	
	public int getIdInter() {
		return idInter;
	}
	public void setIdInter(int id) {
		this.idInter = id;
	}
	public boolean isLu() {
		return lu;
	}
	public void setLu(boolean s) {
		lu = s;
	}
	public int getIdMessage() {
		return idMessage;
	}
	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String s) {
		contenu = s;
	}
	public void setIdEmmeteur(int idEmmeteur) {
		this.idEmmeteur = idEmmeteur;
	}
	public int getIdEmmeteur() {
		return idEmmeteur;
	}
	public void setIdRecepteur(int idRecepteur) {
		this.idRecepteur = idRecepteur;
	}
	public int getIdRecepteur() {
		return idRecepteur;
	}
	public void setLecture(Timestamp lecture) {
		this.lecture = lecture;
	}
	public Timestamp getLecture() {
		return lecture;
	}
	public void setEnvoi(Timestamp envoi) {
		this.envoi = envoi;
	}
	public Timestamp getEnvoi() {
		return envoi;
	}
	public void setIdtype(int idtype) {
		this.idtype = idtype;
	}
	public int getIdtype() {
		return idtype;
	}
	public String getLibelleType() {
		return libelleType;
	}
	public void setLibelleType(String libelleType) {
		this.libelleType = libelleType;
	}
	public void setPrenomNomEmetteur(String prenomNomEmetteur) {
		PrenomNomEmetteur = prenomNomEmetteur;
	}
	public String getPrenomNomEmetteur() {
		return PrenomNomEmetteur;
	}
	
} // class
