package fr.jdiot.wevent.dao.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class DbManagerTest {
	
	String host = "test";
	String port = "5432";
	String database = "test";
	String user = "test";
	String password = "test";
	
	@Spy
	DbManager dbManager = new DbManager(host, port, database, user, password);
	
	@Test
	public void executeSqlQueryTest(){
		Mockito.doNothing().when(dbManager).executeSqlQuery(Mockito.anyString(), Mockito.anyBoolean());
		
		dbManager.executeSqlQuery("test");
		
		Mockito.verify(dbManager, Mockito.times(1)).executeSqlQuery("test", true);
		
	}
	
	@Test
	public void upConfTest(){
		Mockito.doNothing().when(dbManager).executeSqlQuery(Mockito.anyString(), Mockito.anyBoolean());
	
		dbManager.upConf();
		
		Mockito.verify(dbManager, Mockito.times(1)).executeSqlQuery(Mockito.anyString(), Mockito.eq(false));
		Mockito.verify(dbManager, Mockito.times(8)).executeSqlQuery(Mockito.anyString(), Mockito.eq(true));
	}
	
	@Test
	public void downConfTest(){
		Mockito.doNothing().when(dbManager).executeSqlQuery(Mockito.anyString(), Mockito.anyBoolean());
		
		dbManager.downConf();
	
		Mockito.verify(dbManager, Mockito.times(1)).executeSqlQuery(Mockito.anyString(), Mockito.eq(false));
	}
}
