/*	
* Projet AGETAC	
* Jimmy Dano	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity;

import java.io.Serializable;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;

import com.agetac.R;

/**
* Classe VueGroupeIntervention : affiche les heures de départ, d'arrivée sur les lieux, etc pour 
* l'ensemble des moyens d'un groupe de secteur donné
*
* @version 1.0 17/01/2013
* @author Jimmy DANO - 13000159
*/
public class VueGroupeIntervention extends Activity{

	/** Nom du groupe choisi */
	private String groupName;
	
	/** Données reçues */
	Bundle extras;

	/**
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);	
		
		/** Chargement du Layout */
		setContentView(R.layout.maquette_visuel_groupe_intervention);
		
		/** On restaure notre état avec le précédent */
		if (savedInstanceState != null) {
	        super.onRestoreInstanceState(savedInstanceState);
	    }	
		
		// On récupère les données envoyés par le fragment MvcVisualisationMoyen
		this.extras = getIntent().getExtras();
		
		// Si aucun état précédent n'a été sauvegardé
		if (savedInstanceState == null) {
			
			// Utile ?
		    this.extras = getIntent().getExtras();
		    
		    // Si aucune donnée n'a été envoyé 
		    if(extras == null) {
		    	
		    	// Le nom du groupe est à vide
		        groupName = "";
		        
		    // Sinon
		    } else {
		    	
		    	// On récupère le nom du groupe courant et que l'on chargera dans la vue
		        groupName = extras.getString("GROUPE");
		        
		    }
		    
		// Sinon (si un état à été sauvegardé
		} else {
			
			// On récupère l'état préalablement sauvegardé
			groupName = (String) savedInstanceState.getBundle("GROUPE").getString("GROUPE");
			
		}
		
		/** Affichage du groupe en question */
		TextView tv1 = (TextView) findViewById(R.id.gh_groupe);
		tv1.setText(groupName);
		
		/** Chargement du tableau des moyens */
		TableLayout tl = (TableLayout) findViewById(R.id.tableGr);
	
	}// Méthode
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
		savedInstanceState.putBundle("GROUPE", extras);
	    super.onSaveInstanceState(savedInstanceState);
	    
	}

}// Classe