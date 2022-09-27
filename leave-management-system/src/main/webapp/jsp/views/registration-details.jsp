<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<h3 class="my-4">Registration Details</h3>
		
		<div class="row">
			<div class="col">
				<div class="card">
					<div class="card-header">
						Student Information
					</div>
					<div class="card-body">
						<div class="mb-3">
							<label class="form-label">Studnet Name</label>
							<span class="form-control">${ dto.student.name }</span>
						</div>
						<div class="mb-3">
							<label class="form-label">Phone</label>
							<span class="form-control">${ dto.student.phone }</span>
						</div>
						<div class="mb-3">
							<label class="form-label">Email</label>
							<span class="form-control">${ dto.student.email }</span>
						</div>
						<div class="mb-3">
							<label class="form-label">Education</label>
							<span class="form-control">${ dto.student.education }</span>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col">
				<div class="card">
					<div class="card-header">Class Information</div>
					<div class="card-body">
						<div class="mb-3">
							<label class="form-label">Teacher</label>
							<span class="form-control">${ dto.classInfo.teacherName }</span>
						</div>
						<div class="mb-3">
							<label class="form-label">Start Date</label>
							<span class="form-control">${ dto.classInfo.startDate }</span>
						</div>
						<div class="mb-3">
							<label class="form-label">Duration</label>
							<span class="form-control">${ dto.classInfo.months } Months</span>
						</div>
						<div class="mb-3">
							<label class="form-label">Description</label>
							<span class="form-control">${ dto.classInfo.description }</span>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		<div class="mt-4">
			<c:url value="/classes/registration" var="edit">
				<c:param name="id" value="1"></c:param>
			</c:url>
			<a href="${ edit }" class="btn btn-outline-danger"><i class="bi bi-pencil"></i> Edit Registration</a>
		</div>
	</div>
</body>
</html>