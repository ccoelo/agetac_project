package com.agetac.observer;

public interface Observer {
	public void update(String idAsker, String json);
	public void update();
}
