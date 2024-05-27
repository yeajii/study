<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> <!-- Spring 폼 태그 라이브러리 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
	
	<form:form action="updatePostForm" method="post" modelAttribute="Post" enctype="multipart/form-data">
	
		<input type="hidden" name="postNo" value="${contentPost.postNo}">
		<input type="hidden" name="isDeleted" value="${contentPost.isDeleted}">
		<input type="hidden" name="attachPath" value="${contentPost.attachPath}"> <!-- 파일 수정하지 않고 제목이나 내용만 수정하는 경우 기존 파일의 경로 유지 -->
		
			<table border="1">
				<tr> <th>수정일</th> <td><%=strDate%></td> </tr>
				<tr> <th>No.</th> <td>${contentPost.postNo}</td> </tr>
				<tr> <th>제목</th> <td> <input type="text" name="postName" value="${contentPost.postName}"> </td> </tr> 
				<tr> <th>내용</th> <td> <textarea rows="10" cols="50" name="postContent">${contentPost.postContent}</textarea></td> </tr>
				<tr> 	
					<th>첨부파일</th> 
					<td>
						<c:if test="${result == 1}">	
							<input type="file" name="file1">
							<span>현재 파일: ${contentPost.attachName}</span>
						</c:if>	
						<c:if test="${result == 0}">
							<input type="file" name="file1">	
							<span>현재 파일: 없음</span>
						</c:if>	
					</td>
				</tr>		
				<tr> 
					<td>
						<input type="submit" value="등록">
						<input type="button" value="닫기" onclick="location.href='start'">
					</td> 
				</tr>
			</table>
			
	</form:form>
</body>
</html>