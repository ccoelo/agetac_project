package com.agetac.dto;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.agetac.Infos;
import com.agetac.dto.dto_config.DTO;
import com.agetac.model.Coordonnees;
import com.agetac.model.Vehicule;
import com.agetac.observer.Observer;
import com.agetac.tomcat.ExecuteRequestSQL;

/**
 * 
 * @author jimmy
 * 
 */
public class VehiculeDTO extends DTO<Vehicule> implements Observer {

	private Map<String, String> donneesRecuperees = new HashMap<String, String>();

	@Override
	public Vehicule[] findAll(int limit) {

		return null;
	}
	
	public Vehicule[] find(String requete) {

		return null;
	}

	@Override
	public int find(int secteur, boolean present) {
		int res = -1;

		if (super.update != null) {

			ExecuteRequestSQL executerSQL;
			if (present) {
				executerSQL = new ExecuteRequestSQL(super.update,
						"SELECT COUNT(id_vehicule) FROM vehi_groupe "
								+ "WHERE id_secteur = " + secteur + " "
								+ "AND id_intervention = "
								+ Infos.getInstance().idCouranteIntervention + " "
								+ "AND datearrivee IS NOT NULL "
								+ "AND dateretourcaserne IS NULL",null, this);
				executerSQL.setServlet("ExecuteSqlQuery?q=");
			} else {
				executerSQL = new ExecuteRequestSQL(super.update,
						"SELECT COUNT(id_vehicule) FROM vehi_groupe "
								+ "WHERE id_secteur = " + secteur + " "
								+ "AND id_intervention = "
								+ Infos.getInstance().idCouranteIntervention + " "
								+ "AND datedemande IS NOT NULL "
								+ "AND datearrivee IS NULL" + "",null, this);
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

			// Si la requête c'est bien exécutée
			jsonArrayText = this.getDonneesRecuperees().get("" + key + "");
			if (!jsonArrayText.equals(new String("ERROR"))) {

				
				try {
					jsonArray = new JSONArray(jsonArrayText);
					res = Integer.parseInt(jsonArray.getJSONObject(0).getString("count").toString());
				} catch (NumberFormatException e) {
					System.err.println("Impossible de parser l'id \"" + secteur
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

	@Override
	public Vehicule findById(int id) {
		
		Vehicule v = new Vehicule();

		return v;
		
	}

	@Override
	public boolean create(Vehicule obj) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Vehicule update(Vehicule obj) {
		
		if (super.update != null) {

			Date dateNow = new Date();
			
			ExecuteRequestSQL executerSQL;
			executerSQL = new ExecuteRequestSQL(super.update,
							("UPDATE plan SET id_caserne = " + obj.getIdCaserne() + ","
							+ " id_typevehicule = " + obj.getTypeVehicule() + ","
							+ "id_intervention = " + Infos.getInstance().idCouranteIntervention + ","
							+ "id_secteur = " + obj.getIdSecteur() + ","
							+ "id_groupe = " + obj.getIdGroupeInter() + ","
							+ "datedeplacement = " + Timestamp.valueOf(dateNow.toString()) + ","
							+ "id_personne = " + Infos.getInstance().idCourantUtilisateur + ","
							+ "coordx = " + obj.getPosition().getX() + ","
							+ "coordy = " + obj.getPosition().getY()
							+ " WHERE id_vehicule = " + obj.getId()),null, this);
			
			executerSQL.setServlet("ExecuteUpdatePlan?q=");

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

			String jsonArrayText;

			// Si la requête c'est bien exécutée
			jsonArrayText = this.getDonneesRecuperees().get("" + key + "");
			if (!jsonArrayText.equals(new String("ERROR"))) {

					Log.d("Jim",
							"plan bien updaté : ");
			}
			this.getDonneesRecuperees().remove(key);
		}
		return obj;
	}

	@Override
	public void delete(Vehicule obj) {
		try {
			this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE).executeUpdate(
					"DELETE FROM Vehicule WHERE id = " + obj.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.agetac.observer.Observer#update(java.lang.String,
	 * java.lang.String)
	 */
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

	
	/**
	 * permet de récupérer les véhicules d'un groupe donné
	 * le "champs" ne sert à rien ici
	 */
	public Vehicule[] find(String[] champs, int groupe) {
		Vehicule[] m = new Vehicule[0];

		if (super.update != null) {

			ExecuteRequestSQL executerSQL;
			executerSQL = new ExecuteRequestSQL(super.update,
					"SELECT * FROM vehi_groupe WHERE id_intervention = "
							+ Infos.getInstance().idCouranteIntervention
							+ "AND id_groupe = " + groupe
							+ " ORDER BY datedemande DESC",null, this);
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

			// On parse la réponse en JSONArray
			try {
				// Si la requête c'est bien exécutée
				jsonArrayText = this.getDonneesRecuperees().get("" + key + "");
				if (!jsonArrayText.equals(new String("ERROR"))) {

					jsonArray = new JSONArray(jsonArrayText);

					int id_cas;
					int id_type;
					int id_ve;
					int id_inter;
					int id_sec;
					int id_groupe;
					Timestamp datedemande;
					Timestamp datedepartcaserne;
					Timestamp datearrivee;
					Timestamp dateretourcaserne;
					Timestamp datesecteurfinal;
					int secteurFinal;
					
					int nbrMessageTrouves = jsonArray.length();
					m = new Vehicule[nbrMessageTrouves];

					for (int i = 0; i < nbrMessageTrouves; i++) {
						try {

							id_inter = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "id_intervention" + "")
									.toString());
							id_sec = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "id_secteur" + "").toString());
							id_groupe = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "id_groupe" + "").toString());
							id_ve = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "id_vehicule" + "")
									.toString());
							id_cas = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "id_caserne" + "").toString());
							id_type = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "id_typevehicule" + "").toString());
							secteurFinal = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "secteurfinal" + "").toString());
							datedemande = Timestamp.valueOf(jsonArray
									.getJSONObject(i)
									.get("" + "datedemande" + "").toString());
							datedepartcaserne = Timestamp.valueOf(jsonArray
									.getJSONObject(i).get("" + "datedepartcaserne" + "")
									.toString());
							datearrivee = Timestamp.valueOf(jsonArray
									.getJSONObject(i)
									.get("" + "datearrivee" + "").toString());
							dateretourcaserne = Timestamp.valueOf(jsonArray
									.getJSONObject(i).get("" + "dateretourcaserne" + "")
									.toString());
							datesecteurfinal = Timestamp.valueOf(jsonArray
									.getJSONObject(i).get("" + "datesecteurfinal" + "")
									.toString());
							
							Log.d("Jim",
									"Nouveau vehicule : " + " [" + id_cas + "] "
											+ " [" + id_groupe + "] " + " ["
											+ id_ve + "] " + " ["
											+ id_inter + "] "
											+ " [" + id_sec + "] " + " ["
											+ id_type + "] " + " ["
											+ secteurFinal + "] ");

							m[i] = new Vehicule(id_cas,id_type,id_ve,id_inter,id_sec,id_groupe,
									datearrivee,datedemande,datedepartcaserne,dateretourcaserne,
									datesecteurfinal,secteurFinal, null, null);

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
	
	public void update() {}

	/**
	 * @param int
	 * @return
	 */
	public Vehicule[] findByIdSecteur(int idS) {

		Vehicule[] v = new Vehicule[0];
		
		if (super.update != null) {
			
			ExecuteRequestSQL executerSQL;
		
			executerSQL = new ExecuteRequestSQL(super.update,
												"SELECT * FROM vehi_groupe vg " +
												"INNER JOIN vehicule v ON " +
												"vg.id_typevehicule = v.id_typevehicule " +
												"AND vg.id_caserne = v.id_caserne " +
												"AND vg.id_vehicule = v.id_vehicule " +
												"INNER JOIN secteur s ON " +
												"vg.id_secteur = s.id_secteur " +
											    "WHERE vg.id_secteur = " 
							                    + idS + " " +
											    "AND vg.id_intervention = " 
											    + Infos.getInstance().idCouranteIntervention + " " +
											    "AND vg.datearrivee IS NOT NULL " +
											    "AND vg.dateretourcaserne IS NULL" +
											    ""
												,null, this);
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
	
			// On parse la réponse en JSONArray
			try 
			{
				// Si la requête c'est bien exécutée
				jsonArrayText 	= this.getDonneesRecuperees().get(""+key+"");
				if (!jsonArrayText.equals(new String("ERROR"))) 
				{
					
					jsonArray = new JSONArray(jsonArrayText);
				
					int disponibilite;
					int typeVehicule;
					Coordonnees position;
					int id;
					int idCaserne;
					int idInter;
					int idSecteur;
					int idGroupeInter;
					Date demande;
					Date arrivee;
					Date departCaserne;
					Date retour;
					Date secteurFDate;
					int secteurF;
					String name;
					String nameSecteur;
					
					int nbrVehiculesTrouves = jsonArray.length();
					v = new Vehicule[nbrVehiculesTrouves];
										
					for (int i = 0 ; i < nbrVehiculesTrouves ; i++) 
					{
						try {
							
							disponibilite = 1;
							typeVehicule = Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_typevehicule" + "").toString());
							position = null;
							id = Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_vehicule" + "").toString());
							idCaserne = Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_caserne" + "").toString());
							idInter = Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_intervention" + "").toString());
							idSecteur = Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_secteur" + "").toString());
							idGroupeInter = Integer.parseInt(jsonArray.getJSONObject(i).get("" + "id_groupe" + "").toString());
							demande = null;
							arrivee = null;
							departCaserne = null;
							retour = null;
							secteurFDate = null;
							secteurF = 1;
							name = jsonArray.getJSONObject(i).get("" + "libellevehicule" + "").toString().replaceAll("( ){2,}", " ");
							nameSecteur = jsonArray.getJSONObject(i).get("" + "libellesecteur" + "").toString().replaceAll("( ){2,}", " "); 
							
							v[i] = new Vehicule(idCaserne, typeVehicule, id, idInter,
									idSecteur, idGroupeInter, arrivee,
									demande, departCaserne,
									retour, secteurFDate,
									secteurF, name, nameSecteur);
							
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
					
				}
				
			}catch (JSONException  e) 
			{
				System.err.println("Impossible de parser notre jsonArrayText en JSONArray. VEHICULE");
				e.printStackTrace();
			}
			
			this.getDonneesRecuperees().remove(key);
	
		}
			
		return v;
		
	}
	
}
