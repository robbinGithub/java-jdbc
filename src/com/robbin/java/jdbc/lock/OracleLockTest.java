package com.robbin.java.jdbc.lock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import b.cn.itcast.utils.JdbcUtils;

/**
 * Oracle 锁测试
 * 
 * ORACLE里锁有以下几种模式:
	0：none
	1：null 空
	2：Row-S 行共享(RS)：共享表锁，sub share 
	3：Row-X 行独占(RX)：用于行的修改，sub exclusive 
	4：Share 共享锁(S)：阻止其他DML操作，share
	5：S/Row-X 共享行独占(SRX)：阻止其他事务操作，share/sub exclusive 
	6：exclusive 独占(X)：独立访问使用，exclusive

 * @author robbin.zhang
 * @date 2017/08/12 17:08
 *
 */
public class OracleLockTest {
	
	/**
	 * Oracle Insert 
	 * Lock Mode 3 :Row-X 行独占(RX)：用于行的修改，sub exclusive 
	 */
	@Test
	public void test_insert_lock(){
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// 注册 驱动 
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			
			// 获得用于 向 数据 发送 sql语句的 statement对象
			stmt = conn.createStatement();
			// 执行 sql语句
			stmt.executeUpdate("insert into student_score (name,subject,score)values('robbin zhang','Chinese',92)");
			stmt.executeUpdate("insert into student_score (name,subject,score)values('robbin zhang','English',92)");
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				if(conn != null){
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JdbcUtils.release(rs, stmt, conn);
		}
	}
	
	
	/**
	 * Oracle Update 
	 * Lock Mode 3 :Row-X 行独占(RX)：用于行的修改，sub exclusive 
	 */
	@Test
	public void test_update_lock(){
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// 注册 驱动 
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			
			// 获得用于 向 数据 发送 sql语句的 statement对象
			stmt = conn.createStatement();
			// 执行 sql语句
			stmt.executeUpdate("update student_score set score = 120 where name = 'robbin2'");
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				if(conn != null){
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JdbcUtils.release(rs, stmt, conn);
		}
	}
	

	/**
	 * Oracle Query 
	 * Lock Mode 3 :Row-X 行独占(RX)：用于行的修改，sub exclusive 
	 */
	@Test
	public void test_query_lock(){

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// 注册 驱动 
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			
			// 获得用于 向 数据 发送 sql语句的 statement对象
			stmt = conn.createStatement();
			// 执行 sql语句
			stmt.executeQuery("select * from student_score where name = 'robbin2'");
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				if(conn != null){
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JdbcUtils.release(rs, stmt, conn);
		}
	}

}
