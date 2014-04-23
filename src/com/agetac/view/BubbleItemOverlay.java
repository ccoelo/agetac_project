/*	
 * Projet AGETAC	
 * Anthony LE MEE	
 * Universite de Rennes 1	
 * ISTIC	
 */
package com.agetac.view;

import java.util.List;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapView.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * An itemized overlay with an InfoWindow or "bubble" which opens when the user
 * taps on an overlay item, and displays item attributes. <br>
 * Items must be ExtendedOverlayItem. <br>
 * 
 * Class provenant du package osmdroidbonusPack et modifier selon nos besoins
 * 
 * @see ExtendedOverlayItem
 * @see InfoWindow
 * 
 * @author Christophe Coelo - 29001702
 */
public class BubbleItemOverlay<Item extends OverlayItem> extends
		ItemizedIconOverlay<Item> {

	protected InfoWindow mBubble; // only one for all items of this overlay =>
									// one at a time
	protected OverlayItem mItemWithBubble; // the item currently showing the
											// bubble. Null if none.

	static int layoutResId = 0;
	
	
	private int xDragImageOffset = 0;
	private int yDragImageOffset = 0;
	private int xDragTouchOffset = 0;
	private int yDragTouchOffset = 0;
	

	public BubbleItemOverlay(final Context context, final List<Item> aList,
			final MapView mapView, final InfoWindow bubble) {

		super(context, aList, new OnItemGestureListener<Item>() {
			public boolean onItemSingleTapUp(final int index,
					final OverlayItem item) {
				return false;
			}

			public boolean onItemLongPress(final int index,
					final OverlayItem item) {
				return false;
			}
		});
		// mItemsList = aList;
		if (bubble != null) {
			mBubble = bubble;
		} else {
			// build default bubble:
			String packageName = context.getPackageName();
			System.out.println(packageName);
			if (layoutResId == 0) {
				layoutResId = context.getResources().getIdentifier(
						"layout/overlay_bubble", null, packageName);
				System.out.println(layoutResId);
				if (layoutResId == 0)
					System.out.println("erreur");
			}
			mBubble = new DefaultInfoWindow(layoutResId, mapView,this);
		}
		mItemWithBubble = null;
	}

	public BubbleItemOverlay(final Context context, final List<Item> aList,
			final MapView mapView) {
		this(context, aList, mapView, null);
	}

	/**
	 * Opens the bubble on the item. For each ItemizedOverlay, only one bubble
	 * is opened at a time. If you want more bubbles opened simultaneously, use
	 * many ItemizedOverlays.
	 * 
	 * @param index
	 *            of the overlay item to show
	 * @param mapView
	 */
	public void showBubbleOnItem(final int index, final MapView mapView,
			boolean panIntoView) {
		ExtendedOverlayItem eItem = (ExtendedOverlayItem) (getItem(index));
		mItemWithBubble = eItem;
		if (eItem != null) {
			eItem.showBubble(mBubble, mapView, panIntoView);
			// setFocus((Item)eItem);
		}
	}

	/**
	 * Close the bubble (if it's opened).
	 */
	public void hideBubble() {
		mBubble.close();
		mItemWithBubble = null;
	}

	@Override
	protected boolean onSingleTapUpHelper(final int index, final Item item,
			final MapView mapView) {
		showBubbleOnItem(index, mapView, true);
		return true;
	}

	/** @return the item currently showing the bubble, or null if none. */
	public OverlayItem getBubbledItem() {
		if (mBubble.isOpen())
			return mItemWithBubble;
		else
			return null;
	}

	/**
	 * @return the index of the item currently showing the bubble, or -1 if
	 *         none.
	 */
	public int getBubbledItemId() {
		OverlayItem item = getBubbledItem();
		if (item == null)
			return -1;
		else
			return mItemList.indexOf(item);
	}

	@Override
	public synchronized Item removeItem(final int position) {
		Item result = super.removeItem(position);
		if (mItemWithBubble == result) {
			hideBubble();
		}
		return result;
	}

	@Override
	public synchronized boolean removeItem(final Item item) {
		boolean result = super.removeItem(item);
		if (mItemWithBubble == item) {
			hideBubble();
		}
		return result;
	}

	@Override
	public synchronized void removeAllItems() {
		super.removeAllItems();
		hideBubble();
	}

	@Override
	public synchronized void draw(final Canvas canvas, final MapView mapView,
			final boolean shadow) {
		// 1. Fixing drawing focused item on top in ItemizedOverlay (osmdroid
		// issue 354):
		// 2. Fixing lack of synchronization on mItemList
		if (shadow) {
			return;
		}
		final Projection pj = mapView.getProjection();
		final int size = mItemList.size() - 1;
		final Point mCurScreenCoords = new Point();

		/*
		 * Draw in backward cycle, so the items with the least index are on the
		 * front.
		 */
		for (int i = size; i >= 0; i--) {
			final Item item = getItem(i);
			if (item != mItemWithBubble) {
				pj.toMapPixels(item.mGeoPoint, mCurScreenCoords);
				onDrawItem(canvas, item, mCurScreenCoords);
			}
		}
		// draw focused item last:
		if (mItemWithBubble != null) {
			pj.toMapPixels(mItemWithBubble.mGeoPoint, mCurScreenCoords);
			Item mItemWithBubble2 = (Item) mItemWithBubble;
			onDrawItem(canvas, mItemWithBubble2, mCurScreenCoords);
		}
	}

	/*public boolean onTouchEvent(MotionEvent event, MapView mapView) {

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
	}*/

}
