package com.nu.media.views.fragments;

import com.nu.media.R;
import com.nu.media.helpers.ActionBarHelper;
import com.nu.media.views.adapters.ArticlesPagerAdapter;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailFragment extends BaseContentFragment {

	private ViewPager viewPager;
	private ArticlesPagerAdapter mPagerAdapter;
	private PageChangeListener pageChangeCallbak;
	private int currentPosition = 0;
	
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try {
			pageChangeCallbak = (PageChangeListener) activity;
		} catch (Exception e) {
			toastIt("must implement PageChangeListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.viewpager_fragment_screen, container, false);
		viewPager = (ViewPager) v.findViewById(R.id.pagerArticle);
		mPagerAdapter = new ArticlesPagerAdapter(getFragmentManager());
		viewPager.setAdapter(mPagerAdapter);
		viewPager.setOnPageChangeListener(getPageChangeListener());
		viewPager.setCurrentItem(currentPosition);
		return v;
	}
	
	private SimpleOnPageChangeListener getPageChangeListener() {
		// TODO Auto-generated method stub
		return new SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position) {
				pageChangeCallbak.onPageChange(position);
			}
		};
	}
	
	public void setPage(int position){
		currentPosition = position;
		if (viewPager != null) {
			viewPager.setCurrentItem(currentPosition);
		}
	}

	@Override
	public void retrieveData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initializeData(DrawerLayout layout,	ActionBarHelper bar,	SherlockActionBarDrawerToggle toggle) {
		// TODO Auto-generated method stub
		
	}

	public interface PageChangeListener{
		public void onPageChange(int position);
	}

}
