package fr.jdiot.wevent.dao.core;

import java.util.List;

import fr.jdiot.wevent.dao.common.ConnectionPool;

public abstract class CommonDao<T> {
	
	protected ConnectionPool connectionPool;
	
	public CommonDao(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
	
	public abstract T create(T entity);
	
	public abstract T delete(T entity);
	
	public abstract T update(T entity);
	
	protected abstract List<T> read(String columnName, String operator, Object value);
}
