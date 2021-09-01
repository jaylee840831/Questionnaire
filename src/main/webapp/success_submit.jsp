<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>提交問卷</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">

<style>
#brand {
   color: yellow;
 }
</style>
</head>
<body>
		<header>
				<nav class="navbar navbar-expand-md navbar-dark"
					style="background-color: blue">
					<!--  <div>
						<a href="https://www.javaguides.net" class="navbar-brand"> User Management App </a>
					</div>-->
		
					<ul class="navbar-nav">
						<li><a href="<%=request.getContextPath()%>/list" id="brand"
							class="navbar-brand">問卷調查</a></li>
					</ul>
				</nav>
			</header>
			
    <c:if test="${success==true}">
		<div class="text-center">
			<h3>提交問卷成功</h3>
			<a href="<%=request.getContextPath()%>/list">返回主畫面</a>
		</div>
		<div class="text-center">
	  		<img alt="" src="/upload/success.jpg" />
		</div>
     </c:if>
     
      <c:if test="${success==false}">
		<div class="text-center">
			<h3>問卷提交失敗!! 請確保所有題目都有作答</h3>
			<a href="<%=request.getContextPath()%>/list">返回主畫面</a>
		</div>
     </c:if>
     
</body>
</html>