package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Todo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TodoDaoImpl implements TodoDao {
	
	private final SqlSession session;

	// 전체 리스트 
	@Override
	public List<Todo> selectTodoList() {
		
		List<Todo> selectTodoList = null;
		try {
			selectTodoList = session.selectList("selectTodoList");
			System.out.println("TodoDaoImpl selectTodoList-> " + selectTodoList);
		} catch (Exception e) {
			System.out.println("selectTodoList Exception-> " + e.getMessage());
		}
		return selectTodoList;
	}

	// 완료  (N -> Y)
	@Override
	public int doneTodo(Todo todo) {
		
		int doneTodo = 0;
		try {
			doneTodo = session.update("doneTodo", todo);
			System.out.println("TodoDaoImpl doneTodo-> " + doneTodo);
		} catch (Exception e) {
			System.out.println("doneTodo Exception-> " + e.getMessage());
		}
		return doneTodo;
	}

	// 삭제  (N -> Y)
	@Override
	public int deleteTodo(int todoNo) {
		
		int deleteTodo = 0;
		try {
			deleteTodo = session.update("deleteTodo", todoNo);
			System.out.println("TodoDaoImpl deleteTodo-> " + deleteTodo);
		} catch (Exception e) {
			System.out.println("deleteTodo Exception-> " + e.getMessage());
		}
		return deleteTodo;
	}

	// 입력
	@Override
	public int addTodo(Todo todo) {
		
		int addTodo = 0;
		try {
			addTodo = session.insert("addTodo", todo);
			System.out.println("TodoDaoImpl addTodo-> " + addTodo);
		} catch (Exception e) {
			System.out.println("addTodo Exception-> " + e.getMessage());
		}
		return addTodo;
	}
	
	

}
