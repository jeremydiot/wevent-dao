package fr.jdiot.wevent.dao.util;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UtilDaoTest {

	@Mock
	private Connection connection;
	
	@Mock
	private Statement statement;
	
	@Mock
	private ResultSet resultSet;
	
	@Mock
	private PreparedStatement preparedStatement;
	
	@Test
	void initPreparedStmtTestWithArg() throws SQLException {
		
		Mockito.when(connection.prepareStatement("foo ? baz ?",Statement.NO_GENERATED_KEYS)).thenReturn(preparedStatement);
		
		PreparedStatement preparedStatementTest = UtilDao.initPreparedStmt(connection, "foo ? baz ?", false, "bar", "test");
		
		Mockito.verify(connection).prepareStatement("foo ? baz ?",Statement.NO_GENERATED_KEYS);
		Mockito.verify(preparedStatement, Mockito.times(1)).setObject(1, "bar");
		Mockito.verify(preparedStatement, Mockito.times(1)).setObject(2, "test");
		
		assertEquals(preparedStatementTest, preparedStatement);	
	}

	@Test
	void initPreparedStmtTestNoArg() throws SQLException {
		
		Mockito.when(connection.prepareStatement("foo baz",Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
		
		PreparedStatement preparedStatementTest = UtilDao.initPreparedStmt(connection, "foo baz", true);
		
		Mockito.verify(connection).prepareStatement("foo baz",Statement.RETURN_GENERATED_KEYS);
		Mockito.verifyNoMoreInteractions(preparedStatement);
		
		assertEquals(preparedStatementTest, preparedStatement);	
	}
	
	@Test
	void silentCloseTest() throws SQLException {
		UtilDao.silentClose(resultSet, statement, connection);
		
		Mockito.verify(resultSet, Mockito.times(1)).close();
		Mockito.verify(statement, Mockito.times(1)).close();
		Mockito.verify(connection, Mockito.times(1)).close();
	}
	
	@Test
	void silentCloseTestException() throws SQLException {
		
		Mockito.doThrow(SQLException.class).when(resultSet).close();
		Mockito.doThrow(SQLException.class).when(statement).close();
		Mockito.doThrow(SQLException.class).when(connection).close();
		
		UtilDao.silentClose(resultSet, statement, connection);
		
		Mockito.verify(resultSet, Mockito.times(1)).close();
		Mockito.verify(statement, Mockito.times(1)).close();
		Mockito.verify(connection, Mockito.times(1)).close();
	}
	
	@Test
	void silentCloseTestNull() {
		assertDoesNotThrow(() -> {
			UtilDao.silentClose(null, null, null);
		});
	}
	
}
