package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.BoardFile;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardFileDaoImpl implements BoardFileDao {
	
	private final SqlSession session;

	// 전체 리스트 갯수
	@Override
	public int boardFileCount() {
		
		int boardFileCount = 0;
		try {
			boardFileCount = session.selectOne("boardFileCount");
			System.out.println("BoardFileDaoImpl boardFileCount-> " + boardFileCount);
		} catch (Exception e) {
			System.out.println("boardFileCount Exception-> " + e.getMessage());
		}
		return boardFileCount;
	}

	// 전체 리스트 
	@Override
	public List<BoardFile> boardFileList() {

		List<BoardFile> boardFileList = null;
		try {
			boardFileList = session.selectList("boardFileList");
			System.out.println("BoardFileDaoImpl boardFileList-> " + boardFileList);
		} catch (Exception e) {
			System.out.println("boardFileList Exception-> " + e.getMessage());
		}
		return boardFileList;
	}

	@Override
	public int insertBoardFileForm(BoardFile boardFile) {

		int insertBoardFileForm = 0;
		try {
			insertBoardFileForm = session.insert("insertBoardFileForm", boardFile);
			System.out.println("BoardFileDaoImpl insertBoardFileForm-> " + insertBoardFileForm);
		} catch (Exception e) {
			System.out.println("insertBoardFileForm Exception-> " + e.getMessage());
			throw e;
		}
		return insertBoardFileForm;
	}
	

}
