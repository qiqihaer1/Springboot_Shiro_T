package com.symbio.bigdata.query.condition;

import com.symbio.bigdata.enums.CoachStatus;
import com.symbio.bigdata.enums.CoachType;

import java.io.Serializable;

public class StatsCondition implements Serializable {
	private CoachType coachType;
	private CoachStatus coachStatus;
	private String vendorId;
	private String lobId;
	private String siteId;
	private String startTime;
	private String endTime;
	private String hrId;
	private String level;
	
	public CoachType getCoachType() {
		return coachType;
	}
	public void setCoachType(CoachType coachType) {
		this.coachType = coachType;
	}
	public CoachStatus getCoachStatus() {
		return coachStatus;
	}
	public void setCoachStatus(CoachStatus coachStatus) {
		this.coachStatus = coachStatus;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getLobId() {
		return lobId;
	}
	public void setLobId(String lobId) {
		this.lobId = lobId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHrId() {
		return hrId;
	}

	public void setHrId(String hrId) {
		this.hrId = hrId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
