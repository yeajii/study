<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>	<!-- jquery 사용하기 위해 head 안에 CDN 추가 -->
<title>Insert title here</title>
<script type="text/javascript">
	
	$(document).ready(function(){
		
		createCommentTable();
		
		// 댓글 
		function createCommentTable(){
			var postNo = ${contentPost.postNo};
			console.log("글 번호 확인: " + postNo);
			
			var table = $('<table border="1"></table>');
			
			$.ajax({
				url			: 'commentSelect'
				,type		: 'get'
				,data		: {'postNo' : postNo}
				,dateType	: 'json'
				,success	: function(data){
					console.log(data);
					
					table.append(
						$('<tr></tr>')
							.append($('<th>No.</th>'))
							.append($('<th>작성일</th>'))
							.append($('<th>수정일</th>'))
							.append($('<th>내용</th>'))
					);
					
					// 테이블 내용
					$.each(data, function(index, row){
						console.log("row.commentNo-> " + row.commentNo);
						console.log("row.commentBody-> " + row.commentBody);
						
					    // 새로운 행 생성
					    var newRow = $('<tr></tr>');

					    newRow.append($('<td></td>').text(row.commentNo));
					    newRow.append($('<td></td>').text(row.createDate));
					    newRow.append($('<td></td>').text(row.modifyDate));
					    newRow.append($('<td></td>').text(row.commentBody));

					    table.append(newRow);
					});
					
						$("#comment-box").append(table);
					},
					error: function(e){
						console.log(e);
					}
			});
		} 
	});
	
	
	// 해당 글 삭제
	function deletePost(postNo){
		console.log("삭제할 글 번호: " + postNo);
		if(confirm("삭제하시겠습니까?")){
			$.ajax({
				url 		: 'deletePost'
				,type		: 'post'
				,data		: {'postNo' : postNo}
				,dataType 	: 'text'				
				,success	: function(data){
					if(data == 1){
						alert("해당 글이 삭제되었습니다.");	
						var location = "start";
						window.location.href = location;
					}else{
						alert("해당 글이 삭제되지 않았습니다.");
					}
				}
			});	
		}else{
			return false;
		}
	}
	
	
</script>
</head>
<body>
	<h1>Content Page</h1>
	
	<table border="1">
		<tr> <th>No.</th> <td>${contentPost.postNo}</td> </tr>
		<tr> <th>작성일</th> <td>${contentPost.createDate}</td> </tr>
		<tr> <th>제목</th> <td>${contentPost.postName}</td> </tr>
		<tr> <th>내용</th> <td style="width: 70%;">${contentPost.postContent}</td> </tr>
		<tr>  
			<td>
				<input type="button" value="닫기" onclick="location.href='start'">
				<input type="button" value="수정" onclick="location.href='updatePost?postNo=${contentPost.postNo}'">
				<input type="button" value="삭제" onclick="deletePost(${contentPost.postNo})">		
			</td>
		</tr>
	</table><p>

	<!-- 댓글 -->
	<div id="comment-box"></div>

</body>
</html>