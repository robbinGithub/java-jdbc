package b.cn.itcast.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class JdbcUtils {

	
	
	private static String url;
	private static String user;
	private static String password;
	private static String driverClass;

	static{
		
		// ע�� ,������� ͨ��   ResourceBundle �������  properties�ļ��� ���� , Ȼ���ٽ� ��Ӧ�� key�� ����ȥ �ͻ���� value
		// ע�� : �����ļ���ַ ��Ҫ�� ������ 
		
		url = ResourceBundle.getBundle("db").getString("url");
		user = ResourceBundle.getBundle("db").getString("user");
		password = ResourceBundle.getBundle("db").getString("password");
		driverClass = ResourceBundle.getBundle("db").getString("driverClass");
		
	}
	
	// ��� connection���� 
	public static Connection getConnection(){
		
		loadDriver();
		
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// װ������ 
	private static void loadDriver() {
		
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// �ͷ� ��Դ 
	
	public static void release(ResultSet rs, Statement stmt , Connection conn){
		
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs = null;
		}
		
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stmt = null;
		}
		
		if(conn!=null){
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
