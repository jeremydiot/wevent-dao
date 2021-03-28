package fr.jdiot.wevent.dao.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class UtilDao {
	protected static final Logger logger = LogManager.getLogger();
	
	private UtilDao() {}
	
	public static PreparedStatement initPreparedStmt( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
		
		logger.trace(sql);
		
	    PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
	    for ( int i = 0; i < objets.length; i++ ) {
	        preparedStatement.setObject( i + 1, objets[i] );
	    }
	    
	    logger.debug(preparedStatement.toString());
	    
	    return preparedStatement;
	}

	public static void silentClose( ResultSet resultSet ) {
	    if ( resultSet != null ) {
	        try {
	            resultSet.close();
	        } catch ( SQLException e ) {
	        	
	        	logger.warn("Failed to close ResultSet : " + e.getMessage() );
	        	
	        }
	    }
	}

	public static void silentClose( Statement statement ) {
	    if ( statement != null ) {
	        try {
	            statement.close();
	        } catch ( SQLException e ) {
	        	logger.warn("Failed to close Statement : " + e.getMessage() );
	        }
	    }
	}

	public static void silentClose( Connection connexion ) {
	    if ( connexion != null ) {
	        try {
	            connexion.close();
	        } catch ( SQLException e ) {
	        	logger.warn( "Failed to close connexion : " + e.getMessage() );
	        }
	    }
	}

	public static void silentClose( Statement statement, Connection connexion ) {
		silentClose( statement );
		silentClose( connexion );
	}

	public static void silentClose( ResultSet resultSet, Statement statement, Connection connexion ) {
		silentClose( resultSet );
		silentClose( statement, connexion );
	}

}
