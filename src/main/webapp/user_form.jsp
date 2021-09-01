<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>問卷項目新增</title>
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

<!--input: required="required" 代表此輸入是必填
    form: action="" 代表表單會發送到servlet-->

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: blue">
			<!--  <div>
				<a href="https://www.javaguides.net" class="navbar-brand"> ab_test Management App </a>
			</div>-->

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list" id="brand"
					class="navbar-brand">問卷調查</a></li>
			</ul>
		</nav>
	</header>
	
	<br>
	
	
	<div class="text-center">
			<h3>
            	請選擇要新增項目的問卷
			</h3>
			<select id="viewChange">
	    		<option value="test1">Test</option>
	    		<option value="test2">Test2</option>
			</select>
	</div>
	
	<br>
	
	<!-- test -->
	<div name="test">
		<div class="container col-md-5">
			<div class="card">
				<div class="card-body">
					<form action="insert" method="post" enctype="multipart/form-data">		
					<input hidden name="question" value="test1" />
					<fieldset class="form-group">
						<label>測驗名稱</label>
						<input type="text"
								class="form-control"
								name="test_name" required="required">
					</fieldset>			
					<fieldset class="form-group">
						<label>上傳實驗A音檔</label>
						<input accept="audio/wav,audio/mp3" type="file" name="test_a">
					    </fieldset>
					    <fieldset class="form-group">
						<label>上傳實驗B音檔</label>
						<input accept="audio/wav,audio/mp3" type="file" name="test_b">
					 </fieldset>
					<button type="submit" class="btn btn-success">提交</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<!-- test2 -->
	<div name="test" style="display:none;">
		<div class="container col-md-5">
			<div class="card">
				<div class="card-body">
					<form action="insert" method="post" enctype="multipart/form-data">
					<input hidden name="question" value="test2" />
					<fieldset class="form-group">
						<label>測驗名稱</label>
						<input type="text"
							    class="form-control"
								name="test_name" required="required">
						<label>資料集</label>
						<input type="text"
								class="form-control"
								name="data_name" required="required">
					</fieldset>        
			        <fieldset class="form-group">
						<label>上傳實驗音檔</label>
						<input accept="audio/wav,audio/mp3" type="file" name="test_a">
					</fieldset>
					<button type="submit" class="btn btn-success">提交</button>
					</form>
				</div>
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
