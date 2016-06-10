<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 
<%
int id = Integer.parseInt(request.getParameter("id"));
int rootid = Integer.parseInt(request.getParameter("rootid"));
response.setContentType("text/html; charset=gb2312");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>回复</title>

<script language="javascript">
<!--	
	//javascript去空格函数 
	function LTrim(str){ //去掉字符串 的头空格
		var i;
		for(i=0;i<str.length; i++) {
			if(str.charAt(i)!=" ") break;
		}
		str = str.substring(i,str.length);
		return str;
	}
	
	function RTrim(str){
		var i;
		for(i=str.length-1;i>=0;i--){
			if(str.charAt(i)!=" "&&str.charAt(i)!=" ") break;
		}
		str = str.substring(0,i+1);
		return str;
	}
	function Trim(str){
	
		return LTrim(RTrim(str));
	
	}
	
	function check() {
		if(Trim(document.form.title.value) == "") {
			alert("please intput the title!");
			document.form.title.focus();
			return false;
		}
		
		if(Trim(document.form.cont.value) == "") {
			alert("plsease input the content!");
			document.form.cont.focus();
			return false;
		}
		
		return true;
		
	}
-->
</script>
</head>
<body>
<form name="form" action="ReplyOK.jsp" method="post" onsubmit="return check()">
	<input type="hidden" name="id" value="<%= id %>">
	<input type="hidden" name="rootid" value="<%= rootid %>">	
	<table>	
	<tr>
		<td><input type="text" name="title"  size="50"></td>
	</tr>
	<tr>
		<td><textarea name="cont" cols="80" rows="12"></textarea></td>
	</tr>
	<tr>
		<td><input type="submit" value="提交"></td>
	</tr>
	</table>
</form>
</body>
</html>