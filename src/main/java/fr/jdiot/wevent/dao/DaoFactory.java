package fr.jdiot.wevent.dao;

import fr.jdiot.wevent.dao.common.ConnectionPool;
import fr.jdiot.wevent.dao.core.EventDao;
import fr.jdiot.wevent.dao.core.FriendDao;
import fr.jdiot.wevent.dao.core.GuestDao;
import fr.jdiot.wevent.dao.core.UserDao;

public final class DaoFactory {
	
	private ConnectionPool connectionPool;
	
	public DaoFactory(String host, String port, String database, String user, String password) {
		this.connectionPool =  ConnectionPool.init(host ,port, database, user, password);
	}
	
	public DaoFactory(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
	
	public EventDao getEventDao() {
		return new EventDao(connectionPool);
	}
	
	public FriendDao getFriendDao() {
		return new FriendDao(connectionPool);
	}
	
	public GuestDao getGuestDao() {
		return new GuestDao(connectionPool);
	}
	
	public UserDao getUserDao() {
		return new UserDao(connectionPool);
	}
	
	
}
