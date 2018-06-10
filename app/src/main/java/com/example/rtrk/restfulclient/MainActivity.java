package com.example.rtrk.restfulclient;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rtrk.restfulclient.model.RESTfulObject;
import com.example.rtrk.restfulclient.remote.ApiUtils;
import com.example.rtrk.restfulclient.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static int LOGOUT_RESULT = 13;

    ImageView mImageView;   //<! Top image on login screen
    EditText mUsernameText; //<! Username edit box
    EditText mPasswordText; //<! Password edit box
    Button mLoginButton;    //<! Login button on the bottom of the page
    WifiManager mWifiManager;   //<! Monitors if device is connected
    ResourcesCompat mResourcesCompat;   //<! Image resources
    Intent mRESTfulCommIntent;  //<! Used for transition to restful communication
    UserService mUserService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginButton = (Button) this.findViewById(R.id.loginButton);
        mImageView = (ImageView) this.findViewById(R.id.imageView);
        mUsernameText = (EditText) this.findViewById(R.id.usernameBox);
        mPasswordText = (EditText) this.findViewById(R.id.passwordBox);
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mUserService = ApiUtils.getUserService();
        mRESTfulCommIntent = new Intent(this, RESTfulCommunication.class);

        mImageView.setImageDrawable(mResourcesCompat.getDrawable(getResources(),
                R.drawable.splash_screen, null));
        mLoginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case  R.id.loginButton :
                /*Check if WiFi is enabled*/
                if(mWifiManager.isWifiEnabled()){
                    /*Check if device is connected to WiFi network*/
                    if(mWifiManager.getConnectionInfo().getNetworkId() != -1){
                        if(validateLoginData(mUsernameText.getText().toString(),
                                mPasswordText.getText().toString()) == true){
                            serverLogin(mUsernameText.getText().toString(),
                                    mUsernameText.getText().toString());
                        }
                    }
                }
                break;
        }
    }

    /***
     * @func validateLoginData checks if users credentials are given.
     * @param username Part of the users credential communicating with RESTful Server
     * @param password Part of the users credential communicating with RESTful Server
     * @return true if credentials given, else false
     */
    private boolean validateLoginData(String username, String password) {
        boolean returnValue = true;
        if(username == null || username.trim().length() == 0){
            returnValue = false;
            Toast.makeText(this, getApplicationContext().getResources()
                    .getText(R.string.usernameMissing), Toast.LENGTH_SHORT).show();
        }
        else if(password == null || password.trim().length() == 0 ){
            returnValue = false;
            Toast.makeText(this, getApplicationContext().getResources().
                    getText(R.string.passwordMissing), Toast.LENGTH_SHORT).show();
        }

        return returnValue;
    }

    /***
     * @func serverLogin Tries to connect to server based on given user credentials
     * @param username Part of the users credential communicating with RESTful Server
     * @param password Part of the users credential communicating with RESTful Server
     */
    private void serverLogin(String username, String password){

        Call<RESTfulObject> mCall = mUserService.login(username, password);
        mCall.enqueue(new Callback<RESTfulObject>() {
            @Override
            public void onResponse(Call<RESTfulObject> call, Response<RESTfulObject> response) {
                if(response.isSuccessful() == true){
                    RESTfulObject mRESTfulObject = response.body();
                    if(mRESTfulObject.getmRESTfulMessage() == "true"){
                        startActivityForResult(mRESTfulCommIntent, LOGOUT_RESULT);
                    }
                    else{
                        Toast.makeText(MainActivity.this, getApplicationContext()
                                        .getResources().getText(R.string.wrongCredentials),
                                        Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, getApplicationContext().getResources()
                                    .getText(R.string.tryAgain),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RESTfulObject> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}