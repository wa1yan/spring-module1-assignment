<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign In</title>
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

<c:url value="/resources/application.css" var="cssUrl"></c:url>
<link rel="stylesheet" href="${ cssUrl }" />

</head>
<body class="vh-100">
	<div class="d-flex vh-100 justify-content-center align-items-center">
		<div class="card login-form">
			<div class="card-header">
				<i class="bi bi-door-open"></i>
				Sign In
			</div>
			<div class="card-body">
				<c:url var="signin" value="/signin"></c:url>
				<sf:form action="${signin }" method="post">
					<c:if test="${ not empty param.error }">
						<div class="alert alert-warning">Login error</div>
					</c:if>	
					<div class="mb-3">
						<label class="form-label">Email</label>
						<input type="email" name="username" placeholder="Enter Email address" class="form-control" />
					</div>
					<div class="mb-3">
						<label class="form-label">Password</label>
						<input type="password" name="password" placeholder="Enter password" class="form-control" />
					</div>
					<div class="mb-3">
						<button class="btn btn-outline-success">
							<i class="bi bi-door-open"></i>
							Sign In
						</button>
					</div>
				</sf:form>
			</div>
		</div>
	</div>
</body>
</html>