package com.nu.media.views.adapters;

import java.util.List;

import com.nu.media.R;
import com.nu.media.models.CustomMenuItem;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomMenuItemAdapter extends ArrayAdapter<CustomMenuItem> {

	private List<CustomMenuItem> listMenuItems;
//	private int rowResourceId;
	private Context context;
//	private ImageLoader imageLoader;

	public CustomMenuItemAdapter(Context context, int textViewResourceId, int menuText, List<CustomMenuItem> menuItemList) {
		super(context, textViewResourceId, menuItemList);
		this.context = context;
        this.listMenuItems = menuItemList;
//        imageLoader=new ImageLoader(context.getApplicationContext());
//        this.rowResourceId = textViewResourceId;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		CustomMenuItem menu = listMenuItems.get(position);
		CustomMenuItem menuItem = listMenuItems.get(position);
		convertView = inflater.inflate(R.layout.list_menu_items, parent, false);
		TextView txt = (TextView)convertView.findViewById(R.id.menu_text);
		ImageView img = (ImageView)convertView.findViewById(R.id.menu_icon);
		txt.setText(menu.getMenuName());
		img.setImageResource(menu.getIcon());
		return convertView;
		
	}
}
