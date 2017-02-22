package me.euzebe.mele.entrypoints;

import java.util.Map;

import lombok.Data;

@Data
public class DrawResponse {
	private String drawID;
	private Map<String, String> assignments;

	public DrawResponse withDrawID(String id) {
		setDrawID(id);
		return this;
	}

	public DrawResponse withAssignments(Map<String, String> hashMap) {
		setAssignments(hashMap);
		return this;
	}
}
