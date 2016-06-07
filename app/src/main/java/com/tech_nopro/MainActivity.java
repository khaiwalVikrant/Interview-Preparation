package com.tech_nopro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tech_nopro.asynctask.Async_Activity;
import com.tech_nopro.handler.Handler_Main;
import com.tech_nopro.handler.Handler_Thread;
import com.tech_nopro.service.Example_Service;
import com.tech_nopro.service.IntentService_MainActivity;


/*

Service
AsyncTAsk
Thread
IntentService
Handler
Looper

 */
public class MainActivity extends AppCompatActivity {

    Button startService;
    Intent intent;
    private Button intentService, handler, handlerThread, asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        startService = (Button) findViewById(R.id.startService);
        intentService = (Button) findViewById(R.id.intentService);
        handler = (Button) findViewById(R.id.handler);
        handlerThread = (Button) findViewById(R.id.handlerThread);
        asyncTask = (Button) findViewById(R.id.asynctask);


        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Example_Service.class);
                startActivity(intent);
            }
        });
        intentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, IntentService_MainActivity.class);
                startActivity(intent);
            }
        });
        handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Handler_Main.class);
                startActivity(intent);
            }
        });
        handlerThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Handler_Thread.class);
                startActivity(intent);
            }
        });
        asyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Async_Activity.class);
                startActivity(intent);
            }
        });

    }
}
