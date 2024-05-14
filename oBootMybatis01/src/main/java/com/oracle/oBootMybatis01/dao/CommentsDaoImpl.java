package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Comments;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentsDaoImpl implements CommentsDao {
	
	private final SqlSession session;

	// 해당 글 댓글
	@Override
	public List<Comments> commentSelect(int postNo) {
	
		List<Comments> commentSelect = null;
		try {
			commentSelect = session.selectList("commentSelect", postNo);
			System.out.println("CommentsDaoImpl commentSelect -> " + commentSelect);
		} catch (Exception e) {
			System.out.println("commentSelect Exception-> " + e.getMessage());
		}
		return commentSelect;
	}
	
	
}
