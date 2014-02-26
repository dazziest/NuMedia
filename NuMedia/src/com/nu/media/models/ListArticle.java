package com.nu.media.models;

import java.util.List;

public class ListArticle {
	private static List<Article> article;
	
	public static void setArticle(List<Article> art){
		article = art;
	}
	
	public static List<Article> getArticle(){
		return article;
	}

}
