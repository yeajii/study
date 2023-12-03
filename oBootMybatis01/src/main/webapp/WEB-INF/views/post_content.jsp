<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function postDelete(post_no){
	alert("시작!");
	$.ajax({
		url			: 'post_delete',
		type		: 'post',
		dataType	: 'text',
		data		: {'post_no' : post_no},
		success		: function(data){
			if(data == 1){
				alert("삭제 성공!");
				var a = "start";   
				window.location.href = a;
			} else {
				alert("삭제 실패..");
			}
		}
	});
}
</script>

</head>

<body>

	<!-- post 상세 조회 -->
	<table border="1">
		<tr>
			<td>번호</td> <td>이름</td> <td>날짜</td> <td>내용</td>
		</tr>
		<tr>
			<td>${postContent.post_no}</td>
			<td>${postContent.post_name}</td>
			<td>${postContent.create_date}</td>
			<td>${postContent.post_content}</td>
		</tr>
		<tr>
			<td><button type="button" onclick="location.href='start'">목록</button></td>
			<td><button type="button" onclick="location.href='post_update_move?post_no=${postContent.post_no}'">수정</button></td>
			<td><button type="button" onclick="postDelete(${postContent.post_no})">삭제</button></td>
		</tr>
	</table>
	
	
	<!-- 댓글 insert -->
	<form action="comt_insert" method="post">
	<input type="hidden" name="post_no" value="${postContent.post_no}">
		<table border="1">
			<tr>
				<td>댓글 입력</td>
				<td><textarea cols="40" rows="5" name="comment_content"></textarea></td>
			</tr>
			<tr>
				<td><input type="submit" value="등록"></td>
			</tr>
		</table>
	</form>
	

	<!-- 댓글 select -->
	<h5>댓글</h5>
	<table border="1">
		<tr>
			<td>번호</td> <td>이름</td> <td>날짜</td> <td>내용</td>
		</tr>
		<c:forEach var="comt" items="${comtSelect}">
			<tr>
				<td>${comt.post_no}</td> 
				<td>${comt.comment_id}</td>
				<td>${comt.comment_create_date}</td>
				<td>${comt.comment_content}</td>
			</tr>
		</c:forEach>
	</table>
	
	
	
	
	
	

</body>
</html>