package fr.jdiot.wevent.dao.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.jdiot.wevent.dao.common.ConnectionPool;
import fr.jdiot.wevent.dao.contract.EventContract;
import fr.jdiot.wevent.dao.entity.Event;
import fr.jdiot.wevent.dao.entity.User;
import fr.jdiot.wevent.dao.exception.DaoException;
import fr.jdiot.wevent.dao.util.ResultSetToEntity;
import fr.jdiot.wevent.dao.util.SqlPattern;
import fr.jdiot.wevent.dao.util.UtilDao;

public final class EventDao extends CommonDao<Event> implements ResultSetToEntity<Event> {
	
	protected static final Logger logger = LogManager.getLogger();

	public EventDao(ConnectionPool connectionPool) {
		super(connectionPool);
	}

	@Override
	public Event create(Event entity) {
		
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    List<Event> updatedEvents = new ArrayList<Event>();
	    
	    String sqlReq = String.format(SqlPattern.INSERT, EventContract.TABLE_NAME,
	    		  EventContract.COL_ADMIN_ID_NAME+","
	    		+ EventContract.COL_TITLE_NAME+","
	    		+ EventContract.COL_START_DATE_NAME+","
	    		+ EventContract.COL_END_DATE_NAME+","
	    		+ EventContract.COL_CONTENT_NAME
	    		, "?,?,?,?,?");
	    
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, 
	    			entity.getAdmin().getId(),
	    			entity.getTitle(),
	    			entity.getStartDate(),
	    			entity.getEndDate(),
	    			entity.getContent());
	    	
	    	updatedEvents = UtilDao.executeCreate(preparedStatement, this);
	    	
	    	
			if(updatedEvents.size() < 1 ) {
				throw logger.throwing(Level.ERROR,new DaoException("SQL query failed !"));
			}
	    	
		} catch (SQLException e) {
			throw logger.throwing(Level.ERROR,new DaoException(e));
			
		}finally {
			UtilDao.silentClose(preparedStatement, connexion);
		}
	    
		return updatedEvents.get(0);
	}

	@Override
	public Event delete(Event entity) {
		
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    List<Event> updatedEvents = new ArrayList<Event>();
	    
	    String sqlReq = String.format(SqlPattern.DELETE, EventContract.TABLE_NAME, EventContract.COL_ID_NAME+" = ?");
	    
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, entity.getId());
	    	
	    	updatedEvents = UtilDao.executeDelete(preparedStatement, this);
	    	
	    	
			if(updatedEvents.size() < 1) {
				throw logger.throwing(Level.ERROR,new DaoException("SQL query failed !"));
			}
	    	
		} catch (SQLException e) {
			
			throw logger.throwing(Level.ERROR,new DaoException(e));
			
		}finally {
			UtilDao.silentClose(preparedStatement, connexion);
		}
	    
		return updatedEvents.get(0);
	}

	@Override
	public Event update(Event entity) {
		
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    List<Event> updatedEvents = new ArrayList<Event>();
	    
	    String sqlReq = String.format(SqlPattern.UPDATE, EventContract.TABLE_NAME,
	    		  EventContract.COL_ADMIN_ID_NAME+" = ?,"
	    		+ EventContract.COL_TITLE_NAME+" = ?,"
	    		+ EventContract.COL_START_DATE_NAME+" = ?,"
	    		+ EventContract.COL_END_DATE_NAME+" = ?,"
	    		+ EventContract.COL_CONTENT_NAME+" = ?"
	    		, EventContract.COL_ID_NAME+" = ?");
	    
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, 
	    			entity.getAdmin().getId(),
	    			entity.getTitle(),
	    			entity.getStartDate(),
	    			entity.getEndDate(),
	    			entity.getContent(),
	    			entity.getId());
	    	
	    	updatedEvents = UtilDao.executeUpdate(preparedStatement, this);
	    	
	    	if(updatedEvents.size() < 1) {
				throw logger.throwing(Level.ERROR,new DaoException("SQL query failed !"));
			}
	    	
		} catch (SQLException e) {
			throw logger.throwing(Level.ERROR,new DaoException(e));
		}finally {
			UtilDao.silentClose(preparedStatement, connexion);
		}
	    
		return updatedEvents.get(0);
	}

	@Override
	protected List<Event> read(String columnName, String operator, Object value) {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    List<Event> events = new ArrayList<Event>();
	    
	    String sqlReq = String.format(SqlPattern.SELECT,"*",EventContract.TABLE_NAME, columnName+" "+operator+" ?");
	    
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, value);
	    	
	    	events = UtilDao.executeRead(preparedStatement, this);
	    	
		} catch (SQLException e) {
			throw logger.throwing(Level.ERROR,new DaoException(e));
		}finally {
			UtilDao.silentClose(preparedStatement, connexion);
		}
	    
		return events;
	}
	
	public Event findById(String id) {
		List<Event> events = read(EventContract.COL_ID_NAME,"=", id); 
		return events.get(0);
	}
	
	public List<Event> findByAdmin(User admin) {
		List<Event> events = read(EventContract.COL_ADMIN_ID_NAME,"=", admin.getId()); 
		return events;
	}
	
	public List<Event> findByTitle(String title) {
		List<Event> events = read(EventContract.COL_TITLE_NAME,"~*", ".*"+title+".*"); 
		return events;
	}
	
	public List<Event> findByDateGreater(Timestamp date) {
		List<Event> events = read(EventContract.COL_START_DATE_NAME,">=",date); 
		return events;
	}
	
	public List<Event> findByDateLess(Timestamp date) {
		List<Event> events = read(EventContract.COL_START_DATE_NAME,"<=",date); 
		return events;
	}

	@Override
	public Event convert(ResultSet resultSet) throws SQLException{
		UserDao userDao = new UserDao(this.connectionPool);
		
		User userAdmin = userDao.findById(resultSet.getString(EventContract.COL_ADMIN_ID_NAME));
		
		Event event = new Event();
		
		event.setId(resultSet.getString(EventContract.COL_ID_NAME));
		event.setAdmin(userAdmin);
		event.setTitle(resultSet.getString(EventContract.COL_TITLE_NAME));
		event.setStartDate(resultSet.getTimestamp(EventContract.COL_START_DATE_NAME));
		event.setEndDate(resultSet.getTimestamp(EventContract.COL_END_DATE_NAME));
		event.setContent(resultSet.getString(EventContract.COL_CONTENT_NAME));
		event.setCreatedAt(resultSet.getTimestamp(EventContract.COL_CREATED_AT_NAME));
		event.setUpdatedAt(resultSet.getTimestamp(EventContract.COL_UPDATED_AT_NAME));
		
		return event;
	}
}
