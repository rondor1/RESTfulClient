package com.example.rtrk.restfulclient;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mImageView;   //<! Top image on login screen
    EditText mUsernameText; //<! Username edit box
    EditText mPasswordText; //<! Password edit box
    Button mLoginButton;    //<! Login button on the bottom of the page
    WifiManager mWifiManager;   //<! Monitors if device is connected
    ResourcesCompat mResourcesCompat;   //<! Image resources
    Intent mRESTfulCommIntent;  //<! Used for transition to restful communicationo
    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginButton = (Button) this.findViewById(R.id.loginButton);
        mImageView = (ImageView) this.findViewById(R.id.imageView);
        mUsernameText = (EditText) this.findViewById(R.id.usernameBox);
        mPasswordText = (EditText) this.findViewById(R.id.passwordBox);
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mImageView.setImageDrawable(mResourcesCompat.getDrawable(getResources(),
                R.drawable.splash_screen, null));
        mLoginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case  R.id.loginButton :
                mRESTfulCommIntent = new Intent(this, RESTfulCommunication.class);
                mBundle = new Bundle();
                /*Check if WiFi is enabled*/
                if(mWifiManager.isWifiEnabled()){
                    /*Check if device is connected to WiFi network*/
                    if(mWifiManager.getConnectionInfo().getNetworkId() != -1){
                        if(mUsernameText.getText().toString().isEmpty() ||
                                mPasswordText.getText().toString().isEmpty()){
                            Toast.makeText(this, "Username or Password missing!",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            mBundle.putString(getResources().getString(R.string.username),
                                    mUsernameText.getText().toString());
                            mBundle.putString(getResources().getString(R.string.password),
                                    mPasswordText.getText().toString());
                            if(login(mBundle)) {
                                startActivityForResult(mRESTfulCommIntent, RESULT_OK);
                            }
                        }
                    }
                }
                break;
        }
    }

    private boolean login(Bundle mBundle){
        boolean returnValue = true;
        String mUsername = mBundle.getString(getResources().getString(R.string.username));
        String mPassword = mBundle.getString(getResources().getString(R.string.password));


        return returnValue;
    }
}