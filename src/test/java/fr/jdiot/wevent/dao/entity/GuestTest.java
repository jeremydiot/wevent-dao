package fr.jdiot.wevent.dao.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GuestTest {

	Guest guest;
	
	@Mock
	User user;
	
	@Mock
	Event event;
	
	@Mock
	Timestamp timestamp;
	
	@BeforeEach
	void init() {
		guest = new Guest();
	}
	
	@Test
	void userTest() {
		guest.setUser(user);
		assertEquals(guest.getUser(), user);
	}
	
	@Test
	void eventTest() {
		guest.setEvent(event);
		assertEquals(guest.getEvent(), event);
	}
	
	@Test
	void createdAtTest() {
		guest.setCreatedAt(timestamp);
		assertEquals(guest.getCreatedAt(), timestamp);
	}
	
	@Test
	void toStringTest() {
		assertTrue(guest.toString() instanceof String);
	}

}
