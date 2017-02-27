package com.example.servicetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	public AlarmReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent reStartServiceIntent = new Intent(context, MyService.class);
		context.startService(reStartServiceIntent);
	}

}
