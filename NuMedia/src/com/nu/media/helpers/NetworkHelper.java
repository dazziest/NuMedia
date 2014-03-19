package com.nu.media.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkHelper extends BroadcastReceiver {
	public static boolean isOnline(Context context){
		boolean isWifiConnected;
		NetworkInfo activeNetwork = getActiveNetwork(context);
		if (activeNetwork == null) {
			isWifiConnected = false;
			Toast.makeText(context, getConnectionInfo(context), Toast.LENGTH_LONG).show();
		}else {
			if (activeNetwork.getType() != ConnectivityManager.TYPE_WIFI) {
				isWifiConnected = false;
				Toast.makeText(context, getConnectionInfo(context), Toast.LENGTH_LONG).show();
			}else if (activeNetwork.isConnectedOrConnecting()) {
				isWifiConnected = true;
			}else {
				isWifiConnected = false;
				Toast.makeText(context, getConnectionInfo(context), Toast.LENGTH_LONG).show();
			}
		}
		return isWifiConnected;
	}
	
	private static NetworkInfo getActiveNetwork(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}
	
	public static String getConnectionInfo(Context context){
		NetworkInfo activeNetwork = getActiveNetwork(context);
		String info = "Wifi disconnected";
		if (activeNetwork == null) {
			info = "No network available";
		}else {
			if (activeNetwork.isConnectedOrConnecting()) {
				info = "Connected to "+activeNetwork.getTypeName();
			}else {
				info = "Cannot connect to network";
			}
			
		}
		return info;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		WifiListener wListener = null;
		try {
			wListener = (WifiListener) context;
		} catch (Exception e) {
			Log.e("WifiListener", "must implement wifi listener");
			return;
		}
		NetworkInfo nInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
		if(nInfo.getType() == ConnectivityManager.TYPE_WIFI || nInfo.getType() == ConnectivityManager.TYPE_MOBILE){
			if (nInfo.isConnectedOrConnecting()) {
				wListener.onWifiStateChange(true);
			}else {
				wListener.onWifiStateChange(false);
			}
		}else {
			wListener.onWifiStateChange(false);
		}
	}
	
	public interface WifiListener{
		public void onWifiStateChange(boolean connected);
	}
}
