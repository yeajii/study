package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.PostDao;
import com.oracle.oBootMybatis01.model.Post;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	
	private final PostDao pd;

	// 전체 리스트 갯수
	@Override
	public int postListTotalCount() {
		int postListTotalCount = pd.postListTotalCount();
		System.out.println("postServiceImpl postListTotalCount-> " + postListTotalCount);
		return postListTotalCount;
	}
	
	// 전체 리스트 
	@Override
	public List<Post> selectPost() {
		List<Post> selectPost = pd.selectPost();
		System.out.println("postServiceImpl selectPost.size-> " + selectPost.size());
		
		return selectPost;
	}

	// 상세 페이지
	@Override
	public Post contentPost(int postNo) {
		Post contentPost = pd.contentPost(postNo);
		System.out.println("postServiceImpl contentPost-> " + contentPost);
		
		return contentPost;
	}

	// 해당 글 삭제
	@Override
	public int deletePost(int postNo) {
		int deletePost = pd.deletePost(postNo);
		System.out.println("postServiceImpl deletePost-> " + deletePost);
		
		return deletePost;
	}

	// 해당 글 수정 
	@Override
	public int updatePostForm(Post post) {
		int updatePostForm = pd.updatePostForm(post);
		System.out.println("postServiceImpl updatePostForm-> " + updatePostForm);
		
		return updatePostForm;
	}

	// 새 글 입력 
	@Override
	public int insertPostForm(Post post) {
		int insertPostForm = pd.insertPostForm(post);
		System.out.println("postServiceImpl insertPostForm-> " + insertPostForm);
		
		return insertPostForm;
	}

	// 파일 삭제 (1 -> 0)
	@Override
	public int deleteFile(int postNo) {
		int deleteFile = pd.deleteFile(postNo);
		System.out.println("postServiceImpl deleteFile-> " + deleteFile);
		
		return deleteFile;
	}

	
	
	
	
}
