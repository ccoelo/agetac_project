/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity.adapter;


import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agetac.Infos;
import com.agetac.R;
import com.agetac.activity.comparator.MyComparatorMess;
import com.agetac.activity.handler.Updater;
import com.agetac.dto.dto_config.DTO;
import com.agetac.dto.dto_config.DTOFactory;
import com.agetac.fragment.VueListeDesMessages;
import com.agetac.model.Message;


/**
 * @author jimmy
 *
 */
public class AdapterMessage extends BaseAdapter{

	private List<Message> mList;
	
	//Le contexte dans lequel est présent notre adapter
	private Context mContext;
	    	
	//Un mécanisme pour gérer l'affichage graphique depuis un layout XML
	private LayoutInflater mInflater;
	
	private VueListeDesMessages vueListeDesMessages;
	
	public AdapterMessage(Context context, List<Message> aListP, VueListeDesMessages vueListeDesMessages) {
		  
		mContext = context;
		mList = aListP;
		mInflater = LayoutInflater.from(mContext);
		this.vueListeDesMessages = vueListeDesMessages;
		  
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
	
	public void sort(int item) {
		
		Collections.sort(mList, new MyComparatorMess(item));	    
		notifyDataSetChanged();
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final LinearLayout layoutItem;
		
		//Réutilisation des layouts
		if (convertView == null) {
			//Initialisation de notre item à partir du  layout XML
	    	layoutItem = (LinearLayout) mInflater.inflate(R.layout.message_adapter, parent, false);
		} else {
			layoutItem = (LinearLayout) convertView;
		}

		//(2) : Récupération des TextView de notre layout      
		TextView date 			= (TextView)layoutItem.findViewById(R.id.date);
		TextView auteur 		= (TextView)layoutItem.findViewById(R.id.auteur);
		TextView type 			= (TextView)layoutItem.findViewById(R.id.type);
		final TextView resume 	= (TextView)layoutItem.findViewById(R.id.isLu);

		//(3) : Renseignement des valeurs  
		try {
			
			JSONObject contenu = new JSONObject(mList.get(position).getContenu());
			String dateFormatee = new String("");
			String format 				= "dd/MM/yy à H:mm:ss"; 
			String format_date			= "dd/MM/yy";
			SimpleDateFormat formater 	= new java.text.SimpleDateFormat( format_date ); 
			Date dateToday 				= new java.util.Date(); 
			if (formater.format( dateToday ) != null) 
			{
				String dateMess = new SimpleDateFormat( format_date ).format( mList.get( position ).getEnvoi() );
				if (dateMess.equals((String)formater.format( dateToday ))) {
					dateFormatee = new SimpleDateFormat(" H:mm:ss").format( mList.get( position ).getEnvoi() );
					dateFormatee = "Aujourd'hui à" + dateFormatee;
				}
				else
				{
					dateFormatee = new SimpleDateFormat( format ).format( mList.get( position ).getEnvoi() );
				}
			}
			
			date.setText( dateFormatee );
			auteur.setText("de " + mList.get(position).getPrenomNomEmetteur());
			//resume.setText(contenu.getString("texte"));
			type.setText(mList.get(position).getLibelleType() + "");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		layoutItem.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				DTO<Message> mess 	= DTOFactory.getMessage();
				mess.update 		= new Updater(vueListeDesMessages);
				Message m 			= mess.findById(Integer.parseInt(mList.get(position).getIdMessage() + ""));

				try {
					
					JSONObject contenu = new JSONObject(m.getContenu());
					String dateFormatee = new String("");
					String format 				= "dd/MM/yy à H:mm:ss"; 
					String format_date			= "dd/MM/yy";
					SimpleDateFormat formater 	= new java.text.SimpleDateFormat( format_date ); 
					Date dateToday 				= new java.util.Date(); 
					if (formater.format( dateToday ) != null) 
					{
						String dateMess = new SimpleDateFormat( format_date ).format(m.getEnvoi() );
						if (dateMess.equals((String)formater.format( dateToday ))) {
							dateFormatee = new SimpleDateFormat(" H:mm:ss").format(m.getEnvoi() );
							dateFormatee = "Aujourd'hui à" + dateFormatee;
						}
						else
						{
							dateFormatee = new SimpleDateFormat( format ).format(m.getEnvoi() );
						}
					}
					
					// Création de l'AlertDialog
					AlertDialog.Builder adb = new AlertDialog.Builder(vueListeDesMessages.getView().getContext());
			        final AlertDialog alert = adb.create();	
					alert.show();
					
					if (mList.get(position).getIdEmmeteur() != Infos.getInstance().getIdCourantUtilisateur()) {
						
						mList.get(position).setLu(true);
						m.setLu(true);
						m = mess.update(m);
						resume.setBackgroundColor(Color.parseColor("#2999A6"));
						resume.setText("lu");
						
					}
					
					alert.setContentView(R.layout.maquette_details_message);					

					// On pose le listener d'annulation de suppression
					alert.findViewById(R.id.btnFermerMessage).setOnClickListener(new OnClickListener() {

						public void onClick(View v) {

							// On ferme la boite de dialogue
							alert.cancel();
						}});	
					
					((TextView)alert.findViewById(R.id.DateMessage)).setText( dateFormatee );
					((TextView)alert.findViewById(R.id.AuteurMessage)).setText("de " + m.getPrenomNomEmetteur());
					((TextView)alert.findViewById(R.id.contenuMessage)).setText(contenu.getString("texte"));
					((TextView)alert.findViewById(R.id.TypeMessage)).setText(m.getLibelleType() + "");
					
				}catch(Exception e) {
					e.printStackTrace();
				}

			}
			
		});
		  
		//(4) Changement de la couleur du fond de notre item en fonction du message (lu, pas lu, envoyé par moi)
		if (mList.get(position).getIdEmmeteur() == Infos.getInstance().getIdCourantUtilisateur()) {
			resume.setBackgroundColor(Color.parseColor("#4CA633"));
			resume.setText("envoyé");
		} else if (mList.get(position).isLu()) {
			resume.setBackgroundColor(Color.parseColor("#2999A6"));
			resume.setText("lu");
			// resume.setVisibility(View.GONE);
		} else {
			resume.setBackgroundColor(Color.parseColor("#be2424"));
			resume.setText("non lu");
		}

		//On retourne l'item créé.
		return layoutItem;
		
	}
}
