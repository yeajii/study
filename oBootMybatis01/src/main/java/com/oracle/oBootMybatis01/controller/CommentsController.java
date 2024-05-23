package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@ResponseBody						// 요청으로 전송된 미디어 타입 지정 : 클라이언트가 전송한 데이터가 json 형식임을 명시
	@PostMapping(value = "insertComment", consumes = "application/json")	
	public ResponseEntity<?> insertComment(@RequestBody Comments comments) {	
										// @RequestBody: 전달된 json 데이터를 서버에서 자바 객체로 변환하여 처리
		
		log.info("insertComment commentBody : {}", comments.getCommentBody());
		log.info("insertComment postNo : {}", comments.getPostNo());
		
		int insertComment = cs.insertComment(comments);
		log.info("insertComment insertComment : {}", insertComment);
		
		// 상태 코드 200(OK)와 함께 정수 값 1을 응답 본문으로 반환
		return ResponseEntity.ok(insertComment);	
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
