package com.symbio.bigdata.service;

import com.symbio.bigdata.dto.StatsDto;
import com.symbio.bigdata.enums.PeriodType;
import com.symbio.bigdata.query.condition.StatsCondition;

import java.util.List;

public interface StatsService {
	List<StatsDto> findStatsForChart(String hrId, String level, PeriodType periodType, StatsCondition condition);
}
