package fr.jdiot.wevent.dao;

import fr.jdiot.wevent.dao.common.ConnectionPool;
import fr.jdiot.wevent.dao.common.DbManager;
import fr.jdiot.wevent.dao.core.EventDao;
import fr.jdiot.wevent.dao.core.FriendDao;
import fr.jdiot.wevent.dao.core.GuestDao;
import fr.jdiot.wevent.dao.core.UserDao;

public final class DaoFactory {
	
	private ConnectionPool connectionPool;
	
	private DaoFactory(ConnectionPool connectionPoolArg) {
		this.connectionPool = connectionPoolArg;
	}

	public static DaoFactory getInstance(String url, String user, String password) {
		ConnectionPool connectionPool = ConnectionPool.getInstance(url, user, password);
		return new DaoFactory(connectionPool);
	}
	
	public ConnectionPool getConnectionPool() {
		return this.connectionPool;
	}
	
	public DbManager getDbManager() {
		return new DbManager(connectionPool);
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
