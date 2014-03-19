package com.nu.media.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.nu.media.R;
import com.nu.media.models.Article;
import com.nu.media.models.ListArticle;
import com.nu.media.views.adapters.CustomArrayAdapter;
import com.nu.media.views.listeners.SelectedArticleListener;
import com.nu.media.views.listeners.OnMenuClickListener;

public class DetailMenuFragment extends MainFragment {
	
	private ListView list;
	private SelectedArticleListener mListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.listview_layout_screen, container, false);
		list = (ListView)v.findViewById(R.id.listview_screen);
		showResult();
		return v;
	}
	
	@Override
	public void retrieveData() {
		showResult();
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try {
			mListener = (SelectedArticleListener) activity;
		} catch (Exception e) {
			toastIt("must implement onListItemClick");
		}
	}
	
	@Override
	protected void showResult() {
		list.setAdapter(new CustomArrayAdapter(getActivity(), R.layout.main_content_item_list, ListArticle.getArticle())); 
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
//				Article article = (Article) list.getSelectedItem();
				mListener.onSelectedArticle(pos);
				list.setSelection(pos);
			}
		});
	}
	
	public void setNotify(){
		CustomArrayAdapter not = (CustomArrayAdapter) list.getAdapter();
		not.notifyDataSetChanged();
	}
}
