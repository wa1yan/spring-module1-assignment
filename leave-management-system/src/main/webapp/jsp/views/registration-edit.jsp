<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Leave | Teachers</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
</head>
<body>

	<c:import url="/jsp/include/navbar.jsp" >
		<c:param name="view" value="classes"></c:param>
	</c:import>
	
	<div class="container">
		<h3 class="my-4">${ empty param.id ? 'Add New': 'Edit ' } Registration</h3>
		
		<div class="row">
			<sf:form modelAttribute="registForm" cssClass="col-6">
				
				<sf:hidden path="id"/>
				<sf:hidden path="classId"/>
				
				<div class="mb-3">
					<label class="form-label">Start Date</label>
					<span class="form-control">2022-10-22</span>
				</div>
				<div class="mb-3">
					<label class="form-label">Teacher</label>
					<span class="form-control">Guru</span>
				</div>
				<div class="mb-3">
					<label class="form-label">Student Name</label>
					<span class="form-control">Aye Aye</span>
				</div>
				
			</sf:form>
		</div>
	</div>
</body>
</html>