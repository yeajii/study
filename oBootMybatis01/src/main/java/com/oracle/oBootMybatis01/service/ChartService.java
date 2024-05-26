package com.oracle.oBootMybatis01.service;

import java.util.List;

import com.oracle.oBootMybatis01.model.MonthlySales;

public interface ChartService {

	List<MonthlySales> monthlySaleList();		// 월간 판매 리스트

}
