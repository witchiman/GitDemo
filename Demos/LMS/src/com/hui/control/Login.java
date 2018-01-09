/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hui.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hui.dao.AdminDAO;
import com.hui.dao.PersonDAO;
import com.hui.dao.UserDAO;
import com.hui.model.*;
import com.hui.service.IFactory;
import com.hui.service.impl.AdminDAOFactory;
import com.hui.service.impl.UserDAOFactory;

/**
 *登录时校验用户名和密码，并判断是否为管理员
 */
public class Login extends HttpServlet {

	/**
	 * Processes requests for both HTTP
	 * <code>GET</code> and
	 * <code>POST</code> methods.
	 * <p/>
	 * @param request  servlet request
	 * @param response servlet response
	 * <p/>
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=GBK");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username+"|"+password);
		
		if(username.equals("") || password.equals("")) {
			request.getRequestDispatcher("login.jsp?loginInfo=null").forward(request, response);
			return;		
		}
				
		/*初始代码*/
		/*AdminDAO ad = new AdminDAO();
		UserDAO ud = new UserDAO();

		if (ud.checkUser(username, password)) {
			User u = ud.showUser(username);
			//System.out.println(u.getID());
			request.getSession().setAttribute("userInfo", u);
			request.getRequestDispatcher("index.jsp").forward(request, response);

		}else if(ad.checkPerson(username, password)) {
			Admin a = ad.showAdmin(username);
			request.getSession().setAttribute("adminInfo", a);
			request.getRequestDispatcher("index.jsp").forward(request, response);

		}else {
			request.getRequestDispatcher("login.jsp?loginInfo=failure").forward(request, response); 
		}*/
		
		
		/*采用工厂方法后的代码*/
		IFactory factory= new UserDAOFactory();
		PersonDAO pd = factory.createPersonDAO();
		
		if (pd.checkPerson(username, password)) {
			User u = (User) pd.showPerson(username);
			//System.out.println("user:" + u.getName()+u.getNumber());
			request.getSession().setAttribute("userInfo", u);
			request.getRequestDispatcher("index.jsp").forward(request, response);

		}else {
			factory = new AdminDAOFactory();
			pd = factory.createPersonDAO();
			if(pd.checkPerson(username, password)) {
				Admin a = (Admin) pd.showPerson(username);
				//System.out.println("admin:" + a.getName());
				request.getSession().setAttribute("adminInfo", a);
				request.getRequestDispatcher("index.jsp").forward(request, response);
				
			}else {				
				request.getRequestDispatcher("login.jsp?loginInfo=failure").forward(request, response); 
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
