package com.dinkydetails.waketimerforkids;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextClock;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextClock tc = (TextClock) findViewById(R.id.textclock1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
