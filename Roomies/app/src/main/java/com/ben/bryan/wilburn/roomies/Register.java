package com.ben.bryan.wilburn.roomies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Arrays;

public class Register extends Activity {
    // Declare variables
    String string_displayname;
    String string_password;
    String string_username;
    String string_phonenumber;
    EditText edittxt_displayname;
    EditText edittxt_password;
    EditText edittxt_username;
    EditText edittxt_phonenumber;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Locate EditTexts in register.xml
        edittxt_displayname = (EditText) findViewById(R.id.edittxt_displayname);
        edittxt_password = (EditText) findViewById(R.id.edittxt_password);
        edittxt_username = (EditText) findViewById(R.id.edittxt_username);
        edittxt_phonenumber = (EditText) findViewById(R.id.edittxt_phonenumber);

    }

    public void FinalRegisterClick(View view) {
        // Retrieve the text entered from the EditText
        string_password = edittxt_password.getText().toString();
        string_username = edittxt_username.getText().toString();
        string_phonenumber = edittxt_phonenumber.getText().toString();
        string_displayname = edittxt_displayname.getText().toString();

        // Force user to fill up the form
        if (string_username.equals("") || string_password.equals("") || string_displayname.equals("") || string_phonenumber.equals("")) {
            Toast.makeText(getApplication(), "Please complete the register form.",Toast.LENGTH_LONG).show();
        }
        else {
            // Creates new user
            ParseUser user = new ParseUser();
            user.setUsername(string_username);
            user.setPassword(string_password);
            user.put("displayname", string_displayname);
            user.put("phone", string_phonenumber);
            String ID = ParseInstallation.getCurrentInstallation().getInstallationId();
            user.put("phoneID", ID);
                    /* Create new ParseObject Apartment
                    ParseObject apartment = new ParseObject("Apartment");
                    apartment.addUnique("email", Arrays.asList(string_username));
                    apartment.saveInBackground(); */

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        // Log in the user
                        ParseUser.logInInBackground(string_username, string_password,
                                new LogInCallback() {
                                    @Override
                                    public void done(ParseUser user, ParseException e) {
                                        if (user != null) {

                                            Toast.makeText(getApplicationContext(),
                                                    "Successfully Signed up and logged in.",
                                                    Toast.LENGTH_LONG).show();

                                            Intent intent2 = new Intent(Register.this, HouseHold.class);
                                            String message = "Hello 2nd activity!";
                                            intent2.putExtra("message", message);
                                            startActivity(intent2);


                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),
                                                    "Login Failed.",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                "Sign up Error, Please Try Again.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

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