package fr.jdiot.wevent.dao.contract;

public final class GuestContract extends CommonContract {
	
	private GuestContract() {}
	
	public static final String TABLE_NAME = "t_guest";
	
	public static final String COL_USER_ID_NAME = "user_id";
	public static final String COL_EVENT_ID_NAME = "event_id";

	public static final String COL_USER_ID_DATATYPE = "UUID";
	public static final String COL_EVENT_ID_DATATYPE = "UUID";
	
	public static final String COL_USER_ID_CONSTRAINT = "REFERENCES "+UserContract.TABLE_NAME+" ("+UserContract.COL_ID_NAME+") ON DELETE CASCADE NOT NULL";
	public static final String COL_EVENT_ID_CONSTRAINT = "REFERENCES "+EventContract.TABLE_NAME+" ("+EventContract.COL_ID_NAME+") ON DELETE CASCADE NOT NULL";
	
	public static final String TABLE_CONSTRAINT = "UNIQUE ("+COL_USER_ID_NAME+","+COL_EVENT_ID_NAME+")";
}
