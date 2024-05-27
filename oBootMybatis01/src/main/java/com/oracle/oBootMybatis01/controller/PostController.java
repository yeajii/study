package com.oracle.oBootMybatis01.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
	
	// 전체 리스트 --------------------------------------------------------------
	@RequestMapping(value = "start")
	public String selectPost(Model model) {
		System.out.println("----- selectPost start -----");
		
		// 전체 리스트 갯수
		int postListTotalCount = ps.postListTotalCount();
		log.info("postListTotalCount : {}", postListTotalCount);
		
		List<Post> postList = ps.selectPost();
		log.info("selectPost.size : {}", postList.size());
		
//		for(Post post : postList) {
//			System.out.println("post-> " + post);
//		}
		
		model.addAttribute("postListTotalCount", postListTotalCount);
		model.addAttribute("postList", postList);
		
		return "post_select";
	}
	
	// 상세 페이지 --------------------------------------------------------------
	@GetMapping(value = "contentPost")
	public String contentPost(Model model, int postNo) {
		log.info("----- contentPost Start -----");
		
		Post contentPost = ps.contentPost(postNo);
		log.info("contentPost : {}", contentPost);
		
		int result;
		if((contentPost.getAttachName() != null && !contentPost.getAttachName().isEmpty()) && 
		   (contentPost.getAttachPath() != null && !contentPost.getAttachPath().isEmpty())) {
			result = 1;
		}else {
			result = 0;
		}
		
		model.addAttribute("contentPost", contentPost);
		model.addAttribute("result", result);
		
		return "post_content";
	}
	
	// 해당 글 삭제 --------------------------------------------------------------
	@ResponseBody	
	@RequestMapping(value = "deletePost")
	public int deletePost(int postNo) {
		log.info("----- deletePost Start -----");
		log.info("postNo : {}", postNo);
		
		int deletePost = ps.deletePost(postNo);
		log.info("deletePost : {}", deletePost);
		
		return deletePost;  
	}
	
	// 해당 글 수정 하기 위한 상세 정보 가져오기 ---------------------------------------------
	@GetMapping(value = "updatePost")
	public String updatePost(int postNo, Model model) {
		log.info("----- updatePost Start -----");
		
		Post contentPost = ps.contentPost(postNo);
		log.info("contentPost : {}", contentPost);
		
		int result;
		if((contentPost.getAttachName() != null && !contentPost.getAttachName().isEmpty()) && 
		   (contentPost.getAttachPath() != null && !contentPost.getAttachPath().isEmpty())) {
			result = 1;
		}else {
			result = 0;
		}
		
		model.addAttribute("contentPost", contentPost);
		model.addAttribute("result", result);
		
		return "post_update";
	}
	
	// 해당 글 수정 
	@PostMapping(value = "updatePostForm")
	public String updatePostForm(@ModelAttribute Post post
								,@RequestParam(value = "file1", required = false) MultipartFile file1
								,HttpServletRequest request
								,RedirectAttributes redirectAttributes) throws IOException {	// 리다이렉트 시 데이터 전달 위함
		log.info("----- updatePostForm Start -----");
		
		log.info("getPostNo: {}"	, post.getPostNo());
		log.info("getPostName: {}"	, post.getPostName());
		log.info("getPostContent: {}", post.getPostContent());
		
		// 파일 수정 O
		if(file1 != null && !file1.isEmpty()) {	
			// 이전 파일 삭제
			deleteFile(post.getAttachPath(), request);
			
			// 새 파일 업로드 
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
	        String saveName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);
	        
	        // 수정된 파일 정보 설정
	        post.setAttachName(file1.getOriginalFilename());
	        post.setAttachPath(saveName);
		}else {	
			// 파일 수정 X, 기존 파일 정보 유지 
			post.setAttachName(post.getAttachName());
	        post.setAttachPath(post.getAttachPath());
		}
		
		int updatePostForm = ps.updatePostForm(post);
		log.info("updatePostForm : {}", updatePostForm);
		
		redirectAttributes.addAttribute("postNo", post.getPostNo());
		
		return "redirect:/contentPost?postNo={postNo}";
	}
	
	// 이전 파일 삭제 
	private void deleteFile(String attachPath, HttpServletRequest request) {
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		File file = new File(uploadPath, attachPath);
		if(file.exists()) {
			file.delete();
			log.info("이전 파일 삭제 : {}", attachPath);
		}
	}
	
	// 새 글 입력하기 위한 페이지 이동 ---------------------------------------------------
	@RequestMapping(value = "insertPost")
	public String insertPost() {
		log.info("----- insertPost Start -----");
		
		return "post_insert";
	}
	
	// 새 글 입력 
	@RequestMapping(value = "insertPostForm", method = RequestMethod.POST)
	public String insertPostForm(@ModelAttribute Post post
								,@RequestParam(value = "file1", required = false) MultipartFile file1
								,HttpServletRequest request) throws IOException {	// 클라이언트가 보낸 정보들 들어있음
		log.info("----- insertPostForm Start -----");
		
		log.info("getPostName : {}", post.getPostName());
		log.info("getPostContent : {}", post.getPostContent());
		
		if(file1 != null && !file1.isEmpty()) {
			log.info("파일 insert O");
			
			// 저장 위치 지정
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");	
			log.info("uploadPath : {}", uploadPath);
			
			// post_insert.jsp 에서 enctype="multipart/form-data" 있어야 보임 
			log.info("getOriginalFilename : {}", file1.getOriginalFilename());	// 원본 파일명
			log.info("getContentType : {}", 	file1.getContentType());		// 파일 타입
			log.info("getSize : {}", 			file1.getSize());				// 파일 사이즈
			log.info("uploadPath : {}", 		uploadPath);					// 파일 저장되는 주소 
			
			// 저장되는 파일명
			String saveName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);	 
			log.info("saveName : {}", saveName);
			
			post.setAttachPath(saveName);
			post.setAttachName(file1.getOriginalFilename());
		}else {
			log.info("파일 insert X");
			post.setAttachPath(null);
	        post.setAttachName(null); 
		}
		
		log.info("getAttachName : {}", post.getAttachName());
		log.info("getAttachPath : {}", post.getAttachPath());
		
		int insertPostForm = ps.insertPostForm(post);
		log.info("insertPostForm : {}", insertPostForm);
		
		return "redirect:/start";
	}
	
	// uploadFile method
	private String uploadFile(String originalName, byte[] bytes, String uploadPath) throws IOException {
		log.info("----- private uploadFile method Start -----");
		log.info("uploadPath : {}", uploadPath);
		
		// universally unique identifier (UUID): 서버의 파일명 중복 방지 
		UUID uid = UUID.randomUUID();
		
		// 신규 폴더 (directory) 생성
		File fileDirectory = new File(uploadPath);
		if(!fileDirectory.exists()) {
			fileDirectory.mkdirs();
			log.info("업로드용 폴더 생성 : {}", uploadPath);
		}
		
		String savedName = uid.toString() + "_" + originalName;
		log.info("savedName : {}", savedName);
		
		File target = new File(uploadPath, savedName);
		FileCopyUtils.copy(bytes, target);
		
		return savedName;
	}
	
	
	
}
