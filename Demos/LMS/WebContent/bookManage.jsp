<%@page import="com.hui.model.Admin"%>
<%@page contentType="text/html" pageEncoding="GBK"%>
<!DOCTYPE html>
<%
	Admin a = (Admin) request.getSession().getAttribute("adminInfo");
	if (a == null) {
		response.sendRedirect("login.jsp");
	}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title>图书管理</title>
		<link href="css/result.css" rel="stylesheet" type="text/css">
		<style type="text/css">

			input.groovybutton
			{
				font-weight:bold;
				color:#00FFFF;
				background-color:#999999;
				border-style:dashed;
			}

		</style>

		

    </head>
    <body>
		<table width="95%" border="0" align="center">
			<tr>
				<td><div id="headright"><table width="100%" border="0" class="text">
					<tr>
						<td><a href="index.jsp">首页</a></td>
						<td><a href="adminIndex.jsp">管理首页</a></td>
						<td><a href="userManage.jsp">用户管理</a></td>
						<td><a href="Logout">退出</a></td>
					</tr>
				</table></div></td>
			</tr>
			<tr>
				<td><table width="100%" border="0" class="maintable">

						<tr>
							<td>
							<br>
							<br>
							<br>
							<br>
							<br>
								<form name="groovyform" action="addBook.jsp?action=add" method="post">
									<input type="image" name="submit" src="images/bookadd.jpg"/>
								</form>
								<br>
								<br>
								<div id="searchbox"><form name="form1" method="post" action="BookSearch">
										<input name="searchText" type="text" class="components" id="searchText" size="80">
										<input name="button" type="submit" class="components" id="button" value="开始搜索">
									</form></div></td>
						</tr>


					</table></td>
			</tr>
			<tr>
			</tr>
		</table>

	</body>
</html>
