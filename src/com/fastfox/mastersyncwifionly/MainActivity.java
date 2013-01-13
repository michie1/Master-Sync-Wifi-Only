package com.fastfox.mastersyncwifionly;

import android.app.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;

import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.util.Log;


import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private MyBroadcastReceiver WifiStateChangedReceiver = new MyBroadcastReceiver();
	private boolean activated;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) { 
    	activated = true;        
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onStart() {
    	
    	Log.d(TAG, "onStart");
    	Switch activationSwitch = (Switch) findViewById(R.id.activation_switch);
    	
        activationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.d(TAG, "Switched");
				if (isChecked) {
					activated = true;
					Log.d(TAG, "Register");
					WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
					boolean sync = ContentResolver.getMasterSyncAutomatically();
					if (wifiManager.isWifiEnabled()) {
						Log.d(TAG, "Wifi enabled");
						if (sync == false) {
							ContentResolver.setMasterSyncAutomatically(true);
							Log.d(TAG, "Master sync disabled");
						}
					} else {
						Log.d(TAG, "Wifi disabled");
						if (sync) {
						   ContentResolver.setMasterSyncAutomatically(false);
						   Log.d(TAG, "Master sync disabled");
						}
					}
					registerReceiver(WifiStateChangedReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
				} else {
					activated = false;
					Log.d(TAG, "Unregister");
					unregisterReceiver(WifiStateChangedReceiver);
				}
				
			}
		});
        activationSwitch.setChecked(activated);
        super.onStart();
    }

}