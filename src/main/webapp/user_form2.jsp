<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>問卷項目修改</title>
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
            	修改項目
			</h3>
	</div>
	
	<br>
	
	<!-- test -->
	<c:if test="${test_name=='test1'}">
	<div name="test">
	</c:if>
	<c:if test="${test_name=='test2'}">
	<div name="test" style="display:none;">
	</c:if>
		<div class="container col-md-5">
			<div class="card">
				<div class="card-body">
				<c:if test="${ab_test != null}">
				<form action="update" method="post" enctype="multipart/form-data">
				<input type="hidden" name="id" value="<c:out value='${ab_test.id}' />" />
				<input hidden name="question" value="test1" />
	
				<fieldset class="form-group">
					<label>測驗名稱</label>
					<input type="text"
								value="<c:out value='${ab_test.test_name}' />" class="form-control"
								name="test_name" required="required">
				</fieldset>
					
				<fieldset class="form-group">
						<label>實驗A音檔</label><audio controls name="test_a2" src="data:audio/wav;base64,<c:out value="${ab_test.test_a2}"/>">
	Your browser does not support the audio element.
	</audio>
			    </fieldset>
			    <fieldset class="form-group">
						<label>實驗B音檔</label><audio controls name="test_b2" src="data:audio/wav;base64,<c:out value="${ab_test.test_b2}"/>">
	Your browser does not support the audio element.
	</audio>
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
				</c:if>
				</div>
			</div>
		</div>
	</div>
	
	<!-- test2 -->
	<c:if test="${test_name=='test2'}">
	<div name="test">
	</c:if>
	<c:if test="${test_name=='test1'}">
	<div name="test" style="display:none;">
	</c:if>
		<div class="container col-md-5">
			<div class="card">
				<div class="card-body">
				<c:if test="${experiment_test != null}">
					<form action="update" method="post" enctype="multipart/form-data">
					<input type="hidden" name="id" value="<c:out value='${experiment_test.id}' />" />
					<input hidden name="question" value="test2" />
					<fieldset class="form-group">
						<label>測驗名稱</label>
							<input type="text"
								value="<c:out value='${experiment_test.test_name}' />" class="form-control"
								name="test_name" required="required">
						<label>資料集</label>
						<input type="text"
								value="<c:out value='${experiment_test.data_name}' />" class="form-control"
								name="data_name" required="required">
					</fieldset>
					<fieldset class="form-group">
							<label>實驗音檔</label>
							<audio controls name="test_a2" src="data:audio/wav;base64,<c:out value="${experiment_test.audio2}"/>">
	Your browser does not support the audio element.
							</audio>
					</fieldset>
			        <fieldset class="form-group">
						<label>上傳實驗音檔</label>
						<input accept="audio/wav,audio/mp3" type="file" name="test_a">
					</fieldset>

					<button type="submit" class="btn btn-success">提交</button>
					</form>
				</c:if>
				</div>
			</div>
		</div>
	</div>
	
	<script language="javascript"charset="UTF-8">
		
	 
	</script>
	
</body>
</html>
