package fr.jdiot.wevent.dao.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.jdiot.wevent.dao.contract.EventContract;
import fr.jdiot.wevent.dao.contract.FriendContract;
import fr.jdiot.wevent.dao.contract.GuestContract;
import fr.jdiot.wevent.dao.contract.UserContract;
import fr.jdiot.wevent.dao.exception.DbManagerException;
import fr.jdiot.wevent.dao.util.SqlPattern;
import fr.jdiot.wevent.dao.util.UtilDao;

public final class DbManager {

	private ConnectionPool connectionPool;
	
public DbManager(ConnectionPool connectionPoolArg) {
		connectionPool = connectionPoolArg;
	}
	
	public void upConf() {
		createUserTable();
		createEventTable();
		createGuestTable();
		createFriendTable();
	}
	
	public void resetConf() {
		downConf();
		upConf();
	}
	
	public void downConf() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String sqlReq = String.format(SqlPattern.DROP_TABLE, 
				  FriendContract.TABLE_NAME+","
				+ GuestContract.TABLE_NAME+","
				+ EventContract.TABLE_NAME+","
				+ UserContract.TABLE_NAME);
		
		try {
			connection = this.connectionPool.getConnection();
			preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, false);
			preparedStatement.executeQuery();
			
			
		} catch (SQLException e) {
			throw new DbManagerException(e);
		}finally {
			UtilDao.silentClose(preparedStatement, connection);
		}
	}
	
	private void createEventTable() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String sqlReq = String.format(SqlPattern.CREATE_TABLE, EventContract.TABLE_NAME,
				   EventContract.COL_ID_NAME+" "+EventContract.COL_ID_DATATYPE+" "+EventContract.COL_ID_CONSTRAINT+","
				  +EventContract.COL_ADMIN_ID_NAME+" "+EventContract.COL_ADMIN_ID_DATATYPE+" "+EventContract.COL_ADMIN_ID_CONSTRAINT+","
				  +EventContract.COL_TITLE_NAME+" "+EventContract.COL_TITLE_DATATYPE+" "+EventContract.COL_TITLE_CONSTRAINT+","
				  +EventContract.COL_START_DATE_NAME+" "+EventContract.COL_START_DATE_DATATYPE+" "+EventContract.COL_START_DATE_CONSTRAINT+","
				  +EventContract.COL_END_DATE_NAME+" "+EventContract.COL_END_DATE_DATATYPE+" "+EventContract.COL_END_DATE_CONSTRAINT+","
				  +EventContract.COL_CONTENT_NAME+" "+EventContract.COL_CONTENT_DATATYPE+" "+EventContract.COL_CONTENT_CONSTRAINT+","
				  +EventContract.COL_CREATED_AT_NAME+" "+EventContract.COL_CREATED_AT_DATATYPE+" "+EventContract.COL_CREATED_AT_CONSTRAINT+","
				  +EventContract.COL_MODIFIED_AT_NAME+" "+EventContract.COL_MODIFIED_AT_DATATYPE+" "+EventContract.COL_MODIFIED_AT_CONSTRAINT
				);  
		
		try {
			connection = this.connectionPool.getConnection();
			preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, false);
			preparedStatement.execute();
			
			
		} catch (SQLException e) {
			throw new DbManagerException(e);
		}finally {
			UtilDao.silentClose(preparedStatement, connection);
		}
	}
	
	private void createFriendTable() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String sqlReq = String.format(SqlPattern.CREATE_TABLE, FriendContract.TABLE_NAME,
				   FriendContract.COL_USER_ID_NAME+" "+FriendContract.COL_USER_ID_DATATYPE+" "+FriendContract.COL_USER_ID_CONSTRAINT+","
				  +FriendContract.COL_FRIEND_ID_NAME+" "+FriendContract.COL_FRIEND_ID_DATATYPE+" "+FriendContract.COL_FRIEND_ID_CONSTRAINT+","
				  +FriendContract.COL_CREATED_AT_NAME+" "+FriendContract.COL_CREATED_AT_DATATYPE+" "+FriendContract.COL_CREATED_AT_CONSTRAINT+","
				  +FriendContract.TABLE_CONSTRAINT
				);  
		try {
			connection = this.connectionPool.getConnection();
			preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, false);
			preparedStatement.execute();
			
			
		} catch (SQLException e) {
			throw new DbManagerException(e);
		}finally {
			UtilDao.silentClose(preparedStatement, connection);
		}	
	}
	
	private void createGuestTable() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String sqlReq = String.format(SqlPattern.CREATE_TABLE, GuestContract.TABLE_NAME,
				   GuestContract.COL_USER_ID_NAME+" "+GuestContract.COL_USER_ID_DATATYPE+" "+GuestContract.COL_USER_ID_CONSTRAINT+","
				  +GuestContract.COL_EVENT_ID_NAME+" "+GuestContract.COL_EVENT_ID_DATATYPE+" "+GuestContract.COL_EVENT_ID_CONSTRAINT+","
				  +GuestContract.COL_CREATED_AT_NAME+" "+GuestContract.COL_CREATED_AT_DATATYPE+" "+GuestContract.COL_CREATED_AT_CONSTRAINT+","
				  +GuestContract.TABLE_CONSTRAINT
				);

		try {
			connection = this.connectionPool.getConnection();
			preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, false);
			preparedStatement.execute();
			
			
		} catch (SQLException e) {
			throw new DbManagerException(e);
		}finally {
			UtilDao.silentClose(preparedStatement, connection);
		}
	}
	
	private void createUserTable() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String sqlReq = String.format(SqlPattern.CREATE_TABLE, UserContract.TABLE_NAME,
				   UserContract.COL_ID_NAME+" "+UserContract.COL_ID_DATATYPE+" "+UserContract.COL_ID_CONSTRAINT+","
				  +UserContract.COL_USERNAME_NAME+" "+UserContract.COL_USERNAME_DATATYPE+" "+UserContract.COL_USERNAME_CONSTRAINT+","
				  +UserContract.COL_PASSWORD_NAME+" "+UserContract.COL_PASSWORD_DATATYPE+" "+UserContract.COL_PASSWORD_CONSTRAINT+","
				  +UserContract.COL_EMAIL_NAME+" "+UserContract.COL_EMAIL_DATATYPE+" "+UserContract.COL_EMAIL_CONSTRAINT+","
				  +UserContract.COL_CONNECTED_AT_NAME+" "+UserContract.COL_CONNECTED_AT_DATATYPE+" "+UserContract.COL_CONNECTED_AT_CONSTRAINT+","
				  +UserContract.COL_CREATED_AT_NAME+" "+UserContract.COL_CREATED_AT_DATATYPE+" "+UserContract.COL_CREATED_AT_CONSTRAINT+","
				  +UserContract.COL_MODIFIED_AT_NAME+" "+UserContract.COL_MODIFIED_AT_DATATYPE+" "+UserContract.COL_MODIFIED_AT_CONSTRAINT
				);  
		try {
			connection = this.connectionPool.getConnection();
			preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, false);
			preparedStatement.execute();
			
			
		} catch (SQLException e) {
			throw new DbManagerException(e);
		}finally {
			UtilDao.silentClose(preparedStatement, connection);
		}
	}
}
