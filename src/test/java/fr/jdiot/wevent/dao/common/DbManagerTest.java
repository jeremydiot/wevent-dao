package fr.jdiot.wevent.dao.common;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.jdiot.wevent.dao.exception.DbManagerException;


@ExtendWith(MockitoExtension.class)
public class DbManagerTest {
	
	String host = "wrongHost";
	String port = "5432";
	String database = "wevent";
	String user = "root";
	String password = "root";
	
	@Test
	void executeSqlQueryTest() {
		assertThrows(DbManagerException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				DbManager dbManager = new DbManager(host, port, database, user, password);
				dbManager.executeSqlQuery("");
				
			}
		});
	}
	
	@Nested
	public class queryFormaterTest {

		
		
		@Spy
		DbManager dbManager = new DbManager(host, port, database, user, password);
		
		
		@BeforeEach
		void init() {
			Mockito.doNothing().when(dbManager).executeSqlQuery(Mockito.anyString());
		}
		
		@Test
		void createDatabaseTest() {
			
			dbManager.createDatabase();
			
			Mockito.verify(dbManager, Mockito.times(1)).executeSqlQuery(Mockito.anyString());
		}

		
		@Test
		void dropDatabaseTest() {
			
			dbManager.dropDatabase();
			
			Mockito.verify(dbManager, Mockito.times(1)).executeSqlQuery(Mockito.anyString());
		}

		@Test
		void createTablesTest() {
			
			dbManager.createTables();
			
			Mockito.verify(dbManager, Mockito.times(5)).executeSqlQuery(Mockito.anyString());
		}
		
		@Test
		void dropTablesTest() {
			
			dbManager.dropTables();
			
			Mockito.verify(dbManager, Mockito.times(1)).executeSqlQuery(Mockito.anyString());
		}
	}
	
}
