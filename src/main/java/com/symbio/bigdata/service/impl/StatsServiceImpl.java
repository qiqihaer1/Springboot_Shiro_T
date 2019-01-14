package com.symbio.bigdata.service.impl;

import com.symbio.bigdata.dto.StatsDto;
import com.symbio.bigdata.enums.PeriodType;
import com.symbio.bigdata.query.condition.StatsCondition;
import com.symbio.bigdata.repo.StatsRepo;
import com.symbio.bigdata.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {
	@Autowired
	private StatsRepo statsRepo;
	
	@Override
	public List<StatsDto> findStatsForChart(String hrId, String level, PeriodType periodType, StatsCondition condition) {
		return statsRepo.findAll(hrId, level, periodType, condition);
	}

}
