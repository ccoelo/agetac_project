/*
 * Projet AGETAC
 * Anthony LE MEE
 * Universite de Rennes 1
 * ISTIC
 */
package com.agetac.fragment;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.agetac.R;
import com.agetac.activity.VueMessageQuelconque;
import com.agetac.activity.adapter.AdapterMessage;
import com.agetac.activity.adapter.AdapterMessageAgent;
import com.agetac.activity.handler.UpdateView;
import com.agetac.activity.handler.Updater;
import com.agetac.dto.AgentDTO;
import com.agetac.dto.MessageDTO;
import com.agetac.dto.dto_config.DTOFactory;
import com.agetac.model.Agent;
import com.agetac.model.Message;
import com.agetac.observer.Observer;


/**
 * Classe VueListeDesMessages : Activité qui affiche la liste des messages 
 * reçus et un menu d'accès à la saisie de messages
 * 
 * @version 1.0 30 Jan 2013
 * @author Anthony LE MEE - 10003134
 */
public class VueListeDesMessages extends Fragment implements UpdateView, Observer {

	/** Liste des Messages */
	private ListView lvMessages;
	
	/** Liste des Agents */
	private ListView lvAgents;
	
	/** Instance des Handler */
	Updater handler;
	
	/** Instances des modèles à utiliser */
	private MessageDTO 	mMessage;
	private AgentDTO 	mAgent;
	
	/** Données (récupérées via les modèles) à afficher dans la vue */
	private Message[] 	donneesMessages;
	private Agent[] 	donneesAgents;	
	
	/** Adapter */
	private AdapterMessage adapterMessage;
	
	/** ArrayList */
	private ArrayList<Message> listMessage;
	
	/** Mon instance courante */
	private VueListeDesMessages me;
	
	/**
	 * Méthode qui affiche un toast suite à la réception d'un message
	 * @param message
	 */
	public void onMessageReveive(String message) {
		Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Méthode onCreate
	 * @param savedInstanceState Bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);
		super.onCreate(savedInstanceState);
	}
	
	/** Méthode onCreatView */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		/** Chargement du layout */
		View view = inflater.inflate(R.layout.maquette_liste_messages, container, false);
		
		/** Instanciation des handler */
		this.handler = new Updater(this);
		
		/** Instanciations des modèles */
		this.mMessage 			= (MessageDTO) DTOFactory.getMessage();
		this.mMessage.update	= this.handler;
		this.mAgent				= (AgentDTO) DTOFactory.getAgent();
		this.mAgent.update		= this.handler;
		
		/** Récupérations des données via les modèles */
		this.donneesMessages 	= this.mMessage.findAll(100);
		this.donneesAgents 		= this.mAgent.findAll(-1);
		
		return view;
		
	}

	/** Méthode onActivityCreated */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		// Récupération des éléments via leur IDs
		this.lvMessages = (ListView) getActivity().findViewById(R.id.listeMess);		
		this.lvAgents	= (ListView) getActivity().findViewById(R.id.listViewAgentToContact);		
		
		// Récupération de la liste des messages
		listMessage = new ArrayList<Message>(Arrays.asList(this.donneesMessages));
	    
	    // Récupération de la liste des agents
	    ArrayList<Agent> listA = new ArrayList<Agent>(Arrays.asList(this.donneesAgents));
	    
		// Création et initialisation des Adapters
	    adapterMessage = new AdapterMessage(
	    		this.getActivity().getApplicationContext(),
	    		listMessage,
	    		this
	    		);
	    
	    final AdapterMessageAgent adapter2 = new AdapterMessageAgent(
	    		this.getActivity().getApplicationContext(),
	    		listA,
	    		this
	    		);
	    
	    //Initialisation de la liste avec les données
	    lvMessages.setAdapter(adapterMessage); 
	    lvAgents.setAdapter(adapter2); 
	    
	    lvMessages.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("trier en fonction de :");
				final CharSequence[] items = {"date", "auteur", "type"};
				builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int item)
		            {
		            	adapterMessage.sort(item);
		            	adapterMessage.notifyDataSetChanged();
		            }
		        });
				builder.setPositiveButton("OK", null);
				builder.create().show(); 
				return false;
			}
		});
	    
	    Button btnRedigerMessage 	= (Button)this.getActivity().findViewById(R.id.btn1);
	    Button btnRedigerMessageAmb = (Button)this.getActivity().findViewById(R.id.btn2);
	    
	    setMe(this);
	    
	    //btnRedigerMessage.setOnClickListener(new ListenerSwitchToActivity(this.getActivity(), VueMessageQuelconque.class));
	    btnRedigerMessage.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), VueMessageQuelconque.class);
				Bundle args = new Bundle();
				args.putString("labelDest", "TOUS");
				args.putInt("idDest", -1);
				intent.putExtras(args);
		    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    	startActivity(intent);
			}
	    	
	    });
	    
	}
	
	public void updateListMessage () {
		
		/** Récupérations des données via les modèles */
		this.donneesMessages 	= this.mMessage.findAll(100);
		
		//Récupération de la liste des messages
	    this.listMessage = new ArrayList<Message>(Arrays.asList(this.donneesMessages));
		
		// Lancement de la mise à jour
		this.getAdapterMessage().notifyDataSetChanged();
		
	}
	
	/* (non-Javadoc)
	 * @see com.agetac.observer.Observer#update(java.lang.String, java.lang.String)
	 */
	public void update(String idAsker, String json) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.agetac.observer.Observer#update()
	 */
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	/**********************************************/
	/**********   GETTERS AND SETTERS     *********/
	/**********************************************/

	/**
	 * @param mMessage the mMessage to set
	 */
	public void setmMessage(MessageDTO mMessage) {
		this.mMessage = mMessage;
	}

	/**
	 * @return the mMessage
	 */
	public MessageDTO getmMessage() {
		return mMessage;
	}

	/**
	 * @return the lvMessages
	 */
	public ListView getLvMessages() {
		return lvMessages;
	}

	/**
	 * @param lvMessages the lvMessages to set
	 */
	public void setLvMessages(ListView lvMessages) {
		this.lvMessages = lvMessages;
	}

	/**
	 * @return the handler
	 */
	public Updater getHandler() {
		return handler;
	}

	/**
	 * @param handler the handler to set
	 */
	public void setHandler(Updater handler) {
		this.handler = handler;
	}

	/**
	 * @param donneesMessages the donneesMessages to set
	 */
	public void setDonneesMessages(Message[] donneesMessages) {
		this.donneesMessages = donneesMessages;
	}

	/**
	 * @return the donneesMessages
	 */
	public Message[] getDonneesMessages() {
		return donneesMessages;
	}

	/**
	 * @param donneesAgents the donneesAgents to set
	 */
	public void setDonneesAgents(Agent[] donneesAgents) {
		this.donneesAgents = donneesAgents;
	}

	/**
	 * @return the donneesAgents
	 */
	public Agent[] getDonneesAgents() {
		return donneesAgents;
	}

	/**
	 * @param mAgent the mAgent to set
	 */
	public void setmAgent(AgentDTO mAgent) {
		this.mAgent = mAgent;
	}

	/**
	 * @return the mAgent
	 */
	public AgentDTO getmAgent() {
		return mAgent;
	}

	/**
	 * @param lvAgents the lvAgents to set
	 */
	public void setLvAgents(ListView lvAgents) {
		this.lvAgents = lvAgents;
	}

	/**
	 * @return the lvAgents
	 */
	public ListView getLvAgents() {
		return lvAgents;
	}

	/**
	 * @param adapterMessage the adapterMessage to set
	 */
	public void setAdapterMessage(AdapterMessage adapterMessage) {
		this.adapterMessage = adapterMessage;
	}

	/**
	 * @return the adapterMessage
	 */
	public AdapterMessage getAdapterMessage() {
		return adapterMessage;
	}

	/**
	 * @param listMessage the listMessage to set
	 */
	public void setListMessage(ArrayList<Message> listMessage) {
		this.listMessage = listMessage;
	}

	/**
	 * @return the listMessage
	 */
	public ArrayList<Message> getListMessage() {
		return listMessage;
	}

	/**
	 * @param me the me to set
	 */
	public void setMe(VueListeDesMessages me) {
		this.me = me;
	}

	/**
	 * @return the me
	 */
	public VueListeDesMessages getMe() {
		return me;
	}
}// class
