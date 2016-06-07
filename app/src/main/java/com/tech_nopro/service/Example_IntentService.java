package com.tech_nopro.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import java.net.URL;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vikrant on 06/06/16.
 */
/*
The IntentService is used to perform a certain task in the background. Once done, the instance of IntentService terminates itself automatically.
The IntentService class offers the onHandleIntent() method which will be asynchronously called by the Android system.
Once IntentService is started, it handles each Intent using a worker thread and stops itself when it runs out of work.

An IntentService has a few limitations:

1. It can't interact directly with your user interface. To put its results in the UI, you have to send them to an Activity.
2. Work requests run sequentially. If an operation is running in an IntentService, and you send it another request, the request waits until the first operation is finished.
3. An operation running on an IntentService can't be interrupted.
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

	private int result = Activity.RESULT_CANCELED;
	public static final String URL = "urlpath";
	public static final String FILENAME = "filename";
	public static final String FILEPATH = "filepath";
	public static final String RESULT = "result";
	public static final String NOTIFICATION = "com.vogella.android.service.receiver";

	public Example_IntentService() {
		super("DownloadService");
	}

	// will be called asynchronously by Android
	@Override
	protected void onHandleIntent(Intent intent) {
		String urlPath = intent.getStringExtra(URL);
		String fileName = intent.getStringExtra(FILENAME);
		File output = new File(Environment.getExternalStorageDirectory(),
				fileName);
		if (output.exists()) {
			output.delete();
		}

		InputStream stream = null;
		FileOutputStream fos = null;
		try {

			URL url = new URL(urlPath);
			stream = url.openConnection().getInputStream();
			InputStreamReader reader = new InputStreamReader(stream);
			fos = new FileOutputStream(output.getPath());
			int next = -1;
			while ((next = reader.read()) != -1) {
				fos.write(next);
			}
			// successfully finished
			result = Activity.RESULT_OK;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		publishResults(output.getAbsolutePath(), result);
	}

	private void publishResults(String outputPath, int result) {
		Intent intent = new Intent(NOTIFICATION);
		intent.putExtra(FILEPATH, outputPath);
		intent.putExtra(RESULT, result);
		sendBroadcast(intent);
	}
}
