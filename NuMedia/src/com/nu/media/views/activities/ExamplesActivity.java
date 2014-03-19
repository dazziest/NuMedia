package com.nu.media.views.activities;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;
import com.nu.media.R;

public class ExamplesActivity extends FragmentActivity {

	private AQuery aq;
	private String type;
	private static Map<String, String> titleMap;
	
	protected int getContainer(){
		return R.layout.source_activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		
		aq = new AQuery(this);
		
		type = getIntent().getStringExtra("type");
		
		setContentView(getContainer());
		
		String source = getSource();
		
		String title = getSourceTitle();
		
		aq.id(R.id.name).text(title);
		aq.id(R.id.code).text(source);
		aq.id(R.id.go_run).clicked(this, "runSource");
		
		if("image_access_file".equals(type) || "image_access_memory".equals(type)){
//			image_simple();
		}else if("image_file".equals(type) || "image_file_custom".equals(type)){
			aq.cache("http://farm6.static.flickr.com/5035/5802797131_a729dac808_b.jpg", 0);
		}else if("image_preload".equals(type)){
			String small = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_s.jpg";		
			aq.cache(small, 0);
			aq.id(R.id.image).width(250).height(250).image(0).visible();
		}else if("image_ratio".equals(type)){
			aq.id(R.id.image).width(250);
		}else if("image_pre_cache".equals(type)){
//			pre_cache();
		}else if("image_button".equals(type)){
			aq.id(R.id.button).visible();
			aq.id(R.id.go_run).gone();
//			image_button();
		}else if("image_send".equals(type)){
			aq.cache("http://www.androidquery.com/z/images/vikispot/android-w.png", 0);
		}
	}
	
	private String getSource(){
		
		String source = "Failed to load source.";
		
		try{
			String name = getIntent().getStringExtra("type");
		
			InputStream is = getClassLoader().getResourceAsStream("com/androidquery/test/source/" + name);
		
			if(is != null){
				source = new String(AQUtility.toBytes(is));
			}
			
			//AQUtility.debug(name, source);
			
		}catch(Exception e){
			//e.printStackTrace();
		}
		
		return source;
		
	}
	
	private String getSourceTitle(){
		
		String name = getIntent().getStringExtra("type");
		
		if(titleMap == null){
			titleMap = loadMap();
		}
		
		return titleMap.get(name);
	}
	
	private Map<String, String> loadMap(){
		
		String[] names = getResources().getStringArray(R.array.source_keys);
		String[] code = getResources().getStringArray(R.array.source_title);
		
		Map<String, String> result = new HashMap<String, String>();
		
		for(int i = 0; i < names.length; i++){
			result.put(names[i], code[i]);
		}
		
		return result;
	}
}
