package com.oracle.oBootMybatis01.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentsDaoImpl implements CommentsDao {
	
	private final SqlSession session;
	
	
}