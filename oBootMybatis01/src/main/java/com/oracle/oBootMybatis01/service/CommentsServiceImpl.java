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
	public List<Comments> selectComment(int postNo) {
		List<Comments> selectComment = cd.selectComment(postNo);
		System.out.println("CommentsServiceImpl selectComment.size()-> " + selectComment.size());
		
		return selectComment;
	}

	@Override
	public int insertComment(Comments comments) {
		int insertComment = cd.insertComment(comments);
		System.out.println("CommentsServiceImpl insertComment-> " + insertComment);
		
		return insertComment;
	}

	
	
}
