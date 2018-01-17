package com.robbin.java.jdbc.lock;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;

import b.cn.itcast.utils.JdbcUtils;

/**
 * SQLServer 锁测试
 * 
 *  共享 (S) 用于不更改或不更新数据的操作（只读操作），如 SELECT 语句。 
	更新 (U) 用于可更新的资源中。防止当多个会话在读取、锁定以及随后可能进行的资源更新时发生常见形式的死锁。 
	排它 (X) 用于数据修改操作，例如 INSERT、UPDATE 或 DELETE。确保不会同时同一资源进行多重更新。 
	意向锁 用于建立锁的层次结构。意向锁的类型为：意向共享 (IS)、意向排它 (IX) 以及与意向排它共享 (SIX)。 
	架构锁 在执行依赖于表架构的操作时使用。架构锁的类型为：架构修改 (Sch-M) 和架构稳定性 (Sch-S)。 
	大容量更新 (BU) 向表中大容量复制数据并指定了 TABLOCK 提示时使用。 

 * @author robbin.zhang
 * @date 2018/01/17 21:06
 *
 */
@SuppressWarnings({ "deprecation"})
public class SqlServerLockTest {
	
	/**
	 * 测试SQLServer 【排他行锁】
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
	 * 测试带索引的SQLServer更新锁 【行锁】
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
	 * 测试没有索引的SQLServer更新锁   【表 锁】
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
