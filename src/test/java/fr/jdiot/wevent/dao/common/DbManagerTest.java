package fr.jdiot.wevent.dao.common;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.jdiot.wevent.dao.exception.DbManagerException;

@ExtendWith(MockitoExtension.class)
public class DbManagerTest {
	
	@Nested
	public class staticTest{
		
		// TODO integration test ?
	}
	
	@Nested
	public class instanciateTest{
		
		@Mock
		ConnectionPool connectionPool;
		
		@Mock
		Statement statement;
		
		@Mock
		Connection connection;
		
		DbManager dbManager;
		
		@BeforeEach
		void init() throws SQLException {
			dbManager = new DbManager(connectionPool);
			
			Mockito.when(connectionPool.getConnection()).thenReturn(connection);
			Mockito.when(connection.createStatement()).thenReturn(statement);
		}
		
		@Test
		void downConfTest() throws SQLException {

			dbManager.resetConf();
			
			Mockito.verify(connectionPool,Mockito.times(6)).getConnection();
			Mockito.verify(connection,Mockito.times(6)).createStatement();
			Mockito.verify(statement,Mockito.times(6)).execute(Mockito.anyString());
			Mockito.verify(statement,Mockito.times(6)).close();
			Mockito.verify(connection,Mockito.times(6)).close();
		}
		
		@Test
		void downConfTestQueryException() throws SQLException  {

			Mockito.doThrow(SQLException.class).when(statement).execute(Mockito.anyString());
			
			assertThrows(DbManagerException.class, new Executable() {
				
				@Override
				public void execute() throws Throwable {
					dbManager.resetConf();
					
				}
			});
		}
		
		@Test
		void downConfTestStatementException() throws SQLException {
			Mockito.doThrow(SQLException.class).when(statement).close();

			dbManager.resetConf();
		}
		
		@Test
		void downConfTestConnectionException() throws SQLException {
			Mockito.doThrow(SQLException.class).when(connection).close();

			dbManager.resetConf();
		}
		
		
	}
	
	
}
