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
import fr.jdiot.wevent.dao.contract.GuestContract;
import fr.jdiot.wevent.dao.entity.Event;
import fr.jdiot.wevent.dao.entity.Guest;
import fr.jdiot.wevent.dao.entity.User;
import fr.jdiot.wevent.dao.exception.DaoException;
import fr.jdiot.wevent.dao.util.SqlPattern;
import fr.jdiot.wevent.dao.util.UtilDao;

public final class GuestDao extends CommonDao<Guest> {
	
	protected static final Logger logger = LogManager.getLogger();

	public GuestDao(ConnectionPool connectionPool) {
		super(connectionPool);
	}

	@Override
	public Guest create(Guest entity) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Guest newGuest = null;
		
	    String sqlReq = String.format(SqlPattern.INSERT, GuestContract.TABLE_NAME,
	    		GuestContract.COL_USER_ID_NAME+","
	    		+ GuestContract.COL_EVENT_ID_NAME+","
	    		, "?,?");
		
	    try {
	    	connection = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, true, 
	    			entity.getUser().getId(),
	    			entity.getEvent().getId());
	    	
	    	int status = preparedStatement.executeUpdate();
	    	
	    	if(status == 0) {
	    		throw logger.throwing(Level.ERROR,new DaoException("Guest creation failed."));	    		
	    	}
	    	
	    	resultSet = preparedStatement.getGeneratedKeys();
	    	
	    	if(resultSet.next()) {
	    		newGuest = resultSetToGuestEntity(resultSet);
	    	}else {
	    		throw logger.throwing(Level.ERROR,new DaoException("Guest creation failed."));
	    	}
	    	
		} catch (SQLException e) {
			throw logger.throwing(Level.ERROR,new DaoException(e));
		}finally {
			UtilDao.silentClose(resultSet, preparedStatement, connection);
		}
	    
		return newGuest;
	}

	@Override
	public void delete(Guest entity) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
		
	    String sqlReq = String.format(SqlPattern.DELETE, GuestContract.TABLE_NAME,
	    		GuestContract.COL_USER_ID_NAME+" = ? AND "+GuestContract.COL_EVENT_ID_NAME+" = ?");
		
	    try {
	    	connection = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, false, 
	    			entity.getUser().getId(), entity.getEvent().getId());
	    	
	    	int status = preparedStatement.executeUpdate();
	    	
	    	if(status == 0) {
	    		throw new DaoException("Guest delete failed.");	    		
	    	}
	    	
		} catch (SQLException e) {
			throw logger.throwing(Level.ERROR,new DaoException(e));
		}finally {
			UtilDao.silentClose(preparedStatement, connection);
		}
	}

	@Override
	public Guest update(Guest entity) {
		logger.error(new DaoException("Method not implemented."));
		return null;
	}

	@Override
	protected List<Guest> find(String columnName, String operator, Object value) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<Guest> friends = new ArrayList<Guest>();
		
	    String sqlReq = String.format(SqlPattern.SELECT,"*",GuestContract.TABLE_NAME, columnName+" "+operator+" ?");
		
	    try {
	    	connection = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connection, sqlReq, false, value);
	    	
	    	resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
	    		friends.add(resultSetToGuestEntity(resultSet));
			}
			
		} catch (SQLException e) {
			throw logger.throwing(Level.ERROR,new DaoException(e));
		}finally {
			UtilDao.silentClose(resultSet, preparedStatement, connection);
		}
		
		return friends;
	}
	
	public List<Guest> findByEvent(Event event) {
		List<Guest> guests = find(GuestContract.COL_EVENT_ID_NAME, "=", event.getId());
		return guests;
	}
	
	public List<Guest> findByUser(User user) {
		List<Guest> guests = find(GuestContract.COL_USER_ID_NAME, "=", user.getId());
		return guests;
	}
	
	private Guest resultSetToGuestEntity(ResultSet resultSet){
		
		UserDao userDao = new UserDao(this.connectionPool);
		EventDao eventDao = new EventDao(this.connectionPool);
		Guest guest = new Guest();
		
		try {
			User user = userDao.findById(resultSet.getString(GuestContract.COL_USER_ID_NAME));
			Event event = eventDao.findById(resultSet.getString(GuestContract.COL_EVENT_ID_NAME));
			
			guest.setUser(user);
			guest.setEvent(event);
			guest.setCreatedAt(resultSet.getTimestamp(GuestContract.COL_CREATED_AT_NAME));
		} catch (SQLException e) {
			throw logger.throwing(Level.ERROR,new DaoException(e));
		}

		return guest;
	}

}
