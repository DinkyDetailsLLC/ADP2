/*Class used to inflate main activity fragment*/

package com.dinkydetails.waketimerforkids;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.dinkydetails.adapter.CustomGridViewAdapter;
import com.dinkydetails.constants.ConstantMethodsVariables;
import com.dinkydetails.constants.PreferenceData;

public class MainFragment extends Fragment implements OnClickListener, Runnable {

	// GridView to show clocks in grid
	GridView gridView;
	// ArrayList to add clocks in lits
	ArrayList<Item> gridArray = new ArrayList<Item>();
	// Custom adapter to show clocks in gridview
	CustomGridViewAdapter customGridAdapter;
	
	// PowerManager helps to wakr screen everytime if it is mainActivity
	PowerManager.WakeLock mWakeLock;

	TextView wakeUpTime, settingButton, midText, dayText;
	TextClock textClock;
	ImageView sun;
	View view;
	
	static Context thiscontext;
	static int showhourClock = 0;
	static int colorIndex = 0;
	public static String wakeorsleep = "";
	public static boolean singleNapMode = false;
	public static boolean runNaPTimeThread = false;
	
	// TypeFace to change the font in chocolate font
	Typeface face;
	
	//Frame layout to set the color or image as background
	public static FrameLayout changebgColor;
	
	//Relative layout to set the color or image as for soothing
	public static RelativeLayout tintedColor;
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

	static boolean isNapModeEnabled = false;
	
	// SetInterface to change soothe background color
	interface MyHandler {
		void changeBackground();
	}

	// public static Thread bgColor = new Thread(new MainFragment());
	
	// handler to call background instance on UserInterface
	private MyHandler handler = new MyHandler() {

		@Override
		public void changeBackground() {
			// TODO Auto-generated method stub
			new changeBGonUiThread().execute();
		}
	};
	
	// SetInterface for NapMode To come back in normal state
	interface NapHandler {
		void getBackToNormalstate();
	}

	/* handler to handle state of system */
	private NapHandler napHandler = new NapHandler() {

		@Override
		public void getBackToNormalstate() {
			// TODO Auto-generated method stub

		};
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// inflate main activity layout
		view = inflater.inflate(R.layout.activity_main, container, false);
		face = Typeface.createFromAsset(getActivity().getAssets(), "C_BOX.TTF");
		thiscontext = getActivity();

		// method used to initialize the instance of fragments ex textview,
		// gridview and other layouts
		init();

		// wakeupTime
		wakeUpTime.setText(ConstantMethodsVariables.getSystemTime());
		wakeUpTime.setTypeface(face);
		textClock.setTypeface(face);
		
		/// Change the text depending on the time brackets.
		showhourClock = ConstantMethodsVariables.getSystemHour();
		
		/* Conditions to check what time it is of the day */
		//System.out.println("showhourClock>>>>" + showhourClock);
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
		
		 //This code together with the one in onDestroy() will make the screen
		 // be always on until this Activity gets destroyed.
		final PowerManager pm = (PowerManager) getActivity().getSystemService(
				Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
		mWakeLock.acquire();
		
		// wakeupTime
		wakeUpTime.setText(ConstantMethodsVariables.getSystemTime());
		
		// Change Color or Picture of background as selected in Preference
		// screen
		wakeorsleep = PreferenceData.getStringValues(getActivity(),
				"WakeUpOrSleep");
		
		
		 // Check which part of app is in running mode either wakeup or sleep
		 // mode
		
		try {
			Log.i("wakeorsleep>>>", wakeorsleep);
		} catch (Exception e) {
			wakeorsleep = "noValue";
			Log.i("wakeorsleep>>>", wakeorsleep);
		}
		if (wakeorsleep.equalsIgnoreCase("SoothTime")) {
			singleNapMode = false;
			PreferenceData.setStringValues(getActivity(), "SoothTime",
					"SoothTimeOver");
			checkImageorColorBG();
		} else if (wakeorsleep.equalsIgnoreCase("SetNapTime")) {
			singleNapMode = false;
			PreferenceData.setStringValues(getActivity(), "SetNapTime",
					"NapTimeOver");
			checkImageorColorBG();
		} else if (wakeorsleep.equalsIgnoreCase("TimeToSleep")) {
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
					settingButton.getBackground().setAlpha(255);
				}
			} catch (Exception e) {
				e.printStackTrace();
				sun.setBackgroundResource(R.drawable.sun);
				settingButton.getBackground().setAlpha(255);
				getColors = ConstantMethodsVariables.blue;
				changebgColor.setBackgroundColor(Color.parseColor(getColors));
			}
		}

		/* Check if nap mode is enabled or not */
		try {
			isNapModeEnabled = PreferenceData.getBooleanValues(getActivity(),
					"Runthread");
			if (isNapModeEnabled) {
				singleNapMode = false;
				Thread bgColor = new Thread(new MainFragment());
				bgColor.start();
				PreferenceData.setBooleanValues(getActivity(), "Runthread",
						false);
			}
		} catch (Exception e) {
		}

	}
	
	//Run the thread for time soothing 
	void runSoothingThread() {
		Thread bgColor = new Thread(new MainFragment());
		bgColor.start();
		PreferenceData.setBooleanValues(getActivity(), "Runthread", false);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		/* release the wakelock if activity is destroyed */
		mWakeLock.release();
	}

	
	 // method used to initialize the instance of fragments ex textview, gridview
	 // and other layouts
	void init() {
		gridView = (GridView) view.findViewById(R.id.gridview);
		wakeUpTime = (TextView) view.findViewById(R.id.WakeUpTime);
		textClock = (TextClock) view.findViewById(R.id.textclock1);
		settingButton = (TextView) view.findViewById(R.id.settingButton);
		changebgColor = (FrameLayout) view.findViewById(R.id.mainScrollLayout);
		tintedColor = (RelativeLayout) view.findViewById(R.id.mainColorlayout);
		sun = (ImageView) view.findViewById(R.id.sun);
		midText = (TextView) view.findViewById(R.id.midText);
		dayText = (TextView) view.findViewById(R.id.dayTime);
	}

	//Get the Click listener of Views
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
		// Change background color
		tintedColor.setBackgroundColor(Color.parseColor("#000000"));
		// tintedColor.getBackground().setAlpha(20);
		// Setting Button
		settingButton.getBackground().setAlpha(20);
		// WakeUpTime Button
		wakeUpTime.setTextColor(Color.argb(100, 80, 80, 100));
		// In The Text
		midText.setTextColor(Color.argb(100, 100, 100, 100));
		// Day Text
		dayText.setTextColor(Color.argb(100, 100, 100, 100));
		
	}
//Soothe mode will be set to 3 seconds for testing but then back to 10 minutes once figured out
	//******************** NOT WORKING RIGHT NOW *************************
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int index = 0; index <= ConstantMethodsVariables.SoothArray.length; index++) {
			colorIndex = index - 1;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			handler.changeBackground();
		}
	}

	//AsyncTask to show background color on UserInterface
	private class changeBGonUiThread extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				System.out.println("SoothArray>>"
						+ ConstantMethodsVariables.SoothArray[colorIndex]);
				changebgColor
						.setBackgroundColor(Color
								.parseColor(ConstantMethodsVariables.SoothArray[colorIndex]));
			} catch (Exception e) {
				if (!singleNapMode) {
					singleNapMode = true;
					PreferenceData.setStringValues(thiscontext,
							"WakeUpOrSleep", "TimeToSleep");
					Intent i = new Intent(thiscontext, MainActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					thiscontext.startActivity(i);
				}
				try {
					runNaPTimeThread = PreferenceData.getBooleanValues(
							thiscontext, "RunNapTimethread");
				} catch (Exception e1) {
					e1.printStackTrace();
					runNaPTimeThread = false;
				}
				System.out.println("NAPTIME>>>>>>>>>>>>>>" + runNaPTimeThread);
				if (runNaPTimeThread) {
					System.out.println("NAPTIMEINSIDE>>>>>>>>>>>>>>"
							+ runNaPTimeThread);
					PreferenceData.setBooleanValues(thiscontext,
							"RunNapTimethread", false);
					backtonormalState.start();
				}
			}
		}
	}
	
	//AsyncTask to show background color on UserInterface 
	private class changeToNormalMode extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			backtonormalposition();
		}
	}

	Thread backtonormalState = new Thread() {
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			new changeToNormalMode().execute();
		};
	};

	// backtoNormalPosition 
	void backtonormalposition() {
		try {
			getColors = PreferenceData.getStringValues(getActivity(),
					"SelectedColor");
			if (ConstantMethodsVariables.setPictureOrColor
					.equalsIgnoreCase("Color")) {
				changebgColor.setBackgroundColor(Color.parseColor(getColors));
				sun.setBackgroundResource(R.drawable.sun);
				settingButton.getBackground().setAlpha(255);
			} else if (ConstantMethodsVariables.setPictureOrColor
					.equalsIgnoreCase("Picture")) {
				if (backgroungbitmap != null) {
					backgroungbitmap.recycle();
				}
				backgroundImage = PreferenceData.getStringValues(getActivity(),
						"SavedPicture");
				drawble = new BitmapDrawable(getResources(),
						ConstantMethodsVariables
								.StringToBitMap(backgroundImage));
				changebgColor.setBackgroundDrawable(drawble);
				sun.setBackgroundResource(R.drawable.sun);
			}
		} catch (Exception e) {
			e.printStackTrace();
			PreferenceData.setStringValues(thiscontext, "WakeUpOrSleep",
					"WakeUp");
			Intent i = new Intent(thiscontext, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			thiscontext.startActivity(i);
		}
	}

	
	 // Method used to check which part was saved in preferences either color or
	 // picture before running soothing
	 
	void checkImageorColorBG() {
		try {
			getColors = PreferenceData.getStringValues(getActivity(),
					"SelectedColor");
			if (ConstantMethodsVariables.setPictureOrColor
					.equalsIgnoreCase("Color")) {
				changebgColor.setBackgroundColor(Color.parseColor(getColors));
				sun.setBackgroundResource(R.drawable.sun);
				settingButton.getBackground().setAlpha(255);
			} else if (ConstantMethodsVariables.setPictureOrColor
					.equalsIgnoreCase("Picture")) {
				if (backgroungbitmap != null) {
					backgroungbitmap.recycle();
				}
				backgroundImage = PreferenceData.getStringValues(getActivity(),
						"SavedPicture");
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

		// Run the thread for time soothing 
		Thread bgColor = new Thread(new MainFragment());
		bgColor.start();
		PreferenceData.setBooleanValues(getActivity(), "Runthread", false);
	}
}
