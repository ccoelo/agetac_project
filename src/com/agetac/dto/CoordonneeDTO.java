/**
 * 
 */
package com.agetac.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.agetac.dto.dto_config.DTO;
import com.agetac.model.Coordonnees;
import com.agetac.observer.Observer;
import com.agetac.tomcat.ExecuteRequestSQL;


/**
 * @author jimmy
 *
 */
public class CoordonneeDTO extends DTO<Coordonnees> implements Observer {

	private Map<String, String> donneesRecuperees = new HashMap<String, String>();

	@Override
	public Coordonnees[] findAll(int limit) {
		return null;
	}

	@Override
	public Coordonnees[] find(String[] champs, int limit) {
		return null;
	}

	/**
	 * pour trouver la coordonnée des camions sur la carte
	 */
	public Coordonnees findById(int id) {
		Coordonnees m = new Coordonnees();

		if (super.update != null) {

			ExecuteRequestSQL executerSQL;
				executerSQL = new ExecuteRequestSQL(super.update,
						"SELECT * FROM coordgps WHERE id_coorgps = " + id,null, this);
				executerSQL.setServlet("ExecuteSqlQuery?q=");
			
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
			// On attend qu'il est fini sont traitement pour qu'il est le temps
			// de nous retourner sa réponse
			while (executerSQL.isAlive()) {
			}

			JSONObject jSon;
			String jsonArrayText;

			// On parse la réponse en JSONArray
			try {
				// Si la requête c'est bien exécutée
				jsonArrayText = this.getDonneesRecuperees().get("" + key + "");
				if (!jsonArrayText.equals(new String("ERROR"))) {

					jSon = new JSONObject(jsonArrayText);

					int lat;
					int longit;

					try {

						lat = Integer.parseInt(jSon.get(
								"" + "latitude" + "").toString());
						longit = Integer.parseInt(jSon.get(
								"" + "longitude" + "").toString());
						
						Log.d("Jim", "coordonnees : " + " [" + longit + "] " + " [" + lat + "] ");

						m = new Coordonnees(id,lat,longit);

					} catch (NumberFormatException e) {
						System.err
								.println("Impossible de parser un des id en Integer.");
						e.printStackTrace();
					} catch (JSONException e) {
						System.err.println("Erreur sur le JSONObject.");
						e.printStackTrace();
					}
				}
				this.getDonneesRecuperees().remove(key);
			} catch (JSONException e) {
				System.err
						.println("Impossible de parser notre jsonArrayText en JSONObject.");
			}
		}
		return m;
	}

	@Override
	public boolean create(Coordonnees obj) {
		return true;
	}

	@Override
	public Coordonnees update(Coordonnees obj) {
		return null;
	}

	@Override
	public void delete(Coordonnees obj) {
	}

	/* (non-Javadoc)
	 * @see com.agetac.dto.dto_config.DTO#find(int, boolean)
	 */
	@Override
	public int find(int i, boolean b) {
		return 0;
	}

	/** Méthode UPDATE */
	public void update(String idAsker, String json) {
		if (this.getDonneesRecuperees().containsKey(idAsker)) {
			this.getDonneesRecuperees().remove(idAsker);
		}
		this.getDonneesRecuperees().put(idAsker, json);
	}

	public void update() {}
	
	/**
	 * @param donneesRecuperees
	 *            the donneesRecuperees to set
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
