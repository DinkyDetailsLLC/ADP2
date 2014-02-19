package com.dinkydetails.waketimerforkids;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.sax.StartElementListener;
import android.util.Log;

import com.dinkydetails.constants.ConstantMethodsVariables;
import com.dinkydetails.constants.PreferenceData;

public class NightTimeSoothReceiver extends BroadcastReceiver {
	
	MainFragment updateFragment = new MainFragment();
	
	//Display a toast if the system time matches the Night time setting
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("Alarm received!", "Alarm received!");
		ConstantMethodsVariables.displayToastAct(context, "Alarm received!");
		PreferenceData.setStringValues(context, "WakeUpOrSleep", "SoothTime");
		PreferenceData.setBooleanValues(context, "RunNapTimethread", false);
		Intent i = new Intent(context, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(i);
	}
}