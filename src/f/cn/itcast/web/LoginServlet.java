package f.cn.itcast.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c.cn.itcast.domain.User;
import e.cn.itcast.service.UserService;
/*
 *  ���� �����û���¼�����  servlet .
 * 
 */
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// ���� ���� 
		request.setCharacterEncoding("UTF-8");
		
		//����û��� �û��� ������ 
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		// ����һ�� ��׼�� userjavabean����, �û���װ ����
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		// web��,���� ҵ��� 
		
		UserService userService = new UserService();
		
		// ���� ��¼�� user����
		User existUser = userService.login(user);
		
		// �ж� �û� �Ƿ� Ϊ��, ���Ϊ��, ��˵�� ��¼ʧ��,����, ����ת�� ��ҳ.
		
		if(existUser==null){
			// ˵�� �û��� �� ���� ����, ��ת�� ��¼ҳ��,   ������ת������
			
			request.setAttribute("message", "����, �û��� �� ������� ,�� �̼� ");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return ;
		}else{
			
			// �ض��� �� ��ҳ 
			
			request.getSession().setAttribute("existUser", existUser);
			response.sendRedirect("/day12/index.jsp");
			
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
