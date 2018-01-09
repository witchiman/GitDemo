<%@page import="com.hui.model.Admin"%>
<%@page contentType="text/html" pageEncoding="GBK"%>
<!DOCTYPE html>
<%
	Admin a = (Admin)request.getSession().getAttribute("adminInfo");
	if (a == null) {
		response.sendRedirect("login.jsp");
	}

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title>用户管理</title>
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
						<td><a href="bookManage.jsp">图书管理</a></td>
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
								<form name="groovyform" action="userRegister.jsp?action=add" method="post">
									<input type="image"  name="register" src="images/register.jpg"/> 	
								</form>
								<br>
								<br>
								<br>
								<br>
								<div id="searchbox"><form name="form1" method="post" action="UserSearch">
										<input name="userSearch" type="text" class="components" id="userSearch" size="80">
										<input name="button" type="submit" class="components" id="button" value="搜索用户">
									</form></div></td>
						</tr>


					</table></td>
			</tr>
			
		</table>

	</body>
</html>
