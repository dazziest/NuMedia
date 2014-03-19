package com.nu.media.views.fragments;

import java.util.ArrayList;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.nu.media.R;
import com.nu.media.helpers.ActionBarHelper;
import com.nu.media.models.Article;
import com.nu.media.models.CustomMenuItem;
import com.nu.media.models.dao.DataAccess;
import com.nu.media.views.adapters.CustomMenuItemAdapter;
import com.nu.media.views.listeners.OnMenuClickListener;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;

public class MainMenuFragment extends BaseContentFragment {

	private ListView listViewMenu;
	private OnMenuClickListener lListener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater = ((SherlockFragmentActivity)getActivity()).getSupportMenuInflater();
		inflater.inflate(R.menu.refresh, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.listview_layout_screen, container, false);
		listViewMenu = (ListView) view.findViewById(R.id.listview_screen);
		listViewMenu.setAdapter(getListDrawerAdapter());
		listViewMenu.setOnItemClickListener(getItemClickListener());
		listViewMenu.setCacheColorHint(0);
		listViewMenu.setScrollingCacheEnabled(false);
		listViewMenu.setScrollContainer(false);
		listViewMenu.setFastScrollEnabled(true);
		listViewMenu.setSmoothScrollbarEnabled(true);
		return view;
	}
	
	
	protected ListAdapter getListDrawerAdapter() {
		ArrayList<CustomMenuItem> menuItem = new ArrayList<CustomMenuItem>();
		menuItem.add(new CustomMenuItem("Article", MainFragment.class, R.drawable.ic_action_view_as_list));
		menuItem.add(new CustomMenuItem("Favorite", MainFavoriteFragment.class, R.drawable.ic_action_important));
		menuItem.add(new CustomMenuItem("Quiz", MainQuizFragment.class, R.drawable.ic_action_edit));
		return new CustomMenuItemAdapter(getActivity(), R.layout.list_menu_items, R.id.menu_text, menuItem);
	}
	
	protected OnItemClickListener getItemClickListener() {
		return new OnItemClickListener() {
			private int currentMenuPosition;

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
//				mContent.setText(Shakespeare.DIALOGUE[position]);
//				mActionBar.setTitle(Shakespeare.TITLES[position]);
//				rightDrawer.setText("Konten");
//				lListener.onClickMainMenu();
//				mDrawerLayout.closeDrawer(listViewMenu);
				CustomMenuItem item = (CustomMenuItem) listViewMenu.getItemAtPosition(pos);
				if(item.getClassName() != null) {
					lListener.onSelectFragmentMenu(item.getClassName(), pos);
				}else {
					Toast.makeText(getActivity(), "Not yet implemented", Toast.LENGTH_LONG).show();
				}
				currentMenuPosition = pos;
				listViewMenu.setItemChecked(currentMenuPosition, true);
//				mActionBar.setTitle(item);
			}
		};
	}
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try {
			lListener = (OnMenuClickListener) activity;
		} catch (Exception e) {
			toastIt("must implement onListItemClick");
		}
	}
	
	/**
	 * This list item click listener implements very simple view switching by
	 * changing the primary content text. The drawer is closed when a selection
	 * is made.
	 */
//	protected class DrawerItemClickListener implements ListView.OnItemClickListener {
//		private int currentMenuPosition;
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
////			mContent.setText(Shakespeare.DIALOGUE[position]);
////			mActionBar.setTitle(Shakespeare.TITLES[position]);
////			rightDrawer.setText("Konten");
////			lListener.onClickMainMenu();
//			mDrawerLayout.closeDrawer(leftDrawer);
//			try {
//				CustomMenuItem item = (CustomMenuItem) leftDrawer.getItemAtPosition(pos);
//				if(item.getClassName() == MainFragment.class) {
//					lListener.onSelectFragment(item.getClassName().newInstance(), pos);
//				}else {
//					Toast.makeText(getActivity(), "Not yet implemented", Toast.LENGTH_LONG).show();
//				}
//				currentMenuPosition = pos;
//				leftDrawer.setItemChecked(currentMenuPosition, true);
//			} catch (java.lang.InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			mActionBar.setTitle("Title");
//		}
//	}
	
	@Override
	public void retrieveData() {
//		showProgressBar(getView());
//		String url = "http://www.masdevid.com/feeds/posts/default";
//		indexId = 0;
//		listData = new ArrayList<Article>();
//        currentTitle = null;
//        currentImgUrl = null;
//        currentDes = null;
//		aq.ajax(url, XmlPullParser.class, new AjaxCallback<XmlPullParser>(){
//			
//			@Override
//			public void callback(String url, XmlPullParser xpp, AjaxStatus status) {
//		        parseXML(xpp);
//			}
//		});
	}
	
//	protected void parseXML(XmlPullParser xpp) {
//		try{
//	        int eventType = xpp.getEventType();
//	        while(eventType != XmlPullParser.END_DOCUMENT) {
//	        	if(eventType == XmlPullParser.START_TAG){
////	        		String namespace = xpp.getNamespacePrefix(0);
//	                String tag = xpp.getName();
//	                if ("entry".equals(tag)) {
//						currentTitle = "";
//						currentDes = "";
//						currentImgUrl = "";
//					}else if("title".equals(tag)){
//	                 	currentTitle = xpp.nextText();
//	                }else if("content".equals(tag)){
//	                 	currentDes = xpp.nextText();
//	                }else if ("thumbnail".equals(tag)) {
//	                 	getAttributes(xpp);
//					}else if ("total".equals(tag)) {
//						addArticle();
//					}
//	        	}
//	        	eventType = xpp.next();
//	        }
//			hideProgressBar(getView());
//	        showResult();
//        }catch(Exception e){
//            AQUtility.report(e);
//            showErrorPage(getView(), "Error " + e.getMessage());
//            mock();
//        }
//	}
//
//	private void mock() {
//		for (int i = 0; i < 5; i++) {
//			Article art = new Article();
//			art.setDescription("Koneksi mu lemot bro, pake dummy dulu");
//			art.setTitle("Dummy Title "+i);
//			art.setId(i);
//			listData.add(art);
//		}
//		hideProgressBar(getView());
//		showResult();
//		toastIt(NetworkHelper.getConnectionInfo(getActivity()));
//	}
//
//	private void addArticle() {
//		Article article = new Article();
//		article.setId(indexId);
//		article.setDescription(currentDes);
//		article.setTitle(currentTitle);
//		article.setImgUrl(currentImgUrl);
//     	listData.add(article);
//	}
//
//	private void getAttributes(XmlPullParser xpp) {
//		indexId++;
//		int count = xpp.getAttributeCount();
//		for (int i = 0; i <count ; i++) {
//			if ("url".equals(xpp.getAttributeName(i))) {
//				currentImgUrl = xpp.getAttributeValue(i);
//			}
//		}
//	}

//	private void showResult() {
////		list.setAdapter(new CustomArrayAdapter(getActivity(), R.layout.main_content_item_list, listData)); 
////		list.setOnItemClickListener(new OnItemClickListener() {
////
////			@Override
////			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
//////				Article article = (Article) list.getSelectedItem();
////				lListener.onListItemClick(listData);
////			}
////		});
//		listViewMenu.setAdapter(new CustomArrayAdapter(this.getActivity(), R.layout.main_content_item_list, listData));
////		leftDrawer.setOnItemClickListener(new DrawerItemClickListener());
//		listViewMenu.setCacheColorHint(0);
//		listViewMenu.setScrollingCacheEnabled(false);
//		listViewMenu.setScrollContainer(false);
//		listViewMenu.setFastScrollEnabled(true);
//		listViewMenu.setSmoothScrollbarEnabled(true);
//	}
	@Override
	public void saveData() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initializeData(DataAccess<Article> data) {
		// TODO Auto-generated method stub
		
	}

	
}
