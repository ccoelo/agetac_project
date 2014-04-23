/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.adapter;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agetac.R;
import com.agetac.activity.items.itemListDemandeMoyens;
import com.agetac.controller.demandeMoyens.ListenerSupressionItem;
import com.agetac.fragment.VueDemandeDeMoyens;

/**
 * class AdapterListMoyenToAsk : Adapter permettant de charger le layout d'un item dans la liste de demande de 
 * moyens.
 * 
 * @version 2.0 08/02/2013
 * @author Anthony LE MEE - 10003134
*/
public class AdapterListVehiculeToAddOnMap extends ArrayAdapter<itemListDemandeMoyens> {
	    
	/** Attributs */
	private ArrayList<itemListDemandeMoyens> listItems; // Liste de demande de moyens
	private VueDemandeDeMoyens demandeDeMoyens;    
	
	/**
	 * Constructeur de la classe AdapterListMoyenToAsk
	 * @param f - context
	 * @param textViewResourceId - ressource item
	 * @param listItems - liste des item à afficher
	 */
	public AdapterListVehiculeToAddOnMap(Fragment f, int textViewResourceId, ArrayList<itemListDemandeMoyens> listItems) {
	
		super(f.getActivity().getApplicationContext(), textViewResourceId, listItems);
		this.listItems = listItems;
		this.demandeDeMoyens = (VueDemandeDeMoyens) f;
	    
	}// méthode
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		// ON récupère la vue affichée
		View v = convertView;
	  
		// Si la vue n'existe pas 
		if (v == null) {
	    
			// Alors on la créer via le layout maquette_item_moyens
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			v = inflater.inflate(R.layout.maquette_item_moyens, null);
	  
		}
			
		// On récupère les infos que l'on souhaite afficher
		itemListDemandeMoyens user = listItems.get(position);
		  
		// Si on à bien récupéré les infos
		if (user != null) {
	            
			// Alors on charge chacunes d'elles à leur place dans le layout
			TextView nomMoyen = (TextView) v.findViewById(R.id.nomItemMoyenVoulu);
			TextView nbrMoyen = (TextView) v.findViewById(R.id.textView1);
	        RelativeLayout l = (RelativeLayout) v.findViewById(R.id.layoutOfItemMoyenInList);
	        Button btn = (Button) v.findViewById(R.id.suppresionItem);
	        
	        // chargement du nom du moyen
	        nomMoyen.setText(user.getNom());
	        
	        // Modification de la couleur en fonction du secteur
	        l.setBackgroundColor(Color.parseColor(user.getSpinnerChoixSecteur()));
	        
	        // chargement du nombre de moyen de ce type voulu
	        nbrMoyen.setText("" + String.valueOf(user.getNombre()) + "");
	        
	        // Ajout du listener de supression au boutton corbeille
	        btn.setOnClickListener(new ListenerSupressionItem(user, this.demandeDeMoyens));
	  
		}// if

		return v;
	    
	}// méthode
	
}// classe
