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
public class FriendTest {
	
	Friend friend;
	
	@Mock
	User user;
	
	@Mock
	Timestamp timestamp;
	
	@BeforeEach
	void init() {
		friend  = new Friend();
	}
	
	@Test
	void userTest() {
		friend.setUser(user);
		assertEquals(friend.getUser(), user);
	}
	
	@Test
	void friendTest() {
		friend.setFriend(user);
		assertEquals(friend.getFriend(), user);
	}
	
	@Test
	void createdAtTest() {
		friend.setCreatedAt(timestamp);
		assertEquals(friend.getCreatedAt(), timestamp);
	}
	
	@Test
	void toStringTest() {
		assertTrue(friend.toString() instanceof String);
	}
}
