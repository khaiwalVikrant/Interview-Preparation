package com.tech_nopro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tech_nopro.service.Example_Service;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        startService = (Button) findViewById(R.id.startService);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Example_Service.class);
                startActivity(intent);
            }
        });
    }
}
