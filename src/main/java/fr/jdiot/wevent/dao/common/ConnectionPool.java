package fr.jdiot.wevent.dao.common;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public final class ConnectionPool {
	
	private static final String URL = "jdbc:postgresql://127.0.0.1:5432/wevent";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static final String DRIVER = "org.postgresql.Driver";
	
	private static final int MIN_IDLE = 5;
	private static final int MAX_IDLE = 10;
	private static final int MAX_STMT = 100;
	
	private static BasicDataSource basicDataSourceSingleton = null;
	
	private BasicDataSource basicDataSource;
	
	private ConnectionPool(BasicDataSource basicDataSourceArg) {
		 this.basicDataSource = basicDataSourceArg;
	}
	
	public static ConnectionPool getInstance() {
		if (basicDataSourceSingleton == null) {
			
			basicDataSourceSingleton = new BasicDataSource();
			basicDataSourceSingleton.setDriverClassName(DRIVER);
			basicDataSourceSingleton.setUrl(URL);
			basicDataSourceSingleton.setUsername(USER);
			basicDataSourceSingleton.setPassword(PASSWORD);
			
			basicDataSourceSingleton.setMinIdle(MIN_IDLE);
			basicDataSourceSingleton.setMaxIdle(MAX_IDLE);
			basicDataSourceSingleton.setMaxOpenPreparedStatements(MAX_STMT);
			
		}
		
		ConnectionPool instance = new ConnectionPool(basicDataSourceSingleton);
		return instance;
	}
	
	public Connection getConnection() throws SQLException {
		return this.basicDataSource.getConnection();
	}
	
	public int getNumActiveConnection() throws SQLException {
		return this.basicDataSource.getNumActive();
	}
	
	
	public int getNumIdleConnection() throws SQLException {
		return this.basicDataSource.getNumIdle();
	}
	
	public void closeIdleConnection() throws SQLException {
		this.basicDataSource.close();
	}
	
	public void startConnection() throws SQLException {
		this.basicDataSource.start();
	}
	
	public boolean isClosed() {
		return this.basicDataSource.isClosed();
	}
}
