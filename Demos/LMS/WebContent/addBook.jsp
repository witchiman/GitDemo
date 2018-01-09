<%@page import="com.hui.model.Book"%>
<%@page import="com.hui.model.Admin"%>
<%@page contentType="text/html" pageEncoding="GBK"%>
<%
	Admin a = (Admin)request.getSession().getAttribute("adminInfo");
	if (a == null) {
		response.sendRedirect("login.jsp");
	}
	String action = request.getParameter("action");
	String titleValue = "";
	String buttonValue = "";
	String actionValue = "";
	String readonly = "";
	Book b = new Book();
	if (action.equals("add")) {
		titleValue = "添加图书";
		buttonValue = "添加";
		actionValue = "add";
	} else if (action.equals("update")) {
		b = (Book) request.getAttribute("bookInfo");
		titleValue = "修改图书";
		buttonValue = "修改";
		readonly = "readonly disable";
		actionValue = "updateConfirm";
	}

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title>图书管理</title>
		<link href="css/result.css" rel="stylesheet" type="text/css">
    </head>
    <body>
		<table width="95%" border="0" align="center">
			<tr>
				<td><div id="headright"><table width="100%" border="0" class="text">
					<tr>
						<td><a href="index.jsp">首页</a></td>
						<td><a href="adminIndex.jsp">管理首页</a></td>
						<td><a href="bookManage.jsp">图书管理</a></td>
						<td><a href="RecordShow">借阅记录</a></td>
						<td><a href="FineShow">罚款记录</a></td>
						<td><a href="Logout">退出</a></td>
					</tr>
				</table></div></td>
			</tr>
			<tr>
				<td align="center"><div id="infobox">

						<table width="100%" border="0">
							<tr>
								<td colspan="2" align="center"><div id="title"><%=titleValue%></div></td>
							</tr>
                            <tr>
								<td><form  action="BookManage?action=<%=actionValue%>" method="post"><table width="100%" border="0">
											<tr>
												<td width="33%" align="right">ISBN：</td>
												<td width="33%" align="center"><input name="ISBN" type="text" id="textfield" <%if(b.getISBN()!=null) {%>value="<%=b.getISBN()%>" <%} %><%=readonly%>></td>
												<td width="33%">&nbsp;</td>
											</tr>
											<tr>
												<td width="33%" align="right">书名：</td>
												<td width="33%" align="center"><input type="text" <%if(b.getName()!=null) {%>value="<%=b.getName()%>"<%} %> name="name" id="textfield">
												</td>
												<td width="33%">&nbsp;</td>
											</tr>
											<tr>
												<td width="33%" align="right">作者：</td>
												<td width="33%" align="center"><input type="text" <%if(b.getAuthor()!=null) { %>value="<%=b.getAuthor()%>" <%} %>name="author" id="textfield"></td>
												<td width="33%">&nbsp;</td>
											</tr>											
											<tr>
												<td width="33%" align="right">图书类型：</td>
												<td width="33%" align="center"><input type="text" <%if(b.getCategory()!=null) {%>value="<%=b.getCategory()%>" <%} %>name="category" id="textfield"></td>
												<td width="33%">&nbsp;</td>
											</tr>
											<tr>
												<td width="33%" align="right">定价：</td>
												<td width="33%" align="center"><input type="text" value="<%=b.getPrice()%>" name="price" id="textfield"></td>
												<td width="33%">&nbsp;</td>
											</tr>
										
											<tr>
												<td width="33%" align="right">馆藏：</td>
												<td width="33%" align="center"><input type="text" value="<%=b.getNumber()%>" name="number" id="textfield"></td>
												<td width="33%">&nbsp;</td>
											</tr>
											<tr>
												<td colspan="3" align="center"><input type="submit" name="button" id="button" value="<%=buttonValue%>"></td>
											</tr>
										</table>
									</form></td></tr>
						</table>
					</div></td>
			</table>
	</body>
</html>
