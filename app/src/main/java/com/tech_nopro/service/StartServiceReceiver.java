package com.tech_nopro.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vikrant on 06/06/16.
 */

public class StartServiceReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, ServiceClass.class);
		context.startService(service);
	}
}
