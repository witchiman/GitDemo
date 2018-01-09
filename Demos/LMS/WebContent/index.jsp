<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@page import="com.hui.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String identity  = null;
	User u = (User)request.getSession().getAttribute("userInfo");
	Admin a = (Admin)request.getSession().getAttribute("adminInfo");
	
	if (u != null) {
		identity = "user";
	} else if(a !=null){
		identity = "admin";
	}

%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-hans" lang="zh-hans" dir="ltr"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<meta http-equiv="X-UA-Compatible" content="IE=8">
<meta http-equiv="Page-Exit" ;="" content="blendTrans(Duration=2.0)">
    <title>图书馆管理系统</title>
    
<link rel="alternate" type="application/rss+xml" title="首都师范大学图书馆 RSS" href="http://www.lib.cnu.edu.cn/rss.xml">
<link rel="shortcut icon" href="http://www.lib.cnu.edu.cn/sites/default/files/hiroshige_favicon.ico" type="image/x-icon">
    <link type="text/css" rel="stylesheet" media="all" href="./index_files/book.css">
<link type="text/css" rel="stylesheet" media="all" href="./index_files/node.css">
<link type="text/css" rel="stylesheet" media="all" href="./index_files/defaults.css">
<link type="text/css" rel="stylesheet" media="all" href="./index_files/system.css">
<link type="text/css" rel="stylesheet" media="all" href="./index_files/system-menus.css">
<link type="text/css" rel="stylesheet" media="all" href="./index_files/user.css">
<link type="text/css" rel="stylesheet" media="all" href="./index_files/reset.css">
<link type="text/css" rel="stylesheet" media="all" href="./index_files/text.css">
<link type="text/css" rel="stylesheet" media="screen" href="./index_files/layout.css">
<link type="text/css" rel="stylesheet" media="screen" href="./index_files/style.css">
<link type="text/css" rel="stylesheet" media="screen" href="./index_files/views.css">
<link type="text/css" rel="stylesheet" media="screen" href="./index_files/core.css">
<link type="text/css" rel="stylesheet" media="print" href="./index_files/print.css">
        <!--[if IE]>
        <link type="text/css" rel="stylesheet" media="screen" href="/themes/hiroshige/fix-ie.css" />
    <![endif]-->
<script async="" src="./index_files/analytics.js"></script><script type="text/javascript" language="javascript" src="./index_files/jquery-1.7.1.min.js"></script>
<script type="text/javascript" language="javascript" src="./index_files/right-tabs.js"></script>
<link rel="stylesheet" type="text/css" href="./index_files/backToTop.css">
<link rel="stylesheet" type="text/css" href="./index_files/top.css">
<link rel="stylesheet" type="text/css" href="./index_files/mycontent.css">
<link rel="stylesheet" type="text/css" href="./index_files/left.css">
<link rel="stylesheet" type="text/css" href="./index_files/bottom.css">

<style type="text/css">
#informationbar{
position: fixed;
left:0;
width: 100%;
text-indent: 5px;
text-align:center;
padding: 5px 0;
background-color: lightyellow;
border-bottom: 1px solid black;
font: bold 12px Verdana;
}
* html #informationbar{ /*IE6 hack*/
position: absolute;
width: expression(document.compatMode=="CSS1Compat"? document.documentElement.clientWidth+"px" : body.clientWidth+"px");
}
</style>
<link rel="stylesheet" type="text/css" id="TQCSS0.43157754954881966" href="./index_files/style(1).css"><script id="TQJS0.2822092727292329" src="./index_files/float.js"></script></head>

<body class="front not-logged-in page-node two-sidebars">
    <div id="header">
              <div id="header">
          <div id="block-block-10" class="block block-block block-header">
    <div id="header-top">
<div id="header-logo">
<div id="logo-top"><a href="index.jsp"><img src="./index_files/logo.png"></a></div>
<div id="top-menu">
<ul id="top-menu-ul">

<%if(identity == null) {%>
<li class="borderlist2"><a href="login.jsp" >登录</a></li>
<%}else if(identity.equals("admin")) { %>
<li class="borderlist2"> <a>您好，<%=a.getName()%></a></li>
<li class="borderlist2"><a href="adminIndex.jsp" >管理首页</a></li>
<li class="borderlist2"><a href="RecordShow" >借阅记录</a></li>
<li class="borderlist2"><a href="FineShow" >罚款记录</a></li>
<li class="borderlist2"><a href="Logout" >退出</a></li>
<%}else if(identity.equals("user")){ %>
<li class="borderlist2"> <a>您好，<%=u.getName()%></a></li>
<li class="borderlist2"><a href="userInfo.jsp" </a>个人信息</li>
<li class="borderlist2"><a href="RecordShow" >借阅记录</a></li>
<li class="borderlist2"><a href="FineShow" >罚款记录</a></li>
<li class="borderlist2"><a href="Logout" >退出</a></li>
<%} %>
</tr>
</ul>
</div> 
</div>
</div>
</div>
        </div>
      	
    </div> <!-- /header -->
	<div id="banner">
              <div id="header-banner">
          <div id="block-block-11" class="block block-block block-banner">
    <style>
UL {PADDING: 0px; MARGIN: 0px;}
LI {PADDING: 0px; MARGIN: 0px;}
.container {
	WIDTH: 960px;
	HEIGHT: 124px
}
.container A IMG {
	WIDTH: 960px;
	HEIGHT: 124px
}
.container IMG {
	BORDER-BOTTOM-STYLE: none;
	BORDER-RIGHT-STYLE: none;
	BORDER-TOP-STYLE: none;
	BORDER-LEFT-STYLE: none
}
.td_f A IMG {
	PADDING-BOTTOM: 0px;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	PADDING-TOP: 0px
}
.num {
	POSITION: absolute;
	WIDTH: 90px;
	FLOAT: left;
	TOP: 108px;
    LEFT: 10px;
}
.num LI {
	TEXT-ALIGN: center;
	LINE-HEIGHT: 15px;
	LIST-STYLE-TYPE: none;
	MARGIN: 1px;
	WIDTH: 15px;
	FONT-FAMILY: Arial;
	BACKGROUND:url(themes/hiroshige/images/flashbutton.gif) no-repeat -15px 0px;
	FLOAT: left;
	HEIGHT: 15px;
	COLOR: #86a2b8;
	FONT-SIZE: 12px;
	CURSOR: pointer
}
.num LI.on {
	LINE-HEIGHT: 15px;
	WIDTH: 15px;
	BACKGROUND:url(themes/hiroshige/images/flashbutton.gif);
	HEIGHT: 15px;
	COLOR: #ffffff
}
</style>


<div id="idContainer2" class="container" style="position: relative; overflow: hidden;">
<table id="idSlider2" border="0" cellspacing="0" cellpadding="0" style="position: absolute; right: -2880px;">
<tbody>
<tr>


<td class="td_f"><a><img src="./index_files/banner01.png"></a></td>

<td class="td_f"><a ><img src="./index_files/cloudprint.jpg"></a></td>

<td class="td_f"><a><img src="./index_files/zizhujiehuan.jpg"></a></td>

<td class="td_f"><a  target="_blank"><img src="./index_files/m-service.jpg"></a></td>

<td class="td_f"><a target="_blank"><img src="./index_files/dzfwy.jpg"></a></td>

</tr></tbody></table>

 
        </div>
      	
    </div> <!-- /banner -->
	<div id="page-content-band" class="clear-block">
        <div id="content-wrapper" class="container_16">
		
		                <div id="sidebar-left" class="grid_4">
                <div id="block-block-8" class="block block-block block-left">
    <p>&nbsp;</p>
<p>&nbsp;</p>
<div class="list2">
<ul class="listid2">
<li><a href="http://www.lib.cnu.edu.cn/consult/virtual" target="_blank">联合咨询</a></li>
<li><a href="http://www.lib.cnu.edu.cn/question/list" target="_blank">常见问题</a></li>
<li><a href="http://202.204.214.131:8080/" target="_blank">读者留言</a></li>
<li>&nbsp;</li>
</ul>
</div>
</div>
<div id="block-block-9" class="block block-block block-left">
    <p><a class="button2"><span>帮 助</span></a></p>
<div class="list2">
<ul class="listid2">
<li><a href="http://www.lib.cnu.edu.cn/service/guide_freshmen">新生手册</a></li>
<li><a href="http://www.lib.cnu.edu.cn/service/facilities">服务设施</a></li>
<li><a href="http://www.lib.cnu.edu.cn/service/librarians">学科馆员</a></li>
<li><a href="http://www.lib.cnu.edu.cn/service/classification">中图分类</a></li>
<li></li>
</ul>
</div>
</div>
            </div><!-- /#sidebar-left -->
                        
			<div id="page-content" class="grid_8 alpha">
                                                                                                <div id="content-content" class="clear-block">
                  
                </div> <!-- /#content -->
				 
				  				 <div id="my-content">
                     <div id="block-block-3" class="block block-block block-my_content">
    ﻿
<link href="./index_files/search2.css" rel="stylesheet" type="text/css">

<script language="javascript">

function changeTab(oA)
{
    var oAName = oA.getAttribute("name");
    var oAs = document.getElementsByName(oAName);
    for(var i=0; i<oAs.length; i++)
		   { if(oAs[i].className=="selected" && oAs[i] != oA)
		       { oAs[i].className="";
                 var oDiv = document.getElementById(oAs[i].getAttribute("rel"));
                 oDiv.style.display="none";}
			}
    oA.className="selected";
    document.getElementById(oA.getAttribute("rel")).style.display="";
    return false;
}

 function Search1()
    {
        var txtsjk= document.getElementById("textfield1").value;
        var formID= document.getElementById("form1");
        var select1 =document.getElementById("select1");
        switch(select1.value)
        {
            case "1":
                {  // var matchKEY="";
	actionURL="http://202.204.214.94:8991/F/-?find_base=CNU01&find_base=CNU09&func=find-m&find_code=WRD&request="+encodeURIComponent(txtsjk)+"&local_base=&filter_code_1=WLN&filter_request_1=&filter_code_2=WYR&filter_request_2=&filter_code_3=WYR&filter_request_3=&filter_code_4=WFT&filter_request_4=&filter_code_5=WSL&filter_request_5=";                
                    break;
                }
            case "2":
                {
                     var actionURL="http://202.204.214.93:8331/V?FUNC=FIND-DB-1-LCL&FUNC=QUICK-1-CHECK1-LCL&MODE=simple&GROUP_NUMBER=000000737&FIND_REQUEST_1="+encodeURIComponent(txtsjk);
                    break;
                }
            
        }
        window.open(actionURL,"_blank");
    }

/*******************以下代码inputclear用于自动删除input输入框中value*************************************/

          var constant = 0;
         function inputclear()
         {
	            if(constant ==0)
		        {
		           document.getElementById("searchText").value = "";
		           document.getElementById("searchText").className="forminput_search02";
		        }
		       constant = constant+1;
         }
		
</script>

<div id="search">
<ul class="nav"><li></li>
<li></li>
</ul>
 





<div class="cont" id="navi4">
<ul class="shuoming">
<li>提示：输入<font color="#f86005">图书名或ISBN</font>查找本馆全部中外图书</li>
</ul>
  <form class="search_form" name="form4" id="form4" action="BookSearch" method="post" >    
   <input name="searchText" class="forminput_search02" id="searchText" type="text" onmouseover="inputclear();" autocomplete="on" value="请输入图书名称"> 
    <input class="search_button" name="submit4"  type="submit" value="搜 索"> 
  </form>
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <p>&nbsp;</p>
</div>


</div>




</div>
                 </div> 
				 				
				 				 <div id="my-content-left" class="">
                     <div id="block-block-5" class="block block-block block-my_content_left">
    <p><a class="button" href="http://www.lib.cnu.edu.cn/resource/digital_source"><span>资源概览</span></a></p>
<div class="list1">
<ul class="listid1">
<li><a href="http://www.lib.cnu.edu.cn/resource/digital_source">数字资源</a></li>
<li><a href="http://202.204.214.93:8331/V?func=find-db-1-lcl" target="_blank">学术资源门户</a></li>
<li><a href="http://202.204.214.169/tpi_1/sysasp/include/index.asp" target="_blank">特色资源</a></li>
<li><a href="http://202.204.214.94:8991/F/?func=file&amp;file_name=hotinfo" target="_blank">新书通报</a></li>
<li><a href="http://www.lib.cnu.edu.cn/resource/recommand_source">资源推荐</a></li>
</ul>
</div>
</div>
                 </div> 
				 				 
				 				 <div id="my-content-middle" class="">
                     <div id="block-block-6" class="block block-block block-my_content_middle">
    <p><a class="button" href="http://www.lib.cnu.edu.cn/service/guide_teacher"><span>读者服务</span></a></p>
<div class="list1">
<ul class="listid1">
<li><a href="http://www.lib.cnu.edu.cn/service/guide_teacher">读者指南</a></li>
<li><a href="http://www.lib.cnu.edu.cn/service/interloan">馆际互借</a></li>
<li><a href="http://www.lib.cnu.edu.cn/service/wenxianchuandi">文献传递</a></li>
<li><a href="http://www.lib.cnu.edu.cn/service/check">查收查引</a></li>
<li><a href="http://www.lib.cnu.edu.cn/service/teaching-training">教学培训</a></li>
</ul>
</div>
</div>
                 </div> 
				 				 
				 				 <div id="my-content-right" class="">
                     <div id="block-block-7" class="block block-block block-my_content_right">
    <p><a class="button" href="http://www.lib.cnu.edu.cn/guidence/aboutus"><span>本馆概况</span></a></p>
<div class="list1">
<ul class="listid1">
<li><a href="http://www.lib.cnu.edu.cn/guidence/aboutus">本馆介绍</a></li>
<li><a href="http://www.lib.cnu.edu.cn/guidence/rules">规章制度</a></li>
<li><a href="http://www.lib.cnu.edu.cn/guidence/collection">馆藏布局</a></li>
<li><a href="http://www.lib.cnu.edu.cn/guidence/specialbuiding">学科分馆</a></li>
<li><a href="http://www.lib.cnu.edu.cn/guidence/library_reader">图书馆与读者</a></li>
</ul>
</div>
</div>
                 </div> 
				 			
				 
            </div> <!-- /#page-content -->
            
                        <div id="sidebar-right" class="grid_4 omega">
              <div id="block-block-17" class="block block-block block-right">
    ﻿<script language="javascript">
Menu_dis[Menu_dis.length]=0;//默认显示第一
Menu_Scroll[Menu_Scroll.length]=0//默认循环
</script>

<style>
#box0{overflow:hidden; width:255px; margin-left:4px; margin-top:-14px;}
#tabbox0{margin-top:4px; float:left; margin-left:6px; border:none; height:30px; line-height:30px; text-align:center;}
#tabbox0 ul{
list-style:none;
display:inline;
width:162px;
}
.title{overflow:hidden}
.title li{background:url(themes/hiroshige/img/rightbar.png) no-repeat; float:left; width:81px; border:none;margin-left:-1px; text-align:center;}
.title li.hover{background:url(themes/hiroshige/img/rightbar2.png) no-repeat; }
.title li.hover a{display:block;}
a.tabmore{
font-family:"宋体";
font-size:12px;
color:#297700;
text-decoration:none;
font-weight:bold;
display:block;}
a.tabmore:hover{
text-decoration:none;
color:#FF0000;}
#tabcon0{
margin-top:30px;
*margin-top:-4px;
_margin-top:-4px;
width:255px; height:176px;
background:url(themes/hiroshige/img/rightbar-content.png) no-repeat;
}
#tabcon0 ul{
float:left;
}
#tabcon0 ul li{
margin-top:-6px;
margin-left:-14px;
list-style:none;
}
a#pic{
float:left;
margin-left:30px;
margin-right:10px;
margin-top:6px;}
a.amore{
clear:both;
text-decoration:none;
color:#297700;
font-size:12px;
margin-left:194px;
font-family:"宋体";
}
a.amore:hover{
text-decoration:none;
font-weight:bold;}
</style>
              </div>
<div id="block-block-18" class="block block-block block-right">
    ﻿<script language="javascript">
Menu_dis[Menu_dis.length]=0;//默认显示第一
Menu_Scroll[Menu_Scroll.length]=0//默认循环
</script>

<style>
#box1{overflow:hidden; margin-top:-14px; width:255px; margin-left:4px;}
#tabbox1{float:left; margin-left:6px; border:none; height:30px; line-height:30px; text-align:center;}
#tabbox1 ul{
list-style:none;
display:inline;
width:180px;
}
.title{overflow:hidden}
.title li{background:url(themes/hiroshige/img/rightbar.png) no-repeat; float:left; width:81px; border:none; margin-left:-1px; text-align:center;}
.title li.hover{background:url(themes/hiroshige/img/rightbar2.png) no-repeat; }
.title li.hover a{display:block;}
a.tabmore{
font-family:"宋体";
font-size:12px;
color:#297700;
text-decoration:none;
font-weight:bold;
display:block;}
a.tabmore:hover{
text-decoration:none;
color:#FF0000;}
#tabcon1 {
margin-top:26px;
*margin-top:-4px;
_margin-top:-4px;
width:255px; 
height:176px;
background:url(themes/hiroshige/img/rightbar-content.png) no-repeat;
}
#tabcon1 ul{
float:left;
}
#tabcon1 ul li{
margin-top:-6px;
margin-left:-14px;
list-style:none;
}
a#pic{
float:left;
margin-left:30px;
margin-right:10px;
margin-top:6px;}
a.amore{
clear:both;
text-decoration:none;
color:#297700;
font-size:12px;
margin-left:194px;
font-family:"宋体";
}
a.amore:hover{
text-decoration:none;
font-weight:bold;}
</style>
</div>
            </div><!-- /#sidebar-right -->
      </div> <!-- #content-wrapper" -->
    </div> <!-- /#page-content-band -->
	
    <div id="bottom-bar-band" class="clear-block">
        <div id="bottom" class="container_16 clear-block">
                                    <div id="bottom-left" class="grid_16">
              <div id="block-block-1" class="block block-block block-bottom_left">
    <div class="rel_link" id="bottom_link">
 
</div>
</div>
            </div> <!-- /#bottom-left -->
                                                        </div> <!-- /#bottom -->
    </div> <!-- /#bottom-bar-band -->
   
    <div id="footer-band" class="clear-block">
      <div id="footer" class="container_16">
        <div id="block-block-4" class="block block-block block-footer">
    <table width="960">
<tbody><tr>
<td width="25%"></td>

<td width="50%" id="footid1">
<ul id="footerul">
</ul></td>

<td width="25%" id="footid2">
<ul id="footerul">
<li><a style="color:#fff" href="http://www.lib.cnu.edu.cn/statistic" target="_blank">访问统计</a></li>
</ul></td>
</tr></tbody></table></div>
      </div>
    </div> <!-- /#footer-band -->
    
<!--baidu Analysis-->
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F4350ea4595cedeac1fc66e710a7d5fac' type='text/javascript'%3E%3C/script%3E"));
</script><script src="./index_files/h.js" type="text/javascript"></script>
<!--end-->

<!-- Piwik --> 
<script type="text/javascript">
var pkBaseURL = (("https:" == document.location.protocol) ? "https://202.204.214.131/piwik/" : "http://202.204.214.131/piwik/");
document.write(unescape("%3Cscript src='" + pkBaseURL + "piwik.js' type='text/javascript'%3E%3C/script%3E"));
</script><script src="./index_files/piwik.js" type="text/javascript"></script>
<script type="text/javascript">
try {
var piwikTracker = Piwik.getTracker(pkBaseURL + "piwik.php", 1);
piwikTracker.trackPageView();
piwikTracker.enableLinkTracking();
} catch( err ) {}
</script>
<noscript></noscript>
</body></html>