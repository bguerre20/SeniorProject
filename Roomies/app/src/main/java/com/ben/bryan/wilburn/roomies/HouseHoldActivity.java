package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.parse.*;

import java.util.List;

public class HouseHoldActivity extends Activity {
    String editTextstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_hold);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void JoinClick (View view) throws ParseException {
        EditText viewText = (EditText) findViewById(R.id.editText3);
        editTextstr = viewText.getText().toString();
        if (isInData(editTextstr)) {
            ParseUser user = ParseUser.getCurrentUser();
            user.put("Apartment", editTextstr);
        }
    }
    public void CreateClick (View view) throws ParseException{
        EditText viewText = (EditText) findViewById(R.id.editText3);
        editTextstr = viewText.getText().toString();
        if (!isInData(editTextstr)) {
            ParseUser user = ParseUser.getCurrentUser();
            user.put("Apartment",editTextstr);
        }

    }
    private boolean isInData (String name) throws ParseException {
        ParseQuery<ParseObject> apartmentQuery = ParseQuery.getQuery("Apartment");
        apartmentQuery.whereEqualTo("ApartmentID", name);
        List<ParseObject> check = apartmentQuery.find();
        return !check.isEmpty();
    }

}
