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
public class UserTest {

	User user;
	
	@Mock
	Timestamp timestamp;
	
	@BeforeEach
	void init() {
		user = new User();
	}
	
	@Test
	void idTest() {
		user.setId("id-test");
		assertEquals(user.getId(), "id-test");
	}
	
	@Test
	void usernameTest() {
		user.setUsername("username-test");
		assertEquals(user.getUsername(), "username-test");
	}
	
	@Test
	void passwordTest() {
		user.setPassword("password-test");
		assertEquals(user.getPassword(), "password-test");
		
	}
	
	@Test
	void emailTest() {
		user.setEmail("email-test");
		assertEquals(user.getEmail(), "email-test");

	}
	
	@Test
	void connectedAtTest() {
		user.setConnectedAt(timestamp);
		assertEquals(user.getConnectedAt(), timestamp);

	}

	@Test
	void createdAtTest() {
		user.setCreatedAt(timestamp);
		assertEquals(user.getCreatedAt(), timestamp);
		
	}

	@Test
	void modifiedAtTest() {
		user.setUpdatedAt(timestamp);
		assertEquals(user.getUpdatedAt(), timestamp);

	}
	
	@Test
	void toStringTest() {
		assertTrue(user.toString() instanceof String);
	}


}
