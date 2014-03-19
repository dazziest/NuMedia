package com.nu.media.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ARTICLES")
public class Article extends BaseModel {
//	private int id;
	
	@DatabaseField(id = true, columnName = "TITLE")
	private String title;
	@DatabaseField(columnName = "DESC")
	private String description;
	@DatabaseField(columnName = "IMG_URL")
	private String imageUrl;
	
	private boolean isFavorite;
	
	public void setTitle(String title){
		this.title = title;
	}
	public void setDescription(String desc){
		this.description = desc;
	}
	public void setImgUrl(String img){
		this.imageUrl = img;
	}
	
	public void setStatus(boolean status){
		this.isFavorite = status;
	}

	public String getDescription(){
		return description;
	}
	public String getTitle(){
		return title;
	}
	public String getImgUrl(){
		return imageUrl;
	}
	public boolean getStatus(){
		return isFavorite;
	}
}
