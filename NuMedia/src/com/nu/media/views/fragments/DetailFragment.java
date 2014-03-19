package com.nu.media.views.fragments;

import com.nu.media.R;
import com.nu.media.helpers.ActionBarHelper;
import com.nu.media.models.Article;
import com.nu.media.models.ListArticle;
import com.nu.media.models.dao.DataAccess;
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
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

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
		mPagerAdapter = new ArticlesPagerAdapter(getFragmentManager(), ListArticle.getArticle());
		viewPager.setAdapter(mPagerAdapter);
		viewPager.setOnPageChangeListener(getPageChangeListener());
		viewPager.setCurrentItem(currentPosition);
		
		//indicator hit
//				txtIndicator = (TextView) view.findViewById(R.id.txtIndicator);
//				btnIndicatorLeft = (ImageButton) view.findViewById(R.id.btnIndicatorLeft);
//				btnIndicatorRight = (ImageButton) view.findViewById(R.id.btnIndicatorRight);
//				
//				btnIndicatorLeft.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
//						
//					}
//				});
//				
//				btnIndicatorRight.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
//					}
//				});
				
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
	
	public void setNotify(){
		viewPager.getAdapter().notifyDataSetChanged();
	}

	@Override
	public void retrieveData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveData() {
		// TODO Auto-generated method stub
		
	}

	public interface PageChangeListener{
		public void onPageChange(int position);
	}

	@Override
	public void initializeData(DataAccess<Article> data) {
		
	}
	
	public void setNavigationEnable(int position){
//		if (viewPager != null) {
//			int maxPage = viewPager.getAdapter().getCount() - 1;
//			int minPage = 0;
//			btnIndicatorLeft.setImageResource(R.drawable.arrow_left_enable);
//			btnIndicatorRight.setImageResource(R.drawable.arrow_right_enable);
//			if (position == maxPage && position == minPage) {
//				btnIndicatorLeft.setVisibility(View.INVISIBLE);
//				btnIndicatorRight.setVisibility(View.INVISIBLE);
//			}else if (position <= minPage) {
//				btnIndicatorLeft.setVisibility(View.INVISIBLE);
//				btnIndicatorRight.setVisibility(View.VISIBLE);
//			}else if (position >= maxPage) {
//				btnIndicatorLeft.setVisibility(View.VISIBLE);
//				btnIndicatorRight.setVisibility(View.INVISIBLE);
//			}else {
//				btnIndicatorLeft.setVisibility(View.VISIBLE);
//				btnIndicatorRight.setVisibility(View.VISIBLE);
//			}
//		}
	}

}
