package fr.jdiot.wevent.dao.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.jdiot.wevent.dao.common.ConnectionPool;
import fr.jdiot.wevent.dao.contract.FriendContract;
import fr.jdiot.wevent.dao.entity.Friend;
import fr.jdiot.wevent.dao.entity.User;
import fr.jdiot.wevent.dao.exception.DaoException;
import fr.jdiot.wevent.dao.util.SqlPattern;
import fr.jdiot.wevent.dao.util.UtilDao;

public final class FriendDao extends CommonDao<Friend> {

	public FriendDao(ConnectionPool connectionPool) {
		super(connectionPool);
	}

	@Override
	public Friend create(Friend entity) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Friend newFriend = null;
		
	    String sqlReq = String.format(SqlPattern.INSERT, FriendContract.TABLE_NAME,
	    		  FriendContract.COL_USER_ID_NAME+","
	    		+ FriendContract.COL_FRIEND_ID_NAME+","
	    		, "?,?");
		
	    try {
	    	connection = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, true, 
	    			entity.getUser().getId(),
	    			entity.getFriend().getId());
	    	
	    	int status = preparedStatement.executeUpdate();
	    	
	    	if(status == 0) {
	    		throw new DaoException("Friend creation failed.");	    		
	    	}
	    	
	    	resultSet = preparedStatement.getGeneratedKeys();
	    	
	    	if(resultSet.next()) {
	    		newFriend = resultSetToFriendEntity(resultSet);
	    	}else {
	    		throw new DaoException("Friend creation failed.");
	    	}
	    	
		} catch (SQLException e) {
			throw new DaoException(e);
		}finally {
			UtilDao.silentClose(resultSet, preparedStatement, connection);
		}
	    
		return newFriend;
	}

	@Override
	public void delete(Friend entity) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
		
	    String sqlReq = String.format(SqlPattern.DELETE, FriendContract.TABLE_NAME, 
	    		FriendContract.COL_USER_ID_NAME+" = ? AND "+FriendContract.COL_FRIEND_ID_NAME+" = ?");
		
	    try {
	    	connection = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, false, 
	    			entity.getUser().getId(), entity.getFriend().getId());
	    	
	    	int status = preparedStatement.executeUpdate();
	    	
	    	if(status == 0) {
	    		throw new DaoException("Friend delete failed.");	    		
	    	}
	    	
		} catch (SQLException e) {
			throw new DaoException(e);
		}finally {
			UtilDao.silentClose(preparedStatement, connection);
		}
	}

	@Override
	public Friend update(Friend entity) {
		throw new DaoException("Method not implemented.");
	}
	
	@Override
	protected List<Friend> find(String columnName, String operator, Object value) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<Friend> friends = new ArrayList<Friend>();
		
	    String sqlReq = String.format(SqlPattern.SELECT,"*",FriendContract.TABLE_NAME, columnName+" "+operator+" ?");
		
	    try {
	    	connection = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, false, value);
	    	
	    	resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
	    		friends.add(resultSetToFriendEntity(resultSet));
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		}finally {
			UtilDao.silentClose(resultSet, preparedStatement, connection);
		}
		
		return friends;
	}
	
	public List<Friend> findByUser(User user) {
		List<Friend> friends = find(FriendContract.COL_USER_ID_NAME, "=", user.getId());
		return friends;
	}
	
	private Friend resultSetToFriendEntity(ResultSet resultSet) throws SQLException {
		
		UserDao userDao = new UserDao(this.connectionPool);
		
		User user = userDao.findById(resultSet.getString(FriendContract.COL_USER_ID_NAME));
		User friendUser = userDao.findById(resultSet.getString(FriendContract.COL_FRIEND_ID_NAME));
		
		Friend friend = new Friend();
		friend.setUser(user);
		friend.setFriend(friendUser);
		friend.setCreatedAt(resultSet.getTimestamp(FriendContract.COL_CREATED_AT_NAME));
		
		return friend;
	}
}