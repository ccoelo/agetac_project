package com.agetac.dto;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.agetac.Infos;
import com.agetac.dto.dto_config.DTO;
import com.agetac.model.Agent;
import com.agetac.observer.Observer;
import com.agetac.tomcat.ExecuteRequestSQL;


/**
 * 
 * @author jimmy
 *
 */
public class AgentDTO extends DTO<Agent> implements Observer {
	
	private Map<String, String> donneesRecuperees = new HashMap<String, String>();
	
	@Override
	public Agent[] findAll(int limit) {

		Agent[] m = new Agent[0];
		
		if (super.update != null) {
			
			ExecuteRequestSQL executerSQL;
			
			if (limit < 0) 
			{
				
				executerSQL = new ExecuteRequestSQL(
						super.update, 
						"SELECT * FROM personne p " +
						"INNER JOIN resp_groupe rp ON p.id_personne = rp.id_personne " +
						"INNER JOIN grade g ON p.id_grade = g.id_grade " +
						"WHERE id_intervention = "+Infos.getInstance().idCouranteIntervention+" " +
						"AND p.id_personne != "+Infos.getInstance().idCourantUtilisateur+" ",
						null,
						this);
				executerSQL.setServlet("ExecuteSqlQuery?q=");
				
			}
			else
			{
				
				executerSQL = new ExecuteRequestSQL(
						super.update,
						"SELECT * FROM personne p " +
						"INNER JOIN resp_groupe rp ON p.id_personne = rp.id_personne " +
						"INNER JOIN grade g ON p.id_grade = g.id_grade " +
						"WHERE id_intervention = "+Infos.getInstance().idCouranteIntervention+" " +
						"AND p.id_personne != "+Infos.getInstance().idCourantUtilisateur+" " +
						"LIMIT " + limit,
						null, 
						this);
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
					
					int id;
					String nom;
					String prenom;
					int rang;
					String grade;
					int idInter;
					String abrvGrade;
					
					int nbrSecteursTrouves = jsonArray.length();
					m = new Agent[nbrSecteursTrouves];
										
					for (int i = 0 ; i < nbrSecteursTrouves ; i++) 
					{
						try {
							
							id 			= Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_personne" + "").toString());
							nom 		= jsonArray.getJSONObject(i).get("" + "nom" + "").toString().replaceAll("( ){2,}", " ");
							prenom 		= jsonArray.getJSONObject(i).get("" + "prenom" + "").toString().replaceAll("( ){2,}", " ");
							rang 		= Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_grade" + "").toString());
							grade 		= jsonArray.getJSONObject(i).get("" + "libellegrade" + "").toString().replaceAll("( ){2,}", " ");
							idInter 	= Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_intervention" + "").toString());
							abrvGrade	= jsonArray.getJSONObject(i).get("" + "abrevgrade" + "").toString().replaceAll("( ){2,}", " ");
							
							Log.d("Antho",  "Nouveau secteur : " + 
											" ["+ id + "] " + 
											" ["+ nom + "] " +
											" ["+ prenom + "] " +
											" ["+ rang + "] " +
											" ["+ grade + "] " +
											" ["+ idInter + "] " +
											" ["+ abrvGrade + "] ");
							m[i] = new Agent (
									 id,
									 nom,
									 prenom,
									 rang,
									 grade,
									 idInter,
									 abrvGrade);
							
						} catch (NumberFormatException e) {
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
	public Agent[] find(String[] champs, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agent findById(int id) {
		
		return null;
	}

	@Override
	public boolean create(Agent obj) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Agent update(Agent obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Agent obj) {
		try {
            this    .connect
                	.createStatement(
                         ResultSet.TYPE_SCROLL_INSENSITIVE, 
                         ResultSet.CONCUR_UPDATABLE
                    ).executeUpdate(
                         "DELETE FROM Agent WHERE id = " + obj.getId()
                    );
		
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}

	/* (non-Javadoc)
	 * @see com.agetac.dto.dto_config.DTO#find(int, boolean)
	 */
	@Override
	public int find(int i, boolean b) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void update(String idAsker, String json) {
			if(this.getDonneesRecuperees().containsKey(idAsker)) {
				this.getDonneesRecuperees().remove(idAsker);
			}
			this.getDonneesRecuperees().put(idAsker, json);
	}
	
	public void update() {}

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
	
}
