
<%@page import="com.hui.model.User"%>
<%@page contentType="text/html" pageEncoding="GBK"%>
<%
	User u = (User)request.getSession().getAttribute("userInfo");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title>用户信息</title>
		<link href="css/result.css" rel="stylesheet" type="text/css">
    </head>
    <body>
    
    <table align="center"  width="100%" border="0">
	<tr>
		<td><div id="headright"><table width="100%" border="0" class="text">
					<tr>
					
						<%if (u != null) {%>
						<td><div id="username">您好，<%=u.getName()%></div></td>
						<%} else {%>
						<td><a href="login.jsp">请登录</a></td>
						<%}	%>
						<td>   </td>
                        <td><a href="index.jsp">首页</a></td>
						<td><a href="RecordShow">借阅记录</a></td>
						<td><a href="FineShow">罚款记录</a></td>
						<td><a href="Logout">退出</a></td>
					</tr>
				</table></div></td>
		</tr>
	    <tr>
				<td align="center"><table width="100%" border="1" class="text">
							<tr>
								<td colspan="2" align="center"><div id="title">用户信息</div></td>
							</tr>
							<tr>
								<td width="40%" align="right">ID：</td>
								<td align="left"><%=u.getID()%></td>
							</tr>
							<tr>
								<td width="40%" align="right">姓名：</td>
								<td align="left"><%=u.getName()%></td>
							</tr>
							<tr>
								<td width="40%" align="right">性别：</td>
								<td align="left"><%=u.getSex()%></td>
							</tr>							
							<tr>
								<td width="40%" align="right">创建时间：</td>
								<td align="left"><%=u.getDate()%></td>
							</tr>
							<tr>
								<td width="40%" align="right">可借数量：</td>
								<td align="left"><%=u.getNumber()%></td>
							</tr>
						</table></div></td>
			</tr>
		 </table>
    </body>
</html>
