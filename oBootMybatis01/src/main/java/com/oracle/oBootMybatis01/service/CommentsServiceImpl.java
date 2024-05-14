package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.CommentsDao;
import com.oracle.oBootMybatis01.model.Comments;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
	
	private final CommentsDao cd;

	// 해당 글 댓글
	@Override
	public List<Comments> commentSelect(int postNo) {
		List<Comments> commentSelect = cd.commentSelect(postNo);
		System.out.println("CommentsServiceImpl commentSelect.size()-> " + commentSelect.size());
		
		return commentSelect;
	}

	
	
}
