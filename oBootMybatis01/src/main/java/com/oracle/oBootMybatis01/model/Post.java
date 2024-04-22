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
	
}
