package com.symbio.bigdata.dto;

import java.io.Serializable;

public class ManagerDto implements Serializable {
	private Integer id;
	private String hrId;
	private Integer historyId;
	private String firstname;
	private String lastname;
	private String parentId;
	private String level;
	private String name;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHrId() {
		return hrId;
	}
	public void setHrId(String hrId) {
		this.hrId = hrId;
	}
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ManagerDto{" +
				"hrId='" + hrId + '\'' +
				", parentId='" + parentId + '\'' +
				", level='" + level + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
