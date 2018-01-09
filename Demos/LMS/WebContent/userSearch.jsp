<%@page import="com.hui.model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="GBK"%>
<%
	String identity = null;
	if (request.getSession().getAttribute("userInfo") != null) {
		identity = "user";
	} else if(request.getSession().getAttribute("adminInfo")!=null){
		identity = "admin";
	}
	
	ArrayList<User> list = (ArrayList<User>) request.getAttribute("searchresult");
	int pageCount = Integer.parseInt((String) request.getAttribute("pageCount"));
	int pageNow = Integer.parseInt(request.getParameter("pageNow"));
	String userSearch = request.getParameter("userSearch");
%>
<script type="text/javascript">
	function delUser() {
		window.document.getElementsByName("action").value = "del";
		return window.confirm("确定要删除用户？");
	}


</script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=userSearch%> - 搜索结果</title>
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
					<td><a href="login.jsp">请登录</a></td>
								
				<%} else if(identity.equals("user")){%>
						<td><a href="index.jsp">首页</a></td>
						<td><a href="userInfo.jsp">个人信息</a></td>
						<td><a href="RecordShow">借阅记录</a></td>
						<td><a href="Logout">退出</a></td>						 
				<%}	else{%>
					<td><a href="index.jsp">首页</a></td>
					<td><a href="adminIndex.jsp">管理首页</a></td>
					<td><a href="userManage.jsp">用户管理</a></td>
					<td><a href="bookManage.jsp">图书管理</a></td>
					<td><a href="Logout">退出</a></td>
				<%}%>
			</tr>
			</table></div></td>
			</tr>
			<tr>
				<td align="center"><table width="100%" border="1" class="text">
				<br>
				<br>
				<br>
				<br>
						<tr>
							<th>用户ID</th>
							<th>姓名</th>
							<th>性别</th>
							<th>注册时间</th>
							<th>可借数量</th>

							<%
								if (identity.equals("admin")) {
							%><th>修改</th><%
							%><th>删除</th><%									}
								%>
						</tr>
						<%
							if (list != null)
								if (!list.isEmpty()) {
									for (User u : list) {
						%><tr>
							<td><%=u.getID()%></td>
							<td><%=u.getName()%></td>
							<td><%=u.getSex()%></td>
							<td><%=u.getDate()%></td>
							<td><%=u.getNumber()%></td>
							<%
								if (identity.equals("admin")) {
							%> <td><form method="post" action="UserRegister">
									<input type="hidden" name="username" value="<%=u.getName()%>">
									<input type="hidden" name="action" value="update">
									<input type="submit" name="button" id="button" value="修改" >
								</form><%
							%><td><form method="post" action="UserRegister">
									<input type="hidden" name="username" value="<%=u.getName()%>">
									<input type="hidden" name="action" value="delete">
									<input type="hidden" name="userSearch" value="<%=userSearch%>">
									<input type="hidden" name="pageNow" value="<%=pageNow%>">
									<input type="hidden" name="pageCount" value="<%=pageCount%>">
									<input type="submit" name="button" id="button" value="删除" onClick="return delUser();"> </form><%
									}
									%></tr>
							<%
								}
							} else {
							%><tr><td colspan="9" align="center">俺啥也没找到呀！(≧﹏ ≦)</td></tr><%									}

						%>

						<tr>
							<td colspan="9" align="center"><%
								if (pageNow != 1) {
								%><a href="UserSearch?userSearch=<%=userSearch%>&pageNow=1">首页</a><%
								%>&nbsp;<%
								%><a href="UserSearch?userSearch=<%=userSearch%>&pageNow=<%=pageNow - 1%>">上一页</a><%
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
								%><a href="UserSearch?userSearch=<%=userSearch%>&pageNow=<%=i%>">[<%=i%>]</a><%
								} else {
								%>><%=i%><<%
									}
								%>&nbsp;<%
									}
									if (pageNow != pageCount & pageCount != 0) {
								%><a href="UserSearch?userSearch=<%=userSearch%>&pageNow=<%=pageNow + 1%>">下一页</a><%
								%>&nbsp;<%
								%><a href="UserSearch?userSearch=<%=userSearch%>&pageNow=<%=pageCount%>">末页</a><%
									}
								%></td>
						</tr>
					</table></td>
			</tr>
			
		</table>

    </body>
</html>
