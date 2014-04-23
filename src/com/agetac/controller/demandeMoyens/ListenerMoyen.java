/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.controller.demandeMoyens;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.agetac.fragment.VueDemandeDeMoyens;

/**
* Classe ListenerMoyens de VueDemandeDeMoyens : Permet de changer le thème de l'item que 
* l'on sélectionne dans le gridView des moyens.  
*
* @version 1.0 17/01/2013
* @author Anthony LE MEE - 10003134
*/
public class ListenerMoyen implements OnClickListener {

	/** Instance de l'activity principale */
	private VueDemandeDeMoyens demandeDeMoyens;
	
	/** Position actuel du moyen sélectionné */
	private final int position; 
		  
	/**
	 * Construteur
	 * @param a VueDemandeDeMoyens de VueDemandeDeMoyens
	 * @param position int de l'item
	 * @param s savedInstanceStateDemandeMoyens de VueDemandeDeMoyens
	 * @param listMoyen GridView des moyens
	 */
	public ListenerMoyen(VueDemandeDeMoyens a, int position)  
	{  
		this.demandeDeMoyens = (VueDemandeDeMoyens)a;
		this.position = position;  
	}  
	  
	/**
	 * Lors d'un click sur la View v
	 * @param v View
	 */
	public void onClick(View v)  
	{  
		
		/* 
		 * Si on tente de sélectionner un moyen alors que l'on est en train d'éditer le champs autre moyens, on ferme alors
		 * le clavier et on doit recliquer sur un moyen pour que l'action de sélection fonctionne 
		 */
		if (this.demandeDeMoyens.getTextViewAutresMoyens().isFocused()) {
			
			// On ferme le clavier
			((InputMethodManager) demandeDeMoyens.getActivity().getSystemService(
					Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
							this.demandeDeMoyens.getTextViewAutresMoyens().getWindowToken(), 0);
			
			// On enlève le focus du champs autre moyens
			this.demandeDeMoyens.getTextViewAutresMoyens().clearFocus();
			
			// On désélectionne le champs autre moyens
			this.demandeDeMoyens.getTextViewAutresMoyens().setSelected(false);
		
		}
		else
		{
			if (v != null) {
				
				// Pour chaque moyen du GridView on les désactive 
				for (int i = 0 ; i < this.demandeDeMoyens.getGridViewChoixMoyens().getCount(); i++) {
					
					if (i != position) 
					{
						// Si ce n'est pas celui sur lequel j'ai cliqué, alors je mets le thème sombre
						((Button) this.demandeDeMoyens.getGridViewChoixMoyens().getChildAt(i)).setBackgroundColor(Color.parseColor(new String("#293133")));
	
					}
					else
					{
						// Sinon je lance la surbrillance
						((Button) this.demandeDeMoyens.getGridViewChoixMoyens().getChildAt(i)).setBackgroundColor(Color.parseColor(new String("#efefef")));
						// On met à vide le champs autre moyen - ordre important !!
						this.demandeDeMoyens.getTextViewAutresMoyens().setText("");
						// Et ensuite on set l'indice
						this.demandeDeMoyens.getSauvegarde().setIndiceMoyen(i);
	
					}
					
				}
				
			}
			
		}
		
	}

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

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}  

}
