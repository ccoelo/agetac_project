/*	
 * Projet AGETAC	
 * Anthony LE MEE	
 * Universite de Rennes 1	
 * ISTIC	
 */
package com.agetac.controller.editionSoiecSaoiecl;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.agetac.activity.VueDefinirSoiecSaoiecl;

/**
 * Classe ListenerRadioCouleur pour VueDefinirSoiecSaoiecl : Listener pour le
 * menu en haut de la maquette SOIEC/SAOIECL Il gère le changement de couleur de
 * police dans le textEdit courant.
 * 
 * @version 1.0 27/01/2013
 * @author Anthony LE MEE - 10003134
 */
public class ListenerRadioCouleur implements OnCheckedChangeListener {

	/** Instance de la classe principale */
	VueDefinirSoiecSaoiecl vueDefinirSoiecSaoiecl;

	/**
	 * Constructeur
	 * 
	 * @param vueDefinirSoiecSaoiecl
	 */
	public ListenerRadioCouleur(VueDefinirSoiecSaoiecl vueDefinirSoiecSaoiecl) {
		this.vueDefinirSoiecSaoiecl = vueDefinirSoiecSaoiecl;
	}

	/**
	 * Lorsque je change de couleur
	 */
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		//On verifie que l'utilisateur soit bien en train de taper du texte dans l'editText
		if(vueDefinirSoiecSaoiecl.getChamps().isFocused()){
			
			// On cherche la couleur sélectionnée
			for (int i = 0; i < group.getChildCount(); i++) {
	
				if (group.getChildAt(i).getId() == checkedId) {
					//On recupère la position du curseur
					int posCursorInit = this.vueDefinirSoiecSaoiecl.getChamps().getSelectionStart();
					
					//On split le texte par rapport à la position du curseur
					String split1 = (String) this.vueDefinirSoiecSaoiecl.getChamps().getText().toString().substring(0,this.vueDefinirSoiecSaoiecl.getChamps().getSelectionStart());
					String split2 = (String) this.vueDefinirSoiecSaoiecl.getChamps().getText().toString().substring(this.vueDefinirSoiecSaoiecl.getChamps().getSelectionStart());
					
					//On remet le focus sur l'editTexte
					this.vueDefinirSoiecSaoiecl.getChamps().requestFocus();
					
					Toast.makeText(this.vueDefinirSoiecSaoiecl.getApplicationContext(),"Nouvelle couleur", Toast.LENGTH_SHORT).show();
	
					if (group.getChildAt(i).getId() == vueDefinirSoiecSaoiecl.getRadioRouge()) {
						//On insère la couleur sous forme de balise HTML
						this.vueDefinirSoiecSaoiecl.getChamps().setText(split1 + "<font color=red></font>" + split2);

						//On repositionne le curseur entre les deux balises puis on quitte la boucle
						this.vueDefinirSoiecSaoiecl.getChamps().setSelection(posCursorInit + "<font color=red></font>".length()- 7);
						break;
	
					}
	
					if (group.getChildAt(i).getId() == vueDefinirSoiecSaoiecl.getRadioBlanc()) {
						//On insère la couleur sous forme de balise HTML
						this.vueDefinirSoiecSaoiecl.getChamps().setText(split1 + "<font color=white></font>" + split2);
						
						//On repositionne le curseur entre les deux balises puis on quitte la boucle
						this.vueDefinirSoiecSaoiecl.getChamps().setSelection(posCursorInit+ "<font color=white></font>".length() - 7);
						break;
	
					}
	
					if (group.getChildAt(i).getId() == vueDefinirSoiecSaoiecl.getRadioBleu()) {
						//On insère la couleur sous forme de balise HTML
						this.vueDefinirSoiecSaoiecl.getChamps().setText(split1 + "<font color=blue></font>" + split2);

						//On repositionne le curseur entre les deux balises puis on quitte la boucle
						this.vueDefinirSoiecSaoiecl.getChamps().setSelection(posCursorInit + "<font color=blue></font>".length()- 7);
						break;
	
					}
	
					if (group.getChildAt(i).getId() == vueDefinirSoiecSaoiecl.getRadioVert()) {
						//On insère la couleur sous forme de balise HTML
						this.vueDefinirSoiecSaoiecl.getChamps().setText(split1 + "<font color=green></font>" + split2);

						//On repositionne le curseur entre les deux balises puis on quitte la boucle
						this.vueDefinirSoiecSaoiecl.getChamps().setSelection(posCursorInit+ "<font color=green></font>".length() - 7);
						break;
	
					}
	
					if (group.getChildAt(i).getId() == vueDefinirSoiecSaoiecl.getRadioViolet()) {
						//On insère la couleur sous forme de balise HTML
						this.vueDefinirSoiecSaoiecl.getChamps().setText(split1 + "<font color=purple></font>" + split2);

						//On repositionne le curseur entre les deux balises puis on quitte la boucle
						this.vueDefinirSoiecSaoiecl.getChamps().setSelection(posCursorInit+ "<font color=purple></font>".length() - 7);
						break;
	
					}
	
				}// if
	
			}// for
		
		}

	}// méthode

}// classe
