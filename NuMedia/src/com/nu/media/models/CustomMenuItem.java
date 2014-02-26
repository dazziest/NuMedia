package com.nu.media.models;

import android.support.v4.app.Fragment;

/**
 * 
 * Menu Item Model
 *
 */
public class CustomMenuItem extends BaseModel{
	private String name;
	private Class<? extends Fragment> cls;
	private String patientName;
	private String mrno;
	private int icon;
	public CustomMenuItem(String name, Class<? extends Fragment> cls, int icon) {
		this.name = name;
		this.cls = cls;
		this.icon = icon;
	}
	
	public CustomMenuItem(String patientName, String mrno){
		this.patientName = patientName;
		this.mrno = mrno;
	}
	
	public CustomMenuItem(String name){
		this.name = name;
	}
	
	public String getPatientName() {
		return this.patientName;
	}
	
	public String getMrno() {
		return this.mrno;
	}
	
	public String getMenuName(){
		return this.name;
	}
	public int getIcon(){
		return this.icon;
	}
	
	public Class<? extends Fragment> getClassName(){
		return this.cls;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
