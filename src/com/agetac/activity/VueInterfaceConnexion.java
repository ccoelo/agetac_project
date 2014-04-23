/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agetac.Infos;
import com.agetac.R;
import com.agetac.controller.ListenerSwitchToActivity;
import com.agetac.dto.AgentDTO;
import com.agetac.dto.dto_config.DTO;
import com.agetac.dto.dto_config.DTOFactory;
import com.agetac.fragment.age_Main;
import com.agetac.model.Agent;



/**
* Classe VueInterfaceConnexion : première activity lancée. Sert d'authentification aux utilisateurs.
*
* @version 1.0 30/01/2013
* @author Marion GUILLEMIN - 29001737
*/
public class VueInterfaceConnexion extends Activity {
	
	/** EditText Identifiant */
	private EditText identifiant;
	
	/** EditText Mot de passe */
	private EditText mdpasse;
	
	/** Bouton de connexion */
	private Button bouttonConnexion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.maquette_interface_connexion);
		
		// On cache par défaut le clavier
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		//Récupération des éléments de la maquette via leur id
		bouttonConnexion = (Button) findViewById(R.id.btnLogin);
		identifiant = (EditText) findViewById(R.id.label_ident);
		mdpasse = (EditText) findViewById(R.id.label_mdp);

		// Listener sur le boutton connexion pour le traitement des identifiants renseignés
		final Activity me = this;
		bouttonConnexion.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				//Tests pour vérifier si les champs sont remplis.
				if(identifiant.getText().toString().equals(new String ("")) || identifiant == null)
				{
					Toast.makeText(me.getApplicationContext(), "Veuillez renseignez un nom d'utilisateur", Toast.LENGTH_SHORT).show();
					if(mdpasse.getText().toString().equals(new String ("")) || mdpasse == null)
					{
					Toast.makeText(me.getApplicationContext(), "Veuillez renseignez un mot de passe", Toast.LENGTH_SHORT).show();
					}
				}
				else if(mdpasse.getText().toString().equals(new String ("")) || mdpasse == null)
				{
					Toast.makeText(me.getApplicationContext(), "Veuillez renseignez un mot de passe", Toast.LENGTH_SHORT).show();
				}
				else
				{
				
					//Vérification de l'existance du couple id/mdp dans la base de donnée.
					// LDAP
					  
//					DTO<Agent> agent = DTOFactory.getAgent();
//					String[] idMdp ={identifiant.getText().toString(),mdpasse.getText().toString()};
//					Agent[] agentTab = agent.find(idMdp, 1);
//					
					int agentId = Integer.parseInt(identifiant.getText().toString());
					Infos.getInstance().setIdCouranteIntervention(1);
					Infos.getInstance().setIdCourantUtilisateur(agentId);//21
					
					/*
					Infos.getInstance().setIdCourantUtilisateur(agentId);
					Infos.getInstance().setIdCouranteIntervention(DTOFactory.getIntervention().find(agentId, true));
					 */
					
					Intent i = new Intent(me, age_Main.class);
					startActivity(i);
				
				}
				
			}
			
		});
				
	}

	/**
	 * @param bouttonConnexion the bouttonConnexion to set
	 */
	public void setBouttonConnexion(Button bouttonConnexion) {
		this.bouttonConnexion = bouttonConnexion;
	}

	/**
	 * @return the bouttonConnexion
	 */
	public Button getBouttonConnexion() {
		return bouttonConnexion;
	}
}