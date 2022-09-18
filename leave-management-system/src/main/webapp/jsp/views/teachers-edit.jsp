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

	<c:import url="/jsp/include/navbar.jsp" ></c:import>
	
	<div class="container">
		<h3 class="my-3">${ empty param.id ? 'Add' : 'Edit' } Teacher</h3>
		
		<div class="row">
			<c:url value="/teachers" var="saveUrl"></c:url>
			<sf:form action="${ saveUrl }" method="post" cssClass="col-lg-6 col-md-8 col-sm-12" modelAttribute="form">
				
				<sf:hidden path="id"/>
				
				<div class="mb-4">
					<sf:label path="name" cssClass="form-label">Name</sf:label>
					<sf:input path="name" cssClass="form-control" placeholder="Enter Teacher Name"/>
				</div>
				<div class="mb-4">
					<sf:label path="phone" cssClass="form-label">Phone</sf:label>
					<sf:input path="phone" cssClass="form-control" placeholder="Enter Phone Number"/>
				</div>
				<div class="mb-4">
					<sf:label path="email" cssClass="form-label">Email</sf:label>
					<sf:input path="email" cssClass="form-control" placeholder="Enter Email Address"/>
				</div>
				<div class="mb-4">
					<sf:label path="assignDate" cssClass="form-label">Assign Date</sf:label>
					<sf:input type="date" path="assignDate" cssClass="form-control"/>
				</div>
				
				<div class="mb-4">
					<button class="btn btn-outline-success">
						<i class="bi bi-save"></i>
					Save</button>
				</div>
			
			</sf:form>
		</div>
		
		
	</div>
</body>
</html>