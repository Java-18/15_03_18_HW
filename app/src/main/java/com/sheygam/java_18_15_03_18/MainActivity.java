package com.sheygam.java_18_15_03_18;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int START = 0x01;
    public static final int END = 0x02;
    public static final int TOTAL_COUNT = 0x03;
    public static final int CURRENT_FILE = 0x04;
    public static final int CURRENT_PROGRESS = 0x05;

    private TextView totalCountTxt, counterTxt, resultTxt;
    private Button startBtn;
    private ProgressBar myProgress, horProgress;
    private LinearLayout wrapper;
    private Handler handler;
    private int currentCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalCountTxt = findViewById(R.id.total_count_txt);
        counterTxt = findViewById(R.id.counter_txt);
        resultTxt = findViewById(R.id.result_txt);
        startBtn = findViewById(R.id.start_btn);
        myProgress = findViewById(R.id.my_progress);
        horProgress = findViewById(R.id.hor_progress);
        wrapper = findViewById(R.id.download_wrapper);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what){
                    case START:
                        resultTxt.setVisibility(View.INVISIBLE);
                        myProgress.setVisibility(View.VISIBLE);
                        wrapper.setVisibility(View.VISIBLE);
                        horProgress.setVisibility(View.VISIBLE);
                        horProgress.setProgress(0);
                        startBtn.setEnabled(false);
                        break;
                    case END:
                        resultTxt.setVisibility(View.VISIBLE);
                        myProgress.setVisibility(View.INVISIBLE);
                        wrapper.setVisibility(View.INVISIBLE);
                        horProgress.setVisibility(View.INVISIBLE);
                        horProgress.setProgress(0);
                        counterTxt.setText("");
                        startBtn.setEnabled(true);
                        break;
                    case TOTAL_COUNT:
                        currentCount = msg.arg1;
                        totalCountTxt.setText(String.valueOf(msg.arg1));
                        break;
                    case CURRENT_FILE:
                        counterTxt.setText(msg.arg1+"/"+currentCount);
                        break;
                    case CURRENT_PROGRESS:
                        horProgress.setProgress(msg.arg1);
                        break;
                }
                return true;
            }
        });

        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start_btn){
            new Worker(handler).start();
        }
    }
}
