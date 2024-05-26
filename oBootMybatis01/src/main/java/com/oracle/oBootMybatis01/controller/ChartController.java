package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.oBootMybatis01.model.MonthlySales;
import com.oracle.oBootMybatis01.service.ChartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChartController {
	
	private final ChartService chS; 
	
	@RequestMapping(value = "chart")
	public String chart() {
		System.out.println("---------- chart -----------");
		
		return "showChart/chart";
	}
	
	// 월간 판매 리스트
	@ResponseBody
	@GetMapping(value = "getSalesData")
	public List<MonthlySales> getSalesData(){
		
		List<MonthlySales> monthlySaleList = chS.monthlySaleList();
		log.info("monthlySaleList : {}", monthlySaleList.size());
		
		return monthlySaleList;
	}

}
