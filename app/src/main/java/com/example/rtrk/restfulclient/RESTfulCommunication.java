package com.example.rtrk.restfulclient;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rtrk.restfulclient.remote.ApiUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RESTfulCommunication extends AppCompatActivity implements View.OnClickListener{

    Button mCPUStatus;    //<!Send request for CPU load
    Button mRAMStatus;  //<! Send request for RAM Utilization
    Button mStorageStatus;  //<! Send request for Storage usage

    TextView mTextView;
    JSONObject mJSONObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restful_communication);

        mCPUStatus = (Button) this.findViewById(R.id.requestCPU);
        mRAMStatus = (Button) this.findViewById(R.id.requestRAM);
        mStorageStatus = (Button) this.findViewById(R.id.requestStorage);

        mTextView = (TextView) this.findViewById(R.id.previewText);

        mCPUStatus.setOnClickListener(this);
        mRAMStatus.setOnClickListener(this);
        mStorageStatus.setOnClickListener(this);

        mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mTextView.clearComposingText();
                if(mTextView.getText().equals("")){
                    return true;
                }
                else{
                    return false;
                }
            }
        });
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.requestCPU :
                getCPU(getApplicationContext().getResources().getText(R.string.CPU).toString(),
                        getApplicationContext().getResources().getText(R.string.load).toString());
                break;
            case R.id.requestRAM :
                getRAM(getApplicationContext().getResources().getText(R.string.RAM).toString(),
                        getApplicationContext().getResources().getText(R.string.load).toString());
                break;
            case R.id.requestStorage :
                getStorageStatus(getApplicationContext().getResources().getText(R.string.Storage).toString(),
                        getApplicationContext().getResources().getText(R.string.Status).toString());
                break;
        }
    }

    private void getCPU(String type, String data)
    {
        Call<ResponseBody> mCall = ApiUtils.getUserService().acquireData(type, data);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() == true){
                    if(response.body() != null)
                    {
                        try{
                            mJSONObject = new JSONObject(response.body().string());
                            mTextView.setText(mJSONObject.getString("data"));
                        }
                        catch (Exception exception){
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    private void getRAM(String type, String data){
        Call<ResponseBody> mCall = ApiUtils.getUserService().acquireData(type,data);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() == true ){
                    if(response.body() != null){
                        try{
                            mJSONObject = new JSONObject(response.body().string());
                            mTextView.setText(mJSONObject.getString("data"));
                        }
                        catch (Exception mException){

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    private void getStorageStatus(String type, String data){
        Call<ResponseBody> mCall = ApiUtils.getUserService().acquireData(type, data);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() == true){
                    if(response.body() != null){
                        try{
                            mJSONObject = new JSONObject(response.body().string());
                            mTextView.setText(mJSONObject.getString("data"));
                        }
                        catch (Exception expc){

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }
}

