<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>問卷調查</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
	
<style>
#brand {
   color: yellow;
 }
input[type=radio]{
width: 40px; 
height: 40px"
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
			 <div class="navbar-nav">
			     <a href="<%=request.getContextPath()%>/new" class="nav-item nav-link active">新增問卷項目</a>
			    <a href="<%=request.getContextPath()%>/update_delete" class="nav-item nav-link active">修改與刪除問卷項目</a>
			     <a href="javascript: pop()" class="nav-item nav-link active">新增問卷</a>
			     <a href="javascript: pop()" class="nav-item nav-link active">刪除問卷</a>
    		</div>
		</nav>
	</header>
	<br>

    <div class="text-center">
    <h4 >請選擇問卷</h4>
    <select id="viewChange">
	    <option value="test1">Test</option>
	    <option value="test2">Test2</option>
	</select>
	</div>

<script language="javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" charset="UTF-8">

</script>
	
<!-- test1 -->	
<div name="test">
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
						<th>A實驗</th>
						<th>B實驗</th>
						<th>投票</th>
					</tr>
				</thead>
				<tbody>
				 <c:if test="${listABTest.size()!=0}">
					<!--   for (Todo todo: todos) {  -->
					<c:forEach var="i" begin="0" end="${listABTest.size()-1}" step="1">
						<tr>
							<td><c:out value="${listABTest.get(i).test_name}" /></td>
							<td><audio id="audio" controls src="data:audio/wav;base64,<c:out value="${listABTest.get(i).test_a2}"/>">
Your browser does not support the audio element.
</audio></td>
							<td><audio id="audio2" controls src="data:audio/wav;base64,<c:out value="${listABTest.get(i).test_b2}"/>">
Your browser does not support the audio element.
</audio></td>
							<td><input  type="radio" name="radio<c:out value="${i}"/>" value="a">A實驗
    							<input  type="radio" name="radio<c:out value="${i}"/>" value="b">B實驗
    						</td>
							
							<!-- <td><a href="edit?id=<c:out value='${user.id}' />">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								 <a href="delete?id=<c:out value='${user.id}' />">Delete</a></td>-->
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
							<td><input  type="radio" name="radio<c:out value="${i}"/>" value="1">1
    							<input  type="radio" name="radio<c:out value="${i}"/>" value="2">2
    							<input  type="radio" name="radio<c:out value="${i}"/>" value="3">3
    							<input  type="radio" name="radio<c:out value="${i}"/>" value="4">4
    							<input  type="radio" name="radio<c:out value="${i}"/>" value="5">5
    						</td>
    						<td><audio id="audio" controls src="data:audio/wav;base64,<c:out value="${experimentTest.get(i).audio2}"/>">
Your browser does not support the audio element.
</audio></td>			
							<!-- <td><a href="edit?id=<c:out value='${user.id}' />">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								 <a href="delete?id=<c:out value='${user.id}' />">Delete</a></td>-->
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

<!-- 問卷選擇結果傳到servlet -->
	<div class="row justify-content-center">
		<form method="post" action="success" enctype="multipart/form-data">
			<input hidden type="text"name="vote_result" id="vote_result"></intput>
			<button type="submit" class="btn btn-success" onclick="passValue()">提交</button>
		</form>
	</div>
	
	<script language="javascript"charset="UTF-8">
	
		const selectElement = document.getElementById('viewChange');
		var test=document.getElementsByName('test');
		selectElement.onchange= function() {myFunction()};
	
		//問卷提交
		function passValue() {
			
			var Str="";
			var Total_Obj = document.getElementsByTagName("input");
			
			if(selectElement.value=="test1"){
		        for (var i = 0; i < Total_Obj.length; i++) {
		            if (Total_Obj[i].value == "a" || Total_Obj[i].value == "b") {
		                if (Total_Obj[i].checked) {
		                    if (Str == "") {
		                        Str = Total_Obj[i].value;
		                    } else {
		                        Str += Total_Obj[i].value;
		                    }
		                }
		            }
		        }
		        
		        <c:if test="${listABTest!=null}">
		        if(Str.length!=<c:out value="${listABTest.size()}" />){
		        		Str="";
		        }
		        Str=Str+",test1";
		        document.getElementById("vote_result").value=Str;
		        </c:if>
		  }
		  else if(selectElement.value=="test2"){
			  for (var i = 0; i < Total_Obj.length; i++) {
		            if (Total_Obj[i].value == "1" || Total_Obj[i].value == "2" || Total_Obj[i].value == "3" || Total_Obj[i].value == "4" || Total_Obj[i].value == "5") {
		                if (Total_Obj[i].checked) {
		                    if (Str == "") {
		                        Str = Total_Obj[i].value;
		                    } else {
		                        Str += Total_Obj[i].value;
		                    }
		                }
		            }
		        }
		        
		        <c:if test="${experimentTest!=null}">
		        if(Str.length!=<c:out value="${experimentTest.size()}" />){
		        		Str="";
		        }
		        Str=Str+",test2";
		        document.getElementById("vote_result").value=Str;
		        </c:if> 
		  }
	        
		}
		
		function pop(){
			alert("功能待開發中......");
		}
		
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
