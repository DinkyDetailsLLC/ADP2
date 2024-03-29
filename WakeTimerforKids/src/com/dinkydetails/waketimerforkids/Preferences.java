/*Class used to inflate Preference fragment*/
package com.dinkydetails.waketimerforkids;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dinkydetails.constants.ConstantMethodsVariables;
import com.dinkydetails.constants.PreferenceData;

public class Preferences extends Fragment implements OnClickListener {

	TextView saveButton, wakeupTimer, nightTimeSooth, napModeTimer;
	ImageView pinkimage, blueimage, greenimage, purpleimage, redimage,
			orangeimage;
	LinearLayout captureImage, backgroundColor;

	CheckBox enabledNapMode;

	
	TimePicker myTimePicker;
	TimePickerDialog timePickerDialog;
	Typeface face;

	View view;
	ImageButton toggleButton;
	private static String wakeUpTime, nigthSoothTime, napTime;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.preferences, container, false);
		face = Typeface.createFromAsset(getActivity().getAssets(), "C_BOX.TTF");

		init();

		// Click listener to open main activity
		saveButton.setOnClickListener(this);

		// background image listener set to true
		pinkimage.setOnClickListener(this);
		blueimage.setOnClickListener(this);
		greenimage.setOnClickListener(this);
		purpleimage.setOnClickListener(this);
		redimage.setOnClickListener(this);
		orangeimage.setOnClickListener(this);

		// set listener true to capture image from camera or gallery
		captureImage.setOnClickListener(this);
		backgroundColor.setOnClickListener(this);

		// set listener true for wakeup, nightsooth and nap mode
		wakeupTimer.setOnClickListener(this);
		nightTimeSooth.setOnClickListener(this);
		napModeTimer.setOnClickListener(this);

		// set listener true for toggle button
		toggleButton.setOnClickListener(this);

		// set wakeuptime, nightsoothtime and naptime
		wakeUpTime = PreferenceData
				.getStringValues(getActivity(), "WakeUpTime");
		nigthSoothTime = PreferenceData.getStringValues(getActivity(),
				"NightSoothTime");
		napTime = PreferenceData.getStringValues(getActivity(), "NapTime");

		// Check if wakeuptime, nightsoothtime and naptime has values of null
		if (wakeUpTime != null) {
			wakeupTimer.setText(wakeUpTime);
			wakeupTimer.setTypeface(face);
		}
		if (nigthSoothTime != null) {
			nightTimeSooth.setText(nigthSoothTime);
			nightTimeSooth.setTypeface(face);
		}
		if (napTime != null) {
			napModeTimer.setText(napTime);
			napModeTimer.setTypeface(face);
		}

		return view;
	}

	// method used to initialize the instance of fragments ex. textview,
	// Imageview and other layouts
	void init() {
		saveButton = (TextView) view.findViewById(R.id.saveButton);

		pinkimage = (ImageView) view.findViewById(R.id.pinkimage);
		blueimage = (ImageView) view.findViewById(R.id.blueimage);
		greenimage = (ImageView) view.findViewById(R.id.greenimage);
		purpleimage = (ImageView) view.findViewById(R.id.purpleimage);
		redimage = (ImageView) view.findViewById(R.id.redimage);
		orangeimage = (ImageView) view.findViewById(R.id.orangeimage);

		captureImage = (LinearLayout) view.findViewById(R.id.captureImage);
		backgroundColor = (LinearLayout) view
				.findViewById(R.id.backgroundcolors);

		wakeupTimer = (TextView) view.findViewById(R.id.wakeuptimer);
		nightTimeSooth = (TextView) view.findViewById(R.id.nighttimemode);
		napModeTimer = (TextView) view.findViewById(R.id.napmode);

		toggleButton = (ImageButton) view.findViewById(R.id.toggleButton);
		
		enabledNapMode = (CheckBox) view.findViewById(R.id.enabledNapMode);

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		try {
			if (PreferenceData.getStringValues(getActivity(), "ToggleStatus")
					.equalsIgnoreCase("ON")) {
				toggleButton.setBackgroundResource(R.drawable.switch_on);
			} else if (PreferenceData.getStringValues(getActivity(),
					"ToggleStatus").equalsIgnoreCase("OFF")) {
				toggleButton.setBackgroundResource(R.drawable.switch_off);
			}
		} catch (Exception e) {
			toggleButton.setBackgroundResource(R.drawable.switch_on);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.saveButton:
			// start main activity on click of save button
			startActivity(new Intent(getActivity(), MainActivity.class));
			getActivity().finish();
			break;

		case R.id.pinkimage:
			// Set background color to pink
			ConstantMethodsVariables.displayToast(getActivity(),
					"Background color set to pink");
			// Save color in preferences
			PreferenceData.setStringValues(getActivity(), "SelectedColor",
					ConstantMethodsVariables.pink);
			PreferenceData.setStringValues(getActivity(), "PictureOrColor",
					"Color");
			break;
		case R.id.blueimage:
			// Set background color to blue
			MainFragment.getColors = ConstantMethodsVariables.blue;
			ConstantMethodsVariables.displayToast(getActivity(),
					"Background color set to blue");
			ConstantMethodsVariables.setPictureOrColor = "Color";
			// Save color in preferences
			PreferenceData.setStringValues(getActivity(), "SelectedColor",
					ConstantMethodsVariables.blue);
			PreferenceData.setStringValues(getActivity(), "PictureOrColor",
					"Color");
			break;
		case R.id.greenimage:
			// Set background color to green
			ConstantMethodsVariables.displayToast(getActivity(),
					"Background color set to green");
			// Save color in preferences
			PreferenceData.setStringValues(getActivity(), "SelectedColor",
					ConstantMethodsVariables.green);
			PreferenceData.setStringValues(getActivity(), "PictureOrColor",
					"Color");
			break;
		case R.id.purpleimage:
			// Set background color to purple
			ConstantMethodsVariables.displayToast(getActivity(),
					"Background color set to purple");
			// Save color in preferences
			PreferenceData.setStringValues(getActivity(), "SelectedColor",
					ConstantMethodsVariables.purple);
			PreferenceData.setStringValues(getActivity(), "PictureOrColor",
					"Color");
			break;
		case R.id.redimage:
			// Set background color to red
			ConstantMethodsVariables.displayToast(getActivity(),
					"Background color set to red");
			// Save color in preferences
			PreferenceData.setStringValues(getActivity(), "SelectedColor",
					ConstantMethodsVariables.red);
			PreferenceData.setStringValues(getActivity(), "PictureOrColor",
					"Color");
			break;
		case R.id.orangeimage:
			// Set background color to orange
			ConstantMethodsVariables.displayToast(getActivity(),
					"Background color set to orange");
			// Save color in preferences
			PreferenceData.setStringValues(getActivity(), "SelectedColor",
					ConstantMethodsVariables.orange);
			PreferenceData.setStringValues(getActivity(), "PictureOrColor",
					"Color");
			break;
		case R.id.captureImage:
			getpictureforBackgroung();
			break;
		case R.id.backgroundcolors:

			break;
		case R.id.wakeuptimer:
			ConstantMethodsVariables.wakeupTime = true;
			ConstantMethodsVariables.nightSoothTime = false;
			ConstantMethodsVariables.napTime = false;
			openTimePickerDialog(false);
			break;
		case R.id.nighttimemode:
			ConstantMethodsVariables.wakeupTime = false;
			ConstantMethodsVariables.nightSoothTime = true;
			ConstantMethodsVariables.napTime = false;
			openTimePickerDialog(false);
			break;
		case R.id.napmode:
			if (enabledNapMode.isChecked()) {
			ConstantMethodsVariables.wakeupTime = false;
			ConstantMethodsVariables.nightSoothTime = false;
			ConstantMethodsVariables.napTime = true;
			openTimePickerDialog(false);
		} else {
			ConstantMethodsVariables.displayToast(getActivity(),
					"Click on EnableNapMode first, before setting time");
		}
			break;
		case R.id.toggleButton:
			try {
				if (PreferenceData.getStringValues(getActivity(),
						"ToggleStatus").equalsIgnoreCase("ON")) {
					PreferenceData.setStringValues(getActivity(),
							"ToggleStatus", "OFF");
					toggleButton.setBackgroundResource(R.drawable.switch_off);
				} else if (PreferenceData.getStringValues(getActivity(),
						"ToggleStatus").equalsIgnoreCase("OFF")) {
					PreferenceData.setStringValues(getActivity(),
							"ToggleStatus", "ON");
					toggleButton.setBackgroundResource(R.drawable.switch_on);
				}
			} catch (Exception e) {
				e.printStackTrace();
				PreferenceData.setStringValues(getActivity(), "ToggleStatus",
						"OFF");
				toggleButton.setBackgroundResource(R.drawable.switch_off);
			}
			break;
		case R.id.enabledNapMode:

			break;
		}

	}

	// Alert Shows to capture image from camera of gallery
	void getpictureforBackgroung() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Select")
				.setSingleChoiceItems(ConstantMethodsVariables.getImage, -1,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								if (which == 0) {
									dialog.dismiss();
									getPictureFromCamera();
								} else {
									dialog.dismiss();
									getPictureFromGallery();
								}
							}
						}).show();
	}

	/* For Image capture from camera */
	void getPictureFromCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent,
				ConstantMethodsVariables.PICTURE_TAKEN_FROM_CAMERA);
	}

	/* For Image capture from Gallary */
	void getPictureFromGallery() {
		startActivityForResult(new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
				ConstantMethodsVariables.PICTURE_TAKEN_FROM_GALLERY);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String saveImageBitmap = null;
		Bitmap bitmapImage = null;
		switch (requestCode) {
		case ConstantMethodsVariables.PICTURE_TAKEN_FROM_CAMERA:
			if (resultCode == Activity.RESULT_OK) {
				if (bitmapImage != null) {
					bitmapImage.recycle();
				}
				bitmapImage = (Bitmap) data.getExtras().get("data");
				ConstantMethodsVariables.displayToast(getActivity(),
						"Background Image Changed");
				// Save color in preferences
				saveImageBitmap = ConstantMethodsVariables
						.BitMapToString(bitmapImage);
				Log.i("Image", saveImageBitmap);
				PreferenceData.setStringValues(getActivity(), "PictureOrColor",
						"Picture");
				PreferenceData.setStringValues(getActivity(), "SavedPicture",
						saveImageBitmap);
			}
			break;

		case ConstantMethodsVariables.PICTURE_TAKEN_FROM_GALLERY:
			Uri mImageCaptureUri = null;
			if (resultCode == Activity.RESULT_OK) {
				mImageCaptureUri = data.getData();
				try {
					saveImageBitmap = ConstantMethodsVariables
							.BitMapToString(MediaStore.Images.Media.getBitmap(
									getActivity().getContentResolver(),
									mImageCaptureUri));
					Log.i("Image", saveImageBitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ConstantMethodsVariables.displayToast(getActivity(),
						"Background Image Changed");
				PreferenceData.setStringValues(getActivity(), "PictureOrColor",
						"Picture");
				PreferenceData.setStringValues(getActivity(), "SavedPicture",
						saveImageBitmap);
			}
			break;
		}

	}

	// open time picker to set time on clocks
	private void openTimePickerDialog(boolean is24r) {
		Calendar calendar = Calendar.getInstance();

		timePickerDialog = new TimePickerDialog(getActivity(),
				onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), is24r);
		timePickerDialog.setTitle("Set Alarm Time");

		timePickerDialog.show();

	}

	// Listener for time picker used to set time on clender
	OnTimeSetListener onTimeSetListener = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			Calendar calNow = Calendar.getInstance();
			Calendar calSet = (Calendar) calNow.clone();

			calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calSet.set(Calendar.MINUTE, minute);
			calSet.set(Calendar.SECOND, 0);
			calSet.set(Calendar.MILLISECOND, 0);

			if (calSet.compareTo(calNow) <= 0) {
				// Today Set time passed, count to tomorrow
				calSet.add(Calendar.DATE, 1);
			}

			setAlarm(calSet, hourOfDay, minute);
		}
	};

	// set alarm for wakeup, nightsooth and napmode
	private void setAlarm(Calendar targetCal, int hourofday, int minute) {

		if (ConstantMethodsVariables.wakeupTime == true) {
			wakeupTimer.setText(ConstantMethodsVariables.TwHourFormet(
					hourofday, minute));
			PreferenceData.setStringValues(getActivity(), "WakeUpTime",
					ConstantMethodsVariables.TwHourFormet(hourofday, minute));
			callReciever(targetCal, "WakeUpTime");
		} else if (ConstantMethodsVariables.nightSoothTime == true) {
			nightTimeSooth.setText(ConstantMethodsVariables.TwHourFormet(
					hourofday, minute));
			PreferenceData.setStringValues(getActivity(), "NightSoothTime",
					ConstantMethodsVariables.TwHourFormet(hourofday, minute));
			callReciever(targetCal, "NightSoothTime");
		} else if (ConstantMethodsVariables.napTime == true) {
			napModeTimer.setText(ConstantMethodsVariables.TwHourFormet(
					hourofday, minute));
			PreferenceData.setStringValues(getActivity(), "NapTime",
					ConstantMethodsVariables.TwHourFormet(hourofday, minute));
			callReciever(targetCal, "NapTime");
		}

		ConstantMethodsVariables.wakeupTime = false;
		ConstantMethodsVariables.nightSoothTime = false;
		ConstantMethodsVariables.napTime = false;

	}

	// call receiver for wakeuptime , nightsooth and nap mode
	void callReciever(Calendar targetCal, String call) {
		Intent intent = null;
		if (call.equalsIgnoreCase("WakeUpTime")) {
			intent = new Intent(getActivity(), WakeUpTimeReceiver.class);
			ConstantMethodsVariables.displayToast(getActivity(),
					"WakeUpTime is enabled now");
		} else if (call.equalsIgnoreCase("NightSoothTime")) {
			intent = new Intent(getActivity(), NightTimeSoothReceiver.class);
			ConstantMethodsVariables.displayToast(getActivity(),
					"NightSoothTime is enabled now");
		} else if (call.equalsIgnoreCase("NapTime")) {
			intent = new Intent(getActivity(), NapTimeReceiver.class);
			ConstantMethodsVariables.displayToast(getActivity(),
					"NapTime is enabled now");
		}

		PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),
				ConstantMethodsVariables.RQS_1, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getActivity()
				.getSystemService(Context.ALARM_SERVICE);
		if (call.equalsIgnoreCase("NapTime"))
			alarmManager.set(AlarmManager.RTC_WAKEUP,
					targetCal.getTimeInMillis() - 3600000, pendingIntent);
		else
			alarmManager.set(AlarmManager.RTC_WAKEUP,
					targetCal.getTimeInMillis(), pendingIntent);

		Log.d("AlarmManager", "AlarmManager");
	}

}