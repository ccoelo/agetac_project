/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.agetac.controller.demandeMoyens.ListenerMoyen;
import com.agetac.fragment.VueDemandeDeMoyens;

/**
* Classe AdapterButton pour VueDemandeDeMoyens : affiche la fenêtre de demande des moyens et permet de créer une liste de demandes de moyens
* et de la soumettre ensuite.
*
* @version 1.0 21/01/2013
* @author Anthony LE MEE - 10003134
*/
public class AdapterButton extends BaseAdapter {
	
	/** Instance de l'activity principale */
	private VueDemandeDeMoyens activity;
	
	/** Aller chercher dans la BDD et crééer un tableau (moyens.values est un tableau) */
	private String[] moyens;
	
	/**
	 * Constructeur AdapterButton
	 * @param vueDemandeDeMoyens Activity
	 * @param moyensGlobauxs String[] tableau des éléments à afficher
	 * @param b Button Layout des items
	 */
	public AdapterButton (VueDemandeDeMoyens vueDemandeDeMoyens, String[] moyens) {
		
		this.activity = vueDemandeDeMoyens;
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
	 * Méthode appellée afin d'obtenir un élément à la position spécifié du jeu de données
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
			btn = new Button(this.activity.getActivity());
		}
		
		// Création du boutton qui fera fois d'item dans le jeu de données
		btn.setText((String)(moyens[position]));
		btn.setTextSize(25);
		btn.setGravity(Gravity.CENTER);
		btn.setBackgroundColor(Color.parseColor("#293133"));
		//btn.setBackgroundResource(R.drawable.button_noir);
		btn.setPadding(0, 18, 0, 18);
		btn.setId(position);
		btn.setClickable(true);
		
		// Ajout du listener (Click)
		btn.setOnClickListener(new ListenerMoyen(this.activity, position));

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

