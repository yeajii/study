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
			
			$('ul li span').each(function(){
				var fileName = $(this).text();
				var lastIndex = fileName.lastIndexOf("_");	
				var extensionIndex = fileName.lastIndexOf(".");
				
				if (lastIndex !== -1 && extensionIndex !== -1 && lastIndex < extensionIndex) {
	                var displayName = fileName.substring(0, lastIndex) + fileName.substring(extensionIndex);
	                $(this).text(displayName);
	            }
			});
			
			$('#files-delete-btn').on('click', function(){
				if($('input[type="checkbox"][name="deleteFileList"]:checked').length === 0){
			    	if(confirm("파일 삭제 안하면 확인을 눌러주세요")){
			    		$("form").submit();
			            return;		
			    	}else{
			    		return false;
			    	}
			    }
				
				if($('input[type="checkbox"][name="deleteFileList"]:checked').length >= 1){
		            if(confirm('선택한 파일을 삭제하시겠습니까?')){
		                var deleteFileList = [];
		                $('input[type="checkbox"][name="deleteFileList"]:checked').each(function(){ 
		                    deleteFileList.push($(this).val());
		                });
		                
		                $('<input />').attr('type', 'hidden')
		                    .attr('name', 'deleteFileList')
		                    .attr('value', deleteFileList)
		                    .appendTo('form');
		                
		                $("form").submit();
		            } else {
		                return false;
		            }
		        }
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
				<th>첨부 파일</th>
				<td>
					<c:choose>
						<c:when test="${empty fileNameList}">
							<span>현재 파일: 없음</span>
						</c:when>
						<c:otherwise>
							<ul>
					            <c:forEach var="fileName" items="${fileNameList}" varStatus="status">
					                <li>
					                	<span>${fileName}</span>
					                	<input type="checkbox" name="deleteFileList" value="${fileName}"> 
					                </li>
					            </c:forEach>
					        </ul> 
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr> <th>파일 추가</th> <td><input type="file" name="files" multiple="multiple"></td> </tr>
			<tr>
				<td>
					<input type="button" value="목록" id="close-btn">
					<input type="button" value="이전" id="back-btn">
					<input type="submit" value="등록" id="files-delete-btn">
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>