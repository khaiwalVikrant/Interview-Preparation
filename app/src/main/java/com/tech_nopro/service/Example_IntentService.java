package com.tech_nopro.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by vikrant on 06/06/16.
 */
/*
The IntentService is used to perform a certain task in the background. Once done, the instance of IntentService terminates itself automatically.
The IntentService class offers the onHandleIntent() method which will be asynchronously called by the Android system.


 */
public class Example_IntentService extends IntentService {
	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public Example_IntentService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

	}
}
