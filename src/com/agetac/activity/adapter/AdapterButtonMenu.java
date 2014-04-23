/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.agetac.activity.VueDefinirSoiecSaoiecl;
import com.agetac.controller.editionSoiecSaoiecl.ListenerMenuSoiec;

/**
* Classe AdapterButtonMenu pour VueDefinirSoiecSaoiecl : affiche le menu composé
* des éléments à renseigner pour le SOIEC/SAOIECL
*
* @version 1.0 21/01/2013
* @author Anthony LE MEE - 10003134
*/
public class AdapterButtonMenu extends BaseAdapter {
	
	/** Instance de l'activity principale */
	private Activity activity;
	
	/** Aller chercher dans la BDD et crééer un tableau (moyens.values est un tableau) */
	private String[] moyens;
	
	/**
	 * Constructeur AdapterButtonMoyen
	 * @param a Activity
	 * @param moyensGlobauxs String[] tableau des éléments à afficher
	 * @param b Button Layout des items
	 */
	public AdapterButtonMenu (Activity a, String[] moyens) {
		
		this.activity = a;
		this.moyens = moyens;
		
	}

	/**
	 * Récupération du nombre d'élément dans l'adapter
	 * @return int
	 */
	public int getCount() {
		return moyens.length;
	}

	/**
	 * La méthode ci dessous construit l'item de la ListView,
	 * qui sera à l'indice position
	 * et retourne cet item.
	 * @param position int 
	 * @param convertView View
	 * @param parent ViewGroup
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// thème des items du jeu de données
		Button btn;
		
		if (convertView != null)
	  	{
	  		btn = (Button) convertView;
	  	}
		else
		{
			btn = new Button(this.activity);
		}
		
		// Création du boutton qui fera fois d'item dans le jeu de données
		btn.setText((String)(moyens[position]));
		btn.setTextSize(25);
		btn.setGravity(Gravity.CENTER);
		
		if (position == ((VueDefinirSoiecSaoiecl)this.activity).getCurrentItemSelected())
		{
			btn.setBackgroundColor(Color.parseColor(new String("#efefef")));
			btn.setTextColor(Color.parseColor("#363636"));
		}
		else
		{
			btn.setBackgroundColor(Color.parseColor("#293133"));
			btn.setTextColor(Color.parseColor("#e4e4e4"));
		}
		
		btn.setPadding(0, 18, 0, 18);
		btn.setId(position);
		btn.setClickable(true);
		
		// Ajout du listener (Click)
		btn.setOnClickListener(new ListenerMenuSoiec(this.activity, position));

		// On retourne le boutton créé
		return btn;
		
	}

	/**
	 * Non implémentée
	 * @param position int
	 * @return Object
	 */
	public Object getItem(int position) {
		return null;
	}

	/**
	 * Non implémentée
	 * @param position int
	 * @return long
	 */
	public long getItemId(int position) {
		return 0;
	}

}

