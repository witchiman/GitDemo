package com.hui.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hui.dao.UserDAO;
import com.hui.model.*;

/**
 * 用户管理
 * */

@WebServlet(name = "UserRegister", urlPatterns = {"/UserRegister"})
public class UserRegister extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		response.setContentType("text/html;charset=GBK");
		String action = request.getParameter("action");
		String username = request.getParameter("username");
		if (action.equals("add") ||action.equals("updateConfirm")) {
			String password = request.getParameter("password");
			String sex = request.getParameter("sex");
			String date = request.getParameter("date");
			int number = Integer.valueOf(request.getParameter("number"));
			
			User u = new User();
			UserDAO ud = new UserDAO();			
			u.setName(username);
			u.setPassword(password);			
			u.setSex(sex);
			u.setDate(date);
			u.setNumber(number);

			if (action.equals("add")) {
				if (ud.addUser(u)) {
					request.getRequestDispatcher("actionResult.jsp?result=成功&action=用户注册").forward(request, response);
				} else {
					request.getRequestDispatcher("actionResult.jsp?result=失败&action=用户注册").forward(request, response);

				}
			} else if (action.equals("updateConfirm")) {
				if (ud.updateUser(u)) {
					request.getRequestDispatcher("actionResult.jsp?result=成功&action=更新用户信息").forward(request, response);
				} else {
					request.getRequestDispatcher("actionResult.jsp?result=失败&action=更新用户信息").forward(request, response);

				}
			}
		}else if(action.equals("update")) {
			UserDAO ud = new UserDAO();
			User u = (User) ud.showPerson(username);
			request.setAttribute("updateInfo", u);
			request.getRequestDispatcher("userRegister.jsp").forward(request, response);
		}else if (action.equals("delete")) {
			UserDAO ud = new UserDAO();
			if (ud.delUser(username)) {
				request.getRequestDispatcher("UserSearch?delok=ok").forward(request, response);
			} else {
				request.getRequestDispatcher("actionResult.jsp?result=失败&action=删除用户").forward(request, response);
			}

		}



	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP
	 * <code>GET</code> method.
	 * <p/>
	 * @param request  servlet request
	 * @param response servlet response
	 * <p/>
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP
	 * <code>POST</code> method.
	 * <p/>
	 * @param request  servlet request
	 * @param response servlet response
	 * <p/>
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * <p/>
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
