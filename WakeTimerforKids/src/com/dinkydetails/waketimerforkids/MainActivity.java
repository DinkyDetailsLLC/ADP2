package com.dinkydetails.waketimerforkids;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Fragment {

	TextView prefButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_main, container, false);
		prefButton = (TextView) view.findViewById(R.id.prefButton);


		// Click listener to open main activity
		prefButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), PreferenceFragment.class));
			}
		});
		return view;
	}

}
