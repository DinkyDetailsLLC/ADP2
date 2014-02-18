package com.dinkydetails.waketimerforkids;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.dinkydetails.constants.ConstantMethodsVariables;
import com.dinkydetails.constants.PreferenceData;

public class NightTimeSoothReceiver extends BroadcastReceiver {
	//Display a toast if the system time matches the Night time setting
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("Alarm received!", "Alarm received!");
		ConstantMethodsVariables.displayToastAct(context, "Alarm received!");
		ConstantMethodsVariables.setPictureOrColor = "TimeToSleep";
		Log.d("Alarm setPictureOrColor!",
				ConstantMethodsVariables.setPictureOrColor);
		PreferenceData.setStringValues(context, "WakeUpOrSleep", "TimeToSleep");
	}
}