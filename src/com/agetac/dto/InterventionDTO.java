/*	
 * Projet AGETAC	
 * Jimmy Dano	
 * Universite de Rennes 1	
 * ISTIC	
 */
package com.agetac.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;


import com.agetac.dto.dto_config.DTO;
import com.agetac.model.Intervention;
import com.agetac.observer.Observer;
import com.agetac.tomcat.ExecuteRequestSQL;


/**
 * Classe InterventionDTO : DTO (objet de transfert de données) d'une
 * intervention - Propose les fonctions de bases telles que FIND (i.e. SELECT
 * sur id), CREATE, UPDATE et DELETE
 * 
 * @version 2.0 30/01/2013
 * @author Jimmy DANO - 13000159
 */
public class InterventionDTO extends DTO<Intervention> implements Observer{

	private Map<String, String> donneesRecuperees = new HashMap<String, String>();

	@Override
	public Intervention[] findAll(int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Intervention[] find(String[] champs, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Intervention findById(int id) {
		Intervention inter = new Intervention();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE)
					.executeQuery(
							"SELECT * FROM INTERVENTION WHERE id_intervention = " + 0);
			if (result.first()) {
				inter = new Intervention(id /*
											 * , result.getString(
											 * "nom du champ à récup")
											 */);
				System.out.println("youhou");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(inter.getCanal1_2());
		return inter;
	}

	@Override
	public boolean create(Intervention obj) {
		return true;
	}

	@Override
	public Intervention update(Intervention obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeUpdate(
					"UPDATE Intervention SET " + " xxx = '" + " " /* obj.getId() */
							+ "'" + " WHERE id = " + obj.getId());

			obj = this.findById(obj.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public void delete(Intervention obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeUpdate(
					"DELETE FROM Intervention WHERE id = " + obj.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * retourne l'ID de l'intervention correspondant à l'ID de l'agent qui se connecte
	 */
	public int find(int idAgent, boolean b) {
		int res = -1;
		
		if (super.update != null) {

			ExecuteRequestSQL executerSQL = new ExecuteRequestSQL(super.update,
						"SELECT inter.id_intervention FROM intervention as inter, resp_groupe as cos WHERE " +
						"cos.id_intervention = inter.id_intervention AND" +
						"cos.id_personne = " + idAgent,null, this);
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

			JSONArray jsonArray;
			String jsonArrayText;

			// Si la requête c'est bien exécutée
			jsonArrayText = this.getDonneesRecuperees().get("" + key + "");
			if (!jsonArrayText.equals(new String("ERROR"))) {
				try {
					jsonArray = new JSONArray(jsonArrayText);
					//pas sur de moi sur ce résultat mais sait on jamais^^
					res = Integer.parseInt(jsonArray.getJSONObject(0).getString("id_intervention").toString());
				} catch (NumberFormatException e) {
					System.err.println("Impossible de parser l'id \"" + idAgent
							+ "\" en Integer.");
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			this.getDonneesRecuperees().remove(key);
		}
		return res;
	}

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
