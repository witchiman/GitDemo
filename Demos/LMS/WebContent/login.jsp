<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
String msg = "请于此处输入用户名和密码！" ;
String loginInfo = null;
loginInfo = request.getParameter("loginInfo");
if(loginInfo!=null&&loginInfo.equals("failure")){
	msg="用户名或密码错误，请重新输入！";
}else if(loginInfo!=null&&loginInfo.equals("null")){
	msg = "用户名和密码，不能为空！";
}
%>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=GBK"> 
<!-- filename: meta-tags V1.1r014 --> 
 
<meta http-equiv="Cache-Control" content="no-cache"> 
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Expires" content="Sun, 06 Nov 1994 08:49:37 GMT"> 
<meta http-equiv="REFRESH" content="1200; URL=http://202.204.214.94:8991/F/5PCBJAPUVEPMPLDYEPQEG4KU47IP3HPY867BCJJ5XB3VVN5MDG-11386?func=logout"> 
 
 
<link rel="stylesheet" href="./Login_files/exlchi.css" type="text/css"> 
<style> 
.tabshead{background:url(http://202.204.214.94:8991/exlibris/aleph/u20_1/alephe/www_f_chi/icon/tab_bg.gif) bottom repeat-x;} 
.topbar{background-image:url(http://202.204.214.94:8991/exlibris/aleph/u20_1/alephe/www_f_chi/icon/tab_bg.gif);} 
.middlebar{background-image:url(http://202.204.214.94:8991/exlibris/aleph/u20_1/alephe/www_f_chi/icon/f-middle-bar.jpg);} 
.bottombar{background-image:url(http://202.204.214.94:8991/exlibris/aleph/u20_1/alephe/www_f_chi/icon/f-bottom-bar.jpg);} 
#feedbackbar,.feedbackbar{background-image:url(http://202.204.214.94:8991/exlibris/aleph/u20_1/alephe/www_f_chi/icon/f-feedback.jpg);} 
</style> 

<div id="header"></div> 
<div id="idxlogo"><a href="http://202.204.214.94:8991/F/5PCBJAPUVEPMPLDYEPQEG4KU47IP3HPY867BCJJ5XB3VVN5MDG-11399?func=find-b-0"><img src="./Login_files/opac.jpg"></a></div> 
<div id="feedbackbar" style="display:none;text-align:center">&nbsp;</div>  
<div id="feedbackbar" style="display:none;text-align:center">&nbsp;</div> 
 
<div class="title">请确认身份： 
</div> 
<br> 
 
<form method="post" name="form1" action="IdentityVerify"> 
 


<center> 
<table border="0" cellspacing="2" cellpadding="6"> 
 <tbody><tr> 
  <td class="td2" nowrap="">用户:</td> 
  <td class="td2"> 
    <input style="width:250px" name="username" value=""> 
  </td> 
 </tr> 
 <tr> 
  <td class="td2">密    码 :</td> 
  <td class="td2"> 
    <input type="password" style="width:250px" name="password" value=""> 
  </td> 
 </tr> 

 <tr> 
  <td colspan="2" align="center"> 
      <input type="submit" value="确认">&nbsp; 
 
 
  </td> 
 </tr> 
<tr> 
  <td> 
  </td> 
 </tr> 
 <tr> 
</tr><tr> 
  <td colspan="2" align="center"> 
<div align="left"><%=msg %>>
<br> 
</div> 
</td> 
 </tr> 
 <tr> 
</tr></tbody></table> 
</center> 
</form> 

<p id="copyright" class="copyright"><br> 
图书馆管理系统 2015
</p> 
<script>if(self.location!=parent.location&&g('cclterm')){g('cclterm').value='';if(g('find_code'))g('find_code').options[0].selected=true;} 
</script> 
 
 
   

 </body></html>