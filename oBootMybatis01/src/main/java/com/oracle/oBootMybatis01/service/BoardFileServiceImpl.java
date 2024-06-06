package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.BoardFileDao;
import com.oracle.oBootMybatis01.model.BoardFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardFileServiceImpl implements BoardFileService {

	private final BoardFileDao bfd;

	// 전체 리스트 갯수
	@Override
	public int boardFileCount() {
		int boardFileCount = bfd.boardFileCount();
		return boardFileCount;
	}

	// 전체 리스트 
	@Override
	public List<BoardFile> boardFileList() {
		List<BoardFile> boardFileList = bfd.boardFileList();
		return boardFileList;
	}

	// 새 글 입력 
	@Override
	public int insertBoardFileForm(BoardFile boardFile) {
		int insertBoardFileForm = bfd.insertBoardFileForm(boardFile);
		return insertBoardFileForm;
	}

	// 상세 페이지
	@Override
	public BoardFile contentBoardFile(int id) {
		BoardFile contentBoardFile = bfd.contentBoardFile(id);
		return contentBoardFile;
	}

	// 해당 글 수정 
	@Override
	public int updateBoardFileForm(BoardFile boardFile) {
		int updateBoardFileForm = bfd.updateBoardFileForm(boardFile);
		return updateBoardFileForm;
	}
	
}
