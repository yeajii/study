package com.oracle.oBootMybatis01.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.oBootMybatis01.model.BoardFile;
import com.oracle.oBootMybatis01.service.BoardFileService;

import comUtil.FileUtil;
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
									,@RequestParam(value = "files", required = false) MultipartFile[] files
									,HttpServletRequest request) throws IOException {
		log.info("----- insertBoardFileForm Start -----");		
		
	    int boardFileId;
	    // 변수 초기화, 예외 발생 시 이 변수들이 참조할 기본값 
	    String uploadPath = null;
	    String saveName = null;
		
		try {
	        int insertBoardFileForm = bfs.insertBoardFileForm(boardFile);
	        log.info("insertBoardFileForm : {}", insertBoardFileForm);
	        
	        // PK 값 얻기: Mapper에 설정한 keyProperty="id"에 의해 자동 설정
			boardFileId = boardFile.getId();
			log.info("boardFileId : {}", boardFileId);
			
			for(MultipartFile file : files) {
				if(file != null && !file.isEmpty()) {
					uploadPath = "C:\\boardFile\\" + boardFileId;
					saveName = FileUtil.uploadFile(file.getOriginalFilename(), file.getBytes(),uploadPath);
					log.info("saveName : {}", saveName); 
				}
			}
	
	    } catch (Exception e) {
	    	log.error("Error during insertBoardFileForm process: ", e.getMessage());
	    	
	    	// 업로드된 파일 삭제 
	    	if (uploadPath != null && saveName != null) {
	    		FileUtil.deleteFile(uploadPath, saveName);
	    		log.info("Uploaded file deleted due to insert error: {}", saveName);
	    	}
	        throw e;
	    }
	    return "redirect:/boardFile";
	}	
	
	// 상세 페이지 --------------------------------------------------------------
	@GetMapping(value = "contentBoardFile")
	public String contentBoardFile(Model model, int id) {
		log.info("----- contentBoardFile Start -----");
		
		BoardFile contentBoardFile = bfs.contentBoardFile(id);
		log.info("contentBoardFile : {}", contentBoardFile);
		
		// 웹 브라우저에서 파일 경로로 접근할 때는 슬래시(/)를 사용해야 됨 
		String uploadPath = "C:/boardFile/" + id;	
		File dir = new File(uploadPath);
		
		// 파일 존재하면 리스트 반환, 존재하지 않을 시 빈 리스트 반환 : 유틸성이므로 FileUnti 클래스 만듦 함수로 이 기능 만듦 
		List<String> fileInfoList = new ArrayList<>();
		if(dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();			// 지정된 디렉토리의 파일 목록을 가져오기 위해 사용
			if(files != null) {
				for(File file : files) {
					String fileName = file.getName();
					String displayFileName = FileUtil.removeUUIDFromFileName(fileName);
	                fileInfoList.add(uploadPath + "/" + fileName + "," + displayFileName); // 경로와 표시할 이름을 함께 저장
				}
			}
		}
		
		for (String fileInfo : fileInfoList) {
	        log.info("fileInfo: {}", fileInfo);
	    }
		
		model.addAttribute("contentBoardFile", contentBoardFile);
		model.addAttribute("fileInfoList", fileInfoList);
		
		return "boardFile/boardFile_content";
	}
	
	// 해당 글 수정 하기 위한 상세 정보 가져오기 ---------------------------------------------
	@GetMapping("updateBoardFile")
	public String updateBoardFile(int id, Model model) {
		log.info("----- updateBoardFile Start -----");
		
		BoardFile contentBoardFile = bfs.contentBoardFile(id);
		log.info("contentBoardFile : {}", contentBoardFile);
		
		String uploadPath = "C:\\boardFile\\" + id;
		File dir = new File(uploadPath);
		
		List<String> fileNameList = new ArrayList<>();
		if(dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();			
			if(files != null) {
				for(File file : files) {
					String fileName = file.getName();
					fileNameList.add(fileName);
				}
			}
		}
		
		model.addAttribute("contentBoardFile", contentBoardFile);
		model.addAttribute("fileNameList", fileNameList);
		
		return "boardFile/boardFile_update";
	}
	
	// 해당 글 수정 
	@PostMapping(value = "updateBoardFileForm")
	public String updateBoardFileForm(@ModelAttribute BoardFile boardFile,
										@RequestParam(value = "files", required = false) MultipartFile[] files,
										@RequestParam(value = "deleteFileList", required = false) List<String> deleteFileList,
										RedirectAttributes redirectAttributes) throws IOException {
		log.info("----- updateBoardFileForm Start -----");
		
	    try {
	        String uploadPath = "C:\\boardFile\\" + boardFile.getId();
	        File dir = new File(uploadPath);

	        if (!dir.exists()) {
	            dir.mkdir();
	        }
	        
	        // 기존 파일 삭제
	        if (deleteFileList != null) {
	        	log.info("deleteFileList.size(): {}", deleteFileList.size());
	            for (String fileName : deleteFileList) {
	            	log.info("삭제할 파일 이름: {}", fileName);
	            	FileUtil.deleteFile(uploadPath, fileName);
                }
            }
	        
	        // 폴더 안에 파일 전부 삭제된 경우, 폴더도 삭제함
	        if(dir.isDirectory() && dir.list().length == 0) {
	        	dir.delete();
	        	log.info("폴더 삭제됨");
	        }

	        // 파일 업로드
	        if (files != null) {
	            for (MultipartFile file : files) {
	                if (file != null && !file.isEmpty()) {
	                	String saveName = FileUtil.uploadFile(file.getOriginalFilename(), file.getBytes(), uploadPath);
	                    log.info("saveName : {}", saveName);
	                }
	            }
	        }

	        int updateBoardFileForm = bfs.updateBoardFileForm(boardFile);
	        log.info("updateBoardFileForm: {}", updateBoardFileForm);

	        redirectAttributes.addAttribute("id", boardFile.getId());

	        return "redirect:/contentBoardFile?id={id}";
	        
	    } catch (Exception e) {
	        log.error("Error during updateBoardFileForm process: ", e);
	        
	        return "errorPage";
	    }
	}

	
	
	
}
