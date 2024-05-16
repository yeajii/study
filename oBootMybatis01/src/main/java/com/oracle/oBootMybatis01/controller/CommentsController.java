package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	@RequestMapping(value = "selectComment")
	public List<Comments> selectComment(int postNo) {
		log.info("commentSelect postNo : {}", postNo);
		
		List<Comments> selectComment = cs.selectComment(postNo);
		log.info("selectComment : {}", selectComment);
		
		return selectComment;
	}
	
	// 댓글 입력 
	@RequestMapping(value = "insertComment", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> insertComment(@RequestParam("commentBody") String commentBody,
															@RequestParam("postNo") int postNo) {
		log.info("insertComment commentBody : {}", commentBody);
		log.info("insertComment postNo : {}", postNo);
		
		Comments comments = new Comments();
		comments.setCommentBody(commentBody);
		comments.setPostNo(postNo);
		
		int insertComment = cs.insertComment(comments);
		log.info("insertComment insertComment : {}", insertComment);
		
		Map<String, String> result = new HashMap<String, String>();
		if(insertComment == 1) {
			result.put("status", "success");
		}else {
			result.put("status", "error");
		}
		
		return ResponseEntity.ok().body(result);
	}
	
	// 댓글 삭제 
	@ResponseBody
	@RequestMapping(value = "deleteComment", method = RequestMethod.POST)
	public int deleteComment(int postNo, int commentNo) {
		log.info("deleteComment postNo : {}", postNo);
		log.info("deleteComment commentNo : {}", commentNo);
		
		Comments comments = new Comments();
		comments.setPostNo(postNo);
		comments.setCommentNo(commentNo);
		
		int deleteComment = cs.deleteComment(comments);
		log.info("deleteComment deleteComment : {}", deleteComment);
		
		return deleteComment;
	}
	
	
	
}
