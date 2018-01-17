package a.cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class JdbcTest2 {
	
	@Test
	public void testUpdate(){
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// ע�� ���� 
			Class.forName("com.mysql.jdbc.Driver");
			
			// ��� ���� ���� 
			
			conn = DriverManager.getConnection("jdbc:mysql:///day12", "root", "erqiu");
			
			// ���� ����sql��� ��  stmt����
			stmt = conn.createStatement();
			
			// ִ�� sql��� 
			int count = stmt.executeUpdate("insert into user values(null, 'jxj','xjj','jxj@itcast.cn')");
			
			// ���ص�count���ڱ�ʾ ִ�� slq���ʱ Ӱ���� ���� ����
			System.out.println(count);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			// �ͷ���Դ �ᰴ�� һ�� �෴�� ���� ˳�� ȥ �ͷ� .
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
}
