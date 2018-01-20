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
	 * 测试SQLServer 【排他锁】
	 * 
	 * SELECT * from crm_account with (NOLOCK) where id = 3 ;
	 * @throws SQLException 
	 */
	@Test
	public void test_xLock() throws SQLException {
		
		Connection conn = null;;
		
		try {
			  
			conn = JdbcUtils.getSQLServerConnection();
			conn.setAutoCommit(false);
			QueryRunner runner = new QueryRunner();
			String sql = "update crm_account with(rowlock, XLOCK)  set name = ?  where id = ? ";
//			String sql = "update crm_account set name = ?  where id = ? ";
			runner.update(conn, sql, "SASS21212", 1);
			
			conn.commit();
			
			System.out.println("update success");
			
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
	 * 测试带索引的SQLServer 【共享锁】 
	 * @throws SQLException 
	 */
	@Test
	public void test_shareLock() throws SQLException {
		
		Connection conn = null;;
		
		try {
			
			conn = JdbcUtils.getSQLServerConnection();
			conn.setAutoCommit(false);
			QueryRunner runner = new QueryRunner();
			String sql = "select * from crm_account where id = ? ";
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
	 * 测试带索引的SQLServer【更新锁】
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
			
			// 指定锁范围
//			String sql = "select * from crm_account with (rowlock, updlock) where id = ? ";
			
			// 不使用索引
//			String sql = "select * from crm_account with (rowlock, updlock) where name = ? ";
			
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
	 * @see https://zhidao.baidu.com/question/365105761816809252.html
	 */
	public void test_transaction(){
		
	/*	
		SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED 
		SELECT * FROM table ROWLOCK WHERE id = 1
		实例：

		--排它锁 
		--新建两个连接 
		--在第一个连接中执行以下语句 
		begin tran 
		update table1 
		set A='aa' 
		where B='b2' 
		waitfor delay '00:00:30' --等待30秒 
		commit tran 
		 
		--在第二个连接中执行以下语句 
		begin tran 
		select * from table1 
		where B='b2' 
		commit tran 
		 
		--若同时执行上述两个语句，则select查询必须等待update执行完毕才能执行即要等待30秒*/
	}
	
	/**
	 * 查询数据库上的锁
	 */
	public void test_query(){
		
		
	/*	select req_spid
		,case req_status when 1 then '已授予' when 2 then '正在转换' when 3 then '正在等待' end as req_status
		,case rsc_type when 1 then 'NULL 资源（未使用）' when 2 then '数据库' when 3 then '文件'
			when 4 then '索引' when 5 then '表' when 6 then '页' when 7 then '键' 
			when 8 then '扩展盘区' when 9 then 'RID（行 ID)' when 10 then '应用程序' else '' end rsc_type
		,coalesce(OBJECT_NAME(rsc_objid),db_name(rsc_dbid)) as [object]
		,case req_mode when 1 then 'NULL' when 1 then 'Sch-S' when 2 then 'Sch-M' when 3 then 'S' 
			when 4 then 'U' when 5 then 'X' when 6 then 'IS' when 7 then 'IU' when 8 then 'IX' when 9 then 'SIU' 
			when 10 then 'SIX' when 11 then 'UIX' when 12 then 'BU' when 13 then 'RangeS_S' when 14 then 'RangeS_U' 
			when 15 then 'RangeI_N' when 16 then 'RangeI_S' when 17 then 'RangeI_U' when 18 then 'RangeI_X'	
			when 19 then 'RangeX_S' when 20 then 'RangeX_U' when 21 then 'RangeX_X' else '' end req_mode
		,rsc_indid as index_id,rsc_text,req_refcnt
		,case req_ownertype when 1 then '事务' when 2 then '游标' when 3 then '会话' when 4 then 'ExSession' else'' end req_ownertype
		from sys.syslockinfo WHERE rsc_type<>2*/
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
