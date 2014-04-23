/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.controller.demandeMoyens;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.agetac.R;
import com.agetac.activity.items.itemListDemandeMoyens;
import com.agetac.fragment.VueDemandeDeMoyens;

/**
* Classe ListenerAjoutToList : Listener déclenché lors de l'appuie sur la corbeille d'un item
*
* @version 2.0 09/02/2013
* @author Anthony LE MEE - 10003134
*/
public class ListenerSupressionItem implements OnClickListener{

	/** Attributs */
	private itemListDemandeMoyens itemMoyen; 	// Instance de l'item à traiter
	private VueDemandeDeMoyens vue; 			// Instance de la vue d'où est joué le listener
	
	/**
	 * Contructeur de ListenerSupressionItem
	 * @param user 
	 * @param demandeDeMoyens 
	 */
	public ListenerSupressionItem(itemListDemandeMoyens user, VueDemandeDeMoyens demandeDeMoyens) {
		
		this.itemMoyen = user;
		this.vue = demandeDeMoyens;
		
	} // méthode

	/**
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		 
		// Création de l'AlertDialog
		AlertDialog.Builder adb = new AlertDialog.Builder(this.vue.getView().getContext());
        final AlertDialog alert = adb.create();	
		alert.show();
		alert.setContentView(R.layout.maquette_alert_dialog_delete_item);
		
		// Récupération des données pour les listener internes
		final VueDemandeDeMoyens vueDemandeMoyen = this.vue;
		final itemListDemandeMoyens item = this.getItemMoyen();
		final TextView tv = ((TextView)alert.findViewById(R.id.viewNbrToDelete));
		
		// On set la valeur du titre de la boite de Dialog
		((TextView)alert.findViewById(R.id.titleAlertDialog)).setText("Choisissez le nombre de " + item.getNom() + " à supprimer dans ce secteur");
		
		// On set la valeur par défaut du champs "nombre item to delete"
		tv.setText(String.valueOf(this.getItemMoyen().getNombre())); //

		// On pose le listener d'annulation de suppression
		alert.findViewById(R.id.btnAnnulerDialogAlert).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// On ferme la boite de dialogue
				alert.cancel();

			}});
		
		// On pose le listener de confirmation de suppression
		alert.findViewById(R.id.btnSupprimerDialogAlert).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				int tmp = Integer.parseInt(((TextView)alert.findViewById(R.id.viewNbrToDelete)).getText().toString());
				
				// Si l'utilisateur souhaite tout supprimer
				if (tmp == item.getNombre()) {
					
					//alors on retire l'instance courante
					vue.getDonneesMoyensAddedToList().remove(item);
				}
				// Sinon, on diminue l'occurence du type de moyen courant par celui voulu
				else
				{
					if (tmp < item.getNombre()) item.setNombre(item.getNombre() - tmp);
				}
				
				// Lancement de la mise à jour
				vueDemandeMoyen.getAdapterListToSend().notifyDataSetChanged();
				
				// On ferme la boite de dialogue
				alert.cancel();

			}});
		
		// On pose le listener de diminution du nombre de moyen de ce type à supprimer
		alert.findViewById(R.id.btnLessItem).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (Integer.parseInt(((TextView)alert.findViewById(R.id.viewNbrToDelete)).getText() + "") > 1) {
					
					int tmp = Integer.parseInt(((TextView)alert.findViewById(R.id.viewNbrToDelete)).getText().toString()) - 1;
					((TextView)alert.findViewById(R.id.viewNbrToDelete)).setText(
							String.valueOf(tmp));
					
				}

			}});
		
		// On pose le listener d'augmentation du nombre de moyen de ce type à supprimer
		alert.findViewById(R.id.btnMoreItem).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (Integer.parseInt(((TextView)alert.findViewById(R.id.viewNbrToDelete)).getText() + "") < item.getNombre()) {
					
					int tmp = Integer.parseInt(((TextView)alert.findViewById(R.id.viewNbrToDelete)).getText().toString()) + 1;
					((TextView)alert.findViewById(R.id.viewNbrToDelete)).setText(
							String.valueOf(tmp));
					
				}

			}});
		
		
	} // méthode
	
	/*******************************************************************
	 * GETTERS & SETTERS
	 ******************************************************************/
	
	/**
	 * @param itemMoyen the itemMoyen to set
	 */
	public void setItemMoyen(itemListDemandeMoyens itemMoyen) {
		this.itemMoyen = itemMoyen;
	}

	/**
	 * @return the itemMoyen
	 */
	public itemListDemandeMoyens getItemMoyen() {
		return itemMoyen;
	}

	/**
	 * @param vue the vue to set
	 */
	public void setVue(VueDemandeDeMoyens vue) {
		this.vue = vue;
	}

	/**
	 * @return the vue
	 */
	public VueDemandeDeMoyens getVue() {
		return vue;
	}

} // class
