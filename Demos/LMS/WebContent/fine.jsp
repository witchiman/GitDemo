<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
String fine= request.getParameter("fine");
%>
<script language="JavaScript">
function ok(){
   window.parent.parent.location.href='adminIndex.jsp';
}
window.setTimeout("ok();",1500);
function countDown(secs){
      jump.innerText=secs;
      if(--secs>0)
         setTimeout( "countDown(" +secs+ ")" ,1000);
   }
   countDown(3);
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>交纳罚款</title>
</head>
<body>
<div style="color:#FF0000" >
<h1>您借的书已经过期，请交纳<%=fine %>元罚款！</h1>
</div>
</body>
</html>