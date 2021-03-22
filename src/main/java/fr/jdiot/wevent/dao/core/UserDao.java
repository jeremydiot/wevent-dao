package fr.jdiot.wevent.dao.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import fr.jdiot.wevent.dao.common.ConnectionPool;
import fr.jdiot.wevent.dao.contract.UserContract;
import fr.jdiot.wevent.dao.entity.User;
import fr.jdiot.wevent.dao.exception.DaoException;
import fr.jdiot.wevent.dao.util.SqlPattern;
import fr.jdiot.wevent.dao.util.UtilDao;
import fr.jdiot.wevent.dao.util.UtilProperties;

public final class UserDao extends CommonDao<User> {

	protected static final Logger logger = LogManager.getLogger();
	
	public UserDao(ConnectionPool connectionPool) {
		super(connectionPool);
		// TODO Auto-generated constructor stub
	}

	@Override
	public User create(User entity) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    User newUser = null;
		
	    String sqlReq = String.format(SqlPattern.INSERT, UserContract.TABLE_NAME,
	    		  UserContract.COL_USERNAME_NAME+","
	    		+ UserContract.COL_PASSWORD_NAME+","
	    		+ UserContract.COL_EMAIL_NAME+","
	    		+ UserContract.COL_CONNECTED_AT_NAME
	    		, "?,?,?,?");
		
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, true, 
	    			entity.getUsername(),
	    			hashPassword(entity.getPassword()),
	    			entity.getEmail(),
	    			entity.getConnectedAt());
	    	
	    	int status = preparedStatement.executeUpdate();
	    	
	    	if(status == 0) {
	    		logger.error(new DaoException("User creation failed."));	    		
	    	}
	    	
	    	resultSet = preparedStatement.getGeneratedKeys();
	    	
	    	if(resultSet.next()) {
	    		newUser = resultSetToUserEntity(resultSet);
	    	}else {
	    		logger.error(new DaoException("User creation failed."));	
	    	}
	    	
		} catch (SQLException e) {
    		logger.error(new DaoException(e));
		}finally {
			UtilDao.silentClose(resultSet, preparedStatement, connexion);
		}
	    
		return newUser;
	}

	@Override
	public void delete(User entity) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
		
	    String sqlReq = String.format(SqlPattern.DELETE, UserContract.TABLE_NAME, UserContract.COL_ID_NAME+" = ?");
		
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, false, entity.getId());
	    	
	    	int status = preparedStatement.executeUpdate();
	    	
	    	if(status == 0) {
	    		
	    		logger.error(new DaoException("User delete failed."));	    		
	    	}
	    	
		} catch (SQLException e) {
			
			logger.error(new DaoException(e));
		}finally {
			UtilDao.silentClose(preparedStatement, connexion);
		}
	}

	@Override
	public User update(User entity) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    User updatedUser = null;
		
	    String sqlReq = String.format(SqlPattern.UPDATE, UserContract.TABLE_NAME,
	    		  UserContract.COL_USERNAME_NAME+" = ?,"
	    		+ UserContract.COL_PASSWORD_NAME+" = ?,"
	    		+ UserContract.COL_EMAIL_NAME+" = ?,"
	    		+ UserContract.COL_CONNECTED_AT_NAME+" = ?"
	    		, UserContract.COL_ID_NAME+" = ?");
		
	    User databaseUser = findById(entity.getId()); 
	    
	    if(databaseUser.getPassword() != entity.getPassword()) {
	    	if(checkPassword(entity.getPassword(),databaseUser.getPassword())) {
	    		entity.setPassword(databaseUser.getPassword());
	    	}else {
	    		entity.setPassword(hashPassword(entity.getPassword()));
	    	}
	    }
	    
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, true, 
	    			entity.getUsername(),
	    			entity.getPassword(),
	    			entity.getEmail(),
	    			entity.getConnectedAt(),
	    			entity.getId());
	    	
	    	int status = preparedStatement.executeUpdate();
	    	
	    	if(status == 0) {
	    		logger.error( new DaoException("User update failed."));	    		
	    	}
	    	
	    	resultSet = preparedStatement.getGeneratedKeys();
	    	
	    	if(resultSet.next()) {
	    		updatedUser = resultSetToUserEntity(resultSet);
	    	}else {
	    		logger.error( new DaoException("User update failed."));
	    	}
	    	
			
		} catch (SQLException e) {
			
			logger.error(new DaoException(e));
			
		}finally {
			UtilDao.silentClose(resultSet, preparedStatement, connexion);
		}
		
		return updatedUser;
	}

	@Override
	protected List<User> find(String columnName, String operator, Object value) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<User> users = new ArrayList<User>();
		
	    String sqlReq = String.format(SqlPattern.SELECT,"*",UserContract.TABLE_NAME, columnName+" "+operator+" ?");
		
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, false, value);
	    	
	    	resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
				users.add(resultSetToUserEntity(resultSet));
			}
			
		} catch (SQLException e) {
			logger.error(new DaoException(e));
		}finally {
			UtilDao.silentClose(resultSet, preparedStatement, connexion);
		}
		
		return users;
	}
	
	public User findById(String id){
		List<User> users = find(UserContract.COL_ID_NAME, "=", id);
		return users.get(0);
	}
	
	public User findByEmail(String email) {
		List<User> users = find(UserContract.COL_EMAIL_NAME, "=", email);
		return users.get(0);
	}
	
	public List<User> findByUserName(String username) {
		List<User> users = find(UserContract.COL_EMAIL_NAME, "=", username);
		return users;
	}
	
	private User resultSetToUserEntity(ResultSet resultSet){
		
		User user = new User();
		
		try {
			user.setId(resultSet.getString(UserContract.COL_ID_NAME));
			user.setUsername(resultSet.getString(UserContract.COL_USERNAME_NAME));
			user.setPassword(resultSet.getString(UserContract.COL_PASSWORD_NAME));
			user.setEmail(resultSet.getString(UserContract.COL_EMAIL_NAME));
			user.setConnectedAt(resultSet.getTimestamp(UserContract.COL_CONNECTED_AT_NAME));
			user.setCreatedAt(resultSet.getTimestamp(UserContract.COL_CREATED_AT_NAME));
			user.setModifiedAt(resultSet.getTimestamp(UserContract.COL_MODIFIED_AT_NAME));
		} catch (SQLException e) {
			logger.error(new DaoException(e));
		}
		
		return user;
		
	}
	
	private static String hashPassword(String password) {
	    return BCrypt.hashpw(password, BCrypt.gensalt(Integer.parseInt(UtilProperties.getConfProperety("conf.jbcrypt.saltComplexity"))));
	}
	
	public static boolean checkPassword(String candidatePwd, String hashedPwd) {
	    return BCrypt.checkpw(candidatePwd, hashedPwd);
	}

}
