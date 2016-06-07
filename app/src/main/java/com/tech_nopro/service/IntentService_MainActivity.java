package com.tech_nopro.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tech_nopro.R;
import com.tech_nopro.utility.Utility;

/**
 * Created by vikrant on 07/06/16.
 */

public class IntentService_MainActivity extends Activity {

	private TextView textView;
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				String string = bundle.getString(Example_IntentService.FILEPATH);
				int resultCode = bundle.getInt(Example_IntentService.RESULT);
				if (resultCode == RESULT_OK) {

					Utility.print("Download complete. Download URI: " + string);
					textView.setText("Download done");
				} else {

					Utility.print("Download failed ");
					textView.setText("Download failed");
				}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intentservice_layout);
		textView = (TextView) findViewById(R.id.status);


	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, new IntentFilter(Example_IntentService.NOTIFICATION));
	}
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	public void onClick(View view) {
		Utility.print("Download started");
		Intent intent = new Intent(this, Example_IntentService.class);
		// add infos for the service which file to download and where to store
		intent.putExtra(Example_IntentService.FILENAME, "index.html");
		intent.putExtra(Example_IntentService.URL,
				"http://www.vogella.com/index.html");
		startService(intent);
		textView.setText("Service started");
	}
}
