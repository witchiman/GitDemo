package com.hui.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hui.dao.BookDAO;
import com.hui.model.Book;

/**
 * 图书管理*/

@WebServlet(name = "BookrManage", urlPatterns = {"/BookManage"})
public class BookManage extends HttpServlet {

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
		request.setCharacterEncoding("gbk");
		response.setContentType("text/html;charset=GBK");
		String action = request.getParameter("action");
		String ISBN = request.getParameter("ISBN");
		if (action.equals("add")||action.equals("updateConfirm")) {
			Book b = new Book();
			String name = request.getParameter("name");
			String author = request.getParameter("author");
			String category = request.getParameter("category");
			double price = Double.parseDouble(request.getParameter("price"));
			int number = Integer.parseInt(request.getParameter("number"));

			b.setISBN(ISBN);
			b.setName(name);
			b.setAuthor(author);
			b.setCategory(category);
			b.setPrice(price);
			b.setNumber(number);

			BookDAO bd = new BookDAO();
			if (action.equals("add")) {
				if (bd.addBook(b)) {
					request.getRequestDispatcher("actionResult.jsp?result=成功&&action=添加图书").forward(request, response);
				} else {
					request.getRequestDispatcher("actionResult.jsp?result=失败&action=添加图书").forward(request, response);

				}
			} else if (action.equals("updateConfirm")) {
				if (bd.updateBook(b)) {

					request.getRequestDispatcher("actionResult.jsp?result=成功&&action=更新图书信息").forward(request, response);
				} else {
					request.getRequestDispatcher("actonResult.jsp?result=失败&action=更新图书信息").forward(request, response);
				}
			}

		} else if (action.equals("update")) {
			BookDAO bd = new BookDAO();
			Book b = bd.searchBookByISBN(ISBN);
			request.setAttribute("bookInfo", b);

			request.getRequestDispatcher("addBook.jsp").forward(request, response);
		} else if (action.equals("delete")) {
			BookDAO bd = new BookDAO();
			if (bd.delBookByISBN(ISBN)) {
				request.getRequestDispatcher("BookSearch?delok=ok").forward(request, response);
			} else {
				request.getRequestDispatcher("bookSearch.jsp?delok=failed").forward(request, response);
			}

		}


	}

	 
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
