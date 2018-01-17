package a.cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

/*
 * ��ϰ����д�����User�������ɾ�Ĳ������
	
 * 
 */
public class JdbcCrudTestFinal {
	
	@Test
	public void testAdd(){
		Connection conn = null;
		Statement stmt = null;
		
		ResultSet rs = null ;
		
		// ע�� ���� 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// ��� ���Ӷ��� 
			
			conn = DriverManager.getConnection("jdbc:mysql:///day12", "root", "erqiu");
			
			// ������� �� ���� ���� sql���� statement����
			stmt = conn.createStatement();
			// ִ�� sql���
			stmt.executeUpdate("insert into user values(null,'ch','chh','ch@itcast.cn')");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs=null;
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
			}
		}
		
	}
	@Test
	public void testDelete(){
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql:///day12", "root", "erqiu");
			
			stmt = conn.createStatement();
			
			stmt.executeUpdate("delete from user where name='hxx'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs=null;
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
			}
		}
		
	}
	
	@Test
	public void testUpdate(){
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql:///day12", "root", "erqiu");
			
			stmt = conn.createStatement();
			
			stmt.executeUpdate("update user set name='fcc' where id=1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs=null;
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
			}
		}
		
	}
	
	@Test
	public void testSelect(){
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql:///day12", "root", "erqiu");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select * from user");
			
			while(rs.next()){
				String id = rs.getString("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				
				System.out.println("id : " + id +", name : " + name +", password : " + password +", email : "+ email);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs=null;
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
			}
		}
		
	}
}
;