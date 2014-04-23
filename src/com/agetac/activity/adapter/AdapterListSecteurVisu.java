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
import android.widget.TextView;

import com.agetac.R;
import com.agetac.activity.items.itemListVisuSecteur;
import com.agetac.controller.ListenerListeMoyens;
import com.agetac.dto.dto_config.DTO;
import com.agetac.dto.dto_config.DTOFactory;
import com.agetac.fragment.VueVisualisationMoyens;
import com.agetac.model.Vehicule;


/**
 * class AdapterListSecteurVisu : Adapter permettant de charger le layout d'un item dans la liste de visualisation
 * de moyen
 * 
 * @version 2.0 08/02/2013
 * @author Anthony LE MEE - 10003134
*/
public class AdapterListSecteurVisu extends ArrayAdapter<itemListVisuSecteur> {
	    
	/** Attributs */
	private ArrayList<itemListVisuSecteur> listItems; // Liste de demande de moyens
	private VueVisualisationMoyens visuMoyen;    
	
	/**
	 * Constructeur de la classe AdapterListSecteurVisu
	 * @param f - context
	 * @param textViewResourceId - ressource item
	 * @param listItems - liste des item � afficher
	 */
	public AdapterListSecteurVisu(Fragment f, int textViewResourceId, ArrayList<itemListVisuSecteur> listItems) {
	
		super(f.getActivity().getApplicationContext(), textViewResourceId, listItems);
		this.listItems = listItems;
		this.setVisuMoyen((VueVisualisationMoyens) f);
	    
	}// m�thode
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		// ON r�cup�re la vue affich�e
		View v = convertView;
	  
		// Si la vue n'existe pas 
		if (v == null) {
	    
			// Alors on la cr�er via le layout maquette_item_moyens
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			v = inflater.inflate(R.layout.maquette_detail_secteur, null);
	  
		}
			
		// On r�cup�re les infos que l'on souhaite afficher
		itemListVisuSecteur user = listItems.get(position);
		  
		// Si on � bien r�cup�r� les infos
		if (user != null) {
	            
			// Alors on charge chacunes d'elles � leur place dans le layout
			TextView titre 			= (TextView) v.findViewById(R.id.titre_secteur);
			TextView infosActuelles = (TextView) v.findViewById(R.id.info_secteur);
			TextView nbSurLieux		= (TextView) v.findViewById(R.id.secteur_lieux);
			TextView nbPartis		= (TextView) v.findViewById(R.id.secteur_parti);
	        
			// Si les donn�es nbr v�hicules ne sont pas encore r�cup�r�es, alors on s'en charge maintenant
			if ( user.getNombreSurLieux() == -1 || user.getNombrePartis() == -1 ) 
			{
				DTO<Vehicule> ve = DTOFactory.getVehicule();
				ve.update = visuMoyen.getHandler();
				
				user.setNombreSurLieux(ve.find(user.getId(),true));
				user.setNombrePartis(ve.find(user.getId(),false));
			}
			
	        // chargement du nom du secteur
	        titre.setText(user.getNom());
	        
	        // Modification de la couleur en fonction du secteur
	        titre.setBackgroundColor(Color.parseColor(user.getColor()));
	        
	        // Chargement dans le sous-titre
	        infosActuelles.setText("Infos actuelles du secteur " + user.getNom());
	        
	        // Chargement des infos sur les v�hicules sur les lieux
	        nbSurLieux.setText(" " + user.getNombreSurLieux() + " v�hicules sur les lieux");
	        
	        // Chargement des infos sur les v�hicules sur les partis
	        nbPartis.setText(" " + user.getNombrePartis() + " v�hicules partis");
	       
	        // Chargement du listener
	        v.setOnClickListener(new ListenerListeMoyens(titre,this.visuMoyen));
	        
		}// if

		return v;
	    
	}// m�thode

	/**
	 * @param visuMoyen the visuMoyen to set
	 */
	public void setVisuMoyen(VueVisualisationMoyens visuMoyen) {
		this.visuMoyen = visuMoyen;
	}

	/**
	 * @return the visuMoyen
	 */
	public VueVisualisationMoyens getVisuMoyen() {
		return visuMoyen;
	}
	
}// classe
