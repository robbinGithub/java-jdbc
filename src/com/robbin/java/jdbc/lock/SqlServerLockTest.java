package com.robbin.java.jdbc.lock;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;

import b.cn.itcast.utils.JdbcUtils;

/**
 * SQLServer ������
 * 
 *  ���� (S) ���ڲ����Ļ򲻸������ݵĲ�����ֻ������������ SELECT ��䡣 
	���� (U) ���ڿɸ��µ���Դ�С���ֹ������Ự�ڶ�ȡ�������Լ������ܽ��е���Դ����ʱ����������ʽ�������� 
	���� (X) ���������޸Ĳ��������� INSERT��UPDATE �� DELETE��ȷ������ͬʱͬһ��Դ���ж��ظ��¡� 
	������ ���ڽ������Ĳ�νṹ��������������Ϊ�������� (IS)���������� (IX) �Լ��������������� (SIX)�� 
	�ܹ��� ��ִ�������ڱ�ܹ��Ĳ���ʱʹ�á��ܹ���������Ϊ���ܹ��޸� (Sch-M) �ͼܹ��ȶ��� (Sch-S)�� 
	���������� (BU) ����д������������ݲ�ָ���� TABLOCK ��ʾʱʹ�á� 

 * @author robbin.zhang
 * @date 2018/01/17 21:06
 *
 */
@SuppressWarnings({ "deprecation"})
public class SqlServerLockTest {
	
	/**
	 * ����SQLServer ������������
	 * @throws SQLException 
	 */
	@Test
	public void test_xLock() throws SQLException {
		
		Connection conn = null;;
		
		try {
			
			conn = JdbcUtils.getSQLServerConnection();
			conn.setAutoCommit(false);
			QueryRunner runner = new QueryRunner();
			String sql = "select * from crm_account with (rowlock) where id = ? ";
			Object[]  params = {1};
			Account account = runner.query(conn, sql, params, new BeanHandler<Account>(Account.class));
			System.out.println("id:" + account.getId() + ", name:" + account.getName() + ", balance:"+ account.getBalance());
			
			conn.commit();
			
			// update crm_account SET balance = 8000 where id = 2;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}finally {
			
			conn.setAutoCommit(true);
			
			if(conn != null){
				conn.close();
			}
		}
		// id:1, name:robbin, balance:1000.0
		
	}
	
	
	/**
	 * ���Դ�������SQLServer������ ��������
	 * @throws SQLException 
	 */
	@Test
	public void test_updateLock() throws SQLException {
		
		Connection conn = null;;
		
		try {
			
			conn = JdbcUtils.getSQLServerConnection();
			conn.setAutoCommit(false);
			QueryRunner runner = new QueryRunner();
			String sql = "select * from crm_account with (updlock) where id = ? ";
			Object[]  params = {1};
			Account account = runner.query(conn, sql, params, new BeanHandler<Account>(Account.class));
			System.out.println("id:" + account.getId() + ", name:" + account.getName() + ", balance:"+ account.getBalance());
			
			conn.commit();
			
			// update crm_account SET balance = 8000 where id = 2;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}finally {
			
			conn.setAutoCommit(true);
			
			if(conn != null){
				conn.close();
			}
		}
		// id:1, name:robbin, balance:1000.0
		
	}
	
	/**
	 * ����û��������SQLServer������   ���� ����
	 * @throws SQLException 
	 */
	@Test
	public void test_updateLockNoIndex() throws SQLException {
		
		Connection conn = null;;
		
		try {
			
			conn = JdbcUtils.getSQLServerConnection();
			conn.setAutoCommit(false);
			QueryRunner runner = new QueryRunner();
			String sql = "select * from crm_account with (rowlock, updlock) where name = ? ";
			Object[]  params = {"robbin"};
			Account account = runner.query(conn, sql, params, new BeanHandler<Account>(Account.class));
			System.out.println("id:" + account.getId() + ", name:" + account.getName() + ", balance:"+ account.getBalance());
			
			conn.commit();
			
			// update crm_account SET balance = 8000 where id = 2;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}finally {
			
			conn.setAutoCommit(true);
			
			if(conn != null){
				conn.close();
			}
		}
		// id:1, name:robbin, balance:1000.0
		
	}
	
	public static class Account {
	
		private long id;
		private String name;
		private double balance;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getBalance() {
			return balance;
		}
		public void setBalance(double balance) {
			this.balance = balance;
		}
	}
}
