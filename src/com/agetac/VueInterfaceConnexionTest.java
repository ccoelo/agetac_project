package com.agetac;
///*	
// * Projet AGETAC	
// * Anthony LE MEE	
// * Universite de Rennes 1	
// * ISTIC	
// */
//package com.agetac.modeleVueControleur.test;
//
//import junit.framework.Assert;
//
//import com.agetac.R;
//import com.agetac.activity.VueInterfaceConnexion;
//import com.jayway.android.robotium.solo.Solo;
//
//import android.app.Activity;
//import android.app.Instrumentation;
//import android.content.Intent;
//import android.test.ActivityInstrumentationTestCase2;
//import android.test.ActivityUnitTestCase;
//import android.test.InstrumentationTestRunner;
//import android.test.TouchUtils;
//import android.util.Log;
//import android.view.ContextThemeWrapper;
//import android.widget.Button;
//import android.widget.EditText;
//
///**
// * @author jimmy
// * 
// */
//public class VueInterfaceConnexionTest extends
//		ActivityInstrumentationTestCase2<VueInterfaceConnexion> {
//
//	Solo solo;
//	
//	public VueInterfaceConnexionTest() {
//		super(VueInterfaceConnexion.class);
//	}
//
//	public void setUp() throws Exception {
//	    solo = new Solo(getInstrumentation(), getActivity());
//	}
//	
//	public void testConnexion() {
//
//		solo.waitForText("connexion");
//		solo.clearEditText(0);
//		solo.clearEditText(1);
//		solo.enterText(0, "mauvais username");
//		solo.enterText(1, "mauvais passwd");
//		
//		solo.assertCurrentActivity("cool", VueInterfaceConnexion.class);
//
//		solo.goBack();
//		solo.waitForText("connexion");
//
//		solo.clearEditText(0);
//		solo.clearEditText(1);
//		solo.enterText(0, "bon username");
//		solo.enterText(1, "bon passwd");
//		
//		/*Intent intent = new Intent(getInstrumentation().getTargetContext(), VueInterfaceConnexion.class);
//	    setActivityIntent(intent);
//	    Activity a = getActivity();
//		Log.d("OOO", " a" + a);
//		EditText ident = (EditText) a.findViewById(R.id.label_ident);
//		EditText mdp = (EditText) a.findViewById(R.id.label_mdp);
//		ident.setText("mauvais id");
//		mdp.setText("mauvais mdp");
//		Button connexion = (Button) a.findViewById(R.id.btnLogin);
//		assertTrue(ident.getText().toString() != "");
//		assertTrue(mdp.getText().toString() != "");
//		assertTrue(getActivity() == a);
//		assertFalse(getActivity() == a);*/
//	}
//}
