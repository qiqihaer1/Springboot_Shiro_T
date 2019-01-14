package com.symbio.bigdata.dto;

public class StatsDto {
	private String hrId;
	private String firstName;
	private String lastName;
	private Double rate;
	private String coachTime;
	private String name;
	private int status;
	private int classification;
	
	public String getHrId() {
		return hrId;
	}
	public void setHrId(String hrId) {
		this.hrId = hrId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getCoachTime() {
		return coachTime;
	}
	public void setCoachTime(String coachTime) {
		this.coachTime = coachTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getClassification() {
		return classification;
	}

	public void setClassification(int classification) {
		this.classification = classification;
	}

	@Override
	public String toString() {
		return "StatsDto{" +
				"hrId='" + hrId + '\'' +
				", rate=" + rate +
				", coachTime='" + coachTime + '\'' +
				", status=" + status +
				", classification=" + classification +
				'}';
	}
}
