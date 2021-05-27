package fr.jdiot.wevent.dao.util;

import static org.junit.jupiter.api.Assertions.*;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import fr.jdiot.wevent.dao.exception.UtilPropertiesException;

class UtilPropertiesIT {

	protected static final Logger logger = LogManager.getLogger();

	@Test
	void getConfPropertyTest(){
		String propertyValue = UtilProperties.getConfProperty("conf.jdbc.driver");
		assertEquals(propertyValue, "org.postgresql.Driver");
	}
	
	@Test 
	void getTestPropertyTest(){
		String propertyValue = UtilProperties.getTestProperty("db.port");
		assertEquals(propertyValue, "5432");
	}
	
	@Test
	void getPropertyFromFileTest()  {
		String propertyValue = UtilProperties.getPropertyFromFile("conf.properties", "conf.jdbc.driver");
		assertEquals(propertyValue, "org.postgresql.Driver");
	}
	
	@Test
	void getPropertyFromFileTestWrongKey()  {
				
		assertThrows(UtilPropertiesException.class, () -> {
		 UtilProperties.getPropertyFromFile("conf.properties", "wrong.key");	
		});
	}
	
	@Test
	void getPropertyFromFileTestNullKey()  {
		assertThrows(UtilPropertiesException.class, () -> {
			UtilProperties.getPropertyFromFile("app.properties", null);
		});
	}
	
	@Test
	void getPropertyFromFileTestWrongFile()  {
		assertThrows(UtilPropertiesException.class, () -> {
			UtilProperties.getPropertyFromFile("wrongFile.properties", "project.name");
		});
	}
	
	@Test
	void getPropertyFromFileTestNullFile()  {
		assertThrows(UtilPropertiesException.class, () -> {
			UtilProperties.getPropertyFromFile(null, "project.name");
		});
	}

	
	
	
}
