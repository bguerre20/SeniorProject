package com.ben.bryan.wilburn.roomies;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

public class LoginTest extends ActivityInstrumentationTestCase2<Login> {
    // Variables
    private Login mLogin;
    private EditText mUsername;
    private EditText mPassword;

    @SuppressWarnings("deprecation")
    public LoginTest() {
        super("com.ben.bryan.wilburn.roomies.Login", Login.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLogin = this.getActivity();
    }

    public void testPreconditions() {
        assertNotNull(mLogin);
        assertNotNull(mUsername);
        assertNotNull(mPassword);
    }

    public void testUsernameEdittext() {
        mUsername = (EditText) mLogin.findViewById(R.id.edittxt_username);

        mLogin.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUsername.setText("test123");
            }
        });

        assertEquals("test123", mUsername.getText());
    }

    public void testPasswordEdittext() {
        mPassword = (EditText) mLogin.findViewById(R.id.edittxt_password);

        mLogin.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPassword.setText("test123");
            }
        });

        assertEquals("test123", mPassword.getText());
    }
}
