package fr.jdiot.wevent.dao.common;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.jdiot.wevent.dao.util.UtilProperties;

public final class ConnectionPool {
	
	protected static final Logger logger = LogManager.getLogger();
	
	private static BasicDataSource basicDataSourceSingleton = null;
	
	private BasicDataSource basicDataSource;
	
	private ConnectionPool(BasicDataSource basicDataSourceArg) {
		 this.basicDataSource = basicDataSourceArg;
	}
	
	public static ConnectionPool getInstance(String url, String user, String password) {
		
		logger.trace("url="+url+" user="+user+" password="+password);
		
		if (basicDataSourceSingleton == null) {
			
			basicDataSourceSingleton = new BasicDataSource();
			basicDataSourceSingleton.setDriverClassName(UtilProperties.getConfProperty("conf.jdbc.driver"));
			basicDataSourceSingleton.setUrl(url);
			basicDataSourceSingleton.setUsername(user);
			basicDataSourceSingleton.setPassword(password);
			
			basicDataSourceSingleton.setInitialSize(Integer.parseInt(UtilProperties.getConfProperty("conf.bdcp2.initialSize")));
			basicDataSourceSingleton.setMaxTotal(Integer.parseInt(UtilProperties.getConfProperty("conf.bdcp2.maxTotal")));
			basicDataSourceSingleton.setMaxIdle(Integer.parseInt(UtilProperties.getConfProperty("conf.bdcp2.maxIdle")));
			basicDataSourceSingleton.setMinIdle(Integer.parseInt(UtilProperties.getConfProperty("conf.bdcp2.minIdle")));
			basicDataSourceSingleton.setMaxOpenPreparedStatements(Integer.parseInt(UtilProperties.getConfProperty("conf.bdcp2.maxOpenPreparedStatements")));
			
			logger.info("sigleton created");
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
