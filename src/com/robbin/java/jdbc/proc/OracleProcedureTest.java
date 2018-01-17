package com.robbin.java.jdbc.proc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;
import b.cn.itcast.utils.JdbcUtils;


public class OracleProcedureTest {
	
    private static Connection conn  = null;	
    static{
    	
    	conn = JdbcUtils.getConnection();
    }
	
	public static void main(String[] args) {
		
		String procName = "p1";
		OutParameter<Integer> P_outputParam = new OutParameter<Integer>(Types.INTEGER,Integer.class);
		int P_id = 1;
		OutParameter P_CURSOR1 = new OutParameter<Integer>(OracleTypes.CURSOR,null);
		OutParameter P_CURSOR2 = new OutParameter<Integer>(OracleTypes.CURSOR,null);
		Object[] params = { P_outputParam, P_id, P_CURSOR1, P_CURSOR2 };
		OracleProcedureTest obj = new OracleProcedureTest();
		
		try {
			obj.callProcedure(conn, procName, params);
			List<Map<String, Object>> value = (List<Map<String, Object>>)P_CURSOR1.getValue();
			List<Map<String, Object>> value2 = (List<Map<String, Object>>)P_CURSOR2.getValue();
			System.out.println(value);
			System.out.println(value2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn = null;
			}
		}
	}
	
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public List<List<Map<String, Object>>> callProcedure(Connection conn, String procedure, Object... params) throws Exception {
		    	
			int paramsCount = 0;
			String sql = null;
			
			if (conn == null) {
				throw new SQLException("Null connection");
			}
			
			if(params != null){
				paramsCount = params.length;
			}
			
			if (paramsCount > 0) {
				sql = "{call " + procedure + getProcParameters(paramsCount, "?") + "}";
			} else {
				sql = "{call " + procedure + "}";
			}
			
			CallableStatement stmt = null;
			List<List<Map<String, Object>>> results = new LinkedList<List<Map<String, Object>>>();
			try {
		
				conn.setAutoCommit(Boolean.FALSE);
				stmt = conn.prepareCall(sql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						if (params[i] != null) {
							if (stmt != null && params[i] instanceof OutParameter) {
								((OutParameter) params[i]).register(stmt, i + 1);
							} else {
								stmt.setObject(i + 1, params[i]);
							}
						} else {
							int sqlType = Types.VARCHAR;
							stmt.setNull(i + 1, sqlType);
						}
					}
				}
				boolean moreResultSets = stmt.execute();
				ResultSet rs = null;
				while (moreResultSets) {
					 try {   
						List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
						rs = stmt.getResultSet();
						ResultSetMetaData rsmd = rs.getMetaData();
						int cols = rsmd.getColumnCount();
						while(rs.next()){
							Map<String, Object> row = new CaseInsensitiveHashMap();
							for (int i = 1; i <= cols; i++) {
								String columnName = rsmd.getColumnLabel(i);
								if (null == columnName || 0 == columnName.length()) {
									columnName = rsmd.getColumnName(i);
								}
								row.put(columnName, rs.getObject(i));
							}
							result.add(row);
						}
						results.add(result);
						moreResultSets = stmt.getMoreResults();
		
					} finally {
						if (rs != null) {
							rs.close();
							rs = null;
						}
					}
				}
		
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						if (params[i] instanceof OutParameter) {
							if (oracle.jdbc.OracleTypes.CURSOR == ((OutParameter) params[i]).getSqlType()) {
								List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
								ResultSet rst = null;
								try {
									rst = (ResultSet) stmt.getObject(i + 1);
									ResultSetMetaData rsmd = rst.getMetaData();
									int cols = rsmd.getColumnCount();
									while(rst.next()){
										Map<String, Object> row = new CaseInsensitiveHashMap();
										for (int j = 1; j <= cols; j++) {
											String columnName = rsmd.getColumnLabel(j);
											if (null == columnName || 0 == columnName.length()) {
												columnName = rsmd.getColumnName(j);
											}
											row.put(columnName, rst.getObject(j));
										}
										list.add(row);
									}
									((OutParameter)params[i]).setValue(list);
								} finally {
									if (rst != null) {
										rst.close();
										rst = null;
									}
								}
							} else {
								((OutParameter) params[i]).setValue(stmt, i + 1);
							}
						}
					}
				}
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				throw e;
			} finally {
				conn.setAutoCommit(Boolean.TRUE);
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			}
			return results;
		}
		
		private String getProcParameters(int count, String str) {
			String ret = "";
			while (count-- > 0)
				ret += "," + str;
			return "(" + ret.substring(1) + ")";
		}

}
