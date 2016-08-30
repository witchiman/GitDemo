<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html> 
<title>测试页面 </title>
</head>
<body>

	<p> <a href="http://localhost:8080/software/">LOOK HERE</a></p> 
 
	<h1>无参数请求跳转到此页面</h1>
	<br>
	<br>
	<h1>通过EL表达式取值：</h1>
	<h2>model:${requestScope.model}</h2>
	<h2>request:${requestScope.request }</h2>
	<h2>session:${sessionScope.session }</h2>
	<h2>modelmap:${modelmap }</h2>
	<h2>SessionAttributes:${sessionScope.arg1 }</h2>
	<h2>ModelAndView:${requestScope.user.name }</h2>
	<br>
	<br>

	<form action="http://localhost:8080/software/test"> 
		<select name="method">
			<option value="method1">方法1</option>
			<option value="method2"/>方法2</option>
			<option value="method3"/>方法3</option>
			<option value="method4"/>方法4,重定向、前进</option>
			<option value="method5"/>方法5,ModelAndView</option>
		</select>
		<input type="submit"/>
	</form>
	<br>
	<br>
	<form action="http://localhost:8080/software/upload" method="post"
		enctype="multipart/form-data">
		随便写点儿啥吧：<input type="text" name="whatever">
						<input type="file" name="file" value="上传">
						<input type="submit" value="确定">
	</form>

</body>
</html>