/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;
import android.util.Log;

import com.agetac.activity.handler.Updater;
import com.agetac.observer.Observer;
import com.agetac.observer.Subject;

/**
 * @author Anthony LE MEE
 */
public class ExecuteRequestSQL extends Thread implements Subject {

	private Observer 			observer;	// Instance du DTO qui observe le thread courant de ExecuteRequestSQL
	private String 				urlConnexion = "http://148.60.83.213:8080/AgetacWebServer/";
	private String	 			response;	// Réponse sous forme de chaine de caractère du JSONArray récupéré - Si rien alors "ERROR"
	private String				query;		// Requ�te demandé par le DTO
	private Updater 			update;		// Instance de l'Activity � qui on doit envoyer le LOG d'ex�cution de la requ�te
	private Boolean 			status;		// Boolean qui dit si oui ou non tout c'est bien pass�
	private String 				idAsker; 	// Identifiant du DTO qui demande les donn�es
	private String 				servlet;	// Servlet � utiliser pour ex�cuter la requ�te
	private Map<String, String>	params;		// List des params pour un creat, update
	
	/**
	 * Constructeur
	 * @param update La vue "{@link Updater}" qui demande cette requ�te et que l'on notifiera
	 * @param params 
	 */
	public ExecuteRequestSQL(Updater update, String query, Map<String, String> params, Observer o) {
		this.setStatus(true);
		this.setResponse(new String());
		this.setUpdate(update);
		this.setQuery(query);
		this.attach(o);
		this.setParams(params);
	} // constructeur
	
	/**
	 * M�thode run qui ex�cut� via un start() va s'occuper d'aller chercher des donn�es dans la BDD avec la requ�te qu'on lui � fournis
	 * et ensuite de dispatcher un message � l'UPDATER avant d'aller notifier le DTO (observer) gr�ce � idAsker que l'on � quelque chose
	 * pour lui.
	 * @author Anthony LE MEE
	 */
	public void run() {

		String u;
		String jsonText = new String("");
		JSONArray json;
		Log.d("Antho", this.getIdAsker() + "");
		try {
			
			// R�cup�ration de la requ�te
			String requeteSQL = this.getQuery();
			Log.d("Antho", requeteSQL);
			requeteSQL = URLEncoder.encode(requeteSQL, "ISO-8859-1");
			
			// Cr�ation de l'URL avec en param�tre GET q la requ�te //ExecuteSqlQuery?q=
			if (this.getParams() != null) 
			{
				Set<Map.Entry<String, String>> set = ((Map<String, String>)this.getParams()).entrySet(); 
				Iterator<Map.Entry<String, String>> i = set.iterator(); 
				while(i.hasNext()) 
				{ 
					Map.Entry<String, String> me = (Map.Entry<String, String>)i.next(); 
					requeteSQL = requeteSQL + "&" + me.getKey() + "=" + URLEncoder.encode("" + me.getValue() + "", "ISO-8859-1");
				}
			}
			
			u = new String("" + urlConnexion + servlet + requeteSQL + "");

			json = readJsonFromUrl(u);
			
			if (json != null) 
			{
				jsonText = "Donn�es r�cup�r�es pour : " + URLDecoder.decode(requeteSQL, "ISO-8859-1");
				this.setResponse(json.toString());
				this.setStatus(true);
			}
			else
			{
				jsonText = "Aucunes donn�es r�cup�r�es pour : " + URLDecoder.decode(requeteSQL, "ISO-8859-1");
				this.setResponse("ERROR");
				this.setStatus(true);
			}
			
			
			
		} catch (MalformedURLException mue) {

	        System.err.println("L'url de connexion � la servlet com.agetac.tomcat est mauvaise.");
	        mue.printStackTrace();
	        this.setStatus(false);
	        this.setResponse("ERROR");
	        jsonText = "L'url de connexion de la servlet com.agetac.tomcat est mauvaise.";

		} catch (IOException ioe) {
	
			System.err.println("Impossible d'ouvrir notre Stream sur la connexion courante.");
			ioe.printStackTrace();
			this.setStatus(false);
			this.setResponse("ERROR");
			jsonText = "Impossible d'ouvrir notre Stream sur la connexion courante.";
		
		} catch (JSONException e) {
			
			System.err.println("Impossible de convertir la chaine de caract�re en JSON.");
			e.printStackTrace();
			this.setStatus(false);
			this.setResponse("ERROR");
			jsonText = "Impossible de convertir la chaine de caract�re en JSON.";
			
		} finally {	
		
			///////////////////////////
			this.notifyDTO();
			///////////////////////////
			
			// Diffusion du message pour nos Tests
			if (this.getUpdate() != null) 
			{
				Message m = new Message();
				m.setTarget(this.getUpdate());
				m.obj = jsonText;
				m.sendToTarget();
			}
			
		}
		
	} // m�thode
	
	/**
	 * M�thode qui lit les JSONObject envoyer dans le BufferedReader donn� et retourne un JSONArray.
	 * @param rd
	 * @return JSONArray
	 * @throws IOException
	 * @author Anthony LE MEE
	 */
	private static JSONArray readAllJson (BufferedReader rd) throws IOException {
	    
		String s = new String();
		JSONArray json = new JSONArray();
		
	    while ((s = rd.readLine()) != null) {
	    	
	    	try {
				json.put(new JSONObject(s));
				Log.d("Antho", s + "");
			} catch (JSONException e) {
				System.out.println("Impossible de convertir en JSONObject ceci : " + s);
				e.printStackTrace();
			}
			
	    } // while
	    
	    return json;
	    
	} // m�thode
	
	/**
	 * M�thode qui r�cup�re un JSONArray d'une URL
	 * @param url
	 * @return JSONArray
	 * @throws IOException
	 * @throws JSONException
	 * @author Anthony LE MEE
	 */
	public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
	    
		InputStream is = new URL(url).openStream();
	    try {
	      
	    	BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("ISO-8859-1")));
	    	JSONArray json = readAllJson(rd);
	    	Log.d("Antho", json.toString() + "");
	      
	    	return json;
	      
	    } finally {
	    	
	    	is.close();
	      
	    }
	    
	} // m�thode
	
	/** M�thode attach de l'interface Subject */
	public void attach(Observer o) {
		
		this.setObserver(o);
		
	}//methode attach 

	/** M�thode detach de l'interface Subject */
	public void detach(Observer o) {
		
		if (o != null) {
			o = null;
		}
		
	}//methode detach 
	
	/** M�thode notifyDTO de l'interface Subject */
	public void notifyDTO() {
		
		try {
			this.getObserver().update(this.getIdAsker(), this.getResponse());
		} catch (Exception e) {
			Log.d("Antho", "Impossible de communiquer avec le DTO d'id : "+this.getIdAsker() + "");
			e.printStackTrace();
			System.exit(1);
		}

	}//methode notifyCommand 
	
	public void notifyFragment() {}

	/**********************************************/
	/**********   GETTERS AND SETTERS     *********/
	/**********************************************/
	
	/**
	 * @param urlConnexion the urlConnexion to set
	 */
	public void setUrlConnexion(String urlConnexion) {
		this.urlConnexion = urlConnexion;
	}

	/**
	 * @return the urlConnexion
	 */
	public String getUrlConnexion() {
		return urlConnexion;
	}

	/**
	 * @param string the response to set
	 */
	public void setResponse(String string) {
		this.response = string;
	}

	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * @return the update
	 */
	public Updater getUpdate() {
		return update;
	}

	/**
	 * @param update the update to set
	 */
	public void setUpdate(Updater update) {
		this.update = update;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * @param idAsker the idAsker to set
	 */
	public void setIdAsker(String idAsker) {
		this.idAsker = idAsker;
	}

	/**
	 * @return the idAsker
	 */
	public String getIdAsker() {
		return idAsker;
	}

	/**
	 * @param observer the observer to set
	 */
	public void setObserver(Observer observer) {
		this.observer = observer;
	}

	/**
	 * @return the observer
	 */
	public Observer getObserver() {
		return observer;
	}

	/**
	 * @param servlet the servlet to set
	 */
	public void setServlet(String servlet) {
		this.servlet = servlet;
	}

	/**
	 * @return the servlet
	 */
	public String getServlet() {
		return servlet;
	}

	/**
	 * @param params2 the params to set
	 */
	public void setParams(Map<String, String> params2) {
		this.params = params2;
	}

	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

} // class
