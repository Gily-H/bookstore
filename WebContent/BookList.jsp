<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<div class="links">
			<h1>
				<a href="list">Book Store</a>
			</h1>
			<h2>
				<a href="new">Add a Book</a>
			</h2>
		</div>
		<table>
			<caption>List of Books</caption>
			<tr>
				<th>Title</th>
				<th>Author</th>
				<th>Price</th>
			</tr>
			<c:forEach items="${book_list}" var="book">
				<tr>
					<td>${book.getTitle()}</td>
					<td>${book.getAuthor()}</td>
					<td>${book.getPrice()}</td>
				</tr>
			</c:forEach>
		</table>

	</div>
</body>
</html>