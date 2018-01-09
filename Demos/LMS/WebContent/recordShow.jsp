<%@page import="java.sql.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.hui.model.*"%>
<%@page contentType="text/html" pageEncoding="GBK"%>
<%
	User u = (User)request.getSession().getAttribute("userInfo");	 
	ArrayList<Record> list = (ArrayList<Record>)request.getAttribute("recordList");
	int pageCount = 1;
	String pageCountString = (String) request.getParameter("pageCount");
	if (pageCountString != null) {
		pageCount = Integer.parseInt(pageCountString);
	}
	int pageNow = 1;
	String pageNowString = request.getParameter("pageNow");
	if (pageNowString != null) {
		pageNow = Integer.parseInt(pageNowString);
	}
	
	String viewType = request.getParameter("viewType");
	if (viewType == null) {
		viewType = "all";
	} else if (!(viewType.equals("borrow") || viewType.equals("return"))) {
		viewType = "all";
	}
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title>借阅记录</title>
		<link href="css/result.css" rel="stylesheet" type="text/css">
    </head>
    <body>
		<table width="95%" border="0" align="center">
			<tr>
			<td><div id="headright"><table width="100%" border="0" class="text">
					<tr>
					<td><a href="index.jsp">首页</a></td>
					<%if(u != null) {%>
					<td><a href="userInfo.jsp">个人信息</a></td>					
					<%} else {%>
					<td><a href="adminIndex.jsp">管理首页</a></td>
					<%} %>	
					<td><a href="FineShow">罚款记录</a></td>
					<td><a href="Logout">退出</a></td>
					</tr>
					</table></div></td>			 
					
			</tr>
			<tr>
				<td align="center"><div id="infobox"><table width="100%" border="0">
							<tr>
								<td align="right"><div id="title">
								<%if(u!=null){ %>
								我的借阅
								<%}else { %>
								用户借阅
								<%} %>
								</div></td>
							</tr>
							<tr>
								<td><div id="menu">
										<table width="100%" border="0">
											<tr>
												<td width="70%" align="right">&nbsp;</td>
												<td width="10%" align="right">
												<%if (viewType.equals("all")) {%>
												全部借阅
												<%} else {%>
												<a href="RecordShow?viewType=all">全部借阅</a>
												<%}%></td>
											  <td width="10%" align="right">
											  <%if (viewType.equals("return")) {%>
											  	已归还
											  	<%} else {%>
											  	<a href="RecordShow?viewType=return">已归还</a>
											  	<%}%></td>
											  <td width="10%" align="right">
											  <%if (viewType.equals("borrow")) {%>
											   未归还
											  <%} else {%><a href="RecordShow?viewType=borrow">未归还</a>
											  <%}%></td>
											</tr>
										</table>

									</div></td>
							</tr>
							<tr>
								<td><table width="100%" border="1">
										<tr>
											<th>ISBN</th>
											<%if(u != null) {%>
											<td>管理员</td>
											<%}else{ %>
											<td>用户</td>
											<% }%>
											<th>借出时间</th>
											<th>到期时间</th>
											<th>归还时间</th>
										</tr>
										<%
											if (list != null)
												if (!list.isEmpty()) {
													for (Record r : list) {
														Date date = new Date(r.getBorrowDate().getTime() + 30 * 24 * 3600 * 1000L);
										%>
										<tr>
											<td><%=r.getISBN()%></td>
											<%if(u!=null) {%>
											<td><%=r.getAdminName() %>
											<%}else {%>
											<td><%= r.getUserName()%>
											<%} %>
											<td><%=r.getBorrowDate()%></td>											
											<td><%=date%></td>
											<%
												if (r.getReturnDate() != null) {
											%>
											<td><%=r.getReturnDate()%></td>
											<%
											} else {
											%><td>未归还</td><%													}
											%>

										</tr>
										<%											}
										} else {
										%>没有记录<%													}
										%>
										<tr>
											<td colspan="5" align="center"><%
												if (pageNow != 1) {
												%><a href="RecordShow?viewType=<%=viewType%>&pageNow=1">首页</a><%
												%>&nbsp;<%
												%><a href="RecordShow?viewType=<%=viewType%>&pageNow=<%=pageNow - 1%>">上一页</a><%
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
												%><a href="RecordShow?viewType=<%=viewType%>&pageNow=<%=i%>">[<%=i%>]</a><%
												} else {
												%>><%=i%><<%
													}
												%>&nbsp;<%
													}
													if (pageNow != pageCount && pageCount != 0) {
												%><a href="RecordShow?viewType=<%=viewType%>&pageNow=<%=pageNow + 1%>">下一页</a><%
												%>&nbsp;<%
												%><a href="RecordShow?viewType=<%=viewType%>&pageNow=<%=pageCount%>">末页</a><%
													}
												%></td>
										</tr>

									</table></td>
							</tr>
						</table></div></td>
			</tr>
			
		</table>
    </body>
</html>
