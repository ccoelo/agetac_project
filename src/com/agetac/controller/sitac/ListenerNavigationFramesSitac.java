/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.agetac.controller.sitac;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

import com.agetac.activity.VueSitac;

/**
 * Classe ListenerNavigationFramesSitac : qui sert à switcher 
 * entre la barre d'outils et la mapView sur la fenêtre de la SITAC
 *
 * @version 1.0 26/01/2013
 * @author Anthony LE MEE - 10003134
 */
public class ListenerNavigationFramesSitac implements OnClickListener, AnimationListener {

	/** Instance de la classe VueSitac */
	private VueSitac sitacContext;
	
	/** Boolean menu ouvert ; fermé par défaut */
    private boolean menuOut = false;
    
    /** Paramètre de l'animation sous forme de classe AnimParams */
    private AnimParams animParams = new AnimParams();
	
	/**
	 * Constructeur
	 * @param m
	 */
	public ListenerNavigationFramesSitac (VueSitac m) {
		this.sitacContext = m;
	}
	
	/**
	 * OnClick
	 * @param v View
	 */
	public void onClick(View v) {

        ListenerNavigationFramesSitac context = ListenerNavigationFramesSitac.this;
        Animation anim;

        // Calculs des params de l'animation
        int w = this.sitacContext.getApp().getMeasuredWidth();
        int h = this.sitacContext.getApp().getMeasuredHeight();
        int left = (int) (this.sitacContext.getApp().getMeasuredWidth() * 0.75); // 75% de l'écran

        if (!menuOut) {
        	
        	// Création de l'animation
            anim = new TranslateAnimation(0, left, 0, 0);
            
            // On rend visible notre barre d'outils
            this.sitacContext.getMenu().setVisibility(View.VISIBLE);
            
            // On initialise les paramètres de l'animation avec ceux calculés plus haut
            animParams.init(left, 0, left + w, h);
            
            // On change l'état du boutton
            /*((Button)this.sitacContext.getApp().findViewById(R.id.btn_barre_outils_sitac))
            	.setText(R.string.btn_carte_sitac);*/
            
        } else {
        	
        	// On créer notre animation. 
            anim = new TranslateAnimation(0, -left, 0, 0);
            
         	// On initialise les paramètres de l'animation 
            animParams.init(0, 0, w, h);
            
            // On change l'état du boutton
            /*((Button)this.sitacContext.getApp().findViewById(R.id.btn_barre_outils_sitac))
            	.setText(R.string.btn_barre_outils_sitac);*/
            
        }

        anim.setDuration(500);
        anim.setAnimationListener(context);
        anim.setFillAfter(true);

        // Utiliser seulement fillEnabled and fillAfter si on n'appelle pas nous même les layouts
        // anim.setFillEnabled(true);
        // anim.setFillAfter(true);

        this.sitacContext.getApp().startAnimation(anim);
		
	}
	
	void layoutApp(boolean menuOut) {
		
        this.sitacContext.getApp().layout(animParams.left, animParams.top, animParams.right, animParams.bottom);
        this.sitacContext.getApp().clearAnimation();

    }

    
    public void onAnimationEnd(Animation animation) {

        menuOut = !menuOut;
        if (!menuOut) {
            this.sitacContext.getMenu().setVisibility(View.INVISIBLE);
        }
        layoutApp(menuOut);
        
    }

    public void onAnimationRepeat(Animation animation) {
        //System.out.println("onAnimationRepeat");
    }

    
    public void onAnimationStart(Animation animation) {
        //System.out.println("onAnimationStart");
    }

    static class AnimParams {
        
    	int left, right, top, bottom;

        void init(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }
        
    }
    
} // class ListenerNavigationFramesSitac
