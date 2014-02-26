package com.nu.media.views.adapters;


import com.nu.media.models.ListArticle;
import com.nu.media.views.fragments.ViewArticleFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ArticlesPagerAdapter extends FragmentStatePagerAdapter {
	
//	private static final int NUM_PAGES = 5;
	
	public ArticlesPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int pos) {
		
		return ViewArticleFragment.create(pos);
	}

	@Override
	public int getCount() {
		if (ListArticle.getArticle() != null) {
			return ListArticle.getArticle().size();
		} else {
			return 0;
		}
	}

}
