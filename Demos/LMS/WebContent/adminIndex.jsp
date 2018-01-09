<%@page import="com.hui.model.Admin"%>
<%@page contentType="text/html" pageEncoding="GBK"%>
<%
	Admin a = (Admin)request.getSession().getAttribute("adminInfo");
	if(a==null){
		response.sendRedirect("login.jsp");
	}
	
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title>管理首页</title>
		<link href="css/result.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
			function borrowBook() {
				document.bookAction.action.value = "borrow";
				return window.confirm("确认借书？");
			}

			function returnBook() {
				document.bookAction.action.value = "return";
				return window.confirm("确认还书？");
			}
		</script>
    </head>
    <body>
		<table width="95%" border="0" align="center">
			<tr>
				<td><div id="headright"><table width="100%" border="0" class="text">
					<tr>
						<td><a href="index.jsp">首页</a></td>
						<td><a href="userManage.jsp">用户管理</a></td>
						<td><a href="bookManage.jsp">图书管理</a></td>
						<td><a href="Logout">退出</a></td>
					</tr>
				</table></div></td>
			</tr>
			<tr>
				<td align="center"><div id="admininfo">
						<table width="100%" border="0">
							<tr>
								<td colspan="2"><div id="title">管理员信息</div></td>
							</tr>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td width="40%" align="right">管理员：</td>
								<td><%=a.getID()%></td>
							</tr>
							<tr>
								<td width="40%" align="right">姓名：</td>
								<td><%=a.getName()%></td>
							</tr>
							<tr>
								<td width="40%" align="right">性别：</td>
								<td><%=a.getSex()%></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</div>
					<div id="bookcheck">
						<form name="bookAction" method="post" action="BookAction">
							<table width="100%" border="0">
								<tr>
									<th colspan="2" align="center">借书/还书登记</th>
								</tr>
								<tr>
									<td>ISBN: </td>
									<td><input name="ISBN" type="text" id="isbn" maxlength="20"></td>
								</tr>
								<tr>
									<td>用户: </td>
									<td><input name="username" type="text" id="username" maxlength="10"></td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<input type="hidden" name="action" value="">
										<input type="submit" name="button" id="button" value="借书" onClick="return borrowBook();">
										<input type="submit" name="button2" id="button2" value="还书" onClick="return returnBook();">
									</td>
								</tr>
							</table>
						</form>
					</div></td>
			</tr>
		</table>

	</body>
</html>
