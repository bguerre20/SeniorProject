package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import com.parse.*;

import java.util.List;

public class HouseHoldActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_hold);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void JoinClick (View view) {

    }
    public void CreateClick (View view) {

    }
    private boolean isInData (String name) throws ParseException {
        boolean answer = false;
        ParseQuery<ParseObject> apartmentQuery = ParseQuery.getQuery("Apartment");
        apartmentQuery.whereEqualTo("ApartmentID", name);
        List<ParseObject> check = apartmentQuery.find();
        return !check.isEmpty();
    }

}
