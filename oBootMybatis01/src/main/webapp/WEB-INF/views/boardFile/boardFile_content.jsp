<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
		
		function popupOrDownload(filePath, displayFileName) {
			console.log("파일 경로: " + filePath);				// C:/boardFile/12/정처기 답안표_6abd875a-9a7f-4adf-96fc-221fa363278a.hwpx
			console.log("파일 이름: " + displayFileName);		// 정처기 답안표.hwpx
			
		    var ext = displayFileName.split('.').pop().toLowerCase();
		    console.log("ext: " + ext);
			
			if(['jpg', 'jpeg', 'png', 'gif', 'bmp'].indexOf(ext) !== -1){	// indexOf: 찾는 문자열이 없으면 -1 리턴
				// 새 창의 문서가 준비되었을 때 실행
				var newWindow = window.open();
				$(newWindow.document).ready(function() {
			            var downloadLink = $('<a>', {
			                href	: filePath,
			                download: displayFileName,
			                text	: 'Download',
			                css		: {
			                    display	: 'block',
			                    margin	: '10px 0'
			                }
			            });
			            
			            $(newWindow.document.body).prepend(downloadLink);
			            
			            var img = $('<img>', {
			                src	: filePath,
			                css	: {
			                    display	: 'block',
			                    maxWidth: '100%',
			                    height	: 'auto'
			                }
			            });
			            
			            $(newWindow.document.body).append(img);
			        });
			}else{
				console.log("사진 아닌 경우");
				// 이미지 파일이 아닌 경우 팝업 창 없이 바로 다운로드
		        var anchor = $('<a>', {
		            href        : filePath,
		            download    : displayFileName,
		            css         : { display: 'none' }
		        });
		        
		        console.log("1");

		        $('body').append(anchor);
		        anchor.get(0).click();
		        anchor.remove();
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
					<c:when test="${empty fileInfoList}">
						파일이 존재하지 않습니다
					</c:when>
					<c:otherwise>
						<ul>
				            <c:forEach var="fileInfo" items="${fileInfoList}">
				            	<c:set var="filePath" 			value="${fn:split(fileInfo, ',')[0]}"/>
				            	<c:set var="displayFileName" 	value="${fn:split(fileInfo, ',')[1]}"/>
				                <li>
							        <a href="javascript:void(0);" onclick="popupOrDownload('${filePath}', '${displayFileName}')">${displayFileName}</a>
							    </li>
				            </c:forEach>
				        </ul>
					</c:otherwise>
				</c:choose>
			</td> 
		</tr>
		<tr>
			<td>
				<input type="button" id="close-btn" value="목록">
				<input type="button" value="수정" onclick="location.href='updateBoardFile?id=${contentBoardFile.id}'">
				<input type="button" value="삭제" onclick="deleteBoardFile(${contentBoardFile.id})">
			</td>
		</tr>
	</table>
</body>
</html>