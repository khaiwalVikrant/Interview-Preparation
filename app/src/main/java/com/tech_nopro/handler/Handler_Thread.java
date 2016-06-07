package com.tech_nopro.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.tech_nopro.R;

/**
 * Created by vikrant on 07/06/16.
 */
/*
A Handler allows you to send and process Message and Runnable objects associated with a thread’s MessageQueue. Each Handler instance is associated with a single thread and that thread’s message queue.
When you create a new Handler, it is bound to the thread/message queue of the thread that is creating it — from that point on, it will deliver messages and runnables to that message queue
and execute them as they come out of the message queue.
Two ways to create Handler

using the default constructor: new Handler()
using a parameterized constructor that takes a runnable object or callback object

Methods of Handler API:
1. post
2. postDelayed
3. postAtTime

Message Queues:

Threads basically have something called a “Message Queue”. These message queues allow communication between threads and is a sort of pattern, where control (or content) is passed between the threads.
it’s exactly just that: a queue of messages or sequence of instructions, for the thread, to perform one by one. This additionally allows us to do two more cool things:
1. “schedule” Messages and Runnables to be executed at some point in the future
2. enqueue an action to be performed on a different thread other than your own
 */
public class Handler_Thread extends Activity{
	private TextView textView;
	/*
	2 Ways of create an thread

		// Version 1
			public class IAmAThread extends Thread {
			    public IAmAThread() {
			        super("IAmAThread");
			    }

			    @Override
			    public void run() {
			        // your code (sequence of instructions)
			    }
			}
			// to execute this sequence of instructions in a separate thread.
			new IAmAThread().start();

		// Version 2
			public class IAmARunnable implements Runnable {
			    @Override
			    public void run() {
			        // your code (sequence of instructions)
			    }
			}
			// to execute this sequence of instructions in a separate thread.
			IAmARunnable myRunnable = new IAmARunnable();
			new Thread(myRunnable).start();
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.handler_thread_layout);

		textView = (TextView) findViewById(R.id.textViewHandler);

		// we're creating a new handler here
		// and we're in the UI Thread (default)
		// so this Handler is associated with the UI thread
		final Handler mHandler = new Handler();

		// I want to start doing something really long
		// which means I should run the fella in another thread.
		// I do that by sending a message - in the form of another runnable object

		// But first, I'm going to create a Runnable object or a message for this
		Runnable mRunnableOnSeparateThread = new Runnable() {
			@Override
			public void run () {

				// do some long operation
				longOperation();

				// After mRunnableOnSeparateThread is done with it's job,
				// I need to tell the user that i'm done
				// which means I need to send a message back to the UI thread

				// who do we know that's associated with the UI thread?
				mHandler.post(new Runnable(){
					@Override
					public void run(){
						// do some UI related thing
						// like update a progress bar or TextView
						textView.setText("Completed");
					}
				});


			}
		};

		// Cool but I've not executed the mRunnableOnSeparateThread yet
		// I've only defined the message to be sent
		// When I execute it though, I want it to be in a different thread
		// that was the whole point.

		new Thread(mRunnableOnSeparateThread).start();
	}
	private void longOperation() {
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
