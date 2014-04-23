/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.controller.messageAmbiance;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.agetac.activity.VueMessageAmbiance;

/**
* Classe ListenerEnvoiMsgAmb pour VueMessageAmbiance : Listener pour l'envoi du message
*
* @version 1.0 06/02/2013
* @author Christophe Coelo - 29001702
*/
public class ListenerEnvoiMsgAmb implements OnClickListener{

	/** Instance de l'activity principale */
	private VueMessageAmbiance definirMsgAmbiance;
	
	 
	/**
	 * Construteur
	 * @param a Activity de VueMessageAmbiance
	 * @param s savedInstanceStateDemandeMoyens de VueMessageAmbiance
	 */
	public ListenerEnvoiMsgAmb(Activity a)  
	{  
		this.definirMsgAmbiance = (VueMessageAmbiance)a;
	}  
	  
	/**
	 * Lors d'un click sur le bouton b
	 * @param b View
	 */
	public void onClick(View b)  
	{  
		
	}// méthode

	
	
    /********************************************************************************************************/
    /** GETTEURS ET SETTEURS
    /********************************************************************************************************/
	
	/**
	 * @return the definirMsgAmbiance
	 */
	public VueMessageAmbiance getDefinirMsgAmbiance() {
		return definirMsgAmbiance;
	}

	/**
	 * @param definirMsgAmbiance the definirMsgAmbiance to set
	 */
	public void setDefinirMsgAmbiance(VueMessageAmbiance definirMsgAmbiance) {
		this.definirMsgAmbiance = definirMsgAmbiance;
	}
}
