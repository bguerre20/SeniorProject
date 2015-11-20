package com.ben.bryan.wilburn.roomies;

import android.os.Bundle;
import android.app.Activity;
<<<<<<< HEAD
<<<<<<< HEAD
import android.support.annotation.NonNull;
import android.view.KeyEvent;
=======
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
>>>>>>> refs/remotes/origin/master
=======
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
>>>>>>> refs/remotes/origin/master
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class  Financial extends Activity implements OnItemSelectedListener {

    private String currentUser = "YOU"; // default text in case of error
    private List<ParseUser> userList; // list of all the users (parse user obj)
                                      // in current user's apartment
    private List<Double> userBalance; // list of what each user has payed into the apartment
    private List<String> apartmentNames; // names of all members of the current users apartment
    private List<String> financialData; //list items of financial data

    private int selectedIndex = 0;

    private double apartmentTotal;
    private String description;
    //list of payments for each user in same order as userList
    private List<Double> paymentList;

    TextView currentUserLabel;
    TextView individualBalanceLabel;
    TextView apartmentBalanceLabel;
    Spinner individualBalanceSpinner;
    ListView financialListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apartmentNames = new ArrayList<String>();
        userList = new ArrayList<ParseUser>();
        financialData = new ArrayList<String>();
        //get UI elements

        currentUser = ParseUser.getCurrentUser().getString("displayname");
        apartmentNames = new ArrayList<String>();
        userBalance = new ArrayList<Double>();
        apartmentTotal = 0;
        getUserList();

        setContentView(R.layout.financial);

        currentUserLabel = (TextView) findViewById(R.id.selfTextView);
        individualBalanceLabel = (TextView) findViewById(R.id.balanceTextView);
        apartmentBalanceLabel = (TextView) findViewById(R.id.aptBalanceTextView);
        financialListView = (ListView) findViewById(R.id.financialListView);

        apartmentNames.add("default");

        getAllFinancialData();

        //adp.addAll(apartmentNames);
        updateUI();
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        selectedIndex = pos;
        updateUI();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void UpdateClick(View view)
    {
        updateUI();
    }

    private void updateUI(){

        if (userList.size() > 0) {

            ArrayAdapter<String> adp = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,apartmentNames.toArray(new String[apartmentNames.size()]));
            individualBalanceSpinner = (Spinner) findViewById(R.id.spinner1);
            // Apply the adapter to the spinner
            individualBalanceSpinner.setAdapter(adp);

            individualBalanceSpinner.setOnItemSelectedListener(this);

            currentUserLabel.setText(currentUser);
            int currentInd = apartmentNames.indexOf(currentUser);
            double fairOwe = apartmentTotal / userList.size();
            double currentApartmentBalance = userBalance.get(currentInd) - fairOwe;
            apartmentBalanceLabel.setText(String.format("Apartment Balance: $%.2f", currentApartmentBalance));

            individualBalanceSpinner.setSelection(selectedIndex);
            //calculate total overages
            double totalOverage = 0;
            for (int i = 0; i < userList.size(); i++) {
                double tempAptBalance = userBalance.get(i) - fairOwe;
                if (tempAptBalance > 0)
                    totalOverage += tempAptBalance;
            }

            if (userBalance.get(currentInd) >= fairOwe && userBalance.get(selectedIndex) >= fairOwe) {
                individualBalanceLabel.setText("$0");
            }
            else if (userBalance.get(currentInd) <= fairOwe && userBalance.get(selectedIndex) <= fairOwe) {
                individualBalanceLabel.setText("$0");
            }
            else if (userBalance.get(currentInd) > fairOwe && userBalance.get(selectedIndex) < fairOwe) {
                double balanceToDisplay = (currentApartmentBalance / totalOverage) * (fairOwe - userBalance.get(selectedIndex));

                individualBalanceLabel.setText(String.format("owes you $%.2f ", balanceToDisplay));
            }
            else if (userBalance.get(currentInd) < fairOwe && userBalance.get(selectedIndex) > fairOwe) {
                double balanceToDisplay = Math.abs(currentApartmentBalance) * ((userBalance.get(selectedIndex) - fairOwe) / totalOverage);

                individualBalanceLabel.setText(String.format("you owe $%.2f", balanceToDisplay));
            }

            if (financialData.size() > 0) {
                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, financialData.toArray(new String[financialData.size()]));
                financialListView = (ListView) findViewById(R.id.financialListView);
                // Apply the adapter to the spinner
                financialListView.setAdapter(adp2);
            }


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

                apartmentTotal += paymentList.get(counter);
            }
            counter++;
        }


        final ParseQuery<ParseObject> apartQuery = ParseQuery.getQuery("ApartmentID");
        apartQuery.whereEqualTo("Apartment", ParseUser.getCurrentUser().getString("Apartment"));
        apartQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    objects.get(0).put("ApartmentBalance",(objects.get(0).getDouble("ApartmentBalance") + apartmentTotal));
                    objects.get(0).saveInBackground();
                } else {
                    // Something went wrong.
                }
            }});
    }

    private void getAllFinancialData() {

        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> FinQuery = ParseQuery.getQuery("Financial");
        FinQuery.whereEqualTo("Apartment", user.getString("Apartment"));
        FinQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    financialData.clear();
                    for(int i = 0; i < objects.size();i++)
                    {
                        financialData.add(objects.get(i).get("payment") + ", " + objects.get(i).getString("User") + ", " + objects.get(i).getString("Discription"));
                    }

                }
                else {
                    //something went wrong
                }

            }
        });
    }


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
                                        objects.get(0).put("ApartmentBalance", (objects.get(0).getDouble("ApartmentBalance") - value));
                                        objects.get(0).saveInBackground();
                                    } else {
                                        // Something went wrong.
                                    }
                                }
                            });
                            object.deleteInBackground();
                        } else {
                            // Something went wrong.
                        }
                    }
                });
            }
<<<<<<< HEAD

<<<<<<< HEAD
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
=======
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

=======

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

>>>>>>> refs/remotes/origin/master
                ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                userQuery.whereEqualTo("Apartment", ParseUser.getCurrentUser().getString("Apartment"));
                userQuery.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {
                            // The query was successful.
                            userList = objects;
                            apartmentNames.clear();
                            for (int i = 0; i < objects.size(); i++) {
                                double payedIntoApt = userList.get(i).getDouble("UserBalance");

                                userBalance.add(i, payedIntoApt);
                                apartmentTotal += payedIntoApt;
                                apartmentNames.add(i, userList.get(i).getString("displayname"));
                            }
                        } else {
                            // Something went wrong.
                        }
                    }
                });
            }


>>>>>>> refs/remotes/origin/master
}
