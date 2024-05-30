package com.oracle.oBootMybatis01.service;

import java.util.List;

import com.oracle.oBootMybatis01.model.BoardFile;

public interface BoardFileService {

	int boardFileCount();				// 전체 리스트 갯수

	List<BoardFile> boardFileList();	// 전체 리스트 

	int insertBoardFileForm(BoardFile boardFile);	// 새 글 입력 

}
