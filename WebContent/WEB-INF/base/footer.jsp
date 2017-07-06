<%@ page language="java" import="java.util.*" 
contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'footer.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="shortcut icon" href="image/favicon.ico" />

</head>

<body>
<a target="_blank" href="http://cn.mikecrm.com/rAzAxLk" style="position:fixed;z-index:999;right:5px;bottom: 20px;display: inline-block;width: 20px;border-radius: 5px;color:white;font-size:14px;line-height:17px;background: #2476CE;box-shadow: 0 0 5px #666;word-wrap: break-word;padding: 10px 6px 10px 2px;border: 2px solid white;">意见反馈</a>
	<!-- footer -->
	
	<div class="footer" style="background: rgba(0, 28, 50, 0)">
		<p class="text-muted text-center">Copyright © 2017 &nbsp; 牛晓军</p>
	</div>
	
</body>
</html>
