<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>	<!-- jquery 사용하기 위해 head 안에 CDN 추가 -->
<title>Insert title here</title>
<script type="text/javascript">
	
	// 해당 글 삭제
	function deletePost(postNo){
		alert("삭제할 글 번호: " + postNo);
		if(confirm("삭제하시겠습니까?")){
			$.ajax({
				url 		: 'deletePost'
				,type		: 'post'
				,data		: {'postNo' : postNo}
				,dataType 	: 'text'				
				,success	: function(data){
					if(data == 1){
						alert("해당 글이 삭제되었습니다.");	
						history.back();				// 이전 페이지 이동 
					}else{
						alert("해당 글이 삭제되지 않았습니다.");
					}
				}
			});	
		}else{
			return false;
		}
	}
	
	// 댓글 작성
/* 	$(function(){
		$("#comment-write-btn").click(function(){
			var contents = $("#commentContents").val();
			// alert("댓글 내용: " + contents);
		});
	}); */

	// 댓글 출력
/* 	var comments = [];						
	function commentWrite(){
		var contents = $("#commentContents").val();
		var comment = {content : contents};
		comments.push(comment);					// push(): 배열의 끝에 하나 이상의 요소를 추가, 각 댓글을 배열에 추가
		console.log(comments);
		
		$("#comment-list").empty();				// 댓글 리스트 초기화
		comments.forEach(function(comment){		// 배열을 순회하면서 각 댓글 출력
			$("#comment-list").append("<div>" + comment.content + "</div>");
		});
	} */
	
	$(function(){
		var writeBox = $("#comment-write-box");
		var textarea = $('<textarea>',{
						type 	: 'text'
						,id 	: 'comment-contents'
						,rows 	: '5'
						,cols 	: '65'
						,placeholder : '댓글을 입력하세요..'
					});
		var input = $('<input>',{
						type 	: 'button'
						,id 	: 'comment-write-btn'
						,value 	: '댓글 등록'
					});
		
		writeBox.append(textarea);
		writeBox.append(input);
		var commentList = $('#comment-list');
		
		input.on('click', function(){			// .on() : 첫 번째 인자는 이벤트 함수, 두 번쨰 인자는 호출할 콜백함수를 전달하는 메서드
			var comment = textarea.val();
			commentList.append($('<div>').text(comment));	
			textarea.val('');
		});
		
		//-------------------------- 댓글 버튼 ----------------------------------
		var allBtn = $('#comment-all-btn');
		var deleteBtn = $('<input>',{
						type 	: 'button'
						,id 	: 'delete-btn'
						,value 	: '댓글 전체 삭제'
					});
		
		var backgroundBtn = $('<input>',{
						type 	: 'button'
						,id 	: 'background-btn'
						,value 	: '배경색 변경'
					});
		
		var textareaBtn = $('<input>',{
						type 	: 'button'
						,id 	: 'textarea-btn'
						,value 	: 'textarea 배경'
					});
		
		allBtn.append(deleteBtn);
		allBtn.append(backgroundBtn);
		allBtn.append(textareaBtn);
		
		//-------------------------- 댓글 전체 삭제 ---------------------------------- 
		deleteBtn.on('click', function(){
			// console.log("댓글 전체 삭제");
			commentList.empty();
		});
		
		//-------------------------- 배경색 변경---------------------------------- 
		backgroundBtn.on({
			click: function(){
				$('body').css('background-color', 'white');
			}
		
			,mouseover: function(){
				$('body').css('background-color', '#FFFFE4');
			}
		});
		
		//-------------------------- textarea 배경---------------------------------- 
		textareaBtn.on('dblclick', function(){
			$('#comment-contents').css('background-color', '#FFEBFF');
		});
		
		
	});
	
	
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
	
<!-- 댓글 작성 -->
<!--<div id="comment-write">
	<textarea rows="5" cols="65" id="commentContents" placeholder="댓글을 입력하세요.."></textarea><br>
	<button id="comment-write-btn" onclick="commentWrite()">댓글 작성</button>
	</div> -->
	
<!-- 댓글 출력 -->
<!--<div id="comment-list"></div> -->
	
	<div id="comment-write-box"></div>
    <div id="comment-list"></div>
    <div id="comment-all-btn"></div>	<!-- 댓글 전체 삭제, 노란색배경, 하늘색배경 -->
	
</body>
</html>