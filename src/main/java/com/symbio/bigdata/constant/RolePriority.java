package com.symbio.bigdata.constant;

import java.util.HashMap;
import java.util.Map;

public class RolePriority {
	public final static Integer L2_PRIORITY = 3;
	public final static Map<String, Integer> ROLES_PRIORITY = new HashMap<>();
	
	static {
		ROLES_PRIORITY.put("AGENT", 1);
		ROLES_PRIORITY.put("L1", 2);
		ROLES_PRIORITY.put("L2", 3);
		ROLES_PRIORITY.put("L3", 4);
		ROLES_PRIORITY.put("L4", 5);
		ROLES_PRIORITY.put("VP", 6);
		ROLES_PRIORITY.put("SVP", 7);
	}
}
