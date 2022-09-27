<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Leave | Classes</title>
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
		<h3 class="my-4">Class Details</h3>
		<div class="card mb-4">
			<div class="card-header">Class Information</div>
			<div class="card-body row mb-2">
				<div class="col">
					<label class="form-label">Teacher</label>
					<span class="form-control">${ dto.classInfo.teacherName }</span>
				</div>
				<div class="col">
					<label class="form-label">Start Date</label>
					<span class="form-control">${ dto.classInfo.startDate }</span>
				</div>
				<div class="col">
					<label class="form-label">Durations</label>
					<span class="form-control">${ dto.classInfo.months } Months</span>
				</div>
				<div class="col">
					<label class="form-label">Description</label>
					<span class="form-control">${ dto.classInfo.description }</span>
				</div>
			</div>
		</div>
		<div class="d-flex justify-content-between">
			<ul class="nav nav-pills mb-4">
				<li class="nav-item">
					<button class="nav-link active" data-bs-toggle="pill" data-bs-target="#registration"><i class="bi bi-people-fill"></i> Registration</button>
				</li>
				<li class="nav-item">
					<button class="nav-link" data-bs-toggle="pill" data-bs-target="#leaves"><i class="bi bi-person-x"></i> Leave Application</button>
				</li>
			</ul>
			<div>
				<c:url var="editClass" value="/classes/edit">
					<c:param name="id" value="${ dto.classInfo.id }"></c:param>	
				</c:url>
				<a href="${ editClass }" class="btn btn-outline-danger mb-4"><i class="bi bi-pencil"></i> Edit Class</a>
				
				<c:url var="addRegistration" value="/classes/registration">	
					<c:param name="classId" value="${ dto.classInfo.id }"></c:param>	
					<c:param name="teacherName" value="${ dto.classInfo.teacherName }"></c:param>
					<c:param name="startDate" value="${ dto.classInfo.startDate }"></c:param>
				</c:url>
				<a href="${ addRegistration }" class="btn btn-outline-primary mb-4"><i class="bi bi-plus"></i> Add New Registration</a>
			</div>
		</div>
			
		<div class="tab-content" id="contents">
			<div class="tab-pane fade show active" id="registration">
				<c:choose>
					<c:when test="${ empty dto.registrations }">
						<div class="alert alert-info">There is no registration data.</div>
					</c:when>
					<c:otherwise>
						<c:import url="/jsp/include/class-registration.jsp"></c:import>
					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="tab-pane fade" id="leaves">
				<c:choose>
						<c:when test="${ empty dto.leaves }">
							<div class="alert alert-info">There is no leaves data.</div>
						</c:when>
						<c:otherwise>
							<c:import url="/jsp/include/class-leaves.jsp"></c:import>
						</c:otherwise>
				</c:choose>		
			</div>
		</div>
	</div>
	
</body>
</html>