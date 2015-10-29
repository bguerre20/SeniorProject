package com.ben.bryan.wilburn.roomies;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this,
                "k1dHjdoF6RirSdBbn1vlVtG23MS16dIODIHDUzAx",
                "57JGIqDoufHntyBojqi1q0jWSfvYDr0JCE70aVHt");

        // ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        ParseACL.setDefaultACL(defaultACL, true);
    }
}
