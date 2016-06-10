import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestLifeCircleServlet extends HttpServlet {
	
	public TestLifeCircleServlet() {
		System.out.println("constructor!");
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 System.out.println("doGet!");
	}
	@Override
	public void init(ServletConfig config) throws ServletException {
		 System.out.println("init");
	}
	@Override
	public void destroy() {
		 System.out.println("destory");
	}
	

}
