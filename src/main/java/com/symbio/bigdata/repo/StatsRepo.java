package com.symbio.bigdata.repo;

import com.symbio.bigdata.dto.StatsDto;
import com.symbio.bigdata.enums.PeriodType;
import com.symbio.bigdata.query.condition.StatsCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsRepo {
	private final static String STATS_SQL = "select * from stats_%s_%s where hr_id=? and status=? and classification=? and coach_time between ? and ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<StatsDto> findAll(String hrId, String level, PeriodType period, StatsCondition condition) {
		String sql = String.format(STATS_SQL, level.toLowerCase(), period.toString().toLowerCase());
		return jdbcTemplate.query(sql, processQueryParams(hrId, condition), new BeanPropertyRowMapper<StatsDto>(StatsDto.class));
	}
	
	//获取{statHrId，状态completed/acknowledge，coaching分类0/1，开始时间，结束时间}
	private String[] processQueryParams(String hrId, StatsCondition condition) {
		String[] params = new String[] {hrId, String.valueOf(condition.getCoachStatus().getValue()), 
				String.valueOf(condition.getCoachType().getValue()), condition.getStartTime(), condition.getEndTime()};
		return params;
	}
}
