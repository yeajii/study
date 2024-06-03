<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<!-- jquery CDN -->
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> 
	<title>Insert title here</title>
	<script type="text/javascript">
		$(function(){
			initEvent();
		});
		
		function initEvent(){
			$('#close-btn').on('click', function(){
				location.href = 'boardFile';
			});
		}
	</script>
</head>
<body>
	<h1>Content Page</h1>
	
	<table border="1">
		<tr> <th>No.</th> 		<td>${contentBoardFile.id}</td> </tr>
		<tr> <th>제목</th> 		<td>${contentBoardFile.title}</td> </tr>
		<tr> <th>내용</th> 		<td style="width: 70%;">${contentBoardFile.content}</td> </tr>
		<tr> 
			<th>첨부파일</th> 	
			<td>
				<c:choose>
					<c:when test="${empty fileNameList}">
						파일이 존재하지 않습니다
					</c:when>
					<c:otherwise>
						<ul>
				            <c:forEach var="fileName" items="${fileNameList}">
				                <li>${fileName}</li>
				            </c:forEach>
				        </ul>
					</c:otherwise>
				</c:choose>
			</td> 
		</tr>
		<tr>
			<td>
				<input type="button" id="close-btn" value="닫기">
				<input type="button" value="수정" onclick="location.href='updateBoardFile?id=${contentBoardFile.id}'">
				<input type="button" value="삭제" onclick="deleteBoardFile(${contentBoardFile.id})">
				<%-- <input type="button" value="파일삭제" onclick="deleteFile(${contentBoardFile.id}, 여긴 나중에)"> --%>
			</td>
		</tr>
	</table>
</body>
</html>