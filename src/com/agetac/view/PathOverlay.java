/*	
 * Projet AGETAC	
 * Anthony LE MEE	
 * Universite de Rennes 1	
 * ISTIC	
 */
package com.agetac.view;

import java.util.ArrayList;

import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapView.Projection;
import org.osmdroid.views.overlay.Overlay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.agetac.activity.VueSitac;

/**
 * Class pour l'ajout d'un PathOverlay sur la map de la sitac
 * 
 * Class PathOverlay prise de l'outil libre de droit osmdroid et modifier pour notre usage
 * 
 * @author Christophe Coelo - 29001702
 * 
 */

public class PathOverlay extends Overlay {
	/**
	 * Listes des points du chemin
	 */
	private ArrayList<Point> mPoints;

	/**
	 * Nombre de points traiter
	 */
	private int mPointsPrecomputed;

	/**
	 * Paint settings.
	 */
	protected Paint mPaint = new Paint();
	private final Path mPath = new Path();
	private final Point mTempPoint1 = new Point();
	private final Point mTempPoint2 = new Point();

	// bounding rectangle for the current line segment.
	private final Rect mLineBounds = new Rect();
	private Bitmap bmp;
	private Canvas c;
	/**
	 * Instance de la vue, pour observer
	 */
	private VueSitac vs;

	// ===========================================================
	// Constructors
	// ===========================================================

	public PathOverlay(final int color, final ResourceProxy pResourceProxy,
			VueSitac vs) {
		super(pResourceProxy);
		this.vs = vs;
		this.mPaint.setColor(color);
		this.mPaint.setStrokeWidth(2.0f);
		this.mPaint.setStyle(Paint.Style.STROKE);

		this.clearPath();
	}

	/**
	 * Initialisation du Chemin
	 */
	public void clearPath() {
		this.mPoints = new ArrayList<Point>();
		this.mPointsPrecomputed = 0;
		
	}

	/**
	 * Ajout d'un point au chemin
	 * 
	 * @param pt
	 *            : Geopoint à ajouter
	 */
	public void addPoint(final GeoPoint pt) {
		this.addPoint(pt.getLatitudeE6(), pt.getLongitudeE6());
	}

	/**
	 * Ajout d'un point au chemin
	 * 
	 * @param latitudeE6
	 * @param longitudeE6
	 */
	public void addPoint(final int latitudeE6, final int longitudeE6) {
		this.mPoints.add(new Point(latitudeE6, longitudeE6));
	}

	/**
	 * This method draws the line. Note - highly optimized to handle long paths,
	 * proceed with care. Should be fine up to 10K points.
	 */
	@Override
	protected void draw(final Canvas canvas, final MapView mapView,
			final boolean shadow) {

		if (shadow) {
			return;
		}

		if (this.mPoints.size() < 2) {
			// nothing to paint
			return;
		}

		final Projection pj = mapView.getProjection();

		// precompute new points to the intermediate projection.
		final int size = this.mPoints.size();

		while (this.mPointsPrecomputed < size) {
			final Point pt = this.mPoints.get(this.mPointsPrecomputed);
			pj.toMapPixelsProjected(pt.x, pt.y, pt);

			this.mPointsPrecomputed++;
		}

		Point screenPoint0 = null; // points on screen
		Point screenPoint1 = null;
		Point projectedPoint0; // points from the points list
		Point projectedPoint1;

		// clipping rectangle in the intermediate projection, to avoid
		// performing projection.
		final Rect clipBounds = pj.fromPixelsToProjected(pj.getScreenRect());

		mPath.rewind();
		projectedPoint0 = this.mPoints.get(size - 1);
		mLineBounds.set(projectedPoint0.x, projectedPoint0.y,
				projectedPoint0.x, projectedPoint0.y);

		for (int i = size - 2; i >= 0; i--) {
			// compute next points
			projectedPoint1 = this.mPoints.get(i);
			mLineBounds.union(projectedPoint1.x, projectedPoint1.y);

			if (!Rect.intersects(clipBounds, mLineBounds)) {
				// skip this line, move to next point
				projectedPoint0 = projectedPoint1;
				screenPoint0 = null;
				continue;
			}

			// the starting point may be not calculated, because previous
			// segment was out of clip
			// bounds
			if (screenPoint0 == null) {
				screenPoint0 = pj.toMapPixelsTranslated(projectedPoint0,
						this.mTempPoint1);
				mPath.moveTo(screenPoint0.x, screenPoint0.y);
			}

			screenPoint1 = pj.toMapPixelsTranslated(projectedPoint1,
					this.mTempPoint2);

			// skip this point, too close to previous point
			if (Math.abs(screenPoint1.x - screenPoint0.x)
					+ Math.abs(screenPoint1.y - screenPoint0.y) <= 1) {
				continue;
			}

			mPath.lineTo(screenPoint1.x, screenPoint1.y);

			// update starting point to next position
			projectedPoint0 = projectedPoint1;
			screenPoint0.x = screenPoint1.x;
			screenPoint0.y = screenPoint1.y;
			mLineBounds.set(projectedPoint0.x, projectedPoint0.y,
					projectedPoint0.x, projectedPoint0.y);
		}

		canvas.drawPath(mPath, this.mPaint);
	}


	@Override
	/**
	 * Method onTouchEvent qui ajoute un point au chemin courant lorsque 
	 * l'utilisateur click sur la map.
	 */
	public boolean onTouchEvent(MotionEvent e, MapView mapView) {
		if (vs.isBtnTraitActivate()) { //si la sitac est en mode edition de chemin
			if (e.getAction() == MotionEvent.ACTION_DOWN) {
				//récupération des coordonnées
				Projection proj = mapView.getProjection();
				GeoPoint loc = (GeoPoint) proj.fromPixels((int) e.getX(),
						(int) e.getY());
				//ajout du point
				this.addPoint(loc);
				mapView.invalidate(); //rafraichissement de la map
			}

		}
		return true;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setColor(final int color) {
		this.mPaint.setColor(color);
	}

	public void setAlpha(final int a) {
		this.mPaint.setAlpha(a);
	}

	public Paint getPaint() {
		return mPaint;
	}

	public void setPaint(Paint pPaint) {
		if (pPaint == null)
			throw new IllegalArgumentException("pPaint argument cannot be null");
		mPaint = pPaint;
	}

	public int getNumberOfPoints() {
		return this.mPoints.size();
	}

	/**
	 * @return the mPoints
	 */
	public ArrayList<Point> getmPoints() {
		return mPoints;
	}

	/**
	 * @param mPoints the mPoints to set
	 */
	public void setmPoints(ArrayList<Point> mPoints) {
		this.mPoints = mPoints;
	}
	
	public Bitmap getBitmap(){
		/*int w = canvas.getWidth();
		int h = canvas.getHeight();
	    Bitmap toDisk = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);*/
	    c.setBitmap(bmp);
		return bmp;
	}
}
