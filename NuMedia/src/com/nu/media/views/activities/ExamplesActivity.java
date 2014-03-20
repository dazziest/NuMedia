package com.nu.media.views.activities;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;
import com.nu.media.R;

public class ExamplesActivity extends FragmentActivity {

	private AQuery aq;
	private String type;
//	private Map<String, String> titleMap;
	private String htmlString;
	private EditText edtCode;
	
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
		
		setTitle(getFragmentTitle());
		
		aq.id(R.id.name).text(title);
		edtCode = aq.id(R.id.code).getEditText();
		edtCode.setText(source);
		htmlString = source;
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
				htmlString = edtCode.getText().toString();
				Intent intent = new Intent(ExamplesActivity.this, WebViewActivity.class);
				intent.putExtra("web_source", htmlString);
				startActivity(intent);
			}
		});
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
				
		return source;
		
	}
	
	private void resetSource(){
		this.htmlString = getSource();
		aq.id(R.id.code).getEditText().setText(htmlString);
	}
	
	private String getSourceTitle(){
		
		String name = getIntent().getStringExtra("type");
		Map<String, String> titleMap = loadMap(R.array.source_keys, R.array.source_title);
		
		return titleMap.get(name);
	}
	
	private String getFragmentTitle(){
		
		String name = getIntent().getStringExtra("type");
		Map<String, String> titleMap = loadMap(R.array.source_keys, R.array.image_names);
		
		return titleMap.get(name);
	}
	
	private Map<String, String> loadMap(int key, int value){
		
		String[] names = getResources().getStringArray(key);
		String[] code = getResources().getStringArray(value);
		
		Map<String, String> result = new HashMap<String, String>();
		
		for(int i = 0; i < names.length; i++){
			result.put(names[i], code[i]);
		}
		
		return result;
	}
	
	/***
	 * create action bar menu on top from menu layout
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.reset, menu);		
		return true;
	}
	
	/***
	 * Called when button in action bar is clicked
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_reset: {
			this.resetSource();
			return true;
		}default:
			return super.onOptionsItemSelected(item);
		}
	}
}
