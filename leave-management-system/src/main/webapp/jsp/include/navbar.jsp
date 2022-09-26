<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<c:url value="/home" var="homeUrl"></c:url>
<c:url value="/teachers" var="teachersUrl"></c:url>
<c:url value="/classes" var="classesUrl"></c:url>
<c:url value="/leaves" var="leavesUrl"></c:url>
<c:url value="/students" var="studentsUrl"></c:url>
<c:url value="/signout" var="signoutUrl"></c:url>
<c:url value="/resources/logout.js" var="logoutUrl"></c:url>

<nav class="navbar navbar-expand-lg navbar-dark bg-success">
	<div class="container">
		<a href="${ homeUrl }" class="navbar-brand">Leave Management System</a>

		<ul class="navbar-nav">
			<li class="nav-item"><a href="${ teachersUrl }" class="nav-link ${param.view eq 'teachers' ? 'active' : '' }">
				<i class="bi bi-people"></i>
				Teachers
			</a>
			</li>
			<li class="nav-item"><a href="${ classesUrl }" class="nav-link ${param.view eq 'classes' ? 'active' : '' }">
				<i class="bi bi-mortarboard"></i>
				Classes
			</a>
			</li>
			<li class="nav-item"><a href="${ studentsUrl }" class="nav-link ${param.view eq 'students' ? 'active' : '' }">
				<i class="bi bi-people-fill"></i>
				Students
			</a>
			</li>
			<li class="nav-item"><a href="${ leavesUrl }" class="nav-link ${param.view eq 'leaves' ? 'active' : '' }">
				<i class="bi bi-inbox"></i>
				Leaves
			</a>
			</li>
			<li class="nav-item">
				<a class="nav-link sign-out">
				<i class="bi bi-lock"></i>
				Sign Out
			</a>
			</li>
		</ul>
	</div>
</nav>
<sf:form action="${ signoutUrl }" method="post" class="sign-out-form"></sf:form>
<script src="${ logoutUrl }"></script>