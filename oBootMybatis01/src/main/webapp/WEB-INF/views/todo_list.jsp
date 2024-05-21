<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> <!-- jquery 사용하기 위해 head 안에 CDN 추가 -->
<title>Insert title here</title>
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
		
		// 우선순위에 따라 글자 색 변경
		
	}
	
	function initData(){
		showTodoTop();
		showTodoMiddle();
	}
	
	function showTodoTop(){
		var todoBox = $('#todo-box');
		var divBox = $('<div></div>');
		var todo = $('<input>',{
						type 	: 	'text'
						,id		: 	'input-todo'
						,value	: 	'new todo'
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
				            name	: 	'priority',		// 동일한 name 속성을 가진 라디오 버튼들은 하나의 그룹으로 묶인다 
				            value	: 	priority.value	// 서버로 전송 
				        });
			 var radioBtnLabel = $('<label>', {
				            for		: 	priority.id,		// <input> id와 연결
				            text	: 	priority.text		// 라벨의 텍스트 내용(상,중,하)
				        });
			 
			 divBox.append(radioBtn).append(radioBtnLabel); 	// 라디오 버튼과 라벨을 반복문 내에서 divBox에 추가해야 된다 
		});
		
		var addBtn = $('<input>',{
						type 	:	'button'
						,id 	:	'add-btn'
						,value 	:	'add'
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
	    
	    // add 클릭하면 insert 되는 함수 만들기 
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
									,value		: 	todo.todo_no
									,checked	: 	todo.todo_check === 'Y' 
								});
					var checkBoxLable = $('<label></label>').text(todo.todo_content);
					
					// 완료, 삭제 버튼도 만들기 
					var doneBtn = $('<button>',{
									text	:	'완료'
								});
					var deleteBtn = $('<button>',{
									text	:	'삭제'
								});

                    divCheckBox.append(checkBox);
                    divCheckBox.append(checkBoxLable);
                    divCheckBox.append(doneBtn);
                    divCheckBox.append(deleteBtn);
                    
                    todoCheckBox.append(divCheckBox);
				}); 
				
			},
			error: function(e){
				console.log(e);
			}
		});
	}
	

</script>
</head>
<body>
	<h1>TodoList Page</h1>
	
	<div id="todo-box"></div>
	<div id="todo-check-box"></div>
</body>
</html>