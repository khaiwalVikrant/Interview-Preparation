package com.tech_nopro.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;

import com.tech_nopro.utility.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by vikrant on 06/06/16.
 */
/*
How to start service
1. An Android component (service, receiver, activity) can trigger the execution of a service via the startService(intent) method.
2. You can also start a service via the bindService() method call. This allows you to communicate directly with the service.

Process of Service execution
If the startService(intent) method is called and the service is not yet running, the service object is created and the onCreate() method of the service is called.
A service is only started once, no matter how often you call the startService() method.

If startService(intent) is called while the service is running, its onStartCommand() is also called.
	Therefore your service needs to be prepared that onStartCommand() can be called several times.


Communication with services

1. Using Intent data

In a simple scenario no direct communication is required. The service receives the intent data from the starting Android component and performs its work. No notification is necessary.
For example, in case the service updates a content provider, the activity is notified by the content provider and no extra step in the service is necessary.

2. Using receiver

You can also use broadcast events and registered receivers for the communication. For example, your activity can dynamically register a broadcast receiver for an
event and the service sends outs corresponding events.

 */
public class ServiceClass extends Service {
	private final IBinder mBinder = new MyBinder();
	private ArrayList<String> list = new ArrayList<String>();
	public class MyBinder extends Binder {
		ServiceClass getService() {
			Utility.print("MyBinder constructor");
			return ServiceClass.this;
		}
	}

	public List<String> getWordList() {
		return list;
	}

	@Override
	public IBinder onBind(Intent intent) {
		/*
		If the activity wants to interact with the service directly, it can use the bindService() method to start the service.
		This IBinder object can be used by the activity to communicate with the service.


		The system calls this method when another component wants to bind with the service by calling bindService().
		If you implement this method, you must provide an interface that clients use to communicate with the service, by returning an IBinder object.
		You must always implement this method, but if you don't want to allow binding, then you should return null.
		 */
		Utility.print("Binder"+getWordList());
		return mBinder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		/*
		We have to notice that using Android Service we don’t create automatically new threads, so if we implement a simple logic inside our Service,
		that doesn’t require long time processing we don’t need to run it a separate thread, but if we have to implement complex logic, with long time processing,
		we have to take care of creating a new thread, otherwise the service runs on the main thread and it could cause ANR problem.

		Return types of Start Command

		1. Service.START_STICKY:-  Service is restarted if it gets terminated. Intent data passed to the onStartCommand method is null.
									Used for services which manages their own state and do not depend on the Intent data.
		2. Service.START_NOT_STICKY:- Service is not restarted. Used for services which are periodically triggered anyway.
										The service is only restarted if the runtime has pending startService() calls since the service termination.
		3. Service.START_REDELIVER_INTENT:- Similar to Service.START_STICKY but the original Intent is re-delivered to the onStartCommand method.
		 */

		Utility.print("StartCommand");
		Random random = new Random();
		if (random.nextBoolean()) {
			list.add("Linux");
		}
		if (random.nextBoolean()) {
			list.add("Android");
		}
		if (random.nextBoolean()) {
			list.add("iPhone");
		}
		if (random.nextBoolean()) {
			list.add("Windows7");
		}
		Utility.print("List Size : " + list.size());
		if (list.size() >= 20) {
			list.remove(0);
		}
		return Service.START_STICKY;
	}

	@Override
	public void onCreate() {
		/*
		The system calls this method when the service is first created using onStartCommand() or onBind(). This call is required to perform one-time set-up.
		 */
		super.onCreate();
		Utility.print("Create");
	}

//	@Override
//	public void onStart(Intent intent, int startId) {
//		super.onStart(intent, startId);
//		Utility.print("onStart");
//	}

	@Override
	public void onDestroy() {
		/*
		The system calls this method when the service is no longer used and is being destroyed. Your service should implement this to clean up any resources such as threads,
		registered listeners, receivers, etc.
		 */
		super.onDestroy();
		Utility.print("Destroy");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		/*
		The system calls this method when all clients have disconnected from a particular interface published by the service.
		 */
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		/*
		The system calls this method when new clients have connected to the service, after it had previously been notified that all had disconnected in its onUnbind(Intent).
		 */
		super.onRebind(intent);
	}

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		super.onTaskRemoved(rootIntent);
	}
}
