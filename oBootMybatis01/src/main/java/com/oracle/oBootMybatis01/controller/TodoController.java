package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	

}
