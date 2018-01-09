package com.hui.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hui.dao.FineDAO;
import com.hui.model.Admin;
import com.hui.model.User;

/**
 * 处理罚款记录
 * @author HUI
 *
 */
@WebServlet(name="FineShow" , urlPatterns="/FineShow")
public class FineShow extends HttpServlet{
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		request.setCharacterEncoding("gbk");
		response.setContentType("text/html;charset=GBK");
		Admin a = (Admin) request.getSession().getAttribute("adminInfo");
		User u = null;
		String name;
		String nameType;
		
		if(a!=null) {
			name = a.getName();
			nameType="adminName";	
		}else {
			u = (User)request.getSession().getAttribute("userInfo");
			name = u.getName();
			nameType = "userName";
		}
		
		String pageNowString = request.getParameter("pageNow");
		int pageNow = 1;
		int pageSize = 10;
		if (pageNowString != null) {
			pageNow = Integer.parseInt(pageNowString);
		}
		
		FineDAO fd = new FineDAO();
		request.setAttribute("fineList", fd.showFine(name, nameType, pageNow, pageSize));
		int rowCount = fd.getRowCount();
		int pageCount = (int) Math.ceil(1.0 * rowCount / pageSize);
		
		request.getRequestDispatcher("fineShow.jsp?pageNow=" + pageNow + "&pageCount=" + pageCount).forward(request, response);
		 
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
