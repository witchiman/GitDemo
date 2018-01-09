
<%@page contentType="text/html" pageEncoding="GBK"%>
<%
	String result = request.getParameter("result");
	String action = request.getParameter("action");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title>操作结果</title>
		<link href="css/result.css" rel="stylesheet" type="text/css">
    </head>
    <body>
		<table width="95%" border="0" align="center">
			<tr>
				<td><div id="headright"><table width="100%" border="0" class="text">
					<tr>					
						<td><a href="index.jsp">首页</a></td>
						<td><a href="adminIndex.jsp">管理首页</a></td>
						<td><a href="userManage.jsp">用户管理</a></td>
						<td><a href="BookManage.jsp">图书管理</a></td>
						<td><a href="RecordShow">借阅记录</a></td>
						<td><a href="Logout">安全退出</a></td>
					</tr>
				</table></div></td>
			</tr>
			<tr>
				<td align="center"><div id="infobox">
						<table width="50%" border="1">
							<tr>
								<th colspan="2" align="center">操作结果</th>
							</tr>
							<tr>
								<td width="50%" align="center">操作</td>
								<td width="50%" align="center"><%=action%></td>
							</tr>
							<tr>
								<td width="50%" align="center">结果</td>
								<td width="50%" align="center"><%=result%></td>
							</tr>
						</table>
					</div></td>
			</tr>
		</table>

    </body>
</html>
