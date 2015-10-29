package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;

public class HouseHoldActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_hold);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
