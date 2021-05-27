package fr.jdiot.wevent.dao.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.jdiot.wevent.dao.common.ConnectionPool;
import fr.jdiot.wevent.dao.contract.UserContract;
import fr.jdiot.wevent.dao.entity.User;
import fr.jdiot.wevent.dao.exception.DaoException;
import fr.jdiot.wevent.dao.util.ResultSetToEntity;
import fr.jdiot.wevent.dao.util.SqlPattern;
import fr.jdiot.wevent.dao.util.UtilDao;

public final class UserDao extends CommonDao<User> implements ResultSetToEntity<User>{

	protected static final Logger logger = LogManager.getLogger();
	
	
	public UserDao(ConnectionPool connectionPool) {
		super(connectionPool);
	}

	@Override
	public User create(User entity) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    List<User> updatedUsers = new ArrayList<User>();
		
	    String sqlReq = String.format(SqlPattern.INSERT, UserContract.TABLE_NAME,
	    		  UserContract.COL_USERNAME_NAME+","
	    		+ UserContract.COL_PASSWORD_NAME+","
	    		+ UserContract.COL_EMAIL_NAME+","
	    		+ UserContract.COL_CONNECTED_AT_NAME
	    		, "?,?,?,?");
	    
	    try {
			connection = connectionPool.getConnection();
			preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, 
					entity.getUsername(),
					UtilDao.hashPassword(entity.getPassword()),
	    			entity.getEmail(),
	    			entity.getConnectedAt());
			
			updatedUsers = UtilDao.executeCreate(preparedStatement, this);
			
			if(updatedUsers.size() < 1) {
				throw logger.throwing(Level.ERROR,new DaoException("SQL query failed !"));
			}
			
		} catch (SQLException e) {
			throw logger.throwing(Level.ERROR,new DaoException(e));
		}finally {
			UtilDao.silentClose(preparedStatement, connection);
		}
	    
		return updatedUsers.get(0);
	}

	@Override
	public User delete(User entity) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    List<User> updatedUsers = new ArrayList<User>();
		
	    String sqlReq = String.format(SqlPattern.DELETE, UserContract.TABLE_NAME, UserContract.COL_ID_NAME+" = ?");
		
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, entity.getId());
	    	
	    	updatedUsers =  UtilDao.executeDelete(preparedStatement, this);
	    	
			if(updatedUsers.size() < 1) {
				throw logger.throwing(Level.ERROR,new DaoException("SQL query failed !"));
			}
	    	
		} catch (SQLException e) {
			throw logger.throwing(Level.ERROR,new DaoException(e));
		}finally {
			UtilDao.silentClose(preparedStatement, connexion);
		}
	    
		return updatedUsers.get(0);
	}

	@Override
	public User update(User entity) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    List<User> updatedUsers = new ArrayList<User>();
		
	    String sqlReq = String.format(SqlPattern.UPDATE, UserContract.TABLE_NAME,
	    		  UserContract.COL_USERNAME_NAME+" = ?,"
	    		+ UserContract.COL_PASSWORD_NAME+" = ?,"
	    		+ UserContract.COL_EMAIL_NAME+" = ?,"
	    		+ UserContract.COL_CONNECTED_AT_NAME+" = ?"
	    		, UserContract.COL_ID_NAME+" = ?");
		
	    User databaseUser = findById(entity.getId());
	    
	    if(databaseUser.getPassword() != entity.getPassword()) { // if bean contain hashed password
	    	if(UtilDao.checkPassword(entity.getPassword(),databaseUser.getPassword())) { // if bean contain good clear password
	    		entity.setPassword(databaseUser.getPassword()); // replace by hashed password in entity
	    	}else { // bean contain new password
	    		entity.setPassword(UtilDao.hashPassword(entity.getPassword())); // hash new password
	    	}
	    }
	    
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, 
	    			entity.getUsername(),
	    			entity.getPassword(),
	    			entity.getEmail(),
	    			entity.getConnectedAt(),
	    			entity.getId());
	    
	    	updatedUsers = UtilDao.executeUpdate(preparedStatement, this);
	    	
			if(updatedUsers.size() < 1) {
				throw logger.throwing(Level.ERROR,new DaoException("SQL query failed !"));
			}
			
		} catch (SQLException e) {
			
			throw logger.throwing(Level.ERROR,new DaoException(e));
			
		}finally {
			UtilDao.silentClose(preparedStatement, connexion);
		}
		
		return updatedUsers.get(0);
	}

	@Override
	protected List<User> read(String columnName, String operator, Object value) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    List<User> users = new ArrayList<User>();
	    
	    String sqlReq = String.format(SqlPattern.SELECT,"*",UserContract.TABLE_NAME, columnName+" "+operator+" ?");
		
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, value);
	    	
	    	users = UtilDao.executeRead(preparedStatement, this);
	    	
			
		} catch (SQLException e) {
			throw logger.throwing(Level.ERROR,new DaoException(e));
		}finally {
			UtilDao.silentClose(preparedStatement, connexion);
		}
	    
		return users;
	}
	
	public User findById(String id){
		List<User> users = read(UserContract.COL_ID_NAME, "=", id);
		return users.get(0);
	}
	
	public User findByEmail(String email) {
		List<User> users = read(UserContract.COL_EMAIL_NAME, "=", email);
		return users.get(0);
	}
	
	public List<User> findByUserName(String username) {
		List<User> users = read(UserContract.COL_EMAIL_NAME, "=", username);
		return users;
	}

	@Override
	public User convert(ResultSet resultSet) throws SQLException {
		User user = new User();
	
		user.setId(resultSet.getString(UserContract.COL_ID_NAME));
		user.setUsername(resultSet.getString(UserContract.COL_USERNAME_NAME));
		user.setPassword(resultSet.getString(UserContract.COL_PASSWORD_NAME));
		user.setEmail(resultSet.getString(UserContract.COL_EMAIL_NAME));
		user.setConnectedAt(resultSet.getTimestamp(UserContract.COL_CONNECTED_AT_NAME));
		user.setCreatedAt(resultSet.getTimestamp(UserContract.COL_CREATED_AT_NAME));
		user.setUpdatedAt(resultSet.getTimestamp(UserContract.COL_UPDATED_AT_NAME));
		
		return user;
	}
}
