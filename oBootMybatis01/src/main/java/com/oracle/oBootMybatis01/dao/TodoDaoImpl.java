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
	
	

}
