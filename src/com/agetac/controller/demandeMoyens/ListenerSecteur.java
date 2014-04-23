/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.controller.demandeMoyens;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

import com.agetac.fragment.VueDemandeDeMoyens;

/**
* Classe ListenerSecteur de VueDemandeDeMoyens : sélection d'un secteur => changement de la couleur des moyens
* 
* @version 1.0 17/01/2013
* @author Anthony LE MEE - 10003134
*/
public class ListenerSecteur implements AdapterView.OnItemSelectedListener, OnValueChangeListener {
		
	/** Instance de l'activity principale */
	private VueDemandeDeMoyens demandeDeMoyens;
	
	/**
	 * Constructeur ListenerSecteur
	 * @param a VueDemandeDeMoyens
	 */
	public ListenerSecteur (VueDemandeDeMoyens a) {
		
		this.demandeDeMoyens = a;
	
	}
	
	/**
	 * Méthode onItemSelected
	 * @param parent AdapterView<?>
	 * @param vue View 
	 * @param position int
	 * @param id long
	 */
	public void onItemSelected(AdapterView<?> parent, View vue, int position, long id) {
		
		// Notification du changement d'indice secteur
		this.demandeDeMoyens.getSauvegarde().setIndiceSecteur(position);
		
		// On change la couleur en fonction du secteur choisi
		try{
			
			// Pour chaque moyen du GridView
			for (int i = 0 ; i < this.demandeDeMoyens.getGridViewChoixMoyens().getCount(); i++) {
				
				// Changement de couleur du moyen
				((TextView) this.demandeDeMoyens.getGridViewChoixMoyens().getChildAt(i)).setTextColor(Color.parseColor(this.demandeDeMoyens.getDonneesCouleursSecteurs()[position]));
				// Alignement dans le TextView
				((TextView) this.demandeDeMoyens.getGridViewChoixMoyens().getChildAt(i)).setGravity(Gravity.CENTER);
								
			}

			// Changement de couleur du secteur choisi
			((TextView) this.demandeDeMoyens.getArrayAdapterSecteur().getView(position, vue, parent)).setTextColor(Color.parseColor(this.demandeDeMoyens.getDonneesCouleursSecteurs()[position]));
			
			// Changement de couleur du bouton ajouter
			this.demandeDeMoyens.getBouttonAjoutMoyenToList().setBackgroundColor(Color.parseColor(this.demandeDeMoyens.getDonneesCouleursSecteurs()[position]));
			
			// Changement de couleur du bouton +
			this.demandeDeMoyens.getBouttonAjoutQuantite().setBackgroundColor(Color.parseColor(this.demandeDeMoyens.getDonneesCouleursSecteurs()[position]));
			
			// Changement de couleur du bouton -
			this.demandeDeMoyens.getBouttonRetireQuantite().setBackgroundColor(Color.parseColor(this.demandeDeMoyens.getDonneesCouleursSecteurs()[position]));
			
		}catch(Exception e) {
			
			// Impossible de parser la couleur

		}
		
	}// Méthode onItemSelected

	/**
	 * Méthode onNothingSelected
	 * @param parent AdapterView<?>
	 */
	public void onNothingSelected(AdapterView<?> parent) {		

	}

	/**
	 * Quand le texte à changé
	 * @param picker NumberPicker
	 * @param oldVal int
	 * @param newVal int
	 */
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		
	}
	
}// Classe ListenerSecteur
