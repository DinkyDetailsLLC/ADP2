package com.dinkydetails.waketimerforkids;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dinkydetails.constants.ConstantMethodsVariables;

public class NapTimeReceiver extends BroadcastReceiver {
	//Display a toast if the system time matches the nap time setting
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("Alarm received!", "Alarm received!");
		ConstantMethodsVariables.displayToastAct(context, "Alarm received!");
//		MainActivity.bgColor.start();
	}
}
