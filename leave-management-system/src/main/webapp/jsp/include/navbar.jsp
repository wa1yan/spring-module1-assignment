<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/home" var="homeUrl"></c:url>
<c:url value="/teachers" var="teachersUrl"></c:url>
<c:url value="/classes" var="classesUrl"></c:url>
<c:url value="/leaves" var="leavesUrl"></c:url>
<c:url value="/students" var="studentsUrl"></c:url>
<c:url value="/signout" var="signoutUrl"></c:url>

<nav class="navbar navbar-expand-lg navbar-dark bg-success">
	<div class="container">
		<a href="${ homeUrl }" class="navbar-brand">Leave Management System</a>

		<ul class="navbar-nav">
			<li class="nav-item"><a href="${ teachersUrl }" class="nav-link ${param.view eq 'teachers' ? 'active' : '' }">Teachers</a>
			</li>
			<li class="nav-item"><a href="${ classesUrl }" class="nav-link ${param.view eq 'classes' ? 'active' : '' }">Classes</a>
			</li>
			<li class="nav-item"><a href="${ leavesUrl }" class="nav-link ${param.view eq 'leaves' ? 'active' : '' }">Leaves</a>
			</li>
			<li class="nav-item"><a href="${ studentsUrl }" class="nav-link ${param.view eq 'students' ? 'active' : '' }">Students</a>
			</li>
			<li class="nav-item"><a href="${ signoutUrl }" class="nav-link">Sign Out</a>
			</li>
		</ul>
	</div>
</nav>