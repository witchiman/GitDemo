
<%@page import="com.hui.model.Book"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.hui.model.User"%>
<%@page import="com.hui.model.Admin"%>
<%@page contentType="text/html" pageEncoding="GBK"%>
<%  
	User u = null;
	Admin a = null;
	String identity  = null;
	if ((u=(User)request.getSession().getAttribute("userInfo")) != null) {
		identity = "user";
	} else if((a=(Admin)request.getSession().getAttribute("adminInfo"))!=null){
		identity = "admin";
	}
	ArrayList<Book> list= (ArrayList<Book>) request.getAttribute("searchresult");
	int pageCount = Integer.parseInt((String) request.getAttribute("pageCount"));
	int pageNow = Integer.parseInt(request.getParameter("pageNow"));
	String searchText = request.getParameter("searchText");
%>
<script type="text/javascript">
	function delBook() {
		window.document.getElementsByName("action").value = "delete";
		return window.confirm("Are you sure?");
	}


</script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title>"<%=searchText%>"的搜索结果</title>
		<link href="css/result.css" rel="stylesheet" type="text/css">
    </head>
    <body>
		<table width="95%" border="0" align="center">
			<tr>
			<td><div id="headright"><table width="100%" border="0" class="text">
			<tr>
				<%
					if (identity == null) {
				%>		
				    <td><a href="index.jsp">首页</a></td>
					<td><a href="login.jsp">请登录</a></td>
								
				<%} else if(identity.equals("user")){%>
					<%if (u != null) {%>
							<td><a href="index.jsp">首页</a></td>
							<td><a href="userInfo.jsp">个人信息</a></td>
							<td><a href="RecordShow">借阅记录</a></td>
							<td><a href="FineShow">罚款记录</a></td>
							<td><a href="Logout">退出</a></td>						 
					<%}	%>	 
				<%}	else{%>
				 		<%if (a != null) {%>
						<td><a href="index.jsp">首页</a></td>
						<td><a href="adminIndex.jsp">管理首页</a></td>
						<td><a href="RecordShow">借阅记录</a></td>
						<td><a href="FineShow">罚款记录</a></td>
						<td><a href="Logout">退出</a></td>
					<%}%>
				<%}%>
			</tr>
			</table></div></td>
			<tr>
				<td align="center"><table width="100%" border="1" class="text">
				<br>
				<br>
				<br>
				<br>
						<tr>
							<th>ISBN</th>
							<th>书名</th>
							<th>作者</th>
							<th>类型</th>
							<th>定价</th>
							<th>馆藏</th>
								<%if (identity!=null &&identity.equals("admin")) {%>
								<th>修改</th><th>删除</th>
								<%}%>
						</tr>
						<%
							//System.out.println(al);
							if (list != null)
								if (!list.isEmpty()) {
									for (Book book : list) {
						%><tr>
							<td><%=book.getISBN()%></td>
							<td><%=book.getName()%></td>
							<td><%=book.getAuthor()%></td>
							<td><%=book.getCategory()%></td>
							<td><%=book.getPrice()%></td>
							<td><%=book.getNumber()%></td>
							<%
								if (identity!=null &&identity.equals("admin")) {
							%> <td><form method="post" action="BookManage">
									<input type="hidden" name="ISBN" value="<%=book.getISBN()%>">
									<input type="hidden" name="action" value="update">
									<input type="submit" name="button" id="button" value="修改" >
								</form><%
							%><td><form method="post" action="BookManage">
									<input type="hidden" name="ISBN" value="<%=book.getISBN()%>">
									<input type="hidden" name="action" value="delete">
									<input type="hidden" name="searchText" value="<%=searchText%>">
									<input type="hidden" name="pageNow" value="<%=pageNow%>">
									<input type="hidden" name="pageCount" value="<%=pageCount%>">
									<input type="submit" name="button" id="button" value="删除" onClick="return delBook();"> </form><%
										}
									%></tr>
							<%
								}
							} else {
							%><tr><td colspan="9" align="center">俺啥也没找到呀！(≧﹏ ≦) </td></tr><%									}

						%>

						<tr>
							<td colspan="9" align="center"><%
								if (pageNow != 1) {
								%><a href="BookSearch?searchText=<%=searchText%>&pageNow=1">首页</a><%
								%>&nbsp;<%
								%><a href="BookSearch?searchText=<%=searchText%>&pageNow=<%=pageNow - 1%>">上一页</a><%
								%>&nbsp;<%
									}
									for (int i = pageNow - 2; i <= pageNow + 2; i++) {
										if (i <= 0) {
											continue;
										}
										if (i > pageCount) {
											break;
										}
										if (i != pageNow) {
								%><a href="BookSearch?searchText=<%=searchText%>&pageNow=<%=i%>">[<%=i%>]</a><%
								} else {
								%>><%=i%><<%
									}
								%>&nbsp;<%
									}
									if (pageNow != pageCount & pageCount != 0) {
								%><a href="BookSearch?searchText=<%=searchText%>&pageNow=<%=pageNow + 1%>">下一页</a><%
								%>&nbsp;<%
								%><a href="BookSearch?searchText=<%=searchText%>&pageNow=<%=pageCount%>">末页</a><%
									}
								%></td>
						</tr>
					</table></td>
			</tr>
		</table>

    </body>
</html>
