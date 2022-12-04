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
	
<c:url value="/resources/application.css" var="cssUrl"></c:url>
<link rel="stylesheet" href="${ cssUrl }" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
</head>
<body>

	<c:import url="/jsp/include/navbar.jsp" >
		<c:param name="view" value="classes"></c:param>
	</c:import>
	
	<div class="container">
		<h3 class=" my-3">Classes Management</h3>
		
		<form class="row mb-4">
			<div class="col-auto">
				<label class="form-label">Teacher Name</label>
				<input type="text" name="teacher" class="form-control" placeholder="Enter Teacher Name" value="${ param.teacher }"/>
			</div>
			<div class="col-auto">
				<label class="form-label">Date From</label>
				<input type="date" name="from" class="form-control" value="${ param.from }"/>
			</div>
			<div class="col-auto">
				<label class="form-label">Date To</label>
				<input type="date" name="to" class="form-control" value="${ param.to }"/>
			</div>
			<div class="col btn-wrapper">
				<button class="btn btn-outline-success me-2"><i class="bi bi-search"></i> Search</button>
				
				<c:url value="/classes/edit" var="editUrl"></c:url>
				<a href="${ editUrl }" class="btn btn-outline-danger"><i class="bi bi-plus"></i> Add New</a>
			</div>
		</form>
		
		<c:choose>
			<c:when test="${ empty list }">
				<div class="alert alert-success">There is no data</div>
			</c:when>
			<c:otherwise>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>ID</th>
							<th>Teacher</th>
							<th>Teacher Phone No</th>
							<th>Start Date</th>
							<th>Months</th>
							<th>Student</th>
							<th>Description</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ list }" var="item">
							<tr>
								<td>${ item.id }</td>
								<td>${ item.teacherName}</td>
								<td>${ item.teacherPhone }</td>
								<td>${ item.startDate }</td>
								<td>${ item.months }</td>
								<td>${ item.studentCount }</td>
								<td>${ item.description }</td>
								<td>
									<c:url value="/classes/edit" var="edit">
										<c:param name="id" value="${ item.id }"></c:param>
									</c:url>
									<a href="${ edit }" class=""><i class="bi bi-pencil me-3"></i></a>
									
									<c:url value="/classes/${ item.id }" var="details">
									</c:url>
									<a href="${ details }" class=""><i class="bi bi-cursor"></i></a>
								</td>
							</tr>
						</c:forEach>
						
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
		
	</div>
</body>
</html>