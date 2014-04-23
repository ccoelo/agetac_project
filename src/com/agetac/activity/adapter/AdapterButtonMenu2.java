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

import com.agetac.activity.VueMessageAmbiance;
import com.agetac.controller.messageAmbiance.ListenerMenuMsgAmb;

/**
* Classe AdapterButtonMenu2 pour VueMessageAmbiance : affiche le menu compos�
* des �l�ments � renseigner pour le message d'ambiance
*
* @version 1.0 29/01/2013
* @author Anthony LE MEE - 10003134
*/
public class AdapterButtonMenu2 extends BaseAdapter {
	
	/** Instance de l'activity principale */
	private Activity activity;
	
	/** Aller chercher dans la BDD et cr��er un tableau (moyens.values est un tableau) */
	private String[] moyens;
	
	/**
	 * Constructeur AdapterButtonMenu2
	 * @param a Activity
	 * @param moyensGlobauxs String[] tableau des �l�ments � afficher
	 * @param b Button Layout des items
	 */
	public AdapterButtonMenu2 (Activity a, String[] moyens) {
		
		this.activity = a;
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
	 * La m�thode ci dessous construit l'item de la ListView,
	 * qui sera � l'indice position
	 * et retourne cet item.
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
			btn = new Button(this.activity);
		}
		
		// Cr�ation du boutton qui fera fois d'item dans le jeu de donn�es
		btn.setText((String)(moyens[position]));
		btn.setTextSize(25);
		btn.setGravity(Gravity.CENTER);
		
		if (position == ((VueMessageAmbiance)this.activity).getCurrentItemSelected())
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
		btn.setOnClickListener(new ListenerMenuMsgAmb(this.activity, position));

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

} // class

