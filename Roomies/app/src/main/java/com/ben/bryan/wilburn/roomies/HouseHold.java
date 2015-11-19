package com.ben.bryan.wilburn.roomies;

import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.*;

import java.util.List;

public class HouseHold extends Activity {
    String editTextstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_hold);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void JoinClick (View view) throws ParseException {
        EditText viewText = (EditText) findViewById(R.id.editText3);
        editTextstr = viewText.getText().toString();
        if (isInData(editTextstr)) {
            ParseUser user = ParseUser.getCurrentUser();
            user.put("Apartment", editTextstr);
            user.saveInBackground();
            Toast.makeText(this, "House hold joined!", Toast.LENGTH_SHORT);
            finish();
        }
        else
            Toast.makeText(this, "House hold not found!", Toast.LENGTH_SHORT);
    }

    public void CreateClick (View view) throws ParseException{
        EditText viewText = (EditText) findViewById(R.id.editText3);
        editTextstr = viewText.getText().toString();
        if (!isInData(editTextstr)) {

            ParseObject apt = new ParseObject("ApartmentID");
            apt.put("Apartment", editTextstr);
            apt.saveInBackground();
            Toast.makeText(this, "House hold created!", Toast.LENGTH_SHORT);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ParseUser user = ParseUser.getCurrentUser();
            user.put("Apartment", editTextstr);
            user.saveInBackground();

            finish();
        }
        else
            Toast.makeText(this, "House hold create failed!", Toast.LENGTH_SHORT);

    }

    private boolean isInData (String name) throws ParseException {
        ParseQuery<ParseObject> apartmentQuery = ParseQuery.getQuery("ApartmentID");
        apartmentQuery.whereEqualTo("Apartment", name);
        List<ParseObject> check = apartmentQuery.find();
        return !check.isEmpty();
    }

}
