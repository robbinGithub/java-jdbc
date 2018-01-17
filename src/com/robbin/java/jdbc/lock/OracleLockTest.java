package com.robbin.java.jdbc.lock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import b.cn.itcast.utils.JdbcUtils;

/**
 * Oracle ������
 * 
 * ORACLE���������¼���ģʽ:
	0��none
	1��null ��
	2��Row-S �й���(RS)�����������sub share 
	3��Row-X �ж�ռ(RX)�������е��޸ģ�sub exclusive 
	4��Share ������(S)����ֹ����DML������share
	5��S/Row-X �����ж�ռ(SRX)����ֹ�������������share/sub exclusive 
	6��exclusive ��ռ(X)����������ʹ�ã�exclusive

 * @author robbin.zhang
 * @date 2017/08/12 17:08
 *
 */
public class OracleLockTest {
	
	/**
	 * Oracle Insert 
	 * Lock Mode 3 :Row-X �ж�ռ(RX)�������е��޸ģ�sub exclusive 
	 */
	@Test
	public void test_insert_lock(){
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// ע�� ���� 
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			
			// ������� �� ���� ���� sql���� statement����
			stmt = conn.createStatement();
			// ִ�� sql���
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
	 * Lock Mode 3 :Row-X �ж�ռ(RX)�������е��޸ģ�sub exclusive 
	 */
	@Test
	public void test_update_lock(){
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// ע�� ���� 
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			
			// ������� �� ���� ���� sql���� statement����
			stmt = conn.createStatement();
			// ִ�� sql���
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
	 * Lock Mode 3 :Row-X �ж�ռ(RX)�������е��޸ģ�sub exclusive 
	 */
	@Test
	public void test_query_lock(){

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// ע�� ���� 
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			
			// ������� �� ���� ���� sql���� statement����
			stmt = conn.createStatement();
			// ִ�� sql���
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
