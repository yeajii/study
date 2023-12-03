package com.oracle.oBootMybatis01.service;

import java.util.List;

import com.oracle.oBootMybatis01.model.Comt;
import com.oracle.oBootMybatis01.model.Post;

public interface postService {

	List<Post> 			allPostList(); 			// �쟾泥� 由ъ뒪�듃 select 

	int 				postInsert(Post post); 	// insert

	Post 				postContent(int post_no);	//  �긽�꽭 �궡�뿭

	int 				postUpdateStart(Post post); // update

	int 				postDelete(int post_no);   // delete

	List<Comt> 			comtSelect(int post_no);    // 댓글 select 

	int 				comtInsert(Comt comt);		// 댓글 insert

}
