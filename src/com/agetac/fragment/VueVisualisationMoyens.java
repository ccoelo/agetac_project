/*	
* Projet AGETAC	
* Jimmy Dano	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.agetac.R;
import com.agetac.activity.adapter.AdapterListSecteurVisu;
import com.agetac.activity.handler.UpdateView;
import com.agetac.activity.handler.Updater;
import com.agetac.activity.items.itemListVisuSecteur;
import com.agetac.activity.utils.UtilsSecteurs;
import com.agetac.dto.SecteurInterDTO;
import com.agetac.dto.dto_config.DTOFactory;
import com.agetac.model.SecteurIntervention;


/**
* Classe VueVisualisationMoyens : visualisation des moyens
*
* @version 1.0 04/04/2013
* @author Jimmy DANO - 13000159
*/
public class VueVisualisationMoyens extends Fragment implements UpdateView {
	
	/** Instance des Handler */
	Updater handler;
	
	/** Instances des modèles à utiliser */
	private SecteurInterDTO mSecteur;
	
	/** Éléments de la vue */
	private ListView 				listViewSecteurs1; 		// Première liste des secteurs
	private ListView 				listViewSecteurs2; 		// Seconde liste des secteurs
	private ListView 				listViewSecteurs3; 		// troisième liste des secteurs

	/** Données (récupérées via les modèles) à afficher dans la vue */
	private AdapterListSecteurVisu adapterListSecteur1;		// Adapter de la liste 1
	private AdapterListSecteurVisu adapterListSecteur2;		// Adapter de la liste 2
	private AdapterListSecteurVisu adapterListSecteur3;		// Adapter de la liste 3
	private ArrayList<itemListVisuSecteur> arrayListSecteur;// Array list des items de secteur 
	
	/**
	 * Méthode qui affiche un toast suite à la réception d'un message
	 * @param message
	 */
	public void onMessageReveive(String message) {
		Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
	   	/** Chargement du layout */
		View view = inflater.inflate(R.layout.maquette_visu_moyens2, container, false);
		
		/** Instanciation des handler */
		this.handler = new Updater(this);
		
		/** Instanciations des modèles */
		this.mSecteur 			= (SecteurInterDTO) DTOFactory.getSpinnerChoixSecteur();
		this.mSecteur.update 	= this.handler;
		
		/** Récupérations des données via les modèles */
		SecteurIntervention[] donneesSecteurs 	= this.mSecteur.findAll(-1);
		this.arrayListSecteur					= UtilsSecteurs.convertINTOitemSecteur(donneesSecteurs);
		ArrayList<ArrayList<itemListVisuSecteur>> split = UtilsSecteurs.splitIn(this.arrayListSecteur, 3);
		
		if (split != null && split.size() == 3) {
			
			this.adapterListSecteur1 				= new AdapterListSecteurVisu(this, android.R.layout.simple_list_item_1, split.get(0));	
			this.adapterListSecteur2 				= new AdapterListSecteurVisu(this, android.R.layout.simple_list_item_1, split.get(1));	
			this.adapterListSecteur3 				= new AdapterListSecteurVisu(this, android.R.layout.simple_list_item_1, split.get(2));	
			
		} else if (split != null && split.size() == 2) {
		
			this.adapterListSecteur1 				= new AdapterListSecteurVisu(this, android.R.layout.simple_list_item_1, split.get(0));	
			this.adapterListSecteur2 				= new AdapterListSecteurVisu(this, android.R.layout.simple_list_item_1, split.get(1));	
			
		} else if (split != null && split.size() == 1) {
		
			this.adapterListSecteur1 				= new AdapterListSecteurVisu(this, android.R.layout.simple_list_item_1, split.get(0));		
			
		}
		
		return view;
		
	}
	
	/**
	 * Méthode onCreate
	 * @param savedInstanceState Bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);
		super.onCreate(savedInstanceState);	
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
		
		// Récupération des listView
		this.setListViewSecteurs1((ListView) getActivity().findViewById(R.id.listView1Secteurs));
		this.setListViewSecteurs2((ListView) getActivity().findViewById(R.id.listView2Secteurs));
		this.setListViewSecteurs3((ListView) getActivity().findViewById(R.id.listView3Secteurs));
		
		
		
		// Création du ListView de la liste 1	 
		if (this.adapterListSecteur1 != null) getListViewSecteurs1().setAdapter(this.adapterListSecteur1);
	    
	 	// Création du ListView de la liste 2	 
		if (this.adapterListSecteur2 != null) getListViewSecteurs2().setAdapter(this.adapterListSecteur2);
	    
	 	// Création du ListView de la liste 3	 
		if (this.adapterListSecteur3 != null) getListViewSecteurs3().setAdapter(this.adapterListSecteur3);
	
	}
    
    /********************************************************************************************************/
    /** GETTEURS ET SETTEURS
    /********************************************************************************************************/

	/**
	 * @param listViewSecteurs1 the listViewSecteurs1 to set
	 */
	public void setListViewSecteurs1(ListView listViewSecteurs1) {
		this.listViewSecteurs1 = listViewSecteurs1;
	}

	/**
	 * @return the listViewSecteurs1
	 */
	public ListView getListViewSecteurs1() {
		return listViewSecteurs1;
	}

	/**
	 * @param listViewSecteurs2 the listViewSecteurs2 to set
	 */
	public void setListViewSecteurs2(ListView listViewSecteurs2) {
		this.listViewSecteurs2 = listViewSecteurs2;
	}

	/**
	 * @return the listViewSecteurs2
	 */
	public ListView getListViewSecteurs2() {
		return listViewSecteurs2;
	}

	/**
	 * @param listViewSecteurs3 the listViewSecteurs3 to set
	 */
	public void setListViewSecteurs3(ListView listViewSecteurs3) {
		this.listViewSecteurs3 = listViewSecteurs3;
	}

	/**
	 * @return the listViewSecteurs3
	 */
	public ListView getListViewSecteurs3() {
		return listViewSecteurs3;
	}

	/**
	 * @param adapterListSecteur1 the adapterListSecteur1 to set
	 */
	public void setAdapterListSecteur1(AdapterListSecteurVisu adapterListSecteur1) {
		this.adapterListSecteur1 = adapterListSecteur1;
	}

	/**
	 * @return the adapterListSecteur1
	 */
	public AdapterListSecteurVisu getAdapterListSecteur1() {
		return adapterListSecteur1;
	}

	/**
	 * @param adapterListSecteur2 the adapterListSecteur2 to set
	 */
	public void setAdapterListSecteur2(AdapterListSecteurVisu adapterListSecteur2) {
		this.adapterListSecteur2 = adapterListSecteur2;
	}

	/**
	 * @return the adapterListSecteur2
	 */
	public AdapterListSecteurVisu getAdapterListSecteur2() {
		return adapterListSecteur2;
	}

	/**
	 * @param adapterListSecteur3 the adapterListSecteur3 to set
	 */
	public void setAdapterListSecteur3(AdapterListSecteurVisu adapterListSecteur3) {
		this.adapterListSecteur3 = adapterListSecteur3;
	}

	/**
	 * @return the adapterListSecteur3
	 */
	public AdapterListSecteurVisu getAdapterListSecteur3() {
		return adapterListSecteur3;
	}

	/**
	 * @param mSecteur the mSecteur to set
	 */
	public void setmSecteur(SecteurInterDTO mSecteur) {
		this.mSecteur = mSecteur;
	}

	/**
	 * @return the mSecteur
	 */
	public SecteurInterDTO getmSecteur() {
		return mSecteur;
	}

	/**
	 * @param arrayListSecteur the arrayListSecteur to set
	 */
	public void setArrayListSecteur(ArrayList<itemListVisuSecteur> arrayListSecteur) {
		this.arrayListSecteur = arrayListSecteur;
	}

	/**
	 * @return the arrayListSecteur
	 */
	public ArrayList<itemListVisuSecteur> getArrayListSecteur() {
		return arrayListSecteur;
	}

	/**
	 * @return the handler
	 */
	public Updater getHandler() {
		return handler;
	}

	/**
	 * @param handler the handler to set
	 */
	public void setHandler(Updater handler) {
		this.handler = handler;
	}
	
}
