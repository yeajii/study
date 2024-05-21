package com.oracle.oBootMybatis01.service;

import java.util.List;

import com.oracle.oBootMybatis01.model.Todo;

public interface TodoService {

	List<Todo> selectTodoList();	// 전체 리스트 

}
