package com.agetac.controller.editionSoiecSaoiecl;

import org.json.JSONObject;

import android.view.View;
import android.view.View.OnClickListener;

import com.agetac.activity.VueDefinirSoiecSaoiecl;

public class ListenerEnvoyerSoiecSaoiecl implements OnClickListener {

	/** Instance de l'activity principale */
	private VueDefinirSoiecSaoiecl definirSoiecSaiecl;

	public ListenerEnvoyerSoiecSaoiecl(VueDefinirSoiecSaoiecl definirSoiecSaiecl) {
		this.definirSoiecSaiecl = definirSoiecSaiecl;
	}

	public void onClick(View v) {

		//On remet le texte tapé précédemment
		this.definirSoiecSaiecl.getInfosSoiecSaoiecl()[this.definirSoiecSaiecl.getCurrentItemSelected()] = ""+ this.definirSoiecSaiecl.getChamps().getText();
	}

	private JSONObject envoyer(String[] soiecSaoiecl) {
		return new JSONObject();
	}

}
