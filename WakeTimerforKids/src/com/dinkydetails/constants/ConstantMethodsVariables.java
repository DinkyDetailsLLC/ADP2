package com.dinkydetails.constants;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

public class ConstantMethodsVariables {

	static Calendar getCurrentTime = Calendar.getInstance();
	
	public static String[] getImage = { "Camera", "Gallery" };
	public final static int RQS_1 = 1;

	public static boolean wakeupTime = false;
	public static boolean nightSoothTime = false;
	public static boolean napTime = false;

	//Used instead of textClock to format the textviews. 
	public static String getSystemTime() {
		String hour = String.valueOf(getCurrentTime.get(Calendar.HOUR_OF_DAY));
		String minute = String.valueOf(getCurrentTime.get(Calendar.MINUTE));
		String totaltime = hour + ":" + minute;
		return totaltime;
	}

	public static int getSystemHour() {
		return getCurrentTime.get(Calendar.HOUR_OF_DAY);
	}
	//Setting the Colors up for whatever event is taking place
	public static String pink = "#d21771";
	public static String blue = "#0060ff";
	public static String green = "#3bbd01";
	public static String purple = "#6701bd";
	public static String red = "#ff003c";
	public static String orange = "#ffa200";

	public static String sooth1 = "#71726f";
	public static String sooth2 = "#b4b7b0";
	public static String sooth3 = "#7a8c9e";
	public static String sooth4 = "#a9ccbf";
	public static String sooth5 = "#d8c8af";
	public static String sooth6 = "#d7c8de";

	public static String lightwhite = "#524E4E";

	public final static String[] SoothArray = { "#71726f", "#b4b7b0",
		"#7a8c9e", "#a9ccbf", "#d8c8af", "#d7c8de", "TimetoSleep" };

	public static final int PICTURE_TAKEN_FROM_CAMERA = 1;
	public static final int PICTURE_TAKEN_FROM_GALLERY = 2;

	public static String setPictureOrColor;

	public static void displayToast(Activity context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void displayToastAct(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	//Image function
	public static String BitMapToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] arr = baos.toByteArray();
		String result = Base64.encodeToString(arr, Base64.DEFAULT);
		return result;
	}
	//Image function continued
	public static Bitmap StringToBitMap(String image) {
		try {
			byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	//Changing Time format to 12 hours
	public static String TwHourFormet(int hour, int minute) {
		String returnTime = null;
		if (hour < 12) {
			returnTime = pad(hour) + ":" + pad(minute) + " " + "am";
		} else if (hour >= 12) {
			if (hour == 12)
				returnTime = pad(hour) + ":" + pad(minute) + " " + "pm";
			else
				returnTime = pad(hour - 12) + ":" + pad(minute) + " " + "pm";
		}
		return returnTime;
	}

	// Add padding to numbers less than ten 
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
}
