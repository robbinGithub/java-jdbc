package a.cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;


/*
 *  jdbc ���� ���� ���� : 
 *  
1. ���� ���� :  mysql-connector-java-5.0.8-bin.jar
2. ����Ŀ�����ݿ� 
create database day12;
use day12;

create table user(
  id int primary key auto_increment,
  name varchar(20),
  password varchar(30),
  email varchar(30)

);

insert into user values(null,'������','456','fcc@itcast.cn');
insert into user values(null,'hxx','123','hxx@itcast.cn');
insert into user values(null,'cz','789','cz@itcast.cn');


	jdbc��������С�� :
	
		1. ������
		2. д��java��, �и�����, ͨ�� DriverManagerע������
		3. ��� ���Ӷ���Connetion , ��Ҫ url��ַ, �Լ� �û��������� .
		4. ��� statment����, ���� �� ���ݿ� ���� sql��� : select * from user;
		5. ���ص��� һ�� ����� ����, ��� ����������װ�� sql��ѯ�����е� ��� .
		6. ���� ����� , ���� �������ֵ�����
		7. �ر���Դ .
		   �����, statement����, connetion����.


 */
public class JdbcTest {
	
	// ���� ���� :����ѯ�����ݿ�ġ����ݡ�
	@Test
	public void test1(){
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
//		1. ע�� ����  :// ��Ҫ new һ�� Driver���� .
//			DriverManager.registerDriver(new Driver());
			
			// �� ���ص� ʱ��  ���� ʲô ?  �Ὣ ��̬�� ���� �� �Լ� ��̬�� ����, ���� ���� ���� ����.
			
			// mysql -h localhost -uroot -perqiu
			// use day12;
			
			// �õ� һ�� ���� �����ݿ� ������ ���ӵ� connection���� 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day12", "root", "erqiu");
			
			// ע��, Ҫ���� sql��� , ��Ҫ ͨ�� һ�� statement���� ȥ ʵ��
			// ���� �� ���ݿ� ���� sql���
			stmt = conn.createStatement();
			
			//  ���� sql��� ��ִ��, ���� ����� ���� ,�������� ���� �ͷ�װ�� �����ݿ� �в�ѯ���������е�����
			rs = stmt.executeQuery("select * from user");
			
			// �� ����� ������ ���� ���� ���ֵ� ����
			
			// �� rs �� "ָ ��" Ų�� ��һ�� .
			while(rs.next()){
				
				String id = rs.getString("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				
				System.out.println("id : " + id +", name : " + name +", password : " + password +", email : "+ email);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally{
			// ע��:��Ҫ�ر���Դ��
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	@Test
	public void test2(){
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			// �� ���ص� ʱ��  ���� ʲô ?  �Ὣ ��̬�� ���� �� �Լ� ��̬�� ����, ���� ���� ���� ����.
			
//			 ����  Class.forName()  ���Լ������ �ֽ��� ,�� ���� ���ʱ�� �ֻ� ִ��  ��̬�� �����, ���Կ���ͨ�����ַ�ʽ����� ��� ע�� .
			
			// ��һ��: ������ ���� �� ע�� ���� . �ڶ� : ���� �� ���� com.mysql.jdbc.Driver �� . 
			Class.forName("com.mysql.jdbc.Driver");
			
			
			// �õ� һ�� ���� �����ݿ� ������ ���ӵ� connection���� 
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day12", "root", "erqiu");
			// ע�� : ����� url�� д�� ���Լ�дΪ   :jdbc:mysql:///day12
			conn = DriverManager.getConnection("jdbc:mysql:///day12", "root", "erqiu");
			
			// ע��, Ҫ���� sql��� , ��Ҫ ͨ�� һ�� statement���� ȥ ʵ��
			// ���� �� ���ݿ� ���� sql���
			stmt = conn.createStatement();
			
			//  ���� sql��� ��ִ��, ���� ����� ���� ,�������� ���� �ͷ�װ�� �����ݿ� �в�ѯ���������е�����
			rs = stmt.executeQuery("select * from user");
			
			// �� ����� ������ ���� ���� ���ֵ� ����
			
			// �� rs �� "ָ ��" Ų�� ��һ�� .
			while(rs.next()){
				
				String id = rs.getString("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				
				System.out.println("id : " + id +", name : " + name +", password : " + password +", email : "+ email);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally{
			// ע��:��Ҫ�ر���Դ��
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
