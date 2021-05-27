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
public class EventTest {

	Event event;
	
	@Mock
	User user;
	
	@Mock
	Timestamp timestamp;
	
	@BeforeEach
	void init() {
		event = new Event();
	}
	
	@Test
	void idTest() {
		event.setId("id-test");
		assertEquals(event.getId(), "id-test");
	}
	
	@Test
	void titleTest() {
		event.setTitle("title-test");
		assertEquals(event.getTitle(), "title-test");
	}
	
	@Test
	void contentTest() {
		event.setContent("content-test");
		assertEquals(event.getContent(), "content-test");
	}
	
	@Test
	void adminTest() {
		event.setAdmin(user);
		assertEquals(event.getAdmin(), user);
	}

	@Test
	void startDateTest() {
		event.setStartDate(timestamp);
		assertEquals(event.getStartDate(), timestamp);
	}
	
	@Test
	void endDateTest() {
		event.setEndDate(timestamp);
		assertEquals(event.getEndDate(), timestamp);
	}
	
	@Test
	void createdAtTest() {
		event.setCreatedAt(timestamp);
		assertEquals(event.getCreatedAt(), timestamp);
	}
	
	@Test
	void modifiedAtTest() {
		event.setUpdatedAt(timestamp);
		assertEquals(event.getUpdatedAt(), timestamp);
	}
	
	@Test
	void toSitringTest() {
		assertTrue(event.toString() instanceof String);
	}
	
}
