/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.controller.demandeMoyens;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.agetac.activity.items.itemListDemandeMoyens;
import com.agetac.activity.utils.UtilsTypesMoyens;
import com.agetac.fragment.VueDemandeDeMoyens;

/**
* Classe ListenerAjoutToList : Listener déclenché lors de la demande d'ajout d'un moyens à la liste de demande
*
* @version 2.0 08/02/2013
* @author Anthony LE MEE - 10003134
*/
public class ListenerAjoutToList implements OnClickListener{

	/** Instance de l'activity principale de demande de moyens */
	private VueDemandeDeMoyens demandeDeMoyens;
	
	/**
	 * Constructeur de ListenerAjoutToList
	 * @param vueDemandeDeMoyens
	 */
	public ListenerAjoutToList(VueDemandeDeMoyens vueDemandeDeMoyens) {
		
		this.demandeDeMoyens = vueDemandeDeMoyens;
		
	}

	/**
	 * Lors d'un Click sur la vue v
	 * @param v view
	 */
	public void onClick(View v) {

		// Récupération de l'indice de l'élement sélectionner dans la grille. SI aucun, alors on aura -1
		int indiceMoyensSelected = this.demandeDeMoyens.getSauvegarde().getIndiceMoyen();

		/* 
		 * Le champs d'auto-complétion "autre moyen" à le dessus sur la grille.
		 * Si on saisie quelque chose dedans alors c'est que l'on souhaite l'ajouter. 
		 * Dans le cas contraire, il faudra alors obligatoirement sélectionner un moyen dans la grille
		 */
		if (this.demandeDeMoyens.getTextViewAutresMoyens().getText() != null && !(this.demandeDeMoyens.getTextViewAutresMoyens().getText().toString().equals(""))) {
		
			// On créer l'élément 
			itemListDemandeMoyens nomElementSelectionne = new itemListDemandeMoyens(
					this.demandeDeMoyens.getTextViewAutresMoyens().getText() + "",
					Integer.parseInt(this.demandeDeMoyens.getEditTextNombreMoyens().getText() + ""),
					this.demandeDeMoyens.getDonneesCouleursSecteurs()[this.demandeDeMoyens.getSauvegarde().getIndiceSecteur()]);
			
			// Si j'ai déjà ajouté ce type de moyen pour ce secteur, alors j'augmente seulement sont nombre de +1
			itemListDemandeMoyens searchSameElement = UtilsTypesMoyens.searchMoyensAlreadyAdded(nomElementSelectionne, this.demandeDeMoyens.getDonneesMoyensAddedToList());
			if (searchSameElement != null) {
				searchSameElement.setNombre(searchSameElement.getNombre() + Integer.parseInt(this.demandeDeMoyens.getEditTextNombreMoyens().getText() + ""));
			}
			else
			{
				this.demandeDeMoyens.getDonneesMoyensAddedToList().add(0,nomElementSelectionne);
			}
			
			
			// Mise à jour de la list afin qu'elle prenne en compte le nouvel élément
			this.demandeDeMoyens.getAdapterListToSend().notifyDataSetChanged();
			
			// Sauvegarde
			this.demandeDeMoyens.getSauvegarde().setDonneesMoyensAddedToList(this.demandeDeMoyens.getDonneesMoyensAddedToList());

			Toast.makeText(v.getContext().getApplicationContext(), 
					nomElementSelectionne.getNom()
					+ " pour le secteur " 
					+ this.demandeDeMoyens.getDonneesNomsSecteurs()[this.demandeDeMoyens.getSauvegarde().getIndiceSecteur()]
					+ " ajouté ...", Toast.LENGTH_SHORT).show();
			
		}
		// Sinon si j'ai sélectionné un moyen dans la grille
		else if (indiceMoyensSelected >= 0)
		{
			
			// On créer l'élément 
			itemListDemandeMoyens nomElementSelectionne = new itemListDemandeMoyens(
					(String) ((Button)this.demandeDeMoyens.getGridViewChoixMoyens().getChildAt(indiceMoyensSelected)).getText(),
					Integer.parseInt(this.demandeDeMoyens.getEditTextNombreMoyens().getText() + ""),
					this.demandeDeMoyens.getDonneesCouleursSecteurs()[this.demandeDeMoyens.getSauvegarde().getIndiceSecteur()]);
			
			// Si j'ai déjà ajouté ce type de moyen pour ce secteur, alors j'augmente seulement sont nombre de +1
			itemListDemandeMoyens searchSameElement = UtilsTypesMoyens.searchMoyensAlreadyAdded(nomElementSelectionne, this.demandeDeMoyens.getDonneesMoyensAddedToList());
			if (searchSameElement != null) {
				searchSameElement.setNombre(searchSameElement.getNombre() + Integer.parseInt(this.demandeDeMoyens.getEditTextNombreMoyens().getText() + ""));
			}
			else
			{
				this.demandeDeMoyens.getDonneesMoyensAddedToList().add(0,nomElementSelectionne);
			}
			
			
			// Mise à jour de la list afin qu'elle prenne en compte le nouvel élément
			this.demandeDeMoyens.getAdapterListToSend().notifyDataSetChanged();
			
			//Sauvegarde
			this.demandeDeMoyens.getSauvegarde().setDonneesMoyensAddedToList(this.demandeDeMoyens.getDonneesMoyensAddedToList());

			Toast.makeText(v.getContext().getApplicationContext(), 
					nomElementSelectionne.getNom()
					+ " pour le secteur " 
					+ this.demandeDeMoyens.getDonneesNomsSecteurs()[this.demandeDeMoyens.getSauvegarde().getIndiceSecteur()]
					+ " ajouté ...", Toast.LENGTH_SHORT).show();
			
		}
		// dans tous les autres cas
		else
		{
			Toast.makeText(v.getContext().getApplicationContext(), 
					"Veuillez sélectionner un moyens à ajouter", Toast.LENGTH_SHORT).show();
		} // if else
				
	} // méthode
	
	/********************************************************************************************************/
    /** GETTEURS ET SETTEURS
    /********************************************************************************************************/

	/**
	 * @param demandeDeMoyens the demandeDeMoyens to set
	 */
	public void setDemandeDeMoyens(VueDemandeDeMoyens demandeDeMoyens) {
		this.demandeDeMoyens = demandeDeMoyens;
	}

	/**
	 * @return the demandeDeMoyens
	 */
	public VueDemandeDeMoyens getDemandeDeMoyens() {
		return demandeDeMoyens;
	}

} // class ListenerAjoutToList