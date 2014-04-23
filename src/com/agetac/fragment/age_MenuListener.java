package com.agetac.fragment;

import com.agetac.R;
import com.agetac.R.id;

import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;



public class age_MenuListener implements View.OnTouchListener { 
	
	private age_Main main_activity;
	
	public age_MenuListener(age_Main a){
		main_activity = a;
	}
	
    public boolean onTouch(View view, MotionEvent motionEvent) {
    	
	    switch(motionEvent.getAction()){  
	    
	            case MotionEvent.ACTION_DOWN:
	            	((TextView)view).setTextColor(0xFF000000); //black
	                break;      
	                
	            case MotionEvent.ACTION_CANCEL:       
	                ((TextView)view).setTextColor(0xFFFFFFFF); //white
	                break;
	                
	            case MotionEvent.ACTION_UP:
	            	((TextView)view).setTextColor(0xFFFFFFFF); //white
	            	
	            	if (((TextView)view).getId() == R.id.sitac)
	            		main_activity.showSitac();
	            	else if (((TextView)view).getId() == R.id.moyens)
	       		 		main_activity.showMoyens();
	            	else if (((TextView)view).getId() == R.id.messages)
	       		 		main_activity.showMessages();
	            	else
	       				main_activity.showAskMoyen();
	       		 	
	                break;
	    } 
	    
        return false;   
        
    } 
    
}