package com.oracle.oBootMybatis01.service;

import java.util.List;

import com.oracle.oBootMybatis01.model.Comments;

public interface CommentsService {

	List<Comments> commentSelect(int postNo);	// 해당 글 댓글

	

}
