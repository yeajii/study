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
	
	<h4>총 건수: ${boardFileCount}</h4>
	<input type="button" value="글  작성" onclick="location.href='insertBoardFile'">
	
	<table border="1">
		<tr>
			<th>No.</th> <th>작성일</th> <th>제목</th> 
		</tr>
			
		<c:forEach items="${boardFileList}" var="boardFile">
			<tr>
				<td>${boardFile.id}</td>
				<td>${boardFile.createDate}</td>
				<td><a href="contentBoardFile?id=${boardFile.id}">${boardFile.title}</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>