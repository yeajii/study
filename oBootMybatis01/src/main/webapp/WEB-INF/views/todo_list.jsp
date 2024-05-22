<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> <!-- jquery 사용하기 위해 head 안에 CDN 추가 -->
<title>Insert title here</title>
<style type="text/css">
	/* 우선순위에 따라 글자 색 변경 */

</style>
<script type="text/javascript">
	
	$(function(){
		initData();
		initEvnet();
	});
	
	function initEvnet(){
		$('#input-todo').on('click', function(){
			$('#input-todo').val("");
		});
		
		$('#cancel-btn').on('click', function(){
			$('input:radio[name="priority"]').prop('checked', false);	
			$('#input-todo').val("");									
		});
	}
	
	function initData(){
		showTodoTop();
		showTodoMiddle();
	}
	
	function showTodoTop(){
		var todoBox = $('#todo-box');
		var divBox = $('<div></div>');
		var todo = $('<input>',{
						type 			: 	'text'
						,id				: 	'input-todo'
						,placeholder	: 	'new todo'
						,name			: 	'todo_content'
					});
		var priorities = [
	        {id: 'todo-check-top'	, value: '1', text: '상'},
	        {id: 'todo-check-middle', value: '2', text: '중'},
	        {id: 'todo-check-lower'	, value: '3', text: '하'}
	    ];
		
		priorities.forEach(function(priority){
			var radioBtn = $('<input>', {
				            type	: 	'radio',
				            id		: 	priority.id,
				            name	: 	'todo_priority',		// 동일한 name 속성을 가진 라디오 버튼들은 하나의 그룹
				            value	: 	priority.value			// 서버로 전송 
				        });
			 var radioBtnLabel = $('<label>', {
				            for		: 	priority.id,			// <input> id와 연결
				            text	: 	priority.text			// 라벨의 텍스트 내용(상,중,하)
				        });
			 
			 divBox.append(radioBtn).append(radioBtnLabel); 	// 라디오 버튼과 라벨을 반복문 내에서 divBox에 추가해야 된다 
		});
		
		var addBtn = $('<input>',{
						type 	:	'button'
						,id 	:	'add-btn'
						,value 	:	'add'
					}).click(function(){
						var todoContent = $('#input-todo').val();
						var todoPriority = $('input[name = todo_priority]:checked').val();
						console.log("todoContent-> " + todoContent + ", todoPriority-> " + todoPriority);
						
						// trim: 문자열 양쪽 끝 공백 제거, 스페이스나 탭 같은 공백만 입력했을 때 방지하기 위함   
						if(todoContent.trim() === ''){			
							alert("할 일을 입력하세요");
							return;
						}
						
						// length: 체크된 라디오 버튼 갯수
						if($('input[name = todo_priority]:checked').length === 1){	
							addTodo(todoContent, todoPriority);
						}else{
							alert("우선순위를 체크하세요");
						}
					});
		
		var cancelBtn = $('<input>',{
						type 	: 	'button'
						,id 	: 	'cancel-btn'
						,value 	: 	'취소'
					});
		
		divBox.append(todo);
	    divBox.append(addBtn);
	    divBox.append(cancelBtn);
	    
	    todoBox.append(divBox); 
	}
	
	function showTodoMiddle(){
		$.ajax({
			type 		: 	'get'
			,url 		: 	'selectTodoList'
			,dataType 	: 	'json'
			,success 	: 	function(data){
				console.log(data);
				
				var todoCheckBox = $('#todo-check-box');
				todoCheckBox.empty(); 
			
				data.forEach(function(todo){
					var divCheckBox = $('<div></div>');
					var checkBox = $('<input>',{
									type		: 	'checkbox'
									,name 		: 	'todo_check'
									,value		: 	todo.todo_check				
									,checked	: 	todo.todo_check === 'Y' 
								});
					var checkBoxLable = $('<label></label>').text(todo.todo_content);
					
					var doneBtn = $('<input>',{
									type	: 'button'
									,id 	: 'done-btn'
									,name 	: todo.todo_no
									,value 	: '완료'
								});
					var deleteBtn = $('<input>',{
									type	: 'button'
									,id 	: 'delete-btn'
									,name 	: todo.todo_no
									,value 	: '삭제'
								});

                    divCheckBox.append(checkBox);
                    divCheckBox.append(checkBoxLable);
                    divCheckBox.append(doneBtn);
                    divCheckBox.append(deleteBtn);
                    
                    todoCheckBox.append(divCheckBox);
                    
                    // 완료  - 체크박스 선택 안할 시 알림창 , 밑줄 그어지면서 맨 아래로 내려가도록 하기 
                    doneBtn.on('click', function(){
                    	var todoNo = $(this).attr('name');
                    	var isChecked = checkBox.is(':checked');		// checked이면 true 아니면 false 반환
                    	console.log("todoNo-> " + todoNo + ", isChecked-> " + isChecked);
                    	
                    	if(checkBox.is(':checked')){
                    		doneTodo(todoNo, isChecked);
                    	}else{
                    		alert('해당 체크박스를 선택해주세요.');
                    	}
                    });
                    
                    // 삭제 - 체크박스 선택 안할 시 알림창 
                    deleteBtn.on('click', function(){
                    	var todoNo = $(this).attr('name');
                    	var isChecked = checkBox.is(':checked');
                    	console.log("todoNo-> " + todoNo + ", isChecked-> " + isChecked);
                    	
                    	if(checkBox.is(':checked')){
                    		deleteTodo(todoNo);
                    	}else{
                    		alert('해당 체크박스를 선택해주세요.');
                    	}
                    });
				}); 
			},
			error: function(e){
				console.log(e);
			}
		});
	}
	
	function addTodo(todoContent, todoPriority){
		$.ajax({
			type		:	'post'
			,url		:	'addTodo'
			,data		:	{
								'todoContent' : todoContent
								,'todoPriority' : todoPriority
							}
			,dataType	: 'text'
			,success	:	function(data){
				if(data === "1"){
					console.log("addTodo 성공");
					todo.val('');
					showTodoMiddle();
				}else{
					alert('입력 처리 실패');
				}
			}
		});
	}
	
	function doneTodo(todoNo, isChecked){
		if(confirm("완료하시겠습니까?")){
			$.ajax({
				type 		: 'post'
				,url		: 'doneTodo'
				,data		: {
								'todoNo' 	 : todoNo
								,'todoCheck' : isChecked.toString()
								}
				,dataType 	: 'text'
				,success 	: function(data){
					if(data === "1"){
						console.log("doneTodo 성공");
						showTodoMiddle();
					}else{
						alert('완료 처리 실패');
					}
				}
			});
		}else{
			return false;
		} 
	}
	
	function deleteTodo(todoNo){
		if(confirm("삭제하시겠습니까?")){
			$.ajax({
				type		:	'post'
				,url		:	'deleteTodo'
				,data		:	{'todoNo' : todoNo}
				,dataType	:	'text'
				,success	:	function(data){
					if(data === "1"){
						console.log("deleteTodo 성공");
						showTodoMiddle();
					}else{
						alert('삭제 처리 실패');
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
	<h1>TODOLIST</h1>
	
	<div id="todo-box"></div>
	<div id="todo-check-box"></div>
</body>
</html>