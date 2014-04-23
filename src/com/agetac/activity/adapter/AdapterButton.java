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
* Classe AdapterButton pour VueDemandeDeMoyens : affiche la fen�tre de demande des moyens et permet de cr�er une liste de demandes de moyens
* et de la soumettre ensuite.
*
* @version 1.0 21/01/2013
* @author Anthony LE MEE - 10003134
*/
public class AdapterButton extends BaseAdapter {
	
	/** Instance de l'activity principale */
	private VueDemandeDeMoyens activity;
	
	/** Aller chercher dans la BDD et cr��er un tableau (moyens.values est un tableau) */
	private String[] moyens;
	
	/**
	 * Constructeur AdapterButton
	 * @param vueDemandeDeMoyens Activity
	 * @param moyensGlobauxs String[] tableau des �l�ments � afficher
	 * @param b Button Layout des items
	 */
	public AdapterButton (VueDemandeDeMoyens vueDemandeDeMoyens, String[] moyens) {
		
		this.activity = vueDemandeDeMoyens;
		this.moyens = moyens;
		
	}

	/**
	 * R�cup�ration du nombre d'�l�ment dans l'adapter
	 * @return int
	 */
	public int getCount() {
		return moyens.length;
	}

	/**
	 * M�thode appell�e afin d'obtenir un �l�ment � la position sp�cifi� du jeu de donn�es
	 * @param position int 
	 * @param convertView View
	 * @param parent ViewGroup
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// th�me des items du jeu de donn�es
		Button btn;
		
		if (convertView != null)
	  	{
	  		btn = (Button) convertView;
	  	}
		else
		{
			btn = new Button(this.activity.getActivity());
		}
		
		// Cr�ation du boutton qui fera fois d'item dans le jeu de donn�es
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

		// On retourne le boutton cr��
		return btn;
		
	}

	/**
	 * Non impl�ment�e
	 * @param position int
	 * @return Object
	 */
	public Object getItem(int position) {
		return null;
	}

	/**
	 * Non impl�ment�e
	 * @param position int
	 * @return long
	 */
	public long getItemId(int position) {
		return 0;
	} 

}

