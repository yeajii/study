package com.oracle.oBootMybatis01.dao;

import java.util.List;

import com.oracle.oBootMybatis01.model.MonthlySales;

public interface ChartDao {

	List<MonthlySales> monthlySaleList();		// 월간 판매 리스트

}
