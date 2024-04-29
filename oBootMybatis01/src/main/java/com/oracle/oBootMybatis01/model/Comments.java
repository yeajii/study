package com.oracle.oBootMybatis01.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Comments {
	private int 	postNo;
	private int	 	commentNo;
	private Date 	createDate;
	private Date 	modifyDate;
	private String	commentBody;
	private String	isDeleted; 		// 1 삭제 안함, 0 삭제 
}
