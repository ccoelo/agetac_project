/*	
 * Projet AGETAC	
 * Anthony LE MEE	
 * Universite de Rennes 1	
 * ISTIC	
 */
package com.agetac.controller.editionSoiecSaoiecl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.agetac.activity.VueDefinirSoiecSaoiecl;

/**
 * Classe ListenerMenuMsgAmb pour VueDefinirSoiecSaoiecl : Listener pour le menu
 * � gauche de la maquette SOIEC/SAOIECL Il g�re la sauvegarde des donn�es
 * saisie et la r�cup�ration en fonction de l'item du menu s�lectionn�.
 * 
 * @version 1.0 27/01/2013
 * @author Anthony LE MEE - 10003134
 */
public class ListenerMenuSoiec implements OnClickListener {

	/** Instance de l'activity principale */
	private VueDefinirSoiecSaoiecl definirSoiecSaiecl;

	/** Position actuel du moyen s�lectionn� */
	private final int position;

	/**
	 * Construteur
	 * 
	 * @param a
	 *            Activity de VueDefinirSoiecSaoiecl
	 * @param position
	 *            int de l'item
	 * @param s
	 *            savedInstanceStateDemandeMoyens de VueDefinirSoiecSaoiecl
	 * @param listMoyen
	 *            GridView des moyens
	 */
	public ListenerMenuSoiec(Activity a, int position) {
		this.definirSoiecSaiecl = (VueDefinirSoiecSaoiecl) a;
		this.position = position;
	}
	

	/**
	 * Lors d'un click sur la View v
	 * 
	 * @param v
	 *            View
	 */
	public void onClick(View v) {

		/*
		 * Test OBLIGATOIRE
		 * 
		 * Evite que lorsque que l'on edite le textEdit et que l'on cherche �
		 * changer de item, que l'application �choue
		 */
		if (this.definirSoiecSaiecl.getChamps().isFocused()) {
			
			//Si le champs n'est pas vide (on y a d�j� �crit des informations) alors on affiche la textView pour les couleurs
			if(!(Html.fromHtml(this.definirSoiecSaiecl.getChamps().getText().toString()).toString().isEmpty())) refresh();

			// On ferme le clavier
			((InputMethodManager) definirSoiecSaiecl.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.definirSoiecSaiecl.getChamps().getWindowToken(), 0);
			

			// On enl�ve le focus du champs
			this.definirSoiecSaiecl.getChamps().clearFocus();

			// On d�s�lectionne le champs
			this.definirSoiecSaiecl.getChamps().setSelected(false);
			
		} else {

			if (v != null) {

				// On sauvegarde le texte not� dans le champs du menu pr�c�dent
				this.definirSoiecSaiecl.getInfosSoiecSaoiecl()[this.definirSoiecSaiecl.getCurrentItemSelected()] = ""+ this.definirSoiecSaiecl.getChamps().getText();
				
				//On raffiche l' EditText
				recall();

				// Pour chaque moyen du GridView on les d�sactive et on active que celui voulu
				for (int i = 0; i < this.definirSoiecSaiecl.getListeChamps().getCount(); i++) {

					if (i != position) {

						// Si ce n'est pas sur celui sur lequel j'ai cliqu�, alors je mets le th�me sombre
						((Button) this.definirSoiecSaiecl.getListeChamps().getChildAt(i)).setBackgroundColor(Color.parseColor(new String("#293133")));
						((Button) this.definirSoiecSaiecl.getListeChamps().getChildAt(i)).setTextColor(Color.parseColor("#e4e4e4"));
						this.definirSoiecSaiecl.setCurrentItemSelected(position);

						// On r�cup�re le texte tap� � un autre moment pour ce menu
						try {
							this.definirSoiecSaiecl.getChamps().setText(""+ this.definirSoiecSaiecl.getInfosSoiecSaoiecl()[this.definirSoiecSaiecl.getCurrentItemSelected()]);
							if(!(this.definirSoiecSaiecl.getInfosSoiecSaoiecl()[this.definirSoiecSaiecl.getCurrentItemSelected()].isEmpty())) refresh();
						} catch (NullPointerException e) {
							this.definirSoiecSaiecl.getChamps().setText("");
						}

					} else {

						// Sinon je lance la surbrillance
						((Button) this.definirSoiecSaiecl.getListeChamps().getChildAt(i)).setBackgroundColor(Color.parseColor(new String("#efefef")));
						((Button) this.definirSoiecSaiecl.getListeChamps().getChildAt(i)).setTextColor(Color.parseColor("#363636"));

					}// else

				}// for

			}// if

		}// else

	}// m�thode

	/**
	 * M�thode permettant d'afficher une texteView avec le texte du champs color�
	 */
	private void refresh() {
		//On r�cup�re le texte du champs
		String textHTML = definirSoiecSaiecl.getChamps().getText().toString();
		
		//On fait disparaitre l'EditText
		definirSoiecSaiecl.getChamps().setVisibility(View.GONE);
		
		//On remplace les sauts de ligne actuels par des sauts de lignes html
		textHTML = textHTML.replace("\n", "<br/>");
		
		//On d�finie le texte du champs comme texte de la textView en parsant les couleurs
		definirSoiecSaiecl.getTextColor().setText(Html.fromHtml(textHTML));
		
		//On affiche la textView (la scrollView est utile lorsque le texte du champs est trop long pour etre affich� en entier sur la tablette)
		definirSoiecSaiecl.getScrollView().setVisibility(View.VISIBLE);
		definirSoiecSaiecl.getTextColor().setVisibility(View.VISIBLE);
		
		//On affiche le bouton pour editer le texte
		definirSoiecSaiecl.getEditer().setVisibility(View.VISIBLE);

	}

	/**
	 * M�thode permettant de revenir sur l'editText afin de modifier le texte du champs
	 */
	private void recall() {
		
		//On fait disparaitre la textView ainsi que le bouton d'�dition du texte
		definirSoiecSaiecl.getScrollView().setVisibility(View.GONE);
		definirSoiecSaiecl.getTextColor().setVisibility(View.GONE);
		definirSoiecSaiecl.getEditer().setVisibility(View.GONE);
		
		//On affiche l'editText
		definirSoiecSaiecl.getChamps().setVisibility(View.VISIBLE);

	}

	/********************************************************************************************************/
	/**
	 * GETTEURS ET SETTEURS /
	 ********************************************************************************************************/

	/**
	 * @param definirSoiecSaiecl
	 *            the definirSoiecSaiecl to set
	 */
	public void setDemandeDeMoyens(VueDefinirSoiecSaoiecl definirSoiecSaiecl) {
		this.definirSoiecSaiecl = definirSoiecSaiecl;
	}

	/**
	 * @return the definirSoiecSaiecl
	 */
	public VueDefinirSoiecSaoiecl getDemandeDeMoyens() {
		return definirSoiecSaiecl;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

}
