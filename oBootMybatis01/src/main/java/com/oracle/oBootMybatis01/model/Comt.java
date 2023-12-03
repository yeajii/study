package com.oracle.oBootMybatis01.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Comt {
	private int 	comment_id;
	private int 	post_no;
	private String 	comment_content;
	private Date 	comment_create_date;

}
