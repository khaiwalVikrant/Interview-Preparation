package com.tech_nopro.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vikrant on 07/06/16.
 */

public class MyAsyncTask extends AsyncTask<String, Void, Integer> {

private Context mContext;

public MyAsyncTask(Context context) {
		mContext = context.getApplicationContext();
		}

@Override
protected void onPreExecute() {
		Toast.makeText(mContext, "Let's start!", Toast.LENGTH_LONG).show();
		}

@Override
protected Integer doInBackground(String... params) {
		HttpURLConnection connection;
		try {
		connection = (HttpURLConnection) new URL(params[0])
		.openConnection();
		return connection.getResponseCode();
		} catch (IOException e) {
		e.printStackTrace();
		}
		return -1;
		}

@Override
protected void onPostExecute(Integer integer) {
		if (integer != -1) {
		Toast.makeText(mContext, "Got the following code: " + integer,
		Toast.LENGTH_LONG).show();
		}
		}
		}

