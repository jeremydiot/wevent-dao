package fr.jdiot.wevent.dao.contract;

public final class UserContract extends CommonContract {
	
	private UserContract() {}
	
	public static final String TABLE_NAME = "user";
	
	public static final String COL_ID_NAME = "id";
	public static final String COL_USERNAME_NAME = "username";
	public static final String COL_PASSWORD_NAME = "password";
	public static final String COL_EMAIL_NAME = "email";
	public static final String COL_CONNECTED_AT_NAME = "connected_at";

	public static final String COL_ID_DATATYPE = "UUID";
	public static final String COL_USERNAME_DATATYPE = "VARCHAR";
	public static final String COL_PASSWORD_DATATYPE = "VARCHAR";
	public static final String COL_EMAIL_DATATYPE = "VARCHAR";
	public static final String COL_CONNECTED_AT_DATATYPE = "TIMESTAMP";
	
	public static final String COL_ID_CONSTRAINT = "PRIMARY KEY DEFAULT uuid_generate_v4() UNIQUE NOT NULL";
	public static final String COL_USERNAME_CONSTRAINT = "NOT NULL";
	public static final String COL_PASSWORD_CONSTRAINT = "NOT NULL";
	public static final String COL_EMAIL_CONSTRAINT = "UNIQUE NOT NULL";
	public static final String COL_CONNECTED_AT_CONSTRAINT = "";
	
}
