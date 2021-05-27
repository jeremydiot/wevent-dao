package fr.jdiot.wevent.dao.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import fr.jdiot.wevent.dao.util.UtilProperties;

public class ConnectionPoolIT {

	static String DB_HOST = UtilProperties.getTestProperty("db.host");
	static String DB_PORT = UtilProperties.getTestProperty("db.port");
	static String DB_NAME = UtilProperties.getTestProperty("db.name");
	static String DB_USER = UtilProperties.getTestProperty("db.user");
	static String DB_PSSWD = UtilProperties.getTestProperty("db.psswd");
	
	@Test
	public void getInstanceTest1() {
		ConnectionPool cp1 = ConnectionPool.init(DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PSSWD);
		ConnectionPool cp2 = ConnectionPool.getInstance();
		ConnectionPool cp3 = ConnectionPool.getInstance();
		
		assertEquals(cp1, cp2);
		assertEquals(cp1, cp3);
		
	}
	
	@Test
	public void getInstanceTest2() {
		ConnectionPool cp1 = ConnectionPool.init(DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PSSWD);
		ConnectionPool cp2 = ConnectionPool.init(DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PSSWD);
		
		assertNotEquals(cp1, cp2);		
	}

}
