package e.cn.itcast.service;

import java.util.List;

import org.junit.Test;

import c.cn.itcast.domain.User;
import d.cn.itcast.dao.UserDao;
import d.cn.itcast.dao.UserDao2;
/*
 * 
 *  是处于 web经典三层 结构的 业务层 : 通常 这个 类 是个普通的 javabean, 
 *  被 web调用, 然后 在这个类 中, 最终 又 调用到  dao层, 在这个过程 ,传递的是 封装了数据的 标 准的 javabean对象 
 * 
 */
public class UserService2 {
	
	@Test
	public void testInsert(){
		
		// 获得 dao对象 ,用来  被调用
		UserDao2 dao = new UserDao2();
		// 创建 user对象 
		User user = new User();
		
		// 给user 对象封装数据
		user.setId(5);
		user.setName("yr");
		user.setPassword("yrr");
		user.setEmail("yrr@itcast.cn");
		
		// 调用dao的 insert方法 添加 用户 
		dao.insert(user	);
		
	}
	
	@Test
	public void testUpdate(){
		
		UserDao dao = new UserDao();
		User user = new User();
		user.setId(3);
		user.setName("陈重");
		user.setPassword("zcXXXXXXXXX");
		
		// 调用 update方法 , 更新 用户的 信息 
		dao.update(user);
		
	}
	
	@Test
	public void testDelete(){
		
		UserDao dao = new UserDao();
		User user = new User();
		user.setName("yr");
		
		// 调用delete 方法 ,将用户 给删除 , 
		dao.delete(user);
		
	}
	
	// 查询所有的用户, 并将其 放到 一个 list集合中
	@Test
	public void testQueryAll(){
		
		UserDao dao = new UserDao();
		List<User> list = dao.queryAll();
		
		for (User user : list) {
			System.out.println(user);
		}
	}
	
	public User login(User user){
		
		UserDao dao = new UserDao();
		return dao.findByNameAndPassword(user);
	}
}
