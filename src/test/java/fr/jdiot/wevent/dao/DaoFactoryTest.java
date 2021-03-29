package fr.jdiot.wevent.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.jdiot.wevent.dao.common.ConnectionPool;
import fr.jdiot.wevent.dao.core.EventDao;
import fr.jdiot.wevent.dao.core.FriendDao;
import fr.jdiot.wevent.dao.core.GuestDao;
import fr.jdiot.wevent.dao.core.UserDao;

@ExtendWith(MockitoExtension.class)
class DaoFactoryTest {
	
	@Mock
	ConnectionPool connectionPool;
	
	@InjectMocks
	DaoFactory daoFactory;
	
	
	@Test
	void getInstanceTest() {
		String host = "wrongHost";
		String port = "5432";
		String database = "wevent";
		String user = "root";
		String password = "root";
		
		assertTrue(DaoFactory.getInstance(host, port, database, user, password) instanceof DaoFactory);
	}
	
	@Test
	void getEventDaoTest() {
		assertTrue(daoFactory.getEventDao() instanceof EventDao);
	}
	
	@Test
	void getFriendDao() {
		assertTrue(daoFactory.getFriendDao() instanceof FriendDao);
	}
	
	@Test
	void getGuestDao() {
		assertTrue(daoFactory.getGuestDao() instanceof GuestDao);
	}
	
	@Test
	void getUserDao() {
		assertTrue(daoFactory.getUserDao() instanceof UserDao);
	}

}
	