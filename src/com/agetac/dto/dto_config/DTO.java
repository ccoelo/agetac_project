/*	
* Projet AGETAC	
* Jimmy Dano	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.dto.dto_config;

import java.sql.Connection;

import com.agetac.activity.handler.Updater;
import com.agetac.model.GroupeIntervention;

/**
* Classe DTO<T> : Classe abstraite qui définie la structure d'un DTO de façon globale.
* 
* @version 2.0 30/01/2013
* @author Jimmy DANO - 13000159
*/
public abstract class DTO<T> {

	public Updater update;
	public Connection connect;
	
	/**
	 * Permet de récupérer l'ensemble des lignes d'une Table suivant une limite de lignes fixée. 
	 * Si ce nombre est égal à -1, alors aucune limite de lignes n'est appliquée.
	 * @param limit - nombre de lignes max que l'on souhaite sélectionner
	 * i.e SELECT * FROM x LIMIT 30 => .findAll (30)
	 * @return T[]
	 */
	public abstract T[] findAll(int limit);
	
	/**
	 * Permet de récupérer l'ensemble des lignes d'une Table.
	 * Le résultat retourné donnera, pour chaque ligne, des données sur l'ensemble des champs 
	 * fournis dans la méthode.
	 * i.e SELECT nom, prenom FROM x LIMIT 30 => .find ({nom, prenom}, 30)
	 * @param limit - nombre de ligne max que l'on souhaite sélectionner
	 * @return T[]
	 */
	public abstract T[] find(String[] champs, int limit);
	
	/**
	 * Permet de récupérer un objet via son ID
	 * @param id - id de la ligne que l'on cherche
	 * @return T
	 */
	public abstract T findById(int id);
	
	/**
	 * Permet de créer une entrée dans la base de données
	 * par rapport à un objet
	 * @param obj
	 * @return T type du DTO
	 */
	public abstract boolean create(T obj);
	
	/**
	 * Permet de mettre à jour les données d'une entrée dans la base 
	 * @param obj
	 * @return T type du DTO
	 */
	public abstract T update(T obj);
	
	/**
	 * Permet la suppression d'une entrée de la base
	 * @param obj
	 * @return T type du DTO
	 */
	public abstract void delete(T obj);

	/**
	 * permet de récupérer des véhicules en fonction du secteur et s'il est sur les lieux
	 * @param i le secteur
	 * @param b parti ou sur les lieux
	 * @return le nombre de véhicules
	 */
	public abstract int find(int i, boolean b);

	/**
	 * permet de récupérer les groupes du secteur en paramètre
	 */
	public GroupeIntervention[] getGroupe(int secteur) {
		return new GroupeIntervention[3];
	}
	
}// class
