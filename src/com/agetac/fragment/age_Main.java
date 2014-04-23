package com.agetac.fragment;

import com.agetac.R;
import com.agetac.R.drawable;
import com.agetac.R.id;
import com.agetac.R.layout;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.TextView;


public class age_Main extends Activity {


	private Fragment fMenu;
	private Fragment fSitac;
	private Fragment fMoyens;
	private Fragment fMessages;
	private Fragment fAskMoyen;
	
	private int showed = 0;
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("showedlast", showed);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {	 
		
		super.onCreate(savedInstanceState);
		
		//set Fragments layout
		setContentView(R.layout.main);
		
		//keep same Fragment showed up before orientation change
		if (savedInstanceState != null)
		showed = (savedInstanceState.containsKey("showedlast")?savedInstanceState.getInt("showedlast"):0);
		
		
		
		//get Fragments references for show(...) methods
		FragmentManager fm = getFragmentManager();
		
		fMenu = fm.findFragmentById(R.id.frag_menu);
		fSitac = fm.findFragmentById(R.id.frag_sitac);
		fMoyens = fm.findFragmentById(R.id.frag_moyens);
		fMessages = fm.findFragmentById(R.id.frag_messages);
		fAskMoyen = fm.findFragmentById(R.id.frag_ask_moyen);
		
		switch (showed){
		case 1:
			showMoyens();
			break;
		case 2:
			showMessages();
			break;
		case 3:
			showAskMoyen();
			break;
		default:
			showSitac();
			break;
	}
		
	}
	
	/**
	 * show SITAC Fragment view
	 */
	public void showSitac() {
		showed = 0;
		((TextView)findViewById(R.id.sitac)).setBackgroundResource(R.drawable.age_selectedmenu);
		((TextView)findViewById(R.id.moyens)).setBackgroundResource(R.drawable.age_unselectedmenu);
		((TextView)findViewById(R.id.messages)).setBackgroundResource(R.drawable.age_unselectedmenu);
		((TextView)findViewById(R.id.demandeMoyen)).setBackgroundResource(R.drawable.age_unselectedmenu);
        getFragmentManager().beginTransaction()
            .hide(fMessages)
            .hide(fMoyens)
            .show(fSitac)
            .hide(fAskMoyen)
            .commit();
    }
	/**
	 * show Moens Fragment view
	 */
	public void showMoyens() {
		showed = 1;
		((TextView)findViewById(R.id.sitac)).setBackgroundResource(R.drawable.age_unselectedmenu);
		((TextView)findViewById(R.id.moyens)).setBackgroundResource(R.drawable.age_selectedmenu);
		((TextView)findViewById(R.id.messages)).setBackgroundResource(R.drawable.age_unselectedmenu);
		((TextView)findViewById(R.id.demandeMoyen)).setBackgroundResource(R.drawable.age_unselectedmenu);
        getFragmentManager().beginTransaction()
            .hide(fMessages)
            .hide(fSitac)
            .show(fMoyens)
            .hide(fAskMoyen)
            .commit();
    }
	/**
	 * show Messages Fragment view
	 */
	public void showMessages() {
		showed = 2;
		((TextView)findViewById(R.id.sitac)).setBackgroundResource(R.drawable.age_unselectedmenu);
		((TextView)findViewById(R.id.moyens)).setBackgroundResource(R.drawable.age_unselectedmenu);
		((TextView)findViewById(R.id.messages)).setBackgroundResource(R.drawable.age_selectedmenu);
		((TextView)findViewById(R.id.demandeMoyen)).setBackgroundResource(R.drawable.age_unselectedmenu);
        getFragmentManager().beginTransaction()
            .hide(fSitac)
            .hide(fMoyens)
            .show(fMessages)
            .hide(fAskMoyen)
            .commit();
    }
	/**
	 * show AskMoyen Fragment view
	 */
	public void showAskMoyen() {
		showed = 3;
		((TextView)findViewById(R.id.sitac)).setBackgroundResource(R.drawable.age_unselectedmenu);
		((TextView)findViewById(R.id.moyens)).setBackgroundResource(R.drawable.age_unselectedmenu);
		((TextView)findViewById(R.id.messages)).setBackgroundResource(R.drawable.age_unselectedmenu);
		((TextView)findViewById(R.id.demandeMoyen)).setBackgroundResource(R.drawable.age_selectedmenu);
        getFragmentManager().beginTransaction()
            .hide(fSitac)
            .hide(fMoyens)
            .hide(fMessages)
            .show(fAskMoyen)
            .commit();
    }
	
}