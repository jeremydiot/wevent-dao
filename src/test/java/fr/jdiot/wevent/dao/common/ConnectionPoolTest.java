package fr.jdiot.wevent.dao.common;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConnectionPoolTest {

	@Mock
	BasicDataSource basicDataSource;
	
	
	@Test
	void test() {
		assertTrue(true);
	}

}
