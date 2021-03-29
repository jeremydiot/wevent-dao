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

	public static DaoFactory getInstance(String host, String port, String database, String user, String password) {
		return new DaoFactory(ConnectionPool.getInstance(host ,port, database, user, password));
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
