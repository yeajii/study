package com.oracle.oBootMybatis01.dao;

import java.util.List;

import com.oracle.oBootMybatis01.model.BoardFile;

public interface BoardFileDao {

	int boardFileCount();							// 전체 리스트 갯수

	List<BoardFile> boardFileList();				// 전체 리스트 

	int insertBoardFileForm(BoardFile boardFile);	// 새 글 입력 

	BoardFile contentBoardFile(int id);				// 상세 페이지

	int updateBoardFileForm(BoardFile boardFile);	// 해당 글 수정 

}
