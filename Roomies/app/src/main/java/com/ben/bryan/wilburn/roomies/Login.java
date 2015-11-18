package com.ben.bryan.wilburn.roomies;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.LogInCallback;
import com.parse.ParseException;

public class Login extends Activity {
    // Declare variables
    String string_username;
    String string_password;
    EditText edittxt_username;
    EditText edittxt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Locate EditTexts in login
        edittxt_password = (EditText) findViewById(R.id.edittxt_password);
        edittxt_username = (EditText) findViewById(R.id.edittxt_username);
    }

    public void LoginClick(View view) {
        string_username = edittxt_username.getText().toString();
        string_password = edittxt_password.getText().toString();

        // Check if string_email or string_password is empty
        if(string_username.equals("") || string_password.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter a email and/or password.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            // Send data to Parse.com for verification
            ParseUser.logInInBackground(string_username, string_password,
                    new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {
                                // If user exist and authenticated, send user to
                                // MainMenu.class
                                Intent intent = new Intent(Login.this,
                                        MainMenu.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Logged in.",
                                        Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "No such user exist, please Try Again or Register.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void RegisterClick(View view) {
        // Send user to Register.class
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }
}
