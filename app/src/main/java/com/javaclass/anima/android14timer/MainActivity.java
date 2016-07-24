package com.javaclass.anima.android14timer;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private View start,stop;
    private TextView tv1,tv2;
    private Timer timer;
    private uiHandler uiHandler;
    private String Tag= "MainActivity";
    MyTask1 task1;
    MyTask2 task2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        uiHandler = new uiHandler();

        start = findViewById(R.id.btn_start);
        stop = findViewById(R.id.btn_stop);

        tv1 = (TextView) findViewById(R.id.msg1);
        tv2 = (TextView) findViewById(R.id.msg2);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            task2 = new MyTask2();
                timer.scheduleAtFixedRate(task2, 0, 1000);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (task2 != null) {
                    task2.cancel();
                    task2 = null;
                }
            }
        });

        task1 = new MyTask1();
        timer.schedule(task1, 0, 200);
    }

    private class MyTask1 extends TimerTask{

        @Override
        public void run() {
                uiHandler.sendEmptyMessage(0);
        }
    }

    private class MyTask2 extends  TimerTask{
        @Override
        public void run() {
            uiHandler.sendEmptyMessage((int) (Math.random() * 49 + 1));
        }
    }

    private class uiHandler extends  Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Calendar now = Calendar.getInstance();
                now.setTime(new Date(System.currentTimeMillis()));
                tv2.setText(now.get(Calendar.MINUTE) + ":"
                        + now.get(Calendar.SECOND));
            } else {
                tv1.setText("" + msg.what);
            }

        }
    }

    @Override
    public void finish() {
        timer.cancel();
        timer = null;

        super.finish();
    }
}
