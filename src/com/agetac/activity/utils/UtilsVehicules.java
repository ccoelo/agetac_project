/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.utils;

import java.util.ArrayList;
import com.agetac.model.Vehicule;

/**
 * Classe Utils : outils utils pour les traitements des V�hicules
 *
 * @version 2.0 07/02/2013
 * @author Anthony LE MEE - 10003134
 */
public class UtilsVehicules {

	private static UtilsVehicules instance = null;
	
	/**
	 * Constructeur priv�
	 */
	private UtilsVehicules() {}
	
	/**
	 * Retourne l'instance du singleton
	 */
	public static UtilsVehicules getInstance () {

		//Le "Double-Checked Singleton"/"Singleton doublement v�rifi�" permet d'�viter un appel co�teux � synchronized, 
        //une fois que l'instanciation est faite.
		if(instance == null) { 
			// Le mot-cl� synchronized sur ce bloc emp�che toute instanciation multiple m�me par diff�rents "threads".
            // Il est TRES important.
			synchronized(UtilsVehicules.class) {
				if (instance == null) {
					instance = new UtilsVehicules();
				}
			}
		}
		
		return instance;
		
	}

	/**
	 * @param vehicules
	 * @return
	 */
	public static ArrayList<Vehicule> convertINTOarrayListVehicule(Vehicule[] vehicules) {

		ArrayList<Vehicule> listIdsSecteurs = new ArrayList<Vehicule>();
		
		for (int i = 0; i < vehicules.length; ++i) {
			listIdsSecteurs.add(vehicules[i]);
		}
		
		return listIdsSecteurs;
		
	}

	/**
	 * @param arrayListMoyen
	 * @return
	 */
	public static String[] convertINTOarrayNameVehicule(ArrayList<Vehicule> arrayListMoyen) {
		
		String[] tab = null;
		
		if (arrayListMoyen != null) {
			
			tab = new String[arrayListMoyen.size()];
			
			int i = 0;
			for (Vehicule v : arrayListMoyen) {
				
				tab[i] = v.getNomVehicule();
				i++;
				
			}// for
			
		}// if
		
		return tab;
		
	}// m�thode

}
