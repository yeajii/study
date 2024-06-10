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
		
		function popupOrDownload(fileName) {
		    var url = '/download?fileName=' + encodeURIComponent(fileName);
		    var newWindow = window.open();
		    var ext = fileName.split('.').pop().toLowerCase();
			
			if(['jpg', 'jpeg', 'png', 'gif', 'bmp'].indexOf(ext) !== -1){	// indexOf: 찾는 문자열이 없으면 -1 리턴 
				// 새 창의 문서가 준비되었을 때 실행
				 $(newWindow.document).ready(function() {
			            var downloadLink = $('<a>', {
			                href	: url,
			                download: fileName,
			                text	: 'Download',
			                css		: {
			                    display	: 'block',
			                    margin	: '10px 0'
			                }
			            });
			            
			            $(newWindow.document.body).prepend(downloadLink);
			            
			            var img = $('<img>', {
			                src	: url,
			                css	: {
			                    display	: 'block',
			                    maxWidth: '100%',
			                    height	: 'auto'
			                }
			            });
			            
			            $(newWindow.document.body).append(img);
			        });
			}else{
				// 이미지 파일이 아닌 경우 파일 다운로드
				var anchor = $('<a>', {
	            href		: url,
	            download	: fileName,
	            css			: { display: 'none' }
	        });
				
	        $(newWindow.document.body).append(anchor);
	        anchor[0].click(); 	// 다운로드 진행
	        anchor.remove(); 	// 다운로드 후 팝업창에 표시되지 않도록 함 
			}
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
				                <li>
							        <a href="javascript:void(0);" onclick="popupOrDownload('${fileName}')">${fileName}</a>
							    </li>
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