package com.oracle.oBootMybatis01.model;

import java.sql.Date;

import lombok.Data;

@Data
public class BoardFile {
	private int 	id;
	private String 	title;
	private String 	content;
	private Date 	createDate;
	private String 	isDeleted;		// 0 삭제, 1 존재 

}
