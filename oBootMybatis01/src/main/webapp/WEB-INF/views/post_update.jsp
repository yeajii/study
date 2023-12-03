<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="post_update_start" method="post">
	
	<input type="hidden" name="post_no" value="${postContent.post_no}">
	
		<table border="1"> 
			<tr>
				<td>번호</td> <td>이름</td> <td>날짜</td> <td>내용</td>
			</tr>
			<tr>
				<td>${postContent.post_no}</td>
				<td>${postContent.post_name}</td>
				<td>${postContent.create_date}</td>
				<td><textarea cols="10" rows="10" name="post_content">${postContent.post_content}</textarea></td>
				<td> <button type="submit">등록</button></td>
			</tr>	
		</table>
	
	</form>

</body>
</html>