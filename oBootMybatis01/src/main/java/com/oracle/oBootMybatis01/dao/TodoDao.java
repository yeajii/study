package com.oracle.oBootMybatis01.dao;

import java.util.List;

import com.oracle.oBootMybatis01.model.Todo;

public interface TodoDao {

	List<Todo> selectTodoList();	// 전체 리스트 

}
