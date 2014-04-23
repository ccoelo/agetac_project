/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.savedInstanceState;

import java.io.Serializable;
import java.util.ArrayList;

import com.agetac.activity.items.itemListDemandeMoyens;
import com.agetac.activity.utils.UtilsSecteurs;

import android.widget.ArrayAdapter;

/**
* Classe savedInstanceStateDemandeMoyens de VueDemandeDeMoyens : Object que l'on va cr��er afin d'assurer la sauvegarde
* de l'�tat de la maquette lors du flip orientation
* 
* @version 1.0 22/01/2013
* @author Anthony LE MEE - 10003134
*/
public class savedInstanceStateDemandeMoyens implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** Indice du secteur actuellement s�lectionn� */
	private int indiceSecteur;
	
	/** Indice du moyen actuellement s�lectionn� */
	private int indiceMoyen;
	
	/** Quantit� d'un secteur actuellement voulu */
	private int quantiteMoyen;
	
	/** Le texte saisie comme autre moyen */
	private String autreMoyen;
	
	/** Liste des moyens ajout�s � la liste de demande de moyens */
	private ArrayList<itemListDemandeMoyens> donneesMoyensAddedToList;
		
	/** instance courante : Pattern SINGLETON*/
	private static savedInstanceStateDemandeMoyens instanceCourante;
	
	/**
	 * Constructeur de savedInstanceStateDemandeMoyens
	 */
	private savedInstanceStateDemandeMoyens () {
		
		this.indiceSecteur 				= 0;
		this.indiceMoyen 				= -1;
		this.quantiteMoyen 				= 1;
		this.donneesMoyensAddedToList	= new ArrayList<itemListDemandeMoyens>();
		this.autreMoyen 				= "";
		
	}
	
	/**
	 * M�thode de r�cup�ration de l'instance courant de la classe savedInstanceStateDemandeMoyens
	 * @return savedInstanceStateDemandeMoyens
	 */
	public static savedInstanceStateDemandeMoyens getInstance() {
		
		//Le "Double-Checked Singleton"/"Singleton doublement v�rifi�" permet d'�viter un appel co�teux � synchronized, 
        //une fois que l'instanciation est faite.
		if(instanceCourante == null) { 
			// Le mot-cl� synchronized sur ce bloc emp�che toute instanciation multiple m�me par diff�rents "threads".
            // Il est TRES important.
			synchronized(savedInstanceStateDemandeMoyens.class) {
				if (instanceCourante == null) {
					instanceCourante = new savedInstanceStateDemandeMoyens();
				}
			}
		}
		
		return instanceCourante;
		
	}
	
	/********************************************************************************************************/
    /** GETTEURS ET SETTEURS
    /********************************************************************************************************/

	/**
	 * @param indiceSecteur the indiceSecteur to set
	 */
	public void setIndiceSecteur(int indiceSecteur) {
		this.indiceSecteur = indiceSecteur;
	}

	/**
	 * @return the indiceSecteur
	 */
	public int getIndiceSecteur() {
		return indiceSecteur;
	}

	/**
	 * @param indiceMoyen the indiceMoyen to set
	 */
	public void setIndiceMoyen(int indiceMoyen) {
		this.indiceMoyen = indiceMoyen;
	}

	/**
	 * @return the indiceMoyen
	 */
	public int getIndiceMoyen() {
		return indiceMoyen;
	}

	/**
	 * @param autreMoyen the autreMoyen to set
	 */
	public void setAutreMoyen(String autreMoyen) {
		this.autreMoyen = autreMoyen;
	}

	/**
	 * @return the autreMoyen
	 */
	public String getAutreMoyen() {
		return autreMoyen;
	}

	/**
	 * @param quantiteMoyen the quantiteMoyen to set
	 */
	public void setQuantiteMoyen(int quantiteMoyen) {
		this.quantiteMoyen = quantiteMoyen;
	}

	/**
	 * @return the quantiteMoyen
	 */
	public int getQuantiteMoyen() {
		return quantiteMoyen;
	}

	/**
	 * @param arrayList the donneesMoyensAddedToList to set
	 */
	public void setDonneesMoyensAddedToList(ArrayList<itemListDemandeMoyens> arrayList) {
		this.donneesMoyensAddedToList = arrayList;
	}

	/**
	 * @return the donneesMoyensAddedToList
	 */
	public ArrayList<itemListDemandeMoyens> getDonneesMoyensAddedToList() {
		return donneesMoyensAddedToList;
	}

} // Classe savedInstanceStateDemandeMoyens
