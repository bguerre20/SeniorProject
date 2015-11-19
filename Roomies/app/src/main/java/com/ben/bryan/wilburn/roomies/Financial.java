package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class  Financial extends Activity {

    private double payment;
    private String description;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial);
        list = new ArrayList<String>();
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

        financial.saveInBackground();
        finish();

        final ParseQuery<ParseObject> apartQuery = ParseQuery.getQuery("ApartmentID");
        apartQuery.whereEqualTo("Apartment", user.get("Apartment"));
        apartQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    objects.get(0).put("ApartmentBalance",(objects.get(0).getDouble("ApartmentBalance") + payment));
                    objects.get(0).saveInBackground();
                } else {
                    // Something went wrong.
                }
            }});
    }

   // private void updateFinancial() {

    //}

    private void deleteFinancial(String objectID) {
        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> FinQuery = ParseQuery.getQuery("Financial");
        FinQuery.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    final double value = object.getDouble("payment");
                    //fix apartment balance
                    ParseUser user = ParseUser.getCurrentUser();
                    final ParseQuery<ParseObject> apartQuery = ParseQuery.getQuery("ApartmentID");
                    apartQuery.whereEqualTo("Apartment", user.get("Apartment"));
                    apartQuery.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                // The query was successful.
                                objects.get(0).put("ApartmentBalance",(objects.get(0).getDouble("ApartmentBalance") - value));
                                objects.get(0).saveInBackground();
                            } else {
                                // Something went wrong.
                            }
                        }});
                    object.deleteInBackground();
                } else {
                    // Something went wrong.
                }
            }});
    }
    private void deleteFromUserBalance(final String email, final double value) {
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("email", email);
        userQuery.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    objects.get(0).put("UserBalance", (objects.get(0).getDouble("UserBalance") - value));
                    objects.get(0).saveInBackground();
                } else {
                    // Something went wrong.
                }
            }
        });
    }

    private List<String> getUserList() {
        list.clear();
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for (int i = 0; i < objects.size(); i++) {
                        list.add(objects.get(i).get("username").toString());
                    }
                } else {
                    // Something went wrong.
                }
            }});
        return list;
    }



}
