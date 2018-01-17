package f.cn.itcast.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c.cn.itcast.domain.User;
import e.cn.itcast.service.UserService;
/*
 *  用于 处理用户登录请求的  servlet .
 * 
 */
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 处理 乱码 
		request.setCharacterEncoding("UTF-8");
		
		//获得用户的 用户名 和密码 
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		// 创建一个 标准的 userjavabean对象, 用户封装 数据
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		// web层,调用 业务层 
		
		UserService userService = new UserService();
		
		// 返回 登录的 user对象
		User existUser = userService.login(user);
		
		// 判断 用户 是否 为空, 如果为空, 则说明 登录失败,否则, 则跳转到 首页.
		
		if(existUser==null){
			// 说明 用户名 或 密码 错误, 跳转到 登录页面,   用请求转发技术
			
			request.setAttribute("message", "哥们, 用户名 或 密码错误 ,找 刺激 ");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return ;
		}else{
			
			// 重定向 到 首页 
			
			request.getSession().setAttribute("existUser", existUser);
			response.sendRedirect("/day12/index.jsp");
			
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
