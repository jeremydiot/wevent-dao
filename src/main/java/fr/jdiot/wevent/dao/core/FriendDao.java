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
import fr.jdiot.wevent.dao.contract.FriendContract;
import fr.jdiot.wevent.dao.entity.Friend;
import fr.jdiot.wevent.dao.entity.User;
import fr.jdiot.wevent.dao.exception.DaoException;
import fr.jdiot.wevent.dao.util.ResultSetToEntity;
import fr.jdiot.wevent.dao.util.SqlPattern;
import fr.jdiot.wevent.dao.util.UtilDao;

public final class FriendDao extends CommonDao<Friend> implements ResultSetToEntity<Friend> {

	protected static final Logger logger = LogManager.getLogger();
	
	public FriendDao(ConnectionPool connectionPool) {
		super(connectionPool);
	}

	@Override
	public Friend create(Friend entity) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    List<Friend> updatedFriends = new ArrayList<Friend>();
		
	    String sqlReq = String.format(SqlPattern.INSERT, FriendContract.TABLE_NAME,
	    		  FriendContract.COL_USER_ID_NAME+","
	    		+ FriendContract.COL_FRIEND_ID_NAME+","
	    		, "?,?");
		
	    try {
	    	connection = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, 
	    			entity.getUser().getId(),
	    			entity.getFriend().getId());
	    
	    	updatedFriends = UtilDao.executeCreate(preparedStatement, this);
	    	
			if(updatedFriends.size() < 1) {
				throw logger.throwing(Level.ERROR,new DaoException("SQL query failed !"));
			}
	    	
	    } catch (SQLException e) {
			logger.error(new DaoException(e));
		}finally {
			UtilDao.silentClose(preparedStatement, connection);
		}
	    
		return updatedFriends.get(0);
	}

	@Override
	public Friend delete(Friend entity) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    List<Friend> updatedFriends = new ArrayList<Friend>();
		
	    String sqlReq = String.format(SqlPattern.DELETE, FriendContract.TABLE_NAME, 
	    		FriendContract.COL_USER_ID_NAME+" = ? AND "+FriendContract.COL_FRIEND_ID_NAME+" = ?");
		
	    try {
	    	connection = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, 
	    			entity.getUser().getId(), entity.getFriend().getId());
	    	
	    	updatedFriends = UtilDao.executeDelete(preparedStatement, this);
	    	
			if(updatedFriends.size() < 1) {
				throw logger.throwing(Level.ERROR,new DaoException("SQL query failed !"));
			}
	    	
		} catch (SQLException e) {
			logger.error(new DaoException(e));
		}finally {
			UtilDao.silentClose(preparedStatement, connection);
		}
		return updatedFriends.get(0);
	}

	@Override
	public Friend update(Friend entity) {
		logger.error(new DaoException("Method not implemented."));
		return null;
	}
	
	@Override
	protected List<Friend> read(String columnName, String operator, Object value) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    List<Friend> friends = new ArrayList<Friend>();
		
	    String sqlReq = String.format(SqlPattern.SELECT,"*",FriendContract.TABLE_NAME, columnName+" "+operator+" ?");
		
	    try {
	    	connection = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, value);
	    	
	    	friends = UtilDao.executeRead(preparedStatement, this);
			
		} catch (SQLException e) {
			throw logger.throwing(Level.ERROR,new DaoException(e));
		}finally {
			UtilDao.silentClose(preparedStatement, connection);
		}
		
		return friends;
	}
	
	public List<Friend> findByUser(User user) {
		List<Friend> friends = read(FriendContract.COL_USER_ID_NAME, "=", user.getId());
		return friends;
	}

	@Override
	public Friend convert(ResultSet resultSet) throws SQLException {
		UserDao userDao = new UserDao(this.connectionPool);
		Friend friend = new Friend();
		
		User user = userDao.findById(resultSet.getString(FriendContract.COL_USER_ID_NAME));
		User friendUser = userDao.findById(resultSet.getString(FriendContract.COL_FRIEND_ID_NAME));
		
		friend.setUser(user);
		friend.setFriend(friendUser);
		friend.setCreatedAt(resultSet.getTimestamp(FriendContract.COL_CREATED_AT_NAME));
		
		return friend;
	}
}