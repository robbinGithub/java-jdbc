package e.cn.itcast.service;

import java.util.List;

import org.junit.Test;

import c.cn.itcast.domain.User;
import d.cn.itcast.dao.UserDao;
import d.cn.itcast.dao.UserDao2;
/*
 * 
 *  �Ǵ��� web�������� �ṹ�� ҵ��� : ͨ�� ��� �� �Ǹ���ͨ�� javabean, 
 *  �� web����, Ȼ�� ������� ��, ���� �� ���õ�  dao��, ��������� ,���ݵ��� ��װ�����ݵ� �� ׼�� javabean���� 
 * 
 */
public class UserService2 {
	
	@Test
	public void testInsert(){
		
		// ��� dao���� ,����  ������
		UserDao2 dao = new UserDao2();
		// ���� user���� 
		User user = new User();
		
		// ��user �����װ����
		user.setId(5);
		user.setName("yr");
		user.setPassword("yrr");
		user.setEmail("yrr@itcast.cn");
		
		// ����dao�� insert���� ��� �û� 
		dao.insert(user	);
		
	}
	
	@Test
	public void testUpdate(){
		
		UserDao dao = new UserDao();
		User user = new User();
		user.setId(3);
		user.setName("����");
		user.setPassword("zcXXXXXXXXX");
		
		// ���� update���� , ���� �û��� ��Ϣ 
		dao.update(user);
		
	}
	
	@Test
	public void testDelete(){
		
		UserDao dao = new UserDao();
		User user = new User();
		user.setName("yr");
		
		// ����delete ���� ,���û� ��ɾ�� , 
		dao.delete(user);
		
	}
	
	// ��ѯ���е��û�, ������ �ŵ� һ�� list������
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
