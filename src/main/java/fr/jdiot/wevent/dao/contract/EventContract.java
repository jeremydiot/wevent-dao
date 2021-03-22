package fr.jdiot.wevent.dao.contract;

public final class EventContract extends CommonContract {

	private EventContract() {}
	
	public static final String TABLE_NAME = "event";
	
	public static final String COL_ID_NAME = "id";
	public static final String COL_ADMIN_ID_NAME = "admin_id";
	public static final String COL_TITLE_NAME = "title";
	public static final String COL_START_DATE_NAME = "start_date";
	public static final String COL_END_DATE_NAME = "end_date";
	public static final String COL_CONTENT_NAME = "content";
	
	public static final String COL_ID_DATATYPE = "UUID";
	public static final String COL_ADMIN_ID_DATATYPE = "UUID";
	public static final String COL_TITLE_DATATYPE = "VARCHAR";
	public static final String COL_START_DATE_DATATYPE = "TIMESTAMP";
	public static final String COL_END_DATE_DATATYPE = "TIMESTAMP";
	public static final String COL_CONTENT_DATATYPE = "VARCHAR";
	
	public static final String COL_ID_CONSTRAINT = "PRIMARY KEY DEFAULT uuid_generate_v4() UNIQUE NOT NULL";
	public static final String COL_ADMIN_ID_CONSTRAINT = "REFERENCES "+UserContract.TABLE_NAME+" ("+UserContract.COL_ID_NAME+") NOT NULL ON DELETE CASCADE";
	public static final String COL_TITLE_CONSTRAINT = "NOT NULL";
	public static final String COL_START_DATE_CONSTRAINT = "CHECK ("+COL_START_DATE_NAME+" <= "+COL_END_DATE_NAME+") NOL NULL";
	public static final String COL_END_DATE_CONSTRAINT = "CHECK ("+COL_END_DATE_NAME+" >= "+COL_START_DATE_NAME+") NOL NULL";
	public static final String COL_CONTENT_CONSTRAINT = "NOT NULL";
}
