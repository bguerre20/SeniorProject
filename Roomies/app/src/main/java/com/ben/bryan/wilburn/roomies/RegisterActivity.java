package com.ben.bryan.wilburn.roomies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.Arrays;

public class RegisterActivity extends Activity {
    // Declare variables
    Button button_register;
    Button button_back;
    String string_displayname;
    String string_password;
    String string_username;
    String string_phonenumber;
    String string_gid;
    EditText edittxt_displayname;
    EditText edittxt_password;
    EditText edittxt_username;
    EditText edittxt_phonenumber;
    EditText edittxt_gid;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Locate EditTexts in activity_register.xml
        edittxt_displayname = (EditText) findViewById(R.id.edittxt_displayname);
        edittxt_password = (EditText) findViewById(R.id.edittxt_password);
        edittxt_username = (EditText) findViewById(R.id.edittxt_username);
        edittxt_phonenumber = (EditText) findViewById(R.id.edittxt_phonenumber);
        edittxt_gid = (EditText) findViewById(R.id.edittxt_gid);

        // Locate Buttons in activity_register.xml
        button_register = (Button) findViewById(R.id.button_register);
        button_back = (Button) findViewById(R.id.button_back);

        // Register Button Click Listener
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                string_password = edittxt_password.getText().toString();
                string_username = edittxt_username.getText().toString();
                string_phonenumber = edittxt_phonenumber.getText().toString();
                string_displayname = edittxt_displayname.getText().toString();
                string_gid = edittxt_gid.getText().toString();

                // Force user to fill up the form
                if (string_username.equals("") || string_password.equals("") ||
                        string_displayname.equals("") || string_phonenumber.equals("")) {
                    Toast.makeText(getApplication(), "Please complete the register form.",
                            Toast.LENGTH_LONG).show();
                } else {
                    // Creates new user
                    ParseUser user = new ParseUser();
                    user.setUsername(string_username);
                    user.setPassword(string_password);
                    user.put("displayname", string_displayname);
                    user.put("phone", string_phonenumber);
                    String ID = ParseInstallation.getCurrentInstallation().getInstallationId();
                    user.put("phoneID", ID);

                    if(string_gid.equals("")) {
                        // Create new Apartment group
                        ParseObject apartment = new ParseObject("Apartment");
                        apartment.put("email", Arrays.asList(string_username));
                        apartment.saveInBackground();
                    } else {
                        // Find and Add string_username to Apartment group
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Apartment");
                        query.getInBackground(string_gid, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject apartment, ParseException e) {
                                if (e == null) {
                                    apartment.addUnique("email", Arrays.asList(string_username));
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "GroupID does not exist, creating new Group",
                                            Toast.LENGTH_LONG).show();
                                    apartment = new ParseObject("Apartment");
                                    apartment.put("email", Arrays.asList(string_username));
                                    apartment.saveInBackground();
                                }
                            }
                        });
                    }

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a Toast message upon successful
                                // registration and send back to LoginActivity.
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up, please log in.",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign up Error, Please Try Again.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        // Back Button Click Listener
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /* Registration method
    private void register() {
        ParseUser.logInInBackground(string_username, string_password, new LogInCallback() {
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    // User exist
                    Toast.makeText(getApplicationContext(),
                            "User exist, please login",
                            Toast.LENGTH_LONG).show();
                } else {
                    // Creates new user
                    ParseUser user = new ParseUser();
                    user.setUsername(string_username);
                    user.setPassword(string_password);
                    user.put("displayname", string_displayname);
                    user.put("phone", string_phonenumber);
                    String ID = ParseInstallation.getCurrentInstallation().getInstallationId();
                    user.put("phoneID", ID);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a Toast message upon successful
                                // registration and send back to LoginActivity.
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up, please log in.",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign up Error, Please Try Again.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    } */
}