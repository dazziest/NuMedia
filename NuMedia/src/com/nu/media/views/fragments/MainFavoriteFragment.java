package com.nu.media.views.fragments;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nu.media.models.Article;

public class MainFavoriteFragment extends MainFragment {
	
//	private List<Article> listFavorite;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setActionBarTitle("Favorite");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void retrieveData(){
		listData = dataAccess.getAll();
		if (listData.isEmpty()) {
			showErrorPage(v, "No favorite article found");
		} else {
			hideProgressBar(v);
			showResult();
		}
	}

}
