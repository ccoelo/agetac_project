/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.utils;

import java.util.ArrayList;

import com.agetac.activity.items.itemListDemandeMoyens;
import com.agetac.model.TypesMoyens;

/**
 * Classe UtilsTypesMoyens : outils utils pour le traitement des types de moyens
 *
 * @version 2.0 07/02/2013
 * @author Anthony LE MEE - 10003134
 */
public class UtilsTypesMoyens {

	private static UtilsTypesMoyens instance = null;
	
	/**
	 * Constructeur privé
	 */
	private UtilsTypesMoyens() {}
	
	/**
	 * Retourne l'instance du singleton
	 */
	public static UtilsTypesMoyens getInstance () {

		//Le "Double-Checked Singleton"/"Singleton doublement vérifié" permet d'éviter un appel coûteux à synchronized, 
        //une fois que l'instanciation est faite.
		if(instance == null) { 
			// Le mot-clé synchronized sur ce bloc empêche toute instanciation multiple même par différents "threads".
            // Il est TRES important.
			synchronized(UtilsTypesMoyens.class) {
				if (instance == null) {
					instance = new UtilsTypesMoyens();
				}
			}
		}
		
		return instance;
		
	}
	
	/**
	 * Extrait un tableau de noms de type de moyens d'une liste de TypesMoyens
	 * @param listTypesMoyens
	 * @return String[]
	 */
	public static String[] getNamesOfTypesMoyens(TypesMoyens[] listTypesMoyens) {
		
		String[] listNomsTypesMoyens = new String[listTypesMoyens.length];
		for (int i = 0; i < listTypesMoyens.length; ++i) {
			listNomsTypesMoyens[i]=listTypesMoyens[i].getNomTypeMoyen();
		}
		
		return listNomsTypesMoyens;
		
	}
	
	/**
	 * Parcours la liste fournie à la recherche d'un moyen concordant avec celui que je fournis en recherche
	 * @param search itemListDemandeMoyens - élément que je cherche
	 * @param in ArrayList<itemListDemandeMoyens> - zone de recherche
	 * @return itemListDemandeMoyens si trouvé ; null sinon
	 */
	public static itemListDemandeMoyens searchMoyensAlreadyAdded(itemListDemandeMoyens search, ArrayList<itemListDemandeMoyens> in) {
		
		for (int i = 0 ; i < in.size() ; ++i) {
			
			if (search.getNom().equals(in.get(i).getNom()) && search.getSpinnerChoixSecteur().equals(in.get(i).getSpinnerChoixSecteur())) {
				return in.get(i);
			}
			
		}
		
		return null;
		
	}

}
