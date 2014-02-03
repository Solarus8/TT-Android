package com.metapresence.android.terratraveler.api;

public enum ActivityType {

	RUNNING(1),
	GYM(2),
	WALK(3),
	CLUBBING(4),
	CAFE(5),
	DOG_WALK(6),
	DOG_PARK(7),
	CHILD_PARK(8),
	SITE_SEE(9),
	DRINKS(10),
	EVENT(23);


	private final int value;


	ActivityType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value; 
	}

}
