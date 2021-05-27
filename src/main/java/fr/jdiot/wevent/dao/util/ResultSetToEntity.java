package fr.jdiot.wevent.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetToEntity<T> {
	public T convert(ResultSet resultSet) throws SQLException;
}
