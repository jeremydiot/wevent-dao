package fr.jdiot.wevent.dao.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fr.jdiot.wevent.dao.contract.DatabaseContract;
import fr.jdiot.wevent.dao.contract.EventContract;
import fr.jdiot.wevent.dao.contract.FriendContract;
import fr.jdiot.wevent.dao.contract.GuestContract;
import fr.jdiot.wevent.dao.contract.UserContract;
import fr.jdiot.wevent.dao.exception.DbManagerException;
import fr.jdiot.wevent.dao.util.SqlPattern;
import fr.jdiot.wevent.dao.util.UtilProperties;

public class DbManager {

	private static final String CREATE_EXTENSION_UUID_OSSP = String.format(SqlPattern.CREATE_EXTENSION, DatabaseContract.DATABASE_UUID_OSSP_EXTENSION_NAME);
	
	private static final String CREATE_DATABASE = String.format(SqlPattern.CREATE_DATABASE, DatabaseContract.DATABASE_NAME, DatabaseContract.DATABASE_ENCODING_NAME );
	private static final String DROP_DATABASE = String.format(SqlPattern.DROP_DATABASE, DatabaseContract.DATABASE_NAME);
	
	protected static final Logger logger = LogManager.getLogger();
	
	private String jdbcUrl;
	private String jdbcDatabase;
	private String jdbcUser;
	private String jdbcPassword;
	
	public DbManager(String host, String port, String database, String user, String password) {
		
		jdbcUrl = "jdbc:postgresql://"+host+":"+port+"/";
		jdbcDatabase = database;
		jdbcUser = user;
		jdbcPassword = password;
		
	}
	
	public void executeSqlQuery(String sqlQuery) {
		executeSqlQuery(sqlQuery, true);
	}

	public void executeSqlQuery(String sqlQuery, boolean queryOnDatabase) {
		
		logger.trace(sqlQuery);
		
		Connection connection = null;
		Statement statement= null;
		
		try {
			Class.forName(UtilProperties.getConfProperty("conf.jdbc.driver"));
			connection = DriverManager.getConnection(jdbcUrl+(queryOnDatabase ? jdbcDatabase : ""), jdbcUser, jdbcPassword) ;
			statement = connection.createStatement();
			statement.execute(sqlQuery);
			
		} catch (SQLException | ClassNotFoundException e) {
			throw logger.throwing(Level.ERROR,new DbManagerException(e));
			
		}finally {
			try {
				statement.close();
				connection.close();
			} catch (NullPointerException | SQLException e) {
				logger.warn(e);
			}
		}	
	}
	
	public void upConf() {
		executeSqlQuery(CREATE_DATABASE, false);
		executeSqlQuery(CREATE_EXTENSION_UUID_OSSP); // add uuid_ossp extension
		
		createTables();
		
		// function to update updated_at column
		executeSqlQuery(DatabaseContract.DATABASE_FUNCTION_SET_CURRENT_TIMESTAMP_TO_CONNECTED_AT);

		// trigger to update updated_at column for t_event and t_user
		executeSqlQuery(String.format(DatabaseContract.DATABASE_TRIGGER_ON_UPDATE_SET_CURRENT_TIMESTAMP_TO_CONNECTED_AT, EventContract.TABLE_NAME));
		executeSqlQuery(String.format(DatabaseContract.DATABASE_TRIGGER_ON_UPDATE_SET_CURRENT_TIMESTAMP_TO_CONNECTED_AT, UserContract.TABLE_NAME));
	}
	
	public void downConf() {
		executeSqlQuery(DROP_DATABASE, false);
	}
	
	private void createTables() {
		createUserTable();
		createEventTable();
		createGuestTable();
		createFriendTable();
	}
	
	private void createEventTable() {
		
		String sqlQuery = String.format(SqlPattern.CREATE_TABLE, EventContract.TABLE_NAME,
				   EventContract.COL_ID_NAME+" "+EventContract.COL_ID_DATATYPE+" "+EventContract.COL_ID_CONSTRAINT+","
				  +EventContract.COL_ADMIN_ID_NAME+" "+EventContract.COL_ADMIN_ID_DATATYPE+" "+EventContract.COL_ADMIN_ID_CONSTRAINT+","
				  +EventContract.COL_TITLE_NAME+" "+EventContract.COL_TITLE_DATATYPE+" "+EventContract.COL_TITLE_CONSTRAINT+","
				  +EventContract.COL_START_DATE_NAME+" "+EventContract.COL_START_DATE_DATATYPE+" "+EventContract.COL_START_DATE_CONSTRAINT+","
				  +EventContract.COL_END_DATE_NAME+" "+EventContract.COL_END_DATE_DATATYPE+" "+EventContract.COL_END_DATE_CONSTRAINT+","
				  +EventContract.COL_CONTENT_NAME+" "+EventContract.COL_CONTENT_DATATYPE+" "+EventContract.COL_CONTENT_CONSTRAINT+","
				  +EventContract.COL_CREATED_AT_NAME+" "+EventContract.COL_CREATED_AT_DATATYPE+" "+EventContract.COL_CREATED_AT_CONSTRAINT+","
				  +EventContract.COL_UPDATED_AT_NAME+" "+EventContract.COL_UPDATED_AT_DATATYPE+" "+EventContract.COL_UPDATED_AT_CONSTRAINT
				);  
		executeSqlQuery(sqlQuery);
	}
	
	private void createFriendTable() {
		
		String sqlQuery = String.format(SqlPattern.CREATE_TABLE, FriendContract.TABLE_NAME,
				   FriendContract.COL_USER_ID_NAME+" "+FriendContract.COL_USER_ID_DATATYPE+" "+FriendContract.COL_USER_ID_CONSTRAINT+","
				  +FriendContract.COL_FRIEND_ID_NAME+" "+FriendContract.COL_FRIEND_ID_DATATYPE+" "+FriendContract.COL_FRIEND_ID_CONSTRAINT+","
				  +FriendContract.COL_CREATED_AT_NAME+" "+FriendContract.COL_CREATED_AT_DATATYPE+" "+FriendContract.COL_CREATED_AT_CONSTRAINT+","
				  +FriendContract.TABLE_CONSTRAINT
				);  
		
		executeSqlQuery(sqlQuery);
	}
	
	private void createGuestTable() {
		
		String sqlQuery = String.format(SqlPattern.CREATE_TABLE, GuestContract.TABLE_NAME,
				   GuestContract.COL_USER_ID_NAME+" "+GuestContract.COL_USER_ID_DATATYPE+" "+GuestContract.COL_USER_ID_CONSTRAINT+","
				  +GuestContract.COL_EVENT_ID_NAME+" "+GuestContract.COL_EVENT_ID_DATATYPE+" "+GuestContract.COL_EVENT_ID_CONSTRAINT+","
				  +GuestContract.COL_CREATED_AT_NAME+" "+GuestContract.COL_CREATED_AT_DATATYPE+" "+GuestContract.COL_CREATED_AT_CONSTRAINT+","
				  +GuestContract.TABLE_CONSTRAINT
				);
		
		executeSqlQuery(sqlQuery);
	}
	
	private void createUserTable() {
		
		String sqlQuery = String.format(SqlPattern.CREATE_TABLE, UserContract.TABLE_NAME,
				   UserContract.COL_ID_NAME+" "+UserContract.COL_ID_DATATYPE+" "+UserContract.COL_ID_CONSTRAINT+","
				  +UserContract.COL_USERNAME_NAME+" "+UserContract.COL_USERNAME_DATATYPE+" "+UserContract.COL_USERNAME_CONSTRAINT+","
				  +UserContract.COL_PASSWORD_NAME+" "+UserContract.COL_PASSWORD_DATATYPE+" "+UserContract.COL_PASSWORD_CONSTRAINT+","
				  +UserContract.COL_EMAIL_NAME+" "+UserContract.COL_EMAIL_DATATYPE+" "+UserContract.COL_EMAIL_CONSTRAINT+","
				  +UserContract.COL_CONNECTED_AT_NAME+" "+UserContract.COL_CONNECTED_AT_DATATYPE+" "+UserContract.COL_CONNECTED_AT_CONSTRAINT+","
				  +UserContract.COL_CREATED_AT_NAME+" "+UserContract.COL_CREATED_AT_DATATYPE+" "+UserContract.COL_CREATED_AT_CONSTRAINT+","
				  +UserContract.COL_UPDATED_AT_NAME+" "+UserContract.COL_UPDATED_AT_DATATYPE+" "+UserContract.COL_UPDATED_AT_CONSTRAINT
				);  
		
		executeSqlQuery(sqlQuery);
	}
}
