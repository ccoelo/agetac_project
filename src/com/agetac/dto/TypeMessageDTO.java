/**
 * 
 */
package com.agetac.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.agetac.dto.dto_config.DTO;
import com.agetac.model.TypeMessage;
import com.agetac.observer.Observer;
import com.agetac.tomcat.ExecuteRequestSQL;


/**
 * @author Anthony LE MEE
 * 
 */
public class TypeMessageDTO extends DTO<TypeMessage> implements Observer {
	
	private Map<String, String> donneesRecuperees = new HashMap<String, String>();

	/** Méthode UPDATE */
	public void update(String idAsker, String json) {
		if(this.getDonneesRecuperees().containsKey(idAsker)) {
			this.getDonneesRecuperees().remove(idAsker);
		}
		this.getDonneesRecuperees().put(idAsker, json);
	}
	
	/**
	 * @param donneesRecuperees the donneesRecuperees to set
	 */
	public void setDonneesRecuperees(Map<String, String> donneesRecuperees) {
		this.donneesRecuperees = donneesRecuperees;
	}

	/**
	 * @return the donneesRecuperees
	 */
	public Map<String, String> getDonneesRecuperees() {
		return donneesRecuperees;
	}
	
	@Override
	public TypeMessage[] findAll(int limit) {

		TypeMessage[] m = new TypeMessage[0];
		
		if (super.update != null) {
			
			ExecuteRequestSQL executerSQL;
			
			if (limit < 0) 
			{
				
				executerSQL = new ExecuteRequestSQL(super.update, "SELECT * FROM message",null, this);
				
			}
			else
			{
				
				executerSQL = new ExecuteRequestSQL(super.update, "SELECT * FROM message LIMIT " + limit,null, this);
				
			}
			
			// Création de la clé unique que l'on communiquera à executerSQL
			Random r = new Random();
			int key = 100000 + r.nextInt(999999 - 0);
			while (this.getDonneesRecuperees().containsKey(key)) {
				r = new Random();
				key = 100000 + r.nextInt(999999 - 0);
			} 
			
			// On donne notre identifiant à executerSQL
			executerSQL.setIdAsker("" + key + "");
			// On lui dit de se lancer
			executerSQL.start();
			// On attend qu'il est fini sont traitement pour qu'il est le temps de nous retourner sa réponse
			while(executerSQL.isAlive()) {}
			
			JSONArray jsonArray;
			String jsonArrayText;
			
			// On parse la réponse en JSONArray
			try 
			{
				// Si la requête c'est bien exécutée
				jsonArrayText 	= this.getDonneesRecuperees().get(""+key+"");
				if (!jsonArrayText.equals(new String("ERROR"))) {
					
					jsonArray = new JSONArray(jsonArrayText);
					
					int idTypeMessage;
					String libelleTypeMessage;
					
					int nbrTypeMessageTrouves = jsonArray.length();
					m = new TypeMessage[nbrTypeMessageTrouves];
										
					for (int i = 0 ; i < nbrTypeMessageTrouves ; i++) 
					{
						try {
							
							idTypeMessage 		= Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_typemessage" + "").toString());
							libelleTypeMessage	= jsonArray.getJSONObject(i).get("" + "libelletypemessage" + "").toString();
							
							
							
							Log.d("Antho",  "Nouveau typeMessage : " + 
											" ["+ idTypeMessage + "] " + 
											" ["+ libelleTypeMessage + "]");
							m[i] = new TypeMessage(
									idTypeMessage,
									libelleTypeMessage);
							
						} catch (NumberFormatException e) {
							System.err.println("Impossible de parser un des id en Integer.");
							e.printStackTrace();
						} catch (JSONException e) {
							System.err.println("Erreur sur un JSONObject.");
							e.printStackTrace();
						}
					}
				}
				
				this.getDonneesRecuperees().remove(key);
				
			}
			catch (JSONException  e) 
			{
				System.err.println("Impossible de parser notre jsonArrayText en JSONArray.");
			}

		}
		
		return m;
	}

	@Override
	public TypeMessage[] find(String[] champs, int limit) {
		return null;
	}

	@Override
	public TypeMessage findById(int id) {
		return null;
		
	}

	@Override
	public boolean create(TypeMessage obj) {
		return true;
	}

	@Override
	public TypeMessage update(TypeMessage obj) {
		return null;
	}

	@Override
	public void delete(TypeMessage obj) {
		
	}

	/* (non-Javadoc)
	 * @see com.agetac.dto.dto_config.DTO#find(int, boolean)
	 */
	@Override
	public int find(int i, boolean b) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void update() {}
	
}
