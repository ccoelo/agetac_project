package com.agetac.controller;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.agetac.activity.VueGroupeIntervention;
import com.agetac.dto.GroupeInterventionDTO;
import com.agetac.dto.SecteurInterDTO;
import com.agetac.dto.dto_config.DTO;
import com.agetac.dto.dto_config.DTOFactory;
import com.agetac.model.GroupeIntervention;
import com.agetac.model.Intervention;


public class ListenerListeMoyens extends DialogFragment implements OnClickListener {

	TextView title;
	Builder adb;
	String[] map;
	Fragment a;
	
	public ListenerListeMoyens(TextView titre, Fragment mvcVisualisationMoyens){
		title = titre;
		a = mvcVisualisationMoyens;		
	}
	
	public void onClick(View v) {
		
		GroupeInterventionDTO gi = new GroupeInterventionDTO();
		//DTO<Intervention> inter = DTOFactory.getIntervention();
		//inter.findById(0);
		
		GroupeIntervention[] groupes = gi.getGroupe(1);
		if(title.getText().toString().contains("INC")){
			groupes = gi.getGroupe(1);
		}
		map = new String[groupes.length];
		for (int j = 0; j < groupes.length ; j++){
			map[j] = ((String) title.getText()).replace("Secteur", "Groupe") + gi.getGroupe(1)[j]; 
		}
		
		adb = new AlertDialog.Builder(a.getActivity());
		//on attribut un titre à notre boite de dialogue
		adb.setTitle("Choisissez le groupe d'intervention");
		//on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
        adb.setItems(map, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	// argument qui contient la position de l'item
            	Intent intent = new Intent(a.getActivity(), VueGroupeIntervention.class);
            	intent.putExtra("GROUPE", map[which]);
            	intent.putExtra("SECTEUR", title.getText());
            	a.startActivity(intent);
            }
        });
		adb.show();
	}
}
