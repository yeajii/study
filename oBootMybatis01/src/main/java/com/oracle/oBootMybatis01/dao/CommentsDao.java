package com.oracle.oBootMybatis01.dao;

import java.util.List;

import com.oracle.oBootMybatis01.model.Comments;

public interface CommentsDao {

	List<Comments> selectComment(int postNo);	// 해당 글 댓글

	int insertComment(Comments comments);

}
