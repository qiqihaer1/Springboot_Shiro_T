package com.symbio.bigdata.service;

import com.symbio.bigdata.dto.ManagerDto;
import com.symbio.bigdata.dto.StatsDto;
import com.symbio.bigdata.enums.PeriodType;
import com.symbio.bigdata.query.condition.StatsCondition;

import java.util.HashMap;
import java.util.List;

public interface ManagerService {

	ManagerDto findByHrId(String siteName, String statsHrId);

	List<ManagerDto> findL4ByHrId(String hrId);

	List<ManagerDto> findChildren(String siteName, String statsHrId, String level);

	HashMap<String,Object> findHasL2(String siteName, String hrId, String level);

	List<StatsDto> findChildrenData(String lobName, String statsHrId, PeriodType periodType, StatsCondition condition);

	List<StatsDto> findL4ChildrenData(String hrId, PeriodType periodType, StatsCondition condition);

	List<ManagerDto> findL4Children(String siteName, String statsHrId, String level);
}
