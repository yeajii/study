package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Post;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class postDaoImpl implements postDao {
	
	private final SqlSession session;

	// 전체 리스트 select 
	@Override
	public List<Post> allPostList() {
		List<Post> allPostList = null;
		try {
			allPostList = session.selectList("allPostList");
			System.out.println("postDaoImpl allPostList.size()-> " + allPostList.size());
		} catch (Exception e) {
			System.out.println("allPostList Exception-> " + e);
		}
		return allPostList;
	}

	
	// insert
	@Override
	public int postInsert(Post post) {
		int postInsert = 0;
		try {
			postInsert = session.insert("postInsert", post);
			System.out.println("postDaoImpl postInsert-> " + postInsert);
		} catch (Exception e) {
			System.out.println("postInsert Exception-> " + e);
		}
		return postInsert;
	}


	// 상세 내역
	@Override
	public Post postContent(int post_no) {
		Post postContent = null;
		try {
			postContent = session.selectOne("postContent", post_no);
		} catch (Exception e) {
			System.out.println("postInsert Exception-> " + e);
		}
		return postContent;
	}

	// update
	@Override
	public int postUpdateStart(Post post) {
		int postUpdateStart = 0;
		
		try {
			postUpdateStart = session.update("postUpdateStart", post);
		} catch (Exception e) {
			System.out.println("postInsert Exception-> " + e);
		}
		return postUpdateStart;
	}

	
	// delete
	@Override
	public int postDelete(int post_no) {
		int postDelete = 0;
		try {
			postDelete = session.delete("postDelete", post_no);
		} catch (Exception e) {
			System.out.println("postInsert Exception-> " + e);
		}
		return postDelete;
	}

	

}
