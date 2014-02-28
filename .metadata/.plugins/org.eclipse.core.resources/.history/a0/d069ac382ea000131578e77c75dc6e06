package com.dinkydetails.daydream;

import java.util.ArrayList;

import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.service.dreams.DreamService;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.dinkydetails.adapter.CustomGridViewAdapter;
import com.dinkydetails.constants.ConstantMethodsVariables;
import com.dinkydetails.constants.PreferenceData;
import com.dinkydetails.waketimerforkids.MainActivity;
import com.dinkydetails.waketimerforkids.MainFragment;
import com.dinkydetails.waketimerforkids.R;

public class DayDreamActivity extends DreamService implements Runnable {

	// GridView to show clocks in grid
	GridView gridView;
	// ArrayList to add clocks in lits
	ArrayList<Item> gridArray = new ArrayList<Item>();
	// Custom adapter to show clocks in gridview
	CustomGridViewAdapter customGridAdapter;

	// PowerManager helps to wake screen every time if it is mainActivity
	PowerManager.WakeLock mWakeLock;

	/* TextClock */
	TextClock textClock;

	TextView wakeUpTime, settingButton, midText, dayText;
	ImageView sun;

	static Context thiscontext;
	static int showhourClock = 0;
	static int colorIndex = 0;
	public static String wakeorsleep = "";
	public static boolean singleNapMode = false;
	public static boolean runNaPTimeThread = false;

	// TypeFace to change the font in chocolate font
	Typeface face;

	/* Frame layout to set the color or image as background */
	public static FrameLayout changebgColor;
	/* Relative layout to set the color or image as for soothing */
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

	// SetInterface to change sooth background color
	interface MyHandler {
		void changeBackground();
	}

	/* handler to call background instance on UserInterface */
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
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		// Exit dream upon user touch
		setInteractive(false);
		// Hide system UI
		setFullscreen(true);
		// Set the dream layout
		setContentView(R.layout.activity_main);

		thiscontext = DayDreamActivity.this;
	}

	@Override
	public void onDreamingStarted() {
		// TODO Auto-generated method stub
		super.onDreamingStarted();

		
		 // Method used to initialize the instance of fragments ex textview,
		 // gridview and other layouts
		 
		init();

		// wakeupTime
		wakeUpTime.setText(ConstantMethodsVariables.getSystemTime());
		wakeUpTime.setTypeface(face);
		textClock.setTypeface(face);

		// Get the current hours of system 
		showhourClock = ConstantMethodsVariables.getSystemHour();

		// Conditions to check what time it is of the day 
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

		// Show Clock if Switch is On 
		try {
			if (PreferenceData.getStringValues(DayDreamActivity.this,
					"ToggleStatus").equalsIgnoreCase("ON")) {
				gridView.setVisibility(View.VISIBLE);
				gridView.setAdapter(new CustomGridViewAdapter(
						DayDreamActivity.this, mThumbIds12, showhourClock));
			} else {
				gridView.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			gridView.setVisibility(View.VISIBLE);
			gridView.setAdapter(new CustomGridViewAdapter(
					DayDreamActivity.this, mThumbIds12, showhourClock));
		}

		
		 // Change Color or Picture of background as selected in Preference
		 // screen
		 
		wakeorsleep = PreferenceData.getStringValues(DayDreamActivity.this,
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
			PreferenceData.setStringValues(DayDreamActivity.this, "SoothTime",
					"SoothTimeOver");
			checkImageorColorBG();
			runSoothingThread();
		} else if (wakeorsleep.equalsIgnoreCase("SetNapTime")) {
			singleNapMode = false;
			PreferenceData.setStringValues(DayDreamActivity.this, "SetNapTime",
					"NapTimeOver");
			checkImageorColorBG();
			runSoothingThread();
		} else if (wakeorsleep.equalsIgnoreCase("TimeToSleep")) {
			sun.setBackgroundResource(R.drawable.moon);
			checkImageorColorBG();
			setOpacity();
		} else {
			try {
				ConstantMethodsVariables.setPictureOrColor = PreferenceData
						.getStringValues(DayDreamActivity.this,
								"PictureOrColor");
				getColors = PreferenceData.getStringValues(
						DayDreamActivity.this, "SelectedColor");
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
							DayDreamActivity.this, "SavedPicture");
					drawble = new BitmapDrawable(getResources(),
							ConstantMethodsVariables
									.StringToBitMap(backgroundImage));
					changebgColor.setBackgroundDrawable(drawble);
					sun.setBackgroundResource(R.drawable.sun);
					settingButton.getBackground().setAlpha(255);
				}
			} catch (Exception e) {
				sun.setBackgroundResource(R.drawable.sun);
				settingButton.getBackground().setAlpha(255);
				getColors = ConstantMethodsVariables.blue;
				changebgColor.setBackgroundColor(Color.parseColor(getColors));
			}
		}

		/* Check if nap mode is enabled or not */
		try {
			isNapModeEnabled = PreferenceData.getBooleanValues(
					DayDreamActivity.this, "Runthread");
			if (isNapModeEnabled) {
				singleNapMode = false;
				Thread bgColor = new Thread(new MainFragment());
				bgColor.start();
				PreferenceData.setBooleanValues(DayDreamActivity.this,
						"Runthread", false);
			}
		} catch (Exception e) {
		}
	}

	// Run the thread for time soothing 
	void runSoothingThread() {
		Thread bgColor = new Thread(new MainFragment());
		bgColor.start();
		PreferenceData.setBooleanValues(DayDreamActivity.this, "Runthread",
				false);
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

	@Override
	public void onDreamingStopped() {
		// TODO Auto-generated method stub
		super.onDreamingStopped();
	}

	@Override
	public void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}

	void init() {
		gridView = (GridView) findViewById(R.id.gridview);
		wakeUpTime = (TextView) findViewById(R.id.WakeUpTime);
		textClock = (TextClock) findViewById(R.id.textclock1);
		settingButton = (TextView) findViewById(R.id.settingButton);
		changebgColor = (FrameLayout) findViewById(R.id.mainScrollLayout);
		tintedColor = (RelativeLayout) findViewById(R.id.mainColorlayout);
		sun = (ImageView) findViewById(R.id.sun);
		midText = (TextView) findViewById(R.id.midText);
		dayText = (TextView) findViewById(R.id.dayTime);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int index = 0; index <= ConstantMethodsVariables.SoothArray.length; index++) {
			colorIndex = index - 1;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			handler.changeBackground();
		}
	}

	// AsyncTask to show background color on UserInterface 
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
				// tinted color set through array containing soothing colors
				tintedColor
						.setBackgroundColor(Color
								.parseColor(ConstantMethodsVariables.SoothArray[colorIndex]));
				// set alpha 204 equal to .8 in xml
				tintedColor.getBackground().setAlpha(204);
			} catch (Exception e) {
				if (!singleNapMode) {
					singleNapMode = true;
					// Open Night mode when soothing is done
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

	// AsyncTask to show background color on UserInterface 
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

	// Thread Start to keep back to normal state of main activity
	Thread backtonormalState = new Thread() {
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
			}
			new changeToNormalMode().execute();
		};
	};

	// backtoNormalPosition 
	void backtonormalposition() {
		try {
			getColors = PreferenceData.getStringValues(DayDreamActivity.this,
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
				backgroundImage = PreferenceData.getStringValues(
						DayDreamActivity.this, "SavedPicture");
				drawble = new BitmapDrawable(getResources(),
						ConstantMethodsVariables
								.StringToBitMap(backgroundImage));
				changebgColor.setBackgroundDrawable(drawble);
				sun.setBackgroundResource(R.drawable.sun);
			}
		} catch (Exception e) {
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
			getColors = PreferenceData.getStringValues(DayDreamActivity.this,
					"SelectedColor");
			if (ConstantMethodsVariables.setPictureOrColor
					.equalsIgnoreCase("Color")) {
				changebgColor.setBackgroundColor(Color.parseColor(getColors));
				if (wakeorsleep.equalsIgnoreCase("TimeToSleep"))
					sun.setBackgroundResource(R.drawable.moon);
				else
					sun.setBackgroundResource(R.drawable.sun);
				settingButton.getBackground().setAlpha(255);
			} else if (ConstantMethodsVariables.setPictureOrColor
					.equalsIgnoreCase("Picture")) {
				if (backgroungbitmap != null) {
					backgroungbitmap.recycle();
				}
				backgroundImage = PreferenceData.getStringValues(
						DayDreamActivity.this, "SavedPicture");
				drawble = new BitmapDrawable(getResources(),
						ConstantMethodsVariables
								.StringToBitMap(backgroundImage));
				changebgColor.setBackgroundDrawable(drawble);
				if (wakeorsleep.equalsIgnoreCase("TimeToSleep"))
					sun.setBackgroundResource(R.drawable.moon);
				else
					sun.setBackgroundResource(R.drawable.sun);
			}
		} catch (Exception e) {
			try {
				if (wakeorsleep.equalsIgnoreCase("TimeToSleep"))
					sun.setBackgroundResource(R.drawable.moon);
				else
					sun.setBackgroundResource(R.drawable.sun);
			} catch (Exception e1) {
				sun.setBackgroundResource(R.drawable.sun);
			}
			settingButton.getBackground().setAlpha(255);
			getColors = ConstantMethodsVariables.blue;
			changebgColor.setBackgroundColor(Color.parseColor(getColors));
		}
	}
}
