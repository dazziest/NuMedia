package com.nu.media.views.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.nu.media.R;
import com.nu.media.models.Article;

public class CustomArrayAdapter extends ArrayAdapter<Article> {

	private List<Article> listMenuItems;
	private Context context;
	private int rowResourceId;
//	private ImageLoader imageLoader;
	private AQuery listaq;

	public CustomArrayAdapter(Context context, int textViewResourceId, List<Article> menuItemList) {
		super(context, textViewResourceId, menuItemList);
		this.context = context;
        this.listMenuItems = menuItemList;
        this.rowResourceId = textViewResourceId;
        listaq = new AQuery(context);
//        imageLoader=new ImageLoader(context.getApplicationContext());
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = inflater.inflate(rowResourceId, parent, false);
		}
		AQuery aq = listaq.recycle(convertView);
		Article article = listMenuItems.get(position);
		TextView text=(TextView)convertView.findViewById(R.id.textTitle);
	    TextView textDesc=(TextView)convertView.findViewById(R.id.textDesc);
//	    ImageView image=(ImageView)convertView.findViewById(R.id.image);
	    text.setText(article.getTitle());
	    textDesc.setText(Html.fromHtml(article.getDescription()).toString());
	    Bitmap placeholder = aq.getCachedImage(R.drawable.ic_action_refresh);
	    String url = article.getImgUrl();
		if (aq.shouldDelay(position, convertView, parent, url)) {
			aq.id(R.id.image).image(placeholder,0.75f);
		} else {
			aq.id(R.id.image).image(url,true, true, 0, R.drawable.ic_action_refresh, placeholder, 0, 0.75f);
		}
//		if (url == null || url.isEmpty()) {
//			image.setImageResource(R.drawable.ic_action_refresh);
//		}
		return convertView;
	}
}
