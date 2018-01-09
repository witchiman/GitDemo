/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hui.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hui.dao.RecordDAO;
import com.hui.model.*;

 /**
  * 记录用户的借阅信息
  * */

@WebServlet(name = "RecordShow", urlPatterns = {"/RecordShow"})
public class RecordShow extends HttpServlet {
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("gbk");
		response.setContentType("text/html;charset=GBK");
		User u = (User) request.getSession().getAttribute("userInfo");
		String name = null;
		String nameType = null;
		Admin a = null;
		if(u != null) {
			name = u.getName();
			nameType = "username";
		}else {
			a = (Admin)request.getSession().getAttribute("adminInfo");
			name = a.getName();
			nameType="adminname";
		}
		System.out.println(name);
		
		String viewType = request.getParameter("viewType");
		String pageNowString = request.getParameter("pageNow");
		if (viewType == null) {
			viewType = "all";
		}

		int pageNow = 1;
		int pageSize = 10;
		if (pageNowString != null) {
			pageNow = Integer.parseInt(pageNowString);
		}

		RecordDAO rd = new RecordDAO();

		if (viewType.equals("return")) {
			request.setAttribute("recordList", rd.getRecordList(name, nameType,"return",pageNow, pageSize));
		} else if (viewType.equals("borrow")) {
			request.setAttribute("recordList", rd.getRecordList(name, nameType, "borrow",pageNow, pageSize));
		} else {
			request.setAttribute("recordList", rd.getRecordList(name, nameType,"all",pageNow, pageSize));
		}

		int rowCount = rd.getRowCount();
		int pageCount = (int) Math.ceil(1.0 * rowCount / pageSize);
		request.getRequestDispatcher("recordShow.jsp?viewType=" + viewType + "&pageNow=" + pageNow + "&pageCount=" + pageCount).forward(request, response);

	}

	 
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

 
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	 
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
