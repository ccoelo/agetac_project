/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.utils;

import java.util.ArrayList;

import com.agetac.activity.items.itemListVisuSecteur;
import com.agetac.model.SecteurIntervention;

/**
 * Classe UtilsSecteurs : outils utils pour le traitement des secteurs
 *
 * @version 2.0 07/02/2013
 * @author Anthony LE MEE - 10003134
 */
public class UtilsSecteurs {

	private static UtilsSecteurs instance = null;
	
	/**
	 * Constructeur privé
	 */
	private UtilsSecteurs() {}
	
	/**
	 * Retourne l'instance du singleton
	 */
	public static UtilsSecteurs getInstance () {

		//Le "Double-Checked Singleton"/"Singleton doublement vérifié" permet d'éviter un appel coûteux à synchronized, 
        //une fois que l'instanciation est faite.
		if(instance == null) { 
			// Le mot-clé synchronized sur ce bloc empêche toute instanciation multiple même par différents "threads".
            // Il est TRES important.
			synchronized(UtilsSecteurs.class) {
				if (instance == null) {
					instance = new UtilsSecteurs();
				}
			}
		}
		
		return instance;
		
	}
	
	/**
	 * Extrait un tableau de noms de secteurs d'une liste de SecteurIntervention
	 * @param donneesSecteurs
	 * @return String[]
	 */
	public static String[] getNamesOfSecteurs(SecteurIntervention[] donneesSecteurs) {
		
		String[] listNomsSecteurs = new String[donneesSecteurs.length];
		for (int i = 0; i < donneesSecteurs.length; ++i) {
			listNomsSecteurs[i]=donneesSecteurs[i].getNomSecteur();
		}
		
		return listNomsSecteurs;
		
	}
	
	/**
	 * Extrait un tableau d'identifiant de secteurs d'une liste de SecteurIntervention
	 * @param donneesSecteurs
	 * @return String[]
	 */
	public static Integer[] getIdOfSecteurs(SecteurIntervention[] donneesSecteurs) {
		
		Integer[] listIdsSecteurs = new Integer[donneesSecteurs.length];
		for (int i = 0; i < donneesSecteurs.length; ++i) {
			listIdsSecteurs[i]=donneesSecteurs[i].getIdSecteur();
		}
		
		return listIdsSecteurs;
		
	}

	/**
	 * Extrait un tableau de couleurs associés aux secteurs d'une liste de SecteurIntervention
	 * @param donneesSecteurs
	 * @return String[]
	 */
	public static String[] getColorsOfSecteurs(SecteurIntervention[] donneesSecteurs) {

		String[] listColorsSecteurs = new String[donneesSecteurs.length];
		for (int i = 0; i < donneesSecteurs.length; ++i) {
			listColorsSecteurs[i]=donneesSecteurs[i].getCouleurSecteur();
		}
		
		return listColorsSecteurs;
	
	}
	
	/**
	 * Converti en arraylist d'itemListVisuSecteur
	 * @param donneesSecteurs
	 * @return ArrayList<itemListVisuSecteur>
	 */
	public static ArrayList<itemListVisuSecteur> convertINTOitemSecteur (SecteurIntervention[] donneesSecteurs) {
		
		ArrayList<itemListVisuSecteur> result = new ArrayList<itemListVisuSecteur>();
		for (int i = 0; i < donneesSecteurs.length; ++i) {
			result.add(new itemListVisuSecteur (
					donneesSecteurs[i].getIdSecteur(),
					donneesSecteurs[i].getNomSecteur(),
					-1,
					-1,
					donneesSecteurs[i].getCouleurSecteur()));
		}
		
		return result;
	}
	
	public static ArrayList<ArrayList<itemListVisuSecteur>> splitIn (ArrayList<itemListVisuSecteur> a, int nbPart) {
		
		ArrayList<ArrayList<itemListVisuSecteur>> parts = new ArrayList<ArrayList<itemListVisuSecteur>>();
	    final int N = a.size();
	    int saut;
	    if((N%nbPart) == 0){
	    	saut = (N/nbPart);
    	}
    	else{
    		saut = (N/nbPart) + 1;
    	}
	    for (int i = 0; i < N; i += saut) {
	        parts.add(new ArrayList<itemListVisuSecteur>(
	            a.subList(i, Math.min(N, i + saut)))
	        );
	    }
	    return parts;
	}

}
