package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.MonthlySales;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChartDaoImpl implements ChartDao {
	
	private final SqlSession session;

	@Override
	public List<MonthlySales> monthlySaleList() {
		
		List<MonthlySales> monthlySaleList = null;
		try {
			monthlySaleList = session.selectList("monthlySaleList");
			System.out.println("ChartDaoImpl monthlySaleList-> " + monthlySaleList.size());
		} catch (Exception e) {
			System.out.println("Exception monthlySaleList-> " + e);
		}
		return monthlySaleList;
	}

}
