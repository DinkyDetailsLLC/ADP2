/*Class used to inflate main activity fragment*/

package com.dinkydetails.waketimerforkids;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.ClipData.Item;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dinkydetails.adapter.CustomGridViewAdapter;
import com.dinkydetails.constants.ConstantMethodsVariables;
import com.dinkydetails.constants.PreferenceData;

public class MainFragment extends Fragment implements OnClickListener, Runnable {

	GridView gridView;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	CustomGridViewAdapter customGridAdapter;

	TextView wakeUpTime, settingButton, midText, dayText;
	ImageView sun;
	View view;
	static int showhourClock = 0;
	static int colorIndex = 0;
	public static String wakeorsleep = "";
	Typeface face;

	public static ScrollView changebgColor;
	public static String getColors;
	public static Bitmap backgroungbitmap;
	public static String backgroundImage;
	Drawable drawble;

	// Clock drawables array having drwables max 12
	Integer[] mThumbIds12 = { R.drawable.clock, R.drawable.clock,
			R.drawable.clock, R.drawable.clock, R.drawable.clock,
			R.drawable.clock, R.drawable.clock, R.drawable.clock,
			R.drawable.clock, R.drawable.clock, R.drawable.clock,
			R.drawable.clock };

	// SetInterface to change soothe background color
	interface MyHandler {
		void changeBackground();
	}

	public static Thread bgColor = new Thread(new MainFragment());

	private MyHandler handler = new MyHandler() {

		@Override
		public void changeBackground() {
			// TODO Auto-generated method stub
			new changeBGonUiThread().execute();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// inflate main activity layout
		view = inflater.inflate(R.layout.activity_main, container, false);
		face = Typeface.createFromAsset(getActivity().getAssets(), "C_BOX.TTF");

		// method used to initialize the instance of fragments ex textview,
		// gridview and other layouts
		init();

		// wakeupTime
		wakeUpTime.setText(ConstantMethodsVariables.getSystemTime());
		wakeUpTime.setTypeface(face);
		/// Change the text depending on the time brackets.
		showhourClock = ConstantMethodsVariables.getSystemHour();
		System.out.println("showhourClock>>>>" + showhourClock);
		if (showhourClock >= 5 && showhourClock < 11) {
			System.out.println("1");
			dayText.setText("MORNING");
		} else if (showhourClock >= 11 && showhourClock < 17) {
			System.out.println("2");
			dayText.setText("AFTERNOON");
		} else if (showhourClock >= 17 && showhourClock < 23) {
			System.out.println("3");
			dayText.setText("EVENING");
		} else if (showhourClock == 23) {
			System.out.println("4");
			dayText.setText("Sleep Time");
		} else if (showhourClock >= 0 && showhourClock < 5) {
			System.out.println("5");
			dayText.setText("Sleep Time");
		} else {
			System.out.println("6");
		}

		if (showhourClock > 12) {
			showhourClock = showhourClock - 12;
		}

		settingButton.setOnClickListener(this);

		// Show Clock if Switch is On
		try {
			if (PreferenceData.getStringValues(getActivity(), "ToggleStatus")
					.equalsIgnoreCase("ON")) {
				gridView.setVisibility(View.VISIBLE);
				gridView.setAdapter(new CustomGridViewAdapter(getActivity(),
						mThumbIds12, showhourClock));
			} else {
				gridView.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			gridView.setVisibility(View.VISIBLE);
			gridView.setAdapter(new CustomGridViewAdapter(getActivity(),
					mThumbIds12, showhourClock));
		}
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// wakeupTime
		wakeUpTime.setText(ConstantMethodsVariables.getSystemTime());
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// wakeupTime
		wakeUpTime.setText(ConstantMethodsVariables.getSystemTime());
		// Change Color or Picture of background as selected in Preference
		// screen
		wakeorsleep = PreferenceData.getStringValues(getActivity(),
				"WakeUpOrSleep");
		try {
			Log.i("wakeorsleep>>>", wakeorsleep);
		} catch (Exception e) {
			wakeorsleep = "noValue";
			Log.i("wakeorsleep>>>", wakeorsleep);
		}
		if (wakeorsleep.equalsIgnoreCase("TimeToSleep")) {
			changebgColor.setBackgroundResource(R.drawable.black_opacity);
			sun.setBackgroundResource(R.drawable.moon);
			setOpacity();
		} else {
			try {
				ConstantMethodsVariables.setPictureOrColor = PreferenceData
						.getStringValues(getActivity(), "PictureOrColor");
				getColors = PreferenceData.getStringValues(getActivity(),
						"SelectedColor");
				if (ConstantMethodsVariables.setPictureOrColor
						.equalsIgnoreCase("Color")) {
					changebgColor.setBackgroundColor(Color
							.parseColor(getColors));
					sun.setBackgroundResource(R.drawable.sun);
					settingButton.getBackground().setAlpha(255);
				} else if (ConstantMethodsVariables.setPictureOrColor
						.equalsIgnoreCase("Picture")) {
					if (backgroungbitmap != null) {
						backgroungbitmap.recycle();
					}
					backgroundImage = PreferenceData.getStringValues(
							getActivity(), "SavedPicture");
					drawble = new BitmapDrawable(getResources(),
							ConstantMethodsVariables
									.StringToBitMap(backgroundImage));
					changebgColor.setBackgroundDrawable(drawble);
					sun.setBackgroundResource(R.drawable.sun);
				}
			} catch (Exception e) {
				e.printStackTrace();
				sun.setBackgroundResource(R.drawable.sun);
				settingButton.getBackground().setAlpha(255);
				getColors = ConstantMethodsVariables.blue;
				changebgColor.setBackgroundColor(Color.parseColor(getColors));
			}
		}

	}

	void init() {
		gridView = (GridView) view.findViewById(R.id.gridview);
		wakeUpTime = (TextView) view.findViewById(R.id.WakeUpTime);
		settingButton = (TextView) view.findViewById(R.id.settingButton);
		changebgColor = (ScrollView) view.findViewById(R.id.mainScrollLayout);
		sun = (ImageView) view.findViewById(R.id.sun);
		midText = (TextView) view.findViewById(R.id.midText);
		dayText = (TextView) view.findViewById(R.id.dayTime);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.settingButton:
			startActivity(new Intent(getActivity(), PreferenceFragment.class));
			getActivity().finish();
			break;
		}
	}

	// set opacity of the screen to 80%
	void setOpacity() {
		// Setting Button
		settingButton.getBackground().setAlpha(50);
		wakeUpTime.setTextColor(Color
				.parseColor(ConstantMethodsVariables.lightwhite));
		midText.setTextColor(Color
				.parseColor(ConstantMethodsVariables.lightwhite));
		dayText.setTextColor(Color
				.parseColor(ConstantMethodsVariables.lightwhite));

	}
//Soothe mode will be set to 3 seconds for testing but then back to 10 minutes once figured out
	//******************** NOT WORKING RIGHT NOW *************************
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int index = 0; index < ConstantMethodsVariables.SoothArray.length; index++) {
			colorIndex = index;
			handler.changeBackground();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class changeBGonUiThread extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
		//tells the app to go through the soothe colors and display
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			changebgColor
					.setBackgroundColor(Color
							.parseColor(ConstantMethodsVariables.SoothArray[colorIndex]));
		}
	}
}
