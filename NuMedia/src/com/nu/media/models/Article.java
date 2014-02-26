package com.nu.media.models;

public class Article extends BaseModel {
	private int id;
	private String title;
	private String description;
	private String imageUrl;
	
	public void setId(int id){
		this.id = id;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public void setDescription(String desc){
		this.description = desc;
	}
	public void setImgUrl(String img){
		this.imageUrl = img;
	}

	public int getId(){
		return id;
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
}
