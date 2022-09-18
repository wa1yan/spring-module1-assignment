<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-hover">
	<thead>
		<tr>
			<th>Id</th>
			<th>Registration Date</th>
			<th>Student</th>
			<th>Phone</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>1</td>
			<td>2022-10-12</td>
			<td>Aye Aye</td>
			<td>09254298575</td>
			<td>
				<c:url value="/classes/registration" var="edit">
					<c:param name="id" value="1"></c:param>
				</c:url> 
				<a href="${ edit }" class=""><i class="bi bi-pencil me-2"></i></a>
				
				<c:url value="/classes/registration/1" var="details">
				</c:url> 
				<a href="${ details }" class=""><i class="bi bi-cursor"></i></a>
			</td>
		</tr>
	</tbody>
</table>