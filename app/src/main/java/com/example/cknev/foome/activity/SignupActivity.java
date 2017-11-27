package com.example.cknev.foome.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.cknev.foome.R;
import com.example.cknev.foome.model.User;
import com.example.cknev.foome.utitility.DataSource;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity
{
    CallbackManager _callbackManager;
    LoginButton _loginButton;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Sign up");
        _callbackManager = CallbackManager.Factory.create();
        _loginButton = (LoginButton) findViewById(R.id.login_button);
        _loginButton.setReadPermissions("email");


        //Register callback with the loginbutton. On successful login,login result has new access token and recently granted permissions.
        _loginButton.registerCallback(_callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                //Get the user details when login is a succes
                getUserDetails(loginResult);
                //

            }

            @Override
            public void onCancel()
            {

            }

            @Override
            public void onError(FacebookException error)
            {

            }
        });

        accessTokenTracker = new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken)
            {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
    }

    /**
     * Method for getting the user info from facebook
     *
     * */
    protected void getUserDetails(LoginResult loginResult)
    {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject json_object, GraphResponse response)
                    {
                        Intent intent = new Intent(SignupActivity.this,
                                MainActivity.class);
                        intent.putExtra("userProfile", json_object.toString());
                        try
                        {
                            //Create a user from the JSON object received from facebook with a temp password and date of birth;
                            User user = new User(-1, "temp", json_object.getString("name"), json_object.getString("email"), "00/00/0000");
                            DataSource dataSource = new DataSource(SignupActivity.this);
                            dataSource.saveUser(user);
                        } catch (Exception e)
                        {
                            Log.e("Exception", "e: " + e);
                            //If an error occurs the user is send back to login activity
                            finish();
                        }
                        Log.i("fb", json_object.toString());
                        startActivity(intent);
                    }
                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //Pass the result to the callbackmanager
        _callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

}
