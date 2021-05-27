package fr.jdiot.wevent.dao.util;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.jdiot.wevent.dao.exception.DaoException;

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
		
		Mockito.when(connection.prepareStatement(Mockito.anyString(),Mockito.anyInt())).thenReturn(preparedStatement);
		
		PreparedStatement preparedStatementTest = UtilDao.initPreparedStmt(connection, "foo ? baz ?", false, "bar", "test");
		
		Mockito.verify(connection, Mockito.times(1)).prepareStatement("foo ? baz ?",2);
		Mockito.verify(preparedStatement, Mockito.times(1)).setObject(1, "bar");
		Mockito.verify(preparedStatement, Mockito.times(1)).setObject(2, "test");
		Mockito.verify(preparedStatement, Mockito.times(2)).setObject(Mockito.anyInt(), Mockito.anyString());
		
		assertEquals(preparedStatementTest, preparedStatement);	
	}

	@Test
	void initPreparedStmtTestWithoutArg() throws SQLException {
		
		Mockito.when(connection.prepareStatement(Mockito.anyString(),Mockito.anyInt())).thenReturn(preparedStatement);
		
		PreparedStatement preparedStatementTest = UtilDao.initPreparedStmt(connection, "foo baz", true);
		
		Mockito.verify(connection, Mockito.times(1)).prepareStatement("foo baz",1);
		Mockito.verify(preparedStatement, Mockito.times(0)).setObject(Mockito.anyInt(), Mockito.any());
		
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
		
		ResultSet resultSetTest = null;
		Statement statementTest = null;
		Connection connectionTest = null;
		
		UtilDao.silentClose(resultSetTest, statementTest, connectionTest);
		
	}
	
	@Test
	void executeCreateTest() throws SQLException {
		Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
		Mockito.when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
		Mockito.when(resultSet.next()).thenReturn(true, true, false);
		Mockito.when(resultSet.getString(Mockito.anyInt())).thenReturn("resultSet1", "resultSet2");
		
		List<String> returnedValues = UtilDao.executeCreate(preparedStatement, new ResultSetToEntity<String>() {

				@Override
				public String convert(ResultSet resultset) {
					String value = null;
					try {
						value = resultset.getString(1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return value;
				}
			});
		
		Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
		Mockito.verify(preparedStatement, Mockito.times(1)).getGeneratedKeys();
		Mockito.verify(resultSet, Mockito.times(3)).next();
		Mockito.verify(resultSet, Mockito.times(2)).getString(Mockito.anyInt());
		Mockito.verify(resultSet, Mockito.times(1)).close();
		
		assertEquals(returnedValues.get(0), "resultSet1");
		assertEquals(returnedValues.get(1), "resultSet2");
	}
	
	@Test
	void executeUpdateTest() throws SQLException {
		Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
		Mockito.when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
		Mockito.when(resultSet.next()).thenReturn(true, true, false);
		Mockito.when(resultSet.getString(Mockito.anyInt())).thenReturn("resultSet1", "resultSet2");
		
		List<String> returnedValues = UtilDao.executeUpdate(preparedStatement, new ResultSetToEntity<String>() {

				@Override
				public String convert(ResultSet resultset) {
					String value = null;
					try {
						value = resultset.getString(1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return value;
				}
			});
		
		Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
		Mockito.verify(preparedStatement, Mockito.times(1)).getGeneratedKeys();
		Mockito.verify(resultSet, Mockito.times(3)).next();
		Mockito.verify(resultSet, Mockito.times(2)).getString(Mockito.anyInt());
		Mockito.verify(resultSet, Mockito.times(1)).close();
		
		assertEquals(returnedValues.get(0), "resultSet1");
		assertEquals(returnedValues.get(1), "resultSet2");
	}
	
	@Test
	void executeDeleteTest() throws SQLException {
		Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
		Mockito.when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
		Mockito.when(resultSet.next()).thenReturn(true, true, false);
		Mockito.when(resultSet.getString(Mockito.anyInt())).thenReturn("resultSet1", "resultSet2");
		
		List<String> returnedValues = UtilDao.executeDelete(preparedStatement, new ResultSetToEntity<String>() {

				@Override
				public String convert(ResultSet resultset) {
					String value = null;
					try {
						value = resultset.getString(1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return value;
				}
			});
		
		Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
		Mockito.verify(preparedStatement, Mockito.times(1)).getGeneratedKeys();
		Mockito.verify(resultSet, Mockito.times(3)).next();
		Mockito.verify(resultSet, Mockito.times(2)).getString(Mockito.anyInt());
		Mockito.verify(resultSet, Mockito.times(1)).close();
		
		assertEquals(returnedValues.get(0), "resultSet1");
		assertEquals(returnedValues.get(1), "resultSet2");
	}
	
	@Test
	void executeCreateTestStatusException() throws SQLException {
		Mockito.when(preparedStatement.executeUpdate()).thenReturn(0);
		
		assertThrows(DaoException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				UtilDao.executeCreate(preparedStatement, new ResultSetToEntity<String>() {

					@Override
					public String convert(ResultSet resultset) {
						String value = null;
						try {
							value = resultset.getString(1);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return value;
					}
				});

				
			}
		});
		
				
		Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
		Mockito.verify(preparedStatement, Mockito.times(0)).getGeneratedKeys();
		Mockito.verify(resultSet, Mockito.times(0)).next();
		Mockito.verify(resultSet, Mockito.times(0)).getString(Mockito.anyInt());
		Mockito.verify(resultSet, Mockito.times(0)).close();

	}
	
	@Test
	void executeCreateTestSQLException() throws SQLException {
		Mockito.doThrow(SQLException.class).when(preparedStatement).executeUpdate();
		
		assertThrows(DaoException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				UtilDao.executeCreate(preparedStatement, new ResultSetToEntity<String>() {

					@Override
					public String convert(ResultSet resultset) {
						String value = null;
						try {
							value = resultset.getString(1);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return value;
					}
				});

				
			}
		});
		
				
		Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
		Mockito.verify(preparedStatement, Mockito.times(0)).getGeneratedKeys();
		Mockito.verify(resultSet, Mockito.times(0)).next();
		Mockito.verify(resultSet, Mockito.times(0)).getString(Mockito.anyInt());
		Mockito.verify(resultSet, Mockito.times(0)).close();

	}
	
	
	@Test
	void  executeReadTest() throws SQLException {
		Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
		Mockito.when(resultSet.next()).thenReturn(true, true, false);
		Mockito.when(resultSet.getString(Mockito.anyInt())).thenReturn("resultSet1","resultSet2");
		
		List<String> returnedValues = UtilDao.executeRead(preparedStatement, new ResultSetToEntity<String>() {

			@Override
			public String convert(ResultSet resultSet) {
				String value = null;
				try {
					value = resultSet.getString(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return value;
			}
		});
		
		Mockito.verify(preparedStatement,Mockito.times(1)).executeQuery();
		Mockito.verify(resultSet,Mockito.times(3)).next();
		Mockito.verify(resultSet,Mockito.times(2)).getString(Mockito.anyInt());
		Mockito.verify(resultSet,Mockito.times(1)).close();
		
		assertEquals(returnedValues.get(0), "resultSet1");
		assertEquals(returnedValues.get(1), "resultSet2");
		
	}
	
	@Test
	void  executeReadTestSQLException() throws SQLException {
		Mockito.doThrow(SQLException.class).when(preparedStatement).executeQuery();
		
		assertThrows(DaoException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				UtilDao.executeRead(preparedStatement, new ResultSetToEntity<String>() {

					@Override
					public String convert(ResultSet resultSet) {
						String value = null;
						try {
							value = resultSet.getString(1);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return value;
					}
				});
				
			}
		});
		

		
		Mockito.verify(preparedStatement,Mockito.times(1)).executeQuery();
		Mockito.verify(resultSet,Mockito.times(0)).next();
		Mockito.verify(resultSet,Mockito.times(0)).getString(Mockito.anyInt());
		Mockito.verify(resultSet,Mockito.times(0)).close();
		
	}
	
	@Test
	void hashCheckPasswordTest() {
		String password = "myPasswordTest";
		String hashedPassword = UtilDao.hashPassword(password);
		assertTrue(UtilDao.checkPassword(password, hashedPassword));
	}
	
	@Test
	void hashCheckPasswordTestWrong() {
		String hashedPassword = UtilDao.hashPassword("myPasswordTest");
		assertFalse(UtilDao.checkPassword("wrongPassword", hashedPassword));
	}
}
