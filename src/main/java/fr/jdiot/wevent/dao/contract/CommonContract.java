package fr.jdiot.wevent.dao.contract;

public abstract class CommonContract {
	
	public static final String COL_CREATED_AT_NAME = "created_at";
	public static final String COL_UPDATED_AT_NAME = "updated_at";
	
	public static final String COL_CREATED_AT_DATATYPE = "TIMESTAMP";
	public static final String COL_UPDATED_AT_DATATYPE = "TIMESTAMP";
	
	public static final String COL_CREATED_AT_CONSTRAINT = "DEFAULT CURRENT_TIMESTAMP NOT NULL";
	public static final String COL_UPDATED_AT_CONSTRAINT = "DEFAULT CURRENT_TIMESTAMP NOT NULL";
	
}
