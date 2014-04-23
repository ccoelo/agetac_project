package com.agetac.dto;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import com.agetac.Infos;
import com.agetac.dto.dto_config.DTO;
import com.agetac.model.GroupeIntervention;
import com.agetac.observer.Observer;
import com.agetac.tomcat.ExecuteRequestSQL;


/**
 * 
 * @author jimmy
 * 
 */
public class GroupeInterventionDTO extends DTO<GroupeIntervention> implements
		Observer {

	private Map<String, String> donneesRecuperees = new HashMap<String, String>();

	@Override
	public GroupeIntervention[] find(String[] champs, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupeIntervention findById(int id) {
		GroupeIntervention m = new GroupeIntervention(id, id, id, id, id, null);

		if (super.update != null) {

			ExecuteRequestSQL executerSQL;

			if (id > 0) {

				executerSQL = new ExecuteRequestSQL(super.update,
						"SELECT * FROM groupe WHERE id_groupe = " + id,null, this);
				executerSQL.setServlet("ExecuteSqlQuery?q=");
			} else {
				executerSQL = new ExecuteRequestSQL(super.update,
						"SELECT * FROM groupe WHERE id_groupe = 0",null, this);
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

					int idinter;
					int idSecteur;
					int idGroupe;
					Timestamp dateCreation;
					Timestamp dateFin;
					String libelleGr;
					int canalMontant;
					int canalDescendant;

					try {

						idinter = Integer.parseInt(jSon.get(
								"" + "id_intervention" + "").toString());
						idSecteur = Integer.parseInt(jSon.get(
								"" + "id_secteur" + "").toString());
						idGroupe = Integer.parseInt(jSon.get(
								"" + "id_groupe" + "").toString());
						dateCreation = Timestamp.valueOf(jSon.get(
								"" + "datecreation" + "").toString());
						dateFin = Timestamp.valueOf(jSon.get(
								"" + "datefin" + "").toString());

						libelleGr = jSon.get("" + "libellegroupe" + "")
								.toString().replaceAll("( ){2,}", " ");
						canalMontant = Integer.parseInt(jSon.get(
								"" + "canalmontant" + "").toString());
						canalDescendant = Integer.parseInt(jSon.get(
								"" + "canaldescendant" + "").toString());

						Log.d("Jim",
								"Nouveau groupe : " + " [" + idinter + "] "
										+ " [" + idSecteur + "] " + " ["
										+ idGroupe + "] " + " ["
										+ dateCreation.toString() + "] " + " ["
										+ libelleGr + "] " + " ["
										+ canalMontant + "] " + " ["
										+ canalDescendant + "] ");

						m = new GroupeIntervention(idGroupe, idinter,
								idSecteur, canalMontant, canalDescendant,
								libelleGr);

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
	public boolean create(GroupeIntervention obj) {
		// TODO Auto-generated method stub
		return true;
	}

	/** Méthode UPDATE */
	public void update(String idAsker, String json) {
		if (this.getDonneesRecuperees().containsKey(idAsker)) {
			this.getDonneesRecuperees().remove(idAsker);
		}
		this.getDonneesRecuperees().put(idAsker, json);
	}

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

	@Override
	public GroupeIntervention[] findAll(int idSect) {

		GroupeIntervention[] m = new GroupeIntervention[0];

		if (super.update != null) {

			ExecuteRequestSQL executerSQL;

			if (idSect < 0) {

				executerSQL = new ExecuteRequestSQL(super.update,
						"SELECT * FROM groupe WHERE id_intervention = "
								+ Infos.getInstance().idCouranteIntervention
								+ " ORDER BY libellegroupe DESC",null, this);
				executerSQL.setServlet("ExecuteSqlQuery?q=");
			} else {
				executerSQL = new ExecuteRequestSQL(super.update,
						"SELECT * FROM groupe WHERE id_secteur = " + idSect
								+ " AND id_intervention = "
								+ Infos.getInstance().idCouranteIntervention
								+ " ORDER BY libellegroupe DESC",null, this);
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
			// On attend qu'il est fini sont traitement pour qu'il est le temps
			// de nous retourner sa réponse
			while (executerSQL.isAlive()) {
			}

			JSONArray jsonArray;
			String jsonArrayText;

			// On parse la réponse en JSONArray
			try {
				// Si la requête c'est bien exécutée
				jsonArrayText = this.getDonneesRecuperees().get("" + key + "");
				if (!jsonArrayText.equals(new String("ERROR"))) {

					jsonArray = new JSONArray(jsonArrayText);

					int idinter;
					int idSecteur;
					int idGroupe;
					Timestamp dateCreation;
					Timestamp dateFin;
					String libelleGr;
					int canalMontant;
					int canalDescendant;

					int nbrMessageTrouves = jsonArray.length();
					m = new GroupeIntervention[nbrMessageTrouves];

					for (int i = 0; i < nbrMessageTrouves; i++) {
						try {

							idinter = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "id_intervention" + "")
									.toString());
							idSecteur = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "id_secteur" + "").toString());
							idGroupe = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "id_groupe" + "").toString());
							dateCreation = Timestamp.valueOf(jsonArray
									.getJSONObject(i)
									.get("" + "datecreation" + "").toString());
							dateFin = Timestamp.valueOf(jsonArray
									.getJSONObject(i).get("" + "datefin" + "")
									.toString());

							libelleGr = jsonArray.getJSONObject(i)
									.get("" + "libellegroupe" + "").toString()
									.replaceAll("( ){2,}", " ");
							canalMontant = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "canalmontant" + "").toString());
							canalDescendant = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "canaldescendant" + "")
									.toString());

							Log.d("Jim",
									"Nouveau groupe : " + " [" + idinter + "] "
											+ " [" + idSecteur + "] " + " ["
											+ idGroupe + "] " + " ["
											+ dateCreation.toString() + "] "
											+ " [" + libelleGr + "] " + " ["
											+ canalMontant + "] " + " ["
											+ canalDescendant + "] ");

							m[i] = new GroupeIntervention(idGroupe, idinter,
									idSecteur, canalMontant, canalDescendant,
									libelleGr);

						} catch (NumberFormatException e) {
							System.err
									.println("Impossible de parser un des id en Integer.");
							e.printStackTrace();
						} catch (JSONException e) {
							System.err.println("Erreur sur un JSONObject.");
							e.printStackTrace();
						}
					}
				}

				this.getDonneesRecuperees().remove(key);

			} catch (JSONException e) {
				System.err
						.println("Impossible de parser notre jsonArrayText en JSONArray.");
			}

		}

		return m;
	}

	@Override
	public void delete(GroupeIntervention obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeUpdate(
					"DELETE FROM GroupeIntervention WHERE id = " + obj.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.agetac.dto.dto_config.DTO#update(java.lang.Object)
	 */
	@Override
	public GroupeIntervention update(GroupeIntervention obj) {
		// TODO Auto-generated method stub
		return null;
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
