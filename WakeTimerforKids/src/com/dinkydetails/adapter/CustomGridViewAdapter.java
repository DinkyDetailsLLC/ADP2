package com.dinkydetails.adapter;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.dinkydetails.waketimerforkids.MainFragment;

public class CustomGridViewAdapter extends BaseAdapter {
	private Activity mContext;
	private Context cContext;
	private Integer[] mThumbIds;
	private int arraylength;

	public CustomGridViewAdapter(Activity c, Integer[] m, int length) {
		mContext = c;
		mThumbIds = m;
		arraylength = length;
	}

	public CustomGridViewAdapter(Context ctx, Integer[] m, int length) {
		cContext = ctx;
		mThumbIds = m;
		arraylength = length;
	}

	public int getCount() {
		return arraylength;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = null;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			try {
				imageView = new ImageView(mContext);
			} catch (Exception e) {
				imageView = new ImageView(cContext);
			}
			imageView.setLayoutParams(new GridView.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(mThumbIds[position]);
		if (MainFragment.wakeorsleep.equalsIgnoreCase("TimeToSleep"))
			imageView.getDrawable().setAlpha(10);
		else {
			imageView.getDrawable().setAlpha(255);
		}
		return imageView;
	}

	public Integer[] AddItems(Integer[] a) {
		final Integer[] aThumbIds;
		aThumbIds = a;
		Integer[] a2 = new Integer[mThumbIds.length + aThumbIds.length];
		System.arraycopy(mThumbIds, 0, a2, 0, mThumbIds.length);
		System.arraycopy(aThumbIds, 0, a2, mThumbIds.length, aThumbIds.length);
		return mThumbIds;
	}

}
