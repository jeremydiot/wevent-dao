package fr.jdiot.wevent.dao.common;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import fr.jdiot.wevent.dao.exception.DbManagerException;
import fr.jdiot.wevent.dao.util.UtilProperties;

public class DbManagerIT {

	static String DB_HOST = UtilProperties.getTestProperty("db.host");
	static String DB_PORT = UtilProperties.getTestProperty("db.port");
	static String DB_NAME = UtilProperties.getTestProperty("db.name");
	static String DB_USER = UtilProperties.getTestProperty("db.user");
	static String DB_PSSWD = UtilProperties.getTestProperty("db.psswd");
	
	DbManager dbManager;
	
	@BeforeEach
	void init() {
		dbManager = new DbManager(DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PSSWD);
	}
	
	@Test
	@Order(1)
	void functionalTest () {
		dbManager.upConf();
		dbManager.downConf();
		dbManager.upConf();
		dbManager.downConf();
	}
	
	@Test
	@Order(2)
	void unfunctionalTest1 () {
		assertThrows(DbManagerException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				dbManager.upConf();
				dbManager.upConf();
				
			}
		});
		
	}
	
	@Test
	@Order(3)
	void unfunctionalTest2 () {
		assertThrows(DbManagerException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				dbManager.downConf();
				dbManager.downConf();
				
			}
		});
		
	}
}
