package com.nu.media.views.fragments;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.nu.media.R;
import com.nu.media.models.ActivityItem;
import com.nu.media.models.Article;
import com.nu.media.models.dao.DataAccess;

public class ExerciseFragment extends BaseContentFragment {

//	private AQuery aq;
	private ListView listView;
	protected List<Article> listData;
	protected DataAccess<Article> dataAccess;
	protected View v;
	private ArrayList<ActivityItem> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		setActionBarTitle("Article");
		v = inflater.inflate(R.layout.main_content, container, false);
//		aq = new AQuery(getActivity(), v);
		listView = (ListView) v.findViewById(R.id.list);
		setListAdapter();
		return v;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
		} catch (Exception e) {
			toastIt("must implement onListItemClick");
		}
	}

	@Override
	public void retrieveData() {
		showProgressBar(getView());
		// String url = "http://www.masdevid.com/feeds/posts/default";
		String url = "http://feeds.feedburner.com/mboir";
		if (url.contains("feeds/posts/default")) {
		} else if (url.contains("feeds.feedburner.com")) {
		} else {
			toastIt("Invalid Blog URL");
			return;
		}
		listData = new ArrayList<Article>();
	}

	private void setListAdapter() {
		listView.setAdapter(getListAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				ActivityItem ai = list.get(pos);
				invokeIntent(ai);
//				lListener.onSelectedArticle(pos);
			}
		});
		hideProgressBar(v);
	}

	private ListAdapter getListAdapter() {
		int[] ids = getRestList();
		list = new ArrayList<ActivityItem>();
		String[] names = getResources().getStringArray(ids[0]);
		String[] values = getResources().getStringArray(ids[1]);

		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			String value = values[i];
			if (value.startsWith("http")) {
				list.add(new ActivityItem(null, name, value, null));
			} else {
				String[] vs = value.split(":");
				String meta = null;
				if (vs.length > 2) {
					meta = vs[2];
				}
				 list.add(makeActivity(vs[0], name, vs[1], meta));
			}

		}

		ArrayAdapter<ActivityItem> result = new ArrayAdapter<ActivityItem>(
				getActivity(), R.layout.list_item, list) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				if (convertView == null) {
					convertView = getActivity().getLayoutInflater().inflate(
							R.layout.list_item, null);
				}

				ActivityItem ai = (ActivityItem) getItem(position);
				AQuery aq = new AQuery(convertView);

				String text = ai.getName();
				String meta = ai.getMeta();

				if (meta != null) {
					text += "   <small><small><font color=\"red\">" + meta
							+ "</font></small></small>";
				}

				Spanned span = Html.fromHtml(text);

				aq.id(R.id.name).text(span);
				// aq.id(R.id.meta).text(ai.getMeta());

				return convertView;
			}
		};

		return result;
	}

	private ActivityItem makeActivity(String cls, String name, String type,
			String meta) {

		Class c = null;

		try {
			c = Class.forName(cls);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ActivityItem(c, name, type, meta);
	}

	private int[] getRestList() {
		// TODO Auto-generated method stub
		return new int[] { R.array.image_names, R.array.image_values };
	}

//	private void mock() {
//		for (int i = 0; i < 5; i++) {
//			Article art = new Article();
//			art.setDescription("Koneksi mu lemot bro, pake dummy dulu");
//			art.setTitle("Dummy Title " + i);
//			// art.setId(i);
//			listData.add(art);
//		}
//		hideProgressBar(getView());
//		showResult();
//		toastIt(NetworkHelper.getConnectionInfo(getActivity()));
//	}

	// private void addArticle() {
	// Article article = new Article();
	// // article.setId(indexId);
	// article.setDescription(currentDes);
	// article.setTitle(currentTitle);
	// article.setImgUrl(currentImgUrl);
	// listData.add(article);
	// }
	//
	// private void getAttributes(XmlPullParser xpp) {
	// indexId++;
	// int count = xpp.getAttributeCount();
	// for (int i = 0; i <count ; i++) {
	// if ("url".equals(xpp.getAttributeName(i))) {
	// currentImgUrl = xpp.getAttributeValue(i);
	// }
	// }
	// }

//	protected void showResult() {
//		syncListData();
//		ListArticle.setArticle(listData);
//		listView.setAdapter(new CustomArrayAdapter(getActivity(),
//				R.layout.main_content_item_list, listData));
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
//				ActivityItem ai = list.get(pos);
//				invokeIntent(ai);
////				lListener.onSelectedArticle(pos);
//			}
//		});
//	}
	
	private void invokeIntent(ActivityItem ai){
		
		Class<?> cls = ai.getActivityClass();
		String type = ai.getType();
		
		Intent intent = new Intent(getActivity(), cls);
		intent.putExtra("type", type);
		
		startActivity(intent);
	}

//	private void syncListData() {
//		for (Article article : listData) {
//			Article saved = dataAccess.getByColumnNameValue("TITLE",
//					article.getTitle());
//			if (saved != null) {
//				article.setStatus(true);
//			}
//		}
//	}

	@Override
	public void saveData() {

	}

	@Override
	public void initializeData(DataAccess<Article> data) {
		this.dataAccess = data;
	}
}
