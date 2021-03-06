package com.ben.bryan.wilburn.roomies;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.ParseUser;
import com.parse.LogInCallback;
import com.parse.ParseException;

import com.facebook.FacebookSdk;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends Activity {
    // Declare variables
    String string_username;
    String string_password;
    EditText edittxt_username;
    EditText edittxt_password;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.login);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"Facebook Login Success!.",Toast.LENGTH_LONG).show();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,GraphResponse response) {
                                try {
                                    string_username = object.getString("email");
                                    edittxt_username.setText(string_username);

                                }
                                catch(JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Facebook Login Cancelled.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(),"Facebook Login Failed.",Toast.LENGTH_LONG).show();
            }
        });

        // Locate EditTexts in login
        edittxt_password = (EditText) findViewById(R.id.edittxt_password);
        edittxt_username = (EditText) findViewById(R.id.edittxt_username);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
                executeLogin(string_username, string_password);
        }
    }

    public void RegisterClick(View view) {
        // Send user to Register.class
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
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

    public void executeLogin(String username, String password) {
        // Send data to Parse.com for verification

        ParseUser.logInInBackground(username, password,
                new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // If user exist and authenticated, send user to
                            // MainMenu.class
                            Intent intent = new Intent(Login.this, MainMenu.class);

                            intent.putExtra("extra message", "message");
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),
                                    "Successfully Logged in.",
                                    Toast.LENGTH_LONG).show();

                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "No such user exist, please Try Again or Register.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


}





