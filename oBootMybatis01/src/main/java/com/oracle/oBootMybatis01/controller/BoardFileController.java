package com.oracle.oBootMybatis01.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
				if(!file.isEmpty()) {
					uploadPath = "C:\\boardFile\\" + boardFileId;
					saveName = uploadFile(file.getOriginalFilename(), file.getBytes(),uploadPath); 
					log.info("saveName : {}", saveName); 
				}
			}
	
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
		
		// 파일 이름 생성 
		int index = originalName.lastIndexOf(".");
		String extension = originalName.substring(index + 1);
		String nameWithoutExtension = originalName.substring(0, index);
		String savedName = nameWithoutExtension + "_" + uid.toString() + "." + extension;
		log.info("savedName : {}", savedName);
		
		File target = new File(uploadPath, savedName);
		Files.write(target.toPath(), bytes);
		
		return savedName;
	}
	
	// 상세 페이지 --------------------------------------------------------------
	@GetMapping(value = "contentBoardFile")
	public String contentBoardFile(Model model, int id) {
		log.info("----- contentBoardFile Start -----");
		
		BoardFile contentBoardFile = bfs.contentBoardFile(id);
		log.info("contentBoardFile : {}", contentBoardFile);
		
		String uploadPath = "C:\\boardFile\\" + id;	
		File dir = new File(uploadPath);
		
		// 파일 존재하면 리스트 반환, 존재하지 않을 시 빈 리스트 반환 : 유틸성이므로 FileUnti 클래스 만듦 함수로 이 기능 만듦 
		List<String> fileNameList = new ArrayList<>();
		if(dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();			// 지정된 디렉토리의 파일 목록을 가져오기 위해 사용
			if(files != null) {
				for(File file : files) {
					String fileName = file.getName();
					fileNameList.add(removeUUIDFromFileName(fileName));
				}
			}
		}
		
		for(String fileName : fileNameList) {
			log.info("fileName: {}", fileName);
		}
		
		model.addAttribute("contentBoardFile", contentBoardFile);
		model.addAttribute("fileNameList", fileNameList);
		
		return "boardFile/boardFile_content";
	}

	private String removeUUIDFromFileName(String fileName) { // 나중에 이것도 뺌 
		int firstIndex = fileName.lastIndexOf("_");			// "_" 의 인덱스 찾음, 없을 경우 -1 반환
		int lastIndex = fileName.lastIndexOf(".");

		if(firstIndex != -1 && lastIndex != -1 && firstIndex < lastIndex) {
			return fileName.substring(0, firstIndex) + fileName.substring(lastIndex);
		}else {
			return fileName;
		}
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
					fileNameList.add(removeUUIDFromFileName(fileName));
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
										@RequestParam(value = "files", required = false) MultipartFile[] files, // 수정은 이걸로 
										@RequestParam(value = "additionalFiles", required = false) MultipartFile[] additionalFiles, // 제거하고 체크 박스 여러 개 담는 리스트 
										@RequestParam(value = "fileNames", required = false) String[] fileNames, // 제거하기 
										HttpServletRequest request,
										RedirectAttributes redirectAttributes) throws IOException {
		log.info("----- updateBoardFileForm Start -----");
		
	    try {
	        String uploadPath = "C:\\boardFile\\" + boardFile.getId();
	        File dir = new File(uploadPath);

	        if (!dir.exists()) {
	            dir.mkdir();
	        }
	        
	        // 기존 파일 삭제 및 새로운 파일 업로드
	        if (files != null) {
	        	log.info("files.length: {}", files.length);
	            for (int i = 0; i < files.length; i++) {
	                MultipartFile file = files[i];
	                if (file != null && !file.isEmpty()) {
	                    String fileName = (fileNames != null && i < fileNames.length) ? fileNames[i] : null;
	                    if (fileName != null) {
	                        // 기존 파일 삭제
	                        deleteFile(uploadPath, fileName);
	                    }
	                    // 새 파일 업로드
	                    String saveName = uploadFile(file.getOriginalFilename(), file.getBytes(), uploadPath);
	                    log.info("saveName : {}", saveName);
	                }
	            }
	        }

	        // 추가 파일 업로드
	        if (additionalFiles != null) {
	            for (MultipartFile file : additionalFiles) {
	                if (file != null && !file.isEmpty()) {
	                    String saveName = uploadFile(file.getOriginalFilename(), file.getBytes(), uploadPath);
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
	        // 예외 처리 및 적절한 에러 페이지로 이동하도록 설정
	        return "errorPage";
	    }
	}

	// 파일 수정하기 위해 기존 파일 삭제  
	private void deleteFile(String uploadPath, String fileName) {
		File file = new File(uploadPath, fileName);
		if(file.exists()) {
			file.delete();
			log.info("파일 수정하기 위해 기존 파일: {} 삭제 완료!", fileName);
		}
	} 
	
	
}
