package com.agetac.view;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Item avec bubble pouvant être "Draggable" c'est à dire pouvant être bouger dynamiquement
 * sur la map
 * 
 * @author Christophe Coelo - 29001702
 * 
 */
public class BubbleDragItemOverlay extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> items = new ArrayList<OverlayItem>();
	private Drawable marker = null;
	private OverlayItem inDrag = null;
	private ImageView dragImage = null;
	private int xDragImageOffset = 0;
	private int yDragImageOffset = 0;
	private int xDragTouchOffset = 0;
	private int yDragTouchOffset = 0;

	public BubbleDragItemOverlay(Drawable marker, ResourceProxy mResourceProxy,
			int lat, int lon, ImageView iv) {
		super(marker, mResourceProxy);
		this.marker = marker;

		dragImage = iv;

		xDragImageOffset = dragImage.getDrawable().getIntrinsicWidth() / 2;
		yDragImageOffset = dragImage.getDrawable().getIntrinsicHeight();

		items.add(new OverlayItem("Agetac", "item", new GeoPoint(lat, lon)));

		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return (items.get(i));
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);

		marker.setBounds(-marker.getIntrinsicWidth() / 2,
				-marker.getIntrinsicHeight(), marker.getIntrinsicWidth() / 2, 0);
	}

	@Override
	public int size() {
		return (items.size());
	}

	/**
	 * Suivi du marker selon la position du doigt
	 */
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {

		final int action = event.getAction();
		final int x = (int) event.getX();
		final int y = (int) event.getY();

		// final Projection pj;

		boolean result = false;

		System.out.println("onTouchEvent!");

		if (action == MotionEvent.ACTION_DOWN) {

			for (OverlayItem item : items) {
				Point p = new Point(0, 0);
				Point t = new Point(0, 0);

				mapView.getProjection().fromMapPixels(x, y, t);
				mapView.getProjection().toPixels(item.getPoint(), p);
				// hitTest(item, marker, t.x - p.x, t.y - p.y)x-p.x, y-p.y
				if (hitTest(item, marker, t.x - p.x, t.y - p.y)) {
					result = true;
					inDrag = item;
					items.remove(inDrag);
					populate();
					mapView.invalidate();

					xDragTouchOffset = 0;
					yDragTouchOffset = 0;

					setDragImagePosition(p.x, p.y);
					dragImage.setVisibility(View.VISIBLE);

					xDragTouchOffset = t.x - p.x;
					yDragTouchOffset = t.y - p.y;

					break;
				}
			}

		}

		else if (action == MotionEvent.ACTION_MOVE && inDrag != null) {
			dragImage.setVisibility(View.VISIBLE);
			setDragImagePosition(x, y);
			result = true;
		}

		else if (action == MotionEvent.ACTION_UP && inDrag != null) {
			dragImage.setVisibility(View.GONE);

			GeoPoint pt = (GeoPoint) mapView.getProjection().fromPixels(
					x - xDragTouchOffset, y - yDragTouchOffset);

			OverlayItem toDrop = new OverlayItem(inDrag.getTitle(),
					inDrag.getSnippet(), pt);

			items.add(toDrop);
			populate();
			mapView.invalidate();

			inDrag = null;
			result = true;

		}
		return (result || super.onTouchEvent(event, mapView));
	}

	private void setDragImagePosition(int x, int y) {
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) dragImage
				.getLayoutParams();
		lp.setMargins(x - xDragImageOffset - xDragTouchOffset, y
				- yDragImageOffset - yDragTouchOffset, 0, 0);
		dragImage.setLayoutParams(lp);
	}

	public boolean onSnapToItem(int arg0, int arg1, Point arg2, IMapView arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
