package com.agetac.model;

public class GroupeIntervention {

	private int canalMontant;
	private int canalDescendant;
	private String nomGroupe;
	private int id;
	private int idSecteur;
	private int idInter;
	
	/**
	 * @param id2
	 * @param int1
	 * @param int2
	 * @param int3
	 * @param int4
	 * @param string
	 */
	public GroupeIntervention(int id2, int int1, int int2, int int3, int int4,
			String string) {
		id = id2;
		idInter = int1;
		idSecteur = int2;
		canalMontant = int3;
		canalDescendant = int4;
		nomGroupe = string;
	}
	public int getCanalMontant() {
		return canalMontant;
	}
	public void setCanalMontant(int canalMontant) {
		this.canalMontant = canalMontant;
	}
	public int getCanalDescendant() {
		return canalDescendant;
	}
	public void setCanalDescendant(int canalDescendant) {
		this.canalDescendant = canalDescendant;
	}
	public String getNomGroupe() {
		return nomGroupe;
	}
	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdSecteur() {
		return idSecteur;
	}
	public void setIdSecteur(int idSecteur) {
		this.idSecteur = idSecteur;
	}
	public int getIdInter() {
		return idInter;
	}
	public void setIdInter(int idInter) {
		this.idInter = idInter;
	}
}
