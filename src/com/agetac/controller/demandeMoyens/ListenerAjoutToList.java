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
* Classe ListenerAjoutToList : Listener d�clench� lors de la demande d'ajout d'un moyens � la liste de demande
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

		// R�cup�ration de l'indice de l'�lement s�lectionner dans la grille. SI aucun, alors on aura -1
		int indiceMoyensSelected = this.demandeDeMoyens.getSauvegarde().getIndiceMoyen();

		/* 
		 * Le champs d'auto-compl�tion "autre moyen" � le dessus sur la grille.
		 * Si on saisie quelque chose dedans alors c'est que l'on souhaite l'ajouter. 
		 * Dans le cas contraire, il faudra alors obligatoirement s�lectionner un moyen dans la grille
		 */
		if (this.demandeDeMoyens.getTextViewAutresMoyens().getText() != null && !(this.demandeDeMoyens.getTextViewAutresMoyens().getText().toString().equals(""))) {
		
			// On cr�er l'�l�ment 
			itemListDemandeMoyens nomElementSelectionne = new itemListDemandeMoyens(
					this.demandeDeMoyens.getTextViewAutresMoyens().getText() + "",
					Integer.parseInt(this.demandeDeMoyens.getEditTextNombreMoyens().getText() + ""),
					this.demandeDeMoyens.getDonneesCouleursSecteurs()[this.demandeDeMoyens.getSauvegarde().getIndiceSecteur()]);
			
			// Si j'ai d�j� ajout� ce type de moyen pour ce secteur, alors j'augmente seulement sont nombre de +1
			itemListDemandeMoyens searchSameElement = UtilsTypesMoyens.searchMoyensAlreadyAdded(nomElementSelectionne, this.demandeDeMoyens.getDonneesMoyensAddedToList());
			if (searchSameElement != null) {
				searchSameElement.setNombre(searchSameElement.getNombre() + Integer.parseInt(this.demandeDeMoyens.getEditTextNombreMoyens().getText() + ""));
			}
			else
			{
				this.demandeDeMoyens.getDonneesMoyensAddedToList().add(0,nomElementSelectionne);
			}
			
			
			// Mise � jour de la list afin qu'elle prenne en compte le nouvel �l�ment
			this.demandeDeMoyens.getAdapterListToSend().notifyDataSetChanged();
			
			// Sauvegarde
			this.demandeDeMoyens.getSauvegarde().setDonneesMoyensAddedToList(this.demandeDeMoyens.getDonneesMoyensAddedToList());

			Toast.makeText(v.getContext().getApplicationContext(), 
					nomElementSelectionne.getNom()
					+ " pour le secteur " 
					+ this.demandeDeMoyens.getDonneesNomsSecteurs()[this.demandeDeMoyens.getSauvegarde().getIndiceSecteur()]
					+ " ajout� ...", Toast.LENGTH_SHORT).show();
			
		}
		// Sinon si j'ai s�lectionn� un moyen dans la grille
		else if (indiceMoyensSelected >= 0)
		{
			
			// On cr�er l'�l�ment 
			itemListDemandeMoyens nomElementSelectionne = new itemListDemandeMoyens(
					(String) ((Button)this.demandeDeMoyens.getGridViewChoixMoyens().getChildAt(indiceMoyensSelected)).getText(),
					Integer.parseInt(this.demandeDeMoyens.getEditTextNombreMoyens().getText() + ""),
					this.demandeDeMoyens.getDonneesCouleursSecteurs()[this.demandeDeMoyens.getSauvegarde().getIndiceSecteur()]);
			
			// Si j'ai d�j� ajout� ce type de moyen pour ce secteur, alors j'augmente seulement sont nombre de +1
			itemListDemandeMoyens searchSameElement = UtilsTypesMoyens.searchMoyensAlreadyAdded(nomElementSelectionne, this.demandeDeMoyens.getDonneesMoyensAddedToList());
			if (searchSameElement != null) {
				searchSameElement.setNombre(searchSameElement.getNombre() + Integer.parseInt(this.demandeDeMoyens.getEditTextNombreMoyens().getText() + ""));
			}
			else
			{
				this.demandeDeMoyens.getDonneesMoyensAddedToList().add(0,nomElementSelectionne);
			}
			
			
			// Mise � jour de la list afin qu'elle prenne en compte le nouvel �l�ment
			this.demandeDeMoyens.getAdapterListToSend().notifyDataSetChanged();
			
			//Sauvegarde
			this.demandeDeMoyens.getSauvegarde().setDonneesMoyensAddedToList(this.demandeDeMoyens.getDonneesMoyensAddedToList());

			Toast.makeText(v.getContext().getApplicationContext(), 
					nomElementSelectionne.getNom()
					+ " pour le secteur " 
					+ this.demandeDeMoyens.getDonneesNomsSecteurs()[this.demandeDeMoyens.getSauvegarde().getIndiceSecteur()]
					+ " ajout� ...", Toast.LENGTH_SHORT).show();
			
		}
		// dans tous les autres cas
		else
		{
			Toast.makeText(v.getContext().getApplicationContext(), 
					"Veuillez s�lectionner un moyens � ajouter", Toast.LENGTH_SHORT).show();
		} // if else
				
	} // m�thode
	
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