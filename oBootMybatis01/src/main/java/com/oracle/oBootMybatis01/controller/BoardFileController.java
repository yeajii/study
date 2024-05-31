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
		
	    int boardFileId;
	    // 변수 초기화, 예외 발생 시 이 변수들이 참조할 기본값 
	    String uploadPath = null;
	    String saveName = null;
		
		try {
	        int insertBoardFileForm = bfs.insertBoardFileForm(boardFile);
	        log.info("insertBoardFileForm : {}", insertBoardFileForm);
	        
	        // PK 값 얻기:  Mapper에 설정한 keyProperty="id"에 의해 자동으로 설정
			boardFileId = boardFile.getId();
			log.info("boardFileId : {}", boardFileId);
			
			// 파일 저장 경로 설정 (boardFile 폴더의 하위 폴더로 PK 값 사용)
		    uploadPath = "C:\\boardFile\\" + boardFileId;
		    saveName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);
		    log.info("saveName : {}", saveName);
		    
	    } catch (Exception e) {
	    	log.error("Error during insertBoardFileForm process: ", e.getMessage());
	    	
	    	// 업로드된 파일 삭제 
	    	if (uploadPath != null && saveName != null) {
	            File uploadedFile = new File(uploadPath, saveName);
	            if (uploadedFile.exists()) {
	                uploadedFile.delete();
	                log.info("Uploaded file deleted due to insert error: {}", saveName);
	            }
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
