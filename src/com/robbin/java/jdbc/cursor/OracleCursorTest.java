package com.robbin.java.jdbc.cursor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Oracle �α����
 * @author robbin.zhang
 * @date 2018/02/05 22:53
 *
 */
public class OracleCursorTest {
	
	private static final String url = "";
	private static final String user = "";
	private static final String password = "";
	
	/**
	 * ����JAVA�������һ�����ݿ�Ự������ѭ���в�����prepareStatement����ִ��SQL��䣬�����ǲ�ʹ��close()�����ر�prepareStatement���򿪵��α꣬ʵ
	 * �������Ǻܶ�JDBCӦ�ò���ORA-01000�������Ҫԭ�򣬿�����Ա��ʹ��prepareStatementʱ��֪��Ҫʹ��close()�����Ի���OPEN CURSOR��Դ��
     * ע��������ʹ��JDBC APIʱ�ı���(������Ŀǰ������Ӧ����ʽ)��PL/SQL�е��α��Ǵ�������ģ���PL/SQLʹ��close cursor��䲢�������������Ϲر��αꡣ
     * �������ܵĿ�����PL/SQL�е��α꽫�������Ա�����ʹ�ã�ͬʱOracle��ά��һ��cursor��LRU�б�����������Ự���α����������ﵽopen_cursors�������������������ʱ��
     * �ϵ��α꽫������������close���Ա�open�����ߡ�
     * 
	 * ����V$OPEN_CURSOR
	 * @param args
	 * @throws SQLException
	 * @see http://www.askmaclean.com/archives/about-dynamic-view-open_cursor.html
	 */
	public static void main(String[] args) throws SQLException {
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
