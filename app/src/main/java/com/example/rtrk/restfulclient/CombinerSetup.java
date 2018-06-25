package com.example.rtrk.restfulclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rtrk.restfulclient.remote.ApiUtils;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CombinerSetup extends AppCompatActivity implements View.OnClickListener {

    Button mFinishSetup;
    Button mCancelSetup;

    Intent mRESTfulCommunicationIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combiner_setup);

        mFinishSetup = (Button) findViewById(R.id.finishSetup);
        mCancelSetup = (Button) findViewById(R.id.cancelSetup);

        mFinishSetup.setOnClickListener(this);
        mCancelSetup.setOnClickListener(this);

        mRESTfulCommunicationIntent = new Intent(this, CombinerSetup.class);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finishSetup :
                finishActivity(RESULT_OK);
                sendLayout("dummy", "dummy","dummy","dummy");
                finish();
                break;
            case R.id.cancelSetup :
                finishActivity(RESULT_CANCELED);
                finish();
                break;
        }
    }

    private void sendLayout(String type, String layout,  String resolution, String port)
    {
        Call<ResponseBody> mCall = ApiUtils.getUserService().sendRequest(type, layout, resolution, port);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    Log.d("ROBERT", response.body().string());
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
