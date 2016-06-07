package com.tech_nopro.asynctask;

import android.app.Activity;
import android.os.Bundle;

import com.tech_nopro.R;

/**
 * Created by vikrant on 07/06/16.
 */
/*

 */
public class Async_Activity extends Activity {
	@Override
	protected void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.async_layout);
		MyAsyncTask myTask = new MyAsyncTask(this);
		myTask.execute("http://tech-nopro.com");
	}
}
