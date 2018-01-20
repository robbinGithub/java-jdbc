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
	 * ����SQLServer ����������
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
	 * ���Դ�������SQLServer ���������� 
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
	 * ���Դ�������SQLServer����������
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
			
			// ָ������Χ
//			String sql = "select * from crm_account with (rowlock, updlock) where id = ? ";
			
			// ��ʹ������
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
		ʵ����

		--������ 
		--�½��������� 
		--�ڵ�һ��������ִ��������� 
		begin tran 
		update table1 
		set A='aa' 
		where B='b2' 
		waitfor delay '00:00:30' --�ȴ�30�� 
		commit tran 
		 
		--�ڵڶ���������ִ��������� 
		begin tran 
		select * from table1 
		where B='b2' 
		commit tran 
		 
		--��ͬʱִ������������䣬��select��ѯ����ȴ�updateִ����ϲ���ִ�м�Ҫ�ȴ�30��*/
	}
	
	/**
	 * ��ѯ���ݿ��ϵ���
	 */
	public void test_query(){
		
		
	/*	select req_spid
		,case req_status when 1 then '������' when 2 then '����ת��' when 3 then '���ڵȴ�' end as req_status
		,case rsc_type when 1 then 'NULL ��Դ��δʹ�ã�' when 2 then '���ݿ�' when 3 then '�ļ�'
			when 4 then '����' when 5 then '��' when 6 then 'ҳ' when 7 then '��' 
			when 8 then '��չ����' when 9 then 'RID���� ID)' when 10 then 'Ӧ�ó���' else '' end rsc_type
		,coalesce(OBJECT_NAME(rsc_objid),db_name(rsc_dbid)) as [object]
		,case req_mode when 1 then 'NULL' when 1 then 'Sch-S' when 2 then 'Sch-M' when 3 then 'S' 
			when 4 then 'U' when 5 then 'X' when 6 then 'IS' when 7 then 'IU' when 8 then 'IX' when 9 then 'SIU' 
			when 10 then 'SIX' when 11 then 'UIX' when 12 then 'BU' when 13 then 'RangeS_S' when 14 then 'RangeS_U' 
			when 15 then 'RangeI_N' when 16 then 'RangeI_S' when 17 then 'RangeI_U' when 18 then 'RangeI_X'	
			when 19 then 'RangeX_S' when 20 then 'RangeX_U' when 21 then 'RangeX_X' else '' end req_mode
		,rsc_indid as index_id,rsc_text,req_refcnt
		,case req_ownertype when 1 then '����' when 2 then '�α�' when 3 then '�Ự' when 4 then 'ExSession' else'' end req_ownertype
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
