/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.controller.messageQuelconque;

import com.agetac.activity.VueMessageQuelconque;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Benjamin
 *
 */
public class ListenerEnvoi implements OnClickListener {

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	private VueMessageQuelconque msgQuelconque;
	
	public ListenerEnvoi(Activity a){
		
		this.msgQuelconque = (VueMessageQuelconque)a; 
		
	}
	
	public void onClick(View v) {
		// TODO A voir avec le serveur !

	}

	public VueMessageQuelconque getMessageQuelconque(){
		
		return msgQuelconque;
		
	}
	
	public void setMsgQuelconque(VueMessageQuelconque msgQuelconque){
		
		this.msgQuelconque = msgQuelconque;
		
	}
	
}
