package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.oBootMybatis01.model.Post;
import com.oracle.oBootMybatis01.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {
	
	private final PostService ps; 
	
	// 전체 리스트 
	@RequestMapping(value = "start")
	public String selectPost(Model model) {
		System.out.println("------------- start -----------------");
		
		// 전체 리스트 갯수
		int postListTotalCount = ps.postListTotalCount();
		log.info("postController postListTotalCount : {}", postListTotalCount);
		
		List<Post> postList = ps.selectPost();
		log.info("postController selectPost.size : {}", postList.size());
		
//		for(Post post : postList) {
//			System.out.println("postController post-> " + post);
//		}
		
		model.addAttribute("postListTotalCount", postListTotalCount);
		model.addAttribute("postList", postList);
		
		return "post_select";
	}
	
	// 상세 페이지 
	@GetMapping(value = "contentPost")
	public String contentPost(Model model, int postNo) {
		
		Post contentPost = ps.contentPost(postNo);
		log.info("postController contentPost : {}", contentPost);
		
		model.addAttribute("contentPost", contentPost);
		
		return "post_content";
	}
	
	// 해당 글 삭제
	@ResponseBody	// 자바 객체를  http 응답 body로 전송할 수 있다    
	@RequestMapping(value = "deletePost")
	public int deletePost(int postNo) {
		log.info("deletePost postNo : {}", postNo);
		
		int deletePost = ps.deletePost(postNo);
		log.info("deletePost : {}", deletePost);
		
		return deletePost;  
	}
	
	// 해당 글 수정 하기 위한 상세 정보 가져오기 
	@GetMapping(value = "updatePost")
	public String updatePost(int postNo, Model model) {
		
		Post contentPost = ps.contentPost(postNo);
		log.info("contentPost : {}", contentPost);
		
		model.addAttribute("contentPost", contentPost);
		
		return "post_update";
	}
	
	// 해당 글 수정 
	@PostMapping(value = "updatePostForm")
	public String updatePostForm(Post post, RedirectAttributes redirectAttributes) {
		log.info("updatePostForm : {}", post);
		
		int updatePostForm = ps.updatePostForm(post);
		log.info("updatePostForm : {}", updatePostForm);
		
		redirectAttributes.addAttribute("postNo", post.getPostNo());
		
		return "redirect:/contentPost?postNo={postNo}";
	}
	
	// 새 글 입력하기 위한 페이지 이동
	@RequestMapping(value = "insertPost")
	public String insertPost() {
		
		return "post_insert";
	}
	
	// 새 글 입력 
	@RequestMapping(value = "insertPostForm", method = RequestMethod.POST)
	public String insertPostForm(@RequestParam("postName") String postName
								,@RequestParam("postContent")String postContent) {
		
		Post post = new Post();
		post.setPostName(postName);
		post.setPostContent(postContent);
		
		log.info("insertPostForm 제목 : {}", post.getPostName());
		log.info("insertPostForm 내용 : {}", post.getPostContent());
		
		int insertPostForm = ps.insertPostForm(post);
		log.info("insertPostForm : {}", insertPostForm);
		
		return "redirect:/start";
	}
	
	
}
