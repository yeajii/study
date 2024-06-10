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
			$('#back-btn').on('click', function(){
				location.href = 'contentBoardFile?id=${contentBoardFile.id}';
			});
		}
	</script>
</head>
<body>
	<%
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simpleDateFormat.format(date);
	%>
	<h1>Update Page</h1>
	
	<form:form action="updateBoardFileForm" method="post" modelAttribute="BoardFile" enctype="multipart/form-data">
		
		<input type="hidden" name="id" value="${contentBoardFile.id}">
	  	
		<table border="1">
			<tr> <th>No.</th> <td>${contentBoardFile.id}</td> </tr>	
			<tr> <th>수정일</th> <td><%=strDate %></td> </tr>
			<tr> <th>제목</th> <td><textarea rows="1" cols="50" name="title">${contentBoardFile.title}</textarea></td> </tr>
			<tr> <th>내용</th> <td><textarea rows="10" cols="50" name="content">${contentBoardFile.content}</textarea></td> </tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<c:choose>
						<c:when test="${empty fileNameList}">
							<input type="file" name="files" multiple="multiple">
							<span>현재 파일: 없음</span>
						</c:when>
						<c:otherwise>
							<ul>
					            <c:forEach var="fileName" items="${fileNameList}" varStatus="status">
					                <li>
					                	현재 파일: ${fileName}
					                	<input type="file" name="files[${status.index}]">
					                	<input type="hidden" name="fileNames[${status.index}]" value="${fileName}"> <!-- 이름만 서버로 전송, 기존파일 식별하기 위함 -->
					                </li>
					            </c:forEach>
					        </ul>
					                 파일 추가: <input type="file" name="additionalFiles" multiple="multiple">
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="등록">
					<input type="button" value="닫기" id="close-btn">
					<input type="button" value="이전" id="back-btn">
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>