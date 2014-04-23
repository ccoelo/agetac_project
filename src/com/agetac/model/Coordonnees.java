package com.agetac.model;

public class Coordonnees {

	private int x;
	private int y;
	private int id;
	
	/**
	 * @param int1
	 * @param int2
	 * @param i 
	 */
	public Coordonnees(int int1, int int2, int i) {
		id = int1;
		x = int2;
		y = i;
	}
	/**
	 * 
	 */
	public Coordonnees() {
		// TODO Auto-generated constructor stub
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
