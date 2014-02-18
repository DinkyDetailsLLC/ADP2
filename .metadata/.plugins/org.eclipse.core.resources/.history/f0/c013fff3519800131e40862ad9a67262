/*Class used to inflate Preference fragment*/
package com.dinkydetails.waketimerforkids;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class Preferences extends Fragment {

	TextView saveButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.preferences, container, false);
		saveButton = (TextView) view.findViewById(R.id.saveButton);

		// Click listener to open main activity
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), MainFragment.class));
			}
		});
		return view;
	}

}