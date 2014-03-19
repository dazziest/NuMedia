package com.nu.media.helpers;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

/**
 * Database Manager is used to get helper from OpenHelperManager based on open helper class.
 * The helper can be kept open across all activities in app with the same SQLite database connection reused by all threads.
 * So it could avoid using multiple connections to the same database.
 * 
 * @author muhammad_a
 *
 * @param <H>
 */
public class DatabaseManager<H extends OrmLiteSqliteOpenHelper> {
	
	private H helper;
	
	@SuppressWarnings("unchecked")
	public H getHelper(Context context)
	{
		if(helper == null)
		{
			helper = (H) OpenHelperManager.getHelper(context, DatabaseHelper.class);
		}
		return helper;
	}
	
	public void releaseHelper(H helper)
	{
		if (helper != null) {
			OpenHelperManager.releaseHelper();
			helper = null;
		}
	}
	
}
