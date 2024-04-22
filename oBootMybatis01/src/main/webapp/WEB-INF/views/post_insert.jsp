<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	<h1>insert page</h1>

	<form action="insertPostForm" method="post">
	
		<table border="1">
			<tr> <th>등록일</th> <td><%=strDate%></td> </tr>
			<tr> <th>제목</th> <td><input type="text" name="postName"></td> </tr>	
			<tr> <th>내용</th> <td><textarea rows="10" cols="50" name="postContent"></textarea></td> </tr>
			<tr> 
				<td><input type="submit" value="등록"></td>
				<td><input type="button" value="닫기" onclick="location.href='start'"></td> 
			</tr>
		</table>
		
	</form>
	
</body>
</html>