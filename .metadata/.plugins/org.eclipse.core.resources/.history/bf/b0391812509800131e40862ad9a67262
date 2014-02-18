/*Class used to show main activity layout using fragment*/

package com.dinkydetails.waketimerforkids;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainFragment extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// request to remove window title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_fragment);
	}
	
	// Fragment manager used to replace the main activity layout with fragment
	public void selectFrag(View view) {
		 Fragment fr = new MainActivity();
		 FragmentManager fm = getFragmentManager();
	     FragmentTransaction fragmentTransaction = fm.beginTransaction();
	     fragmentTransaction.replace(R.id.fragment_main_activity, fr);
	     fragmentTransaction.commit();
		 
	}

}
