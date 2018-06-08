package com.example.rtrk.restfulclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RESTfulCommunication extends AppCompatActivity implements View.OnClickListener {

    Button mClearButton;    //<!Currently, no function, just return to previous
                            //<! intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restful_communication);

        mClearButton = (Button) this.findViewById(R.id.clear);
        mClearButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Intent mMainIntent = new Intent(this, MainActivity.class);
        switch (view.getId()){
            case R.id.clear :
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
