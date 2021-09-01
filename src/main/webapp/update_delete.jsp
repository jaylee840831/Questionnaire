<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>問卷項目修改刪除</title>
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
		<nav class="navbar navbar-expand-lg navbar-dark"
			style="background-color: blue">
			<div>
				<a href="<%=request.getContextPath()%>/list" id="brand" class="navbar-brand">問卷調查</a>
			</div>
		</nav>
	</header>
	<br>
	
	<div class="text-center">
			    <h4 >請選擇要刪除或修改項目的問卷</h4>
			    <select id="viewChange">
				    <option value="test1">Test</option>
				    <option value="test2">Test2</option>
				</select>
	</div>

<!-- test1 -->
	<div name="test">
		<div class="row">
			<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->
	
			<div class="container">
				<br>
				
				<table class="table table-bordered">
					<thead>
						<tr>
							<!-- <th>ID</th>-->
							<th>測驗名稱</th>
							<th>A實驗</th>
							<th>B實驗</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${listABTest.size()!=0}">
							<c:forEach var="i" begin="0" end="${listABTest.size()-1}" step="1">
								<tr>
									<td><c:out value="${listABTest.get(i).test_name}" /></td>
									<td><audio id="audio" controls src="data:audio/wav;base64,<c:out value="${listABTest.get(i).test_a2}"/>">
		Your browser does not support the audio element.
		</audio></td>
									<td><audio id="audio2" controls src="data:audio/wav;base64,<c:out value="${listABTest.get(i).test_b2}"/>">
		Your browser does not support the audio element.
		</audio></td>
									<td><a href="edit?id=<c:out value='${listABTest.get(i).id}' />&test_name=test1">修改</a>
										&nbsp;&nbsp;&nbsp;&nbsp;
										 <a href="delete?id=<c:out value='${listABTest.get(i).id}' />&test_name=test1">刪除</a>
									</td>
									
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${listABTest.size()==0}">
							<td>無資料</td>
						</c:if>
					</tbody>
	
				</table>
				
			</div>
		</div>
	</div>
	
	
<!-- test2 -->
	<div name="test" style="display:none;">
			<div class="row">
			<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->
	
			<div class="container">
				<div class="container text-left">
					<!-- <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add
						New User</a>-->
				</div>
				<br>
				<table class="table table-bordered">
					<thead>
						 <tr>
							<!-- <th>ID</th>-->
							<th>測驗名稱</th>
							<th>資料集</th>
							<th>分數</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					    <c:if test="${experimentTest.size()!=0}">
							<c:forEach var="i" begin="0" end="${experimentTest.size()-1}" step="1">
								<tr>
									<td><c:out value="${experimentTest.get(i).test_name}" /></td>
									<td><c:out value="${experimentTest.get(i).data_name}" /></td>
		    						<td><audio id="audio" controls src="data:audio/wav;base64,<c:out value="${experimentTest.get(i).audio2}"/>">
		Your browser does not support the audio element.
		</audio></td>			
									<td><a href="edit?id=<c:out value='${experimentTest.get(i).id}' />&test_name=test2">修改</a>
										&nbsp;&nbsp;&nbsp;&nbsp;
										 <a href="delete?id=<c:out value='${experimentTest.get(i).id}' />&test_name=test2">刪除</a>
									</td>
								</tr>
								
							</c:forEach>
						</c:if>
						<c:if test="${experimentTest.size()==0}">
							<td>無資料</td>
						</c:if>
					</tbody>
	
				</table>
				
			</div>
		</div>
	</div>
	
	<script language="javascript"charset="UTF-8">
		const selectElement = document.getElementById('viewChange');
		var test=document.getElementsByName('test');
		selectElement.onchange= function() {myFunction()};
		
		//得到當前畫面問卷的種類
		function myFunction() {
			
		  if(selectElement.value=="test1"){
				  test[0].style.display="block";
				  test[1].style.display="none";
		  }
		  else if(selectElement.value=="test2"){
	 			test[0].style.display="none";
	 			test[1].style.display="block";
		  }
			  
		}
	</script>
	
</body>
</html>
