package com.symbio.bigdata.enums;

public enum CoachStatus {
	COMPLETED(1), ACKNOWLEDGED(4);

	private int status;

	private CoachStatus(int status) {
		this.status = status;
	}

	public int getValue() {
		return this.status;
	}
}
