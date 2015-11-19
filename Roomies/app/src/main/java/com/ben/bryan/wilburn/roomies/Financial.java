package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class Financial extends Activity {

    private String payment;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial);
    }

    private void addFinancial() {
        //EditText viewText = (EditText) findViewById(R.id.editText);
        //name = viewText.getText().toString();

       //viewText = (EditText) findViewById(R.id.editText2);
        //description = viewText.getText().toString();

        //viewText = (EditText) findViewById(R.id.editText4);
        //date = viewText.getText().toString();

        ParseUser user = ParseUser.getCurrentUser();
        ParseObject financial = new ParseObject("Financial");
        financial.put("Value", payment);
        financial.put("Description", description);
        financial.put("User", user.get("displayname"));
        financial.put("Apartment", user.get("Apartment"));

        chore.saveInBackground();
        finish();
    }



}
