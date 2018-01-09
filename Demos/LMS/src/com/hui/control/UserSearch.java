package com.hui.control;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hui.dao.UserDAO;
import com.hui.model.*;

/**
 *ÓÃ»§ËÑË÷
 */
@WebServlet(name = "UserSearch", urlPatterns = {"/UserSearch"})
public class UserSearch extends HttpServlet {

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
		request.setCharacterEncoding("GBK");
		response.setContentType("text/html;charset=GBK");
		int pageSize = 10;
		int pageNow = 1;
		String searchString = request.getParameter("userSearch");
		String pageNowString = request.getParameter("pageNow");
		String identity = request.getParameter("admin");
		 
		if (pageNowString != null) {
			pageNow = Integer.parseInt(pageNowString);
		}
		UserDAO ud = new UserDAO();
		System.out.println(searchString);
		int resultCount = ud.getSearchCount(searchString);
		int pageCount = (int) Math.ceil(1.0 * resultCount / pageSize);
		request.setAttribute("pageCount", pageCount + "");

		ArrayList<User> list = ud.searchUser(searchString, pageNow, pageSize);
		request.setAttribute("searchresult", list);
	 
		request.getRequestDispatcher("userSearch.jsp?userSearch=" + searchString + "&pageNow=" + pageNow).forward(request, response);
		 

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
