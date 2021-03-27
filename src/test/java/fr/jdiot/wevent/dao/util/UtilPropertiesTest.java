package fr.jdiot.wevent.dao.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import fr.jdiot.wevent.dao.exception.UtilPropertiesException;

class UtilPropertiesTest {

	protected static final Logger logger = LogManager.getLogger();
	
	@ParameterizedTest
	@MethodSource("getConfPropertyProvider")
	void getConfPropertyTest(String key, String value) {
		assertEquals(UtilProperties.getConfProperty(key), value);
	}
	
	private static Stream<Arguments> getConfPropertyProvider() {
	    return Stream.of(
	            Arguments.of("conf.jdbc.driver", "org.postgresql.Driver"),
	            Arguments.of("conf.bdcp2.initialSize", "0"),
	            Arguments.of("conf.bdcp2.maxTotal", "8"),
	            Arguments.of("conf.bdcp2.maxIdle", "8"),
	            Arguments.of("conf.bdcp2.minIdle", "0"),
	            Arguments.of("conf.bdcp2.maxOpenPreparedStatements", "-1"),
	            Arguments.of("conf.sql.limit", "20"),
	            Arguments.of("conf.jbcrypt.saltComplexity", "10")
	    );
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
