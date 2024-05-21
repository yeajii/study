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

}
