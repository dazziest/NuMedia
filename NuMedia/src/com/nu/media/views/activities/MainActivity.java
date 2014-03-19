package com.nu.media.views.activities;

import java.util.List;

import com.nu.media.R;
import com.nu.media.models.Article;
import com.nu.media.models.ListArticle;
import com.nu.media.views.fragments.BaseContentFragment;
import com.nu.media.views.fragments.MainFragment;
import com.nu.media.views.fragments.MainMenuFragment;
import com.nu.media.views.listeners.SelectedArticleListener;
import com.nu.media.views.listeners.OnMenuClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MainActivity extends BaseActivity implements OnMenuClickListener, SelectedArticleListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		overridePendingTransition(R.anim.activity_close_scale,R.anim.activity_open_translate);
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
	}
	
	@Override
	protected void setLeftDrawer() {
		leftDrawer = new MainMenuFragment();
		replaceFragment(R.id.left_drawer, leftDrawer);
	}
	
	@Override
	protected void setRightDrawer() {
		rightDrawer = new MainFragment();
		replaceFragment(R.id.right_drawer, rightDrawer);
	}

	@Override
	public void onResume(){
		super.onResume();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.refresh, menu);
//		return true;
//	}

//	@Override
//	public void onListItemClick(List<Article> list) {
//		ListArticle.setArticle(list);
//	}

	@Override
	public void onSelectedArticle(int position) {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra("PagePosition", position);
		startActivity(intent);
	}

	@Override
	public void onClickMainMenu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClickMainMenu(Class<? extends Fragment> cls, int position) {
		
	}

	@Override
	public void onSelectFragmentMenu(Class<? extends Fragment> cls, int pos) {
		mDrawerLayout.closeDrawer(leftDrawerLayout);
		try {
			Fragment frag = cls.newInstance();
			replaceFragment(R.id.right_drawer, frag);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed(){
		super.onBackPressed();
	}
}
