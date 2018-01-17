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
			// 注册 驱动 
			Class.forName("com.mysql.jdbc.Driver");
			
			// 获得 连接 对象 
			
			conn = DriverManager.getConnection("jdbc:mysql:///day12", "root", "erqiu");
			
			// 创建 发送sql语句 的  stmt对象
			stmt = conn.createStatement();
			
			// 执行 sql语句 
			int count = stmt.executeUpdate("insert into user values(null, 'jxj','xjj','jxj@itcast.cn')");
			
			// 返回的count用于表示 执行 slq语句时 影响了 几行 数据
			System.out.println(count);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			// 释放资源 会按照 一个 相反的 创建 顺序 去 释放 .
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
