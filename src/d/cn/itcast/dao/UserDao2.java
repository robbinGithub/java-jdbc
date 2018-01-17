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
 *   ʹ�� preapraedStatement ���� dao
 * 
 */
public class UserDao2 {

	public void insert(User user) {
		
		Connection conn = null;
		PreparedStatement stmt = null ;
		ResultSet rs = null;
		try{
			// ��� connection���� 
			conn = JdbcUtils.getConnection();
			
			// ��������ݿⷢ�� sql��� �� statement����
//			String sql = "insert into user values(null,?,?,?)";
			String sql = "insert into user values (null, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1,user.getName());
			stmt.setString(2,user.getPassword());
			stmt.setString(3,user.getEmail());
//			stmt.setObject(parameterIndex, x)
			
		//	String sql = "insert into user values(null,'"+user.getName()+"','"+user.getPassword()+"','"+user.getEmail()+"')";
			System.out.println(sql);
			
			stmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			// �ͷ���Դ 
			JdbcUtils.release(rs, stmt, conn);
		}
	}

	public void update(User user) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			conn = JdbcUtils.getConnection();
			String sql = "update user set name=?,password=? where id=?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setObject(1, user.getName());
			stmt.setObject(2, user.getPassword());
			stmt.setObject(3, user.getId());
			
			//stmt.executeUpdate("update user set name="+user.getName()+",password="+user.getPassword()+", where id="+user.getId()+"");
			
//			String sql = "update user set name='"+user.getName()+"',password='"+user.getPassword()+"' where id="+user.getId()+"";
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.release(rs, stmt, conn);
		}
	}

	public void delete(User user) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = JdbcUtils.getConnection();
			stmt = conn.createStatement();
			
			stmt.executeUpdate("delete from user where name='"+user.getName()+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.release(rs, stmt, conn);
		}
	}

	// select * from user where name='zs';    --->> User
	// select * from user :��List
	public List<User> queryAll(){
		
		Connection conn = null;
		Statement stmt = null;
		
		ResultSet rs = null;
		
		try {
			
			conn = JdbcUtils.getConnection();
			
			stmt = conn.createStatement();
			
			rs= stmt.executeQuery("select * from user");
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

}
