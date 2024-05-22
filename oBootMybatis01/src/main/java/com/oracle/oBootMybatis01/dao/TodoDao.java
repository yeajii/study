package com.oracle.oBootMybatis01.dao;

import java.util.List;

import com.oracle.oBootMybatis01.model.Todo;

public interface TodoDao {

	List<Todo> selectTodoList();	// 전체 리스트 

	int doneTodo(Todo todo);		// 완료  (N -> Y)

	int deleteTodo(int todoNo);		// 삭제  (N -> Y)

	int addTodo(Todo todo);			// 입력

}
