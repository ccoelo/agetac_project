package com.agetac.model;

public class Agent {

	private int id;
	private String nom;
	private String prenom;
	private int rang;
	private String grade;
	private int idInter;
	private String abrvGrade;
	
	public Agent (
			 int id,
			 String nom,
			 String prenom,
			 int rang,
			 String grade,
			 int idInter,
			 String abrvGrade) {
		
		this.id				= id;
		this.nom			= nom;
		this.prenom			= prenom;
		this.rang			= rang;
		this.grade			= grade;
		this.idInter		= idInter;
		this.abrvGrade		= abrvGrade;
		
	}
	
	public int getIdInter() {
		return idInter;
	}
	public void setIdInter(int idInter) {
		this.idInter = idInter;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getRang() {
		return rang;
	}
	public void setRang(int rang) {
		this.rang = rang;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getGrade() {
		return grade;
	}

	/**
	 * @param abrvGrade the abrvGrade to set
	 */
	public void setAbrvGrade(String abrvGrade) {
		this.abrvGrade = abrvGrade;
	}

	/**
	 * @return the abrvGrade
	 */
	public String getAbrvGrade() {
		return abrvGrade;
	}
}
