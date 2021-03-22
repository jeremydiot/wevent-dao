package fr.jdiot.wevent.dao.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.jdiot.wevent.dao.exception.UtilPropertiesException;

public final class UtilProperties {

	private static final String CONF_PROPERTIES_FILE_PATH="conf.properties";
	
	protected static final Logger logger = LogManager.getLogger();
	
	private UtilProperties() {}
	
	public static String getConfProperety(String propertyName) {
		return getPropertyFromFile(CONF_PROPERTIES_FILE_PATH, propertyName);
	}

	private static String getPropertyFromFile(String propertiesFilePath, String propertyName) {
		
		logger.trace(propertiesFilePath+" "+propertyName);
		
		InputStream inputStream = null;
		Properties properties = new Properties();
		String propertyValue = null;
		
		inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONF_PROPERTIES_FILE_PATH);	

		if (inputStream == null) {
			
			logger.error(new UtilPropertiesException("Properties file "+propertiesFilePath+" does not exist."));
		}
		
		try {
			properties.load(inputStream);
			propertyValue = properties.getProperty(propertyName);
		} catch (IOException e) {
			
			logger.error(new UtilPropertiesException("Unable to load "+propertyName+" property"));
		}finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.warn("Failed to close inputStream : "+e);
			}
		}
		
		return propertyValue;
	}
	
	
}