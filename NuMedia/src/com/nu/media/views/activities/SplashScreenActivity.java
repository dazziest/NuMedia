package com.nu.media.views.activities;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.nu.media.R;
import com.nu.media.R.layout;
import com.nu.media.R.menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.widget.LinearLayout;

public class SplashScreenActivity extends SherlockFragmentActivity {

	private boolean isSuccessInflatingView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_spalsh_screen);
			isSuccessInflatingView = true;
			new InitializerAsyncTask().execute("");
		} catch (Exception e) {
			//Many exceptions caused by low memory resource (small screen device) in splash screen activity 
			//the codes below try to handle it 
			isSuccessInflatingView = false;
			new InitializerAsyncTask().execute("");
		}
	}

	@Override
	public void onResume() {
		//For small screen device
		if (!isSuccessInflatingView) {
			LinearLayout layout = (LinearLayout) this.findViewById(R.id.splash_screen);
			layout.setBackgroundResource(R.drawable.splash_screen);
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		//For small screen device
		if (!isSuccessInflatingView) {
			LinearLayout layout = (LinearLayout) this.findViewById(R.id.splash_screen);
			layout.setBackgroundResource(R.drawable.splash_screen);
		}
	}
	
	/**
	 * Initialize strict mode.
	 * @throws ClassNotFoundException 
	 */
//	private void initializeStrictMode() throws ClassNotFoundException {
//		
//		StrictMode.ThreadPolicy policy =
//				new StrictMode.ThreadPolicy.Builder()
//					.permitAll()
//					.build();
//		
//		StrictMode.setThreadPolicy(policy);
//		
//		//use the statements below rather than penalty death 
//		StrictMode.setVmPolicy(
//				new StrictMode.VmPolicy.Builder()
//					.detectAll()
//					.penaltyLog()
//					.penaltyDropBox()
//				    .build());
//	}
	
	/**
	 * Background thread responsible for initializing the app.
	 */
	private class InitializerAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
//			try {
//				initializeStrictMode();
				//initialize database
//				DatabaseInitializer initializer = new DatabaseInitializer(SplashScreenActivity.this);
//				initializer.createDatabase();
//				initializer.close();
//			}catch (IOException e) {
//				e.printStackTrace();
//			}catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return "executed";
		}

		@Override
		protected void onPostExecute(String result) {
			
			// Delay 1 sec before start Main Activity
			Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() {
		        	Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
		     		startActivity(intent);
		     		finish();
		         } 
		    }, 1000); 
			
		}
	}
}
