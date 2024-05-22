package com.oracle.oBootMybatis01.service;

import java.util.List;

import com.oracle.oBootMybatis01.model.Todo;

public interface TodoService {

	List<Todo> selectTodoList();	// 전체 리스트 

	int doneTodo(Todo todo);		// 완료  (N -> Y)

	int deleteTodo(int todoNo);		// 삭제  (N -> Y)

	int addTodo(Todo todo);			// 입력

}
