package com.oracle.oBootMybatis01.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Post {
	private int 	postNo;
	private String 	postName;
	private String 	postContent;
	private Date 	createDate;
	private String 	isDeleted;		// 1 삭제 안함, 0 삭제 
	private String 	attachName;		// 첨부파일명
	private String 	attachPath;		// 첨부파일경로
	
}
