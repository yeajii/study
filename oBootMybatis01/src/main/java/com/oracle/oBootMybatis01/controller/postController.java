package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.oBootMybatis01.model.Comt;
import com.oracle.oBootMybatis01.model.Post;
import com.oracle.oBootMybatis01.service.postService;

import lombok.RequiredArgsConstructor;
import oracle.net.aso.c;

@Controller
@RequiredArgsConstructor
public class postController {
	
	private final postService ps;
	
	@RequestMapping(value = "start")
	public String start(Model model) {
		
		// 전체 리스트 select 
		List<Post> allPostList = ps.allPostList();
		System.out.println("allPostList.size()-> " + allPostList.size());
		
		// 각 post 확인하기 위해 
		for (Post postList : allPostList) {
			System.out.println("postList" + postList);
		}
		model.addAttribute("allPostList", allPostList);
		
		return "move";
	}
	
// -------------------------------------------------------------	
	
	// insert 하기 위해 페이지 이동 
	@RequestMapping(value = "post_insert_move")
	public String postInsertMove() {
		return "post_insert";
	}

	// insert
	@PostMapping(value = "post_insert") 
	public String postInsert(@ModelAttribute Post post, Model model) {  
		// @ModelAttribute : 폼에서 입력한 것들을 Post 객체로 받아온 것인데 생략해도 무방 
		
		int postInsert = ps.postInsert(post);
		System.out.println("postInsert-> " + postInsert);
		model.addAttribute("postInsert", postInsert);
		
		return "redirect:/start";
	}

// -------------------------------------------------------------	
	
	// 상세 내역
	@RequestMapping(value = "post_content")
	public String postUpdate(int post_no, Model model) {
		
		// post 상세 내역
		Post postContent = ps.postContent(post_no);
		model.addAttribute("postContent", postContent);
		
		// 댓글 select 
		List<Comt> comtSelect = ps.comtSelect(post_no);
		System.out.println("comtSelect.size()-> " + comtSelect.size());
		model.addAttribute("comtSelect", comtSelect);
		
		return "post_content";
	}
	
	// 댓글 insert
	@PostMapping(value = "comt_insert")
	public String comtInsert(@ModelAttribute Comt comt, Model model, int post_no, RedirectAttributes redirectAttributes) {
		
		comt.setPost_no(post_no);
		int comtInsert = ps.comtInsert(comt);
		System.out.println("댓글 insert-> " + comtInsert);
		
		redirectAttributes.addAttribute("post_no", comt.getPost_no());
		
		return "redirect:/post_content?post_no={post_no}";
	}
	
// -------------------------------------------------------------	
	
	// 수정하기 위해 상세 내역 갖고 옴
	@RequestMapping(value = "post_update_move")
	public String postUpdateMove(int post_no, Model model) {
		
		Post postContent = ps.postContent(post_no);
		model.addAttribute("postContent", postContent);
		
		return "post_update";
	}
	
	
	// update
	@PostMapping(value = "post_update_start")
	public String postUpdateStart(Post post, Model model) {
		
		System.out.println(post.getPost_no());
		System.out.println(post.getPost_content());
		
		int postUpdateStart = ps.postUpdateStart(post);
		System.out.println("postUpdateStart-> " + postUpdateStart);
		model.addAttribute("postUpdateStart", postUpdateStart);
		
		return "redirect:/start";
	}
	
	
// -------------------------------------------------------------	

	// delete
	@ResponseBody
	@PostMapping(value = "post_delete")
	public int postDelete(int post_no) {
		
		int postDelete = ps.postDelete(post_no);
		System.out.println("postDelete-> " + postDelete);
		
		return postDelete;
	}
	
// -------------------------------------------------------------	
	
	
	
	
	
	
	
	
	
	
	
	
}
