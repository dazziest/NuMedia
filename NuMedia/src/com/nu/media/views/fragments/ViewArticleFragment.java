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

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.androidquery.AQuery;
import com.nu.media.R;
import com.nu.media.helpers.AqueryImageParser;
import com.nu.media.models.Article;
import com.nu.media.models.ListArticle;



/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
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
//	private static List<Article> listArticle;
    
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
    	TextView textContent = (TextView) rootView.findViewById(R.id.isi);
    	AqueryImageParser p = new AqueryImageParser(textContent, getActivity(), R.drawable.ic_action_refresh);
    	Spanned htmlSpan = Html.fromHtml(article.getDescription(), p, null);
    	textContent.setText(htmlSpan);
    	textContent.setMovementMethod(LinkMovementMethod.getInstance());
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
