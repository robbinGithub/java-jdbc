package com.robbin.java.jdbc.cursor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Oracle 游标测试
 * @author robbin.zhang
 * @date 2018/02/05 22:53
 *
 */
public class OracleCursorTest {
	
	private static final String url = "jdbc:oracle:thin:@XXX.19:1521:XXXX";
	private static final String user = "XXX";
	private static final String password = "XXXX";
	
	
	/**
	 * 
	 *  【测试SQL】
	 *  select * from CRM_REASON where REASONID ='300'
	   
	 *  【查询查看当前cursor状态】
         select * from v$open_cursor where sql_text like '%select * from CRM_REASON where REASONID%'; 
        
	           【查询SQL硬解析、软解析次数】
        select sql_text,s.PARSE_CALLS,loads,executions from v$sql s
        where sql_text like 'select * from CRM_REASON where REASONID%'
        order by 1,2,3,4;
        
                      每执行一次SQL,创建一个PreparedStatement,则每次会生成一个Session Cursor
                      
                      当某个session第三次执行相同的SQL语句时，则会把该SQL语句的游标信息转移到该session的PGA中。这样，当该session在执行该SQL语句时，会直接从PGA中取出执行计划，从而跳过硬解析的所有步骤。
        
	 * 
	 * Oracle 11g 测试当前打开的游标数
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		
		try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	  }catch(Exception e ){}
    
	   Connection myconn=DriverManager.getConnection(url, user, password);
	
	  /* Statement stat1=myconn.createStatement();
	   ResultSet rst1=stat1.executeQuery("select * from v$version");
	   while(rst1.next())
	   {
	       System.out.println(rst1.getString(1));
	   }*/
	   
	   /*rst1=stat1.executeQuery("select distinct sid from v$mystat");
	
	   while (rst1.next()){
	       System.out.println("MY SID IS "+rst1.getString(1));
	   }*/
	
	   PreparedStatement s[]=new PreparedStatement[2000];
	   PreparedStatement p;
	   
	   /**
	    * OK
	    */
//	    p=myconn.prepareStatement("select * from CRM_REASON where REASONID = ?");
	   
	   
	   //ResultSet r[]=new ResultSet[2000];
	   int i=0;
	   while(i<10){
	    //  m[i]=DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.121:1521:G10R2", "maclean", "maclean");
	      //s[i]=m[i].createStatement();
	      //m[i].setAutoCommit(false);
	      //s[i].execute("insert into testjava values(1)");
		   
		
		   /**
		    * For Test
		    */
	       p=myconn.prepareStatement("select * from CRM_REASON where REASONID = ?");
	       p.setString(1, "ASYPZ" + i);
		   
//	       p=myconn.prepareStatement("select * from CRM_REASON where REASONID = 'S900'");
//	       p.setString(1, "SY" + i);
	   	
	   	
	       p.execute();
	
	            try {
	                Thread.sleep(200);
	            } catch (InterruptedException ex) {
//	                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	            }
	
	      i++;
	      System.out.println(i+" cursor is ok !");
	      
          if(p != null)
          {
        	  p.close();
          }
	   }
	   
	   System.out.println("SESSION CURSOR CACHED...");
	   
	   // SESSION CURSOR CACHED
	   i = 0;
	   while(i<10){
		    //  m[i]=DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.121:1521:G10R2", "maclean", "maclean");
		      //s[i]=m[i].createStatement();
		      //m[i].setAutoCommit(false);
		      //s[i].execute("insert into testjava values(1)");
			   
			
			   /**
			    * For Test
			    */
		       p=myconn.prepareStatement("select * from CRM_REASON where REASONID = ?");
		       p.setString(1, "SYP" + i);
			   
//		       p=myconn.prepareStatement("select * from CRM_REASON where REASONID = 'S900'");
//		       p.setString(1, "SY" + i);
		   	
		   	
		       p.execute();
		
		            try {
		                Thread.sleep(200);
		            } catch (InterruptedException ex) {
//		                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		            }
		
		      i++;
		      System.out.println(i+" cursor is ok !");
		      
	          /*if(p != null)
	          {
	        	  p.close();
	          }*/
		   }
	   
	   
	   
	   System.out.println("WHILTE OVER!");
	   
	}
	
	/**
	 * 以下JAVA代码会打个一个数据库会话，并在循环中不断以prepareStatement对象执行SQL语句，且我们不使用close()方法关闭prepareStatement所打开的游标，实
	 * 际上这是很多JDBC应用产生ORA-01000问题的主要原因，开发人员在使用prepareStatement时不知道要使用close()方法以回收OPEN CURSOR资源。
     * 注意这里在使用JDBC API时的表现(可能是目前最流行应用形式)和PL/SQL中的游标是存在区别的，在PL/SQL使用close cursor语句并不会真正意义上关闭游标。
     * 出于性能的考量，PL/SQL中的游标将被缓存以备将来使用，同时Oracle会维护一张cursor的LRU列表，但如果当本会话的游标数量即将达到open_cursors参数所定义的上限数量时，
     * 老的游标将被真正意义上close，以便open后来者。
     * 
	 * 关于V$OPEN_CURSOR
	 * @param args
	 * @throws SQLException
	 * @see http://www.askmaclean.com/archives/about-dynamic-view-open_cursor.html
	 */
	public static void main2(String[] args) throws SQLException {
          try {
		        Class.forName("oracle.jdbc.driver.OracleDriver");
		  }catch(Exception e ){}
        
//		   Connection cnn1=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:G11R2", "maclean", "maclean");
		
		  // Connection m[]=new Connection[2000];
		   Connection myconn=DriverManager.getConnection(url, user, password);
		
		   Statement stat1=myconn.createStatement();
		   ResultSet rst1=stat1.executeQuery("select * from v$version");
		   while(rst1.next())
		   {
		       System.out.println(rst1.getString(1));
		   }
		   rst1=stat1.executeQuery("select distinct sid from v$mystat");
		
		   while (rst1.next()){
		       System.out.println("MY SID IS "+rst1.getString(1));
		   }
		
		   PreparedStatement s[]=new PreparedStatement[2000];
		   PreparedStatement p;
		   
		   /**
		    * OK
		    */
//		   p=myconn.prepareStatement("select /* FIND_ME_OPPO */ * from dual");
		   
		   
		   //ResultSet r[]=new ResultSet[2000];
		   int i=0;
		   while(i<2000){
		    //  m[i]=DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.121:1521:G10R2", "maclean", "maclean");
		      //s[i]=m[i].createStatement();
		      //m[i].setAutoCommit(false);
		      //s[i].execute("insert into testjava values(1)");
			   
			   /**
			    * For Test
			    */
		       p=myconn.prepareStatement("select /* FIND_ME_OPPO */ * from dual");
		       p.execute();
		
		            try {
		                Thread.sleep(200);
		            } catch (InterruptedException ex) {
//		                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		            }
		            
		           /* if(p != null)
			          {
			        	  p.close();
			          }*/
		
		      i++;
		      System.out.println(i+" cursor is ok !");
		      
	          if(p != null)
	          {
	        	  p.close();
	          }
		   }
	 }
	
/*	public static void main(String[] args) throws SQLException {
		
        Connection conn = null;;
		
		try {
			  
			conn = JdbcUtils.getConnection(url, user, password);
			conn.setAutoCommit(false);
			QueryRunner runner = new QueryRunner();
			String sql = "select * from crm_reason where REASONID = ? ";
			Object[]  params = {"1001"};
			Reason reason = runner.query(conn, sql, params, new BeanHandler<Reason>(Reason.class));
			System.out.println("id:" + reason.getREASONID() + ", name:" + reason.getREASONNAME());
			
			conn.commit();
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}finally {
			
			conn.setAutoCommit(true);
			if(conn != null){
				conn.close();
			}
		}
	}
	*/
	
	public static class Reason {
		
		private String EID;
		private String REASONID;
		private String REASONNAME;
		public String getEID() {
			return EID;
		}
		public void setEID(String eID) {
			EID = eID;
		}
		public String getREASONID() {
			return REASONID;
		}
		public void setREASONID(String rEASONID) {
			REASONID = rEASONID;
		}
		public String getREASONNAME() {
			return REASONNAME;
		}
		public void setREASONNAME(String rEASONNAME) {
			REASONNAME = rEASONNAME;
		}
	}
	
}
