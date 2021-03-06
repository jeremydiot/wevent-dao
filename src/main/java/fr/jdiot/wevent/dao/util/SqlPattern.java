package fr.jdiot.wevent.dao.util;

public final class SqlPattern {
	
	private SqlPattern () {}

	public static final String CREATE_DATABASE= "CREATE DATABASE %s ENCODING = \"%s\"";
	public static final String DROP_DATABASE = "DROP DATABASE %s";
	
	public static final String CREATE_EXTENSION = "CREATE EXTENSION IF NOT EXISTS \"%s\"";
	
	public static final String CREATE_TABLE = "CREATE TABLE %s (%s)";
	public static final String DROP_TABLE = "DROP TABLE %s";
	
	public static final String UPDATE = "UPDATE %s SET %s WHERE %s";
	public static final String INSERT = "INSERT INTO %s (%s) VALUES (%s)";
	public static final String SELECT = "SELECT %s FROM %s WHERE %s";
	public static final String DELETE = "DELETE FROM %s WHERE %s";
}

