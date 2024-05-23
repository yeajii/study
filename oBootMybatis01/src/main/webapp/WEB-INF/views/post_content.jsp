<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> <!-- jquery 사용하기 위해 head 안에 CDN 추가 -->
<title>Insert title here</title>
<script type="text/javascript">

	$(document).ready(function(){
		var postNo;						// 전역변수 남발하지 않기 위해 캡슐화 (즉시 호출 함수 표현식, IIFE)
		
		initData();				
		initEvent();			
	});	
		
	function initData(){
		postNo = ${contentPost.postNo};	// 전역변수 
		console.log("initData의 postNo-> " + postNo);
		
	/*	다시 해보기 
		if (postNo == null) {
	        window.location.href = 'error.jsp';
	        return; // 추가적인 코드 실행을 막기 위해 return
	    } */ 
		
 		commentList(postNo);	// 댓글 리스트
		
		// 댓글 입력 
		var writeComment = $('#comment-write-box');
		var textarea = $('<textarea>',{
							type 	: 'text'
							,id		: 'comment-write-textarea'
							,rows 	: '5'
							,cols 	: '65'
							,placeholder : '댓글을 입력하세요..'
						});
		var input = $('<input>',{
							type 	: 'button'
							,id 	: 'comment-write-btn'
							,value 	: '댓글 등록'
						});
		writeComment.append(textarea);
		writeComment.append(input);
	}	
		
	function initEvent(){
		$('#close-btn').on('click', function(){
			location.href = 'start';
		});
		
		$('#comment-write-btn').on('click', function(){
			insertComment(postNo); 
		});
	}	
	
	// 댓글 리스트	
	function commentList(postNo){
		$("#comment-box").empty(); 
		
		var table = $('<table border="1"></table>');
		
		$.ajax({
			type		: 'get'
			,url		: 'selectComment'
			,data		: {'postNo' : postNo}
			,dataType	: 'json'
			,success	: function(data){  
				console.log(data);
			
	            $("#no-comments").remove();			// '현재 댓글이 없습니다' 제거  
				
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
					    // var deleteBtnId = 'deleteBtn_' + row.commentNo; 	// 댓글 번호를 기반으로 고유한 ID 생성
					    // deleteBtn.attr('id', deleteBtnId); 				// 고유한 ID를 삭제 버튼에 할당
					    deleteBtn.attr('value', row.commentNo);		
					    newRow.append($('<td></td>').append(deleteBtn));
					   
					    table.append(newRow);
					    
					    // 댓글 삭제	 initEvent에 빼면 --> deleteBtnId가 commentList 함수 안에 있어서 인식 안돼서 이벤트 발생 안함 
					    deleteBtn.on('click', function(){
					    	var commentNo = $(this).attr('value');	
					    	console.log("댓글 삭제  commentNo-> " + commentNo);  
					    	console.log("댓글 삭제  postNo-> " + postNo);
					    	deleteComment(postNo, commentNo);
					    });  
					});
						
					$("#comment-box").append(table);
				}else{
					var noComment = $('<div id="no-comments"></div>');
					noComment.text("현재 댓글이 없습니다");
					$('body').append(noComment);
					console.log("현재 댓글이 없습니다");
				}
			},
			error: function(e){
				console.log(e);
			} 
		});
	}	
	
	// 댓글 입력
	function insertComment(postNo){
		console.log("insertComment postNo------> " + postNo);
		
		var writeComment = $('#comment-write-box');
	    var textarea = writeComment.find('#comment-write-textarea'); 
	    var commentBody = textarea.val(); 							 

	    console.log("insertComment commentBody------> " + commentBody);
	    
	    $.ajax({
			type 		: 'post'
			,url 		: 'insertComment'
			,contentType: 'application/json'				// 클라이언트가 보내는 데이터가 JSON 형식임을 명시, 서버에게 해당 데이터 형식을 알려주는 역할
			,data 		: JSON.stringify({					// JSON으로 변환해서 전송
							 'commentBody' 	: commentBody
							 ,'postNo' 		: postNo
							})
			,dataType 	: 'text'
			,success	: function(data){
				if(data === "1"){ 
					console.log("댓글이 성공적으로 등록되었습니다.");
					textarea.val('');	
					commentList(postNo); 					// 댓글 리스트 업데이트
				}else{
					console.error("댓글 등록에 실패했습니다.");
				}
			},
			error: function(e){
				console.error("댓글 등록 안됨-> ", e);
			}
		}); 
	}
	
	// 댓글 삭제 
	function deleteComment(postNo, commentNo){
		console.log("deleteComment postNo-> " + postNo);
		console.log("deleteComment commentNo-> " + commentNo);
		
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
						commentList(postNo); 			// 댓글 리스트 업데이트
					}else{
						alert("댓글 삭제되지 않았습니다.");
					}
				}
			});
		}else{
			return false;
		}
	}
	
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
		<tr>
			<th>No.</th>
			<td>${contentPost.postNo}</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${contentPost.createDate}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${contentPost.postName}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td style="width: 70%;">${contentPost.postContent}</td>
		</tr>
		<tr>
			<td>
				<input type="button" id="close-btn" value="닫기"> 
				<input type="button" value="수정" onclick="location.href='updatePost?postNo=${contentPost.postNo}'">
				<input type="button" value="삭제" onclick="deletePost(${contentPost.postNo})">
			</td>
		</tr>
	</table>
	<p>

	<!-- 댓글 입력 -->
	<div id="comment-write-box"></div>

	<!-- 댓글 리스트 -->
	<div id="comment-box"></div>
	
</body>
</html>