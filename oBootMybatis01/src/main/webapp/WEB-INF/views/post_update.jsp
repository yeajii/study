<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>	<!-- jquery 사용하기 위해 head 안에 CDN 추가 -->
<title>Insert title here</title>
</head>
<body>
	<%
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simpleDateFormat.format(date);
	%>
	
	<h1>Update Page</h1>
	
	<form action="updatePostForm" method="post">
	
		<input type="hidden" name="postNo" value="${contentPost.postNo}">
		<input type="hidden" name="isDeleted" value="${contentPost.isDeleted}">
		
			<table border="1">
				<tr> <th>수정일</th> <td><%=strDate%></td> </tr>
				<tr> <th>No.</th> <td>${contentPost.postNo}</td> </tr>
				<tr> <th>제목</th> <td> <input type="text" name="postName" value="${contentPost.postName}"> </td> </tr> 
				<tr> <th>내용</th> <td> <textarea rows="10" cols="50" name="postContent">${contentPost.postContent}</textarea></td> </tr>
				<tr> 
					<td><input type="submit" value="등록"></td> 
					<td><input type="button" value="닫기" onclick="location.href='start'"></td> 
				</tr>
			</table>
			
	</form>
	
		
</body>
</html>