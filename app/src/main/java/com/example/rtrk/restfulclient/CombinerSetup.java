package com.example.rtrk.restfulclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rtrk.restfulclient.remote.ApiUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CombinerSetup extends AppCompatActivity implements View.OnClickListener {

    Button mFinishSetup;
    Button mCancelSetup;

    CheckBox mFHDCheckBox;
    CheckBox mFHDiCheckBox;
    CheckBox mHDReadyCheckBox;
    CheckBox mWXGACheckBox;
    CheckBox mXGACheckBox;

    CheckBox mPortNum1;
    CheckBox mPortNum2;
    CheckBox mPortNum3;
    CheckBox mPortNum4;

    CheckBox mFullscreenCheckBox;
    CheckBox mSplitScreenCheckBox;
    CheckBox mUnevenSplitCheckBox;

    Intent mRESTfulCommunicationIntent;

    String ResolutionSetup;
    String PortSetup;
    String LayoutSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combiner_setup);

        mFinishSetup = (Button) findViewById(R.id.SetLayout);
        mCancelSetup = (Button) findViewById(R.id.CancelLayout);

        mFinishSetup.setOnClickListener(this);
        mCancelSetup.setOnClickListener(this);

        mFHDCheckBox = (CheckBox) findViewById(R.id.FHDCheck);
        mFHDiCheckBox = (CheckBox) findViewById(R.id.FHDiCheck);
        mHDReadyCheckBox = (CheckBox) findViewById(R.id.HDReadyCheck);
        mWXGACheckBox = (CheckBox) findViewById(R.id.WXGACheck);
        mXGACheckBox = (CheckBox) findViewById(R.id.XGACheck);

        mPortNum1 = (CheckBox) findViewById(R.id.Port1Check);
        mPortNum2 = (CheckBox) findViewById(R.id.Port2Check);
        mPortNum3 = (CheckBox) findViewById(R.id.Port3Check);
        mPortNum4 = (CheckBox) findViewById(R.id.Port4Check);

        mFullscreenCheckBox = (CheckBox) findViewById(R.id.FullscreenCheck);
        mSplitScreenCheckBox = (CheckBox) findViewById(R.id.SplitCheck);
        mUnevenSplitCheckBox = (CheckBox) findViewById(R.id.UnevenSplitCheck);

        mRESTfulCommunicationIntent = new Intent(this, CombinerSetup.class);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SetLayout :
                sendLayout(getApplicationContext().getString(R.string.combinerEvent), LayoutSetup,
                        ResolutionSetup, PortSetup);
                finishActivity(RESULT_OK);
                finish();
                break;
            case R.id.CancelLayout :
                finishActivity(RESULT_CANCELED);
                finish();
                break;

        }
    }

    public void selectResolution(View view){
        boolean buttonChecked = ((CheckBox) view).isChecked();

        switch (view.getId()){
            case  R.id.FHDCheck :
                if(buttonChecked == true){

                    ResolutionSetup = getApplicationContext().getString(R.string.FHD);
                    mFHDiCheckBox.setChecked(false);
                    mHDReadyCheckBox.setChecked(false);
                    mWXGACheckBox.setChecked(false);
                    mXGACheckBox.setChecked(false);
                }
                break;
            case R.id.FHDiCheck :
                if(buttonChecked == true){

                    ResolutionSetup = getApplicationContext().getString(R.string.FHDi);
                    mFHDCheckBox.setChecked(false);
                    mHDReadyCheckBox.setChecked(false);
                    mWXGACheckBox.setChecked(false);
                    mXGACheckBox.setChecked(false);
                }
                break;
            case R.id.HDReadyCheck :
                if(buttonChecked == true){

                    ResolutionSetup = getApplicationContext().getString(R.string.HDReady);
                    mFHDCheckBox.setChecked(false);
                    mFHDiCheckBox.setChecked(false);
                    mWXGACheckBox.setChecked(false);
                    mXGACheckBox.setChecked(false);
                }
                break;
            case R.id.WXGACheck :
                if(buttonChecked == true){

                    ResolutionSetup = getApplicationContext().getString(R.string.WXGA);
                    mFHDCheckBox.setChecked(false);
                    mFHDiCheckBox.setChecked(false);
                    mHDReadyCheckBox.setChecked(false);
                    mXGACheckBox.setChecked(false);
                }
                break;
            case R.id.XGACheck :
                if(buttonChecked == true){

                    ResolutionSetup = getApplicationContext().getString(R.string.XGA);
                    mFHDCheckBox.setChecked(false);
                    mFHDiCheckBox.setChecked(false);
                    mHDReadyCheckBox.setChecked(false);
                    mWXGACheckBox.setChecked(false);
                }
                break;
        }
    }

    public void selectLayout(View view){
        boolean buttonChecked = ((CheckBox) view).isChecked();

        switch (view.getId()){
            case R.id.FullscreenCheck :
                if(buttonChecked == true){

                    LayoutSetup = getApplicationContext().getString(R.string.fullscreen);
                    mUnevenSplitCheckBox.setChecked(false);
                    mSplitScreenCheckBox.setChecked(false);
                }
                break;
            case R.id.SplitCheck :
                if(buttonChecked == true){
                    LayoutSetup = getApplicationContext().getString(R.string.split_screen);
                    mUnevenSplitCheckBox.setChecked(false);
                    mFullscreenCheckBox.setChecked(false);
                }
                break;
            case R.id.UnevenSplitCheck :
                if(buttonChecked == true){
                    LayoutSetup = getApplicationContext().getString(R.string.uneven_split);
                    mSplitScreenCheckBox.setChecked(false);
                    mFullscreenCheckBox.setChecked(false);
                }
                break;
        }
    }

    public void selectPort(View view){
        boolean buttonChecked = ((CheckBox) view).isChecked();

        switch (view.getId()){
            case R.id.Port1Check :
                if(buttonChecked == true){

                    PortSetup = getApplicationContext().getString(R.string.PortNum1);
                    mPortNum2.setChecked(false);
                    mPortNum3.setChecked(false);
                    mPortNum4.setChecked(false);
                }
                break;
            case R.id.Port2Check :
                if(buttonChecked == true){
                    PortSetup = getApplicationContext().getString(R.string.PortNum2);
                    mPortNum1.setChecked(false);
                    mPortNum3.setChecked(false);
                    mPortNum4.setChecked(false);
                }
                break;
            case R.id.Port3Check :
                if(buttonChecked == true){
                    PortSetup = getApplicationContext().getString(R.string.PortNum3);
                    mPortNum1.setChecked(false);
                    mPortNum2.setChecked(false);
                    mPortNum4.setChecked(false);
                }
                break;
            case R.id.Port4Check :
                if(buttonChecked == true){
                    PortSetup = getApplicationContext().getString(R.string.PortNum4);
                    mPortNum1.setChecked(false);
                    mPortNum2.setChecked(false);
                    mPortNum3.setChecked(false);
                }
                break;
        }
    }

    private void sendLayout(String type, String layout, String resolution, String port)
    {
        Call<ResponseBody> mCall = ApiUtils.getUserService().sendRequest(type, layout, resolution, port);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject mJSONResponse;
                try{
                    mJSONResponse = new JSONObject(response.body().string());
                    Toast.makeText(CombinerSetup.this, getApplicationContext().
                            getString(R.string.Status) + mJSONResponse.getString("payload"),
                            Toast.LENGTH_SHORT).show();
                }
                catch (Exception exception){
                    Log.d("ROBERT", exception.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(CombinerSetup.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
