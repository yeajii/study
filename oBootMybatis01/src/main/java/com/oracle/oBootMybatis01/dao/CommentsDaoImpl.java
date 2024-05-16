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
	public List<Comments> selectComment(int postNo) {
	
		List<Comments> selectComment = null;
		try {
			selectComment = session.selectList("selectComment", postNo);
			System.out.println("CommentsDaoImpl selectComment -> " + selectComment);
		} catch (Exception e) {
			System.out.println("selectComment Exception-> " + e.getMessage());
		}
		return selectComment;
	}

	// 댓글 입력 
	@Override
	public int insertComment(Comments comments) {

		int insertComment = 0;
		try {
			insertComment = session.insert("insertComment", comments);
			System.out.println("CommentsDaoImpl insertComment -> " + insertComment);
		} catch (Exception e) {
			System.out.println("insertComment Exception-> " + e.getMessage());
		}
		return insertComment;
	}

	// 댓글 삭제 
	@Override
	public int deleteComment(Comments comments) {

		int deleteComment = 0;
		try {
			deleteComment = session.update("deleteComment", comments);
			System.out.println("CommentsDaoImpl deleteComment -> " + deleteComment);
		} catch (Exception e) {
			System.out.println("deleteComment Exception-> " + e.getMessage());
		}
		return deleteComment;
	}
	
	
}
