/*
 * Projet AGETAC
 * Benjamin Plouzennec
 * Universite de Rennes 1
 * ISTIC
 */
package com.agetac.activity;


import java.sql.Timestamp;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agetac.Infos;
import com.agetac.R;
import com.agetac.activity.handler.UpdateView;
import com.agetac.activity.handler.Updater;
import com.agetac.dto.MessageDTO;
import com.agetac.dto.dto_config.DTOFactory;
import com.agetac.fragment.VueListeDesMessages;
import com.agetac.model.Message;
import com.agetac.observer.Observer;
import com.agetac.observer.Subject;


/**
 * Classe VueMessageQuelconque : Activité pour l'envoie d'un message quelconque
 * 
 * @author Benjamin Plouzennec - 12004549
 */
public class VueMessageQuelconque extends Activity implements UpdateView, Subject {
	
	/** Infos venant de la vue principale déterminant la portée du message */
	private String destName; 					// Nom du destinataire choisi
	private int destinataireId; 				// Id du destinataire choisi
	private VueListeDesMessages vueListMessage;	// Instance de la vue liste de message
	
	/** Données reçues */
	private Bundle extras;

	/** Instance des Handler */
	Updater handler;
	
	/** Instances des modèles à utiliser */
	private MessageDTO 	mMessage;
	
	/** Zone de texte et boutons*/
	private EditText texteMessage;
	private Button bouttonEnvoyer;
	private Button bouttonRetour;
	private Button bouttonJoindre;
	private String contenuMessage;
	
	/**
	 * Méthode qui affiche un toast suite à la réception d'un message
	 * @param message
	 */
	public void onMessageReveive(String message) {
		Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	/** Méthode onCreate */
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.maquette_message_quelconque);

		if (savedInstanceState != null) {
			super.onRestoreInstanceState(savedInstanceState);
		}

		// On cache par défaut le clavier
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		// Instanciations des modèles 
		this.mMessage 		= (MessageDTO) DTOFactory.getMessage();
		this.handler 		= new Updater(this);
		this.mMessage.update= this.handler;
		
		// Récupération des éléments de l'interface
		texteMessage = (EditText) findViewById(R.id.texteMessageToSend);
		bouttonEnvoyer = (Button) findViewById(R.id.bouttonEnvoyer);
		bouttonJoindre = (Button) findViewById(R.id.bouttonJoindre);
		
		// Listener pour joindre un fichier
		bouttonJoindre.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, 0);
			}
		});
		
		// Listener qui charge le texte tapé dans notre variable encapsulée contenuMessage
		texteMessage.addTextChangedListener(new TextWatcher() {
		    public void afterTextChanged(Editable s) 
		    {
		    	contenuMessage = s.toString();
		    }
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		});
		
		// On récupère les données envoyés par le fragment MvcVisualisationMoyen
		this.extras = getIntent().getExtras();
		
		// Si aucun état précédent n'a été sauvegardé
		if (savedInstanceState == null) {
		    
		    // Si aucune donnée n'a été envoyé 
		    if(extras == null) {
		    	
		    	destName 		= "TOUS";
		    	destinataireId 	= -1;
		        
		    // Sinon
		    } else {
		    	
		    	// On récupère le nom et l'id du destinataire courant et que l'on chargera dans la vue
		    	destName 		= extras.getString("labelDest");
		    	destinataireId	= extras.getInt("idDest");
		    	vueListMessage  = (VueListeDesMessages) extras.getSerializable("VUE");
		        
		    }
		    
		// Sinon (si un état à été sauvegardée
		} else {
			
			// On récupère l'état préalablement sauvegardé
			if (savedInstanceState.getBundle("sauvegarde") != null) {
				destName 		= savedInstanceState.getBundle("sauvegarde").getString("labelDest");
				destinataireId 	= savedInstanceState.getBundle("sauvegarde").getInt("idDest");
			}
			else
			{
				destName 		= "TOUS";
		    	destinataireId 	= -1;
			}
			
		}
		
		// On change le champs (sur la vue) destinataire
		((Button)this.findViewById(R.id.BouttonDestinataire)).setText(""+destName+"");
		
		// Récupération de la date courante
		java.util.Date date = new java.util.Date(System.currentTimeMillis()); 
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime()); 
		
		// Création de la clé unique que l'on communiquera à executerSQL [BOUCHON : inutil si la BDD avait été faite avec un INC de l'ID]
		Random r = new Random();
		int key = 100000 + r.nextInt(999999 - 0);
		
		
		final int idinter 			= Infos.getInstance().getIdCouranteIntervention();
		final int idMessage 		= key;
		final int idEmmeteur 		= Infos.getInstance().getIdCourantUtilisateur();
		final Timestamp envoi 		= timestamp;
		final int idtype 			= 12; // HARDCODE !!!! 
		final String libelletype 	= "Message quelconque";
		final String emetteur 		= "Anthony LE MÉE";
		
		bouttonEnvoyer.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				
				if (destinataireId > 0) 
				{
				
					mMessage.create(new Message(
												idinter,
												idMessage,
												idEmmeteur,
												destinataireId,
												envoi,
												null,
												contenuMessage,
												idtype,
												libelletype,
												emetteur,
												false));
					
					// On met à jours la liste de message afin de voir ce nouveau message
					//	vueListMessage.updateListMessage();
					// On ferme l'activity
					finish();
					
				}// if
				
			}// onClick
			
		});// setOnClickListener
		
	}// Méthode
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
		savedInstanceState.putBundle("sauvegarde", extras);
	    super.onSaveInstanceState(savedInstanceState);
	    
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		 if (resultCode == RESULT_OK){
		 	Uri targetUri = data.getData();
		 	texteMessage.setText(targetUri.toString());
		 }
	}// Méthode
	
	public void attach(Observer o) {
		
	}

	public void detach(Observer o) {
		
	}

	public void notifyDTO() {}

	public void notifyFragment() {
		
	}
	
	/**********************************************/
	/**********   GETTERS AND SETTERS     *********/
	/**********************************************/

	/**
	 * @return Zone de texte du message
	 */
	public EditText getTexteMsg() {
		return texteMessage;
	}

	/**
	 * @param textMessage La zone de texte qui remplacera la zone de texte du message
	 * 
	 */
	public void setTexteMsg(EditText textMessage) {
		this.texteMessage = textMessage;
	}

	/**
	 * @return Le boutton d'envoie
	 */
	public Button getBouttonEnvoyer() {
		return bouttonEnvoyer;
	}

	/**
	 * @param bouttonEnvoyer Boutton qui remplacera le boutton actuel d'envoie
	 * 
	 */
	public void setBouttonEnvoyer(Button bouttonEnvoyer) {
		this.bouttonEnvoyer = bouttonEnvoyer;
	}
	
	/**
	 * @return Boutton de retour
	 */
	public Button getBouttonRetour() {
		return bouttonRetour;
	}

	/**
	 * @param bouttonEnvoyer Boutton qui remplacera le boutton actuel de retour
	 * 
	 */
	public void setBouttonRetour(Button bouttonRetour) {
		this.bouttonRetour = bouttonRetour;
	}
	
	/**
	 * @return Boutton pour joindre des fichiers
	 */
	public Button getBouttonJoindre() {
		return bouttonJoindre;
	}

	/**
	 * @param bouttonEnvoyer Boutton qui remplacera le boutton actuel pour joindre des fichiers
	 * 
	 */
	public void setBouttonJoindre(Button bouttonJoindre) {
		this.bouttonJoindre = bouttonJoindre;
	}

	/**
	 * @param destName the destName to set
	 */
	public void setDestName(String destName) {
		this.destName = destName;
	}

	/**
	 * @return the destName
	 */
	public String getDestName() {
		return destName;
	}

	/**
	 * @param destinataireId the destinataireId to set
	 */
	public void setDestinataireId(int destinataireId) {
		this.destinataireId = destinataireId;
	}

	/**
	 * @return the destinataireId
	 */
	public int getDestinataireId() {
		return destinataireId;
	}

	/**
	 * @return the vueListMessage
	 */
	public VueListeDesMessages getVueListMessage() {
		return vueListMessage;
	}

	/**
	 * @param vueListMessage the vueListMessage to set
	 */
	public void setVueListMessage(VueListeDesMessages vueListMessage) {
		this.vueListMessage = vueListMessage;
	}

}
