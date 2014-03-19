package com.nu.media.helpers;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * A database open helper class to help manage when the application needs to create or upgrade its database
 * 
 * @author muhammad_a
 *
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String DATABASE_NAME = "nu.db";
	private static final int DATABASE_VERSION = 1;	
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	/**
	 * Create database on runtime based on class model
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
//		try {
//			TableUtils.createTable(connectionSource, Patient.class);
//		} catch (SQLException e) {
//			Log.e(DatabaseHelper.class.getName(), "Can't create databases", e);
//		}
	}

	/**
	 * Drop and recreate database on runtime based on class model
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//		try {
//			TableUtils.dropTable(connectionSource, Patient.class, true);
//			onCreate(db);
//		} catch (SQLException e) {
//			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
//			throw new RuntimeException(e);
//		}
	}

	
	@Override
	public void close() {
		super.close();
	}
	/**
	 * Clear all table content based on Class model
	 * @param cls
	 */
	public void clearTable(Class<?> cls){
		try {
			TableUtils.clearTable(connectionSource, cls);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
