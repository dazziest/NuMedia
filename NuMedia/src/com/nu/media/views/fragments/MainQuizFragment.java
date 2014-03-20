package com.nu.media.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;

import com.nu.media.R;
import com.nu.media.models.Article;
import com.nu.media.models.dao.DataAccess;

public class MainQuizFragment extends BaseContentFragment {

	private WebView webView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.activity_webview, container, false);
		
		webView = (WebView) v.findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
	 	webView.loadUrl("file:///android_asset/www/jQuizzy.htm");
	 	webView.getSettings().setRenderPriority(RenderPriority.HIGH);
	 	webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
	 	getActivity().setTitle("NuMedia - Quiz");
//	 	webView.loadUrl("http://demos.jquerymobile.com/1.2.0/docs/pages/page-transitions.html");
	 	
	 	return v;
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
	public void initializeData(DataAccess<Article> data) {
		// TODO Auto-generated method stub
		
	}

}
