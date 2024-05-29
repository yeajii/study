<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h4>총 건수: ${postListTotalCount}</h4>
	<input type="button" value="글  작성" onclick="location.href='insertPost'">
	
	<table border="1">
		<tr>
			<th>No.</th> <th>작성일</th> <th>제목</th>
		</tr>
		
		<c:forEach items="${postList}" var="post">
		    <tr>
		        <td>${post.postNo}</td>
		        <td>${post.createDate}</td>
		        <td><a href="contentPost?postNo=${post.postNo}">${post.postName}</a></td>
		    </tr>
		</c:forEach>
	</table>
	
</body>
</html>