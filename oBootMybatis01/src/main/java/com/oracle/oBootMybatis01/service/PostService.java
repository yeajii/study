package com.oracle.oBootMybatis01.service;

import java.util.List;

import com.oracle.oBootMybatis01.model.Post;

public interface PostService {

	int postListTotalCount();		// 전체 리스트 갯수
	
	List<Post> selectPost();		// 전체 리스트 

	Post contentPost(int postNo);	// 상세 페이지

	int deletePost(int postNo);		// 해당 글 삭제

	int updatePostForm(Post post);	// 해당 글 수정 

	int insertPostForm(Post post);	// 새 글 입력 

	int deleteFile(int postNo);		// 파일 삭제 (1 -> 0)

	


	
}
