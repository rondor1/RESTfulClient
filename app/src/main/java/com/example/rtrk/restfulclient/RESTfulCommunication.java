package com.example.rtrk.restfulclient;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rtrk.restfulclient.remote.ApiUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RESTfulCommunication extends AppCompatActivity implements View.OnClickListener{

    final long SLEEP_PERIOD = 2000;

    Button mPerformUpdate;    //<!Send request for CPU load
    Button mCombinerSetup;
    Intent mCombinerIntent;

    String marks = "\"";
    String openBracketString = "{";
    String closedBracketString = "}";
    String payloadString = "payload";
    Thread mDiagnosticsThread;

    /***
     * Changeable values
     */

    TextView mDiagnosticsTextView;

    JSONObject mJSONObject;

    Handler mDiagnosticsDataHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        JSONObject receivedData = (JSONObject) msg.obj;
            try{

                String DiagnosticsString = mJSONObject.getString("payload");
                DiagnosticsString = DiagnosticsString.replace(openBracketString, "");
                DiagnosticsString = DiagnosticsString.replace(closedBracketString, "");
                DiagnosticsString = DiagnosticsString.replace(payloadString, "");
                DiagnosticsString = DiagnosticsString.replace(marks, "");
                mDiagnosticsTextView.setText(DiagnosticsString);
            }
            catch (Exception exception){
                exception.printStackTrace();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restful_communication);

        mPerformUpdate = (Button) this.findViewById(R.id.performAlphaUpdate);
        mCombinerSetup = (Button) this.findViewById(R.id.setupCombiner);

        mDiagnosticsThread = new Thread(mDiagnosticsRunnable);
        mDiagnosticsThread.start();
        mCombinerIntent = new Intent(this, CombinerSetup.class);

        mDiagnosticsTextView = (TextView) findViewById(R.id.diagnosticsTextView);

        mPerformUpdate.setOnClickListener(this);
        mCombinerSetup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.performAlphaUpdate :
                performAppUpdate();
                break;
            case R.id.setupCombiner:
                startActivityForResult(mCombinerIntent, RESULT_OK);
                Log.d("ROBERT", "Clicked intent switch");
                break;
        }
    }


    private void performAppUpdate(){
        Call<ResponseBody> mCall = ApiUtils.getUserService().sendRequest(getApplicationContext()
                .getString(R.string.updaterEvent),
                getApplicationContext()
                .getString(R.string.ipAddress),getApplicationContext().getString(R.string.port),
                getApplicationContext().getString(R.string.packageName));
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        try{
                            JSONObject mJSONResponse;
                            String data = "Update temporary data";
                            try{
                                mJSONResponse = new JSONObject(response.body().string());
                                data = mJSONResponse.getString("payload");
                            }
                            catch (Exception mException){
                                mException.printStackTrace();
                            }
                            Toast.makeText(RESTfulCommunication.this, getApplicationContext().
                                            getString(R.string.updateStatus) +
                                            data
                                    , Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception exception){
                            Toast.makeText(RESTfulCommunication.this, getApplicationContext().
                                            getString(R.string.updateStatus) +
                                            exception.getMessage()
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(RESTfulCommunication.this, getApplicationContext().
                        getString(R.string.updateStatus) + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RESULT_OK && resultCode == RESULT_OK)
        {
            Log.d("ROBERT", "RETURNED WITH FINISH");
        }
        else if(requestCode == RESULT_OK && resultCode == RESULT_CANCELED)
        {
            Log.d("ROBERT", "RETURNED WITH CANCEL");
        }
    }

    Runnable mDiagnosticsRunnable = new Runnable() {
        @Override
        public void run() {
            long future = System.currentTimeMillis() + SLEEP_PERIOD;
            while(System.currentTimeMillis() < future)
            {
                Call<ResponseBody> mCall = ApiUtils.getUserService().periodicDiagnosticsRequest
                        (getApplicationContext().getString(R.string.DiagnosticsEvent));
                mCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            if(response.body() != null)
                            {
                                try{
                                    mJSONObject = new JSONObject(response.body().string());
                                    Message mDiagnosticsMessage = new Message();
                                    mDiagnosticsMessage.obj = mJSONObject;
                                    mDiagnosticsDataHandler.sendMessage(mDiagnosticsMessage);

                                }
                                catch (Exception exception){
                                    Toast.makeText(RESTfulCommunication.this, exception.
                                            getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        Toast.makeText(RESTfulCommunication.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });

                SystemClock.sleep(SLEEP_PERIOD);
                future += SLEEP_PERIOD;
            }
            }
    };

}
