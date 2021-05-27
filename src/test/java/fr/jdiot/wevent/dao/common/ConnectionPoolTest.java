package fr.jdiot.wevent.dao.common;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConnectionPoolTest {

	static String DB_HOST = "dbhost";
	static String DB_PORT = "5432";
	static String DB_NAME = "wevent";
	static String DB_USER = "dbuser";
	static String DB_PSSWD = "dbpsswd";

	@Mock
	BasicDataSource basicDataSource;
	
	@InjectMocks
	ConnectionPool connectionPool;
	
	@Mock
	Connection connection;
	
	@Test
	void getConnectionTest() throws SQLException {
		
		Mockito.when(basicDataSource.getConnection()).thenReturn(connection);
		
		Connection connectionTest =  connectionPool.getConnection();
		
		Mockito.verify(basicDataSource, Mockito.times(1)).getConnection();
		assertEquals(connection, connectionTest);
	}
	
	@Test
	void getNumActiveConnectionTest() {
		connectionPool.getNumActiveConnection();
		Mockito.verify(basicDataSource,Mockito.times(1)).getNumActive();
	}
	
	@Test
	void getNumIdleConnectionTest() {
		connectionPool.getNumIdleConnection();
		Mockito.verify(basicDataSource,Mockito.times(1)).getNumIdle();
	}

	@Test
	void closeIdleConnectionTest() throws SQLException {
		connectionPool.closeIdleConnection();
		Mockito.verify(basicDataSource,Mockito.times(1)).close();
	}
	
	@Test
	void startConnectionTest() throws SQLException {
		connectionPool.startConnection();
		Mockito.verify(basicDataSource,Mockito.times(1)).start();
	}
	
	@Test
	void isClosedTest() {
		connectionPool.isClosed();
		Mockito.verify(basicDataSource,Mockito.times(1)).isClosed();
	}
	
}
