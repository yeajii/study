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
	<table border="1">
		    <tr>
		      	<td>번호</td> <td>이름</td> <td>날짜</td> <td>내용</td>
		    </tr>
		    
		    <c:forEach var="post" items="${allPostList}">
			    <tr>
			    	<td>${post.post_no}</td> 
			      	<td>${post.post_name}</td>
			      	<td>${post.create_date}</td>
			      	<td><a href="post_content?post_no=${post.post_no}">${post.post_content}</a></td>
			    </tr>
		  </c:forEach>
			<tr>
			    <td colspan="4">
			      <input type="submit" value="작성" onclick="location.href='post_insert_move'">
			    </td>
		  	</tr>
	</table>	
</body>
</html>