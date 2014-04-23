/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
* Classe ListenerSwitchToActivity : Permet de générer un intent sur un évenement onClick d'un item.
*
* @version 1.0 28/01/2013
* @author Anthony LE MEE - 10003134
*/
public class ListenerSwitchToActivity implements OnClickListener {

	private Activity from;
	private Class<?> to;
	
	/**
	 * Constructeur
	 * @param from activity d'où on exécute l'action
	 * @param to nom de la classe (Activity) vers laquelle on veux switcher
	 */
	public ListenerSwitchToActivity(Activity from, Class<?> to) {

		this.from = from;
		this.to = to;
	
	}

	/**
	 * Méthode déclenchée à l'appuie sur un item de type View
	 * On créer un intent suivant les données reçues et on l'exécute via un StartActivity()
	 */
	public void onClick(View arg0) {

		Intent i = new Intent(from, to);
		from.startActivity(i);
		
	}

}
