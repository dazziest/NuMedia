/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nu.media.views.fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.actionbarsherlock.app.SherlockFragment;
import com.androidquery.AQuery;
import com.nu.media.R;
import com.nu.media.helpers.AqueryImageParser;
import com.nu.media.helpers.HtmlCleanAPI;
import com.nu.media.helpers.HtmlCleanAPI.PageContent;
import com.nu.media.helpers.StringUtil;
import com.nu.media.models.Article;
import com.nu.media.models.ListArticle;



/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
@SuppressLint("NewApi")
public class ViewArticleFragment extends SherlockFragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    
    private AQuery aq;
    private FavoriteListener fListener;

	private ImageView btnFavorite;
	private boolean isFavorite;

	private LinearLayout lineLayout;

	private Context mContext;
    
    public static ViewArticleFragment create(int pageNumber) {
//    	listArticle = listData;
        ViewArticleFragment fragment = new ViewArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewArticleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        aq = new AQuery(getActivity());
        mContext = getActivity().getApplicationContext();
    }
    
    @Override
    public void onAttach(Activity activity){
    	super.onAttach(activity);
    	try {
			fListener = (FavoriteListener) activity;
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        final Article article = ListArticle.getArticle().get(mPageNumber);
        isFavorite = article.getStatus();
        // Set the title view to show the page number.
        ((TextView) rootView.findViewById(R.id.judul)).setText(article.getTitle());
        btnFavorite = (ImageView) rootView.findViewById(R.id.favorite);
        btnFavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fListener.setFavorite(mPageNumber);
				isFavorite = article.getStatus();
				setFavorite();
			}
		});
        setFavorite();
        setView(rootView);
        return rootView;
    }
    
    protected void setFavorite() {
		if (isFavorite) {
			btnFavorite.setImageResource(R.drawable.ic_action_important);
		}else {
			btnFavorite.setImageResource(R.drawable.ic_action_not_important);
		}
	}

	private void setView(final ViewGroup rootView) {
    	Article article = ListArticle.getArticle().get(mPageNumber);
    	lineLayout = (LinearLayout) rootView.findViewById(R.id.lineLayout);
//    	AqueryImageParser p = new AqueryImageParser(textContent, getActivity(), R.drawable.ic_action_refresh);
//    	Spanned htmlSpan = Html.fromHtml(article.getDescription(), p, null);
    	HtmlCleanAPI api2 = new HtmlCleanAPI();
    	HtmlCleaner cleaner = new HtmlCleaner();
		TagNode node = null;
		PageContent pageContent= null;
		try {
			node = cleaner.clean(article.getDescription());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (node != null) {
			List<PageContent> list = api2.getPageList(node);
			for (PageContent page : list) {
				setPage(page);
			}
//			pageContent = api2.getPage(node, 1);
		}
//    	textContent.setText(htmlSpan);
//    	textContent.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	private void setPage(PageContent page) {
		List<HtmlCleanAPI.HtmlContent> hcList = page.getContentList();
		setAllComponent(hcList);
	}

	public static final int TEXT_SIZE = 17;
	public void setAllComponent(List<HtmlCleanAPI.HtmlContent> hcList){
		for(HtmlCleanAPI.HtmlContent hc: hcList){
			String tag = hc.getTag();
			String content =  hc.getContent();
			if(StringUtil.isEmpty(content)) continue;
			
			String contentMBlank = StringUtil.mergeBlank(content);
			if(StringUtil.isEmpty(contentMBlank)) continue;
			
			String contentRBlank = StringUtil.rmvEnter(StringUtil.trim(contentMBlank));
			if(StringUtil.isEmpty(contentRBlank)) continue;
			
			String contentRSpecial = StringUtil.rmvSpecial(contentRBlank);
			if(StringUtil.isEmpty(contentRSpecial)) continue;
			
			if(tag.equalsIgnoreCase("P")){ 
				final TextView tagPTV = new TextView(mContext);
//				tagPTV.setBackgroundResource(R.drawable.tag_p_drawable);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				lp.setMargins(2, 3, 2, 3); 
				tagPTV.setLayoutParams(lp);
				tagPTV.setPadding(3, 3, 3, 3);
				tagPTV.setPaddingRelative(3, 3, 3, 3);
				tagPTV.setMovementMethod(ScrollingMovementMethod.getInstance()) ;
				tagPTV.setTextColor(Color.BLACK);
				tagPTV.setText(contentRSpecial);
				tagPTV.setTextSize(TEXT_SIZE);
//				tagPTV.setOnLongClickListener(new View.OnLongClickListener() {
//					@Override
//					public boolean onLongClick(View v) {
//						openDialog(tagPTV.getText().toString());
//						return false;
//					}
//				});
				lineLayout.addView(tagPTV);
				
			}else if(tag.equalsIgnoreCase("pre")){
				final TextView tagPre = new TextView(mContext);
				tagPre.setBackgroundColor(Color.parseColor("#D2EADE"));
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				lp.setMargins(2, 2, 2, 2); 
				tagPre.setLayoutParams(lp);
				tagPre.setPadding(2, 2, 2, 2);
				tagPre.setPaddingRelative(2, 2, 2, 2);
				tagPre.setMovementMethod(ScrollingMovementMethod.getInstance()) ;
				tagPre.setTextColor(Color.BLACK);
//				String contentRSpecial = StringUtil.rmvSpecial(content);
				tagPre.setText(StringUtil.rmvSpecial(content));
				tagPre.setTextSize(TEXT_SIZE);
//				tagPre.setOnLongClickListener(new View.OnLongClickListener() {
//					@Override
//					public boolean onLongClick(View v) {
//						openDialog(tagPre.getText().toString());
//						return false;
//					}
//				});				
				lineLayout.addView(tagPre);
			}else if(tag.equalsIgnoreCase("dt") || tag.equalsIgnoreCase("H1") || tag.equalsIgnoreCase("H2") || tag.equalsIgnoreCase("H3")){//title
				TextView tagDtHTV = new TextView(mContext);
				tagDtHTV.setBackgroundColor(Color.DKGRAY);
				tagDtHTV.setTypeface(null,Typeface.BOLD);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				lp.setMargins(2, 0, 2, 0); 
				tagDtHTV.setLayoutParams(lp);
				tagDtHTV.setPadding(2, 2, 2, 2);
				tagDtHTV.setPaddingRelative(2, 2, 2, 2);
				tagDtHTV.setMovementMethod(ScrollingMovementMethod.getInstance()) ;
				tagDtHTV.setTextColor(Color.WHITE);
				tagDtHTV.setText(contentRSpecial);
				tagDtHTV.setTextSize(TEXT_SIZE);
				lineLayout.addView(tagDtHTV);
			}else if(tag.equalsIgnoreCase("dd")){
				final TextView tagDD = new TextView(mContext);
//				tagDD.setBackgroundResource(R.drawable.tag_p_drawable);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				lp.setMargins(2, 2, 2, 2); 
				tagDD.setLayoutParams(lp);
				tagDD.setPadding(3, 3, 3, 3);
				tagDD.setPaddingRelative(3, 3, 3, 3);
				tagDD.setMovementMethod(ScrollingMovementMethod.getInstance()) ;
				tagDD.setTextColor(Color.BLACK);
				tagDD.setText(contentRSpecial);
				tagDD.setTextSize(TEXT_SIZE);
//				tagDD.setOnLongClickListener(new View.OnLongClickListener() {
//					@Override
//					public boolean onLongClick(View v) {
//						openDialog(tagDD.getText().toString());
//						return false;
//					}
//				});					
				lineLayout.addView(tagDD);
			}else if(tag.equalsIgnoreCase("li")){
				final TextView tagLigTV = new TextView(mContext);
//				tagLigTV.setBackgroundResource(R.drawable.tag_p_drawable);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				lp.setMargins(2, 2, 2, 2); 
				tagLigTV.setLayoutParams(lp);
				tagLigTV.setPaddingRelative(2, 2, 2, 2);
				tagLigTV.setMovementMethod(ScrollingMovementMethod.getInstance()) ;
				tagLigTV.setTextColor(Color.BLACK);
				tagLigTV.setText(contentRSpecial);
				tagLigTV.setTextSize(TEXT_SIZE);
//				tagLigTV.setOnLongClickListener(new View.OnLongClickListener() {
//					@Override
//					public boolean onLongClick(View v) {
//						openDialog(tagLigTV.getText().toString());
//						return false;
//					}
//				});					
				lineLayout.addView(tagLigTV);
			}else if(tag.equalsIgnoreCase("img")){
//				final TextView imgTV = new TextView(mContext);
//				imgTV.setBackgroundResource(R.drawable.tag_p_drawable);
//				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
//				lp.setMargins(2, 2, 2, 2); 
//				imgTV.setLayoutParams(lp);
//				imgTV.setPaddingRelative(2, 2, 2, 2);
//				imgTV.setMovementMethod(ScrollingMovementMethod.getInstance()) ;
//				imgTV.setTextColor(Color.BLACK);
//				Spanned data = Html.fromHtml(content);
//				imgTV.setText(data);
//				imgTV.setTextSize(TEXT_SIZE);
//				lineLayout.addView(imgTV);
				
				ImageView imageView = new ImageView(mContext);
				LinearLayout.LayoutParams imgLP = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				imageView.setLayoutParams(imgLP);
				aq.id(imageView).image(content);
//				Bitmap localBt = getLoacalBitmap(baseFolder + File.separator +  content);
//				imageView.setImageBitmap(localBt);
				lineLayout.addView(imageView);
			}
		}
	}
	
//	public void openDialog(String content){
//		CustomDialog cusDialog = new CustomDialog(activity,R.style.custom_dialog_style,content);
//		Window wd = cusDialog.getWindow();
//		WindowManager.LayoutParams lp = wd.getAttributes();
//		lp.alpha = 1.0f;
//		wd.setAttributes(lp);
//		cusDialog.show();
//	}
	
	public Bitmap getLoacalBitmap(String localUrl) {
		try {
			FileInputStream fis = new FileInputStream(localUrl);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
    
    public interface FavoriteListener{
    	public void setFavorite(int position);
    }
}
