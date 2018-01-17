package a.cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;


/*
 *  jdbc 快速 入门 程序 : 
 *  
1. 导入 驱动 :  mysql-connector-java-5.0.8-bin.jar
2. 创建目标数据库 
create database day12;
use day12;

create table user(
  id int primary key auto_increment,
  name varchar(20),
  password varchar(30),
  email varchar(30)

);

insert into user values(null,'范超超','456','fcc@itcast.cn');
insert into user values(null,'hxx','123','hxx@itcast.cn');
insert into user values(null,'cz','789','cz@itcast.cn');


	jdbc快速入门小结 :
	
		1. 导驱动
		2. 写个java类, 有个方法, 通过 DriverManager注册驱动
		3. 获得 连接对象Connetion , 需要 url地址, 以及 用户名和密码 .
		4. 获得 statment对象, 用于 向 数据库 发送 sql语句 : select * from user;
		5. 返回的是 一个 结果集 对象, 这个 结果集对象封装了 sql查询的所有的 结果 .
		6. 遍历 结果集 , 解析 各个部分的数据
		7. 关闭资源 .
		   结果集, statement对象, connetion对象.


 */
public class JdbcTest {
	
	// 快速 入门 :　查询　数据库的　数据　
	@Test
	public void test1(){
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
//		1. 注册 驱动  :// 需要 new 一个 Driver进来 .
//			DriverManager.registerDriver(new Driver());
			
			// 类 加载的 时候  会做 什么 ?  会将 静态的 代码 块 以及 静态的 变量, 方法 都被 加载 进来.
			
			// mysql -h localhost -uroot -perqiu
			// use day12;
			
			// 拿到 一个 代表 与数据库 建立起 连接的 connection对象 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day12", "root", "erqiu");
			
			// 注意, 要发送 sql语句 , 需要 通过 一个 statement对象 去 实现
			// 用于 向 数据库 发送 sql语句
			stmt = conn.createStatement();
			
			//  发送 sql语句 并执行, 返回 结果集 对象 ,这个结果集 对象 就封装了 从数据库 中查询出来的所有的数据
			rs = stmt.executeQuery("select * from user");
			
			// 从 结果集 对象中 解析 各个 部分的 数据
			
			// 将 rs 的 "指 针" 挪到 第一行 .
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
			// 注意:还要关闭资源　
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
			// 类 加载的 时候  会做 什么 ?  会将 静态的 代码 块 以及 静态的 变量, 方法 都被 加载 进来.
			
//			 由于  Class.forName()  可以加载类的 字节码 ,而 加载 类的时候 又会 执行  静态的 代码快, 所以可以通过这种方式巧妙的 完成 注册 .
			
			// 第一个: 避免了 驱动 被 注册 两次 . 第二 : 避免 了 导入 com.mysql.jdbc.Driver 类 . 
			Class.forName("com.mysql.jdbc.Driver");
			
			
			// 拿到 一个 代表 与数据库 建立起 连接的 connection对象 
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day12", "root", "erqiu");
			// 注意 : 上面的 url的 写法 可以简写为   :jdbc:mysql:///day12
			conn = DriverManager.getConnection("jdbc:mysql:///day12", "root", "erqiu");
			
			// 注意, 要发送 sql语句 , 需要 通过 一个 statement对象 去 实现
			// 用于 向 数据库 发送 sql语句
			stmt = conn.createStatement();
			
			//  发送 sql语句 并执行, 返回 结果集 对象 ,这个结果集 对象 就封装了 从数据库 中查询出来的所有的数据
			rs = stmt.executeQuery("select * from user");
			
			// 从 结果集 对象中 解析 各个 部分的 数据
			
			// 将 rs 的 "指 针" 挪到 第一行 .
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
			// 注意:还要关闭资源　
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
