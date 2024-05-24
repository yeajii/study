package com.oracle.oBootMybatis01.model;

import lombok.Data;

@Data
public class MonthlySales {
	private int 	id;
	private String 	month;		// 디폴트 형태: 2000-01 
	private int 	sales;		// 해당 월의 매출액

}
