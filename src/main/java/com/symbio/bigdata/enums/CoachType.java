package com.symbio.bigdata.enums;

public enum CoachType {
	COACHING(0), TRIADCOACHING(1);
	
	private int type;

	private CoachType(int type) {
		this.type = type;
	}

	public int getValue() {
		return this.type;
	}
}
