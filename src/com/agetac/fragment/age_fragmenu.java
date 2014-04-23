package com.agetac.fragment;

import com.agetac.R;
import com.agetac.R.id;
import com.agetac.R.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class age_fragmenu extends Fragment{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		TextView sitac = (TextView) getActivity().findViewById(R.id.sitac);
		TextView moyens = (TextView) getActivity().findViewById(R.id.moyens);
		TextView messages = (TextView) getActivity().findViewById(R.id.messages);
		TextView askmoyens = (TextView) getActivity().findViewById(R.id.demandeMoyen);

		//set listeners on menu textViews
		sitac.setOnTouchListener(new age_MenuListener((age_Main)getActivity()));
		moyens.setOnTouchListener(new age_MenuListener((age_Main)getActivity()));
		messages.setOnTouchListener(new age_MenuListener((age_Main)getActivity()));
		askmoyens.setOnTouchListener(new age_MenuListener((age_Main)getActivity()));
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.age_fragmenu, container, false);
		return view;
	}
	
}