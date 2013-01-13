package com.fastfox.mastersyncwifionly;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = "MyBroadcastReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
		boolean sync = ContentResolver.getMasterSyncAutomatically();
		switch (extraWifiState) {
		  case WifiManager.WIFI_STATE_DISABLING:
		   Log.d(TAG, "Wifi disabled");
		   if (sync) {
			   ContentResolver.setMasterSyncAutomatically(false);
			   Log.d(TAG, "Master sync disabled");
		   }
		   break;
		  case WifiManager.WIFI_STATE_ENABLED:
		   Log.d(TAG, "Wifi enabled");
		   if (sync == false) {
			   ContentResolver.setMasterSyncAutomatically(true);
			   Log.d(TAG, "Master sync disabled");
		   }		   
		   break;
		}
	}
}
