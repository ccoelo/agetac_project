/*	
 * Projet AGETAC	
 * Anthony LE MEE	
 * Universite de Rennes 1	
 * ISTIC	
 */
package com.agetac.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.agetac.R;
import com.agetac.activity.adapter.AdapterButtonMenu;
import com.agetac.controller.editionSoiecSaoiecl.ListenerEditerChamps;
import com.agetac.controller.editionSoiecSaoiecl.ListenerEnvoyerSoiecSaoiecl;
import com.agetac.controller.editionSoiecSaoiecl.ListenerRadioCouleur;

/**
 * Classe MvcDefinirSoiecSaoiecl : Pour définir le SOIEC/SAOIECL
 * 
 * @version 1.0 24/01/2013
 * @author Anthony LE MEE - 10003134
 */
public class VueDefinirSoiecSaoiecl extends Activity {

	/** Liste des champs du SOIEC/SAOIECL à remplir */
	private ListView listeChamps;

	/** EditText */
	private EditText champs;

	/** SOIEC constitué */
	private String[] infosSoiecSaoiecl;

	/** Liste des champs du SOIEC */
	private String[] champsSoiec = { "Situation", "Objectifs",
			"Idée de manoeuvre", "Exécution", "Commandement" };

	/** Liste des champs du SAOIECL */
	private String[] champsSaoiecl = { "Situation", "Anticipation",
			"Objectifs", "Idée de manoeuvre", "Exécution", "Commandement",
			"Logistique" };

	/** Position du dernier champs sélectionné */
	private int currentItemSelected = 0;

	/** Id du bouton radio rouge */
	private int radioRouge;

	/** Id du bouton radio bleu */
	private int radioBleu;

	/** Id du bouton radio vert */
	private int radioVert;

	/** Id du bouton radio violet */
	private int radioViolet;

	/** Id du bouton radio blanc */
	private int radioBlanc;
	
	/** Scrollview pour slider la textView lorsque le texte est trop long */
	private ScrollView scrollView;

	/** TextView contenant le texte affiché en couleurs */
	private TextView textColor;
	
	/** Bouton permettant de revenir à l'editText lorsqu'on voit le texte en couleur afin de le modifier */
	private Button editer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.maquette_soiec_saoiecl);

		// Récupération des composants by ID
		this.listeChamps = (ListView) findViewById(R.id.list_SOIEC_SAOIECL);
		this.champs = (EditText) findViewById(R.id.editTextSoiecSaoiecl);
		RadioGroup radioGroupCouleur = (RadioGroup) findViewById(R.id.radioGroupCouleur);

		((Button) findViewById(R.id.envoyerSoiec)).setOnClickListener(new ListenerEnvoyerSoiecSaoiecl(this));
		

		// Initialisation des infos du SOIEC/SAOIECL
		initInfos(infosSoiecSaoiecl);

		// Ajout du listener sur les radioButton de la palette de couleurs
		radioGroupCouleur.setOnCheckedChangeListener(new ListenerRadioCouleur(
				this));

		// Création du menu vertical
		getListeChamps().setAdapter(new AdapterButtonMenu(this, champsSoiec));

		//On récupère l'id des boutons radios pour identifier les couleurs
		radioRouge = this.findViewById(R.id.radioRouge).getId();
		radioBleu = this.findViewById(R.id.radioBleu).getId();
		radioBlanc = this.findViewById(R.id.radioBlanc).getId();
		radioVert = this.findViewById(R.id.radioVert).getId();
		radioViolet = this.findViewById(R.id.radioViolet).getId();
		
		//On identifie la textView pour afficher le texte en couleurs
		textColor = (TextView) this.findViewById(R.id.textView1);
		
		//On récupère le bouton d'édition et on lui assigne un onClickListener
		editer = (Button) this.findViewById(R.id.editerSoiec);
		editer.setOnClickListener(new ListenerEditerChamps(this));
		
		//doubletap = (TextView) this.findViewById(R.id.textView2);
		
		//On identifie la scrollView
		scrollView = (ScrollView) this.findViewById(R.id.scrollView1);

	}

	/**
	 * Méthode qui initialise le tableau des infos du SOIEC/SAOIECL
	 * 
	 * @param infos
	 */
	private void initInfos(String[] infos) {

		this.infosSoiecSaoiecl = new String[this.champsSoiec.length];
		for (int i = 0; i < this.infosSoiecSaoiecl.length; i++) {
			this.infosSoiecSaoiecl[i] = new String();
		}

	}

	/********************************************************************************************************/
	/**
	 * GETTEURS ET SETTEURS /
	 ********************************************************************************************************/

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
	 * @return the infosSoiecSaoiecl
	 */
	public String[] getInfosSoiecSaoiecl() {
		return infosSoiecSaoiecl;
	}

	/**
	 * @param infosSoiecSaoiecl the infosSoiecSaoiecl to set
	 */
	public void setInfosSoiecSaoiecl(String[] infosSoiecSaoiecl) {
		this.infosSoiecSaoiecl = infosSoiecSaoiecl;
	}

	/**
	 * @return the champsSoiec
	 */
	public String[] getChampsSoiec() {
		return champsSoiec;
	}

	/**
	 * @param champsSoiec the champsSoiec to set
	 */
	public void setChampsSoiec(String[] champsSoiec) {
		this.champsSoiec = champsSoiec;
	}

	/**
	 * @return the champsSaoiecl
	 */
	public String[] getChampsSaoiecl() {
		return champsSaoiecl;
	}

	/**
	 * @param champsSaoiecl the champsSaoiecl to set
	 */
	public void setChampsSaoiecl(String[] champsSaoiecl) {
		this.champsSaoiecl = champsSaoiecl;
	}

	/**
	 * @param currentItemSelected the currentItemSelected to set
	 */
	public void setCurrentItemSelected(int currentItemSelected) {
		this.currentItemSelected = currentItemSelected;
	}

	/**
	 * @return the currentItemSelected
	 */
	public int getCurrentItemSelected() {
		return currentItemSelected;
	}


	/**
	 * @return the radioRouge
	 */
	public int getRadioRouge() {
		return radioRouge;
	}


	/**
	 * @param radioRouge the radioRouge to set
	 */
	public void setRadioRouge(int radioRouge) {
		this.radioRouge = radioRouge;
	}


	/**
	 * @return the radioBleu
	 */
	public int getRadioBleu() {
		return radioBleu;
	}


	/**
	 * @param radioBleu the radioBleu to set
	 */
	public void setRadioBleu(int radioBleu) {
		this.radioBleu = radioBleu;
	}


	/**
	 * @return the radioVert
	 */
	public int getRadioVert() {
		return radioVert;
	}


	/**
	 * @param radioVert the radioVert to set
	 */
	public void setRadioVert(int radioVert) {
		this.radioVert = radioVert;
	}


	/**
	 * @return the radioViolet
	 */
	public int getRadioViolet() {
		return radioViolet;
	}


	/**
	 * @param radioViolet the radioViolet to set
	 */
	public void setRadioViolet(int radioViolet) {
		this.radioViolet = radioViolet;
	}


	/**
	 * @return the radioBlanc
	 */
	public int getRadioBlanc() {
		return radioBlanc;
	}


	/**
	 * @param radioBlanc the radioBlanc to set
	 */
	public void setRadioBlanc(int radioBlanc) {
		this.radioBlanc = radioBlanc;
	}


	/**
	 * @return the scrollView
	 */
	public ScrollView getScrollView() {
		return scrollView;
	}


	/**
	 * @param scrollView the scrollView to set
	 */
	public void setScrollView(ScrollView scrollView) {
		this.scrollView = scrollView;
	}


	/**
	 * @return the textColor
	 */
	public TextView getTextColor() {
		return textColor;
	}


	/**
	 * @param textColor the textColor to set
	 */
	public void setTextColor(TextView textColor) {
		this.textColor = textColor;
	}


	/**
	 * @return the editer
	 */
	public Button getEditer() {
		return editer;
	}


	/**
	 * @param editer the editer to set
	 */
	public void setEditer(Button editer) {
		this.editer = editer;
	}
	

}