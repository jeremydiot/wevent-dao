package fr.jdiot.wevent.dao.contract;

public abstract class CommonContract {
	
	public static final String COL_CREATED_AT_NAME = "created_at";
	public static final String COL_MODIFIED_AT_NAME = "modified_at";
	
	public static final String COL_CREATED_AT_DATATYPE = "TIMESTAMP";
	public static final String COL_MODIFIED_AT_DATATYPE = "TIMESTAMP";
	
	public static final String COL_CREATED_AT_CONSTRAINT = "DEFAULT CURRENT_TIMESTAMP NOT NULL";
	public static final String COL_MODIFIED_AT_CONSTRAINT = "DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL";
	
}
