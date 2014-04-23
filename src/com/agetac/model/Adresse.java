package com.agetac.model;

import com.agetac.dto.dto_config.DTO;
import com.agetac.dto.dto_config.DTOFactory;

public class Adresse {

	private int num;
	private String rue;
	private String ville;
	private int codePostale;
	private int etage;
	private Coordonnees c;
	private int id;
	
	/**
	 * @param int1
	 * @param string
	 * @param string2
	 * @param int2
	 * @param int3
	 * @param i 
	 */
	public Adresse(int int1, String string, String string2, int int2, int int3, int i) {
		num = int1;
		rue = string;
		ville = string2;
		codePostale = int2;
		etage = int3;
		if(i != -1){
			DTO<Coordonnees> d = DTOFactory.getCoordonnee();
			c = d.findById(i);
		}
	}
	
	public Adresse() {
	}
	
	public int getNumRue() {
		return num;
	}
	public void setNumRue(int numRue) {
		this.num = numRue;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ille) {
		ville = ille;
	}
	public int getCodePostale() {
		return codePostale;
	}
	public void setCodePostale(int codePostale) {
		this.codePostale = codePostale;
	}
	public Coordonnees getC() {
		return c;
	}
	public void setC(Coordonnees c) {
		this.c = c;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEtage() {
		return etage;
	}
	public void setEtage(int etage) {
		this.etage = etage;
	}
	
}
