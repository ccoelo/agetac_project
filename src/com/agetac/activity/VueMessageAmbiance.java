/*
 * Projet AGETAC
 * Christophe Coelo
 * Universite de Rennes 1
 * ISTIC
 */
package com.agetac.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.agetac.R;
import com.agetac.activity.adapter.AdapterButtonMenu2;
import com.agetac.controller.ListenerSwitchToActivity;
import com.agetac.controller.messageAmbiance.ListenerEnvoiMsgAmb;
import com.agetac.fragment.VueDemandeDeMoyens;

/**
 * Classe VueMessageAmbiance : Activité qui affiche la fenêtre de saisie d'un message
 * d'ambiance
 * 
 * @version 1.0 20 Jan 2013
 * @author Christophe Coelo - 29001702
 */
public class VueMessageAmbiance extends Activity {

	/** Liste des champs du Message d'ambiance à remplir */
	private ListView listeChamps;
	
	/** EditText */
	private EditText champs;
	
	/** Boutons **/
	private Button boutonEnvoi;
	private Button boutonDemandeMoyen;
	
	/** Message d'ambiance constitué */
	private String[] infosMessageAmbiance;
	
	/** Liste des champs du Message d'ambiance */
	private String[] champsMsgAmb = {"Je suis", "Je vois", "Je prévois", "Je fais", "Je demande"};
		
	/** Position du dernier champs sélectionné */
	private int currentItemSelected = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.maquette_message_ambiance);

		// Récupération des composants by ID
		this.listeChamps = (ListView) findViewById(R.id.list_MessageAmbiance);
		this.champs = (EditText) findViewById(R.id.editTextMsgAmb);
		this.boutonEnvoi = (Button) findViewById(R.id.envoyerMessageAmbiance);
		this.boutonDemandeMoyen = (Button) findViewById(R.id.btnDemandeMoyen);
		
		// Initialisation des infos du message d'ambiance
		initInfos(infosMessageAmbiance);
		
				
		// Création du menu vertical
		getListeChamps().setAdapter(new AdapterButtonMenu2(this, champsMsgAmb));
		
		//AJout listener sur le boutton de demande de moyen : envoi sur la maquette correspondante
		this.boutonDemandeMoyen.setOnClickListener(new ListenerSwitchToActivity(this, VueDemandeDeMoyens.class));
		
		//Ajout listener sur le boutton d'envoi du message
		this.boutonEnvoi.setOnClickListener(new ListenerEnvoiMsgAmb(this));
	}
	
	/**
	 * Méthode qui initialise le tableau des infos du message d'ambiance
	 * @param infos
	 */
	private void initInfos(String[] infos) {
		
		this.infosMessageAmbiance = new String[5];
		this.infosMessageAmbiance[0] = "Je suis ";
		this.infosMessageAmbiance[1] = "Je vois ";
		this.infosMessageAmbiance[2] = "Je prévois ";
		this.infosMessageAmbiance[3] = "Je fais ";
		this.infosMessageAmbiance[4] = "Je demande ";
		this.champs.setText(infosMessageAmbiance[0]); // initialisaiton de l'EditText
		
	}
	
    /********************************************************************************************************/
    /** GETTEURS ET SETTEURS
    /********************************************************************************************************/

	/**
	 * @return the listeChamps
	 */
	public ListView getListeChamps() {
		return listeChamps;
	}

	/**
	 * @param listeChamps the listeChamps to set
	 */
	public void setListeChamps(ListView listeChamps) {
		this.listeChamps = listeChamps;
	}

	/**
	 * @return the champs
	 */
	public EditText getChamps() {
		return champs;
	}

	/**
	 * @param champs the champs to set
	 */
	public void setChamps(EditText champs) {
		this.champs = champs;
	}

	/**
	 * @return the infosMessageAmbiance
	 */
	public String[] getInfosMessageAmbiance() {
		return infosMessageAmbiance;
	}

	/**
	 * @param infosMessageAmbiance the infosMessageAmbiance to set
	 */
	public void setInfosMessageAmbiance(String[] infosMessageAmbiance) {
		this.infosMessageAmbiance = infosMessageAmbiance;
	}

	/**
	 * @return the champsMsgAmb
	 */
	public String[] getChampsMsgAmb() {
		return champsMsgAmb;
	}

	/**
	 * @param champsMsgAmb the champsMsgAmb to set
	 */
	public void setChampsMsgAmb(String[] champsMsgAmb) {
		this.champsMsgAmb = champsMsgAmb;
	}

	/**
	 * @return the currentItemSelected
	 */
	public int getCurrentItemSelected() {
		return currentItemSelected;
	}

	/**
	 * @param currentItemSelected the currentItemSelected to set
	 */
	public void setCurrentItemSelected(int currentItemSelected) {
		this.currentItemSelected = currentItemSelected;
	}

}// class
