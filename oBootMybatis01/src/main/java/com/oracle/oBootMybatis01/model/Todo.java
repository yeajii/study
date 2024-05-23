package com.oracle.oBootMybatis01.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Todo {
	private int 	todo_no;
	private String 	todo_content;
	private String 	use_yn;				// 삭제 Y/N
	private String 	todo_check;			// 완료 Y/N
	private Date 	todo_date;
	private int 	todo_priority;		// (높) 1,2,3 (낮)

}
