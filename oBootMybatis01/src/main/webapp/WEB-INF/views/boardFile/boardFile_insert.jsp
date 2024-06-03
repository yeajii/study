<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<%
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simpleDateFormat.format(date);
	%>
	
	<h1>Insert Page</h1>
	
	<form:form action="insertBoardFileForm" method="post" modelAttribute="BoardFile" enctype="multipart/form-data">
		<table border="1">
		<tr>
			<th>등록일</th>
			<td><%=strDate %></td>
		</tr>
		
		<tr>
			<th>제목</th>
			<td><textarea name="title" cols="50" rows="1"></textarea></td>
		</tr>
		
		<tr>
			<th>내용</th>
			<td><textarea name="content" cols="50" rows="10"></textarea></td>
		</tr>
		
		<tr>
            <th>첨부파일</th>
            <td><input type="file" name="file1" multiple="multiple"></td>
        </tr>
		
		<tr>
			<td>
				<input type="submit" value="등록">
				<input type="button" value="닫기" onclick="location.href='boardFile'">
			</td>
		</tr>
	</table>
	</form:form>
	
</body>
</html>