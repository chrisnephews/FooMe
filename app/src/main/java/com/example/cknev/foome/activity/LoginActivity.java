package com.example.cknev.foome.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cknev.foome.R;


public class LoginActivity extends AppCompatActivity
{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initializeComponents();
        _loginButton.setOnClickListener(new View.OnClickListener()
        {//Do login

            @Override
            public void onClick(View v)
            {
                login();
            }
        });
        _signupLink.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }



    private void initializeComponents()
    {
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);
    }

    public void login()
    {
        Log.d(TAG, "Trying to login");

        if (!validate())
        {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressBar progressBar = new ProgressBar(LoginActivity.this,
                null, android.R.attr.progressBarStyleSmall);
        progressBar.setIndeterminate(true);
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable()
                {
                    public void run()
                    {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressBar.setVisibility(View.GONE);
                    }
                }, 1000);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_SIGNUP)
        {
            if (resultCode == RESULT_OK)
            {
                setContentView(R.layout.activity_main);
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        // disable going back
        moveTaskToBack(true);
    }

    public void onLoginSuccess()
    {
        _loginButton.setEnabled(true);
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(mainIntent);
    }

    public void onLoginFailed()
    {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate()
    {
        boolean valid = true;
        return valid;/*
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;*/
    }
}

