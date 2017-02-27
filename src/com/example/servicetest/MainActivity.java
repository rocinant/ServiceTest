package com.example.servicetest;

import android.support.v4.app.ServiceCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	private MyService.ServiceBinder serviceBinder;
	
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
		
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			serviceBinder = (MyService.ServiceBinder)service;
			//call functions exposed in Service via binder
			serviceBinder.startRoutin();
			Log.d("MainActivity Binder", "" + serviceBinder.getProgress());
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button startService = (Button) findViewById(R.id.start_service);
		Button stopService = (Button) findViewById(R.id.stop_service);
		Button bindService = (Button) findViewById(R.id.bind_service);
		Button unbindService = (Button) findViewById(R.id.unbind_service);
		Button startIntentService = (Button) findViewById(R.id.start_intentservice);
		startService.setOnClickListener(this);
		stopService.setOnClickListener(this);
		bindService.setOnClickListener(this);
		unbindService.setOnClickListener(this);
		startIntentService.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.start_service:
			Intent startIntent = new Intent(this, MyService.class);
			startService(startIntent);
			break;
		case R.id.stop_service:
			Intent stopIntent = new Intent(this, MyService.class);
			stopService(stopIntent);
			break;
		case R.id.bind_service:
			Intent bindIntent = new Intent(this, MyService.class);
			bindService(bindIntent, connection, BIND_AUTO_CREATE);
			break;
		case R.id.unbind_service:
			unbindService(connection);
			break;
		case R.id.start_intentservice:
			Log.d("MainActivity", "The thread id is: " + Thread.currentThread().getId());
			Intent startIntentServiceIntent = new Intent(this, MyIntentService.class);
			startService(startIntentServiceIntent);
			break;
		default:
			break;
		}
		
	}
}
