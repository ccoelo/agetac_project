/*	
 * Projet AGETAC	
 * Anthony LE MEE	
 * Universite de Rennes 1	
 * ISTIC	
 */
package com.agetac.view;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Default implementation of InfoWindow. It handles a text and a description. It
 * also handles optionally a sub-description and an image. Clicking on the
 * bubble will close it.
 * 
 * Class provenant du package osmdroidbonusPack et modifier selon nos besoins
 * 
 * @author Christophe Coelo - 29001702
 */
public class DefaultInfoWindow extends InfoWindow {

	static int mTitleId = 0, mDescriptionId = 0, mSubDescriptionId = 0,
			mImageId = 0; // resource ids

	private static void setResIds(Context context) {
		String packageName = context.getPackageName(); // get application
														// package name
		mTitleId = context.getResources().getIdentifier("id/bubble_title",
				null, packageName);
		mDescriptionId = context.getResources().getIdentifier(
				"id/bubble_description", null, packageName);
		mSubDescriptionId = context.getResources().getIdentifier(
				"id/bubble_subdescription", null, packageName);
		mImageId = context.getResources().getIdentifier("id/bubble_image",
				null, packageName);
		if (mTitleId == 0 || mDescriptionId == 0 || mSubDescriptionId == 0
				|| mImageId == 0) {
			System.out.println("erreur DefaultInfo");
		}
	}

	public DefaultInfoWindow(int layoutResId, MapView mapView,BubbleItemOverlay obj) {
		super(layoutResId, mapView);
		final BubbleItemOverlay o=obj;
		if (mTitleId == 0)
			setResIds(mapView.getContext());

		// default behavior: close it when clicking on the bubble:
		mView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent e) {
				if (e.getAction() == MotionEvent.ACTION_UP)
					close();
				return false; // TODO: should consume the event - but it doesn't!
			}
		});
		
		mView.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext());
				builder.setTitle("Title");

				// Set up the input
				final EditText input = new EditText(mView.getContext());
				// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
				input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
				input.setText(((ExtendedOverlayItem) o.getItem(0)).getDescription());
				input.setSelection(0, ((ExtendedOverlayItem) o.getItem(0)).getDescription().length());
				builder.setView(input);

				// Set up the buttons
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				    public void onClick(DialogInterface dialog, int which) {
				        ((ExtendedOverlayItem) o.getItem(0)).setDescription(input.getText().toString());
				    }
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int which) {
				        dialog.cancel();
				    }
				});

				builder.show();
				return true;
			}
		});
	}

	public void onOpen(ExtendedOverlayItem item) {
		String title = item.getTitle();
		if (title == null)
			title = "";
		((TextView) mView.findViewById(mTitleId /* R.id.title */))
				.setText(title);

		String snippet = item.getDescription();
		if (snippet == null)
			snippet = "";
		((TextView) mView.findViewById(mDescriptionId /* R.id.description */))
				.setText(snippet);

		// handle sub-description, hidding or showing the text view:
		TextView subDescText = (TextView) mView.findViewById(mSubDescriptionId);
		String subDesc = item.getSubDescription();
		if (subDesc != null && !("".equals(subDesc))) {
			subDescText.setText(subDesc);
			subDescText.setVisibility(View.VISIBLE);
		} else {
			subDescText.setVisibility(View.GONE);
		}

		// handle image
		ImageView imageView = (ImageView) mView.findViewById(mImageId /*
																	 * R.id.image
																	 */);
		Drawable image = item.getImage();
		if (image != null) {
			imageView.setImageDrawable(image); // or
												// setBackgroundDrawable(image)?
			imageView.setVisibility(View.VISIBLE);
		} else
			imageView.setVisibility(View.GONE);
	}

	public void onClose() {
		// by default, do nothing
	}

}
