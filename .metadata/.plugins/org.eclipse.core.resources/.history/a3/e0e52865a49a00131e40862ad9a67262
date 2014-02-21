package com.dinkydetails.waketimerforkids;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dinkydetails.constants.ConstantMethodsVariables;
import com.dinkydetails.constants.PreferenceData;

public class WakeUpTimeReceiver extends BroadcastReceiver {
	//Display a toast if the system time matches the Wake time setting
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("Alarm received!", "Alarm received!");
		ConstantMethodsVariables.displayToastAct(context, "Alarm received!");
		PreferenceData.setStringValues(context, "WakeUpOrSleep", "WakeUp");
	}
}
