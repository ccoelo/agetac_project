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
* Classe VueGroupeIntervention : affiche les heures de d�part, d'arriv�e sur les lieux, etc pour 
* l'ensemble des moyens d'un groupe de secteur donn�
*
* @version 1.0 17/01/2013
* @author Jimmy DANO - 13000159
*/
public class VueGroupeIntervention extends Activity{

	/** Nom du groupe choisi */
	private String groupName;
	
	/** Donn�es re�ues */
	Bundle extras;

	/**
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);	
		
		/** Chargement du Layout */
		setContentView(R.layout.maquette_visuel_groupe_intervention);
		
		/** On restaure notre �tat avec le pr�c�dent */
		if (savedInstanceState != null) {
	        super.onRestoreInstanceState(savedInstanceState);
	    }	
		
		// On r�cup�re les donn�es envoy�s par le fragment MvcVisualisationMoyen
		this.extras = getIntent().getExtras();
		
		// Si aucun �tat pr�c�dent n'a �t� sauvegard�
		if (savedInstanceState == null) {
			
			// Utile ?
		    this.extras = getIntent().getExtras();
		    
		    // Si aucune donn�e n'a �t� envoy� 
		    if(extras == null) {
		    	
		    	// Le nom du groupe est � vide
		        groupName = "";
		        
		    // Sinon
		    } else {
		    	
		    	// On r�cup�re le nom du groupe courant et que l'on chargera dans la vue
		        groupName = extras.getString("GROUPE");
		        
		    }
		    
		// Sinon (si un �tat � �t� sauvegard�
		} else {
			
			// On r�cup�re l'�tat pr�alablement sauvegard�
			groupName = (String) savedInstanceState.getBundle("GROUPE").getString("GROUPE");
			
		}
		
		/** Affichage du groupe en question */
		TextView tv1 = (TextView) findViewById(R.id.gh_groupe);
		tv1.setText(groupName);
		
		/** Chargement du tableau des moyens */
		TableLayout tl = (TableLayout) findViewById(R.id.tableGr);
	
	}// M�thode
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
		savedInstanceState.putBundle("GROUPE", extras);
	    super.onSaveInstanceState(savedInstanceState);
	    
	}

}// Classe