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
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agetac.R;
import com.agetac.activity.VueSitac;
import com.agetac.activity.items.itemListDemandeMoyens;
import com.agetac.activity.items.itemListDessins;
import com.agetac.controller.demandeMoyens.ListenerSupressionItem;
import com.agetac.view.PathOverlay;

/**
 * class AdapterListDessins : Adapter permettant de charger le layout d'un item
 * dans la liste de dessins ajoutés
 * 
 * @version 2.0 26/03/2013
 * @author Anthony LE MEE - 10003134
 * @author Christophe Coelo - 29001702
 */
public class AdapterListDessins extends ArrayAdapter<itemListDessins> {

	/** Attributs */
	private ArrayList<itemListDessins> listItems; // Liste de dessins
	private VueSitac vueSitac;

	/**
	 * Constructeur de la classe AdapterListMoyenToAsk
	 * 
	 * @param f
	 *            - context
	 * @param textViewResourceId
	 *            - ressource item
	 * @param listItems
	 *            - liste des item à afficher
	 */
	public AdapterListDessins(Fragment f, int textViewResourceId,
			ArrayList<itemListDessins> listItems) {

		super(f.getActivity().getApplicationContext(), textViewResourceId,
				listItems);
		this.listItems = listItems;
		this.setVueSitac((VueSitac) f);

	}// méthode

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// ON récupère la vue affichée
		View v = convertView;
		final int p = position;

		// Si la vue n'existe pas
		if (v == null) {

			// Alors on la créer via le layout maquette_item_moyens
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.maquette_item_dessins, null);

		}

		// On récupère les infos que l'on souhaite afficher
		final itemListDessins user = listItems.get(position);

		// Si on à bien récupéré les infos
		if (user != null) {

			// Alors on charge chacunes d'elles à leur place dans le layout
			TextView nomMoyen = (TextView) v.findViewById(R.id.nomItemDessin);
			ImageView miniature = (ImageView) v.findViewById(R.id.couleurDessin);
			Button btn = (Button) v.findViewById(R.id.suppresionItemDessin);

			// chargement du nom du moyen
			nomMoyen.setText(user.getNom());
			
			//affichage de la miniature
			switch (user.getType()){
			case 0 :
				miniature.setBackgroundColor(user.getColor());
				break;
			case 1 :
				miniature.setBackgroundResource(user.getIcon());
				break;
			case 2 :
				Drawable mini = vueSitac.getResources().getDrawable(user.getIcon());
				mini.mutate().setColorFilter(new PorterDuffColorFilter(user.getColor(),Mode.MULTIPLY));
				miniature.setBackgroundDrawable(mini);
				break;
			}
			
			
			// Ajout du listener de supression au boutton corbeille
			btn.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					if(user.getType()==0){
						vueSitac.setNbPath(vueSitac.getNbPath()-1);
					}
					vueSitac.getMapView().getOverlays().remove(p);
					vueSitac.getDonneesDessins().remove(user);
					vueSitac.getAdapterListDessin().notifyDataSetChanged();
					vueSitac.getMapView().invalidate();
				}

			});

		}// if

		return v;

	}// méthode
	
	/**
	 * @param vueSitac
	 *            the vueSitac to set
	 */
	public void setVueSitac(VueSitac vueSitac) {
		this.vueSitac = vueSitac;
	}

	/**
	 * @return the vueSitac
	 */
	public VueSitac getVueSitac() {
		return vueSitac;
	}

}// classe
