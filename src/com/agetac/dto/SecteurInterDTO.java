package com.agetac.dto;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.agetac.Infos;
import com.agetac.dto.dto_config.DTO;
import com.agetac.model.SecteurIntervention;
import com.agetac.observer.Observer;
import com.agetac.tomcat.ExecuteRequestSQL;


/**
 * @author Jimmy DANO & Anthony LE MEE & Jimmy DANO
 * 
 */
public class SecteurInterDTO extends DTO<SecteurIntervention> implements Observer {
	
	private Map<String, String> donneesRecuperees = new HashMap<String, String>();
	
	@Override
	public SecteurIntervention[] findAll(int limit) {
		
		SecteurIntervention[] m = new SecteurIntervention[0];
		
		if (super.update != null) {
			
			ExecuteRequestSQL executerSQL;
			
			if (limit < 0) 
			{
				
				executerSQL = new ExecuteRequestSQL(super.update, "SELECT * FROM secteur as s" +
						", groupe as g WHERE " +
						"g.id_intervention = " + Infos.getInstance().getIdCouranteIntervention() + " " +
						"AND g.id_secteur = s.id_secteur",null, this);
				executerSQL.setServlet("ExecuteSqlQuery?q=");
				
			}
			else
			{
				
				executerSQL = new ExecuteRequestSQL(super.update, "SELECT * FROM secteur as s" +
						", groupe as g WHERE" +
						"g.id_intervention = " + Infos.getInstance().getIdCouranteIntervention()
						+ " AND g.id_secteur = s.id_secteur "+
						"LIMIT " + limit,null, this);
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
					String 	couleur;
					
					int nbrSecteursTrouves = jsonArray.length();
					m = new SecteurIntervention[nbrSecteursTrouves];
										
					for (int i = 0 ; i < nbrSecteursTrouves ; i++) 
					{
						try {
							
							id 		= Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_secteur" + "").toString());
							libelle = jsonArray.getJSONObject(i).get("" + "libellesecteur" + "").toString().replaceAll("( ){2,}", " ");
							couleur	= jsonArray.getJSONObject(i).get("" + "couleur_secteur" + "").toString().replaceAll("( ){2,}", " ");
							
							Log.d("Antho",  "Nouveau secteur : " + 
											" ["+ id + "] " + 
											" ["+ libelle + "] " + 
											" ["+ couleur + "] ");
							m[i] = new SecteurIntervention(id, libelle,	couleur);
							
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
				e.printStackTrace();
			}

		}
		
		return m;

	}

	@Override
	public SecteurIntervention[] find(String[] champs, int limit) {
		return null;
	}

	@Override
	public SecteurIntervention findById(int id) {
		return null;
	}

	@Override
	public boolean create(SecteurIntervention obj) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public SecteurIntervention update(SecteurIntervention obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(SecteurIntervention obj) {


	}

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
