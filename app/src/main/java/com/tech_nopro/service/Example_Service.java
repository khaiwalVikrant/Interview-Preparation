package com.tech_nopro.service;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.tech_nopro.R;
import com.tech_nopro.utility.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikrant on 06/06/16.
 */
/*
Service : is a component of android which performs long running operation in the background, mostly with out having UI.it is not bound to the lifecycle of an activity.
Services are used for repetitive and potentially long running operations, i.e., Internet downloads, checking for new data, data processing, updating content providers and the like.
By default, a service runs in the same process as the main thread of the application.

Foreground services

A foreground service is a service that should have the same priority as an active activity and therefore should not be killed by the Android system, even if the system is low on memory.
A foreground service must provide a notification for the status bar, which is placed under the "Ongoing" heading, which means that the notification cannot be dismissed unless the service is either stopped
    or removed from the foreground.

Thread : is a O.S level feature that allow you to do some operation in the background.

Though conceptually both looks similar there are some crucial differentiation.

1.Service - if it is destroyed while performing its job, in the middle by Android due to low memory scenario. Then android will make sure that it will restart your service,
    if you have returned START_STICKY or START_REDELIVER_INTENT from onStartCommand().
2.Thread - if it is destroyed by android in middle due to low memory, then android will not guarantee to restart it again. That means user lost his half work.
3.Service - is a component of android, so it has priority levels to be considered while destroying an application due to low memory.
    Thread- is not a component of android, so android will not take thread priority into consideration while killing an application due to low memory.

I will try to explain this 3rd point.

Lets say you have a requirement of connecting to internet from your activity. You can do it by using a service(with thread) or directly by creating a thread in activity.
Consider the second scenario where you are connecting to internet in a thread. Then

i. What will happen if user closes the activity, while still thread is running in the background. Will that thread continue to run in back ground ? Answer is you can&#39;t really predict.
ii.Assume that in continuation for above scenario, even after killing activity your thread continued to do its intended operation. Then there is a low memory situation arises in your phone. Then this application will be the first susceptible app to be killed as there is no priority for this application.

So bottom line is: If you want to do some heavy background functionality then it is always better to have a service with thread. If you feel that that background functionality to be alive as long as your activity is alive, then go for activity with thread or activity with async task.
 */
public class Example_Service extends ListActivity {

    private ArrayAdapter<String> adapter;
    private List<String> wordList;
    private ServiceClass serviceClass;
    private boolean mIsBound;

    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.service_layout);

        wordList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, wordList);
        Utility.print("Size: " + wordList.size());
        setListAdapter(adapter);
        // use this to start and trigger a service
        //Intent i= new Intent(getApplicationContext(), ServiceClass.class);
// potentially add data to the intent
        //i.putExtra("KEY1", "Value to be used by the service");
        //startService(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.print("onPause");
        unbindService(mConnection);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.print("onResume");
        Intent intent = new Intent(this, ServiceClass.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
//    void doBindService() {
//        // Establish a connection with the service.  We use an explicit
//        // class name because we want a specific service implementation that
//        // we know will be running in our own process (and thus won't be
//        // supporting component replacement by other applications).
//        bindService(new Intent(Example_Service.this,
//                ServiceClass.class), mConnection, Context.BIND_AUTO_CREATE);
//        mIsBound = true;
//    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            ServiceClass.MyBinder b = (ServiceClass.MyBinder) service;
//            serviceClass = b.getService();
            serviceClass = ((ServiceClass.MyBinder) service).getService();
            Utility.print("Service Connection connected");
            Toast.makeText(Example_Service.this, "Connected", Toast.LENGTH_SHORT);
        }

        //@Override
        public void onServiceDisconnected(ComponentName name) {
            serviceClass = null;
        }
    };

    public void onClick(View view){
        if(serviceClass != null){
            Toast.makeText(Example_Service.this, "Number of elements: " + serviceClass.getWordList(), Toast.LENGTH_SHORT);
            wordList.clear();
            wordList.addAll(serviceClass.getWordList());
            adapter.notifyDataSetChanged();
        }
    }
}
