/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.controller.demandeMoyens;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.agetac.fragment.VueDemandeDeMoyens;

/**
* Classe ListenerQuantiteMoyens de VueDemandeDeMoyens : augmentation ou diminution du nombre de moyen à ajouter à la liste
* 
* @version 1.0 17/01/2013
* @author Anthony LE MEE - 10003134
*/
public class ListenerQuantiteMoyens implements OnClickListener {

	/** Instance de l'activity principale */
	private VueDemandeDeMoyens demandeDeMoyens;
	
	/**
	 * Constructeur ListenerSecteur
	 * @param a VueDemandeDeMoyens
	 */
	public ListenerQuantiteMoyens (VueDemandeDeMoyens a) {
		
		this.setDemandeDeMoyens(a);
		
	}
	
	/**
	 * Lors d'un click sur la View v
	 * @param v View
	 */
	public void onClick(View v) {
		
		// convertion de la valeur courant en int
        int nombreMoyen = Integer.parseInt(this.getDemandeDeMoyens().getEditTextNombreMoyens().getText().toString());
		
		if( this.getDemandeDeMoyens().getBouttonAjoutQuantite().getId() == ((Button)v).getId() ){
			
			// On incremente
	        nombreMoyen++;
	        
		}
		else if(this.getDemandeDeMoyens().getBouttonRetireQuantite().getId() == ((Button)v).getId() ){

			// On décrémente
	        nombreMoyen--;
			
		}
		
		// Mise à jour
        this.getDemandeDeMoyens().getEditTextNombreMoyens().setText("" + nombreMoyen + "");
		
	}

	/********************************************************************************************************/
    /** GETTEURS ET SETTEURS
    /********************************************************************************************************/
	
	/**
	 * @param demandeDeMoyens the demandeDeMoyens to set
	 */
	public void setDemandeDeMoyens(VueDemandeDeMoyens demandeDeMoyens) {
		this.demandeDeMoyens = demandeDeMoyens;
	}

	/**
	 * @return the demandeDeMoyens
	 */
	public VueDemandeDeMoyens getDemandeDeMoyens() {
		return demandeDeMoyens;
	}
	
}
