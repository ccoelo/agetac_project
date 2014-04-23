/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.utils;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

import com.agetac.activity.items.itemListVisuSecteur;
import com.agetac.model.SecteurIntervention;

/**
 * Classe Utils : outils utils pour les traitements g�n�raux
 *
 * @version 2.0 07/02/2013
 * @author Anthony LE MEE - 10003134
 */
public class Utils {

	private static Utils instance = null;
	
	/**
	 * Constructeur priv�
	 */
	private Utils() {}
	
	/**
	 * Retourne l'instance du singleton
	 */
	public static Utils getInstance () {

		//Le "Double-Checked Singleton"/"Singleton doublement v�rifi�" permet d'�viter un appel co�teux � synchronized, 
        //une fois que l'instanciation est faite.
		if(instance == null) { 
			// Le mot-cl� synchronized sur ce bloc emp�che toute instanciation multiple m�me par diff�rents "threads".
            // Il est TRES important.
			synchronized(Utils.class) {
				if (instance == null) {
					instance = new Utils();
				}
			}
		}
		
		return instance;
		
	}	

}
