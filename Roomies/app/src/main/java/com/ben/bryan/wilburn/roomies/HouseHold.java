package com.ben.bryan.wilburn.roomies;

import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.*;

import java.util.ArrayList;
import java.util.List;

public class HouseHold extends Activity {
    String editTextstr;
    List<ParseObject>check;
    List<String>listItems = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_hold);

        final ParseQuery<ParseObject> boardQuery = ParseQuery.getQuery("ApartmentID");
        boardQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for (int i = 0; i < objects.size(); i++) {
                        listItems.add(objects.get(i).getString("Apartment"));
                    }
                } else {
                    // Something went wrong.
                }
            }
        });

    }

    public void JoinClick (View view) throws ParseException {
        EditText viewText = (EditText) findViewById(R.id.editText3);
        editTextstr = viewText.getText().toString();
        if (!isInData(editTextstr)) {
            ParseUser user = ParseUser.getCurrentUser();
            user.put("Apartment", editTextstr);
            user.saveInBackground();
            Toast.makeText(this, "House hold joined!", Toast.LENGTH_SHORT);
            finish();
        }
        else
            Toast.makeText(this, "House hold not found!", Toast.LENGTH_SHORT).show();
    }

    public void CreateClick (View view) throws ParseException{
        EditText viewText = (EditText) findViewById(R.id.editText3);
        editTextstr = viewText.getText().toString();
        if (isInData(editTextstr)) {

            ParseObject apt = new ParseObject("ApartmentID");
            apt.put("Apartment", editTextstr);
            apt.saveInBackground();
            Toast.makeText(this, "House hold created!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "House hold create failed!", Toast.LENGTH_SHORT).show();

    }

    private boolean isInData (String name) throws ParseException {
        return (listItems.contains(name));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            return true; // you missed this line
        }
        return super.onKeyDown(keyCode, event);
    }
}
