package com.oracle.oBootMybatis01.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Post {
	private int 	post_no;
	private String 	post_name;
	private String 	post_content;
	private Date 	create_date;
	
}
