package com.nu.media.views.listeners;

import com.nu.media.helpers.ActionBarHelper;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

public class DrawerListener implements DrawerLayout.DrawerListener {

	private ActionBarHelper mActionBar;
	private SherlockActionBarDrawerToggle mDrawerToggle;
	
	public DrawerListener(SherlockActionBarDrawerToggle toggle, ActionBarHelper bar){
		this.mActionBar = bar;
		this.mDrawerToggle = toggle;
	}
	
	@Override
	public void onDrawerOpened(View drawerView) {
		mDrawerToggle.onDrawerOpened(drawerView);
		mActionBar.onDrawerOpened();
	}

	@Override
	public void onDrawerClosed(View drawerView) {
		mDrawerToggle.onDrawerClosed(drawerView);
		mActionBar.onDrawerClosed();
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
	}

	@Override
	public void onDrawerStateChanged(int newState) {
		mDrawerToggle.onDrawerStateChanged(newState);
	}
}
