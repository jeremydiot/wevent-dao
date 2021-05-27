package fr.jdiot.wevent.dao.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.jdiot.wevent.dao.exception.UtilPropertiesException;

public final class UtilProperties {

	private static final String CONF_PROPERTIES_FILE_PATH="conf.properties";
	private static final String TEST_PROPERTIES_FILE_PATH="test.properties";
	
	protected static final Logger logger = LogManager.getLogger();
	
	private UtilProperties() {}
	
	public static String getConfProperty(String propertyName) {
		return getPropertyFromFile(CONF_PROPERTIES_FILE_PATH, propertyName);
	}
	
	public static String getTestProperty(String propertyName) {
		return getPropertyFromFile(TEST_PROPERTIES_FILE_PATH, propertyName);
	}

	public static String getPropertyFromFile(String propertiesFilePath, String propertyName) {
		
		logger.trace(propertiesFilePath+" "+propertyName);
		
		InputStream inputStream = null;
		Properties properties = new Properties();
		String propertyValue = null;
		
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFilePath);
			properties.load(inputStream);
			propertyValue = properties.getProperty(propertyName);			
		} catch ( IOException | NullPointerException e) {

			throw logger.throwing(Level.ERROR, new UtilPropertiesException(e));
		}
		
		if(propertyValue == null) {
			throw logger.throwing(Level.ERROR, new UtilPropertiesException("Unable to load "+propertyName+" property from "+propertiesFilePath+" file"));
		}
		
		return propertyValue;
	}
	
	
}