package com.oracle.oBootMybatis01.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.oBootMybatis01.model.BoardFile;
import com.oracle.oBootMybatis01.service.BoardFileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardFileController {
	
	private final BoardFileService bfs;
	
	// 전체 리스트 --------------------------------------------------------------
	@RequestMapping(value = "boardFile")
	public String boardFile(Model model) {
		System.out.println("---------- BoardFile Start -----------");
		
		// 전체 리스트 갯수
		int boardFileCount = bfs.boardFileCount();
		log.info("boardFileCount : {}", boardFileCount);
		
		// 전체 리스트 
		List<BoardFile> boardFileList = bfs.boardFileList();
		log.info("boardFileList.size : {}", boardFileList.size());
		
		model.addAttribute("boardFileCount", boardFileCount);
		model.addAttribute("boardFileList", boardFileList);
		
		return "boardFile/boardFile_list";
	}
	
	// 새 글 입력하기 위한 페이지 이동 ---------------------------------------------------
	@RequestMapping(value = "insertBoardFile")
	public String insertBoardFile() {
		System.out.println("---------- BoardFile Start -----------");
		
		return "boardFile/boardFile_insert";
	}
	
	// 새 글 입력 
	@PostMapping(value = "insertBoardFileForm")
	public String insertBoardFileForm(@ModelAttribute BoardFile boardFile
									,@RequestParam(value = "file1", required = true) MultipartFile file1
									,HttpServletRequest request) throws IOException {
		log.info("----- insertBoardFileForm Start -----");
		
		String uploadPath = "C:\\boardFile";
		String saveName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);
		log.info("saveName : {}", saveName);
		
		try {
	        int insertBoardFileForm = bfs.insertBoardFileForm(boardFile);
	        log.info("insertBoardFileForm : {}", insertBoardFileForm);
	    } catch (Exception e) {
	        log.error("Error inserting board file: ", e);
	        // 데이터베이스 삽입이 실패하면 파일 업로드를 롤백하기 위해 업로드된 파일을 삭제
	        File uploadedFile = new File(uploadPath, saveName);
	        if (uploadedFile.exists()) {
	            uploadedFile.delete();
	            log.info("Uploaded file deleted due to insert error: {}", saveName);
	        }
	        throw e;
	    }
		return "redirect:/boardFile";
	}
	
	private String uploadFile(String originalName, byte[] bytes, String uploadPath) throws IOException {
		log.info("----- private uploadFile method Start -----");
		log.info("uploadPath : {}", uploadPath);
		
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
	
	// 상세 페이지 --------------------------------------------------------------
	
	
}