package com.robbin.java.jdbc.proc;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class OutParameter<T> {
	private final int sqlType;
	private final Class<T> javaType;
	private T value = null;

	public OutParameter(int sqlType, Class<T> javaType) {
		this.sqlType = sqlType;
		this.javaType = javaType;
	}

	public OutParameter(int sqlType, Class<T> javaType, T value) {
		this.sqlType = sqlType;
		this.javaType = javaType;
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public int getSqlType() {
		return sqlType;
	}

	public Class<T> getJavaType() {
		return javaType;
	}
	
    public void setValue(CallableStatement stmt, int index) throws SQLException {
        Object object = stmt.getObject(index);
        value = javaType.cast(object);
    }
    
    public void register(CallableStatement stmt, int index) throws SQLException {
        stmt.registerOutParameter(index, sqlType);
        if (value != null) {
            stmt.setObject(index, value);
        }
    }

}
