package a.cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import b.cn.itcast.utils.JdbcUtils;

/*
 * 练习：编写程序对User表进行增删改查操作。
	
 * 
 */
public class JdbcCrudTest {
	
	@Test
	public void testAdd(){
		Connection conn = null;
		Statement stmt = null;
		
		ResultSet rs = null ;
		
		// 注册 驱动 
		try {
			conn = JdbcUtils.getConnection();
			
			// 获得用于 向 数据 发送 sql语句的 statement对象
			stmt = conn.createStatement();
			// 执行 sql语句
			stmt.executeUpdate("insert into user values(null,'ch','chh','ch@itcast.cn')");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			JdbcUtils.release(rs, stmt, conn);
		}
		
	}
	@Test
	public void testDelete(){
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			stmt = conn.createStatement();
			
			stmt.executeUpdate("delete from user where name='ch'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			JdbcUtils.release(rs, stmt, conn);
		}
		
	}
	
	@Test
	public void testUpdate(){
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			stmt = conn.createStatement();
			
			stmt.executeUpdate("update user set name='fcc' where id=1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.release(rs, stmt, conn);
		}
		
	}
	
	@Test
	public void testSelect(){
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
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
			JdbcUtils.release(rs, stmt, conn);
		}
		
	}
}
;