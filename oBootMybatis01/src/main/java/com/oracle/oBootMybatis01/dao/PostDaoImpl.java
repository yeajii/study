package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Post;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostDaoImpl implements PostDao {
	
	private final SqlSession session;

	// 전체 리스트 갯수
	@Override
	public int postListTotalCount() {
		
		int postListTotalCount = 0;
		try {
			postListTotalCount = session.selectOne("postListTotalCount");
			System.out.println("postDaoImpl postListTotalCount-> " + postListTotalCount);
		} catch (Exception e) {
			System.out.println("postListTotalCount Exception-> " + e.getMessage());
		}
		return postListTotalCount;
	}
	
	// 전체 리스트 
	@Override
	public List<Post> selectPost() {
		
		List<Post> selectPost = null;
		try {
			selectPost = session.selectList("selectPost");
			System.out.println("postDaoImpl selectPost.size-> " + selectPost.size());
		} catch (Exception e) {
			System.out.println("postSelect Exception-> " + e.getMessage());
		}
		return selectPost;
	}

	// 상세 페이지
	@Override
	public Post contentPost(int postNo) {
		
		Post contentPost = null;
		try {
			contentPost = session.selectOne("contentPost", postNo);
			System.out.println("postDaoImpl contentPost-> " + contentPost);
		} catch (Exception e) {
			System.out.println("contentPost Exception-> " + e.getMessage());
		}
		return contentPost;
	}

	// 해당 글 삭제
	@Override
	public int deletePost(int postNo) {
		
		int deletePost = 0;
		try {
			deletePost = session.update("deletePost", postNo);
			System.out.println("postDaoImpl deletePost-> " + deletePost);
		} catch (Exception e) {
			System.out.println("deletePost Exception-> " + e.getMessage());
		}
		return deletePost;
	}

	// 해당 글 수정 
	@Override
	public int updatePostForm(Post post) {
		
		int updatePostForm = 0;
		try {
			updatePostForm = session.update("updatePostForm", post);
			System.out.println("postDaoImpl updatePostForm-> " + updatePostForm);
		} catch (Exception e) {
			System.out.println("updatePostForm Exception-> " + e.getMessage());
		}
		return updatePostForm;
	}

	// 새 글 입력 
	@Override
	public int insertPostForm(Post post) {
		
		int insertPostForm = 0;
		try {
			insertPostForm = session.insert("insertPostForm", post);
			System.out.println("postDaoImpl insertPostForm-> " + insertPostForm);
		} catch (Exception e) {
			System.out.println("insertPostForm Exception-> " + e.getMessage());
		}
		return insertPostForm;
	}
	

	

}
