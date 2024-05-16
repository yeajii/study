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
		var postNo = ${contentPost.postNo};	
		
		// 이벤트 초기화 함수 넣기 
		
		// 댓글 리스트
		commentList();				
		function commentList(){
			$("#comment-box").empty(); 
			
			var table = $('<table border="1"></table>');
			
			$.ajax({
				type		: 'get'
				,url		: 'selectComment'
				,data		: {'postNo' : postNo}
				,dateType	: 'json'
				,success	: function(data){
					console.log(data);
					
					if(data != null && data.length > 0){
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
							    
							    // 댓글 삭제 버튼
							    var deleteBtn = $('<button>삭제</button>');
							    deleteBtn.attr('value', row.commentNo);		
							    newRow.append($('<td></td>').append(deleteBtn));
							   
							    table.append(newRow);
							    
							    // 댓글 삭제 
							    deleteBtn.on('click', function(){
							    	var commentNo = $(this).attr('value');	
							    	console.log("댓글 삭제  commentNo-> " + commentNo);  
							    	console.log("댓글 삭제  postNo-> " + postNo);
							    	deleteComment(postNo, commentNo);
							    });
							});
							
							$("#comment-box").append(table);
							
							// 댓글 삭제 
							function deleteComment(postNo, commentNo){
								console.log("삭제할 postNo-> " + postNo);
								console.log("삭제할 commentNo-> " + commentNo);
								
								if(confirm("삭제하시겠습니까?")){
									$.ajax({
										type 		: 'post'
										,url 		: 'deleteComment'
										,data 		: {
														'postNo' 	 : postNo	
														,'commentNo' : commentNo
														}
										,dataType 	: 'text'
										,success 	: function(data){
											if(data == 1){
												alert("댓글 삭제 완료되었습니다.");
												commentList(); 			// 댓글 리스트 업데이트
											}else{
												alert("댓글 삭제되지 않았습니다.");
											}
										}
									});
								}else{
									return false;
								}
							}
							
					}else{
						console.log("댓글 데이터가 없습니다");
					}
				},
				error: function(e){
					console.log(e);
				} 
			});
		}
		
		// 댓글 입력 
		var writeComment = $('#comment-write-box');
		var textarea = $('<textarea>',{
							type : 'text'
							,id	: 'comment-write-textarea'
							,rows : '5'
							,cols : '65'
							,placeholder : '댓글을 입력하세요..'
						});
		var input = $('<input>',{
							type : 'button'
							,id : 'comment-write-btn'
							,value : '댓글 등록'
						});
		writeComment.append(textarea);
		writeComment.append(input);
		
		input.on('click', function(){
			var commentBody =  textarea.val();
			console.log("commentBody------> " + commentBody);
			
			$.ajax({
				type 	: 'post'
				,url 	: 'insertComment'
				,data 	: {
						 'commentBody' 	: commentBody
						 ,'postNo' 		: postNo
						}
				,dataType : 'json'
				,success: function(data){
					if(data.status === "success"){ 
						console.log("댓글이 성공적으로 등록되었습니다.");
						textarea.val('');	
						commentList(); 			// 댓글 리스트 업데이트
					}else{
						console.error("댓글 등록에 실패했습니다.");
					}
				},
				error: function(e){
					console.error("댓글 등록 안됨-> ", e);
				}
			});
		});
		
		
	});
	
	// 해당 글 삭제
	function deletePost(postNo){
		console.log("삭제할 글 번호: " + postNo);
		if(confirm("삭제하시겠습니까?")){
			$.ajax({
				type		: 'post'
				,url 		: 'deletePost'
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

	<!-- 댓글 입력 -->
	<div id="comment-write-box"></div>

	<!-- 댓글 리스트 -->
	<div id="comment-box"></div>

</body>
</html>