package com.nu.media.views.activities;

import java.util.List;

import com.nu.media.R;
import com.nu.media.helpers.DatabaseHelper;
import com.nu.media.helpers.DatabaseManager;
import com.nu.media.models.Article;
import com.nu.media.models.ListArticle;
import com.nu.media.models.dao.DataAccess;
import com.nu.media.views.fragments.DetailFragment;
import com.nu.media.views.fragments.DetailFragment.PageChangeListener;
import com.nu.media.views.fragments.ViewArticleFragment.FavoriteListener;
import com.nu.media.views.fragments.DetailMenuFragment;
import com.nu.media.views.listeners.SelectedArticleListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailActivity extends BaseActivity implements SelectedArticleListener, PageChangeListener, FavoriteListener{

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
	}
	
	@Override
	protected void setLeftDrawer() {
		leftDrawer = new DetailMenuFragment();
		replaceFragment(R.id.left_drawer, leftDrawer);
	}
	
	@Override
	protected void setRightDrawer() {
		rightDrawer = new DetailFragment();
		replaceFragment(R.id.right_drawer, rightDrawer);
		
		Bundle extras = getIntent().getExtras();
		int pos = extras.getInt("PagePosition", 0);
		setPagePosition(pos);
	}
	
//	@Override
//	protected void setLeftDrawer() {
//		listViewArticle = (ListView) findViewById(R.id.left_drawer);
//		listViewArticle.setAdapter(getListDrawerAdapter());
//		listViewArticle.setOnItemClickListener(getItemClickListener());
//		listViewArticle.setCacheColorHint(0);
//		listViewArticle.setScrollingCacheEnabled(false);
//		listViewArticle.setScrollContainer(false);
//		listViewArticle.setFastScrollEnabled(true);
//		listViewArticle.setSmoothScrollbarEnabled(true);
//	}
//	
//	protected ListAdapter getListDrawerAdapter() {
//		return new CustomArrayAdapter(this, R.layout.main_content_item_list, ListArticle.getArticle());
//	}
//	
//	protected OnItemClickListener getItemClickListener() {
//		return new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
////				mContent.setText(Shakespeare.DIALOGUE[position]);
////				mActionBar.setTitle(Shakespeare.TITLES[position]);
////				rightDrawer.setText("Konten");
////				lListener.onClickMainMenu();
//				Adapter adpater = listViewArticle.getAdapter(); 
//				Article article = (Article) adpater.getItem(pos);
//				mActionBar.setTitle(article.getTitle());
//				mDrawerLayout.closeDrawer(listViewArticle);
////				Toast.makeText(getActivity(), "Not yet implemented", Toast.LENGTH_LONG).show();
//				
//			}
//		};
//	}

//	@Override
//	public void onListItemClick(List<Article> article) {
//		
//	}

	@Override
	public void onSelectedArticle(int pos) {
		mDrawerLayout.closeDrawer(leftDrawerLayout);
		setPagePosition(pos);
	}

	private void setPagePosition(int pos) {
		if (rightDrawer instanceof DetailFragment) {
			DetailFragment content = (DetailFragment) rightDrawer;
			content.setPage(pos);
		}else {
			toastIt("Not implemented yet");
		}
	}

	@Override
	public void onPageChange(int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFavorite(int position) {
		Article article = ListArticle.getArticle().get(position);
		Article savedArticle = dataAccess.getByColumnNameValue("TITLE", article.getTitle());
		if(savedArticle == null){
			dataAccess.create(article);
			article.setStatus(true);
			toastIt("article saved");
		}else {
			dataAccess.delete(article);
			article.setStatus(false);
//			updateFragment(article);
			toastIt("article deleted");
		}
	}

	private void updateFragment(Article article) {
		ListArticle.getArticle().remove(article);
		DetailFragment det = (DetailFragment) rightDrawer;
		DetailMenuFragment men = (DetailMenuFragment) leftDrawer;
		det.setNotify();
		men.setNotify();
	}

	
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		super.onCreateOptionsMenu(menu, inflater);
//	}
	
	
}
