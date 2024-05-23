package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.oBootMybatis01.model.Todo;
import com.oracle.oBootMybatis01.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TodoController {
	
	private final TodoService ts;
	
	@RequestMapping(value = "todoList")
	public String todoList() {
		System.out.println("------------- todoList -----------------");
		
		return "todo_list";
	}
	
	// 전체 리스트 
	@ResponseBody
	@GetMapping(value = "selectTodoList")
	public List<Todo> selectTodo() {
		
		List<Todo> selectTodoList = ts.selectTodoList();
		log.info("selectTodoList : {}" , selectTodoList);
		
		return selectTodoList;
	}
	
	// 완료  (N -> Y)
	@ResponseBody
	@PostMapping(value = "doneTodo")
	public int doneTodo(int todoNo, String todoCheck) {
		log.info("doneTodo todoNo: {}" , todoNo);
		log.info("doneTodo todoCheck: {}" , todoCheck);
		
		if("true".equals(todoCheck)) {
			todoCheck = "Y";
		}else {
			todoCheck = "N";
		}
		
		Todo todo = new Todo();
		todo.setTodo_no(todoNo);
		todo.setTodo_check(todoCheck);
		
		int doneTodo = ts.doneTodo(todo);
		log.info("doneTodo : {}" , doneTodo);
		
		return doneTodo;
	}
	
	// 삭제  (N -> Y)
	@ResponseBody
	@PostMapping(value = "deleteTodo")
	public int deleteTodo(int todoNo) {
		log.info("deleteTodo todoNo: {}", todoNo);
		
		int deleteTodo = ts.deleteTodo(todoNo);
		log.info("deleteTodo : {}", deleteTodo);
		
		return deleteTodo;
	}

	// 입력
	@ResponseBody
	@PostMapping(value = "addTodo", consumes = "application/json")
	public int addTodo(@RequestBody Todo todo) {
		log.info("addTodo todo.getTodo_content(): {}", todo.getTodo_content());
		log.info("addTodo todo.getTodo_priority(): {}", todo.getTodo_priority());
		
		int addTodo = ts.addTodo(todo);
		log.info("addTodo : {}", addTodo);
		
		return addTodo;
	}
	
	
}
