package com.nu.media.models.dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.nu.media.helpers.DatabaseHelper;

/**
 * Database Access Objects that handle the reading and writing a class from the database
 * @author muhammad_a
 *
 * @param <T>
 */
public class DataAccess<T> {
	
	private Dao<T, String> dao;
	
	public DataAccess()
	{
		//need by DAO
	}
	
	/**
	 * Set DAO for class object
	 * @param db
	 * @param cls
	 */
	public void setDao(DatabaseHelper db, Class<?> cls){
		try {
				dao = db.getDao(cls);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
	}

	/**
	 * Create data on database
	 * @param data
	 * @return
	 */
	public int create(T data){
		try {
			dao.createOrUpdate(data);
			return 1;
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Update data on database
	 * @param user
	 * @return
	 */
	public int update(T user){
		try {
			return dao.update(user);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Delete data on database
	 * @param data
	 * @return
	 */
	public int delete(T data){
		try {
			return dao.delete(data);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Get item by columnName's value
	 * @param columnName
	 * @param value
	 * @return
	 */
	public T getByColumnNameValue(String columnName, String value){		
		try {
			QueryBuilder<T, String> qb = dao.queryBuilder();
			
			qb.where().eq(columnName, value);
			
			PreparedQuery<T> pq = qb.prepare();
			return dao.queryForFirst(pq);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get all item from database
	 * @return
	 */
	public List<T> getAll(){		
		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Close Data Access Object
	 */
	public void closeDao(){
		this.dao = null;
	}
}
