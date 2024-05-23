<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Insert title here</title>
	
	<!-- Bootstrap CSS (스타일 적용) -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	
	<!-- jQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	
	<!-- Bootstrap JS (동적 기능) -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
    <style type="text/css"> /* 우선순위에 따라 글자 색 변경하기*/
		.form-control{margin-bottom: 10px;}
		
		.radio-btn-label {margin-left: 5px;
    					margin-right: 5px;}
    					
    	.col-md-3{background-color: #FAF4C0}				
    				
    	#delete-btn{position: absolute;
    					right: 5px;}			
    					
		#done-btn{position: absolute;
					right: 90px;}				
    		
		#div-check-box{margin-bottom: 10px;}
		
		#add-btn{margin-right: 10px;
				margin-bottom: 15px;}
				
		#cancel-btn{margin-bottom: 15px;}		
	</style>
<script type="text/javascript">

	$(function(){
		initData();		
		initEvent();
	});
	
	function initEvent(){
		// 이벤트 위임을 사용하여 동적으로 생성된 #cancel-btn 요소에 핸들러 적용
		$(document).on('click', '#cancel-btn', function(){
	        $('input:radio[name="todo_priority"]').prop('checked', false);	
	        $('#input-todo').val("");									
	    });
	}
	
	function initData(){
		createTodoOutBox();
		todoInput();	
		todoList();
	}
	
	function createTodoOutBox(){
		// 존재하는지 확인하고, 존재하면 반환
		var existingBox = $('#todo-out-box');
		if(existingBox.length > 0){
			return existingBox;
		}
		
		// 존재하지 않으면 새로 생성하고 반환
		var newBox = $('<div id="todo-out-box" class="col-md-3"></div>');
		$('body').append(newBox);
		return newBox; 
	}
	
	function todoInput(){
		var todoOutBox = createTodoOutBox();
		
		var todoInputBox = $('#todo-input-box');
		if(todoInputBox.length === 0){
			todoInputBox = $('<div id="todo-input-box"></div>');
		}
		var divBox = $('<div></div>');
		var todo = $('<input>',{
						type 			: 	'text'
						,id				: 	'input-todo'
						,placeholder	: 	'new todo'
						,name			: 	'todo_content'
						,class			:	"form-control"
					});
		var priorities = [
	        {id: 'todo-check-top'	, value: '1', text: '상'},
	        {id: 'todo-check-middle', value: '2', text: '중'},
	        {id: 'todo-check-lower'	, value: '3', text: '하'}
	    ];
		
		priorities.forEach(function(priority){
			var radioBtn = $('<input>', {
				            type	: 	'radio'
				            ,id		: 	priority.id
				            ,name	: 	'todo_priority'			// 동일한 name 속성을 가진 라디오 버튼들은 하나의 그룹
				            ,value	: 	priority.value			// 서버로 전송 
				        });
			 var radioBtnLabel = $('<label>', {
				            for		: 	priority.id				// <input> id와 연결
				            ,text	: 	priority.text			// 라벨의 텍스트 내용(상,중,하)
				            ,class	:	'radio-btn-label'
				        });
			 
			 divBox.append(radioBtn).append(radioBtnLabel); 	// 라디오 버튼과 라벨을 반복문 내에서 divBox에 추가해야 된다 
		});
		
		var addBtn = $('<input>',{
						type 	:	'button'
						,id 	:	'add-btn'
						,value 	:	'add'
						,class	: 	"btn btn-secondary btn-sm"
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
						,value 	: 	'입력 취소'
						,class	:	"btn btn-light btn-sm"
					});
		
		divBox.append(todo);
	    divBox.append(addBtn);
	    divBox.append(cancelBtn);
	    
	    todoInputBox.append(divBox); 
	    todoOutBox.append(todoInputBox); 
	    
	    $('body').append(todoOutBox);
	}
	
	function todoList(){
		$.ajax({
			type 		: 	'get'
			,url 		: 	'selectTodoList'
			,dataType 	: 	'json'
			,success 	: 	function(data){
				console.log(data);
				
				var todoOutBox = createTodoOutBox();	
				
				var todoListBox = $('#todo-list-box');
				if (todoListBox.length === 0) {
	                todoListBox = $('<div id="todo-list-box"></div>');
	                todoOutBox.append(todoListBox);
	            } else {
	                todoListBox.empty();
	            } 
			
				data.forEach(function(todo){
					var divCheckBox = $('<div id="div-check-box"></div>');
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
									,class	: "btn btn-outline-secondary btn-sm"
								});
					var deleteBtn = $('<input>',{
									type	: 'button'
									,id 	: 'delete-btn'
									,name 	: todo.todo_no
									,value 	: '삭제'
									,class	: "btn btn-outline-secondary btn-sm"
								});

                    divCheckBox.append(checkBox);
                    divCheckBox.append(checkBoxLable);
                    divCheckBox.append(doneBtn);
                    divCheckBox.append(deleteBtn);
                    
                    todoListBox.append(divCheckBox);
                 
                    // 완료  - 체크박스 선택 안할 시 알림창 
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
			,contentType: 	'application/json'
			,data		:	JSON.stringify({
								'todo_content' : todoContent
								,'todo_priority' : todoPriority
							})
			,dataType	: 'text'
			,success	:	function(data){
				if(data === "1"){
					console.log("addTodo 성공");
					$('#input-todo').val('');
					$('input[name = todo_priority]').prop('checked', false);
					todoList();
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
						todoList();
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
						todoList();
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
		<h3>TODOLIST</h3>
</body>
</html>