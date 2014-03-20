package com.nu.media.views.activities;

import com.nu.media.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.RenderPriority;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends Activity {

	private WebView webView;
	 
	public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_webview);
 
	   webView = (WebView) findViewById(R.id.webview);
	   webView.getSettings().setJavaScriptEnabled(true);

	   String customHtml = getIntent().getExtras().getString("web_source");
	   webView.loadData(customHtml, "text/html", "UTF-8");
	   webView.getSettings().setRenderPriority(RenderPriority.HIGH);
	   webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

	   overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
	}
}
