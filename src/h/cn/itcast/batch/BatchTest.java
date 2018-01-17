package h.cn.itcast.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import b.cn.itcast.utils.JdbcUtils;

public class BatchTest {
	
	// 使用 statement对象 进行 批处理 
	@Test
	public void testStatement(){
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = JdbcUtils.getConnection();
			stmt = conn.createStatement();
			
			// 添加 批处理语句 
			stmt.addBatch("create database day12x");
			stmt.addBatch("use day12x");
			stmt.addBatch("create table user(id int, name varchar(30), gender varchar(6))");
			stmt.addBatch("insert into user values(1,'zhangsan','female')");
			stmt.addBatch("insert into user values(1,'lisi','male')");
			stmt.addBatch("update user set name='erqiu' where name='zhangsan'");
			
			
			stmt.executeBatch();  // 执行 批处理 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.release(null, stmt, conn);
		}
		
	}
	
	@Test
	public void testPreparedStatement(){
		
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		
		try {
			String sql = "insert into user values(null,?,?,?)";
			// 通过 preparedStatement实现批处理
			stmt = conn.prepareStatement(sql);
			
			for (int i = 1; i < 200; i++) {
				stmt.setObject(1, "zk" + i);
				stmt.setObject(2, "k"+i);
				stmt.setObject(3, "zk"+i+"@itcast.cn");
				
				stmt.addBatch();
			}
			
			stmt.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			JdbcUtils.release(null, stmt, conn);
		}
	}
}
