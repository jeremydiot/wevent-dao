package fr.jdiot.wevent.dao.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.jdiot.wevent.dao.common.ConnectionPool;
import fr.jdiot.wevent.dao.contract.EventContract;
import fr.jdiot.wevent.dao.entity.Event;
import fr.jdiot.wevent.dao.entity.User;
import fr.jdiot.wevent.dao.exception.DaoException;
import fr.jdiot.wevent.dao.util.SqlPattern;
import fr.jdiot.wevent.dao.util.UtilDao;

public final class EventDao extends CommonDao<Event> {

	public EventDao(ConnectionPool connectionPool) {
		super(connectionPool);
	}

	@Override
	public Event create(Event entity) {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Event newEvent = null;
	    
	    String sqlReq = String.format(SqlPattern.INSERT, EventContract.TABLE_NAME,
	    		  EventContract.COL_ADMIN_ID_NAME+","
	    		+ EventContract.COL_TITLE_NAME+","
	    		+ EventContract.COL_START_DATE_NAME+","
	    		+ EventContract.COL_END_DATE_NAME+","
	    		+ EventContract.COL_CONTENT_NAME
	    		, "?,?,?,?,?");
	    
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, true, 
	    			entity.getAdmin().getId(),
	    			entity.getTitle(),
	    			entity.getStartDate(),
	    			entity.getEndDate(),
	    			entity.getContent());
	    	
	    	int status = preparedStatement.executeUpdate();
	    	
	    	if(status == 0) {
	    		throw new DaoException("Event creation failed.");	    		
	    	}
	    	
	    	resultSet = preparedStatement.getGeneratedKeys();
	    	
	    	if(resultSet.next()) {
	    		newEvent = resultSetToEventEntity(resultSet);
	    	}else {
	    		throw new DaoException("Event creation failed.");
	    	}
	    	
		} catch (SQLException e) {
			throw new DaoException(e);
		}finally {
			UtilDao.silentClose(resultSet, preparedStatement, connexion);
		}
	    
		return newEvent;
	}

	@Override
	public void delete(Event entity) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    
	    String sqlReq = String.format(SqlPattern.DELETE, EventContract.TABLE_NAME, EventContract.COL_ID_NAME+" = ?");
	    
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, false, entity.getId());
	    	
	    	int status = preparedStatement.executeUpdate();
	    	
	    	if(status == 0) {
	    		throw new DaoException("Event delete failed.");	    		
	    	}
	    	
		} catch (SQLException e) {
			throw new DaoException(e);
		}finally {
			UtilDao.silentClose(preparedStatement, connexion);
		}
	}

	@Override
	public Event update(Event entity) {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Event updatedEvent = null;
	    
	    String sqlReq = String.format(SqlPattern.UPDATE, EventContract.TABLE_NAME,
	    		  EventContract.COL_ADMIN_ID_NAME+" = ?,"
	    		+ EventContract.COL_TITLE_NAME+" = ?,"
	    		+ EventContract.COL_START_DATE_NAME+" = ?,"
	    		+ EventContract.COL_END_DATE_NAME+" = ?,"
	    		+ EventContract.COL_CONTENT_NAME+" = ?"
	    		, EventContract.COL_ID_NAME+" = ?");
	    
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, true, 
	    			entity.getAdmin().getId(),
	    			entity.getTitle(),
	    			entity.getStartDate(),
	    			entity.getEndDate(),
	    			entity.getContent(),
	    			entity.getId());
	    	
	    	int status = preparedStatement.executeUpdate();
	    	
	    	if(status == 0) {
	    		throw new DaoException("Event update failed.");	    		
	    	}
	    	
	    	resultSet = preparedStatement.getGeneratedKeys();
	    	
	    	if(resultSet.next()) {
	    		updatedEvent = resultSetToEventEntity(resultSet);
	    	}else {
	    		throw new DaoException("Event creation failed.");
	    	}
	    	
		} catch (SQLException e) {
			throw new DaoException(e);
		}finally {
			UtilDao.silentClose(resultSet, preparedStatement, connexion);
		}
	    
		return updatedEvent;
	}

	@Override
	protected List<Event> find(String columnName, String operator, Object value) {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<Event> events = new ArrayList<Event>();
	    
	    String sqlReq = String.format(SqlPattern.SELECT,"*",EventContract.TABLE_NAME, columnName+" "+operator+" ?");
	    
	    try {
	    	connexion = this.connectionPool.getConnection();
	    	preparedStatement = UtilDao.initPreparedStmt(connexion, sqlReq, false, value);
	    	
	    	resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
	    		events.add(resultSetToEventEntity(resultSet));
	    	}
	    	
		} catch (SQLException e) {
			throw new DaoException(e);
		}finally {
			UtilDao.silentClose(resultSet, preparedStatement, connexion);
		}
	    
		return events;
	}
	
	public Event findById(String id) {
		List<Event> events = find(EventContract.COL_ID_NAME,"=", id); 
		return events.get(0);
	}
	
	public List<Event> findByAdmin(User admin) {
		List<Event> events = find(EventContract.COL_ADMIN_ID_NAME,"=", admin.getId()); 
		return events;
	}
	
	public List<Event> findByTitle(String title) {
		List<Event> events = find(EventContract.COL_TITLE_NAME,"~*", ".*"+title+".*"); 
		return events;
	}
	
	public List<Event> findByDateGreater(Timestamp date) {
		List<Event> events = find(EventContract.COL_START_DATE_NAME,">=",date); 
		return events;
	}
	
	public List<Event> findByDateLess(Timestamp date) {
		List<Event> events = find(EventContract.COL_START_DATE_NAME,"<=",date); 
		return events;
	}
	
	private Event resultSetToEventEntity(ResultSet resultSet) throws SQLException {
		
		UserDao userDao = new UserDao(this.connectionPool);
		User admin = userDao.findById(resultSet.getString(EventContract.COL_ADMIN_ID_NAME));
		
		Event event = new Event();
		event.setId(resultSet.getString(EventContract.COL_ID_NAME));
		event.setAdmin(admin);
		event.setTitle(resultSet.getString(EventContract.COL_TITLE_NAME));
		event.setStartDate(resultSet.getTimestamp(EventContract.COL_START_DATE_NAME));
		event.setEndDate(resultSet.getTimestamp(EventContract.COL_END_DATE_NAME));
		event.setContent(resultSet.getString(EventContract.COL_CONTENT_NAME));
		event.setCreatedAt(resultSet.getTimestamp(EventContract.COL_CREATED_AT_NAME));
		event.setModifiedAt(resultSet.getTimestamp(EventContract.COL_MODIFIED_AT_NAME));
		
		return event;
	}
}
