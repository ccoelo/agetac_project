/**
 * 
 */
package com.agetac.dto;


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
import com.agetac.model.Message;
import com.agetac.observer.Observer;
import com.agetac.tomcat.ExecuteRequestSQL;


/**
 * @author Jimmy DANO & Anthony LE MEE
 */
public class MessageDTO extends DTO<Message> implements Observer {

	private Map<String, String> donneesRecuperees = new HashMap<String, String>();

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
	public Message[] findAll(int limit) {

		Message[] m = new Message[0];

		if (super.update != null) {

			ExecuteRequestSQL executerSQL;

			if (limit < 0) {

				executerSQL = new ExecuteRequestSQL(
						super.update,
						"SELECT m.id_intervention, m.id_message,tm.libelleTypeMessage, p.prenom, p.nom, " +
						"m.id_persemetteur, m.dateenvoi, m.textemessage, m.id_typemessage,a.id_personne_receptrice, a.is_lu FROM message m "
								+ "INNER JOIN typeMessage tm ON m.id_typemessage = tm.id_typemessage "
								+ "INNER JOIN personne p ON m.id_persemetteur = p.id_personne "
								+ "INNER JOIN assoc_message_personne a ON m.id_message = a.id_message "
								+ "WHERE m.id_intervention = "
								+ Infos.getInstance()
										.getIdCouranteIntervention() + " "
								+ "ORDER BY m.dateenvoi DESC", null, this);
				executerSQL.setServlet("ExecuteSqlQuery?q=");
			} else {

				executerSQL = new ExecuteRequestSQL(
						super.update,
						"SELECT m.id_intervention, m.id_message,tm.libelleTypeMessage, p.prenom, p.nom, " +
						"m.id_persemetteur, m.dateenvoi, m.textemessage,m.id_typemessage,a.id_personne_receptrice, a.is_lu FROM  message m "
								+ "INNER JOIN typeMessage tm ON m.id_typemessage = tm.id_typemessage "
								+ "INNER JOIN personne p ON m.id_persemetteur = p.id_personne "
								+ "INNER JOIN assoc_message_personne a ON m.id_message = a.id_message "
								+ "WHERE m.id_intervention = "
								+ Infos.getInstance()
										.getIdCouranteIntervention() + " "
								+ "ORDER BY m.dateenvoi DESC LIMIT " + limit,
						null, this);
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
					int idMessage;
					int idEmmeteur;
					// int idRecepteur;
					Timestamp envoi;
					// Timestamp lecture;
					String contenu;
					int idtype;
					String libelletype;
					String emetteur;
					int islu;
					int idRecepteur;

					int nbrMessageTrouves = jsonArray.length();
					m = new Message[nbrMessageTrouves];

					for (int i = 0; i < nbrMessageTrouves; i++) {
						try {

							idinter = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "id_intervention" + "")
									.toString());
							idMessage = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "id_message" + "").toString());
							idEmmeteur = Integer.parseInt(jsonArray
									.getJSONObject(i)
									.get("" + "id_persemetteur" + "")
									.toString());
							envoi = Timestamp.valueOf(jsonArray
									.getJSONObject(i)
									.get("" + "dateenvoi" + "").toString());
							contenu = jsonArray.getJSONObject(i)
									.get("" + "textemessage" + "").toString()
									.replaceAll("( ){2,}", " ");
							idtype = Integer
									.parseInt(jsonArray.getJSONObject(i)
											.get("" + "id_typemessage" + "")
											.toString());
							libelletype = jsonArray.getJSONObject(i)
									.get("" + "libelletypemessage" + "")
									.toString().replaceAll("( ){2,}", " ");
							emetteur = jsonArray.getJSONObject(i)
									.get("" + "prenom" + "").toString()
									.replaceAll("( ){2,}", " ")
									+ " "
									+ jsonArray.getJSONObject(i)
											.get("" + "nom" + "").toString()
											.replaceAll("( ){2,}", " ");
							islu = Integer
									.parseInt(jsonArray.getJSONObject(i)
											.get("" + "is_lu" + "")
											.toString());

							Log.d("Jim", "Nouveau message : " + " ["
									+ idinter + "] " + " [" + idMessage
									+ "] " + " [" + idEmmeteur + "] "
									+ " [" + envoi.toString() + "] " + " ["
									+ contenu + "] " + " [" + idtype + "] "
									+ " [" + libelletype + "] "
									+ " [" + islu + "] ");
							
							Log.d("Antho", "Nouveau message : " + " ["
									+ idinter + "] " + " [" + idMessage
									+ "] " + " [" + idEmmeteur + "] "
									+ " [" + envoi.toString() + "] " + " ["
									+ contenu + "] " + " [" + idtype + "] "
									+ " [" + libelletype + "] "
									+ " [" + islu + "] ");
							idRecepteur = Integer
									.parseInt(jsonArray.getJSONObject(0)
											.get("" + "id_personne_receptrice" + "")
											.toString());

							m[i] = new Message(idinter, idMessage, idEmmeteur, idRecepteur,
									 envoi, null, contenu, idtype, libelletype,
									emetteur, (islu == 1));
							
						} catch (NumberFormatException e) {
							Log.d("Jim", "Erreur 1  : ");
							System.err
									.println("Impossible de parser un des id en Integer.");
							e.printStackTrace();
						} catch (JSONException e) {
							Log.d("Jim", "Erreur 2  : " + e);
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
	public Message[] find(String[] champs, int limit) {
		return null;
	}

	@Override
	public Message findById(int id) {

		Message m = null;

		if (super.update != null) {

			ExecuteRequestSQL executerSQL;

			if (id >= 0) {

				executerSQL = new ExecuteRequestSQL(
						super.update,
						"SELECT m.id_intervention, m.id_message,tm.libelleTypeMessage, p.prenom, p.nom, " +
						"m.id_persemetteur, m.dateenvoi, m.textemessage,m.id_typemessage,a.id_personne_receptrice, a.is_lu FROM  message m "
								+ "INNER JOIN typeMessage tm ON m.id_typemessage = tm.id_typemessage "
								+ "INNER JOIN personne p ON m.id_persemetteur = p.id_personne "
								+ "INNER JOIN assoc_message_personne a ON m.id_message = a.id_message "
						+ "WHERE m.id_intervention = "
						+ Infos.getInstance()
								.getIdCouranteIntervention() + " " +
						" AND m.id_message = " + id + " "
						+ "ORDER BY dateenvoi DESC", null, this);

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
				// On attend qu'il est fini sont traitement pour qu'il est le
				// temps de nous retourner sa réponse
				while (executerSQL.isAlive()) {
				}

				JSONArray jsonArray;
				String jsonArrayText;

				// On parse la réponse en JSONArray
				try {
					// Si la requête c'est bien exécutée
					jsonArrayText = this.getDonneesRecuperees().get(
							"" + key + "");
					if (!jsonArrayText.equals(new String("ERROR"))) {

						jsonArray = new JSONArray(jsonArrayText);

						int idinter;
						int idMessage;
						int idEmmeteur;
						// int idRecepteur;
						Timestamp envoi;
						// Timestamp lecture;
						String contenu;
						int idtype;
						String libelletype;
						String emetteur;
						int islu;
						int idRecepteur;
						
						int nbrMessageTrouves = jsonArray.length();

						if (nbrMessageTrouves == 1) {

							try {

								idinter = Integer.parseInt(jsonArray
										.getJSONObject(0)
										.get("" + "id_intervention" + "")
										.toString());
								idMessage = Integer
										.parseInt(jsonArray.getJSONObject(0)
												.get("" + "id_message" + "")
												.toString());
								idEmmeteur = Integer.parseInt(jsonArray
										.getJSONObject(0)
										.get("" + "id_persemetteur" + "")
										.toString());
								envoi = Timestamp.valueOf(jsonArray
										.getJSONObject(0)
										.get("" + "dateenvoi" + "").toString());
								contenu = jsonArray.getJSONObject(0)
										.get("" + "textemessage" + "")
										.toString().replaceAll("( ){2,}", " ");
								idtype = Integer.parseInt(jsonArray
										.getJSONObject(0)
										.get("" + "id_typemessage" + "")
										.toString());
								libelletype = jsonArray.getJSONObject(0)
										.get("" + "libelletypemessage" + "")
										.toString().replaceAll("( ){2,}", " ");
								emetteur = jsonArray.getJSONObject(0)
										.get("" + "prenom" + "").toString()
										.replaceAll("( ){2,}", " ")
										+ " "
										+ jsonArray.getJSONObject(0)
												.get("" + "nom" + "")
												.toString()
												.replaceAll("( ){2,}", " ");
								islu = Integer
										.parseInt(jsonArray.getJSONObject(0)
												.get("" + "is_lu" + "")
												.toString());
								idRecepteur = Integer
										.parseInt(jsonArray.getJSONObject(0)
												.get("" + "id_personne_receptrice" + "")
												.toString());

								Log.d("Jim", "Nouveau message : " + " ["
										+ idinter + "] " + " [" + idMessage
										+ "] " + " [" + idEmmeteur + "] "
										+ " [" + envoi.toString() + "] " + " ["
										+ contenu + "] " + " [" + idtype + "] "
										+ " [" + libelletype + "] "
										+ " [" + islu + "] ");
								
								Log.d("Antho", "Nouveau message : " + " ["
										+ idinter + "] " + " [" + idMessage
										+ "] " + " [" + idEmmeteur + "] "
										+ " [" + envoi.toString() + "] " + " ["
										+ contenu + "] " + " [" + idtype + "] "
										+ " [" + libelletype + "] "
										+ " [" + islu + "] ");

								m = new Message(idinter, idMessage, idEmmeteur, idRecepteur,
										 envoi, null, contenu, idtype, libelletype,
										emetteur, (islu == 1));

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

		}

		return m;

	}

	@Override
	public boolean create(Message obj) {

		boolean is_added_correctly = true;

		if (super.update != null) {

			ExecuteRequestSQL executerSQL;

			Map<String, String> params = new HashMap<String, String>();
			params.put("id_intervention", "" + obj.getIdInter());
			params.put("id_message", "" + obj.getIdMessage());
			params.put("id_persemetteur", "" + obj.getIdEmmeteur());
			params.put("dateenvoi", "" + obj.getEnvoi());
			params.put("id_personne_receptrice", "" + obj.getIdRecepteur());
			try {
				params.put("textemessage", ""
						+ new JSONObject("{\"texte\" : \"" + obj.getContenu()
								+ "\"}"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			params.put("id_typemessage", "" + obj.getIdtype());
			params.put("id_personne_receptrice", "" + obj.getIdRecepteur());

			executerSQL = new ExecuteRequestSQL(super.update,
					"INSERT INTO message " + "(" + "id_intervention,"
							+ "id_message," + "id_persemetteur," + "dateenvoi,"
							+ "textemessage," + "id_typemessage"
							+ ") VALUES (?,?,?,?,?,?) ", params, this);
			executerSQL.setServlet("ExecuteInsertMessageQuery?q=");

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
			// d'inserer en base la nouvelle donnée
			while (executerSQL.isAlive()) {
			}
			// On actualise la liste des messages
			// this.findAll(100);
		}
		return is_added_correctly;
	}

	@Override
	public Message update(Message obj) {
		
		if (super.update != null) {
			
			ExecuteRequestSQL executerSQL = null;
			Map<String, String> params = new HashMap<String, String>();
			params.put("id_message", "" + obj.getIdMessage());
			params.put("id_personne_receptrice", "" + obj.getIdRecepteur());
			params.put("id_intervention", "" + obj.getIdInter());
			params.put("is_lu", "" + (obj.isLu() ? 1:0));
			
			executerSQL = new ExecuteRequestSQL(super.update,
					"UPDATE assoc_message_personne SET is_lu = " + (obj.isLu() ? 1 : 0)
					+ ", id_personne_receptrice = " + obj.getIdRecepteur() + 
					" WHERE id_message = " + obj.getIdMessage(), params, this);
			executerSQL.setServlet("ExecuteUpdateMessageLu?q=");
		
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
			// d'inserer en base la nouvelle donnée
			while (executerSQL.isAlive()) {
			}

			// On actualise la liste des messages
			// this.findAll(100);

		}

		return obj;

	}

	@Override
	public void delete(Message obj) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.agetac.dto.dto_config.DTO#find(int, boolean)
	 */
	@Override
	public int find(int i, boolean b) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void update() {}

}
