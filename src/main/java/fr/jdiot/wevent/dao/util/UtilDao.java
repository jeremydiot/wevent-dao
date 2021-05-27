package fr.jdiot.wevent.dao.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import fr.jdiot.wevent.dao.exception.DaoException;

public final class UtilDao {
	protected static final Logger logger = LogManager.getLogger();
	
	private UtilDao() {}
	
	public static PreparedStatement initPreparedStmt( Connection connexion, String sql, Object... objets ) throws SQLException {
		
		logger.trace(sql);
		PreparedStatement preparedStatement = null;
		
	    preparedStatement = connexion.prepareStatement(sql);
		    for ( int i = 0; i < objets.length; i++ ) {
		        preparedStatement.setObject( i + 1, objets[i] );
		    }

		return preparedStatement;
	}
	
	public static <T> List<T> executeCreate(PreparedStatement preparedStatement, ResultSetToEntity<T> resultSetToEntity) throws SQLException {
		List<T> createdEntities = new ArrayList<T>();
		ResultSet resultSet = null;
				
		preparedStatement.executeUpdate();
			
		resultSet = preparedStatement.getGeneratedKeys();
			
		while(resultSet.next()) {
			createdEntities.add(resultSetToEntity.convert(resultSet));
		}
			
		silentClose(resultSet);
		
		return createdEntities;
	}
	
	public static <T> List<T> executeUpdate(PreparedStatement preparedStatement, ResultSetToEntity<T> resultSetToEntity) throws SQLException {
		return executeCreate(preparedStatement, resultSetToEntity);
	}
	
	public static <T> List<T> executeDelete(PreparedStatement preparedStatement, ResultSetToEntity<T> resultSetToEntity) throws SQLException {
		return executeCreate(preparedStatement, resultSetToEntity);
	}
	
	public static <T> List<T> executeRead(PreparedStatement preparedStatement, ResultSetToEntity<T> resultSetToEntity) throws SQLException {
		List<T> findedEntities = new ArrayList<T>();
		ResultSet resultSet = null;
		
		resultSet = preparedStatement.executeQuery();
			
		while(resultSet.next()) {
				
			findedEntities.add(resultSetToEntity.convert(resultSet));
		}
			
		silentClose(resultSet);
		
		return findedEntities;
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

	public static String hashPassword(String password) {
	    return BCrypt.hashpw(password, BCrypt.gensalt(Integer.parseInt(UtilProperties.getConfProperty("conf.jbcrypt.saltComplexity"))));
	}
	
	public static boolean checkPassword(String candidatePwd, String hashedPwd) {
	    return BCrypt.checkpw(candidatePwd, hashedPwd);
	}
	
}
