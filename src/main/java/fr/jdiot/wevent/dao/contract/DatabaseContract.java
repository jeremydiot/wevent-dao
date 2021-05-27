package fr.jdiot.wevent.dao.contract;

public final class DatabaseContract {
	
	private DatabaseContract() {}

	public static final String DATABASE_NAME = "wevent";
	public static final String DATABASE_ENCODING_NAME = "UTF8";
	
	public static final String DATABASE_UUID_OSSP_EXTENSION_NAME = "uuid-ossp";
	
	public static final String DATABASE_FUNCTION_SET_CURRENT_TIMESTAMP_TO_CONNECTED_AT = 
			  "CREATE OR REPLACE FUNCTION func_set_timestamp_on_update() RETURNS TRIGGER AS $$ "
			+ "BEGIN "
			+ "NEW."+CommonContract.COL_UPDATED_AT_NAME+" = CURRENT_TIMESTAMP; "
			+ "RETURN NEW; "
			+ "END; "
			+ "$$ LANGUAGE plpgsql;";
	
	public static final String DATABASE_TRIGGER_ON_UPDATE_SET_CURRENT_TIMESTAMP_TO_CONNECTED_AT =
			  "CREATE TRIGGER trigger_set_timestamp_on_update "
			+ "BEFORE UPDATE ON %s "
			+ "FOR EACH ROW "
			+ "EXECUTE PROCEDURE func_set_timestamp_on_update();";
}
