package d.cn.itcast.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import b.cn.itcast.utils.JdbcUtils;
import c.cn.itcast.domain.User;
/*
 *  dao 负责 直接与  数据源 数据库 打交道 , 完成 CRUD操作  , 并且 提供 方法给 业务层, 在业务层调用的时候 需要传递的 to对象
 * 
 */
public class UserDao {

	public void insert(User user) {
		
		Connection conn = null;
		Statement stmt = null ;
		ResultSet rs = null;
		try{
			// 获得 connection对象 
			conn = JdbcUtils.getConnection();
			
			// 获得向数据库发送 sql语句 的 statement对象
			stmt = conn.createStatement();
			
			String sql = "insert into user values(null,'"+user.getName()+"','"+user.getPassword()+"','"+user.getEmail()+"')";
			System.out.println(sql);
			
			stmt.executeUpdate(sql);
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			// 释放资源 
			JdbcUtils.release(rs, stmt, conn);
		}
	}

	public void update(User user) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			
			conn = JdbcUtils.getConnection();
			stmt = conn.createStatement();
			
			//stmt.executeUpdate("update user set name="+user.getName()+",password="+user.getPassword()+", where id="+user.getId()+"");
			
			String sql = "update user set name='"+user.getName()+"',password='"+user.getPassword()+"' where id="+user.getId()+"";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.release(rs, stmt, conn);
		}
	}

	public void delete(User user) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = JdbcUtils.getConnection();
			String sql ="delete from user where name=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getName());
			
//			stmt.executeUpdate("delete from user where name='"+user.getName()+"'");
			
			
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.release(rs, stmt, conn);
		}
	}

	// select * from user where name='zs';    --->> User
	// select * from user :　List
	public List<User> queryAll(){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		ResultSet rs = null;
		
		try {
			
			conn = JdbcUtils.getConnection();
			
//			stmt = conn.createStatement();
			stmt = conn.prepareStatement("select * from user");
			
			rs= stmt.executeQuery();
			List<User> list = new ArrayList<User>();
			
			
			while(rs.next()){
				
				User user = new User();
				int id = rs.getInt("id");
				
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				
				user.setId(id);
				user.setName(name);
				user.setPassword(password);
				user.setEmail(email);
				
				list.add(user);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			JdbcUtils.release(rs, stmt, conn);
		}
		
		return null;
	}

	// select * from user where name=? and password=?;
	public User findByNameAndPassword(User user) {
		
		Connection conn = null;
		
		 PreparedStatement stmt= null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
//			stmt = conn.createStatement();
			
			// 通过  ?  占位符 先 占 一个位, 然后 再去 替换掉 占位符 
			String sql = "select * from user where name=? and password=?";
			
			stmt = conn.prepareStatement(sql);  // 可以 解决 sql注入 的问题 
			stmt.setString(1, user.getName());  // 替换 第一个 占位符 
			
			stmt.setString(2, user.getPassword());  // 替换 第二 个 占位符 
			
			//select * from user where name='zhangsan' and password='123';
			
			//String sql = "select * from user where name='"+user.getName()+"' and password='"+user.getPassword()+"'";
			
			System.out.println(sql);
			
			rs= stmt.executeQuery(sql);
			if(rs.next()){
				
				User existUser = new User();
				int id = rs.getInt("id");
				
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				existUser.setId(id);
				existUser.setName(name);
				existUser.setPassword(password);
				existUser.setEmail(email);
				
				return existUser;
				
			}
			return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
