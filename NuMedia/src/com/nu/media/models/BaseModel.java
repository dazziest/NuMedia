package com.nu.media.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.text.format.DateFormat;
/**
 * Base Model
 *
 */
public class BaseModel {
	protected final String DATE_FORMAT = "dd-MM-yyyy";
	protected final String TIME_FORMAT = "hh:mm";
	
	/**
	 * Convert Date to String
	 * Return string empty when date = null
	 * Return date base on date format when not null
	 * @param date
	 * @return
	 */
	protected String convertDateToString(Date date) {
		if (date == null) {
			return "";
		}else {
			return DateFormat.format(DATE_FORMAT, date).toString();
		}
	}
	
	/**
	 * Convert String to Date
	 * Parse dateString using Simple Date Format based on Date Format
	 * @param dateString
	 * @return
	 */
	protected Date convertStringToDate(String dateString){
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());	 
		try {
			if (isInvalid(dateString)) {
				return null;
			}else {
				dateString = formatDate(dateString);
				Date date = formatter.parse(dateString);
				return date;
			}			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Ensure date string is in correct date format
	 * @param dateString
	 * @return
	 */
	private String formatDate(String dateString) {
		String newDate = dateString.replace("/", "-");
		return newDate;
	}

	/**
	 * Convert Time to String
	 * @param date
	 * @return string time based on Time Format
	 */
	protected String convertTimeToString(Date date) {
		if (date == null) {
			return "";
		}else {
			return DateFormat.format(TIME_FORMAT, date).toString();
		}
	}
	
	/**
	 * Convert String to Time
	 * @param dateString
	 * @return Parse time using Simple Date Format based on Time Format
	 */
	protected Date convertStringToTime(String dateString){
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());	 
		try {
			if (isInvalid(dateString)) {
				return null;
			}else {
				Date date = formatter.parse(dateString);
				return date;
			}			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Parse String to Integer
	 * @param num
	 * @return
	 */
	protected Integer toInteger(String num) {
		if (isInvalid(num)) {
			return 0;
		}else {
			return Integer.parseInt(num);
		}
	}
	
	/**
	 * Check if sent string data is invalid
	 * @param data
	 * @return
	 */
	private boolean isInvalid(String data) {
		return (data == null || data.equalsIgnoreCase("null") || data.isEmpty());
	}

	/**
	 * Parse String to Double
	 * @param num
	 * @return
	 */
	protected Double toDouble(String num) {
		if (isInvalid(num)) {
			return 0.0;
		}else {
			return Double.parseDouble(num);
		}
	}
	
	/**
	 * Parse String to Boolean
	 * @param bool
	 * @return
	 */
	protected Boolean toBoolean(String bool){
		if (bool.contentEquals("false") || bool.equalsIgnoreCase("true")) {
			return Boolean.parseBoolean(bool);
		}else {
			return null;
		}
	}
	
	/**
	 * Parse integer to String
	 * @param num
	 * @return
	 */
	protected String toString(int num) {
		return Integer.toString(num);
	}
	
	/**
	 * parse boolean to String
	 * @param bool
	 * @return
	 */
	protected String toString(boolean bool) {
		return Boolean.toString(bool);
	}
	
	/**
	 * Parse Double to String
	 * @param num
	 * @return
	 */
	protected String toString(double num) {
		return Double.toString(num);
	}
	
	/**
	 * Get Value
	 * @param data
	 * @return string empty when data = null
	 */
	protected String getValue(String data) {
		if (isInvalid(data)) {
			return "";
		}else {
			return data;
		}
	}
	
	/**
	 * Get Integer Value
	 * @param data
	 * @return 0 when data = null
	 */
	protected Integer getIntValue(Integer data) {
		if (data == null || data.equals("null")) {
			return 0;
		}
		else {
			return data;
		}
	}
	
	/**
	 * Get Double Value
	 * @param data
	 * @return 0 when data = null
	 */
	protected Double getDubleValue(Double data){
		if (data == null || data.equals("null")) {
			return 0.0;
		}
		else {
			return data;
		}
	}
}
