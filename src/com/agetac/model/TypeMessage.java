package com.agetac.model;

public class TypeMessage {

	private int idtype;
	private String libelleTypeMessage;
		
	/**
	   Constructeur typeMessage
	*/
	public TypeMessage(int idtype, String libelleTypeMessage){
		
		this.libelleTypeMessage		= libelleTypeMessage;
		this.idtype					= idtype;
		
	} // Méthode constructeur

	/**********************************************/
	/**********   GETTERS AND SETTERS     *********/
	/**********************************************/

	/**
	 * @param idtype the idtype to set
	 */
	public void setIdtype(int idtype) {
		this.idtype = idtype;
	}

	/**
	 * @return the idtype
	 */
	public int getIdtype() {
		return idtype;
	}

	/**
	 * @param libelleTypeMessage the libelleTypeMessage to set
	 */
	public void setLibelleTypeMessage(String libelleTypeMessage) {
		this.libelleTypeMessage = libelleTypeMessage;
	}

	/**
	 * @return the libelleTypeMessage
	 */
	public String getLibelleTypeMessage() {
		return libelleTypeMessage;
	}
	
} // class
