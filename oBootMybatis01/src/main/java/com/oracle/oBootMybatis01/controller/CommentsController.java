package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.oBootMybatis01.model.Comments;
import com.oracle.oBootMybatis01.service.CommentsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentsController {
	
	private final CommentsService cs;
	
	// 해당 글 댓글
	@ResponseBody
	@RequestMapping(value = "commentSelect")
	public List<Comments> commentSelect(int postNo) {
		log.info("commentSelect postNo : {}", postNo);
		
		List<Comments> commentSelect = cs.commentSelect(postNo);
		log.info("commentSelect : {}", commentSelect);
		
		return commentSelect;
	}
	
	
	
	
}
