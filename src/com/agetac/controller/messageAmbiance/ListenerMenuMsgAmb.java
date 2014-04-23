/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.controller.messageAmbiance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.agetac.activity.VueMessageAmbiance;

/**
* Classe ListenerMenuMsgAmb pour VueMessageAmbiance : Listener pour le menu � gauche de la maquette message 
* d'ambiance. Il g�re la sauvegarde des donn�es saisies et la r�cup�ration en fonction de l'item du menu
* s�lectionn�.
*
* @version 1.0 27/01/2013
* @author Christophe Coelo - 29001702
*/
public class ListenerMenuMsgAmb implements OnClickListener{

	/** Instance de l'activity principale */
	private VueMessageAmbiance definirMsgAmbiance;
	
	/** Position actuel du moyen s�lectionn� */
	private final int position; 
		  
	/**
	 * Construteur
	 * @param a Activity de VueMessageAmbiance
	 * @param position int de l'item
	 * @param s savedInstanceStateDemandeMoyens de VueMessageAmbiance
	 * @param listMoyen GridView des moyens
	 */
	public ListenerMenuMsgAmb(Activity a, int position)  
	{  
		this.definirMsgAmbiance = (VueMessageAmbiance)a;
		this.position = position;  
	}  
	  
	/**
	 * Lors d'un click sur la View v
	 * @param v View
	 */
	public void onClick(View v)  
	{  
		
		/*
		 *  Test OBLIGATOIRE
		 *  
		 *  Evite que lorsque que l'on edite le textEdit et que l'on cherche � changer de 
		 *  item, que l'application �choue
		 */
		if (this.definirMsgAmbiance.getChamps().isFocused()) {
			
			// On ferme le clavier
			((InputMethodManager) definirMsgAmbiance.getSystemService(
					Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
							this.definirMsgAmbiance.getChamps().getWindowToken(), 0);
			
			// On enl�ve le focus du champs
			this.definirMsgAmbiance.getChamps().clearFocus();
			
			// On d�s�lectionne le champs
			this.definirMsgAmbiance.getChamps().setSelected(false);
			
		}
		else
		{
		
			if (v != null) {
				
				// On sauvegarde le texte not� dans le champs du menu pr�c�dent
				this.definirMsgAmbiance.getInfosMessageAmbiance()[this.definirMsgAmbiance.getCurrentItemSelected()] = 
					"" + this.definirMsgAmbiance.getChamps().getText();	
				
				// Pour chaque moyen du GridView on les d�sactive et on active que celui voulu
				for (int i = 0 ; i < this.definirMsgAmbiance.getListeChamps().getCount(); i++) {
					
					if (i != position) 
					{
						
						// Si ce n'est pas sur celui sur lequel j'ai cliqu�, alors je mets le th�me sombre
						((Button) this.definirMsgAmbiance.getListeChamps().getChildAt(i)).setBackgroundColor(Color.parseColor(new String("#293133")));
						((Button) this.definirMsgAmbiance.getListeChamps().getChildAt(i)).setTextColor(Color.parseColor("#e4e4e4"));
						this.definirMsgAmbiance.setCurrentItemSelected(position);
						
						// On r�cup�re le texte tap� � un autre moment pour ce menu
						try {
							this.definirMsgAmbiance.getChamps().setText(
								"" + this.definirMsgAmbiance.getInfosMessageAmbiance()[this.definirMsgAmbiance.getCurrentItemSelected()]);
						}
						catch(NullPointerException e) {
							this.definirMsgAmbiance.getChamps().setText("");
						}
						
					}
					else
					{
						
						// Sinon je lance la surbrillance
						((Button) this.definirMsgAmbiance.getListeChamps().getChildAt(i)).setBackgroundColor(Color.parseColor(new String("#efefef")));
						((Button) this.definirMsgAmbiance.getListeChamps().getChildAt(i)).setTextColor(Color.parseColor("#363636"));
	
					}// else
					
				}// for
				
			}// if
			
		}// else
		
	}// m�thode
	
    /********************************************************************************************************/
    /** GETTEURS ET SETTEURS
    /********************************************************************************************************/

	/**
	 * @param definirMsgAmbiance the definirMsgAmbiance to set
	 */
	public void setDemandeDeMoyens(VueMessageAmbiance definirMsgAmbiance) {
		this.definirMsgAmbiance = definirMsgAmbiance;
	}

	/**
	 * @return the definirMsgAmbiance
	 */
	public VueMessageAmbiance getDemandeDeMoyens() {
		return definirMsgAmbiance;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}  
	
}
