/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.handler;

/**
 * @author Mathieu ROLLAND
 */
import android.os.Handler;
import android.os.Message;

public class Updater extends Handler{

	private UpdateView view;
	
	public Updater(UpdateView view){
		this.view = view;
	}
	
	public void dispatchMessage(Message msg){
		view.onMessageReveive((String)msg.obj);
	}
	
}
