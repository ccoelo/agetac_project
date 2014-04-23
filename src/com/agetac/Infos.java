/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac;


/**
 * @author jimmy
 *
 */
public class Infos {

	public int idCourantUtilisateur = 0;
	public int idCouranteIntervention = 0;
	private static Infos instance = null;
	
	/**
	 * Constructeur priv�
	 */
	private Infos() {}
	
	/**
	 * Retourne l'instance du singleton
	 */
	public static Infos getInstance () {

		//Le "Double-Checked Singleton"/"Singleton doublement v�rifi�" permet d'�viter un appel co�teux � synchronized, 
        //une fois que l'instanciation est faite.
		if(instance == null) { 
			// Le mot-cl� synchronized sur ce bloc emp�che toute instanciation multiple m�me par diff�rents "threads".
            // Il est TRES important.
			synchronized(Infos.class) {
				if (instance == null) {
					instance = new Infos();
				}
			}
		}
		
		return instance;
		
	}

	/**
	 * @return the idCourantUtilisateur
	 */
	public int getIdCourantUtilisateur() {
		return idCourantUtilisateur;
	}

	/**
	 * @param idCourantUtilisateur the idCourantUtilisateur to set
	 */
	public void setIdCourantUtilisateur(int idCourantUtilisateur) {
		this.idCourantUtilisateur = idCourantUtilisateur;
	}

	/**
	 * @return the idCouranteIntervention
	 */
	public int getIdCouranteIntervention() {
		return idCouranteIntervention;
	}

	/**
	 * @param idCouranteIntervention the idCouranteIntervention to set
	 */
	public void setIdCouranteIntervention(int idCouranteIntervention) {
		this.idCouranteIntervention = idCouranteIntervention;
	}

	/**
	 * @param instance the instance to set
	 */
	public void setInstance(Infos instance) {
		Infos.instance = instance;
	}
	
}
