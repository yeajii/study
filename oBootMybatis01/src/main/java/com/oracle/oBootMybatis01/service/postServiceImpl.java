package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.postDao;
import com.oracle.oBootMybatis01.model.Post;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class postServiceImpl implements postService {
	
	private final postDao pd;

	// 전체 리스트 select 
	@Override
	public List<Post> allPostList() {
		List<Post> allPostList = pd.allPostList();
		System.out.println("postServiceImpl allPostList.size()-> " + allPostList.size());
		return allPostList;
	}

	
	// insert
	@Override
	public int postInsert(Post post) {
		int postInsert = pd.postInsert(post);
		
		return postInsert;
	}

	
	//  상세 내역
	@Override
	public Post postContent(int post_no) {
		Post postContent = pd.postContent(post_no);
		
		return postContent;
	}


	// update
	@Override
	public int postUpdateStart(Post post) {
		int postUpdateStart = pd.postUpdateStart(post);
		
		return postUpdateStart;
	}

	// delete
	@Override
	public int postDelete(int post_no) {
		int postDelete = pd.postDelete(post_no);
		
		return postDelete;
	}
	
	
	
	
	
	
	
	
}
