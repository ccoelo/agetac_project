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
* Classe DTO<T> : Classe abstraite qui d�finie la structure d'un DTO de fa�on globale.
* 
* @version 2.0 30/01/2013
* @author Jimmy DANO - 13000159
*/
public abstract class DTO<T> {

	public Updater update;
	public Connection connect;
	
	/**
	 * Permet de r�cup�rer l'ensemble des lignes d'une Table suivant une limite de lignes fix�e. 
	 * Si ce nombre est �gal � -1, alors aucune limite de lignes n'est appliqu�e.
	 * @param limit - nombre de lignes max que l'on souhaite s�lectionner
	 * i.e SELECT * FROM x LIMIT 30 => .findAll (30)
	 * @return T[]
	 */
	public abstract T[] findAll(int limit);
	
	/**
	 * Permet de r�cup�rer l'ensemble des lignes d'une Table.
	 * Le r�sultat retourn� donnera, pour chaque ligne, des donn�es sur l'ensemble des champs 
	 * fournis dans la m�thode.
	 * i.e SELECT nom, prenom FROM x LIMIT 30 => .find ({nom, prenom}, 30)
	 * @param limit - nombre de ligne max que l'on souhaite s�lectionner
	 * @return T[]
	 */
	public abstract T[] find(String[] champs, int limit);
	
	/**
	 * Permet de r�cup�rer un objet via son ID
	 * @param id - id de la ligne que l'on cherche
	 * @return T
	 */
	public abstract T findById(int id);
	
	/**
	 * Permet de cr�er une entr�e dans la base de donn�es
	 * par rapport � un objet
	 * @param obj
	 * @return T type du DTO
	 */
	public abstract boolean create(T obj);
	
	/**
	 * Permet de mettre � jour les donn�es d'une entr�e dans la base 
	 * @param obj
	 * @return T type du DTO
	 */
	public abstract T update(T obj);
	
	/**
	 * Permet la suppression d'une entr�e de la base
	 * @param obj
	 * @return T type du DTO
	 */
	public abstract void delete(T obj);

	/**
	 * permet de r�cup�rer des v�hicules en fonction du secteur et s'il est sur les lieux
	 * @param i le secteur
	 * @param b parti ou sur les lieux
	 * @return le nombre de v�hicules
	 */
	public abstract int find(int i, boolean b);

	/**
	 * permet de r�cup�rer les groupes du secteur en param�tre
	 */
	public GroupeIntervention[] getGroupe(int secteur) {
		return new GroupeIntervention[3];
	}
	
}// class
