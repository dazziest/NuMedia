package com.nu.media.views.activities;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.nu.media.R;
import com.nu.media.helpers.ActionBarHelper;
import com.nu.media.helpers.NetworkHelper;
import com.nu.media.helpers.NetworkHelper.WifiListener;
import com.nu.media.views.fragments.BaseContentFragment;
import com.nu.media.views.fragments.DetailMenuFragment;
import com.nu.media.views.listeners.DrawerListener;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;

import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


/**
 * Base activity which provide basic function 
 * @author muhammad_a
 *
 */
public abstract class BaseActivity extends SherlockFragmentActivity implements WifiListener {
	
	private NetworkHelper wifiReceiver;	
	protected DrawerLayout mDrawerLayout;
//	protected FrameLayout rightDrawer;
	protected SherlockActionBarDrawerToggle mDrawerToggle;
	protected ActionBarHelper mActionBar;
	protected ListView listViewArticle;

	protected BaseContentFragment leftDrawer;
	protected BaseContentFragment rightDrawer;
	protected FrameLayout rightDrawerLayout;
	protected FrameLayout leftDrawerLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getString(R.string.app_name));
//		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
		setContentView(R.layout.fragment_main_layout);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		leftDrawerLayout = (FrameLayout) findViewById(R.id.left_drawer);
		rightDrawerLayout = (FrameLayout) findViewById(R.id.right_drawer);
		
		setRightDrawer();
		setLeftDrawer();
		
		mActionBar = createActionBarHelper();
		mActionBar.init();

		// ActionBarDrawerToggle provides convenient helpers for tying together
		// the
		// prescribed interactions between a top-level sliding drawer and the
		// action bar.
		mDrawerToggle = new SherlockActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer_light, R.string.drawer_open, R.string.drawer_close);
		mDrawerLayout.setDrawerListener(new DrawerListener(mDrawerToggle, mActionBar));
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerToggle.syncState();
		wifiReceiver = new NetworkHelper();
	}
	
	protected abstract void setRightDrawer();

	private ActionBarHelper createActionBarHelper() {
		return new ActionBarHelper(this);
	}
	
	protected abstract void setLeftDrawer();
	
//	protected ListAdapter getListDrawerAdapter() {
//		return null;
//	}
//	
//	protected OnItemClickListener getItemClickListener() {
//		return null;
//	}
	
	
	@Override
	public void onResume(){
		super.onResume();
		this.registerReceiver(wifiReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}
		
	/**
	 * Replace target layout with source fragment
	 * @param target
	 * @param source
	 */
	protected void replaceFragment(int target, Fragment source) {
		this.getSupportFragmentManager().beginTransaction()
		.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
		.replace(target, source).commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/**
		 * The action bar home/up action should open or close the drawer.
		 * mDrawerToggle will take care of this.
		 */
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	/***
	 * create action bar menu on top from menu layout
	 */
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.refresh, menu);
//		return super.onCreateOptionsMenu(menu);
//	}
	
//	@Override
//	public boolean onPrepareOptionsMenu(Menu menu) {
//		if (content != null) {
//			content.onPrepareOptionsMenu(menu);
//		}		
//		return super.onPrepareOptionsMenu(menu);
//	}
	
	/**
	 * Handle action bar action
	 */
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		boolean state = super.onOptionsItemSelected(item);
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			toggle();
//			break;
//		case R.id.menu_refresh:
//			if (content != null) {
//				content.onOptionsItemSelected(item);
//			}			
////			setDropDownResources();
//			break;
//		default:
//			break;
//		}
//		return state;
//	}
	
	/**
	 * Get all drop down options
	 */
//	private void setDropDownResources() {
//		if (!DropDownMenuHelper.isSynchronized()) {
//			DropDownMenuHelper.setOptions(getApplicationContext());
//		}
//	}
	
	public void toastIt(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
	
	@Override
	  protected void onPause(){
	    super.onPause();
	    //closing transition animations
//	    overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
	    this.unregisterReceiver(wifiReceiver);
	  }

	@Override
	public void onWifiStateChange(boolean connected) {
//		if (!connected) {
//			toastIt("Connection Lost");
//		}else if (content != null && !content.isSynchronize()) {
//			content.retrieveData();
//		}
	}
	
	
}
