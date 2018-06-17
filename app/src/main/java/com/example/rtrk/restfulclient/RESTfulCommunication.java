package com.example.rtrk.restfulclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rtrk.restfulclient.remote.ApiUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RESTfulCommunication extends AppCompatActivity implements View.OnClickListener{

    Button mCPUStatus;    //<!Send request for CPU load
    Button mRAMStatus;  //<! Send request for RAM Utilization

    ImageView mCPUImageView;
    ImageView mRAMImageView;

    /***
     * Static text
     */
    TextView mCpuLoad;
    TextView mCurrentCpuLoad;
    TextView mTotalRam;
    TextView mCurrentRamUtil;

    /***
     * Changeable values
     */
    TextView mCpuLoadVal;
    TextView mCurrentCpuLoadVal;
    TextView mVmemUtil;
    TextView mPhysicalRAMUtil;

    JSONObject mJSONObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restful_communication);

        mCPUStatus = (Button) this.findViewById(R.id.requestCPU);
        mRAMStatus = (Button) this.findViewById(R.id.requestRAM);

        mCPUImageView = (ImageView) this.findViewById(R.id.cpuIcon);
        mCPUImageView.getLayoutParams().height = 250;
        mCPUImageView.getLayoutParams().width = 250;
        mCPUImageView.setImageResource(R.drawable.cpu);


        mRAMImageView = (ImageView) this.findViewById(R.id.ramIcon);
        mRAMImageView.getLayoutParams().height = 250;
        mRAMImageView.getLayoutParams().width = 250;
        mRAMImageView.setImageResource(R.drawable.ram);

        mCpuLoadVal = (TextView) this.findViewById(R.id.cpuStats);
        mCurrentCpuLoadVal = (TextView) this.findViewById(R.id.currentProcCPUStats);
        mVmemUtil = (TextView) this.findViewById(R.id.ramStats);
        mPhysicalRAMUtil = (TextView) this.findViewById(R.id.currentProcRAMStats);



        mCPUStatus.setOnClickListener(this);
        mRAMStatus.setOnClickListener(this);

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
                            mCpuLoadVal.setText(mJSONObject.getString("total_cpu_util")+"%");
                            mCurrentCpuLoadVal.setText(mJSONObject.getString("current_proc_cpu_util")+"%");
                        }
                        catch (Exception exception){
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(RESTfulCommunication.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
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
                            mPhysicalRAMUtil.setText(mJSONObject.getString("vmem_util")+
                                    getApplicationContext().getResources().getText(R.string.UnitName));
                            mVmemUtil.setText(mJSONObject.getString("current_ram_util")+
                                    getApplicationContext().getResources().getText(R.string.UnitName));
                        }
                        catch (Exception mException){

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(RESTfulCommunication.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
