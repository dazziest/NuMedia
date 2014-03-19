package com.nu.media.views.activities;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;
import com.nu.media.R;

public class ExamplesActivity extends FragmentActivity {

	private AQuery aq;
	private String type;
	private static Map<String, String> titleMap;
	private String htmlString;
	
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
		aq.id(R.id.go_run).clicked(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (htmlString != null) {
					loadHtml();
				}else {
					(Toast.makeText(ExamplesActivity.this, "Error load source", Toast.LENGTH_LONG)).show();
				}
			}

			private void loadHtml() {
				Intent intent = new Intent(ExamplesActivity.this, WebViewActivity.class);
				intent.putExtra("web_source", htmlString);
				startActivity(intent);
			}
		});

		overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
	}
	
	private String getSource(){
		
		String source = "Failed to load source.";
		
		try{
			String name = getIntent().getStringExtra("type");
		
			InputStream is = getClassLoader().getResourceAsStream("com/nu/media/sources/" + name);
		
			if(is != null){
				source = new String(AQUtility.toBytes(is));
			}
			
			//AQUtility.debug(name, source);
			
		}catch(Exception e){
			//e.printStackTrace();
		}
		
		this.htmlString = source;		
		return source;
		
	}
	
	private void runSource(){
		
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
