package com.dinkydetails.waketimerforkids;
/*Class used to show main preference layout using fragment*/
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class PreferenceFragment extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// request to remove window title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.preference_fragments);
	}

	// Fragment manager used to replace the preference layout with fragment
	public void selectFrag(View view) {
		Fragment fr = new Preferences();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_place, fr);
		fragmentTransaction.commit();

	}
}
