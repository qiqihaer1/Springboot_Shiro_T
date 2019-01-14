package com.symbio.bigdata.repo;

import com.symbio.bigdata.dto.ManagerDto;
import com.symbio.bigdata.dto.StatsDto;
import com.symbio.bigdata.enums.PeriodType;
import com.symbio.bigdata.query.condition.StatsCondition;
import com.symbio.bigdata.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ManagerRepo {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ManagerDto> findByHrId(String siteName,String statsHrId) {
		String sql = "select hr_id,CONCAT(firstname,' ',lastname) AS name,parent_id,level from manager where hr_id='"+statsHrId+"'";
		if(!"".equals(siteName)){
			sql = "select hr_id,CONCAT(firstname,' ',lastname) AS name,parent_id,level from manager where parent_id = '"+siteName +"' AND level = 'L3'";
		}
		try {
			return jdbcTemplate.query(sql, new String[] {}, new BeanPropertyRowMapper<ManagerDto>(ManagerDto.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<ManagerDto> selectL4ByHrId(String hrId) {
		String sql="SELECT distinct(parent_id,\"_\",3) FROM manager WHERE parent_id in(SELECT b.parent_id from manager b WHERE b.`level`='L4' AND SUBSTRING_INDEX(b.hr_id,\"_\",-1)='"+hrId+"') and level=\"L3\"";
		try {
			return jdbcTemplate.query(sql, new String[] {}, new BeanPropertyRowMapper<ManagerDto>(ManagerDto.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<ManagerDto> findChildren(String siteName,String statsHrId,String level) {
		if("L4".equalsIgnoreCase(level)||"VP".equalsIgnoreCase(level)||"SVP".equalsIgnoreCase(level)){
			level = "L3";
		}
		System.out.println("siteName: " + siteName);
		System.out.println("statsHrId: " + statsHrId);
		System.out.println("level: " + level);
		String sql = "";
		switch (level) {
			case "AGENT":
				sql = "select a.hr_id,CONCAT(a.firstname,' ',a.lastname) AS name,a.parent_id,a.level from manager a where a.hr_id = '"+ statsHrId +"' and a.`level` = 'AGENT'";
				break;
			case "L1":
				sql = "select a.hr_id,CONCAT(a.firstname,' ',a.lastname) AS name,a.parent_id,a.level from manager a where a.parent_id = '"+ statsHrId +"' and a.`level` = 'AGENT'" ;
				break;
			case "L3":
				sql = "select a.hr_id,CONCAT(a.firstname,' ',a.lastname) AS name,a.parent_id,a.level from manager a where a.parent_id = '"+ siteName +"' and (a.`level` = 'L2' or a.`level` = 'L1')";
				break;
			default:
				break;
		}
		try {
			return jdbcTemplate.query(sql, new String[] {}, new BeanPropertyRowMapper<ManagerDto>(ManagerDto.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<ManagerDto> findHasL2(String siteName,String hrId,String level){
//		String sqlLevel = "select a.`level` from manager a where a.hr_id = '"+ hrId +"'";
//		if("AGENT".equals(level)){
//			List<ManagerDto> managerDtos = jdbcTemplate.query(sqlLevel, new String[] {}, new BeanPropertyRowMapper<ManagerDto>(ManagerDto.class));
//			Optional<ManagerDto> opManager = managerDtos.stream().sorted((m1, m2) -> {
//				return ROLES_PRIORITY.get(m2.getLevel().toUpperCase())
//						.compareTo(ROLES_PRIORITY.get(m1.getLevel().toUpperCase()));
//			}).findFirst();
//			ManagerDto manager = opManager.isPresent() ?  opManager.get() : null;
//			level = manager.getLevel().toUpperCase();
//		}

		if("L4".equalsIgnoreCase(level)||"VP".equalsIgnoreCase(level)||"SVP".equalsIgnoreCase(level)){
			level = "L3";
		}
		String sql = "";
		switch (level) {
			case "AGENT":
				sql = "select b.parent_id,CONCAT(b.firstname,' ',b.lastname) AS name,b.`level` from manager b where b.level='L1' AND b.hr_id =(select a.parent_id from manager a where a.`level` = 'AGENT'and hr_id = '" + hrId + "')";
				break;
			case "L1":
				sql = "select a.parent_id,CONCAT(a.firstname,' ',a.lastname) AS name,a.`level` from manager a where a.hr_id = '" + hrId + "' and a.`level` = 'L1'" ;
				break;
			case "L3":
				sql = "select a.hr_id,CONCAT(a.firstname,' ',a.lastname) AS name,a.parent_id,a.level from manager a where a.parent_id = '"+ siteName +"' and (a.`level` = 'L2' or a.`level` = 'L1')";
				break;
			default:
				break;
		}
		try {
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper<ManagerDto>(ManagerDto.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<ManagerDto> getCurData(String hrId,String level){
		String sql = "select hr_id,CONCAT(firstname,' ',lastname) AS name,parent_id,level  from manager where hr_id = '"+ hrId +"' and `level` = '"+ level +"'";
		try {
			return jdbcTemplate.query(sql, new String[] {}, new BeanPropertyRowMapper<ManagerDto>(ManagerDto.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<StatsDto> findChildrenData(String lobName, String statsHrId, PeriodType periodType, StatsCondition condition){
		// stats_agent_weekly
		String tableNm = "stats_";
		//？？？？？？？？？
		String level = condition.getLevel().toLowerCase();
		if("L4".equalsIgnoreCase(level)||"VP".equalsIgnoreCase(level)||"SVP".equalsIgnoreCase(level)){
			level = "L3";
		}
		int coachStatus = condition.getCoachStatus().getValue();
		int coachType = condition.getCoachType().getValue();
		String startTime = condition.getStartTime();
		String endTime = condition.getEndTime();

		if("L3".equalsIgnoreCase(level)||"L4".equalsIgnoreCase(level)||"VP".equalsIgnoreCase(level)||"SVP".equalsIgnoreCase(level)){ //TODO 有 L2 的情况
			tableNm +=  "l1";
		}else if(("L1".equalsIgnoreCase(level) || "AGENT".equalsIgnoreCase(level)) && coachType == 0){//COACHING
			tableNm +=  "agent";
		}else if(("L1".equalsIgnoreCase(level) || "AGENT".equalsIgnoreCase(level)) && coachType == 1){//TRAIDCOACHING
			tableNm +=  "l1";
		}


		if(periodType.WEEKLY.equals(periodType)){//weekly
			tableNm = tableNm + "_" + "weekly";
			if(startTime == null || "".equals(startTime)){
				LocalDate localDate = LocalDate.now();
				LocalDate firstDayOfWeek = DateUtil.getFirstDayOfWeek(localDate);
                startTime = DateUtil.formatToDateStr(firstDayOfWeek);
			}
			if(endTime == null || "".equals(endTime)){
				LocalDate localDate = LocalDate.now();
				endTime = DateUtil.formatToDateStr(localDate);
			}

		}else if(periodType.MONTHLY.equals(periodType)){//monthly
			tableNm = tableNm + "_" + "monthly";
			if(startTime == null || "".equals(startTime)){
				LocalDate localDate = LocalDate.now();
				LocalDate firstDayOfWeek = DateUtil.getFirstDayOfMonth(localDate);
				startTime = DateUtil.formatToDateStr(firstDayOfWeek);
			}
			if(endTime == null || "".equals(endTime)){
				LocalDate localDate = LocalDate.now();
				endTime = DateUtil.formatToDateStr(localDate);
			}
		}else {//default weekly
			tableNm = tableNm + "_" + "weekly";
			if(startTime == null || "".equals(startTime)){
				LocalDate localDate = LocalDate.now();
				LocalDate firstDayOfWeek = DateUtil.getFirstDayOfWeek(localDate);
				startTime = DateUtil.formatToDateStr(firstDayOfWeek);
			}
			if(endTime == null || "".equals(endTime)){
				LocalDate localDate = LocalDate.now();
				endTime = DateUtil.formatToDateStr(localDate);
			}
		}
		String sqlRate = null;


		if("AGENT".equals(condition.getLevel())){
			 sqlRate = "select hr_id,'AGENT' AS level,CONCAT(first_name,' ',last_name) AS name,rate,`status`,classification,coach_time,create_time from "+ tableNm +" where hr_id = '"+ statsHrId +"' AND  status="+coachStatus+" AND classification="+coachType;
			 if(startTime != null && !"".equals(startTime)){
			 	sqlRate = sqlRate + " AND coach_time >= '"+startTime+"'";
			 }
			if(endTime != null && !"".equals(endTime)){
				sqlRate = sqlRate + " AND coach_time <='"+endTime+"'";
			}
		}else  if("L3".equals(condition.getLevel())||"L4".equalsIgnoreCase(condition.getLevel())||"VP".equalsIgnoreCase(condition.getLevel())||"SVP".equalsIgnoreCase(condition.getLevel())){
			sqlRate = "select hr_id,'L1' AS level,CONCAT(first_name,' ',last_name) AS name,rate,`status`,classification,coach_time,create_time from "+ tableNm +" where hr_id in (select a.hr_id from manager a where a.parent_id = '"+ lobName +"' AND a.level='L1') AND  status="+coachStatus+" AND classification="+coachType;
			if(startTime != null && !"".equals(startTime)){
				sqlRate = sqlRate + " AND coach_time >= '" + startTime + "'";
			}
			if(endTime != null && !"".equals(endTime)){
				sqlRate = sqlRate + " AND coach_time <= '" + endTime +"'";
			}
		}else{
			if(coachType == 0){//COACHING
				sqlRate = "select hr_id,'AGENT' AS level,CONCAT(first_name,' ',last_name) AS name,rate,`status`,classification,coach_time,create_time from "+ tableNm +" where hr_id in (select a.hr_id from manager a where a.parent_id = '"+ statsHrId +"') AND  status="+coachStatus+" AND classification="+coachType;
			}else if(coachType == 1){//TRAIDCOACHING
				sqlRate = "select hr_id,'L1' AS level,CONCAT(first_name,' ',last_name) AS name,rate,`status`,classification,coach_time,create_time from "+ tableNm +" where hr_id = '"+ statsHrId +"' AND  status="+coachStatus+" AND classification="+ coachType;
			}

			if(startTime != null && !"".equals(startTime)){
				sqlRate = sqlRate + " AND coach_time >= '" + startTime + "'";
			}
			if(endTime != null && !"".equals(endTime)){
				sqlRate = sqlRate + " AND coach_time <= '" + endTime +"'";
			}
		}
		try {
			return jdbcTemplate.query(sqlRate, new String[] {}, new BeanPropertyRowMapper<StatsDto>(StatsDto.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	//测试，获取L4的子L3
	public List<ManagerDto> selectL4Children(String siteName,String statsHrId,String level) {
		//VP、SVP=L4
		if("VP".equalsIgnoreCase(level)||"SVP".equalsIgnoreCase(level)){
			level = "L4";
		}
		System.out.println("siteName: " + siteName);
		System.out.println("statsHrId: " + statsHrId);
		System.out.println("level: " + level);
		String hr_id = statsHrId.split("_")[3];

		String sql = "select a.hr_id,CONCAT(a.firstname,' ',a.lastname) AS name,a.parent_id,a.level from manager a where a.parent_id = '"+ siteName +"' and (a.`level` = 'L2' or a.`level` = 'L1');";

		switch (level) {
			case "AGENT":
				sql = "select a.hr_id,CONCAT(a.firstname,' ',a.lastname) AS name,a.parent_id,a.level from manager a where a.hr_id = '"+ statsHrId +"' and a.`level` = 'AGENT'";
				break;
			case "L1":
				sql = "select a.hr_id,CONCAT(a.firstname,' ',a.lastname) AS name,a.parent_id,a.level from manager a where a.parent_id = '"+ statsHrId +"' and a.`level` = 'AGENT'" ;
				break;
			case "L3":
				sql = "select a.hr_id,CONCAT(a.firstname,' ',a.lastname) AS name,a.parent_id,a.level from manager a where a.parent_id = '"+ siteName +"' and (a.`level` = 'L2' or a.`level` = 'L1')";
				break;
			case "L4":
				sql ="SELECT distinct(parent_id),level FROM manager WHERE parent_id in(SELECT b.parent_id from manager b WHERE b.`level`='L4' AND SUBSTRING_INDEX(b.hr_id,\"_\",-1)='"+hr_id+"') and level='L3'";
				break;
			default:
				break;
		}
		try {
			return jdbcTemplate.query(sql, new String[] {}, new BeanPropertyRowMapper<ManagerDto>(ManagerDto.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	//测试，获取L4的子L3的统计信息
	public List<StatsDto> selectL4ChildrenData(String hrId, PeriodType periodType, StatsCondition condition){
//		String hr_id=statsHrId.split("_")[3];
		String statsHrId=condition.getVendorId() + "_" + condition.getLobId() + "_" + condition.getSiteId() + "_" + hrId;
		// stats_agent_weekly
		String tableNm = "stats_";
		//？？？？？？？？？
		String level = condition.getLevel().toLowerCase();
		int coachStatus = condition.getCoachStatus().getValue();
		int coachType = condition.getCoachType().getValue();
		String startTime = condition.getStartTime();
		String endTime = condition.getEndTime();

		if("L4".equalsIgnoreCase(level)){
			tableNm +=  "l3";
		}else if("L3".equalsIgnoreCase(level)||"VP".equalsIgnoreCase(level)||"SVP".equalsIgnoreCase(level)){ //TODO 有 L2 的情况
			tableNm +=  "l1";
		}else if(("L1".equalsIgnoreCase(level) || "AGENT".equalsIgnoreCase(level)) && coachType == 0){//COACHING
			tableNm +=  "agent";
		}else if(("L1".equalsIgnoreCase(level) || "AGENT".equalsIgnoreCase(level)) && coachType == 1){//TRAIDCOACHING
			tableNm +=  "l1";
		}

		if(periodType.WEEKLY.equals(periodType)){//weekly
			tableNm = tableNm + "_" + "weekly";
			if(startTime == null || "".equals(startTime)){
				LocalDate localDate = LocalDate.now();
				LocalDate firstDayOfWeek = DateUtil.getFirstDayOfWeek(localDate);
				startTime = DateUtil.formatToDateStr(firstDayOfWeek);
			}
			if(endTime == null || "".equals(endTime)){
				LocalDate localDate = LocalDate.now();
				endTime = DateUtil.formatToDateStr(localDate);
			}

		}else if(periodType.MONTHLY.equals(periodType)){//monthly
			tableNm = tableNm + "_" + "monthly";
			if(startTime == null || "".equals(startTime)){
				LocalDate localDate = LocalDate.now();
				LocalDate firstDayOfWeek = DateUtil.getFirstDayOfMonth(localDate);
				startTime = DateUtil.formatToDateStr(firstDayOfWeek);
			}
			if(endTime == null || "".equals(endTime)){
				LocalDate localDate = LocalDate.now();
				endTime = DateUtil.formatToDateStr(localDate);
			}
		}else {//default weekly
			tableNm = tableNm + "_" + "weekly";
			if(startTime == null || "".equals(startTime)){
				LocalDate localDate = LocalDate.now();
				LocalDate firstDayOfWeek = DateUtil.getFirstDayOfWeek(localDate);
				startTime = DateUtil.formatToDateStr(firstDayOfWeek);
			}
			if(endTime == null || "".equals(endTime)){
				LocalDate localDate = LocalDate.now();
				endTime = DateUtil.formatToDateStr(localDate);
			}
		}

		String sqlRate = null;
		if("L4".equalsIgnoreCase(condition.getLevel())){
			String siteNames=
					sqlRate ="select hr_id,'L3' AS level,rate,`status`,classification,coach_time,create_time from stats_l3_weekly where hr_id in (SELECT distinct(SUBSTRING_INDEX(parent_id,\"_\",3)) FROM manager WHERE parent_id in(SELECT b.parent_id from manager b WHERE b.`level`='L4' AND SUBSTRING_INDEX(b.hr_id,\"_\",-1)='"+hrId+"') and level=\"L3\")  AND  status="+coachStatus+" AND classification="+coachType;
			if(startTime != null && !"".equals(startTime)){
				sqlRate = sqlRate + " AND coach_time >= '" + startTime + "'";
			}
			if(endTime != null && !"".equals(endTime)){
				sqlRate = sqlRate + " AND coach_time <= '" + endTime +"'";
			}
		}else{
			if(coachType == 0){//COACHING
				sqlRate = "select hr_id,'AGENT' AS level,CONCAT(first_name,' ',last_name) AS name,rate,`status`,classification,coach_time,create_time from "+ tableNm +" where hr_id in (select a.hr_id from manager a where a.parent_id = '"+ statsHrId +"') AND  status="+coachStatus+" AND classification="+coachType;
			}else if(coachType == 1){//TRAIDCOACHING
				sqlRate = "select hr_id,'L1' AS level,CONCAT(first_name,' ',last_name) AS name,rate,`status`,classification,coach_time,create_time from "+ tableNm +" where hr_id = '"+ statsHrId +"' AND  status="+coachStatus+" AND classification="+ coachType;
			}

			if(startTime != null && !"".equals(startTime)){
				sqlRate = sqlRate + " AND coach_time >= '" + startTime + "'";
			}
			if(endTime != null && !"".equals(endTime)){
				sqlRate = sqlRate + " AND coach_time <= '" + endTime +"'";
			}
		}
		try {
			return jdbcTemplate.query(sqlRate, new String[] {}, new BeanPropertyRowMapper<StatsDto>(StatsDto.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


}
