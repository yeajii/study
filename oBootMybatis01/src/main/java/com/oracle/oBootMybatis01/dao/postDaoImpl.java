package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Comt;
import com.oracle.oBootMybatis01.model.Post;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class postDaoImpl implements postDao {
	
	private final SqlSession session;

	// �쟾泥� 由ъ뒪�듃 select 
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


	// �긽�꽭 �궡�뿭
	@Override
	public Post postContent(int post_no) {
		Post postContent = null;
		try {
			postContent = session.selectOne("postContent", post_no);
		} catch (Exception e) {
			System.out.println("postContent Exception-> " + e);
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
			System.out.println("postUpdateStart Exception-> " + e);
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
			System.out.println("postDelete Exception-> " + e);
		}
		return postDelete;
	}

	// 댓글 select 
	@Override
	public List<Comt> comtSelect(int post_no) {
		List<Comt> comtSelect = null;
		
		try {
			comtSelect = session.selectList("comtSelectList", post_no);
		} catch (Exception e) {
			System.out.println("comtSelect Exception-> " + e);
		}
		return comtSelect;
	}

	// 댓글 insert 
	@Override
	public int comtInsert(Comt comt) {
		int comtInsert = 0;
		try {
			comtInsert = session.insert("comtInsert", comt);
		} catch (Exception e) {
			System.out.println("comtSelect Exception-> " + e);
		}
		return comtInsert;
	}

	

}
