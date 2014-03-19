package com.nu.media.views.fragments;

import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.nu.media.R;
import com.nu.media.helpers.ActionBarHelper;
import com.nu.media.helpers.NetworkHelper;
import com.nu.media.models.Article;
import com.nu.media.models.CustomMenuItem;
import com.nu.media.models.ListArticle;
import com.nu.media.models.dao.DataAccess;
import com.nu.media.views.adapters.CustomArrayAdapter;
import com.nu.media.views.listeners.SelectedArticleListener;
import com.nu.media.views.listeners.OnMenuClickListener;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;

public class MainFragment extends BaseContentFragment {

	private AQuery aq;
	private ListView list;
//	private LazyAdapter adapter;
	private String currentImgUrl;
	private String currentTitle;
	private String currentDes;
	private int indexId;
	private List<CustomMenuItem> menuItem;
	protected List<Article> listData;
	private SelectedArticleListener lListener;
	private OnMenuClickListener mListener;
	private boolean isFeedBurner;
	
	protected DataAccess<Article> dataAccess;
	protected View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		setActionBarTitle("Article");
		v = inflater.inflate(R.layout.main_content, container, false);
		aq = new AQuery(getActivity(), v);
		list=(ListView)v.findViewById(R.id.list);
		retrieveData();
		return v;
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try {
			lListener = (SelectedArticleListener) activity;
		} catch (Exception e) {
			toastIt("must implement onListItemClick");
		}
	}
	
	@Override
	public void retrieveData() {
		showProgressBar(getView());
//		String url = "http://www.masdevid.com/feeds/posts/default";
		String url = "http://feeds.feedburner.com/mboir";
		if (url.contains("feeds/posts/default")) {
			isFeedBurner = false;
		} else if(url.contains("feeds.feedburner.com")) {
			isFeedBurner = true;
		}else {
			toastIt("Invalid Blog URL");
			return;
		}
		indexId = 0;
		listData = new ArrayList<Article>();
        currentTitle = null;
        currentImgUrl = null;
        currentDes = null;
		aq.ajax(url, XmlPullParser.class, 0, new AjaxCallback<XmlPullParser>(){
			
			@Override
			public void callback(String url, XmlPullParser xpp, AjaxStatus status) {
				parseXML(xpp);
			}
		});
	}
	
	protected void parseXML(XmlPullParser xpp) {
		if (xpp != null) {
			if (isFeedBurner) {
				parseFeedBurnerXML(xpp);
			} else {
				parseAtomXML(xpp);
			}
		}else {
			showErrorPage(getView(), "Failed download content");
            mock();
		}
	}

	private void parseAtomXML(XmlPullParser xpp) {
		try{
	        int eventType = xpp.getEventType();
	        while(eventType != XmlPullParser.END_DOCUMENT) {
	        	if(eventType == XmlPullParser.START_TAG){
//	        		String namespace = xpp.getNamespacePrefix(0);
	                String tag = xpp.getName();
	                if ("entry".equals(tag)) {
						currentTitle = "";
						currentDes = "";
						currentImgUrl = "";
					}else if("title".equals(tag)){
	                 	currentTitle = xpp.nextText();
	                }else if("content".equals(tag)){
	                 	currentDes = xpp.nextText();
	                }else if ("thumbnail".equals(tag)) {
	                 	getAttributes(xpp);
					}else if ("total".equals(tag)) {
						addArticle();
					}
	        	}
	        	eventType = xpp.next();
	        }
			hideProgressBar(getView());
	        showResult();
        }catch(Exception e){
            AQUtility.report(e);
            showErrorPage(getView(), "Failed parsing content");
            mock();
        }
	}

	private void parseFeedBurnerXML(XmlPullParser xpp) {
		try{
	        int eventType = xpp.getEventType();
	        while(eventType != XmlPullParser.END_DOCUMENT) {
	        	if(eventType == XmlPullParser.START_TAG){
//	        		String namespace = xpp.getNamespacePrefix(0);
	                String tag = xpp.getName();
	                if ("item".equals(tag)) {
						currentTitle = "";
						currentDes = "";
						currentImgUrl = "";
					}else if("title".equals(tag)){
	                 	currentTitle = xpp.nextText();
	                }else if("description".equals(tag)){
	                 	currentDes = xpp.nextText();
	                }else if ("thumbnail".equals(tag)) {
	                 	getAttributes(xpp);
					}else if ("total".equals(tag)) {
						addArticle();
					}
	        	}
	        	eventType = xpp.next();
	        }
			hideProgressBar(getView());
	        showResult();
        }catch(Exception e){
            AQUtility.report(e);
            showErrorPage(getView(), "Failed parsing content");
            mock();
        }
	}

	private void mock() {
		for (int i = 0; i < 5; i++) {
			Article art = new Article();
			art.setDescription("Koneksi mu lemot bro, pake dummy dulu");
			art.setTitle("Dummy Title "+i);
//			art.setId(i);
			listData.add(art);
		}
		hideProgressBar(getView());
		showResult();
		toastIt(NetworkHelper.getConnectionInfo(getActivity()));
	}

	private void addArticle() {
		Article article = new Article();
//		article.setId(indexId);
		article.setDescription(currentDes);
		article.setTitle(currentTitle);
		article.setImgUrl(currentImgUrl);
     	listData.add(article);
	}

	private void getAttributes(XmlPullParser xpp) {
		indexId++;
		int count = xpp.getAttributeCount();
		for (int i = 0; i <count ; i++) {
			if ("url".equals(xpp.getAttributeName(i))) {
				currentImgUrl = xpp.getAttributeValue(i);
			}
		}
	}

	protected void showResult() {
		syncListData();
		ListArticle.setArticle(listData);
		list.setAdapter(new CustomArrayAdapter(getActivity(), R.layout.main_content_item_list, listData)); 
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				lListener.onSelectedArticle(pos);
			}
		});
	}
	private void syncListData() {
		for (Article article : listData) {
			Article saved = dataAccess.getByColumnNameValue("TITLE", article.getTitle());
			if (saved != null) {
				article.setStatus(true);
			}
		}
	}

	@Override
	public void saveData() {
		
	}

	@Override
	public void initializeData(DataAccess<Article> data) {
		this.dataAccess = data;
	}
}
