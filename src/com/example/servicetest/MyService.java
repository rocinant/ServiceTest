package com.example.servicetest;

import java.util.Date;
import java.util.Random;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class MyService extends Service {
	
	private ServiceBinder serviceBinder = new ServiceBinder();

	public MyService() {
		// TODO Auto-generated constructor stub
	}
	
	class ServiceBinder extends Binder{
		
		public void startRoutin(){
			Log.d("MyService Binder", "Task begins");
		}
		
		public int getProgress(){
			Log.d("MyService Binder", "get progress");
			return (int)new Random().nextInt(100);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return serviceBinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.d("MyService", "OnCreat() get called");
		
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		Notification.Builder builder = new Notification.Builder(this);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker("This is ticker text");
		builder.setContentTitle("This is notification title");
		builder.setContentText("This is content body");
		builder.setContentIntent(pendingIntent);
		builder.setAutoCancel(true);
		builder.setOngoing(false);
		builder.setSubText("This is subtext");
		builder.setNumber(100);
		builder.setWhen(SystemClock.currentThreadTimeMillis());
		Notification notification  = builder.build();
		startForeground(1, notification);
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d("MyService", "onStartCommand() get called");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//do the processing logics here
				Log.d("MyService", "executed at " + new Date().toString());
				//stopSelf();
			}
		}).start();
		
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int anHour = 1000*60*60;// in milliseconds
		long triggerTime = SystemClock.elapsedRealtime() + anHour;
		
		Intent intent1 = new Intent(this, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent1, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent);
		
		return super.onStartCommand(intent1, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d("MyService", "onDestroy() get called");
		super.onDestroy();
	}

}
