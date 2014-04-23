package com.agetac.model;

public class Intervention {

	private int id;
	private Adresse a;
	private int canal1_2;
	private int canal3_4;
	
	public Intervention(int id2) {
		id = id2;
	}

	public Intervention() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Adresse getA() {
		return a;
	}

	public void setA(Adresse a) {
		this.a = a;
	}

	public int getCanal1_2() {
		return canal1_2;
	}

	public void setCanal1_2(int canal1_2) {
		this.canal1_2 = canal1_2;
	}

	public int getCanal3_4() {
		return canal3_4;
	}

	public void setCanal3_4(int canal3_4) {
		this.canal3_4 = canal3_4;
	}
	
	
	
	
	
	
}
