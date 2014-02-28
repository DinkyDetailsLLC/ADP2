package com.dinkydetails.waketimerforkids;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dinkydetails.constants.ConstantMethodsVariables;
import com.dinkydetails.constants.PreferenceData;
import com.dinkydetails.daydream.DayDreamActivity;

public class NapTimeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("Alarm received!", "Alarm received!");
		ConstantMethodsVariables
				.displayToastAct(context, "Nap Alarm received!");
		PreferenceData.setBooleanValues(context, "RunNapTimethread", true);
		PreferenceData.setStringValues(context, "WakeUpOrSleep", "SetNapTime");
		Intent i = new Intent(context, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(i);
		
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("com.dinkydetails.daydream.DayDreamActivity");
		context.startService(serviceIntent);

	}
}
