package fr.jdiot.wevent.dao.contract;

public final class FriendContract extends CommonContract{
	
	private FriendContract () {}
	
	public static final String TABLE_NAME = "friend";
	
	public static final String COL_USER_ID_NAME = "user_id";
	public static final String COL_FRIEND_ID_NAME = "friend_id";

	public static final String COL_USER_ID_DATATYPE = "UUID";
	public static final String COL_FRIEND_ID_DATATYPE = "UUID";
	
	public static final String COL_USER_ID_CONSTRAINT = "REFERENCES "+UserContract.TABLE_NAME+" ("+UserContract.COL_ID_NAME+") NOT NULL ON DELETE CASCADE";
	public static final String COL_FRIEND_ID_CONSTRAINT = "REFERENCES "+UserContract.TABLE_NAME+" ("+UserContract.COL_ID_NAME+") NOT NULL ON DELETE CASCADE";
	
	public static final String TABLE_CONSTRAINT = "UNIQUE ("+COL_USER_ID_NAME+","+COL_FRIEND_ID_NAME+")";
}
