package com.agetac.controller.editionSoiecSaoiecl;

import com.agetac.activity.VueDefinirSoiecSaoiecl;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * Listener du bouton Editer le texte de la vue DefinirSoiecSaoiecl
 * Il permet de rebasculer sur le textEdit lorsque l'on voit le texte en couleurs
 * 
 *
 */

public class ListenerEditerChamps implements OnClickListener{
	
	private VueDefinirSoiecSaoiecl mvcDefinirSoiecSaoiecl;
	
	public ListenerEditerChamps(VueDefinirSoiecSaoiecl mvcDefinirSoiecSaoiecl){
		this.mvcDefinirSoiecSaoiecl = mvcDefinirSoiecSaoiecl;
	}

	public void onClick(View arg0) {
		//On fait disparaitre la textView ainsi que le bouton d'édition du texte
		mvcDefinirSoiecSaoiecl.getScrollView().setVisibility(View.GONE);
		mvcDefinirSoiecSaoiecl.getTextColor().setVisibility(View.GONE);
		mvcDefinirSoiecSaoiecl.getEditer().setVisibility(View.GONE);
		
		//On affiche l'editText
		mvcDefinirSoiecSaoiecl.getChamps().setVisibility(View.VISIBLE);

		
		
	}

}
