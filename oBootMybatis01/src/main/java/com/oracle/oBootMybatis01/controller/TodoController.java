package com.oracle.oBootMybatis01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TodoController {
	
	private final TodoService ts;
	
	@RequestMapping(value = "todo")
	public String selectTodo() {
		
		return "todo_list";
	}

}
