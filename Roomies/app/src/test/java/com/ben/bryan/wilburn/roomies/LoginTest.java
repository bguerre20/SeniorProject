package com.ben.bryan.wilburn.roomies;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;

public class LoginTest extends ActivityInstrumentationTestCase2<Login> {
    // Variables
    private Login mLogin;
    private EditText mUsername;
    private EditText mPassword;
    private Button mLog;
    private Button mRegister;

    @SuppressWarnings("deprecation")
    public LoginTest() {
        super("com.ben.bryan.wilburn.roomies.Login", Login.class);
    }

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        mLogin = this.getActivity();
    }

    @SmallTest
    public void testPreconditions() {
        assertNotNull(mLogin);
        assertNotNull(mUsername);
        assertNotNull(mPassword);
        assertNotNull(mLog);
        assertNotNull(mRegister);
    }

    @MediumTest
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

    @MediumTest
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

    @MediumTest
    public void testLoginButton_layout() {
        mLog = (Button) mLogin.findViewById(R.id.button_login);

        final View decorView = mLogin.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mLog);

        final ViewGroup.LayoutParams layoutParams = mLog.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @MediumTest
    public void testRegisterButton_layout() {
        mRegister = (Button) mLogin.findViewById(R.id.button_register);

        final View decorView = mLogin.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mRegister);

        final ViewGroup.LayoutParams layoutParams = mRegister.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
