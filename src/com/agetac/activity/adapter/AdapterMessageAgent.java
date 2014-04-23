/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agetac.R;
import com.agetac.activity.VueMessageQuelconque;
import com.agetac.fragment.VueListeDesMessages;
import com.agetac.model.Agent;

/**
 * @author jimmy
 */
public class AdapterMessageAgent extends BaseAdapter{

	private List<Agent> mList;
	
	//Le contexte dans lequel est présent notre adapter
	private Context mContext;
	    	
	//Un mécanisme pour gérer l'affichage graphique depuis un layout XML
	private LayoutInflater mInflater;
	
	private VueListeDesMessages vueListeDesMessages;
	
	public AdapterMessageAgent(Context context, List<Agent> aListP, VueListeDesMessages vueListeDesMessages) {
		  
		mContext = context;
		mList = aListP;
		mInflater = LayoutInflater.from(mContext);
		this.setVueListeDesMessages(vueListeDesMessages);
		  
	}
	
	public int getCount() {
		return mList.size();
	}

	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final LinearLayout layoutItem;
		
		//Réutilisation des layouts
		if (convertView == null) {
			//Initialisation de notre item à partir du  layout XML
	    	layoutItem = (LinearLayout) mInflater.inflate(R.layout.maquette_agent_send_message, parent, false);
		} else {
			layoutItem = (LinearLayout) convertView;
		}

		// On récupère les infos que l'on souhaite afficher
		final Agent user = mList.get(position);
		
		//(2) : Récupération des TextView de notre layout      
		TextView nomAgent 			= (TextView)layoutItem.findViewById(R.id.nomAgent);
		
		nomAgent.setText(user.getAbrvGrade() + " - " + user.getPrenom() + " " + user.getNom());
		Log.d("Jim", "vueListeDesMessages  : " + vueListeDesMessages);
		layoutItem.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				Intent i = new Intent(mContext, VueMessageQuelconque.class);
				String 	label 		= new String("TOUS");
				int 	id			= -1;
				if (user.getAbrvGrade() != null && user.getNom() != null && user.getPrenom() != null) {
				
					label 	= new String(
							user.getAbrvGrade() + 
							" - " + user.getPrenom() +
							" " + user.getNom());
					id		= user.getId();
					i.putExtra("labelDest", label);
					i.putExtra("idDest", id);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mContext.startActivity(i);
					
				}
				
			}
		
		});
		
		//On retourne l'item créé.
		return layoutItem;
		
	}

	/**
	 * @param vueListeDesMessages the vueListeDesMessages to set
	 */
	public void setVueListeDesMessages(VueListeDesMessages vueListeDesMessages) {
		this.vueListeDesMessages = vueListeDesMessages;
	}

	/**
	 * @return the vueListeDesMessages
	 */
	public VueListeDesMessages getVueListeDesMessages() {
		return vueListeDesMessages;
	}
}
