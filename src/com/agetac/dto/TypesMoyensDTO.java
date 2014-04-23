/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.agetac.dto.dto_config.DTO;
import com.agetac.model.TypesMoyens;
import com.agetac.observer.Observer;
import com.agetac.tomcat.ExecuteRequestSQL;


/**
 *
 */
public class TypesMoyensDTO extends DTO<TypesMoyens> implements Observer {
	
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
	public TypesMoyens[] findAll(int limit) {

		TypesMoyens[] m = new TypesMoyens[0];
		
		if (super.update != null) {
			
			ExecuteRequestSQL executerSQL;
			
			if (limit < 0) 
			{
				
				executerSQL = new ExecuteRequestSQL(super.update, "SELECT * FROM typevehicule",null, this);
				executerSQL.setServlet("ExecuteSqlQuery?q=");
			}
			else
			{
				
				executerSQL = new ExecuteRequestSQL(super.update, "SELECT * FROM typevehicule LIMIT " + limit,null, this);
				executerSQL.setServlet("ExecuteSqlQuery?q=");
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
					
					Integer id = null;
					String 	libelle;
					Integer	idSecteur;
					
					int nbrTypesMoyensTrouves = jsonArray.length();
					m = new TypesMoyens[nbrTypesMoyensTrouves];
										
					for (int i = 0 ; i < nbrTypesMoyensTrouves ; i++) 
					{
						try {
							
							id 		= Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_typevehicule" + "").toString());
							libelle = jsonArray.getJSONObject(i).get("" + "libelletype" + "").toString();
							idSecteur	= Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_secteur" + "").toString());
							
							Log.d("Antho",  "Nouveau moyen : " + 
											" ["+ id + "] " + 
											" ["+ libelle + "] " + 
											" ["+ idSecteur + "] ");
							m[i] = new TypesMoyens(id, libelle,	idSecteur);
							
						} catch (NumberFormatException e) {
							System.err.println("Impossible de parser l'id \"" +	id + "\" en Integer.");
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
	public TypesMoyens[] find(String[] champs, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypesMoyens findById(int id) {
		return null;
		
	}

	@Override
	public boolean create(TypesMoyens obj) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public TypesMoyens update(TypesMoyens obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(TypesMoyens obj) {
		
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
