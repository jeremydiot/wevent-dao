package fr.jdiot.wevent.dao.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DbManagerExceptionTest {

	@Mock
	static Throwable throwable;

	@ParameterizedTest
	@MethodSource("instanciateTestProvider")
	void instanciateTest(Executable executable) {
		
		assertThrows(DbManagerException.class,executable);
	}
	
	private static Stream<Executable> instanciateTestProvider() {
	    return Stream.of(
	            new Executable() {

					@Override
					public void execute() throws Throwable {
						throw new DbManagerException();
						
					}
	            	
	            },
	            new Executable() {

					@Override
					public void execute() throws Throwable {
						throw new DbManagerException("test message");
						
					}
	            	
	            },
	            new Executable() {

					@Override
					public void execute() throws Throwable {
						throw new DbManagerException(throwable);
						
					}
	            	
	            },
	            new Executable() {

					@Override
					public void execute() throws Throwable {
						throw new DbManagerException("test message",throwable);
						
					}
	            	
	            },
	            new Executable() {

					@Override
					public void execute() throws Throwable {
						throw new DbManagerException("test message",throwable, true, true);
						
					}
	            	
	            }
	    );
	}

}
