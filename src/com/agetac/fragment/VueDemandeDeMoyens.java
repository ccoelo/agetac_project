/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.agetac.R;
import com.agetac.activity.adapter.AdapterButton;
import com.agetac.activity.adapter.AdapterListMoyenToAsk;
import com.agetac.activity.filter.FilterInputNumber;
import com.agetac.activity.handler.UpdateView;
import com.agetac.activity.handler.Updater;
import com.agetac.activity.items.itemListDemandeMoyens;
import com.agetac.activity.savedInstanceState.savedInstanceStateDemandeMoyens;
import com.agetac.activity.utils.UtilsSecteurs;
import com.agetac.activity.utils.UtilsTypesMoyens;
import com.agetac.controller.demandeMoyens.ListenerAjoutToList;
import com.agetac.controller.demandeMoyens.ListenerAutresMoyens;
import com.agetac.controller.demandeMoyens.ListenerQuantiteMoyens;
import com.agetac.controller.demandeMoyens.ListenerSecteur;
import com.agetac.dto.SecteurInterDTO;
import com.agetac.dto.TypesMoyensDTO;
import com.agetac.model.SecteurIntervention;
import com.agetac.model.TypesMoyens;

/**
* Classe VueDemandeDeMoyens : affiche la fenêtre de demande des moyens et permet de créer une liste de demandes de moyens
* et de la soumettre ensuite.
* 
* @version 2.0 08/02/2013
* @author Anthony LE MEE - 10003134
*/
public class VueDemandeDeMoyens extends Fragment implements UpdateView {
	
	/** Instance des Handler */
	Updater handler;
	
	/** Instances des modèles à utiliser */
	private TypesMoyensDTO 	mTypesMoyens;
	private SecteurInterDTO mSecteur;
	
	/** Instances des controlers à utiliser */
	private ListenerAjoutToList 	cAjoutToList;				// Listener (Controler) sur le boutton d'ajout à la liste des moyens
	private ListenerQuantiteMoyens 	cQuantiteMoyens;			// Listener (Controler) sur le choix de la quantité de moyens voulu via les boutons +/-
	private ListenerSecteur 		cSecteur;					// Listener (Controler) sur le choix d'un secteur via la liste déroulante de secteurs
	private ListenerAutresMoyens 	cAutresMoyens;				// Listener (Controler) sur le choix d'un autre moyen via le champs autre moyens
	
	/** Éléments de la vue */
	private GridView 				gridViewChoixMoyens; 		// GridView correspondant à l'ensemble des moyens
	private ArrayAdapter<String> 	arrayAdapterSecteur; 		// ArrayAdapter qui fera office de liste déroulante de secteurs 
	private AutoCompleteTextView 	textViewAutresMoyens; 		// Champs d'auto-complétion pour la recherche d'un autre moyen 
	private Button 					bouttonAjoutMoyenToList; 	// Boutton d'ajout du moyen à la liste 
	private Button 					bouttonAjoutQuantite; 		// Augmente le nombre de moyens à ajouter 
	private Button 					bouttonRetireQuantite; 		// Diminue le nombre de moyens à ajouter 
	private EditText 				editTextNombreMoyens; 		// Nombre de ce moyen à ajouter à la liste 
	private Spinner 				spinnerChoixSecteur; 		// Liste déroulante des secteurs
	private ListView 				listViewMoyensToSend; 		// Liste des éléments ajouté et que l'on va envoyer au serveur 
	
	/** Données de sauvegarde au flip orientation */
	private savedInstanceStateDemandeMoyens sauvegarde = savedInstanceStateDemandeMoyens.getInstance(); // LastSave
	
	/** Données (récupérées via les modèles) à afficher dans la vue */
	private String[]							donneesNomsAllMoyens;		// Liste des noms des moyens que l'on chargera dans la vue
	private String[] 							donneesNomsUsestMoyens;		// Liste (limitée au plus utilisés) des noms des moyens que l'on chargera dans la vue
	private String[] 							donneesNomsSecteurs;		// Liste des noms des secteurs d'interventions que l'on chargera dans la vue
	private String[] 							donneesCouleursSecteurs;	// Liste des couleurs des secteurs d'interventions que l'on utilisera dans la vue
	private ArrayList<itemListDemandeMoyens>	donneesMoyensAddedToList;   // Liste des moyens ajoutés à la liste de demande de moyens
	private AdapterListMoyenToAsk 				adapterListToSend;			// Liste des moyens de la liste de demande de moyens
	
	/**
	 * Méthode qui affiche un toast suite à la réception d'un message
	 * @param message
	 */
	public void onMessageReveive(String message) {
		Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	/** Méthode onCreate */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		setRetainInstance(true);
		super.onCreate(savedInstanceState);
		
	}
	
	/** Méthode onCreateView */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
	   	/** Chargement du layout */
		View view = inflater.inflate(R.layout.maquette_demande_de_moyen, container, false);
		
		/** Instanciation des handler */
		this.handler = new Updater(this);
		
		/** Instanciations des contrôlers */
		this.cAjoutToList 		= new ListenerAjoutToList(this);
		this.cQuantiteMoyens 	= new ListenerQuantiteMoyens(this);
		this.cSecteur 			= new ListenerSecteur(this);
		this.cAutresMoyens 		= new ListenerAutresMoyens(this);
		
		/** Instanciations des modèles */
		this.mTypesMoyens 		= new TypesMoyensDTO();
		this.mTypesMoyens.update= this.handler;
		this.mSecteur 			= new SecteurInterDTO();
		this.mSecteur.update 	= this.handler;
		
		
		/** Récupérations des données via les modèles */
		SecteurIntervention[] donneesSecteurs 	= this.mSecteur.findAll(-1);
		TypesMoyens[] donneesMoyensAll 			= this.mTypesMoyens.findAll(-1);
		TypesMoyens[] donneesMoyensUsest 		= this.mTypesMoyens.findAll(15);
		
		/** Chargements des données dans les attributs correspondants */
		this.donneesNomsSecteurs 		= UtilsSecteurs.getNamesOfSecteurs(donneesSecteurs);
		this.donneesCouleursSecteurs 	= UtilsSecteurs.getColorsOfSecteurs(donneesSecteurs);
		this.donneesNomsAllMoyens 		= UtilsTypesMoyens.getNamesOfTypesMoyens(donneesMoyensAll);
		this.donneesNomsUsestMoyens 	= UtilsTypesMoyens.getNamesOfTypesMoyens(donneesMoyensUsest);
		this.donneesMoyensAddedToList	= new ArrayList<itemListDemandeMoyens>();
		this.adapterListToSend 			= new AdapterListMoyenToAsk(this, android.R.layout.simple_list_item_1, this.donneesMoyensAddedToList);		
		
		/** LOG */
		Log.d("Antho",  "Instanciations faites");
		
		return view;
		
	}
	
	/** Méthode onActivityCreated */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);			
		
		// Récupération d'une sauvegarde pré-existante
		if (savedInstanceState != null) {
	        sauvegarde = (savedInstanceStateDemandeMoyens) savedInstanceState.getSerializable("sauvegarde");
	        Log.d("Antho",  sauvegarde.getIndiceMoyen() + "");
	    }
					
		// Récupération des éléments 
		spinnerChoixSecteur 	= (Spinner) getActivity().findViewById(R.id.choix);
		bouttonAjoutMoyenToList = (Button) getActivity().findViewById(R.id.bouttonAjoutToList);
		bouttonAjoutQuantite 	= (Button) getActivity().findViewById(R.id.bouttonAjoutQuantite);
		bouttonRetireQuantite 	= (Button) getActivity().findViewById(R.id.bouttonEnleveQuantite);
		textViewAutresMoyens 	= (AutoCompleteTextView) getActivity().findViewById(R.id.AutreMoyens);
		editTextNombreMoyens 	= (EditText) getActivity().findViewById(R.id.quantiteeMoyens);
		gridViewChoixMoyens 	= (GridView) getActivity().findViewById(R.id.choixMoyens);
		listViewMoyensToSend	= (ListView) getActivity().findViewById(R.id.listMoyensToSend);
		
		// Ajout listener sur le boutton d'ajout à la liste des moyens
		bouttonAjoutMoyenToList.setOnClickListener(this.cAjoutToList);
		
		// Ajout du filtre de domaine sur le champs numerique de quantite de moyens
		editTextNombreMoyens.setFilters(new InputFilter[]{new FilterInputNumber("1", "100")});
		
		// Ajout listener sur + et -
		getBouttonAjoutQuantite().setOnClickListener(cQuantiteMoyens);
		getBouttonRetireQuantite().setOnClickListener(cQuantiteMoyens);
					
		// Création de la liste déroulante		
		setArrayAdapterSecteur(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, this.donneesNomsSecteurs));
		getArrayAdapterSecteur().setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerChoixSecteur.setOnItemSelectedListener(cSecteur);
		spinnerChoixSecteur.setAdapter(getArrayAdapterSecteur());
		
		// Creation de la grille de moyens
		getGridViewChoixMoyens().setAdapter(new AdapterButton(this, this.donneesNomsUsestMoyens)); 
		
		// Création du champs d'auto-complétion pour la recherche de d'autres moyens
	    getTextViewAutresMoyens().addTextChangedListener(cAutresMoyens);
	    getTextViewAutresMoyens().setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, this.donneesNomsAllMoyens));
	    getTextViewAutresMoyens().setOnItemClickListener(cAutresMoyens);
	    
	    /*
	     * Remise en état suivant la sauvegarde
	     */
	    
	    // On met à jour le secteur sélectionné avec celui contenu dans la sauvegarde
	    this.spinnerChoixSecteur.setSelection(sauvegarde.getIndiceSecteur());
	    
	    // Liste des moyens demandés : Si la sauvegarde a du contenu qui nous manque alors on la charge
	    if (this.donneesMoyensAddedToList.size() < sauvegarde.getDonneesMoyensAddedToList().size()) {
	    	this.donneesMoyensAddedToList 	= sauvegarde.getDonneesMoyensAddedToList();
	    	this.adapterListToSend 			= new AdapterListMoyenToAsk(this, android.R.layout.simple_dropdown_item_1line, this.donneesMoyensAddedToList);
	    }
	
	 	// Création du ListView de la liste de moyens demandés	 
	    getListViewMoyensToSend().setAdapter(this.adapterListToSend);
	    
	}
	
	/** Méthode onSaveInstanceState */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
		/** LOG */
		Log.d("Antho", "Sauvegarde faite : " + sauvegarde + "");
		
		/** Sauvegarde */
		savedInstanceState.putSerializable("sauvegarde", sauvegarde);
	    super.onSaveInstanceState(savedInstanceState);
	    
	}
    
    /********************************************************************************************************/
    /** GETTEURS ET SETTEURS
    /********************************************************************************************************/

	/**
	 * @return the mTypesMoyens
	 */
	public TypesMoyensDTO getmTypesMoyens() {
		return mTypesMoyens;
	}

	/**
	 * @param mTypesMoyens the mTypesMoyens to set
	 */
	public void setmTypesMoyens(TypesMoyensDTO mTypesMoyens) {
		this.mTypesMoyens = mTypesMoyens;
	}

	/**
	 * @return the mSecteur
	 */
	public SecteurInterDTO getmSecteur() {
		return mSecteur;
	}

	/**
	 * @param mSecteur the mSecteur to set
	 */
	public void setmSecteur(SecteurInterDTO mSecteur) {
		this.mSecteur = mSecteur;
	}

	/**
	 * @return the cAjoutToList
	 */
	public ListenerAjoutToList getcAjoutToList() {
		return cAjoutToList;
	}

	/**
	 * @param cAjoutToList the cAjoutToList to set
	 */
	public void setcAjoutToList(ListenerAjoutToList cAjoutToList) {
		this.cAjoutToList = cAjoutToList;
	}

	/**
	 * @return the cQuantiteMoyens
	 */
	public ListenerQuantiteMoyens getcQuantiteMoyens() {
		return cQuantiteMoyens;
	}

	/**
	 * @param cQuantiteMoyens the cQuantiteMoyens to set
	 */
	public void setcQuantiteMoyens(ListenerQuantiteMoyens cQuantiteMoyens) {
		this.cQuantiteMoyens = cQuantiteMoyens;
	}

	/**
	 * @return the cSecteur
	 */
	public ListenerSecteur getcSecteur() {
		return cSecteur;
	}

	/**
	 * @param cSecteur the cSecteur to set
	 */
	public void setcSecteur(ListenerSecteur cSecteur) {
		this.cSecteur = cSecteur;
	}

	/**
	 * @return the cAutresMoyens
	 */
	public ListenerAutresMoyens getcAutresMoyens() {
		return cAutresMoyens;
	}

	/**
	 * @param cAutresMoyens the cAutresMoyens to set
	 */
	public void setcAutresMoyens(ListenerAutresMoyens cAutresMoyens) {
		this.cAutresMoyens = cAutresMoyens;
	}

	/**
	 * @return the gridViewChoixMoyens
	 */
	public GridView getGridViewChoixMoyens() {
		return gridViewChoixMoyens;
	}

	/**
	 * @param gridViewChoixMoyens the gridViewChoixMoyens to set
	 */
	public void setGridViewChoixMoyens(GridView gridViewChoixMoyens) {
		this.gridViewChoixMoyens = gridViewChoixMoyens;
	}

	/**
	 * @return the arrayAdapterSecteur
	 */
	public ArrayAdapter<String> getArrayAdapterSecteur() {
		return arrayAdapterSecteur;
	}

	/**
	 * @param arrayAdapterSecteur the arrayAdapterSecteur to set
	 */
	public void setArrayAdapterSecteur(ArrayAdapter<String> arrayAdapterSecteur) {
		this.arrayAdapterSecteur = arrayAdapterSecteur;
	}

	/**
	 * @return the textViewAutresMoyens
	 */
	public AutoCompleteTextView getTextViewAutresMoyens() {
		return textViewAutresMoyens;
	}

	/**
	 * @param textViewAutresMoyens the textViewAutresMoyens to set
	 */
	public void setTextViewAutresMoyens(AutoCompleteTextView textViewAutresMoyens) {
		this.textViewAutresMoyens = textViewAutresMoyens;
	}

	/**
	 * @return the bouttonAjoutMoyenToList
	 */
	public Button getBouttonAjoutMoyenToList() {
		return bouttonAjoutMoyenToList;
	}

	/**
	 * @param bouttonAjoutMoyenToList the bouttonAjoutMoyenToList to set
	 */
	public void setBouttonAjoutMoyenToList(Button bouttonAjoutMoyenToList) {
		this.bouttonAjoutMoyenToList = bouttonAjoutMoyenToList;
	}

	/**
	 * @return the bouttonAjoutQuantite
	 */
	public Button getBouttonAjoutQuantite() {
		return bouttonAjoutQuantite;
	}

	/**
	 * @param bouttonAjoutQuantite the bouttonAjoutQuantite to set
	 */
	public void setBouttonAjoutQuantite(Button bouttonAjoutQuantite) {
		this.bouttonAjoutQuantite = bouttonAjoutQuantite;
	}

	/**
	 * @return the bouttonRetireQuantite
	 */
	public Button getBouttonRetireQuantite() {
		return bouttonRetireQuantite;
	}

	/**
	 * @param bouttonRetireQuantite the bouttonRetireQuantite to set
	 */
	public void setBouttonRetireQuantite(Button bouttonRetireQuantite) {
		this.bouttonRetireQuantite = bouttonRetireQuantite;
	}

	/**
	 * @return the editTextNombreMoyens
	 */
	public EditText getEditTextNombreMoyens() {
		return editTextNombreMoyens;
	}

	/**
	 * @param editTextNombreMoyens the editTextNombreMoyens to set
	 */
	public void setEditTextNombreMoyens(EditText editTextNombreMoyens) {
		this.editTextNombreMoyens = editTextNombreMoyens;
	}

	/**
	 * @return the spinnerChoixSecteur
	 */
	public Spinner getSpinnerChoixSecteur() {
		return spinnerChoixSecteur;
	}

	/**
	 * @param spinnerChoixSecteur the spinnerChoixSecteur to set
	 */
	public void setSpinnerChoixSecteur(Spinner spinnerChoixSecteur) {
		this.spinnerChoixSecteur = spinnerChoixSecteur;
	}

	/**
	 * @return the listViewMoyensToSend
	 */
	public ListView getListViewMoyensToSend() {
		return listViewMoyensToSend;
	}

	/**
	 * @param listViewMoyensToSend the listViewMoyensToSend to set
	 */
	public void setListViewMoyensToSend(ListView listViewMoyensToSend) {
		this.listViewMoyensToSend = listViewMoyensToSend;
	}

	/**
	 * @return the sauvegarde
	 */
	public savedInstanceStateDemandeMoyens getSauvegarde() {
		return sauvegarde;
	}

	/**
	 * @param sauvegarde the sauvegarde to set
	 */
	public void setSauvegarde(savedInstanceStateDemandeMoyens sauvegarde) {
		this.sauvegarde = sauvegarde;
	}

	/**
	 * @return the donneesNomsAllMoyens
	 */
	public String[] getDonneesNomsAllMoyens() {
		return donneesNomsAllMoyens;
	}

	/**
	 * @param donneesNomsAllMoyens the donneesNomsAllMoyens to set
	 */
	public void setDonneesNomsAllMoyens(String[] donneesNomsAllMoyens) {
		this.donneesNomsAllMoyens = donneesNomsAllMoyens;
	}

	/**
	 * @return the donneesNomsUsestMoyens
	 */
	public String[] getDonneesNomsUsestMoyens() {
		return donneesNomsUsestMoyens;
	}

	/**
	 * @param donneesNomsUsestMoyens the donneesNomsUsestMoyens to set
	 */
	public void setDonneesNomsUsestMoyens(String[] donneesNomsUsestMoyens) {
		this.donneesNomsUsestMoyens = donneesNomsUsestMoyens;
	}

	/**
	 * @return the donneesNomsSecteurs
	 */
	public String[] getDonneesNomsSecteurs() {
		return donneesNomsSecteurs;
	}

	/**
	 * @param donneesNomsSecteurs the donneesNomsSecteurs to set
	 */
	public void setDonneesNomsSecteurs(String[] donneesNomsSecteurs) {
		this.donneesNomsSecteurs = donneesNomsSecteurs;
	}

	/**
	 * @return the donneesCouleursSecteurs
	 */
	public String[] getDonneesCouleursSecteurs() {
		return donneesCouleursSecteurs;
	}

	/**
	 * @param donneesCouleursSecteurs the donneesCouleursSecteurs to set
	 */
	public void setDonneesCouleursSecteurs(String[] donneesCouleursSecteurs) {
		this.donneesCouleursSecteurs = donneesCouleursSecteurs;
	}

	/**
	 * @return the donneesMoyensAddedToList
	 */
	public ArrayList<itemListDemandeMoyens> getDonneesMoyensAddedToList() {
		return donneesMoyensAddedToList;
	}

	/**
	 * @param donneesMoyensAddedToList the donneesMoyensAddedToList to set
	 */
	public void setDonneesMoyensAddedToList(
			ArrayList<itemListDemandeMoyens> donneesMoyensAddedToList) {
		this.donneesMoyensAddedToList = donneesMoyensAddedToList;
	}

	/**
	 * @return the adapterListToSend
	 */
	public AdapterListMoyenToAsk getAdapterListToSend() {
		return adapterListToSend;
	}

	/**
	 * @param adapterListToSend the adapterListToSend to set
	 */
	public void setAdapterListToSend(AdapterListMoyenToAsk adapterListToSend) {
		this.adapterListToSend = adapterListToSend;
	}

}// Class VueDemandeDeMoyens
