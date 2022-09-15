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
	
<c:url value="/resources/application.css" var="cssUrl"></c:url>
<link rel="stylesheet" href="${ cssUrl }" />

</head>
<body>

	<c:import url="/jsp/include/navbar.jsp" >
		<c:param name="view" value="teachers"></c:param>
	</c:import>
	
	<div class="container">
		<h3 class=" my-3">Teacher Management</h3>
		
		<form class="row mb-4">
			<div class="col-auto">
				<label class="form-label">Name</label>
				<input type="text" name="name" class="form-control" placeholder="Enter Name" />
			</div>
			<div class="col-auto">
				<label class="form-label">Phone</label>
				<input type="text" name="phone" class="form-control" placeholder="Enter Phone" />
			</div>
			<div class="col-auto">
				<label class="form-label">Email</label>
				<input type="text" name="email" class="form-control" placeholder="Enter Email" />
			</div>
			<div class="col btn-wrapper">
				<button class="btn btn-outline-success me-2">Search</button>
				
				<c:url value="/teachers/edit" var="editUrl"></c:url>
				<a href="${ editUrl }" class="btn btn-outline-danger">Add New</a>
			</div>
		</form>
		
		<table class="table table-hover">
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Phone</th>
					<th>Email</th>
					<th>Assign Date</th>
					<th>Classes</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td>William</td>
					<td>09254295287</td>
					<td>william@gmail.com</td>
					<td>2022-09-14</td>
					<td>5</td>
					<td>
						<c:url value="/teachers/edit" var="edit">
							<c:param name="id" value="1"></c:param>
						</c:url>
						<a href="${ edit }" class="">Edit</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>