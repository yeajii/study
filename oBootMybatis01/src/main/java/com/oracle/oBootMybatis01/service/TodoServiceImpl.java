package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.TodoDao;
import com.oracle.oBootMybatis01.model.Todo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
	
	private final TodoDao td;

	// 전체 리스트 
	@Override
	public List<Todo> selectTodoList() {
		List<Todo> selectTodoList = td.selectTodoList();
		System.out.println("TodoServiceImpl selectTodoList.size()-> " + selectTodoList.size());
		
		return selectTodoList;
	}

	// 완료  (N -> Y)
	@Override
	public int doneTodo(Todo todo) {
		int doneTodo = td.doneTodo(todo);
		System.out.println("TodoServiceImpl doneTodo-> " + doneTodo);
		
		return doneTodo;
	}

	// 삭제  (N -> Y)
	@Override
	public int deleteTodo(int todoNo) {
		int deleteTodo = td.deleteTodo(todoNo);
		System.out.println("TodoServiceImpl deleteTodo-> " + deleteTodo);
		
		return deleteTodo;
	}

	// 입력
	@Override
	public int addTodo(Todo todo) {
		int addTodo = td.addTodo(todo);
		System.out.println("TodoServiceImpl addTodo-> " + addTodo);
		
		return addTodo;
	}

}
