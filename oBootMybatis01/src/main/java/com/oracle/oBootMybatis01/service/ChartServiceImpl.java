package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.ChartDao;
import com.oracle.oBootMybatis01.model.MonthlySales;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChartServiceImpl implements ChartService {

	private final ChartDao chD;

	// 월간 판매 리스트
	@Override
	public List<MonthlySales> monthlySaleList() {
		List<MonthlySales> monthlySaleList = chD.monthlySaleList();
		System.out.println("ChartServiceImpl monthlySaleList-> " + monthlySaleList.size());
		
		return monthlySaleList;
	}
	
}
