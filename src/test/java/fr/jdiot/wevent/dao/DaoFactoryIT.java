package fr.jdiot.wevent.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.jdiot.wevent.dao.core.EventDao;
import fr.jdiot.wevent.dao.core.FriendDao;
import fr.jdiot.wevent.dao.core.GuestDao;
import fr.jdiot.wevent.dao.core.UserDao;
import fr.jdiot.wevent.dao.util.UtilProperties;

class DaoFactoryIT {
	
	static String DB_HOST = UtilProperties.getTestProperty("db.host");
	static String DB_PORT = UtilProperties.getTestProperty("db.port");
	static String DB_NAME = UtilProperties.getTestProperty("db.name");
	static String DB_USER = UtilProperties.getTestProperty("db.user");
	static String DB_PSSWD = UtilProperties.getTestProperty("db.psswd");
	
	DaoFactory daoFactory = new DaoFactory(DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PSSWD);  
	
	@Test
	void getInstanceTest() {

		assertNotEquals(daoFactory, new DaoFactory(DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PSSWD));
		assertTrue( daoFactory instanceof DaoFactory);
	}
	
	@Test
	void getEventDaoTest() {
		assertNotEquals(daoFactory.getEventDao(), daoFactory.getEventDao());
		assertTrue(daoFactory.getEventDao() instanceof EventDao);
	}
	
	@Test
	void getFriendDao() {
		assertNotEquals(daoFactory.getFriendDao(), daoFactory.getFriendDao());
		assertTrue(daoFactory.getFriendDao() instanceof FriendDao);
	}
	
	@Test
	void getGuestDao() {
		assertNotEquals(daoFactory.getGuestDao(), daoFactory.getGuestDao());
		assertTrue(daoFactory.getGuestDao() instanceof GuestDao);
	}
	
	@Test
	void getUserDao() {
		assertNotEquals(daoFactory.getUserDao(), daoFactory.getUserDao());
		assertTrue(daoFactory.getUserDao() instanceof UserDao);
	}
}
	