package com.tech_nopro.handler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tech_nopro.R;
import com.tech_nopro.utility.Utility;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

/**
 * Created by vikrant on 07/06/16.
 */
/*
The Handler class can be used to register to a thread and provides a simple channel to send data to this thread.A Handler is particular useful if you have want to post multiple times data to the main thread.


When we install an application in android then it create a thread  for that application called MAIN UI Thread, All activities run inside that thread ,
 By the android single thread model rule we can not access UI elements (bitmap , textview etc..) directly for another thread defined inside that activity.
 So if want to access Main UI Thread elements by another thread then we will use  handlers.
 */
public class Handler_Main extends Activity {
	private ProgressDialog progressDialog;
	private ImageView imageView;
	private String url = "http://www.hallaminternet.com/assets/URL-tagging-image.png";
	private Bitmap bitmap = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.handler_layout);
		imageView = (ImageView) findViewById(R.id.imageView);

		Button start = (Button) findViewById(R.id.button1);
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				progressDialog = ProgressDialog.show(Handler_Main.this, "", "Loading..");
				new Thread() {
					public void run() {
						bitmap = downloadBitmap(url);
						messageHandler.sendEmptyMessage(0);
					}
				}.start();
			}
		});
	}

	private Handler messageHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			imageView.setImageBitmap(bitmap);
			progressDialog.dismiss();
		}
	};

	private Bitmap downloadBitmap(String url) {
		// Initialize the default HTTP client object
		final DefaultHttpClient client = new DefaultHttpClient();

		//forming a HttpGet request
		final HttpGet getRequest = new HttpGet(url);
		try {
			HttpResponse response = client.execute(getRequest);
			//check 200 OK for success
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Utility.print("Error " + statusCode + " while retrieving bitmap from " + url);
				//Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					// getting contents from the stream
					inputStream = entity.getContent();
					// decoding stream data back into image Bitmap
					Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					return bitmap;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			getRequest.abort();
			Utility.print("Error "+ e.toString());
			//Log.e(getString(R.string.app_name), "Error "+ e.toString());
		}
		return null;
	}
}
