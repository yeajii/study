package com.oracle.oBootMybatis01.dao;

import java.util.List;

import com.oracle.oBootMybatis01.model.Post;

public interface postDao {

	List<Post> 			allPostList();			// 전체 리스트 select 

	int 				postInsert(Post post);	// insert

	Post 				postContent(int post_no);	// 상세 내역

	int 				postUpdateStart(Post post);  // update

	int 				postDelete(int post_no);	// delete

	

}
