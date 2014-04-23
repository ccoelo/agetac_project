package com.agetac.activity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.agetac.R;
import com.agetac.activity.adapter.AdapterListDessins;
import com.agetac.activity.adapter.AdapterListVehiculeToAddOnMap;
import com.agetac.activity.handler.UpdateView;
import com.agetac.activity.handler.Updater;
import com.agetac.activity.items.itemListDessins;
import com.agetac.activity.utils.UtilsSecteurs;
import com.agetac.activity.utils.UtilsVehicules;
import com.agetac.activity.yuku.ambilwarna.AmbilWarnaDialog;
import com.agetac.activity.yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import com.agetac.controller.ListenerSwitchToActivity;
import com.agetac.dto.SecteurInterDTO;
import com.agetac.dto.VehiculeDTO;
import com.agetac.dto.dto_config.DTOFactory;
import com.agetac.model.SecteurIntervention;
import com.agetac.model.Vehicule;
import com.agetac.view.BubbleDragItemOverlay;
import com.agetac.view.DragItemOverlay;
import com.agetac.view.PathOverlay;


/**
 * Classe VueSitac : visualisation et édition de la situation tactique
 * 
 * @version 1.0 24/01/2013
 * @author Anthony LE MEE - 10003134
 * @author Christophe Coelo - 29001702
 */
public class VueSitac extends Fragment implements UpdateView {
	
	/** Instance des Handler */
	Updater handler;
	
	/** Instances des modèles à utiliser */
	private VehiculeDTO mVehicule;
	private SecteurInterDTO mSecteur;
	
	/** Attributs en rapport avec la osmDroid */
	private MapController mapController; // Controler de la map
	private MapView mapView; // Vue de la map
	private ResourceProxy mResourceProxy; // Ressource Map

	/** Attributs de la SITAC view */
	private View menu; // Frame de la barre de menu
	private View app; // Frame de l'affichage principale de la sitac (mapView)

	/** Attributs en relations avec la sélection de couleur */
	private int currentColor = Color.BLACK; // Couleur actuelle des futurs
											// dessins
	private Button tv; // Vue indicatrice de la couleur actuelle
	private AmbilWarnaDialog dialog; // Dialog de choix de couleur

	/** Bouton menu */
	private Button openCloseToolBar; // Boutton d'ouverture du menu de dessin -
										// sert aussi de prévualisation de
										// l'élément courant.
	private Button openCloseListLayers; // Boutton d'ouverture de la liste des
										// éléments dessinés
										// sur la map

	/** Boutons de la barre d'outils */
	private Button BtnVehicule;
	private Button BtnTrait;
	private Button BtnPoint;
	private Button BtnSensible;
	private Button BtnDanger;
	private Button BtnMove;
	private Button BtnHand;
	
	private boolean btnVehiculeActivate = false;// Tag qui dit si oui ou non le
												// boutton est activé
	private boolean btnTraitActivate = false;// Tag qui dit si oui ou non le
												// boutton est activé
	private boolean btnPointActivate = false;// Tag qui dit si oui ou non le
												// boutton est activé
	private boolean btnSensibleActivate = false;// Tag qui dit si oui ou non le
												// boutton est activé
	private boolean btnDangerActivate = false;// Tag qui dit si oui ou non le
												// boutton est activé
	private boolean btnHandActivate = true; // Tag qui dit si oui ou non le
											// boutton est activé
	private boolean btnMoveActivate = false;// Tag qui dit si oui ou non le
											// boutton est activé

	/** La barre d'outils */
	private View popupMenu;
	private PopupWindow popwinMenu;
	private boolean click = true;

	/** La barre des Layers */
	private View popupLayers;
	private PopupWindow popwinLayers;
	private boolean click2 = true;

	/** */
	private PathOverlay lastPathOverlay = null;

	private DragItemOverlay lastDragItem = null;
	
	private int nbPath=0;

	/** Données à afficher dans la vue */
	private ArrayList<itemListDessins> donneesDessins; // Liste des dessins ajoutés
	private AdapterListDessins adapterListDessin;// Adapter de la liste des dessins ajoutés
	private AdapterListVehiculeToAddOnMap adapterListMoyen;		// Adapter de la liste des véhicules à placer
	private ArrayList<Vehicule> arrayListMoyen;// Array list des items de véhicules 
	
	private Map<Integer, Integer> mapVehicule;

	/**
	 * Méthode qui affiche un toast suite à la réception d'un message
	 * @param message
	 */
	public void onMessageReveive(String message) {
		Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	/*********************************************************************************************
	 * Méthode onCreate
	 * 
	 * @param savedInstanceState
	 *            Bundle
	 *********************************************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);
		super.onCreate(savedInstanceState);
	}

	/*********************************************************************************************
	 * Méthode onActivityCreated
	 * 
	 * @param savedInstanceState
	 *            Bundle
	 *********************************************************************************************/
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		/** Chargements des données dans les attributs correspondants */
		this.donneesDessins = new ArrayList<itemListDessins>();
		this.adapterListDessin = new AdapterListDessins(this,
				android.R.layout.simple_list_item_1, this.donneesDessins);

		// Création mapvehicule
		mapVehicule = new Hashtable<Integer, Integer>();
		mapVehicule.put(0, R.drawable.fpt);
		mapVehicule.put(1, R.drawable.fpt);
		mapVehicule.put(2, R.drawable.vsav);
		mapVehicule.put(3, R.drawable.vsav);
		mapVehicule.put(4, R.drawable.epa);

		// Chargement de la map
		mapView = (MapView) getActivity().findViewById(R.id.mapview);
		mapView.setTileSource(TileSourceFactory.MAPNIK);
		mapView.setBuiltInZoomControls(true);
		mapController = mapView.getController();
		mapController.setZoom(20);
		GeoPoint point = new GeoPoint(48.0694900, -1.6665840); // Beaulieu -
																// Rennes
		mapController.setCenter(point);
		mResourceProxy = new DefaultResourceProxyImpl(getActivity()
				.getApplicationContext());

		// Creation la navigation entre les deux fenêtres 'map' et 'barre
		// d'outils'
		setMenu(getActivity().findViewById(R.id.menu));
		setApp(getActivity().findViewById(R.id.app));

		// Listener de switch activity pour le boutton d'accès à l'édition du
		// SOIEC/SAOIECL
		getActivity().findViewById(R.id.gotoSoiec).setOnClickListener(
				new ListenerSwitchToActivity(this.getActivity(),
						VueDefinirSoiecSaoiecl.class));

		// Création du menu de dessin sous forme de popup
		this.popupMenu = getActivity().getLayoutInflater().inflate(
				R.layout.maquette_sitac_menu, null);
		this.popwinMenu = new PopupWindow(this.popupMenu,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		this.popwinMenu.setAnimationStyle(android.R.style.Animation_Dialog);
		this.popwinMenu.setOutsideTouchable(true);

		// Listener sur le boutton d'ouverture de la barre de dessin
		final View b = getActivity().findViewById(R.id.outilsCourant);
		this.openCloseToolBar = ((Button) b);
		this.openCloseToolBar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (click) {
					if (getLastPathOverlay() != null) {
						
						addDessinsToList(new itemListDessins("Tracé",
								((Object) getLastPathOverlay()), 0,
								getCurrentColor(), 0), true);
						resetLastPath();
						
					}
					showMenu();
				}
			}
		});

		// Création du menu de la liste des dessins dessinés sur la map
		this.popupLayers = getActivity().getLayoutInflater().inflate(
				R.layout.maquette_sitac_contentlayers, null);
		this.popwinLayers = new PopupWindow(this.popupLayers,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		this.popwinLayers.setAnimationStyle(android.R.style.Animation_Dialog);
		this.popwinLayers.setOutsideTouchable(true);

		ListView list = (ListView) this.popupLayers
				.findViewById(R.id.listDessins);
		list.setAdapter(this.adapterListDessin);

		// Listener sur le boutton d'ouverture de la barre de layers
		final View b2 = getActivity().findViewById(R.id.listingCalque);
		this.openCloseListLayers = ((Button) b2);
		this.openCloseListLayers.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (click2) {
					showContentLayers();
				}
			}
		});

	}

	/*********************************************************************************************
	 * Méthode qui ajoute un dessin à la liste de dessins
	 *********************************************************************************************/
	public void addDessinsToList(itemListDessins dessin, boolean path) {

		if (path) {
			this.donneesDessins.add(donneesDessins.size()-(donneesDessins.size()-nbPath), dessin);
			nbPath++;
		} else {
			this.donneesDessins.add(dessin);
		}
		// Mise à jour de la list afin qu'elle prenne en compte le nouvel
		// élément
		this.adapterListDessin.notifyDataSetChanged();
	}

	/*********************************************************************************************
	 * Méthode resetLastPath remet à null le dernier path overlay car on l'a
	 * enregistré
	 *********************************************************************************************/
	public void resetLastPath() {
		this.lastPathOverlay = null;
	}

	/*********************************************************************************************
	 * Méthode onActivityCreated
	 * 
	 * @param savedInstanceState
	 *            Bundle
	 *********************************************************************************************/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		/** Chargement du layout */
		View view = inflater.inflate(R.layout.maquette_sitac, container, false);
		return view;

	}

	/*********************************************************************************************
	 * Méthode desactivateAllBouttonOfMenuSitac
	 *********************************************************************************************/
	public void desactivateAllBouttonOfMenuSitac() {
		this.BtnVehicule.setBackgroundResource(R.drawable.vehicule);
		this.BtnTrait.setBackgroundResource(R.drawable.trace);
		this.BtnPoint.setBackgroundResource(R.drawable.point);
		this.BtnSensible.setBackgroundResource(R.drawable.incendieinverse);
		this.BtnDanger.setBackgroundResource(R.drawable.incendie);
		this.BtnMove.setBackgroundResource(R.drawable.move);
		this.BtnHand.setBackgroundResource(R.drawable.hand);
		btnVehiculeActivate = false;
		btnTraitActivate = false;
		btnPointActivate = false;
		btnSensibleActivate = false;
		btnDangerActivate = false;
		btnMoveActivate = false;
		btnHandActivate = false;
	}

	/*********************************************************************************************
	 * Méthode desactivateAllBouttonOfMenuSitac
	 *********************************************************************************************/
	public void closeMenu() {

		PopupWindow popwinMenu = this.popwinMenu;
		if (popwinMenu != null) {
			popwinMenu.dismiss();
			this.click = true;
		}

	}

	/*********************************************************************************************
	 * Méthode desactivateAllBouttonOfMenuSitac
	 *********************************************************************************************/
	public void closeContentLayers() {

		PopupWindow popwinLayers = this.popwinLayers;
		if (popwinLayers != null) {
			popwinLayers.dismiss();
			this.click2 = true;
		}

	}

	/*********************************************************************************************
	 * Méthode desactivateAllBouttonOfMenuSitac
	 *********************************************************************************************/
	public void showContentLayers() {

		if (!this.click)
			closeMenu();

		this.popupLayers = getActivity().getLayoutInflater().inflate(
				R.layout.maquette_sitac_contentlayers, null); // inflating popup
																// layout
		this.popwinLayers = new PopupWindow(this.popupLayers,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

		this.popwinLayers.setAnimationStyle(android.R.style.Animation_Dialog);
		this.popwinLayers.setOutsideTouchable(true);

		// Affichage du menu
		popwinLayers.showAtLocation(popupLayers,
				Gravity.BOTTOM | Gravity.RIGHT, 0, 0);

		// Ajout du listener sur le boutton fermer
		View v = this.popupLayers.findViewById(R.id.fermer);
		v.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				closeContentLayers();

			}
		});

		this.adapterListDessin = new AdapterListDessins(this,
				android.R.layout.simple_list_item_1, this.donneesDessins);
		Log.d("Antho", this.donneesDessins.size() + "");
		ListView list = (ListView) this.popupLayers
				.findViewById(R.id.listDessins);
		list.setAdapter(this.adapterListDessin);

		this.click2 = false;

	}

	/*********************************************************************************************
	 * Méthode desactivateAllBouttonOfMenuSitac lancée pour afficher le menu,
	 * initialiser les vues et les listeners.
	 *********************************************************************************************/
	public void showMenu() {

		if (!this.click2)
			closeContentLayers();

		// Création de la vue menu
		this.popupMenu = getActivity().getLayoutInflater().inflate(
				R.layout.maquette_sitac_menu, null); // inflating popup layout
		this.popwinMenu = new PopupWindow(this.popupMenu,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

		this.popwinMenu.setAnimationStyle(android.R.style.Animation_Dialog);
		this.popwinMenu.setOutsideTouchable(true);

		// Affichage du menu
		popwinMenu.showAtLocation(popupMenu, Gravity.BOTTOM | Gravity.LEFT, 0,
				0); // Displaying popup

		// Mise a jour de l'indicateur de couleur actuelle
		tv = (Button) popupMenu.findViewById(R.id.curcol);
		tv.setBackgroundColor(currentColor);

		final Button BtnCouleurCurrent = (Button) app
				.findViewById(R.id.couleurCourante);

		// initialisation du dialog choix de couleur
		dialog = new AmbilWarnaDialog(getActivity(), currentColor, new OnCC() {
			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				currentColor = color;
				tv.setBackgroundColor(currentColor);
				BtnCouleurCurrent.setBackgroundColor(currentColor);
			}

			@Override
			public void onCancel(AmbilWarnaDialog dialog) {

			}
		});

		// association des listeners aux vues a afficher
		View v = popupMenu.findViewById(R.id.curcol);
		v.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				dialog.show();
				return true;
			}
		});

		this.BtnVehicule = (Button) popupMenu.findViewById(R.id.vehicule);
		this.BtnTrait = (Button) popupMenu.findViewById(R.id.trait);
		this.BtnPoint = (Button) popupMenu.findViewById(R.id.point);
		this.BtnSensible = (Button) popupMenu.findViewById(R.id.sensible);
		this.BtnDanger = (Button) popupMenu.findViewById(R.id.danger);
		this.BtnMove = (Button) popupMenu.findViewById(R.id.deplacement);
		this.BtnHand = (Button) popupMenu.findViewById(R.id.hand);

		final Button BtnVehicule = this.BtnVehicule;
		final Button BtnPoint = this.BtnPoint;
		final Button BtnTrace = this.BtnTrait;
		final Button BtnSensible = this.BtnSensible;
		final Button BtnDanger = this.BtnDanger;
		final Button BtnMove = this.BtnMove;
		final Button BtnHand = this.BtnHand;
		final Button BtnOutilsCurrent = (Button) app
				.findViewById(R.id.outilsCourant);

		if (this.btnSensibleActivate) {
			desactivateAllBouttonOfMenuSitac();
			BtnSensible.setBackgroundResource(R.drawable.incendieinverse_hover);
		} else if (this.btnHandActivate) {
			desactivateAllBouttonOfMenuSitac();
			BtnHand.setBackgroundResource(R.drawable.hand_hover);
		} else if (this.btnDangerActivate) {
			desactivateAllBouttonOfMenuSitac();
			BtnDanger.setBackgroundResource(R.drawable.incendie_hover);
		} else if (this.btnMoveActivate) {
			desactivateAllBouttonOfMenuSitac();
			BtnMove.setBackgroundResource(R.drawable.move_hover);
		} else if (this.btnPointActivate) {
			desactivateAllBouttonOfMenuSitac();
			BtnPoint.setBackgroundResource(R.drawable.point_hover);
		} else if (this.btnTraitActivate) {
			desactivateAllBouttonOfMenuSitac();
			BtnTrace.setBackgroundResource(R.drawable.trace_hover);
		} else if (this.btnVehiculeActivate) {
			desactivateAllBouttonOfMenuSitac();
			BtnVehicule.setBackgroundResource(R.drawable.vehicule_hover);
		} else {

		}
		
		/** Instanciation des handler */
		this.handler = new Updater(this);
		
		/** Instanciations des modèles */
		this.mVehicule 			= (VehiculeDTO) DTOFactory.getVehicule();
		this.mSecteur			= (SecteurInterDTO) DTOFactory.getSpinnerChoixSecteur();
		this.mVehicule.update 	= this.handler;
		this.mSecteur.update 	= this.handler;
		
		this.arrayListMoyen = new ArrayList<Vehicule>();

		/**
		 * Gestion du bouton véhicule
		 */
		final Activity conteneur = this.getActivity();
		BtnVehicule.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				// On active le bouton
				desactivateAllBouttonOfMenuSitac();
				BtnVehicule.setBackgroundResource(R.drawable.vehicule_hover);
				BtnOutilsCurrent.setBackgroundResource(R.drawable.vehicule_hover);
				btnVehiculeActivate = true;

				/** Récupérations des données via les modèles */
				// On récupère la liste des secteurs de notre intervention
				SecteurIntervention[] donneesSecteur 	= mSecteur.findAll(-1); 
				// On converti les données en tableau d'ids de secteur
				Integer[] arrayIdsListSecteurs 			= UtilsSecteurs.getIdOfSecteurs(donneesSecteur);
				
				try {
				
					// On récupère les véhicules arrivée et disponibles 
					for (int i = 0; i < arrayIdsListSecteurs.length; ++i) {
						Log.d("VEHICULE", "Secteur : " + arrayIdsListSecteurs[i]);
						Vehicule[] vTab = ((Vehicule[])mVehicule.findByIdSecteur(arrayIdsListSecteurs[i]));
						ArrayList<Vehicule> vAlist = null;
						Log.d("VEHICULE", "vTab : " + vTab);
						if (vTab != null) {
							vAlist = ((ArrayList<Vehicule>)UtilsVehicules.convertINTOarrayListVehicule(vTab));
						}
						Log.d("VEHICULE", "vAlist : " + vAlist);
						if (vAlist != null) {
							arrayListMoyen.addAll(vAlist);
						}
						
					}
					
					// On va chercher la liste des véhicules que l'on peux ajouter
					final String[] vehicules = UtilsVehicules.convertINTOarrayNameVehicule(arrayListMoyen);
	
					// On créer la liste déroulante des véhicules que l'on peux
					// ajouter
					Builder adb = new AlertDialog.Builder(conteneur);
					// On lui attribut un titre
					adb.setTitle("Choisissez le véhicule à ajouter");
					// On insère la liste des véhicules disponibles
					adb.setItems(vehicules, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
							// Génération du nom de l'image en fonction du véhicule choisi
							String nameSecteur = arrayListMoyen.get(which).getNameSecteur().replaceAll(" ", "").toLowerCase();
							String nameVehicule = vehicules[which].replaceAll(" ", "_").toLowerCase();
							String nameImageVehicule = nameVehicule+""+nameSecteur;
							Log.d("VEHICULE", "nameImageVehicule : " + nameImageVehicule);
							
							try {
								addDragItemOverlay(mapView.getMapCenter()
										.getLatitudeE6(), mapView.getMapCenter()
										.getLongitudeE6(), R.drawable.class.getField(nameImageVehicule).getInt(null));
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchFieldException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
	
					adb.show();
					
				}catch (Exception e) {
					e.printStackTrace();
				}

				closeMenu();

			}
		});

		BtnDanger.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				desactivateAllBouttonOfMenuSitac();
				BtnDanger.setBackgroundResource(R.drawable.incendie_hover);
				BtnOutilsCurrent.setBackgroundResource(R.drawable.incendie);
				btnDangerActivate = true;

				addTriangle(mapView.getMapCenter().getLatitudeE6(), mapView
						.getMapCenter().getLongitudeE6());

				closeMenu();
			}

		});

		BtnPoint.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				desactivateAllBouttonOfMenuSitac();
				BtnPoint.setBackgroundResource(R.drawable.point_hover);
				BtnOutilsCurrent.setBackgroundResource(R.drawable.point);
				btnPointActivate = true;

				addBubbleItemOverlay(mapView.getMapCenter().getLatitudeE6(),
						mapView.getMapCenter().getLongitudeE6());

				closeMenu();
			}
		});

		BtnTrace.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				desactivateAllBouttonOfMenuSitac();
				BtnTrace.setBackgroundResource(R.drawable.trace_hover);
				BtnOutilsCurrent.setBackgroundResource(R.drawable.trace);
				btnTraitActivate = true;
				addPathOverlay();

				closeMenu();
			}
		});

		BtnSensible.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				desactivateAllBouttonOfMenuSitac();
				BtnSensible
						.setBackgroundResource(R.drawable.incendieinverse_hover);
				BtnOutilsCurrent
						.setBackgroundResource(R.drawable.incendieinverse);
				btnSensibleActivate = true;

				addTriangleInverse(mapView.getMapCenter().getLatitudeE6(),
						mapView.getMapCenter().getLongitudeE6());
				closeMenu();
			}
		});

		BtnMove.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				desactivateAllBouttonOfMenuSitac();
				BtnMove.setBackgroundResource(R.drawable.move_hover);
				BtnOutilsCurrent.setBackgroundResource(R.drawable.move);
				btnMoveActivate = true;
				closeMenu();
			}
		});

		BtnHand.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				desactivateAllBouttonOfMenuSitac();
				BtnHand.setBackgroundResource(R.drawable.hand_hover);
				BtnOutilsCurrent.setBackgroundResource(R.drawable.hand);
				btnHandActivate = true;
				closeMenu();
				return false;
			}
		});

		this.click = false;

	}

	/*********************************************************************************************
	 * Class interne OnCC qui implemente l'interface ambilWarna (choix de
	 * couleur)
	 *********************************************************************************************/
	private class OnCC implements OnAmbilWarnaListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.agetac.activity.yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener#onCancel(yuku
		 * .ambilwarna.AmbilWarnaDialog)
		 */
		public void onCancel(AmbilWarnaDialog dialog) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.agetac.activity.yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener#onOk(yuku.
		 * ambilwarna.AmbilWarnaDialog, int)
		 */
		public void onOk(AmbilWarnaDialog dialog, int color) {

		}
	}

	/*********************************************************************************************
	 * Ajout d'un Item Draggable à la map
	 * 
	 * @param lat
	 *            : latitude
	 * @param lon
	 *            : longitude
	 *********************************************************************************************/
	private void addDragItemOverlay(int lat, int lon, int idmarker) {

		// récupération de la ressource du marker
		Drawable marker = getResources().getDrawable(idmarker);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());

		// création d'un Layout imageview pour y mettre l'item.
		ImageView dragImage = new ImageView(getActivity());
		dragImage.setImageResource(idmarker);
		RelativeLayout rl = (RelativeLayout) getActivity().findViewById(
				R.id.app);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dragImage.setLayoutParams(lp);
		dragImage.setVisibility(View.GONE);
		rl.addView(dragImage);

		// créaton de l'item
		DragItemOverlay ditem = new DragItemOverlay(marker, mResourceProxy,
				lat, lon, dragImage);
		mapView.getOverlays().add(ditem); // ajout de l'overlay
		System.out.println("size :" + mapView.getOverlays().size());
		mapView.invalidate(); // rafraichisement de la map

		// Ajout à la liste des éléments crée
		addDessinsToList(new itemListDessins("Vehicule", ((Object) ditem), 1,
				getCurrentColor(), idmarker), false);

	}

	/*********************************************************************************************
	 * Ajout d'un BuubleItemOverlay à la map
	 * 
	 * @param lat
	 *            : latitude
	 * @param lon
	 *            : longitude
	 *********************************************************************************************/
	/*
	 * private void addBubbleItemOverlay(int lat, int lon) {
	 * 
	 * final ArrayList<ExtendedOverlayItem> roadItems = new
	 * ArrayList<ExtendedOverlayItem>(); BubbleItemOverlay<ExtendedOverlayItem>
	 * roadNodes = new BubbleItemOverlay<ExtendedOverlayItem>( getActivity(),
	 * roadItems, mapView); mapView.getOverlays().add(roadNodes);
	 * 
	 * // marker de l'Item Drawable marker =
	 * getResources().getDrawable(R.drawable.logoagetac72); GeoPoint point = new
	 * GeoPoint(lat, lon);
	 * 
	 * ExtendedOverlayItem nodeMarker = new ExtendedOverlayItem("titre",
	 * "point", point, getActivity());
	 * nodeMarker.setMarkerHotspot(OverlayItem.HotspotPlace.CENTER);
	 * nodeMarker.setMarker(marker); roadNodes.addItem(nodeMarker);
	 * 
	 * nodeMarker.setDescription("coucou");// Texte de la bubble Drawable icon =
	 * getResources().getDrawable(R.drawable.camion); nodeMarker.setImage(icon);
	 * // Ajout à la liste des éléments crée addDessinsToList(new
	 * itemListDessins("Bubble", ((Object) roadNodes), 1, getCurrentColor(),
	 * R.drawable.logoagetac72),false);
	 * 
	 * mapView.invalidate(); // rafraichisement de la map }
	 */
	private void addBubbleItemOverlay(int lat, int lon) {
		// récupération de la ressource du marker
		int idmarker = R.drawable.logoagetac72;
		Drawable marker = getResources().getDrawable(idmarker);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());

		// création d'un Layout imageview pour y mettre l'item.
		ImageView dragImage = new ImageView(getActivity());
		dragImage.setImageResource(idmarker);
		RelativeLayout rl = (RelativeLayout) getActivity().findViewById(
				R.id.app);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dragImage.setLayoutParams(lp);
		dragImage.setVisibility(View.GONE);
		rl.addView(dragImage);

		// créaton de l'item
		BubbleDragItemOverlay ditem = new BubbleDragItemOverlay(marker, mResourceProxy,
				lat, lon, dragImage);
		mapView.getOverlays().add(ditem); // ajout de l'overlay
		System.out.println("size :" + mapView.getOverlays().size());
		mapView.invalidate(); // rafraichisement de la map

		// Ajout à la liste des éléments crée
		addDessinsToList(new itemListDessins("bubble", ((Object) ditem), 1,
				getCurrentColor(), idmarker), false);

	}

	/*********************************************************************************************
	 * Ajout d'un PathOverlay à la map Il suffit alors de clicker sur la map
	 * pour ajouter des points
	 *********************************************************************************************/
	private void addPathOverlay() {

		// Ajout de l'overlay au mapview
		this.lastPathOverlay = new PathOverlay(currentColor, mResourceProxy,
				this);
		mapView.getOverlays().add(mapView.getOverlays().size()-(mapView.getOverlays().size()-nbPath), lastPathOverlay);

	}

	/**
	 * Ajout d'un Triangle de signalisation sur la carte, celui ci sera de la
	 * couleur courante.
	 * 
	 * @param lat
	 * @param lon
	 */
	private void addTriangle(int lat, int lon) {
		// récupération de la ressource du marker
		int idmarker = R.drawable.triangle;
		Drawable marker = getResources().getDrawable(idmarker);

		marker.mutate().setColorFilter(
				new PorterDuffColorFilter(getCurrentColor(), Mode.MULTIPLY));

		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());

		// création d'un Layout imageview pour y mettre l'item.
		ImageView dragImage = new ImageView(getActivity());
		dragImage.setImageResource(idmarker);
		RelativeLayout rl = (RelativeLayout) getActivity().findViewById(
				R.id.app);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dragImage.setLayoutParams(lp);
		dragImage.setVisibility(View.GONE);
		rl.addView(dragImage);

		// créaton de l'item
		DragItemOverlay ditem = new DragItemOverlay(marker, mResourceProxy,
				lat, lon, dragImage);
		mapView.getOverlays().add(ditem); // ajout de l'overlay
		mapView.invalidate(); // rafraichisement de la map

		// Ajout à la liste des éléments crée
		addDessinsToList(new itemListDessins("Danger", ((Object) ditem), 2,
				getCurrentColor(), idmarker), false);
	}

	/**
	 * Ajout d'un Triangle à l'envers de signalisation sur la carte, celui ci
	 * sera de la couleur courante.
	 * 
	 * @param lat
	 * @param lon
	 */
	private void addTriangleInverse(int lat, int lon) {
		// récupération de la ressource du marker
		int idmarker = R.drawable.triangleinverse;
		Drawable marker = getResources().getDrawable(idmarker);

		marker.mutate().setColorFilter(
				new PorterDuffColorFilter(getCurrentColor(), Mode.MULTIPLY));

		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());

		// création d'un Layout imageview pour y mettre l'item.
		ImageView dragImage = new ImageView(getActivity());
		dragImage.setImageResource(idmarker);
		RelativeLayout rl = (RelativeLayout) getActivity().findViewById(
				R.id.app);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dragImage.setLayoutParams(lp);
		dragImage.setVisibility(View.GONE);
		rl.addView(dragImage);

		// créaton de l'item
		DragItemOverlay ditem = new DragItemOverlay(marker, mResourceProxy,
				lat, lon, dragImage);
		mapView.getOverlays().add(ditem); // ajout de l'overlay
		mapView.invalidate(); // rafraichisement de la map

		// Ajout à la liste des éléments crée
		addDessinsToList(new itemListDessins("Point Sensible",
				((Object) ditem), 2, getCurrentColor(), idmarker), false);
	}

	/********************************************************************************************************
	 * GETTEURS ET SETTEURS
	 ********************************************************************************************************/

	/**
	 * @param menu
	 *            the menu to set
	 */
	public void setMenu(View menu) {
		this.menu = menu;
	}

	/**
	 * @return the menu
	 */
	public View getMenu() {
		return menu;
	}

	/**
	 * @return the mapController
	 */
	public MapController getMapController() {
		return mapController;
	}

	/**
	 * @param mapController
	 *            the mapController to set
	 */
	public void setMapController(MapController mapController) {
		this.mapController = mapController;
	}

	/**
	 * @return the mapView
	 */
	public MapView getMapView() {
		return mapView;
	}

	/**
	 * @param mapView
	 *            the mapView to set
	 */
	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}

	/**
	 * @return the app
	 */
	public View getApp() {
		return app;
	}

	/**
	 * @param app
	 *            the app to set
	 */
	public void setApp(View app) {
		this.app = app;
	}

	/**
	 * @return the btnTraitActivate
	 */
	public boolean isBtnTraitActivate() {
		return btnTraitActivate;
	}

	/**
	 * @return the lastPathOverlay
	 */
	public PathOverlay getLastPathOverlay() {
		return lastPathOverlay;
	}

	/**
	 * @param lastPathOverlay
	 *            the lastPathOverlay to set
	 */
	public void setLastPathOverlay(PathOverlay lastPathOverlay) {
		this.lastPathOverlay = lastPathOverlay;
	}

	/**
	 * @return the lastDragItem
	 */
	public DragItemOverlay getLastGragItem() {
		return lastDragItem;
	}

	/**
	 * @param lastPathOverlay
	 *            the lastPathOverlay to set
	 */
	public void setLastDragItem(DragItemOverlay lastDragItem) {
		this.lastDragItem = lastDragItem;
	}

	/**
	 * @return the currentColor
	 */
	public int getCurrentColor() {
		return currentColor;
	}

	/**
	 * @param currentColor
	 *            the currentColor to set
	 */
	public void setCurrentColor(int currentColor) {
		this.currentColor = currentColor;
	}

	/**
	 * @return the donneesDessins
	 */
	public ArrayList<itemListDessins> getDonneesDessins() {
		return donneesDessins;
	}

	/**
	 * @param donneesDessins
	 *            the donneesDessins to set
	 */
	public void setDonneesDessins(ArrayList<itemListDessins> donneesDessins) {
		this.donneesDessins = donneesDessins;
	}

	/**
	 * @return the adapterListDessin
	 */
	public AdapterListDessins getAdapterListDessin() {
		return adapterListDessin;
	}

	/**
	 * @param adapterListDessin
	 *            the adapterListDessin to set
	 */
	public void setAdapterListDessin(AdapterListDessins adapterListDessin) {
		this.adapterListDessin = adapterListDessin;
	}

	/**
	 * @param mVehicule the mVehicule to set
	 */
	public void setmVehicule(VehiculeDTO mVehicule) {
		this.mVehicule = mVehicule;
	}

	/**
	 * @return the mVehicule
	 */
	public VehiculeDTO getmVehicule() {
		return mVehicule;
	}

	/**
	 * @param adapterListMoyen the adapterListMoyen to set
	 */
	public void setAdapterListMoyen(AdapterListVehiculeToAddOnMap adapterListMoyen) {
		this.adapterListMoyen = adapterListMoyen;
	}

	/**
	 * @return the adapterListMoyen
	 */
	public AdapterListVehiculeToAddOnMap getAdapterListMoyen() {
		return adapterListMoyen;
	}

	/**
	 * @param arrayListMoyen the arrayListMoyen to set
	 */
	public void setArrayListMoyen(ArrayList<Vehicule> arrayListMoyen) {
		this.arrayListMoyen = arrayListMoyen;
	}

	/**
	 * @return the arrayListMoyen
	 */
	public ArrayList<Vehicule> getArrayListMoyen() {
		return arrayListMoyen;
	}

	/**
	 * @return the nbPath
	 */
	public int getNbPath() {
		return nbPath;
	}

	/**
	 * @param nbPath the nbPath to set
	 */
	public void setNbPath(int nbPath) {
		this.nbPath = nbPath;
	}

	/**
	 * @param mSecteur the mSecteur to set
	 */
	public void setmSecteur(SecteurInterDTO mSecteur) {
		this.mSecteur = mSecteur;
	}

	/**
	 * @return the mSecteur
	 */
	public SecteurInterDTO getmSecteur() {
		return mSecteur;
	}

}
