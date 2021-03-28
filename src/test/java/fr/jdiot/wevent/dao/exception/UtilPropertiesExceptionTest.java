package fr.jdiot.wevent.dao.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class UtilPropertiesExceptionTest {

	@ParameterizedTest
	@MethodSource("instanciateTestProvider")
	void instanciateTest(Executable executable) {
		
		assertThrows(UtilPropertiesException.class,executable);
	}
	
	private static Stream<Executable> instanciateTestProvider() {
	    return Stream.of(
	            new Executable() {

					@Override
					public void execute() throws Throwable {
						throw new UtilPropertiesException();
						
					}
	            	
	            },
	            new Executable() {

					@Override
					public void execute() throws Throwable {
						throw new UtilPropertiesException("test message");
						
					}
	            	
	            },
	            new Executable() {

					@Override
					public void execute() throws Throwable {
						throw new UtilPropertiesException(new Throwable());
						
					}
	            	
	            },
	            new Executable() {

					@Override
					public void execute() throws Throwable {
						throw new UtilPropertiesException("test message",new Throwable());
						
					}
	            	
	            },
	            new Executable() {

					@Override
					public void execute() throws Throwable {
						throw new UtilPropertiesException("test message",new Throwable(), true, true);
						
					}
	            	
	            }
	    );
	}
}