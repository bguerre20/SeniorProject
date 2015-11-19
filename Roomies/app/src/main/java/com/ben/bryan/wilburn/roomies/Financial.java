package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
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

public class Financial extends Activity {

    private double apartmentBalance;
    private String description;
    private List<ParseUser> userList;
    //list of payments for each user in same order as userList
    private List<Double> paymentList;
    private List<Double> userBalance;
    private List<String> apartmentNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial);
        apartmentNames = new ArrayList<String>();
        userBalance = new ArrayList<Double>();
        apartmentBalance = 0;
        getUserList();
        //sleep here of before you fill data
        try {
            Thread.sleep(500,0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addFinancial() {
        //update paymentList for all members
        int counter = 0;
        for (ParseUser u : userList) {
            if (paymentList.get(counter) > 0) {
                ParseUser user = u;
                ParseObject financial = new ParseObject("Financial");
                financial.put("Value", paymentList.get(counter));
                financial.put("Description", description);
                financial.put("User", user.get("displayname"));
                financial.put("Apartment", user.get("Apartment"));
                financial.saveInBackground();

                apartmentBalance += paymentList.get(counter);
            }
            counter++;
        }


        final ParseQuery<ParseObject> apartQuery = ParseQuery.getQuery("ApartmentID");
        apartQuery.whereEqualTo("Apartment", ParseUser.getCurrentUser().getString("Apartment"));
        apartQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    objects.get(0).put("ApartmentBalance",(objects.get(0).getDouble("ApartmentBalance") + apartmentBalance));
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

    private void getUserList() {
        userList.clear();
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("Apartment", ParseUser.getCurrentUser().getString("Apartment"));
        userQuery.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    userList = objects;
                    for (int i = 0; i < objects.size(); i++) {
                        userBalance.add(i, userList.get(i).getDouble("payment"));
                        apartmentBalance += userList.get(i).getDouble("payment");
                        apartmentNames.add(i, userList.get(i).getString("user"));
                    }
                } else {
                    // Something went wrong.
                }
            }
        });
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
